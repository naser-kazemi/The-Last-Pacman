package Sample.Controller;

import Sample.Model.Map;
import Sample.Model.Pacman;
import Sample.Model.User;
import Sample.View.PacmanView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;

public class GameController implements EventHandler<KeyEvent> {

    protected static int EnergyBombCounter;
    protected static User currentUser;

    @FXML
    private Label scoreLabel;
    @FXML private Label levelLabel;
    @FXML private Label gameOverLabel;
    @FXML private PacmanView pacmanView;
    private Pacman pacman;

    ArrayList<Map> userMaps;


    private Timer timer;
    private static int ghostEatingModeCounter;
    private boolean paused;



    public void setMaps() {
        try  {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Sample/Data/Maps/" +
                                    currentUser.getUsername() + ".json")));
            userMaps = new Gson().fromJson(data, new TypeToken<ArrayList<Map>>(){}.getType());

        }catch(IOException e) {
            e.printStackTrace();
        }
    }



    public GameController() {
        this.paused = false;
    }






    @Override
    public void handle(KeyEvent keyEvent) {

    }


    public static int getEnergyBombCounter() {
        return EnergyBombCounter;
    }
}
