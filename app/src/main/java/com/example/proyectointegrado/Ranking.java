package com.example.proyectointegrado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
    String ip = "192.168.1.105";
    //cambiar entre documentos de php
    String prim = "usuarios.php";
    //cambiar campos
    String id = "idusuario";
    String campo = "nombreUsuario";
    JSONArray result;
    JSONObject jsonobject;

    //ranking
    ListView listaranking;
    ArrayList<String> rank;
    ArrayAdapter<String> adapter;
    ConsultaRemota acceso;
    int posicion;
    String idIdioma, nombreIdioma;

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

    }

    // Consulta idioma
    private class ConsultaRemota extends AsyncTask<Void, Void, String> {
        // Constructor
        public ConsultaRemota() {
        }

        // Inspectores
        protected void onPreExecute() {
            Toast.makeText(Ranking.this, "Obteniendo datos...", Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(Void... argumentos) {
            try {
                // Crear la URL de conexión al API
                URL url = new URL("http://" + ip + "/ApiRest/" + prim);
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
                    idIdioma = jsonobject.getString(id);
                    nombreIdioma = jsonobject.getString(campo);
                    responseBody.close();
                    responseBodyReader.close();
                    myConnection.disconnect();
                } else {
                    // Error en la conexión
                    Log.println(Log.ERROR, "Error2", "¡Conexión fallida!");
                }
            } catch (Exception e) {
                Log.println(Log.ERROR, "Error1", e.getMessage());
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
                        rank.add(jsonobject.getString(id) + "  -  " + jsonobject.getString(campo));
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}