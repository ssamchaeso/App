package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomizeActivity extends AppCompatActivity {

    private RadioGroup player1Group, player2Group;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        player1Group = findViewById(R.id.player1Group);
        player2Group = findViewById(R.id.player2Group);

        confirmButton=findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(v -> {
            int player1Id = player1Group.getCheckedRadioButtonId();
            int player2Id = player2Group.getCheckedRadioButtonId();

            if (player1Id == -1 || player2Id == -1) {
                Toast.makeText(this, "두 플레이어 모두 선택하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (player1Id == player2Id ||
                    getIconIndex(player1Id) == getIconIndex(player2Id)) {
                Toast.makeText(this, "같은 아이콘은 선택할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            int player1Icon = getIconDrawableRes(getIconIndex(player1Id));
            int player2Icon = getIconDrawableRes(getIconIndex(player2Id));

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("player1Icon", player1Icon);
            intent.putExtra("player2Icon", player2Icon);
            startActivity(intent);
            finish();
        });
    }

    private int getIconIndex(int radioButtonId) {
        if (radioButtonId == R.id.icon1_p1 || radioButtonId == R.id.icon1_p2) return 1;
        if (radioButtonId == R.id.icon2_p1 || radioButtonId == R.id.icon2_p2) return 2;
        if (radioButtonId == R.id.icon3_p1 || radioButtonId == R.id.icon3_p2) return 3;
        return 4;
    }

    private int getIconDrawableRes(int index) {
        switch (index) {
            case 1: return R.drawable.icon1;
            case 2: return R.drawable.icon2;
            case 3: return R.drawable.icon3;
            case 4: return R.drawable.icon4;
        }
        return R.drawable.icon1;
    }
}
