package com.example.myapplication;


public class Score implements Comparable<Score> {
    private String scoreDate;
    public int scoreNum;
    public int index;

    public Score(String date, int num){
        scoreDate=date;
        scoreNum=num;
    }

    @Override
    public int compareTo(Score sc) {
        //return 0 if equal
        //1 if passed greater than this
        //-1 if this greater than passed
        return sc.scoreNum>scoreNum? 1 : sc.scoreNum<scoreNum? -1 : 0;
    }

    public String getScoreText() {
        return scoreNum +" points - " +scoreDate;
    }
}