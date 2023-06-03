package com.example.vocalog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // 버튼을 찾아옵니다.
        Button backButton = findViewById(R.id.backButton);

        // 버튼의 클릭 이벤트 리스너를 설정합니다.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 액티비티를 종료하고 이전 화면으로 돌아갑니다.
                finish();
            }
        });


    }
}