package sample.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.controller.AccountSettingPageController;
import sample.controller.GameController;
import sample.model.Pacman;
import sample.model.User;
import sample.view.MazeView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static sample.view.MainPage.isPlayAsGuest;

public class MazePage extends Application {


    private static final double FRAMES_PER_SECOND = 5.0;
    private static Stage mainStage;
    protected static int energyBombCounter;
    protected static User currentUser;
    private static boolean gameHasEnded;
    private static boolean savedGame;
    private static Pacman classPacman;


    @FXML private Label scoreLabel;
    @FXML private Label liveLabel;
    @FXML private Label gameOverLabel;
    @FXML private PacmanView pacmanView;
    private Pacman pacman;
    private boolean paused;
    private Timer timer;




    public MazePage() {
        this.paused = false;
    }

    public static Pacman getPacman() {
        return classPacman;
    }

    public static void setPacman(Pacman pacman) {
        classPacman = pacman;
    }



    public void initialize() {
        this.pacman = new Pacman();
        this.update(Pacman.Direction.NONE);
        energyBombCounter = 100;
        this.startTimer();
    }




    public void update(Pacman.Direction direction) {
        this.pacman.makeMove(direction);
        this.pacmanView.updateGrid(pacman);
        timer.cancel();
    }


    public void startTimer() {
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        update(Pacman.getCurrentDirection());
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(750.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }


    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        URL pageAddress = getClass().getResource("/Sample/fxml/maze_page.fxml");
        FXMLLoader loader = new FXMLLoader(pageAddress);
        Parent root = loader.load();
        GameController controller = loader.getController();
        root.setOnKeyPressed(controller);
        double sceneWidth = controller.getBoardWidth() + 20.0;
        double sceneHeight = controller.getBoardHeight() + 100.0;
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
                if (isPlayAsGuest) {
                    while (User.getUsernames().contains("******Guest******"))
                        AccountSettingPageController.getInstance().deleteAccount();
                }
            }
        });
        stage.setScene(new Scene(root, sceneWidth, sceneHeight));
        stage.show();
        controller.pause();
        PacmanView.themeSound.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
