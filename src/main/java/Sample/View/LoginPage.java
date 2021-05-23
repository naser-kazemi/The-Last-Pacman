package Sample.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class LoginPage extends Application {

    private static Stage mainStage;


    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL loginPageAddress = getClass().getResource("/Sample/fxml/login_page.fxml");
        Parent loginPagePane = FXMLLoader.load(Objects.requireNonNull(loginPageAddress));
        Scene scene = new Scene(loginPagePane);
        stage.setScene(scene);
        stage.show();
    }


    public void goBack() throws Exception {
        new WelcomePage().start(mainStage);
    }


}
