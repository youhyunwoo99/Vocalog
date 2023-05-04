package com.example.vocalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationBarView;
public class MainActivity extends AppCompatActivity {

    Memorizing memorizingFragment;
    Testing testingFragment;
    Reminding remindingFragment;
    MyPage myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memorizingFragment = new Memorizing();
        testingFragment = new Testing();
        remindingFragment = new Reminding();
        myPageFragment = new MyPage();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, myPageFragment).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, myPageFragment).commit();
                        return true;
                    case R.id.memorizing:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, memorizingFragment).commit();
                        return true;
                    case R.id.testing:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, testingFragment).commit();
                        return true;
                    case R.id.reminding:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, remindingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}