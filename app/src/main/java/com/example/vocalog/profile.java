package com.example.vocalog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.widget.LinearLayout;
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

        Button profileButton = findViewById(R.id.profile);

        loadProfileData();
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileButtonClick();
            }
        });


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

    private void onProfileButtonClick() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText studentIdInput = new EditText(this);
        studentIdInput.setHint("학번");
        linearLayout.addView(studentIdInput);

        final EditText nameInput = new EditText(this);
        nameInput.setHint("이름");
        linearLayout.addView(nameInput);

        final EditText majorInput = new EditText(this);
        majorInput.setHint("학과");
        linearLayout.addView(majorInput);

        final EditText emailInput = new EditText(this);
        emailInput.setHint("이메일");
        linearLayout.addView(emailInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("프로필 정보 입력")
                .setView(linearLayout)
                .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String studentId = studentIdInput.getText().toString();
                        String name = nameInput.getText().toString();
                        String major = majorInput.getText().toString();
                        String email = emailInput.getText().toString();

                        updateTextViews(studentId, name, major, email);

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    private void updateTextViews(String studentId, String name, String major, String email) {
        TextView studentIdTextView = findViewById(R.id.studentID);
        studentIdTextView.setText("학번: " + studentId);

        TextView nameTextView = findViewById(R.id.name);
        nameTextView.setText("이름: " + name);

        TextView majorTextView = findViewById(R.id.Major);
        majorTextView.setText("학과: " + major);

        TextView emailTextView = findViewById(R.id.email);
        emailTextView.setText("이메일: " + email);

        SharedPreferences sharedPreferences = getSharedPreferences("ProfilePreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("studentId", studentId);
        editor.putString("name", name);
        editor.putString("major", major);
        editor.putString("email", email);
        editor.apply();
    }

    private void loadProfileData() {
        SharedPreferences sharedPreferences = getSharedPreferences("ProfilePreferences", Context.MODE_PRIVATE);

        String studentId = sharedPreferences.getString("studentId", "");
        String name = sharedPreferences.getString("name", "");
        String major = sharedPreferences.getString("major", "");
        String email = sharedPreferences.getString("email", "");

        updateTextViews(studentId, name, major, email);
    }

}