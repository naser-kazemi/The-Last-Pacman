package sample.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.controller.LoginPageController;
import sample.model.User;

import java.net.URL;
import java.util.Objects;

public class WelcomePage extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pac-Man");
        mainStage = stage;
        URL welcomePageAddress = getClass().getResource("/Sample/fxml/welcome_page.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(welcomePageAddress));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void run(String[] args) {
        launch(args);
    }



    public void exit(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSource());
        System.exit(0);
    }


    public void goToLoginPage(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new LoginPage().start(mainStage);
    }

    public void goToMainPageAsGuest(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        MainPage.isPlayAsGuest = true;
        new User("******Guest******", "%$#@NoPassword@#$%");
        LoginPageController.getInstance().login("******Guest******", "%$#@NoPassword@#$%");
        new MainPage().start(mainStage);
    }


}
