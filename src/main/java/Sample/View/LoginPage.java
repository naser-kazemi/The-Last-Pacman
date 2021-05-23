package Sample.View;

import Sample.Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class LoginPage extends Application {

    protected static LoginPage instance = new LoginPage();
    private static Stage mainStage;



    protected User currentUser;
    public TextField username;
    public TextField password;


    public static LoginPage getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return instance.currentUser;
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL loginPageAddress = getClass().getResource("/Sample/fxml/login_page.fxml");
        Parent loginPagePane = FXMLLoader.load(Objects.requireNonNull(loginPageAddress));
        Scene scene = new Scene(loginPagePane);
        stage.setScene(scene);
        stage.show();
    }


    public void goToSignUpPage(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new SignUpPage().start(mainStage);
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new WelcomePage().start(mainStage);
    }



    public static Stage getMainStage() {
        return mainStage;
    }
}
