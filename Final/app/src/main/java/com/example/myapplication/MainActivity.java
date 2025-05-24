package com.example.myapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button[] buttons = new Button[9];
    boolean playerX = true; // true: X, false: O
    int[] board = new int[9]; // 0: empty, 1: X, 2: O

    boolean isPaused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        // 일시정지 상태이거나 이미 눌린 칸이면 무시
        if (isPaused || board[index] != 0) return;

        // 클릭 사운드 재생
        MediaPlayer.create(this, R.raw.click).start();

        // 보드 상태 갱신
        board[index] = playerX ? 1 : 2;

        // 버튼에 X 또는 O 표시
        buttons[index].setText(playerX ? "X" : "O");

        // 색상 설정: X - 코랄핑크, O - 민트색
        buttons[index].setTextColor(Color.parseColor(playerX ? "#FF8A80" : "#4DD0E1"));

        // 승리 판단
        if (checkWin()) {
            MediaPlayer.create(this, R.raw.win).start();  // 승리 사운드
            showResultDialog((playerX ? "X" : "O") + " Wins!");
        }
        // 무승부 판단
        else if (isDraw()) {
            MediaPlayer.create(this, R.raw.draw).start(); // 무승부 사운드
            showResultDialog("Draw!");
        }
        // 다음 턴으로 전환
        else {
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
        }
        playerX = true;
    }

    private void showResultDialog(String message) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.game_result);
        dialog.setCancelable(false);

        TextView textResult = dialog.findViewById(R.id.text_result);
        Button btnRestart = dialog.findViewById(R.id.btn_restart);
        Button btnExit = dialog.findViewById(R.id.btn_exit);

        textResult.setText(message);

        btnRestart.setOnClickListener(v -> {
            resetGame();
            dialog.dismiss();
        });

        btnExit.setOnClickListener(v -> finish());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void showPauseDialog() {
        isPaused = true;

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.game_pause); // XML 파일 존재해야 함
        dialog.setCancelable(false);

        Button btnResume = dialog.findViewById(R.id.btn_resume); // 올바른 ID
        Button btnExit = dialog.findViewById(R.id.btn_exit2);     // 올바른 ID

        btnResume.setOnClickListener(v -> {
            isPaused = false;
            dialog.dismiss();
        });

        btnExit.setOnClickListener(v -> finish());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
