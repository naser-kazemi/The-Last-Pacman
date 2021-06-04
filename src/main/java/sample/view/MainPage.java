package sample.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import sample.controller.AccountSettingPageController;
import sample.controller.GameController;
import sample.model.Pacman;
import sample.model.User;
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
    private static User currentUser;
    private static Scene mainPageScene;
    public static boolean isPlayAsGuest;


    protected Alert resultAlert = new Alert(Alert.AlertType.NONE, "You have no saved games!", ButtonType.OK);



    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL mainPageAddress;
        if (isPlayAsGuest)
            mainPageAddress = getClass().getResource("/Sample/fxml/main_page_as_guest.fxml");
        else
            mainPageAddress = getClass().getResource("/Sample/fxml/main_page.fxml");
        Parent mainPagePane = FXMLLoader.load(Objects.requireNonNull(mainPageAddress));
        Scene scene = new Scene(mainPagePane);
        mainPageScene = scene;
        stage.setScene(scene);
        stage.show();
        System.out.println(LoginPage.getCurrentUser().getUsername() + "\t" + LoginPage.getCurrentUser().getPassword());
    }

    public void newGame(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        GameController.setCurrentUser(currentUser);
        new SelectLivesAndMode().start(mainStage);
    }

    public void continueLastGame(MouseEvent mouseEvent) throws Exception {
        if (currentUser.getHasSaved()) {
            GameController.setSavedGame(true);
            System.out.println(mouseEvent.getSource());
            new GamePage().start(mainStage);
            System.out.println(currentUser.getPacman());
            System.out.println(GameController.getPacman());
        }
        else {
            URL css = getClass().getResource("/sample/css/alert_style.css");
            DialogPane newDialogPane = resultAlert.getDialogPane();
            newDialogPane.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());
            newDialogPane.getStyleClass().add("dialog-pane2");
            ButtonBar buttonBar2 = (ButtonBar) resultAlert.getDialogPane().lookup(".button-bar");
            buttonBar2.setStyle("-fx-background-color: #403e45;" +
                    "-fx-pref-height: 40px;" + "-fx-text-fill: black" + "");
            buttonBar2.getButtons().forEach(button -> button.setStyle("-fx-background-color: #e2b44d;" +
                    "-fx-pref-height: 30px;" + "-fx-text-fill: black"));
            resultAlert.showAndWait();
            if (resultAlert.getResult() == ButtonType.OK)
                resultAlert.close();
        }
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent.getSource());
        if (isPlayAsGuest) {
            while (User.getUsernames().contains("******Guest******"))
            AccountSettingPageController.getInstance().deleteAccount();
            new WelcomePage().start(mainStage);
        }
        else
            new LoginPage().start(mainStage);
        isPlayAsGuest = false;
        Pacman.isMapDefault = true;
    }


    public static void setCurrentUser(User currentUser) {
        MainPage.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Parent getMainPageScene() {
        return mainPageScene.getRoot();
    }

    public void goToAccountSetting(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent);
        new AccountSettingPage().start(mainStage);
    }

    public void goToSelectMap(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent);
        new SelectMapPage().start(mainStage);
    }

    public void goToScoreboardPage(MouseEvent mouseEvent) throws Exception {
        new ScoreboardPage().start(mainStage);
    }
}
