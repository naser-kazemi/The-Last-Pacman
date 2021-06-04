package sample.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class MazePage extends Application {



    @FXML private int rowCount;
    @FXML private int columnCount;
    private ImageView[][] cellViews;
    private Image pacmanRightImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image ghost1Image;
    private Image ghost2Image;
    private Image ghost3Image;
    private Image ghost4Image;
    private Image blueGhostImage;
    private Image wallImage;
    private Image energyBombImage;
    private Image dotImage;






    //TODO ghost images
    public void setElements() {
        this.pacmanRightImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/pacmanRight.gif")));
        this.pacmanUpImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/pacmanUp.gif")));
        this.pacmanDownImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/pacmanDown.gif")));
        this.pacmanLeftImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/pacmanLeft.gif")));
        this.ghost1Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/blinky.png")));
        this.ghost2Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/clyde.png")));
        this.ghost3Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/inky.png")));
        this.ghost4Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/pinky.png")));
        this.blueGhostImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/blueghost.gif")));
        this.wallImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/wall.png")));
        this.energyBombImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/energyBomb.png")));
        this.dotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Sample/Images/smalldot.png")));
//        dotEatingSound = new MediaPlayer(new Media(Paths.get("eat.mp3").toUri().toString()));
//        energyBombEatingSound = new MediaPlayer(new Media(Paths.get("eat.mp3").toUri().toString()));
        File file = new File("src/main/resources/Sample/sounds/eatDot.mp3");
//        file.renameTo(new File("src/main/resources/Sample/sounds/eat.mp3"));
        String dotEatingSoundUri = file.toURI().getPath();
        System.out.println("/Users/nkazemi/Desktop/IntelliJ%20IDEA/Pacman/src/main/resources/Sample/sounds/eatDot.mp3".charAt(32));
        System.out.println(dotEatingSoundUri);
        dotEatingSoundUri = dotEatingSoundUri.replaceAll(" ", "%20");
        System.out.println(dotEatingSoundUri);
//        dotEatingSound = new MediaPlayer(new Media(dotEatingSoundUri));
    }


    @Override
    public void start(Stage stage) throws Exception {

    }
}
