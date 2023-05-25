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
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;


public class Reminding extends Fragment {

    private ViewGroup buttonContainer;
    private int buttonIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminding, container, false);
        Button btn_createBoard = view.findViewById(R.id.plus_vector);
        buttonContainer = view.findViewById(R.id.bord);
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
                String boardTitle = boardName.getText().toString();
                addCustomView(boardTitle);
            }
        });

        builder.setNegativeButton("취소", null);

        AlertDialog alertDialog = builder.setCancelable(false).create();
        alertDialog.show();
    }
    private void addCustomView(String boardTitle) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout customLayout = (RelativeLayout) inflater.inflate(R.layout.bord_view, null);

        TextView boardName = (TextView) customLayout.findViewById(R.id.bord_name);
        TextView boardCount = (TextView) customLayout.findViewById(R.id.bord_count);
        Button deleteButton = (Button) customLayout.findViewById(R.id.delete);

        boardName.setText(boardTitle);
        boardCount.setText(String.format("%d/50", buttonIndex + 1));
        customLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOtherActivity(buttonIndex, boardTitle); // Passing boardTitle to openOtherActivity
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                buttonContainer.removeView(customLayout);
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.margin_top), 0, 0);

        buttonContainer.addView(customLayout,layoutParams);

        buttonIndex++;
    }
    private void openOtherActivity(int buttonId, String boardTitle) {
        Intent intent = new Intent(getActivity(), Reminding_wordlist.class);
        intent.putExtra("buttonId", buttonId);
        intent.putExtra("boardTitle", boardTitle); // Pass the boardTitle as an extra
        startActivity(intent);
    }

}