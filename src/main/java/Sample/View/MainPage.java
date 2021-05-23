package Sample.View;

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

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL mainPageAddress = getClass().getResource("/Sample/fxml/main_page.fxml");
        Parent mainPagepane = FXMLLoader.load(Objects.requireNonNull(mainPageAddress));
        Scene scene = new Scene(mainPagepane);
        stage.setScene(scene);
        stage.show();
    }

    public void newGame(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSource());
    }

    public void continueLastGame(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSource());
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new WelcomePage().start(mainStage);
    }


}
