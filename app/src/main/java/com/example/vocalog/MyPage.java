package com.example.vocalog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyPage extends Fragment {

    private final int REQUEST_PROFILE_PIC = 1;
    ImageView myPageProfileImage;
    TextView profileNameTextView;

    private void startProfileActivity() {
        Intent intent1 = new Intent(getActivity(), profile.class);
        startActivityForResult(intent1, REQUEST_PROFILE_PIC);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);

        // CalendarView를 찾아옵니다.
        CalendarView calendarView = rootView.findViewById(R.id.calendarView);

        // CalendarView의 이벤트 리스너를 설정합니다.
        myPageProfileImage = rootView.findViewById(R.id.profileImageView);
        loadProfileImage();

        profileNameTextView = rootView.findViewById(R.id.profileNameTextView);
        loadProfileName();



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 선택한 날짜에 대한 동작을 수행합니다.
                // 예를 들어, 선택한 날짜의 정보를 출력하거나 처리할 수 있습니다.
            }
        });


        // 버튼을 찾아옵니다.
        Button button = rootView.findViewById(R.id.button);
        Button profile_button = rootView.findViewById(R.id.profileButton);

        // 버튼의 클릭 이벤트 리스너를 설정합니다.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 페이지로 이동하는 코드를 작성합니다.
                // 예를 들어, 다른 액티비티로 이동하는 코드를 작성할 수 있습니다.
                Intent intent = new Intent(getActivity(), My_V_Log.class);
                startActivity(intent);
            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 페이지로 이동하는 코드를 작성합니다.
                // 예를 들어, 다른 액티비티로 이동하는 코드를 작성할 수 있습니다.

                startProfileActivity();
            }
        });

        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PROFILE_PIC) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    myPageProfileImage.setImageBitmap(bitmap);
                    saveProfileImage(bitmap); // 이미지를 저장합니다.
                    String name = data.getStringExtra("name");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // 현재 프로필 액티비티에서 받은 이름값이 저장되어 있는지 확인
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");

        // SharedPreferences에서 이름값이 있으면 TextView 업데이트
        if (!name.isEmpty()) {
            profileNameTextView.setText(name);
        }
    }
    private void loadProfileImage() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPageProfile", MODE_PRIVATE);
        String imageString = sharedPreferences.getString("profile_image", "");

        if (!imageString.equals("")) {
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            myPageProfileImage.setImageBitmap(bitmap);
        }
    }

    private void loadProfileName() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfilePreferences", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "IU");
        profileNameTextView.setText(name);
    }

    // 프로필 이미지를 저장하는 함수입니다.
    private void saveProfileImage(Bitmap bitmap) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPageProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        editor.putString("profile_image", imageString);
        editor.apply();
    }

}