package com.example.vocalog;
public class save_word {
    private String word;
    private String mean;
    private String boardTitle;

    public save_word() {
        // 기본 생성자 (Firebase에 데이터를 읽고 쓸 때 필요)
    }

    public save_word(String word, String mean, String boardId, String boardTitle) {
        this.word = word;
        this.mean = mean;
        this.boardTitle = boardTitle;
    }

    public String getWord() {
        return word;
    }

    public String getMean() {
        return mean;
    }

    public String getBoardTitle() {
        return boardTitle;
    }
}