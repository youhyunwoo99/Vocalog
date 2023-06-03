package com.example.vocalog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class board_main extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

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

        Button backButton = findViewById(R.id.back);

        // 버튼의 클릭 이벤트 리스너를 설정합니다.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 액티비티를 종료하고 이전 화면으로 돌아갑니다.
                finish();
            }
        });

        btn_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(board_main.this, web_view.class);
                startActivity(intent1);
            }
        });

        FloatingActionButton btn_add_word = findViewById(R.id.add_word);
        btn_add_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(board_main.this);
                builder.setTitle("단어 생성");

                View dialogView = getLayoutInflater().inflate(R.layout.word_dialog_layout, null);
                final EditText word = dialogView.findViewById(R.id.word);
                final EditText mean = dialogView.findViewById(R.id.mean);
                builder.setView(dialogView);

                builder.setPositiveButton("생성", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newWord = word.getText().toString();
                        String newMean = mean.getText().toString();
                        save_word(newWord, newMean);
                        LinearLayout wordlist = findViewById(R.id.word_list);
                        TextView word_mean = new TextView(board_main.this);
                        word_mean.setText(newWord + ": " + newMean);
                        word_mean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        word_mean.setTextColor(Color.parseColor("#566065"));

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        int marginTopInPixels = 175;
                        int marginLeftInPixels = 40;
                        layoutParams.setMargins(marginLeftInPixels, marginTopInPixels, 0, 0);
                        word_mean.setLayoutParams(layoutParams);

                        wordlist.addView(word_mean);
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void save_word(String word, String mean) {
        save_word saveWord = new save_word(word, mean);
        databaseReference.child("voca").child(word).setValue(saveWord);
    }
}