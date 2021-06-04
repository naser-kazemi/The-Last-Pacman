package sample.view;

import javafx.scene.paint.Color;
import sample.controller.SingUpPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class SignUpPage extends Application {

    private static Stage mainStage;
    private static String message;



    public TextField username = new TextField();
    public TextField password = new TextField();
    public TextField confirmPassword = new TextField();
    public Text result = new Text("");


    @Override
    public void start(Stage stage) throws Exception {
        username.setStyle("-fx-text-inner-color: azure;");
        password.setStyle("-fx-text-inner-color: azure;");
        confirmPassword.setStyle("-fx-text-inner-color: azure;");
        mainStage = stage;
        URL signUpPageAddress = getClass().getResource("/Sample/fxml/sign_up_page.fxml");
        System.out.println(signUpPageAddress);
        Parent signUpPagePane = FXMLLoader.load(Objects.requireNonNull(signUpPageAddress));
        Scene scene = new Scene(signUpPagePane);
        stage.setScene(scene);
        stage.show();
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new LoginPage().start(mainStage);

    }


    public void goToWelcomePage(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new WelcomePage().start(mainStage);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void signUp(MouseEvent mouseEvent) throws Exception {
        String usernameString = username.getText();
        String passwordString = password.getText();
        String confirmPasswordString = confirmPassword.getText();
        SingUpPageController.getInstance().signUp(usernameString, passwordString, confirmPasswordString);
        if (!message.equals("Sign Up Successful!")) {
            result.setFill(new Color(1, 0.05, 0.2, 0.7));
            result.setText(message);
        }
    }

    public static void setMessage(String message) {
        SignUpPage.message = message;
    }
}
