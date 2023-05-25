package com.example.vocalog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class board_main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_main);

        RelativeLayout wordlist = findViewById(R.id.word_board);
        TextView title = findViewById(R.id.board_title);

        Intent intent = getIntent();
        int boardId = intent.getIntExtra("buttonId", -1);
        String boardTitle = intent.getStringExtra("boardTitle");
        title.setText(boardTitle);
        wordlist.setId(boardId);

        FloatingActionButton btn_dictionary = findViewById(R.id.dictionary);

        btn_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}