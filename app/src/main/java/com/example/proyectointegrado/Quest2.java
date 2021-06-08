package com.example.proyectointegrado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Quest2 extends AppCompatActivity {
    private int ids_answers[] = {
            R.id.answer2_1, R.id.answer2_2, R.id.answer2_3, R.id.answer2_4
    };
    String name;
    int nota;
    Button volver;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest2);

        //intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("nombreUsu");
        nota = intent.getIntExtra("notaUsu", 0);

        TextView text_question = (TextView) findViewById(R.id.text_question2);
        text_question.setText(R.string.Listapreguntas2);

        String[] answers = getResources().getStringArray(R.array.preguntas2);

        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            rb.setText(answers[i]);
        }

        final int correct_answer = getResources().getInteger(R.integer.respuestacorrecta2);
        final RadioGroup group = (RadioGroup) findViewById(R.id.answer_group2);

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
                Intent intent1 = new Intent(Quest2.this, Quest3.class);
                intent1.putExtra("nombreUsu", name);
                intent1.putExtra("notaUsu", nota);
                startActivity(intent1);
            }
        });
        volver = findViewById(R.id.volver2);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Quest2.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
