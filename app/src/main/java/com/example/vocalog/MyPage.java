package com.example.vocalog;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

import java.io.IOException;

public class MyPage extends Fragment {

    private final int REQUEST_PROFILE_PIC = 1;

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
                // 이미지를 사용하여 필요한 작업을 수행합니다.
                // 예를 들어, ImageView에 이미지를 설정할 수 있습니다.
                ImageView myPageProfileImage = getView().findViewById(R.id.profileImageView);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    myPageProfileImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}