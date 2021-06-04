package sample.controller;

import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import sample.model.Pacman;
import sample.model.Score;
import sample.model.User;
import sample.view.*;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements EventHandler<KeyEvent> {


    private static final double FRAMES_PER_SECOND = 5.0;
    private static Stage mainStage;
    protected static int energyBombCounter;
    protected static User currentUser;
    private static boolean gameHasEnded;
    private static boolean savedGame;
    private static Pacman classPacman;
    public StackPane pauseMenu = new StackPane();


    @FXML private Label scoreLabel;
    @FXML private Label liveLabel;
    @FXML private Label gameOverLabel;
    @FXML private PacmanView pacmanView;
    private Pacman pacman;
    private boolean paused;
    private Timer timer;
    @FXML public MediaView themeMediaView;
    @FXML public Button muteButton = new Button();
    @FXML public Button pauseButton = new Button();
    @FXML protected final ImageView pauseImage = new ImageView(new Image(Objects.requireNonNull(GamePage.class.
    getResourceAsStream("/Sample/images/pause.png"))));
    @FXML protected final ImageView muted = new ImageView(new Image((Objects.requireNonNull(GamePage.class.
            getResourceAsStream("/Sample/images/muted.png")))));
    @FXML protected final ImageView unmuted = new ImageView(new Image((Objects.requireNonNull(GamePage.class.
            getResourceAsStream("/Sample/images/unmuted.png")))));
    public boolean isMuted;
    public Button resumeButton = new Button();
    public Button restartGameButton = new Button();
    public Button returnToMainPageButton = new Button();




    public GameController() {
        this.paused = false;
    }

    public static Pacman getPacman() {
        return classPacman;
    }

    public static void setPacman(Pacman pacman) {
        classPacman = pacman;
    }



    public void initialize() throws IOException {
        System.out.println("savedGame: " + savedGame);
        if (!savedGame) {
            this.pacman = new Pacman();
            this.update(Pacman.Direction.NONE);
            energyBombCounter = 100;
            this.startTimer();
        }
        else {
            this.pacman = currentUser.getPacman();
            this.update(Pacman.Direction.NONE);
            energyBombCounter = 100;
            this.startTimer();
            savedGame = false;
        }
        setPacman(pacman);
        themeMediaView = new MediaView(PacmanView.themeSound);
    }



    public void update(Pacman.Direction direction) {
        pacmanView.setEffect(null);
        pacmanView.requestFocus();
        pauseMenu.setVisible(false);
        unmuted.setFitHeight(30);
        unmuted.setFitWidth(30);
        muted.setFitHeight(30);
        muted.setFitWidth(30);
        pauseImage.setFitHeight(30);
        pauseImage.setFitWidth(30);
        if (isMuted)
            muteButton.setGraphic(muted);
        else
            muteButton.setGraphic(unmuted);
        pauseButton.setGraphic(pauseImage);
        this.pacman.makeMove(direction);
        this.pacmanView.updateGrid(pacman);
        this.scoreLabel.setText(String.format("Score: %d", this.pacman.getScore()));
        this.liveLabel.setText(String.format("Lives: %d", this.pacman.getLives()));
        if (Pacman.isGameOver()) {
            if (currentUser.getHighestScore() < this.pacman.getScore()) {
                new Score(this.pacman.getScore(), currentUser.getUsername());
                Score.updateScores();
                currentUser.setHighestScore(this.pacman.getScore());
            }
            this.gameOverLabel.setText(String.format("GAME OVER"));
            pause();
            currentUser.setHasSaved(false);
        }
        if (Pacman.haveYouWon()) {
            this.gameOverLabel.setText(String.format("YOU WON!"));
        }
        if (Pacman.isEnergyBombActive()) {
            energyBombCounter--;
        }
        if (energyBombCounter == 0 && Pacman.isEnergyBombActive()) {
            Pacman.setEnergyBombActive(false);
        }
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
        long frameTimeInMilliseconds = (long)(600.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    public void pause() {
        this.timer.cancel();
        this.paused = true;
        pauseMenu.setVisible(true);
        pacmanView.setEffect(new GaussianBlur());
    }


    @Override
    public void handle(KeyEvent keyEvent) {
        boolean keyRecognized = true;
        KeyCode code = keyEvent.getCode();
        Pacman.Direction direction = Pacman.Direction.NONE;
        if (code == KeyCode.UP)
            direction = Pacman.Direction.UP;
        else if (code == KeyCode.RIGHT)
            direction = Pacman.Direction.RIGHT;
        else if (code == KeyCode.DOWN)
            direction = Pacman.Direction.DOWN;
        else if (code == KeyCode.LEFT)
            direction = Pacman.Direction.LEFT;
        else if (code == KeyCode.SPACE && !paused)
            this.pause();
        else if (code == KeyCode.SPACE)
            this.resume();
        else
            keyRecognized = false;

        if (keyRecognized) {
            keyEvent.consume();
            pacman.setCurrentDirection(direction);
        }
    }

    @FXML
    private void resume() {
        pauseMenu.setVisible(false);
        this.startTimer();
        paused = false;
    }


    public static int getEnergyBombCounter() {
        return energyBombCounter;
    }


    public static void setEnergyBombCounter() {
        energyBombCounter = 100;
    }

    public double getBoardWidth() {
        return PacmanView.CELL_WIDTH * this.pacmanView.getColumnCount();
    }

    public double getBoardHeight() {
        return PacmanView.CELL_WIDTH * this.pacmanView.getRowCount();
    }


    public static void setCurrentUser(User currentUser) {
        GameController.currentUser = currentUser;
    }


    public static void setMainStage(Stage mainStage) {
        GameController.mainStage = mainStage;
    }

    public void returnToMainPage() throws Exception {
        if (currentUser != null) {
            currentUser.setPacman(pacman);
            pacman.setSavedGame(true);
            FileWriter jsonWriter = new FileWriter("src/main/resources/Sample/Data/savedGames/" + currentUser.getUsername() + ".json");
            jsonWriter.write(new Gson().toJson(currentUser.getPacman()));
            jsonWriter.close();
            if (!Pacman.isGameOver())
                currentUser.setHasSaved(true);
            User.updateUsers();
        }
        this.timer.cancel();
        PacmanView.themeSound.stop();
        new MainPage().start(GamePage.getMainStage());
    }

    public void restartGame() throws Exception {
        PacmanView.themeSound.stop();
        new GamePage().start(GamePage.getMainStage());
    }

    public static void setSavedGame(boolean savedGame) {
        GameController.savedGame = savedGame;
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        new NewMazePage().start(NewMazePage.mainStage);
    }

    public void createNewMap(MouseEvent mouseEvent) throws Exception {
        new NewMazePage().createAnotherMap();
    }

    public void muteUnmute(MouseEvent mouseEvent) throws Exception {
        if (isMuted)
            unMuteGame();
        else
            muteGame();
    }


    public void muteGame() {
        muteButton.setGraphic(muted);
        PacmanView.themeSound.setVolume(0);
        PacmanView.dotEatingSound.setVolume(0);
        PacmanView.pacmanDiesSound.setVolume(0);
        PacmanView.eatingGhostSound.setVolume(0);
        isMuted = true;
    }

    public void unMuteGame() {
        muteButton.setGraphic(unmuted);
        PacmanView.themeSound.setVolume(2);
        PacmanView.dotEatingSound.setVolume(2.5);
        PacmanView.pacmanDiesSound.setVolume(2.5);
        PacmanView.eatingGhostSound.setVolume(2.5);
        isMuted = false;
    }
}
