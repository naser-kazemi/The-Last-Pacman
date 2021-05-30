package sample.model;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.*;


public class Map extends Application {

    protected static ArrayList<Map> maps = new ArrayList<>();

    int height, width;

    Pacman.Cell[][] cells;
    protected int lives;
    protected Pacman pacman;
    protected int pacmanInitialRow;
    protected int pacmanInitialColumn;
    protected int score;
    protected Ghost inky;
    protected Ghost blinky;
    protected Ghost pinky;
    protected Ghost stinky;

    private int      tileHeight;
    private int      tileWidth;



//    private void initialize() {
//        cells = new Cell[height][width];
//        for (int i = 0; i < height; i++)
//            for (int j = 0; i < width; i++)
//                cells[i][j] = new Cell();
//    }




//    public Map(int height, int width) {
//        this.height = 2 * height + 1;
//        this.width = 2 * width + 1;
//        initialize();
//        Maze maze = new Maze(height, width);
//        convertMazeToMap(maze);
//        pacman = new Pacman(height, width, this, 3);
//        inky = new Ghost(1, 1, this, "inky.png");
//        blinky = new Ghost(this.height - 2, 1, this, "blinky.png");
//        pinky = new Ghost(1, this.width - 2, this, "pinky.png");
//        stinky = new Ghost(tileHeight - 2, this.width - 2, this, "clyde.png");
//        inky.start();
//        blinky.start();
//        stinky.start();
//        pinky.start();
//    }



//    private void convertMazeToMap(Maze maze) {
//        for (int i = 0; i < height; i++)
//            for (int j = 0; j < width; ++j) {
//                if (maze.map[i][j] == 0)
//                    cells[i][j] = new Pacman.Cell(new Wall());
//                else if (maze.map[i][j] == 1)
//                    cells[i][j] = new Pacman.Cell(new Dot());
//                else if (maze.map[i][j] == 3) {
//                    cells[i][j] = new Pacman.Cell(new Dot());
//                }
//                else if (maze.map[i][j] == 4)
//                    cells[i][j] = new Pacman.Cell(new EnergyBomb());
//            }
//    }


//    public Pacman.Cell[][] getCells() {
//        return cells;
//    }
//
//
//
//    public static void main(String[] args) {
//        Map map = new Map(10, 10);
//
//    }
//
//
    @Override
    public void start(Stage stage) throws Exception {

    }
}
