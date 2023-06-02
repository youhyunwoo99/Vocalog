package com.example.vocalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Reminding_wordlist extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminding_wordlist);

        Intent intent = getIntent();
        // Retrieve the boardTitle passed from the previous activity
        String boardTitle = intent.getStringExtra("boardTitle");

        TextView pageId = findViewById(R.id.page_id);
        pageId.setText(boardTitle); // Set the boardTitle as the text of the TextView

        FloatingActionButton btn_dictionary = findViewById(R.id.dictionary);

        btn_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Reminding_wordlist.this, web_view.class);
                startActivity(intent1);
            }
        });

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티를 종료하고 이전 액티비티로 돌아갑니다.
            }
        });
    }

}

