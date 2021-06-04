package sample.model;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Score {

    protected static ArrayList<Score> scores = new ArrayList<>();


    protected LocalDateTime id;
    protected Integer scoreValue = new Integer(0);
    protected String username;
    protected int rank;


    public Score() {}


    public Score(int score, String username) {
       this.username = username;
       this.scoreValue = new Integer(score);
       this.id = LocalDateTime.now();
       scores.add(this);
        try {
            FileWriter jsonWriter = new FileWriter("src/main/resources/Sample/Data/Scores.json");
            jsonWriter.write(new Gson().toJson(scores));
            jsonWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void updateScores() {
        try {
            FileWriter jsonWriter = new FileWriter("src/main/resources/Sample/Data/Scores.json");
            jsonWriter.write(new Gson().toJson(scores));
            jsonWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getScoreValue() {
        return scoreValue;
    }
    public static void setScores(ArrayList<Score> scores) {
        Score.scores = scores;
    }

    public Score getScoreByUser(User user) {
        for (Score score : scores) {
            if (score.username.equals(user.getUsername()))
                return score;
        }
        return  null;
    }


    public static void sortScores() {
        scores.sort((s1, s2) -> {
            if (s1.scoreValue != s2.scoreValue)
                return s2.scoreValue.compareTo(s1.scoreValue);
            else
                return s1.id.compareTo(s2.id);
        });
    }

    public static ArrayList<Score> getScores() {
        return scores;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public static void main(String[] args) {
        User user = new User("naser", "pass");
        new Score(100, user.getUsername());
        new Score(823, user.getUsername());
        new Score(87, user.getUsername());
        new Score(3883, user.getUsername());
        new Score(100, user.getUsername());
        new Score(355, user.getUsername());
        new Score(23, user.getUsername());
        new Score(823, user.getUsername());
        new Score(76, user.getUsername());
        new Score(700, user.getUsername());
        Score.sortScores();
        for (Score score : scores) {
            System.out.println(score.scoreValue + "\t\t" + score.id);
        }
    }

    public String getUsername() {
        return username;
    }


}
