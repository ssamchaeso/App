package com.example.myapplication;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
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

    int player1Icon;  // 전역변수로 선언
    int player2Icon;  // 전역변수로 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CustomizeActivity에서 리소스값 받아오기
        String player1Name = getIntent().getStringExtra("player1Name");
        String player2Name = getIntent().getStringExtra("player2Name");
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

        // 아이콘으로 표시 (텍스트 지우기)
        buttons[index].setBackgroundResource(playerX ? player1Icon : player2Icon);
        buttons[index].setText("");

        if (checkWin()) {
            MediaPlayer.create(this, R.raw.win).start();
            showResultDialog(playerX ? 1 : 2);  // playerX면 Player1 승리
        } else if (isDraw()) {
            MediaPlayer.create(this, R.raw.draw).start();
            showResultDialog(0);  // 0은 무승부
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

    private void resetGame() {
        Arrays.fill(board, 0);
        for (Button b : buttons) {
            b.setText("");
            b.setBackgroundResource(0);  // 배경 제거(아이콘 초기화)
        }
        playerX = true;
    }

    private void showResultDialog(int winnerPlayer) {
        if (isFinishing()) return;
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.game_result);
        dialog.setCancelable(false);


        TextView textResult =dialog.findViewById(R.id.text_result);
        ImageView imageResult = dialog.findViewById(R.id.image_result);
        Button btnRestart = dialog.findViewById(R.id.btn_restart);
        Button btnExit = dialog.findViewById(R.id.btn_exit);



        String player1Name = getIntent().getStringExtra("player1Name");
        String player2Name = getIntent().getStringExtra("player2Name");

        DBHelper dbHelper = new DBHelper(this);
        if (winnerPlayer == 1) {
            dbHelper.insertMatch(player1Name, "Win", player2Name, "Lose");
            imageResult.setImageResource(player1Icon);
        } else if (winnerPlayer == 2) {
            dbHelper.insertMatch(player1Name, "Lose", player2Name, "Win");
            imageResult.setImageResource(player2Icon);
        } else {
            dbHelper.insertMatch(player1Name, "Draw", player2Name, "Draw");
            imageResult.setImageResource(R.drawable.draw_icon);
            textResult.setVisibility(TextView.GONE);
        }

        btnRestart.setOnClickListener(v -> {
            resetGame();
            dialog.dismiss();
        });

        btnExit.setOnClickListener(v -> {
                resultDialog.dismiss();
                finish();
                });
        resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resultDialog.show();
    }
    private void saveMatchResult(String p1Name, String p1Result, String p2Name, String p2Result) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("player1", p1Name);
        values.put("player1Result", p1Result);
        values.put("player2", p2Name);
        values.put("player2Result", p2Result);

        db.insert("match_records", null, values);
        db.close();
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
            finish();         // 그 다음 activity 종료
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
