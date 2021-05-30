package sample.view;

import sample.controller.LoginPageController;
import sample.model.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginPage extends Application {

    protected static LoginPage instance = new LoginPage();
    private static Stage mainStage;
    private static String message;
    protected static User currentUser = null;


    public TextField username = new TextField();
    public TextField password = new TextField();
    public Text result;
    public Button backLogout = new Button("Back");
    public Text usernameText = new Text();
    public Text passwordText = new Text();
    @FXML private Button button;


    public static LoginPage getInstance() {
        return instance;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LoginPage.currentUser = currentUser;
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL loginPageAddress;
        if (currentUser != null)
            loginPageAddress  = getClass().getResource("/Sample/fxml/logedin_page.fxml");
        else
            loginPageAddress = getClass().getResource("/Sample/fxml/login_page.fxml");
        Parent loginPagePane = FXMLLoader.load(Objects.requireNonNull(loginPageAddress));
        Scene scene = new Scene(loginPagePane);
        stage.setScene(scene);
        stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (currentUser != null) {
                    usernameText.setText(currentUser.getUsername());
                    passwordText.setText(currentUser.getPassword());
                }
            }
        });
        stage.show();
    }


    public void goToSignUpPage(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new SignUpPage().start(mainStage);
    }

    public void goBackLogout(MouseEvent mouseEvent) throws Exception {
        currentUser = null;
        System.out.println(mouseEvent.getSource());
        new WelcomePage().start(mainStage);
    }


    public void login(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        String usernameString = username.getText();
        String passwordString = password.getText();
        LoginPageController.getInstance().login(usernameString, passwordString);
        if (message.equals("Login Successful!"))
            result.setText(message);
        if (message.equals("Login Successful!")){
            TimeUnit.SECONDS.sleep(0);
            new MainPage().start(mainStage);
        }
        result.setText(message);
    }


    public static Stage getMainStage() {
        return mainStage;
    }


    public static void setMessage(String message) {
        LoginPage.message = message;
    }

    @FXML
    protected void show() {
        if (currentUser != null) {
            usernameText.setText(currentUser.getUsername());
            passwordText.setText(currentUser.getPassword());
        }
    }


    public void goToMainPage(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        new MainPage().start(mainStage);
    }
}
