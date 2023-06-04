package com.example.vocalog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Board_Spell_Tests extends AppCompatActivity {

    private TextView wordTextView;
    private Button nextButton;

    private List<save_word> wordList;
    private int currentIndex;

    private ArrayList<String> wrongWords;
    private ArrayList<String> wrongSpell;
    private boolean isWrongWordMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_spell_tests);

        Button button = findViewById(R.id.button_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "돌아가기",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

        EditText editText = findViewById(R.id.sp_word);
        editText.setText(editText.getText());

        Button button_next = findViewById(R.id.button_next);
        wordTextView = findViewById(R.id.word);

        wordList = new ArrayList<>();
        currentIndex = 0;
        wrongWords = new ArrayList<>();
        wrongSpell = new ArrayList<>();
        isWrongWordMode = false;

        // 파이어베이스에서 선택된 보드에 해당하는 단어와 스펠링을 가져옵니다.
        String boardTitle = getIntent().getStringExtra("boardTitle");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("voca");
        Query wordQuery = databaseReference.orderByChild("boardTitle").equalTo(boardTitle);
        wordQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wordList.clear();
                for (DataSnapshot wordSnapshot : dataSnapshot.getChildren()) {
                    save_word word = wordSnapshot.getValue(save_word.class);
                    wordList.add(word);
                }
                if (wordList.size() > 0) {
                    displayNextWord();
                } else {
                    // 보드에 등록된 단어가 없는 경우 처리할 내용을 추가하세요.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리 로직을 추가하십시오.
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString().trim();

                save_word word = wordList.get(currentIndex);
                if (inputText.equalsIgnoreCase(isWrongWordMode ? wrongWords.get(currentIndex) : word.getWord())) {
                    showAlertDialog("정답입니다!", "다음 단어로 넘어갑니다.");

                    if (isWrongWordMode) {
                        wrongWords.remove(currentIndex);
                        currentIndex--; // 틀린 단어의 경우, 인덱스를 감소시켜 다음 단어가 올바르게 표시되도록 설정
                    }
                } else {
                    String message = isWrongWordMode ? "틀린 단어입니다! 다시 시도해주세요." : "오답입니다! 틀린 단어로 기록됩니다.";
                    showAlertDialog("오답입니다.", message);

                    if (!isWrongWordMode) {
                        wrongWords.add(word.getWord());
                    }
                }

                currentIndex++;

                if (currentIndex >= (isWrongWordMode ? wrongWords.size() : wordList.size())) {
                    if (wrongWords.isEmpty()) {
                        showAlertDialogWithFinish("테스트가 완료되었습니다!", "테스트를 종료합니다!");
                        return;
                    } else {
                        isWrongWordMode = true;
                        currentIndex = 0;
                    }
                }

                displayNextWord();
                editText.getText().clear();
            }
        });
    }

    private void displayNextWord() {
        if (isWrongWordMode) {
            wordTextView.setText(wrongWords.get(currentIndex));
        } else {
            save_word word = wordList.get(currentIndex);
            wordTextView.setText(word.getMean());
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAlertDialogWithFinish(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}