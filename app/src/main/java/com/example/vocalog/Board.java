package com.example.vocalog;

public class Board {
    private String name;

    public Board() {
        // 파이어베이스에서 객체로 변환할 때 필요한 기본 생성자
    }

    public Board(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}