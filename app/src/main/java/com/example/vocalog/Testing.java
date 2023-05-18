package com.example.vocalog;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Testing extends Fragment {
    private Button Spell_Test;
    private Button Meaning_Test;

    private Button more;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testing, container, false);
        Spell_Test = view.findViewById(R.id.Spell_Test);
        Meaning_Test = view.findViewById(R.id.Meaning_Test);
        more = view.findViewById(R.id.more);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spell_Test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpellTest.class);
                startActivity(intent);
            }
        });

        Meaning_Test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeaningTest.class);
                startActivity(intent);
            }
        });

        more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "next time", Toast.LENGTH_LONG).show();
            }
        });
    }
}