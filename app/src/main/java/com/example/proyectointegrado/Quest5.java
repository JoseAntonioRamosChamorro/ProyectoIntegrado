package com.example.proyectointegrado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Quest5 extends AppCompatActivity {
    private int ids_answers[] = {
            R.id.answer5_1, R.id.answer5_2, R.id.answer5_3, R.id.answer5_4
    };
    String name;
    int nota;


    //aceso a la BD

    JSONArray result;
    JSONObject jsonobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest5);

        //intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("nombreUsu");
        nota = intent.getIntExtra("notaUsu", 0);

        TextView text_question = (TextView) findViewById(R.id.text_question5);
        text_question.setText(R.string.Listapreguntas5);

        String[] answers = getResources().getStringArray(R.array.preguntas5);

        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            rb.setText(answers[i]);
        }

        final int correct_answer = getResources().getInteger(R.integer.respuestacorrecta5);
        final RadioGroup group = (RadioGroup) findViewById(R.id.answer_group5);

        Button btn_check = (Button) findViewById(R.id.btn_check5);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = group.getCheckedRadioButtonId();
                int answer = -1;
                for (int i = 0; i < ids_answers.length; i++) {
                    if (ids_answers[i] == id) {
                        answer = i;
                    }
                }
                //comprobar respuesta correcta
                if (answer == correct_answer) {
                    nota = nota + 1;
                }

                Intent intent1 = new Intent(Quest5.this, Final.class);
                intent1.putExtra("nombreUsu", name);
                intent1.putExtra("notaUsu", nota);

                startActivity(intent1);
            }

        });
    }
}