package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Pacman;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SelectLivesAndMode extends Application {

    public static Stage mainStage;


    protected Alert livesAlert = new Alert(Alert.AlertType.NONE, "Lives selected!", ButtonType.OK);
    protected Alert modeAlert = new Alert(Alert.AlertType.NONE, "Mode selected!", ButtonType.OK);


    private void setAlerts() {
        URL css = getClass().getResource("/sample/css/alert_style.css");
        DialogPane dialogPane = livesAlert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        ButtonBar buttonBar = (ButtonBar) livesAlert.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-background-color: #403e45;" +
                "-fx-pref-height: 40px;" + "-fx-text-fill: black" + "");
        buttonBar.getButtons().forEach(button -> button.setStyle("-fx-background-color: #e2b44d;" +
                "-fx-pref-height: 30px;" + "-fx-text-fill: black"));
        DialogPane newDialogPane = modeAlert.getDialogPane();
        newDialogPane.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());
        newDialogPane.getStyleClass().add("dialog-pane2");
        ButtonBar buttonBar2 = (ButtonBar) modeAlert.getDialogPane().lookup(".button-bar");
        buttonBar2.setStyle("-fx-background-color: #403e45;" +
                "-fx-pref-height: 40px;" + "-fx-text-fill: black" + "");
        buttonBar2.getButtons().forEach(button -> button.setStyle("-fx-background-color: #e2b44d;" +
                "-fx-pref-height: 30px;" + "-fx-text-fill: black"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sample/fxml/select_lives.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();

    }

    public void continueToSelectMode(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sample/fxml/select_mode.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.show();
        root.requestFocus();
    }

    public void continueToGamePage(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new GamePage().start(mainStage);
    }

    public void setLives2(MouseEvent mouseEvent) {
        setAlerts();
        System.out.println(mouseEvent);
        Pacman.selectedLives = 2;
        livesAlert.showAndWait();
        if (livesAlert.getResult() == ButtonType.OK)
            livesAlert.close();
    }

    public void setLives3(MouseEvent mouseEvent) {
        setAlerts();
        System.out.println(mouseEvent);
        Pacman.selectedLives = 3;
        livesAlert.showAndWait();
        if (livesAlert.getResult() == ButtonType.OK)
            livesAlert.close();
    }
    public void setLives4(MouseEvent mouseEvent) {
        setAlerts();
        System.out.println(mouseEvent);
        Pacman.selectedLives = 4;
        livesAlert.showAndWait();
        if (livesAlert.getResult() == ButtonType.OK)
            livesAlert.close();
    }
    public void setLives5(MouseEvent mouseEvent) {
        setAlerts();
        System.out.println(mouseEvent);
        Pacman.selectedLives = 5;
        livesAlert.showAndWait();
        if (livesAlert.getResult() == ButtonType.OK)
            livesAlert.close();
    }

    public void selectEasyMode(MouseEvent mouseEvent) {
        setAlerts();
        Pacman.staticMode = Pacman.Mode.EASY;
        modeAlert.showAndWait();
        if (modeAlert.getResult() == ButtonType.OK)
            modeAlert.close();
    }

    public void selectHardMode(MouseEvent mouseEvent) {
        setAlerts();
        Pacman.staticMode = Pacman.Mode.HARD;
        modeAlert.showAndWait();
        if (modeAlert.getResult() == ButtonType.OK)
            modeAlert.close();
    }


    public void backToSelectLives(MouseEvent mouseEvent) throws IOException {
        System.out.println(mouseEvent);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sample/fxml/select_lives.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.show();
        root.requestFocus();
    }

    public void backToMainPage(MouseEvent mouseEvent) throws Exception {
        new MainPage().start(mainStage);
    }

}
