package sample.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import sample.controller.AccountSettingPageController;
import sample.controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.User;

import static sample.view.MainPage.isPlayAsGuest;

public class GamePage extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sample/fxml/game_page.fxml"));
        Parent root = loader.load();
        GameController controller = loader.getController();
        root.setOnKeyPressed(controller);
        double sceneWidth = controller.getBoardWidth() + 20.0;
        double sceneHeight = controller.getBoardHeight() + 100.0;
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
                if (isPlayAsGuest) {
                    while (User.getUsernames().contains("******Guest******"))
                        AccountSettingPageController.getInstance().deleteAccount();
                }
            }
        });
        stage.setScene(new Scene(root, sceneWidth, sceneHeight));
        stage.show();
        root.requestFocus();
    }

    public static void main(String[] args) throws Exception{
        launch(args);
    }

    public void exit(MouseEvent mouseEvent) throws Exception{
        new MainPage().start(mainStage);
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}
/*
 --module-path "/Users/nkazemi/Jetbrains/javafx-sdk-11.0.2/lib" --add-modules=javafx.controls,javafx.graphics,javafx.fxml,Sample.Model.Pacman
 45Parzikjsck
 */