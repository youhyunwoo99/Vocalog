package com.example.vocalog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    }

}

