package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomizeActivity extends AppCompatActivity {

    private int[] iconResIds = {
            R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.icon3,
            R.drawable.icon4
    };

    private int player1Index = 0;
    private int player2Index = 1;

    private ImageView iconImageP1, iconImageP2;
    private ImageButton leftArrowP1, rightArrowP1, leftArrowP2, rightArrowP2;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        iconImageP1 = findViewById(R.id.iconImageP1);
        iconImageP2 = findViewById(R.id.iconImageP2);
        leftArrowP1 = findViewById(R.id.leftArrowP1);
        rightArrowP1 = findViewById(R.id.rightArrowP1);
        leftArrowP2 = findViewById(R.id.leftArrowP2);
        rightArrowP2 = findViewById(R.id.rightArrowP2);
        confirmButton = findViewById(R.id.confirmButton);
        EditText player1NameInput = findViewById(R.id.player1_name);
        EditText player2NameInput = findViewById(R.id.player2_name);
        updateIcons(); // 초기 아이콘 표시


        leftArrowP1.setOnClickListener(v -> {
            player1Index = (player1Index + iconResIds.length - 1) % iconResIds.length;
            updateIcons();
        });

        rightArrowP1.setOnClickListener(v -> {
            player1Index = (player1Index + 1) % iconResIds.length;
            updateIcons();
        });

        leftArrowP2.setOnClickListener(v -> {
            player2Index = (player2Index + iconResIds.length - 1) % iconResIds.length;
            updateIcons();
        });

        rightArrowP2.setOnClickListener(v -> {
            player2Index = (player2Index + 1) % iconResIds.length;
            updateIcons();
        });

        confirmButton.setOnClickListener(v -> {
            String name1 = player1NameInput.getText().toString().trim();
            String name2 = player2NameInput.getText().toString().trim();

            if (name1.isEmpty() || name2.isEmpty()) {
                Toast.makeText(this, "두 플레이어 이름을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (player1Index == player2Index) {
                Toast.makeText(this, "같은 아이콘은 선택할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("player1Name", name1);
            intent.putExtra("player2Name", name2);
            intent.putExtra("player1Icon", iconResIds[player1Index]);
            intent.putExtra("player2Icon", iconResIds[player2Index]);
            startActivity(intent);
            finish();
        });
    }

    private void updateIcons() {
        iconImageP1.setImageResource(iconResIds[player1Index]);
        iconImageP2.setImageResource(iconResIds[player2Index]);
    }
}
