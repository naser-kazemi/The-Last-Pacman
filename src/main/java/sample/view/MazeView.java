package sample.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.controller.GameController;
import sample.model.Maze;
import sample.model.Pacman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

import static sample.view.PacmanView.CELL_WIDTH;

public class MazeView extends Group {

    private static Pacman.Direction lastDirection;
    private static Pacman.Direction currentDirection;


    private int rowCount = 21;
    private int columnCount = 19;
    private Pacman.Cell[][] grid;
    private int score;
    protected int lives;
    protected int dotCount;
    protected int initialDotCount;
    protected int energyBombCount;
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




    public MazeView() {
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
        File file = new File("src/main/resources/Sample/sounds/eatDot.mp3");
        String dotEatingSoundUri = file.toURI().getPath();
        System.out.println("/Users/nkazemi/Desktop/IntelliJ%20IDEA/Pacman/src/main/resources/Sample/sounds/eatDot.mp3".charAt(32));
        System.out.println(dotEatingSoundUri);
        dotEatingSoundUri = dotEatingSoundUri.replaceAll(" ", "%20");
        System.out.println(dotEatingSoundUri);
        setGrid();
        setMap();
    }

    private void setMap() {
        for (int row = 0; row < this.rowCount; row++) {
            for (int column = 0; column < this.columnCount; column++) {
                Pacman.Cell value = grid[row][column];
                if (value == Pacman.Cell.WALL)
                    cellViews[row][column].setImage(this.wallImage);
                else if (value == Pacman.Cell.ENERGY_BOMB)
                    cellViews[row][column].setImage(this.energyBombImage);
                else if (value == Pacman.Cell.DOT)
                    cellViews[row][column].setImage(this.dotImage);
                else if (value == Pacman.Cell.PACMAN_HOME)
                    cellViews[row][column].setImage(this.pacmanRightImage);
                else if (value == Pacman.Cell.GHOST_1_HOME)
                    cellViews[row][column].setImage(this.ghost1Image);
                else if (value == Pacman.Cell.GHOST_2_HOME)
                    cellViews[row][column].setImage(this.ghost2Image);
                else if (value == Pacman.Cell.GHOST_3_HOME)
                    cellViews[row][column].setImage(this.ghost3Image);
                else if (value == Pacman.Cell.GHOST_4_HOME)
                    cellViews[row][column].setImage(this.ghost4Image);
                else
                    cellViews[row][column].setImage(null);
            }
        }
    }


    public void setGrid() {
        initializeGrid();
        File file = new File("src/main/resources/Sample/Data/test.txt");
        columnCount = 0;
        rowCount = 0 ;
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(scanner.hasNextLine()){
            int row = 0;
            int column = 0;
            String line= scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()){
                String value = lineScanner.next();
                Pacman.Cell thisValue;
                if (value.equals("W"))
                    thisValue = Pacman.Cell.WALL;
                else if (value.equals("S"))
                    thisValue = Pacman.Cell.DOT;
                else if (value.equals("B"))
                    thisValue = Pacman.Cell.ENERGY_BOMB;
                else if (value.equals("1"))
                    thisValue = Pacman.Cell.GHOST_1_HOME;
                else if (value.equals("2"))
                    thisValue = Pacman.Cell.GHOST_2_HOME;
                else if (value.equals("3"))
                    thisValue = Pacman.Cell.GHOST_3_HOME;
                else if (value.equals("4"))
                    thisValue = Pacman.Cell.GHOST_4_HOME;
                else if (value.equals("P"))
                    thisValue = Pacman.Cell.PACMAN_HOME;
                else
                    thisValue = Pacman.Cell.EMPTY;
                grid[row][column] = thisValue;
                column++;
            }
            row++;
        }
    }


    public void initializeGrid() {

        grid = new Pacman.Cell[21][19];

//        for (int i = 0; i < 21; i++)
//            for (int j = 0; j < 19; j++)
//                grid[i][j] = new Pacman.Cell;

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




    public static void main(String[] args) {
        Maze maze = new Maze();
        String[][] mazeString = maze.printMaze();
    }


}
