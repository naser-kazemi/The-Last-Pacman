package sample.controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Map;
import sample.model.Pacman;
import sample.model.User;
import sample.view.GamePage;
import sample.view.MainPage;
import sample.view.PacmanView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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


    @FXML private Label scoreLabel;
    @FXML private Label liveLabel;
    @FXML private Label gameOverLabel;
    @FXML private PacmanView pacmanView;
    private Pacman pacman;
    private boolean paused;
    private Timer timer;

    ArrayList<Map> userMaps;



    public GameController() {
        this.paused = false;
    }

    public static Pacman getPacman() {
        return classPacman;
    }

    public static void setPacman(Pacman pacman) {
        classPacman = pacman;
    }

    public void setMaps() {
        try  {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Sample/Data/Maps/" +
                                    currentUser.getUsername() + ".json")));
            userMaps = new Gson().fromJson(data, new TypeToken<ArrayList<Map>>(){}.getType());

        }catch(IOException e) {
            e.printStackTrace();
        }
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
//            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Sample/Data/savedGames/" +
//                                        currentUser.getUsername() + ".json")));
//            this.pacman = new Gson().fromJson(data, new TypeToken<Pacman>(){}.getType());
            this.pacman = currentUser.getPacman();
            this.update(Pacman.Direction.NONE);
            energyBombCounter = 100;
            this.startTimer();
            savedGame = false;
        }
        setPacman(pacman);
    }




    //TODO Lives
    public void update(Pacman.Direction direction) {
        this.pacman.makeMove(direction);
        //TODO pacmanView
        this.pacmanView.updateGrid(pacman);
        this.scoreLabel.setText(String.format("Score: %d", this.pacman.getScore()));
        this.liveLabel.setText(String.format("Lives: %d", this.pacman.getLives()));
        if (Pacman.isGameOver()) {
            this.gameOverLabel.setText(String.format("GAME OVER"));
            pause();
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
        long frameTimeInMilliseconds = (long)(650.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    public void pause() {
        this.timer.cancel();
        this.paused = true;
    }


    //TODO add more keys
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

    private void resume() {
        this.startTimer();
        paused = false;
    }


    public static int getEnergyBombCounter() {
        return energyBombCounter;
    }


    //TODO set counter
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

    //TODO remove user instantiation
    public void returnToMainPage(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent);
        if (currentUser != null) {
            currentUser.setPacman(pacman);
            pacman.setSavedGame(true);
            FileWriter jsonWriter = new FileWriter("src/main/resources/Sample/Data/savedGames/" + currentUser.getUsername() + ".json");
            jsonWriter.write(new Gson().toJson(currentUser.getPacman()));
            jsonWriter.close();
            currentUser.setHasSaved(true);
            User.updateUsers();
        }
        this.timer.cancel();
        new MainPage().start(GamePage.getMainStage());
    }

    public void restartGame(MouseEvent mouseEvent) throws Exception {
        System.out.println(mouseEvent);
//        this.timer.cancel();
//        pacman.initializeGame("src/main/resources/Sample/Data/Maps/level1.txt");
//        paused = false;
//        pacman.setGameOver(false);
        new GamePage().start(GamePage.getMainStage());
    }

    public static void setSavedGame(boolean savedGame) {
        GameController.savedGame = savedGame;
    }
}
