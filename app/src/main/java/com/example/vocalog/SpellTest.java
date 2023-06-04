package com.example.vocalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class SpellTest extends AppCompatActivity {
    TextView textView;
    Spinner spinner;
    Button button1;
    Button button2;
    List<String> boardTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_test);

        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);

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
                String selectedItem = textView.getText().toString();
                if (boardTitles.contains(selectedItem)) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("boards");
                    Query query = databaseReference.orderByChild("name").equalTo(selectedItem);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                String boardTitle = firstChild.child("name").getValue(String.class);
                                Intent intent = new Intent(getApplicationContext(), Board_Spell_Tests.class);
                                intent.putExtra("boardTitle", boardTitle);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 에러 처리 로직
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "보드를 선택해주세요.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // 파이어베이스에서 보드 제목들을 가져와서 스피너에 표시합니다.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("boards");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boardTitles.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String boardTitle = snapshot.child("name").getValue(String.class);
                    boardTitles.add(boardTitle);
                }
                setSpinnerAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리 로직을 추가하십시오.
            }
        });
    }

    private void setSpinnerAdapter() {
        // Null 값을 처리하기 위해 임시로 사용할 대체 값
        final String NULL_TITLE_VALUE = "1";

        // Null 값을 처리하는 로직 추가
        for (int i = 0; i < boardTitles.size(); i++) {
            if (boardTitles.get(i) == null) {
                boardTitles.set(i, NULL_TITLE_VALUE);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, boardTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                String selectedTitle = boardTitles.get(position);
                // 대체 값인 경우에는 textView에 빈 문자열로 설정
                textView.setText(selectedTitle.equals(NULL_TITLE_VALUE) ? "" : selectedTitle);
            }

            @Override
            public void onNothingSelected(AdapterView adapterView){
                textView.setText("");
            }
        });
    }
}