package com.example.vocalog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class Memorizing extends Fragment {

    private ViewGroup buttonContainer;
    private int buttonIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memorizing, container, false);
        Button btn_createBoard = view.findViewById(R.id.btn_createBoard);
        buttonContainer = view.findViewById(R.id.boardList);
        btn_createBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        return view;
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("보드 생성");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        final EditText boardName = dialogView.findViewById(R.id.board_name);
        builder.setView(dialogView);

        builder.setPositiveButton("생성", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String buttonText = boardName.getText().toString();
                addButton(buttonText);
            }
        });

        builder.setNegativeButton("취소", null);

        AlertDialog alertDialog = builder.setCancelable(false).create();
        alertDialog.show();
    }
    private void addButton(String buttonText) {
        Button newButton = new Button(getActivity());
        newButton.setText(buttonText);
        newButton.setBackgroundResource(R.drawable.board_button);
        newButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        newButton.setTextColor(Color.parseColor("#566065"));
        newButton.setClickable(true);
        newButton.setId(buttonIndex);
        buttonIndex++;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.margin_left), getResources().getDimensionPixelSize(R.dimen.margin_top), 0 , 0);

        buttonContainer.addView(newButton, layoutParams);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonId = view.getId();
                openOtherActivity(buttonId);
            }
        });
    }

    private void openOtherActivity(int buttonId) {
        String boardTitle = ((Button)buttonContainer.findViewById(buttonId)).getText().toString();

        Intent intent = new Intent(getActivity(), board_main.class);
        intent.putExtra("boardId", buttonId);
        intent.putExtra("boardTitle", boardTitle);
        startActivity(intent);
    }
}