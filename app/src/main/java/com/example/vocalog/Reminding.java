package com.example.vocalog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Reminding extends Fragment {

    private ViewGroup buttonContainer;
    private int buttonIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminding, container, false);
        Button btn_createBoard = view.findViewById(R.id.btn_createBoard);
        buttonContainer = view.findViewById(R.id.bord);

        btn_createBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        fetchBoardData(); // Fetch and display existing boards from Firebase

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
                addButton(boardTitle);
                saveBoardToFirebase(boardTitle);
            }
        });

        builder.setNegativeButton("취소", null);

        AlertDialog alertDialog = builder.setCancelable(false).create();
        alertDialog.show();
    }

    private void addButton(String boardTitle) {
        Button newButton = new Button(getActivity());
        newButton.setText(boardTitle);
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
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.margin_top), 0 , 0);

        buttonContainer.addView(newButton, layoutParams);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonId = view.getId();
                openOtherActivity(buttonId);
            }
        });
    }

    private void saveBoardToFirebase(String boardName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference boardRef = database.getReference("boards");

        // Create a new Board object
        Board newBoard = new Board(boardName);

        // Save the board to Firebase
        String boardId = boardRef.push().getKey();
        boardRef.child(boardId).setValue(newBoard);
    }

    private void openOtherActivity(int buttonId) {
        String boardTitle = ((Button)buttonContainer.findViewById(buttonId)).getText().toString();

        Intent intent = new Intent(getActivity(), Reminding_wordlist.class);
        intent.putExtra("boardId", buttonId);
        intent.putExtra("boardTitle", boardTitle);
        startActivity(intent);
    }

    private void fetchBoardData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference boardRef = database.getReference("boards");
        boardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Remove previously added board buttons
                int childCount = buttonContainer.getChildCount();
                for (int i = childCount - 1; i >= 0; i--) {
                    View childView = buttonContainer.getChildAt(i);
                    if (childView instanceof Button) {
                        Button button = (Button) childView;
                        if (!button.getText().equals("+보드 추가하기")) {
                            buttonContainer.removeViewAt(i);
                        }
                    }
                }

                // Display boards retrieved from Firebase as buttons
                for (DataSnapshot boardSnapshot : snapshot.getChildren()) {
                    Board board = boardSnapshot.getValue(Board.class);
                    addButton(board.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }
}