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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        String boardTitle = intent.getStringExtra("boardTitle");
        title.setText(boardTitle);

        FloatingActionButton btn_dictionary = findViewById(R.id.dictionary);

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        save_word(newWord, newMean, boardTitle);
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

        Query wordQuery = databaseReference.child("voca").orderByChild("boardTitle").equalTo(boardTitle);
        wordQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinearLayout wordListLayout = findViewById(R.id.word_list);
                wordListLayout.removeAllViews();

                for (DataSnapshot wordSnapshot : dataSnapshot.getChildren()) {
                    save_word word = wordSnapshot.getValue(save_word.class);
                    String wordString = word.getWord();
                    String meanString = word.getMean();

                    TextView wordMeanTextView = new TextView(board_main.this);
                    wordMeanTextView.setText(wordString + ": " + meanString);
                    wordMeanTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    wordMeanTextView.setTextColor(Color.parseColor("#566065"));

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    int marginTopInPixels = 175;
                    int marginLeftInPixels = 40;
                    layoutParams.setMargins(marginLeftInPixels, marginTopInPixels, 0, 0);
                    wordMeanTextView.setLayoutParams(layoutParams);

                    wordListLayout.addView(wordMeanTextView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리 로직을 추가하십시오.
            }
        });
    }

    public void save_word(String word, String mean, String boardTitle) {
        save_word saveWord = new save_word(word, mean, "", boardTitle);
        databaseReference.child("voca").push().setValue(saveWord);
    }
}