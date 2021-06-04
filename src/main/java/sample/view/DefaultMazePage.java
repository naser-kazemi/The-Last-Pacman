package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Pacman;
import sample.model.User;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class DefaultMazePage extends Application {

    public static Stage mainStage;
    public static User currentUser;

    public Label result;
    protected Alert resultAlert;


    public void setAlert() {
        URL css = getClass().getResource("/sample/css/alert_style.css");
        DialogPane newDialogPane = resultAlert.getDialogPane();
        newDialogPane.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());
        newDialogPane.getStyleClass().add("dialog-pane2");
        ButtonBar buttonBar = (ButtonBar) resultAlert.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-background-color: #403e45;" +
                "-fx-pref-height: 40px;" + "-fx-text-fill: black" + "");
        buttonBar.getButtons().forEach(button -> button.setStyle("-fx-background-color: #e2b44d;" +
                "-fx-pref-height: 30px;" + "-fx-text-fill: black"));
    }


    @Override
    public void start(Stage stage) throws Exception {
        currentUser = MainPage.getCurrentUser();
        mainStage = stage;
        URL signUpPageAddress = getClass().getResource("/Sample/fxml/default_maze_page.fxml");
        System.out.println(signUpPageAddress);
        Parent signUpPagePane = FXMLLoader.load(Objects.requireNonNull(signUpPageAddress));
        Scene scene = new Scene(signUpPagePane);
        stage.setScene(scene);
        stage.show();
    }

    public void selectMap1(MouseEvent mouseEvent) {
        File file = new File("src/main/resources/sample/data/maps/level1.txt");
        if (file.exists()) {
            Pacman.mapFileName = "src/main/resources/sample/data/maps/level1.txt";
            Pacman.isMapDefault = false;
            resultAlert = new Alert(Alert.AlertType.NONE, "Map selected!", ButtonType.OK);
        }
        else
            resultAlert = new Alert(Alert.AlertType.NONE, "Map doesn't exist!", ButtonType.OK);
        setAlert();
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }

    public void selectMap2(MouseEvent mouseEvent) {
        File file = new File("src/main/resources/sample/data/maps/level2.txt");
        if (file.exists()) {
            Pacman.mapFileName = "src/main/resources/sample/data/maps/level2.txt";
            Pacman.isMapDefault = false;
            resultAlert = new Alert(Alert.AlertType.NONE, "Map selected!", ButtonType.OK);
        }
        else
            resultAlert = new Alert(Alert.AlertType.NONE, "Map doesn't exist!", ButtonType.OK);
        setAlert();
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }

    public void selectMap3(MouseEvent mouseEvent) {
        File file = new File("src/main/resources/sample/data/maps/level3.txt");
        if (file.exists()) {
            Pacman.mapFileName = "src/main/resources/sample/data/maps/level3.txt";
            Pacman.isMapDefault = false;
            resultAlert = new Alert(Alert.AlertType.NONE, "Map selected!", ButtonType.OK);
        }
        else
            resultAlert = new Alert(Alert.AlertType.NONE, "Map doesn't exist!", ButtonType.OK);
        setAlert();
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }

    public void selectMap4(MouseEvent mouseEvent) {
        File file = new File("src/main/resources/sample/data/maps/level4.txt");
        if (file.exists()) {
            Pacman.mapFileName = "src/main/resources/sample/data/maps/level4.txt";
            Pacman.isMapDefault = false;
            resultAlert = new Alert(Alert.AlertType.NONE, "Map selected!", ButtonType.OK);
        }
        else
            resultAlert = new Alert(Alert.AlertType.NONE, "Map doesn't exist!", ButtonType.OK);
        setAlert();
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }

    public void selectMap5(MouseEvent mouseEvent) {
        File file = new File("src/main/resources/sample/data/maps/level5.txt");
        if (file.exists()) {
            Pacman.mapFileName = "src/main/resources/sample/data/maps/level5.txt";
            Pacman.isMapDefault = false;
            resultAlert = new Alert(Alert.AlertType.NONE, "Map selected!", ButtonType.OK);
        }
        else
            resultAlert = new Alert(Alert.AlertType.NONE, "Map doesn't exist!", ButtonType.OK);
        setAlert();
        resultAlert.showAndWait();
        if (resultAlert.getResult() == ButtonType.OK)
            resultAlert.close();
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        new SelectMapPage().start(mainStage);
    }
}
