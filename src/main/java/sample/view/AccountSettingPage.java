package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.*;
import sample.controller.AccountSettingPageController;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AccountSettingPage extends Application {

    private static Stage mainStage;
    private static Scene mainScene;
    private static String message;


    public Text result = new Text();
    public TextField oldPassword = new TextField();
    public TextField newPassword = new TextField();
    public TextField confirmNewPassword = new TextField();
    protected Alert deleteAccountAlert = new Alert(AlertType.NONE, "Do you want to delete this account?" +
            "(The process can not be undone!)", ButtonType.YES, ButtonType.CANCEL);
    protected Alert resultAlert = new Alert(AlertType.NONE, "Account Deleted!", ButtonType.OK);

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL pageAddress = getClass().getResource("/Sample/fxml/account_setting_page.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(pageAddress));
        Scene scene = new Scene(root);
        mainScene = scene;
        stage.setScene(scene);
        stage.show();
    }


    public void goToChangePassword(MouseEvent mouseEvent) throws IOException {
        URL pageAddress = getClass().getResource("/Sample/fxml/change_password_page.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(pageAddress));
        Scene scene = new Scene(root);
        mainScene = scene;
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void changePassword(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
        String oldPasswordString = oldPassword.getText();
        String newPasswordString = newPassword.getText();
        String confirmNewPasswordString = confirmNewPassword.getText();
        AccountSettingPageController.getInstance().changePassword(oldPasswordString,
                newPasswordString,
                confirmNewPasswordString);
        if (message.equals("Password Changed!"))
            result.setFill(new Color(0, 1, 0, 1));
        else
            result.setFill(new Color(1, 0.05, 0.2, 0.7));

        result.setText(message);
    }




    private void setAlerts() {
        deleteAccountAlert.setTitle("Confirm Delete Account");
        URL css = getClass().getResource("/sample/css/alert_style.css");
        DialogPane dialogPane = deleteAccountAlert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        ButtonBar buttonBar = (ButtonBar) deleteAccountAlert.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-background-color: #403e45;" +
                "-fx-pref-height: 40px;" + "-fx-text-fill: black" + "");
        buttonBar.getButtons().forEach(button -> button.setStyle("-fx-background-color: #e2b44d;" +
                "-fx-pref-height: 30px;" + "-fx-text-fill: black"));
        DialogPane newDialogPane = resultAlert.getDialogPane();
        newDialogPane.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());
        newDialogPane.getStyleClass().add("dialog-pane2");
        ButtonBar buttonBar2 = (ButtonBar) resultAlert.getDialogPane().lookup(".button-bar");
        buttonBar2.setStyle("-fx-background-color: #403e45;" +
                "-fx-pref-height: 40px;" + "-fx-text-fill: black" + "");
        buttonBar2.getButtons().forEach(button -> button.setStyle("-fx-background-color: #e2b44d;" +
                "-fx-pref-height: 30px;" + "-fx-text-fill: black"));
    }



    public void deleteAccount(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent);
        setAlerts();
        deleteAccountAlert.showAndWait();
        if (deleteAccountAlert.getResult() == ButtonType.YES) {
            AccountSettingPageController.getInstance().deleteAccount();
            resultAlert.showAndWait();
            if (resultAlert.getResult() == ButtonType.OK)
                resultAlert.close();
                new LoginPage().start(mainStage);
        }
        else
            deleteAccountAlert.close();
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent);
        new MainPage().start(mainStage);
    }


    public static void setMessage(String message) {
        AccountSettingPage.message = message;
    }

    public void goBackToAccountSettingPage(MouseEvent mouseEvent) throws IOException {
        System.out.println(mouseEvent);
        URL pageAddress = getClass().getResource("/Sample/fxml/account_setting_page.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(pageAddress));
        Scene scene = new Scene(root);
        mainScene = scene;
        mainStage.setScene(scene);
        mainStage.show();
    }

}
