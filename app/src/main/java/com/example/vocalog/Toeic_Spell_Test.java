package com.example.vocalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Toeic_Spell_Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_spell_test);

        Button button = findViewById(R.id.button_back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "돌아가기",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

        EditText editText = (EditText)findViewById(R.id.sp_word);
        editText.setText(editText.getText());

        Button button_next = findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "다음",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}