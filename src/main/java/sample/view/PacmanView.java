package sample.view;

import javafx.scene.media.MediaPlayer;
import sample.controller.GameController;
import sample.model.Pacman;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;


import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

public class PacmanView extends Group {

    public final static double CELL_WIDTH = 40.0;
    private static MediaPlayer dotEatingSound;
    private static MediaPlayer energyBombEatingSound;

    @FXML
    private int rowCount;
    @FXML private int columnCount;
    private ImageView[][] cellViews;
    private final Image pacmanRightImage;
    private final Image pacmanUpImage;
    private final Image pacmanDownImage;
    private final Image pacmanLeftImage;
    private final Image ghost1Image;
    private final Image ghost2Image;
    private final Image ghost3Image;
    private final Image ghost4Image;
    private final Image blueGhostImage;
    private final Image wallImage;
    private final Image energyBombImage;
    private final Image dotImage;






    //TODO ghost images
    public PacmanView() {
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
        System.out.println("/Users/nkazemi/Desktop/IntelliJ IDEA/Pacman/src/main/resources/Sample/sounds/eatDot.mp3".charAt(32));
        System.out.println(dotEatingSoundUri);
//        dotEatingSound = new MediaPlayer(new Media(dotEatingSoundUri));
    }


    public void initializeGrid() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.cellViews = new ImageView[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double) column * CELL_WIDTH);
                    imageView.setY((double) row * CELL_WIDTH);
                    imageView.setFitHeight(CELL_WIDTH);
                    imageView.setFitWidth(CELL_WIDTH);
                    this.cellViews[row][column] = imageView;
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    public void setCellImage(int row, int column,ImageView cell, Pacman pacman) {
        Pacman.Cell value = pacman.getCellValue(row, column);
        if (value == Pacman.Cell.WALL) {
            cell.setImage(this.wallImage);
        }
        else if (value == Pacman.Cell.ENERGY_BOMB) {
            cell.setImage(this.energyBombImage);
        }
        else if (value == Pacman.Cell.DOT) {
            cell.setImage(this.dotImage);
        }
        else {
            cell.setImage(null);
        }
    }

    public boolean isPacmanInLocation(int row, int column, Pacman pacman) {
        return (row == (int)pacman.getLocation().getX() && column == (int)pacman.getLocation().getY());
//                (row == (int)pacman.getLocation().getX() + 1 && column == (int)pacman.getLocation().getY()) ||
//                (row == (int)pacman.getLocation().getX() && column == (int)pacman.getLocation().getY() + 1) ||
//                (row == (int)pacman.getLocation().getX() + 1 && column == (int)pacman.getLocation().getY() + 1);
//        if ((row == (int)pacman.getLocation().getX() && column == (int)pacman.getLocation().getY()))
//            return true;
//        else if ((row == (int)pacman.getLocation().getX() + 1 && column == (int)pacman.getLocation().getY()))
//            return true;
//        else if ((row == (int)pacman.getLocation().getX() && column == (int)pacman.getLocation().getY() + 1))
//            return true;
//        else if ((row == (int)pacman.getLocation().getX() + 1 && column == (int)pacman.getLocation().getY() + 1))
//            return true;
//        else
//            return false;

    }


    public void setPacmanDirection(int row, int column, ImageView cell, Pacman pacman) {
        if (isPacmanInLocation(row, column, pacman)){
            if (Pacman.getLastDirection() == Pacman.Direction.RIGHT || Pacman.getLastDirection() == Pacman.Direction.NONE)
                cell.setImage(this.pacmanRightImage);
            else if (Pacman.getLastDirection() == Pacman.Direction.LEFT)
                cell.setImage(this.pacmanLeftImage);
            else if (Pacman.getLastDirection() == Pacman.Direction.UP)
                cell.setImage(this.pacmanUpImage);
            else if (Pacman.getLastDirection() == Pacman.Direction.DOWN)
                cell.setImage(this.pacmanDownImage);
        }
    }


    public boolean isGhostInLocation(int row, int column, Point2D ghostLocation) {
        return (row == (int)ghostLocation.getX() && (int)column == ghostLocation.getY());
    }

    public void setGhostsImageWithEnergyBombActive(int row, int column, ImageView cell, Pacman pacman) {
        if (GameController.getEnergyBombCounter() == 6 || GameController.getEnergyBombCounter() == 4 ||
                GameController.getEnergyBombCounter() == 2) {
            setGhostsImage(row, column, cell, pacman);
        }
        else {
            if (isGhostInLocation(row, column, pacman.getGhost1Location()))
                cell.setImage(this.blueGhostImage);
            if (isGhostInLocation(row, column, pacman.getGhost2Location()))
                cell.setImage(this.blueGhostImage);
            if (isGhostInLocation(row, column, pacman.getGhost3Location()))
                cell.setImage(this.blueGhostImage);
            if (isGhostInLocation(row, column, pacman.getGhost4Location()))
                cell.setImage(this.blueGhostImage);
        }
    }


    public void setGhostsImage(int row, int column, ImageView cell, Pacman pacman) {
        if (isGhostInLocation(row, column, pacman.getGhost1Location()))
            cell.setImage(this.ghost1Image);
        if (isGhostInLocation(row, column, pacman.getGhost2Location()))
            cell.setImage(this.ghost2Image);
        if (isGhostInLocation(row, column, pacman.getGhost3Location()))
            cell.setImage(this.ghost3Image);
        if (isGhostInLocation(row, column, pacman.getGhost4Location()))
            cell.setImage(this.ghost4Image);
    }


    public void updateGrid(Pacman pacman) {

        assert pacman.getRowCount() == this.rowCount && pacman.getColumnCount() == this.columnCount;

        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                setCellImage(row, column, this.cellViews[row][column], pacman);
                setPacmanDirection(row, column, this.cellViews[row][column], pacman);
                if (Pacman.isEnergyBombActive())
                    setGhostsImageWithEnergyBombActive(row, column, this.cellViews[row][column], pacman);
                else
                    setGhostsImage(row, column, this.cellViews[row][column], pacman);
            }
        }
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.initializeGrid();
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        this.initializeGrid();
    }

    public static void playDotEatingSound(boolean play) {
//        dotEatingSound.setAutoPlay(play);
    }

}
