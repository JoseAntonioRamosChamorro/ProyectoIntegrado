package com.example.proyectointegrado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Final extends AppCompatActivity {
    TextView nombre, puntos, descripcion;
    String name;
    int nota;

    Button reiniciar, subir;
    AltaRemota alta;

    //modificar en caso de que cambie la ip del equipo
    String ip = "192.168.1.107";
    //cambiar entre documentos de php
    String prim = "usuarios.php";
    String campo ="nombreUsuario";
    String campo2 ="puntuacionUsuario";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        //intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("nombreUsu");
        nota = intent.getIntExtra("notaUsu", 0);

        nombre = findViewById(R.id.mostrarNombre);
        puntos = findViewById(R.id.mostrarPuntos);
        descripcion = findViewById(R.id.descripcion);
        reiniciar = findViewById(R.id.btnReiniciar);
        subir = findViewById(R.id.btnSubir);

        descripcion.setText("");
        //ver resultado
        if (nota == 0) {
            descripcion.setText("0 puntos, deberias aprender a reciclar");
        } else if (nota == 1) {
            descripcion.setText("1 punto, deberias repasar el como reciclar");
        } else if (nota == 2) {
            descripcion.setText("2 puntos, te falta un poco mas saber reciclar lo necesario");
        } else if (nota == 3) {
            descripcion.setText("3 puntos, sabes reciclar lo justo");
        } else if (nota == 4) {
            descripcion.setText("4 puntos, sabes reciclar bien");
        } else if (nota == 5) {
            descripcion.setText("5 puntos, reciclas perfectamente");
        }
        //mostrar nombre y nota
        nombre.setText(name);
        puntos.setText(nota + "");


        //botones
        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Final.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alta = new AltaRemota(nombre.getText().toString(), puntos.getText().toString());
                alta.execute();

            }
        });
    }
    // Alta idiomas
    private class AltaRemota extends AsyncTask<Void, Void, String> {
        // Atributos
        String nombre;
        String puntosusu;

        // Constructor
        public AltaRemota(String nombre, String puntos) {
            this.nombre = nombre;
            this.puntosusu = puntos;
        }

        // Inspectores
        protected void onPreExecute() {
            Toast.makeText(Final.this, "Subiendo datos...", Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(Void... argumentos) {
            try {
                // Crear la URL de conexión al API
                URL url = new URL("http://" + ip + "/ApiRest/" + prim);
                // Crear la conexión HTTP
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                // Establecer método de comunicación
                myConnection.setRequestMethod("POST");
                // Conexión exitosa
                HashMap<String, String> postDataParams = new HashMap<>();
                postDataParams.put(campo, this.nombre);
                postDataParams.put(campo2, this.puntosusu);
                myConnection.setDoInput(true);
                myConnection.setDoOutput(true);
                OutputStream os = myConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();
                myConnection.getResponseCode();
                if (myConnection.getResponseCode() == 200) {
                    // Success
                    myConnection.disconnect();
                } else {
                    // Error handling code goes here
                    Log.println(Log.ASSERT, "Error2", "Error");
                }
            } catch (Exception e) {
                Log.println(Log.ASSERT, "Excepción1", e.getMessage());
            }
            return (null);
        }

        protected void onPostExecute(String mensaje) {
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    result.append("&");
                }
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        }
    }
}