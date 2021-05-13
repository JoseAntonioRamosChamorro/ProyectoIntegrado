package com.example.proyectointegrado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Quest1 extends AppCompatActivity {
    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };
    String name;
    int nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest1);
        //intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("nombreUsu");


        TextView text_question = (TextView) findViewById(R.id.text_question);
        text_question.setText(R.string.Listapreguntas1);

        String[] answers = getResources().getStringArray(R.array.preguntas1);

        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            rb.setText(answers[i]);
        }

        final int correct_answer = getResources().getInteger(R.integer.respuestacorrecta1);
        final RadioGroup group = (RadioGroup) findViewById(R.id.answer_group);

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
                Intent intent1 = new Intent(Quest1.this, Quest2.class);
                intent1.putExtra("nombreUsu", name);
                intent1.putExtra("notaUsu", nota);

                startActivity(intent1);

            }
        });
    }
}
