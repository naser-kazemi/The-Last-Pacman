package Sample.Model;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


//TODO create easy and hard mode
public class Pacman {

    public enum Cell {
        EMPTY, DOT, ENERGY_BOMB, WALL, GHOST_1_HOME, GHOST_2_HOME, GHOST_3_HOME, GHOST_4_HOME, PACMAN_HOME
    };
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    };

    protected static String currentFilename;
    private static Direction lastDirection;
    private static Direction currentDirection;
    private static boolean energyBombActive;
    private static boolean gameOver;
    private static boolean hasLost;



    @FXML protected int rowCount;
    @FXML protected int columnCount;
    private Cell[][] grid;
    private int score;
    protected int lives;
    protected int dotCount;
    protected Map startMap;
    private Point2D pacmanLocation;
    private Point2D pacmanVelocity;
    private Point2D ghost1Location;
    private Point2D ghost1Velocity;
    private Point2D ghost2Velocity;
    private Point2D ghost2Location;
    private Point2D ghost3Velocity;
    private Point2D ghost3Location;
    private Point2D ghost4Velocity;
    private Point2D ghost4Location;
    protected int row = 0;
    protected int pacmanRow = 0;
    protected int pacmanColumn = 0;
    protected int ghost1Row = 0;
    protected int ghost1Column = 0;
    protected int ghost2Row = 0;
    protected int ghost2Column = 0;
    protected int ghost3Row = 0;
    protected int ghost3Column = 0;
    protected int ghost4Row = 0;
    protected int ghost4Column = 0;



    public Pacman() {
        this.startNewGame();
    }




    //TODO initialize level
    private void startNewGame() {
        gameOver = false;
        energyBombActive = false;
        hasLost = false;

        dotCount = 0;
        rowCount = 0;
        columnCount = 0;
        this.score = 0;
        this.lives = 3;
        initializeGame(currentFilename);
    }


    public void setDimensions(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                lineScanner.next();
                this.columnCount++;
            }
            this.rowCount++;
        }
    }


    public void setGrid(Scanner scanner) {
        while(scanner.hasNextLine()){
            int column = 0;
            String line= scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()){
                String value = lineScanner.next();
                Cell thisValue;
                if (value.equals("W")){
                    thisValue = Cell.WALL;
                }
                else if (value.equals("S")){
                    thisValue = Cell.DOT;
                    dotCount++;
                }
                else if (value.equals("B")){
                    thisValue = Cell.ENERGY_BOMB;
                    dotCount++;
                }
                else if (value.equals("1")){
                    thisValue = Cell.GHOST_1_HOME;
                    ghost1Row = row;
                    ghost1Column = column;
                }
                else if (value.equals("2")){
                    thisValue = Cell.GHOST_2_HOME;
                    ghost2Row = row;
                    ghost2Column = column;
                }
                else if (value.equals("3")){
                    thisValue = Cell.GHOST_3_HOME;
                    ghost3Row = row;
                    ghost3Column = column;
                }
                else if (value.equals("4")){
                    thisValue = Cell.GHOST_4_HOME;
                    ghost4Row = row;
                    ghost4Column = column;
                }
                else if (value.equals("P")){
                    thisValue = Cell.PACMAN_HOME;
                    pacmanRow = row;
                    pacmanColumn = column;
                }
                else
                    thisValue = Cell.EMPTY;
                grid[row][column] = thisValue;
                column++;
            }
            row++;
        }
    }

    public void setElements() {
        pacmanLocation = new Point2D(pacmanRow, pacmanColumn);
        pacmanVelocity = new Point2D(0,0);
        ghost1Location = new Point2D(ghost1Row,ghost1Column);
        ghost1Velocity = new Point2D(-1, 0);
        ghost2Location = new Point2D(ghost2Row,ghost2Column);
        ghost2Velocity = new Point2D(-1, 0);
        ghost3Location = new Point2D(ghost3Row,ghost3Column);
        ghost3Velocity = new Point2D(-1, 0);
        ghost4Location = new Point2D(ghost4Row,ghost4Column);
        ghost4Velocity = new Point2D(-1, 0);
        currentDirection = Direction.NONE;
        lastDirection = Direction.NONE;
    }


    public void initializeGame(String fileName) {
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setDimensions(scanner);
        this.grid = new Cell[rowCount][columnCount];
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setGrid(scanner);
        setElements();
    }



    public void startNextRound() {
        if (this.dotCount == 0) {
            this.lives++;
            rowCount = 0;
            columnCount = 0;
            hasLost = false;
            energyBombActive = false;
        }
        else if (this.lives == 0) {
            gameOver = true;
        }
    }



    public Point2D setVelocity(Direction direction) {
        if (direction == Direction.UP)
            return new Point2D(-1, 0);
        else if (direction == Direction.RIGHT)
            return new Point2D(0, 1);
        else if (direction == Direction.DOWN)
            return new Point2D(1, 0);
        else if (direction == Direction.LEFT)
            return new Point2D(0, -1);
        else
            return new Point2D(0, 0);
    }




    public void setVelocityWithSameDirection(Point2D location, Point2D velocity) {
        int column = (int) location.getY();
        int row = (int) location.getX();
        if (grid[row][column] == Cell.WALL) {
            pacmanVelocity = setVelocity(Direction.NONE);
            setLastDirection(Direction.NONE);
        }
        else {
            pacmanVelocity = velocity;
            pacmanLocation = location;
        }
    }


    public void setVelocityWithNewDirection(Point2D location, Point2D velocity, Direction direction) {
        int column = (int) location.getY();
        int row = (int) location.getX();
        if (grid[row][column] == Cell.WALL) {
            pacmanVelocity = setVelocity(lastDirection);
            location = pacmanLocation.add(velocity);
            column = (int) location.getY();
            row = (int) location.getX();
            if (grid[row][column] == Cell.WALL) {
                pacmanVelocity = setVelocity(Direction.NONE);
                setLastDirection(Direction.NONE);
            }
            else {
                pacmanVelocity = setVelocity(lastDirection);
                pacmanLocation = pacmanLocation.add(pacmanVelocity);
            }
        }
        else {
            pacmanVelocity = velocity;
            pacmanLocation = location;
            setLastDirection(direction);
        }
    }

    public static void setLastDirection(Direction lastDirection) {
        Pacman.lastDirection = lastDirection;
    }

    public void movePacman(Direction direction) {
        Point2D pacmanVelocityCandidate = setVelocity(direction);
        Point2D pacmanLocationCandidate = this.pacmanLocation.add(pacmanVelocityCandidate);

        pacmanLocationCandidate = adjustColumnLocation(pacmanLocationCandidate);

        if (direction == lastDirection)
            setVelocityWithSameDirection(pacmanLocationCandidate, pacmanVelocityCandidate);
        else
            setVelocityWithNewDirection(pacmanLocationCandidate, pacmanVelocityCandidate, direction);
    }





    public Point2D adjustColumnLocation(Point2D location) {
        if (location.getY() >= columnCount) {
            location = new Point2D(location.getX(), 0);
        }
        if (location.getY() < 0) {
            location = new Point2D(location.getX(), columnCount - 1);
        }
        return location;
    }

    public Point2D adjustRowLocation(Point2D location) {
        if (location.getY() >= rowCount) {
            location = new Point2D( 0, location.getX());
        }
        if (location.getY() < 0) {
            location = new Point2D(rowCount - 1, location.getX());
        }
        return location;
    }



    public void moveGhosts() {

    }


    public Point2D moveThisGhost(Point2D location, Point2D velocity) {
        Random random = new Random();

        return new Point2D(0, 0);
    }


    public static void setEnergyBombActive(boolean energyBombActive) {
        Pacman.energyBombActive = energyBombActive;
    }




    public int getRowCount() {
        return this.rowCount;
    }

    public int getColumnCount() {
        return this.columnCount;
    }


    public Cell getCellValue(int row, int column) {
        assert row >= 0 && row < this.grid.length && column >= 0 && column < this.grid[0].length;
        return grid[row][column];
    }


    public Point2D getLocation() {
        return this.pacmanLocation;
    }

    public static Direction getLastDirection() {
        return lastDirection;
    }

    public static Direction getCurrentDirection() {
        return currentDirection;
    }

    public static boolean isEnergyBombActive() {
        return energyBombActive;
    }


    public Point2D getGhost1Location() {
        return ghost1Location;
    }

    public Point2D getGhost2Location() {
        return ghost2Location;
    }

    public Point2D getGhost3Location() {
        return ghost3Location;
    }

    public Point2D getGhost4Location() {
        return ghost4Location;
    }
}
