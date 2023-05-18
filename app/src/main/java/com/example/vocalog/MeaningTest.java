package com.example.vocalog;

import static com.example.vocalog.R.id.button3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MeaningTest extends AppCompatActivity {
    TextView textView;
    String[] items = {"토익영단어", "전공영단어"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning_test);
        Button button1 = findViewById(R.id.button2);
        Button button2 = findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "돌아가기",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //선택한 항목에 따라 다른 창으로 이동하는 코드
                String selectedItem = textView.getText().toString();
                if(selectedItem.equals("토익영단어")) {
                    //토익영단어를 선택한 경우
                    Intent intent = new Intent(getApplicationContext(), toeic_meaning_test.class);
                    startActivity(intent);
                }
                else if (selectedItem.equals("전공영단어")) {
                    //전공영단어를 선택한 경우
                    Intent intent = new Intent(getApplicationContext(), major_meaning_test.class);
                    startActivity(intent);
                }
            }
        });
        textView = (TextView) findViewById(R.id.textView2);
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                textView.setText(items[position]);
            }
            @Override
            public void onNothingSelected(AdapterView adapterView){
                textView.setText("");
            }
        });
    }
}