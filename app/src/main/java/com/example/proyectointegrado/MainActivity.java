package com.example.proyectointegrado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edNombre;
    Button btnIniciar, btnRanking;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNombre = findViewById(R.id.edTNombre);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnRanking = findViewById(R.id.btnRanking);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edNombre.getText().toString();
                if (name.equals("")){
                    Toast.makeText(getApplicationContext(), "Escriba su nombre", Toast.LENGTH_SHORT).show();

                }else {
                    Intent nombre = new Intent(MainActivity.this, Quest1.class);
                    nombre.putExtra("nombreUsu", name);
                    startActivity(nombre);
                }


            }
        });
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rank = new Intent(MainActivity.this, Ranking.class);
                startActivity(rank);
            }
        });

    }
}