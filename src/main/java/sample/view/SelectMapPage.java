package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class SelectMapPage extends Application {

    public static Stage mainStage;

    public void goToNewMaze(MouseEvent mouseEvent) throws Exception {
        new NewMazePage().start(mainStage);
    }

    public void goToPersonalMaze(MouseEvent mouseEvent) throws Exception {
        new PersonalMazePage().start(mainStage);
    }

    public void goToDefaultMaze(MouseEvent mouseEvent) throws Exception {
        new DefaultMazePage().start(mainStage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL signUpPageAddress = getClass().getResource("/Sample/fxml/select_map_page.fxml");
        System.out.println(signUpPageAddress);
        Parent signUpPagePane = FXMLLoader.load(Objects.requireNonNull(signUpPageAddress));
        Scene scene = new Scene(signUpPagePane);
        stage.setScene(scene);
        stage.show();
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent);
        new MainPage().start(mainStage);
    }
}
