package com.example.vocalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.widget.TextView;

public class profile extends AppCompatActivity {

    private static final int SELECT_PROFILE_PIC = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

// 버튼을 찾아옵니다.
        Button backButton = findViewById(R.id.backButton);
        Button photoButton = findViewById(R.id.photo);




// 버튼의 클릭 이벤트 리스너를 설정합니다.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// 액티비티를 종료하고 이전 화면으로 돌아갑니다.
                finish();
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        checkGalleryAccessPermission();
    }
    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, SELECT_PROFILE_PIC);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PROFILE_PIC) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, data);
                finish();
            }
        }
    }

    private void checkGalleryAccessPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }


}