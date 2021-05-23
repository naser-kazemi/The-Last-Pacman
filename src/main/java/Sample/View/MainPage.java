package Sample.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
}
