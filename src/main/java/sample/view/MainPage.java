package sample.view;

import sample.controller.GameController;
import sample.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class MainPage extends Application {

    private static Stage mainStage;
    private static User currentUser;
    private static Scene mainPageScene;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL mainPageAddress = getClass().getResource("/Sample/fxml/main_page.fxml");
        Parent mainPagePane = FXMLLoader.load(Objects.requireNonNull(mainPageAddress));
        Scene scene = new Scene(mainPagePane);
        mainPageScene = scene;
        stage.setScene(scene);
        stage.show();
        System.out.println(LoginPage.getCurrentUser().getUsername() + "\t" + LoginPage.getCurrentUser().getPassword());
    }

    public void newGame(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        GameController.setCurrentUser(currentUser);
        new GamePage().start(mainStage);
    }

    public void continueLastGame(MouseEvent mouseEvent) throws Exception {
        if (currentUser.getHasSaved()) {
            GameController.setSavedGame(true);
            System.out.println(mouseEvent.getSource());
            new GamePage().start(mainStage);
            System.out.println(currentUser.getPacman());
            System.out.println(GameController.getPacman());
        }
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new LoginPage().start(mainStage);
    }


    public static void setCurrentUser(User currentUser) {
        MainPage.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Parent getMainPageScene() {
        return mainPageScene.getRoot();
    }
}
