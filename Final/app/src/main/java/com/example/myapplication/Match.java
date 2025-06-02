package com.example.myapplication;
//Match클래스는 데이터 모델 역할
public class Match {
    public String player1;
    public String player1Result;
    public String player2;
    public String player2Result;

    public Match(String p1, String p1Result, String p2, String p2Result) {
        this.player1 = p1;
        this.player1Result = p1Result;
        this.player2 = p2;
        this.player2Result = p2Result;
    }
}