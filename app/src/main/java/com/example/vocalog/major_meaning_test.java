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

import java.util.ArrayList;

public class major_meaning_test extends AppCompatActivity {

    private TextView wordTextView;
    private Button nextButton;
    private String[] spell = {"전공", "물", "불", "개", "고양이"};
    private String[] words = {"major","water","fire","dog","cat"};
    private int currentIndex = 0;

    private ArrayList<String> wrongWords = new ArrayList<>();
    private ArrayList<String> wrongSpell = new ArrayList<>();
    private boolean isWrongWordMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_meaning_test);

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
        wordTextView = findViewById(R.id.word);
        wordTextView.setText(words[currentIndex]);

        button_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString().trim();

                if (inputText.equals(isWrongWordMode ? wrongSpell.get(currentIndex) : spell[currentIndex])) {
                    showAlertDialog("정답입니다!", "다음 단어로 넘어갑니다.");

                    if(isWrongWordMode) {
                        wrongWords.remove(currentIndex);
                        wrongSpell.remove(currentIndex);
                        currentIndex--; // 틀린 단어의 경우, 인덱스를 감소시켜 다음 단어가 올바르게 표시되도록 설정
                    }
                } else {
                    String message = isWrongWordMode ? "틀린 단어입니다! 다시 시도해주세요." : "오답입니다! 틀린 단어로 기록됩니다.";
                    showAlertDialog("오답입니다.", message);

                    if(!isWrongWordMode) {
                        wrongWords.add(words[currentIndex]);
                        wrongSpell.add(spell[currentIndex]);
                    }
                }

                currentIndex++;

                if (currentIndex >= (isWrongWordMode ? wrongWords.size() : words.length)) {
                    if (wrongWords.isEmpty()) {
                        showAlertDialogWithFinish("테스트가 완료되었습니다!", "테스트를 종료합니다!");
                        return;
                    } else {
                        isWrongWordMode = true;
                        currentIndex = 0;
                    }
                }

                wordTextView.setText(isWrongWordMode ? wrongWords.get(currentIndex) : words[currentIndex]);
                editText.getText().clear();
            }
        });
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