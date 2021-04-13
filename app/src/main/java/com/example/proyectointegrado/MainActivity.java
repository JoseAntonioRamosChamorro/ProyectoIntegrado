package com.example.proyectointegrado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edNombre.getText().toString();
                Intent nombre = new Intent(MainActivity.this, Quest1.class);
                nombre.putExtra("nombreUsu", name);
                startActivity(nombre);
            }
        });


        btnRanking = findViewById(R.id.btnRanking);
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rank = new Intent(MainActivity.this, Ranking.class);
                startActivity(rank);
            }
        });

    }
}