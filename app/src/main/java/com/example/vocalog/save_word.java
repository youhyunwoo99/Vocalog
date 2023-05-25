package com.example.vocalog;

public class save_word {
    String word;
    String mean;

    public save_word(){}

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public save_word(String word, String mean) {
        this.word = word;
        this.mean = mean;
    }
}
