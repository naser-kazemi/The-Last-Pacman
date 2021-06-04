package sample.view;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.model.Score;


public class ScoreboardPage extends Application {

    private static Stage mainStage;


    public GridPane gridPane = new GridPane();
    private int rankToGive = 1;
    private int totalRank = 1;
    Button backButton = new Button("Back");




    private void setScoreboardRanks() {
        int currentScore = 0;
        Score.sortScores();
        if (Score.getScores() != null && Score.getScores().size() > 0) {
            Score.getScores().get(0).setRank(rankToGive);
            totalRank++;
            currentScore = Score.getScores().get(0).getScoreValue();
            for (int i = 1; i < 10 && i < Score.getScores().size(); i++) {
                if (Score.getScores().get(i).getScoreValue() == currentScore) {
                    Score.getScores().get(i).setRank(rankToGive);
                    totalRank++;
                }
                else {
                    rankToGive = totalRank;
                    totalRank++;
                    Score.getScores().get(i).setRank(rankToGive);
                }
                currentScore = Score.getScores().get(i).getScoreValue();
            }
        }
    }


    public void setScoreBoard() {
        Label title = new Label("Scoreboard");
        title.setStyle("-fx-text-fill: azure; -fx-cell-size: 5; -fx-font-size: 35;");
        gridPane.add(title, 0, 0);
        GridPane.setHalignment(title, HPos.CENTER);
        setScoreboardRanks();
        int i = 0;
        for (; i < Score.getScores().size() && i < 10; i++) {
            Label text = new Label(Score.getScores().get(i).getRank() + "             " +
                    Score.getScores().get(i).getUsername() +
                    "                      " +  Score.getScores().get(i).getScoreValue());
            text.setStyle("-fx-text-fill: azure; -fx-cell-size: 5; -fx-font-size: 20;");
            gridPane.add(text, 0, i + 1);
            GridPane.setHalignment(text, HPos.CENTER);
        }
        backButton.setOnMouseClicked((MouseEvent event) -> {
            try {
                new MainPage().start(mainStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        backButton.setStyle("-fx-background-color: #e2b44d; -fx-pref-height: 30px; -fx-text-fill: black; -fx-alignment: center;");
        gridPane.add(backButton, 0, i + 1);
        GridPane.setHalignment(backButton, HPos.CENTER);
    }




    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        gridPane.setMinSize(800, 600);
        gridPane.setPadding(new Insets(100, 100, 100, 100));
        gridPane.setVgap(20);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-pref-height: 549.0; -fx-pref-width: 800.0; -fx-background-color: #261e7d;");
        setScoreBoard();
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
        for (Score score : Score.getScores()) {
            System.out.println(score.getScoreValue());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
