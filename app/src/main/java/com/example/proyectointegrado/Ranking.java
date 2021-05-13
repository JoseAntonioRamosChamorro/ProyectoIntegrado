package com.example.proyectointegrado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Ranking extends AppCompatActivity {
    //modificar en caso de que cambie la ip del equipo
    String ip = "192.168.1.108";
    //cambiar entre documentos de php
    String php1 = "usuarios.php";
    String php2 = "usuarios2.php";
    //cambiar campos
    String campo = "nombreUsuario";
    String campo2 = "puntuacionUsuario";
    JSONArray result;
    JSONObject jsonobject;

    //ranking
    ListView listaranking;
    ArrayList<String> rank;
    ArrayAdapter<String> adapter;
    ConsultaRemota acceso;
    ConsultaRemota2 acceso2;
    int posicion;
    String nombreUsuario, puntuacionusu;

    Button btnRankVolver, btnRankSolo5, btnTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        //crear ranking
        listaranking = findViewById(R.id.listaRanking);
        rank = new ArrayList<>();
        // Creamos el adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rank);
        // Asignamos el adaptador a nuestro ListView
        listaranking.setAdapter(adapter);

        acceso = new ConsultaRemota();
        acceso.execute();
        //botones

        btnTodo = findViewById(R.id.btnTodo);
        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceso = new ConsultaRemota();
                acceso.execute();
            }
        });

        btnRankSolo5 = findViewById(R.id.btnRankSolo5);
        btnRankSolo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceso2 = new ConsultaRemota2();
                acceso2.execute();
            }
        });


        btnRankVolver = findViewById(R.id.btnRankVolver);
        btnRankVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Ranking.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });


    }

    // Consulta
    private class ConsultaRemota extends AsyncTask<Void, Void, String> {
        // Constructor
        public ConsultaRemota() {
        }

        // Inspectores
        protected void onPreExecute() { ;
        }

        protected String doInBackground(Void... argumentos) {
            try {
                // Crear la URL de conexión al API
                URL url = new URL("http://" + ip + "/ApiRest/" + php1);
                // Crear la conexión HTTP
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                // Establecer método de comunicación.
                myConnection.setRequestMethod("GET");
                if (myConnection.getResponseCode() == 200) {
                    // Conexión exitosa
                    // Creamos Stream para la lectura de datos desde el servidor
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    // Creamos Buffer de lectura
                    BufferedReader bR = new BufferedReader(responseBodyReader);
                    String line = "";
                    StringBuilder responseStrBuilder = new StringBuilder();
                    // Leemos el flujo de entrada
                    while ((line = bR.readLine()) != null) {
                        responseStrBuilder.append(line);
                    }
                    // Parseamos respuesta en formato JSON
                    result = new JSONArray(responseStrBuilder.toString());
                    // Nos quedamos solamente con la primera
                    posicion = 0;
                    jsonobject = result.getJSONObject(posicion);
                    // Sacamos dato a dato obtenido
                    nombreUsuario = jsonobject.getString(campo);
                    puntuacionusu = jsonobject.getString(campo2);
                    responseBody.close();
                    responseBodyReader.close();
                    myConnection.disconnect();
                } else {
                    // Error en la conexión
                    Log.println(Log.ERROR, "Error2", "¡Conexión fallida!");
                }
            } catch (Exception e) {
                Log.println(Log.ERROR, "Error1", e.getMessage() + ">Conexion fallida 2");
            }
            return (null);
        }

        protected void onPostExecute(String mensaje) {
            // Añado los idiomas obtenidos a la lista
            try {
                rank.clear();
                if (result != null) {
                    int longitud = result.length();
                    for (int i = 0; i < longitud; i++) {
                        jsonobject = result.getJSONObject(i);
                        rank.add(jsonobject.getString(campo) + ": puntuación->" + jsonobject.getString(campo2));
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    // Consulta 2
    private class ConsultaRemota2 extends AsyncTask<Void, Void, String> {
        // Constructor
        public ConsultaRemota2() {
        }

        // Inspectores
        protected void onPreExecute() {
        }

        protected String doInBackground(Void... argumentos) {
            try {
                // Crear la URL de conexión al API
                URL url = new URL("http://" + ip + "/ApiRest/" + php2);
                // Crear la conexión HTTP
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                // Establecer método de comunicación.
                myConnection.setRequestMethod("GET");
                if (myConnection.getResponseCode() == 200) {
                    // Conexión exitosa
                    // Creamos Stream para la lectura de datos desde el servidor
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    // Creamos Buffer de lectura
                    BufferedReader bR = new BufferedReader(responseBodyReader);
                    String line = "";
                    StringBuilder responseStrBuilder = new StringBuilder();
                    // Leemos el flujo de entrada
                    while ((line = bR.readLine()) != null) {
                        responseStrBuilder.append(line);
                    }
                    // Parseamos respuesta en formato JSON
                    result = new JSONArray(responseStrBuilder.toString());
                    // Nos quedamos solamente con la primera
                    posicion = 0;
                    jsonobject = result.getJSONObject(posicion);
                    // Sacamos dato a dato obtenido
                    nombreUsuario = jsonobject.getString(campo);
                    puntuacionusu = jsonobject.getString(campo2);
                    responseBody.close();
                    responseBodyReader.close();
                    myConnection.disconnect();
                } else {

                    // Error en la conexión

                    Log.println(Log.ERROR, "Error2", "¡Conexión fallida!");
                }
            } catch (Exception e) {
                Log.println(Log.ERROR, "Error1", e.getMessage() + ">Conexion fallida 2");
            }
            return (null);
        }

        protected void onPostExecute(String mensaje) {
            // Añado los idiomas obtenidos a la lista
            try {
                rank.clear();
                if (result != null) {
                    int longitud = result.length();
                    for (int i = 0; i < longitud; i++) {
                        jsonobject = result.getJSONObject(i);
                        rank.add(jsonobject.getString(campo) + ": puntuación->" + jsonobject.getString(campo2));
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}