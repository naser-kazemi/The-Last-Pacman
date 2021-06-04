package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.controller.GameController;
import sample.model.Pacman;

public class SelectLives extends Application {

    public static Stage mainStage;


    protected Alert resultAlert = new Alert(Alert.AlertType.NONE, "Lives selected", ButtonType.OK);

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sample/fxml/select_lives.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();

    }

    public void continueToGamePage(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new GamePage().start(mainStage);
    }

    public void setLives2(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
        Pacman.selectedLives = 2;
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }

    public void setLives3(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
        Pacman.selectedLives = 3;
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }
    public void setLives4(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
        Pacman.selectedLives = 4;
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }
    public void setLives5(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
        Pacman.selectedLives = 5;
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }
}
