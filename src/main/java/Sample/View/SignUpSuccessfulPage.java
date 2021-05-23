package Sample.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class SignUpSuccessfulPage extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL signUpPageAddress = getClass().getResource("/Sample/fxml/sign_up_successful_page.fxml");
        Parent signUpPagePane = FXMLLoader.load(Objects.requireNonNull(signUpPageAddress));
        Scene scene = new Scene(signUpPagePane);
        stage.setScene(scene);
        stage.show();
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new LoginPage().start(mainStage);
    }
}
