package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        Button btnMakeRoom = findViewById(R.id.btn_make_room);
        btnMakeRoom.setOnClickListener(v -> createGameRoom("user_abc"));
        Button btnJoinRoom = findViewById(R.id.btn_join_room);
        btnJoinRoom.setOnClickListener(v -> joinAvailableRoom("user_xyz"));
    }

    private void createGameRoom(String userId) {
        String gameId = UUID.randomUUID().toString();
        DatabaseReference gameRef = FirebaseDatabase.getInstance().getReference("games").child(gameId);

        Map<String, Object> gameData = new HashMap<>();
        gameData.put("playerX", userId);
        gameData.put("playerO", "");
        gameData.put("board", new String[]{"", "", "", "", "", "", "", "", ""});
        gameData.put("currentTurn", "X");
        gameData.put("status", "waiting");
        gameData.put("timestamp", ServerValue.TIMESTAMP);

        gameRef.setValue(gameData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "방 생성됨: " + gameId);
                    // MainActivity로 이동
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("gameId", gameId);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "방 생성 실패: " + e.getMessage());
                });
    }
    private void joinAvailableRoom(String userId) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("games");

        dbRef.orderByChild("status").equalTo("waiting").limitToFirst(1)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            String gameId = child.getKey();
                            DatabaseReference gameRef = dbRef.child(gameId);

                            gameRef.child("playerO").setValue(userId);
                            gameRef.child("status").setValue("playing");

                            Log.d("Firebase", "참가 성공: " + gameId);

                            // MainActivity로 이동
                            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                            intent.putExtra("gameId", gameId);
                            startActivity(intent);

                            break; // 하나만 참가
                        }
                    } else {
                        Log.d("Firebase", "참가할 방이 없습니다.");
                        Toast.makeText(this, "참가할 수 있는 방이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "방 조회 실패: " + e.getMessage());
                    Toast.makeText(this, "방 참가 중 오류 발생", Toast.LENGTH_SHORT).show();
                });
    }

}
