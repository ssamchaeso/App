package com.example.myapplication;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button[] buttons = new Button[9];
    boolean playerX = true; // true: X, false: O
    int[] board = new int[9]; // 0: empty, 1: X, 2: O
    private Dialog resultDialog;
    boolean isPaused = false;

    int player1Icon;
    int player2Icon;
    String player1Name,player2Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CustomizeActivity에서 리소스값 받아오기
        player1Name = getIntent().getStringExtra("player1Name");
        player2Name = getIntent().getStringExtra("player2Name");
        player1Icon = getIntent().getIntExtra("player1Icon", R.drawable.icon1);
        player2Icon = getIntent().getIntExtra("player2Icon", R.drawable.icon2);

        for (int i = 0; i < 9; i++) {
            String buttonID = "button" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            int finalI = i;
            buttons[i].setOnClickListener(v -> handleMove(finalI));
        }

        ImageButton pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(v -> showPauseDialog());
    }

    private void handleMove(int index) {
        if (isPaused || board[index] != 0) return;

        MediaPlayer.create(this, R.raw.click).start();

        board[index] = playerX ? 1 : 2;


        buttons[index].setBackgroundResource(playerX ? player1Icon : player2Icon);
        buttons[index].setText("");

        if (checkWin()) {
            MediaPlayer.create(this, R.raw.win).start();
            showResultDialog(playerX ? 1 : 2);
        } else if (isDraw()) {
            MediaPlayer.create(this, R.raw.draw).start();
            showResultDialog(0);
        } else {
            playerX = !playerX;
        }
    }

    private boolean checkWin() {
        int[][] winPositions = {
                {0,1,2}, {3,4,5}, {6,7,8},
                {0,3,6}, {1,4,7}, {2,5,8},
                {0,4,8}, {2,4,6}
        };
        for (int[] pos : winPositions) {
            if (board[pos[0]] != 0 &&
                    board[pos[0]] == board[pos[1]] &&
                    board[pos[1]] == board[pos[2]]) {
                return true;
            }
        }
        return false;
    }

    private boolean isDraw() {
        for (int value : board) {
            if (value == 0) return false;
        }
        return true;
    }

    private void showResultDialog(int winnerPlayer) {
        resultDialog = new Dialog(this);
        resultDialog.setContentView(R.layout.game_result);
        resultDialog.setCancelable(false);


        TextView textResult = resultDialog.findViewById(R.id.text_result);
        ImageView imageResult = resultDialog.findViewById(R.id.image_result);
        Button btnExit = resultDialog.findViewById(R.id.btn_exit);

        boolean isCustomNames =
                        player1Name != null && !player1Name.trim().isEmpty() &&
                        player2Name != null && !player2Name.trim().isEmpty();

        if (player1Name == null || player1Name.trim().isEmpty()) {
            player1Name = "플레이어 1";
        }
        if (player2Name == null || player2Name.trim().isEmpty()) {
            player2Name = "플레이어 2";
        }
        if (player1Icon == 0) {
            player1Icon = R.drawable.icon1;
        }
        if (player2Icon == 0) {
            player2Icon = R.drawable.icon2;
        }
        if (winnerPlayer == 1) {
            textResult.setText(player1Name + " 승리!");
            imageResult.setImageResource(player1Icon);
        } else if (winnerPlayer == 2) {
            textResult.setText(player2Name + " 승리!");
            imageResult.setImageResource(player2Icon);
        } else {
            textResult.setText("무승부");
            imageResult.setImageResource(R.drawable.draw_icon);
        }


        if (isCustomNames) {

            DBHelper dbHelper = new DBHelper(this);

            if (winnerPlayer == 1) {
                textResult.setText(player1Name + " 승리!");

                dbHelper.insertMatch(player1Name, "Win", player2Name, "Lose");

            } else if (winnerPlayer == 2) {
                textResult.setText(player2Name + " 승리!");

                dbHelper.insertMatch(player1Name, "Lose", player2Name, "Win");

            } else {
                textResult.setText("무승부");

                dbHelper.insertMatch(player1Name, "Draw", player2Name, "Draw");
            }
        }

        btnExit.setOnClickListener(v -> {
            resultDialog.dismiss();
            finish();
        });

        Window window = resultDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        resultDialog.show();
    }



    private void showPauseDialog() {
        isPaused = true;

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.game_pause);
        dialog.setCancelable(false);

        Button btnResume = dialog.findViewById(R.id.btn_resume);
        Button btnExit = dialog.findViewById(R.id.btn_exit2);
        Button btnReturn = dialog.findViewById(R.id.btn_return);

        btnResume.setOnClickListener(v -> {
            isPaused = false;
            dialog.dismiss();
        });

        btnExit.setOnClickListener(v -> {
            dialog.dismiss(); // 먼저 dialog 닫기
            finish();         // 그 다음 activity 닫기
        });
        btnReturn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    @Override
    protected void onDestroy() {
        if (resultDialog != null && resultDialog.isShowing()) {
            resultDialog.dismiss();
        }
        super.onDestroy();
    }
}
