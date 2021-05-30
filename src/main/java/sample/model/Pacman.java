package sample.model;

import sample.controller.GameController;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import sample.view.PacmanView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


//TODO create easy and hard mode
public class Pacman {


    public enum Cell {
        EMPTY, DOT, ENERGY_BOMB, WALL, GHOST_1_HOME, GHOST_2_HOME, GHOST_3_HOME, GHOST_4_HOME, PACMAN_HOME
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    protected static String currentFilename;
    private static Direction lastDirection;
    private static Direction currentDirection;
    private static boolean energyBombActive;
    private static boolean gameOver;
    private static boolean hasLost;
    private static boolean hasWon;




    @FXML protected int rowCount;
    @FXML protected int columnCount;
    private Cell[][] grid;
    private int score;
    protected int lives;
    protected int dotCount;
    protected int initialDotCount;
    protected int energyBombCount;
    protected int initialEnergyBombCount;
    protected int eatenGhostCount = 0;
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
    protected int row;
    protected int pacmanRow;
    protected int pacmanColumn;
    protected int ghost1Row;
    protected int ghost1Column;
    protected int ghost2Row;
    protected int ghost2Column;
    protected int ghost3Row;
    protected int ghost3Column;
    protected int ghost4Row;
    protected int ghost4Column;
    private Point2D pacmanInitialLocation;
    private Point2D ghost1InitialLocation;
    private Point2D ghost2InitialLocation;
    private Point2D ghost3InitialLocation;
    private Point2D ghost4InitialLocation;
    protected boolean savedGame;


    public Pacman() {
        if (savedGame)
            loadSavedGame();
        else
            this.startNewGame();
    }


    private void loadSavedGame() {

    }


    //TODO initialize level
    public void startNewGame() {
        gameOver = false;
        energyBombActive = false;
        hasLost = false;

        dotCount = 0;
        energyBombCount = 0;
        rowCount = 0;
        columnCount = 0;
        this.score = 0;
        this.lives = 3;
//        initializeGame(currentFilename);
        initializeGame("src/main/resources/sample/data/maps/level1.txt");
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
        initiateValues();
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
                    initialDotCount++;
                }
                else if (value.equals("B")){
                    thisValue = Cell.ENERGY_BOMB;
                    energyBombCount++;
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
//                System.out.print(value);
            }
//            System.out.println();
            row++;
        }
    }


    public void initiateValues() {
        row = 0;
        pacmanRow = 0;
        pacmanColumn = 0;
        ghost1Row = 0;
        ghost1Column = 0;
        ghost2Row = 0;
        ghost2Column = 0;
        ghost3Row = 0;
        ghost3Column = 0;
        ghost4Row = 0;
        ghost4Column = 0;
    }



    public void setElements() {
        pacmanLocation = new Point2D(pacmanRow, pacmanColumn);
        pacmanInitialLocation = new Point2D(pacmanRow, pacmanColumn);
        pacmanVelocity = new Point2D(0,0);
        ghost1Location = new Point2D(ghost1Row,ghost1Column);
        ghost1InitialLocation = new Point2D(ghost1Row,ghost1Column);
        ghost1Velocity = new Point2D(-1, 0);
        ghost2Location = new Point2D(ghost2Row,ghost2Column);
        ghost2InitialLocation = new Point2D(ghost2Row,ghost2Column);
        ghost2Velocity = new Point2D(-1, 0);
        ghost3Location = new Point2D(ghost3Row,ghost3Column);
        ghost3InitialLocation = new Point2D(ghost3Row,ghost3Column);
        ghost3Velocity = new Point2D(-1, 0);
        ghost4Location = new Point2D(ghost4Row,ghost4Column);
        ghost4InitialLocation = new Point2D(ghost4Row,ghost4Column);
        ghost4Velocity = new Point2D(-1, 0);
//        System.out.println(ghost1Location + "\t" + ghost1Velocity);
//        System.out.println(ghost2Location + "\t\t" + ghost2Velocity);
//        System.out.println(ghost3Location + "\t\t" + ghost3Velocity);
//        System.out.println(ghost4Location + "\t\t" + ghost4Velocity);
        currentDirection = Direction.NONE;
        lastDirection = Direction.NONE;
    }


    public void initializeGame(String fileName) {
        File file = new File(fileName);
        columnCount = 0;
        rowCount = 0 ;
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setDimensions(scanner);
        columnCount = columnCount/rowCount;
        this.grid = new Cell[rowCount][columnCount];
        Scanner newScanner = null;
        try {
            newScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setGrid(newScanner);
        setElements();
    }



    public void startNextRound() {
        if (this.dotCount == 0 && this.energyBombCount == 0) {
            this.lives++;
            rowCount = 0;
            columnCount = 0;
            hasLost = false;
            energyBombActive = false;
            initializeGame("src/main/resources/Sample/Data/Maps/level1.txt");
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
            velocity = setVelocity(lastDirection);
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


    public Point2D[] moveInTheSameColumnTowardsPacman(Point2D location, Point2D velocity) {
        Random random = new Random();
        if (location.getX() > pacmanLocation.getX()) {
            velocity = setVelocity(Direction.UP);
        } else {
            velocity = setVelocity(Direction.DOWN);
        }
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        int column = (int) potentialLocation.getY();
        int row = (int) location.getX();
        while (grid[row][column] == Cell.WALL) {
            int randomNum = random.nextInt(4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        System.out.println("\n-----------------------------------------------------");
        System.out.println(potentialLocation);
        System.out.println(velocity);
        System.out.println("\n-----------------------------------------------------");
        return new Point2D[]{location, velocity};
    }


    public Point2D[] moveInTheSameRowTowardsPacman(Point2D location, Point2D velocity) {
        Random random = new Random();
        if (location.getY() > pacmanLocation.getY()) {
            velocity = setVelocity(Direction.LEFT);
        } else {
            velocity = setVelocity(Direction.RIGHT);
        }
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        int column = (int) potentialLocation.getY();
        int row = (int) location.getX();
        while (grid[row][column] == Cell.WALL) {
            int randomNum = random.nextInt(4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        System.out.println("\n-----------------------------------------------------");
        System.out.println(potentialLocation);
        System.out.println(velocity);
        System.out.println("\n-----------------------------------------------------");
        return  new Point2D[]{location, velocity};
    }


    public Direction intToDirection(int x){
        if (x == 0){
            return Direction.LEFT;
        }
        else if (x == 1){
            return Direction.RIGHT;
        }
        else if(x == 2){
            return Direction.UP;
        }
        else{
            return Direction.DOWN;
        }
    }

    public Point2D[] getAwayInTheSameColumnFromPacman(Point2D location, Point2D velocity) {
        Random random = new Random();
        if (location.getX() > pacmanLocation.getX()) {
            velocity = setVelocity(Direction.DOWN);
        } else {
            velocity = setVelocity(Direction.UP);
        }
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        int column = (int) potentialLocation.getY();
        int row = (int) location.getX();
        while (grid[row][column] == Cell.WALL) {
            int randomNum = random.nextInt(4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        System.out.println("\n-----------------------------------------------------");
        System.out.println(potentialLocation);
        System.out.println(velocity);
        System.out.println("\n-----------------------------------------------------");
        location = potentialLocation;
        return new Point2D[]{location, velocity};
    }


    public Point2D[] getAwayInTheSameRowFromPacman(Point2D location, Point2D velocity) {
        Random random = new Random();
        if (location.getY() > pacmanLocation.getY()) {
            velocity = setVelocity(Direction.RIGHT);
        } else {
            velocity = setVelocity(Direction.LEFT);
        }
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        int column = (int) potentialLocation.getY();
        int row = (int) location.getX();
        while (grid[row][column] == Cell.WALL) {
            int randomNum = random.nextInt(4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        System.out.println("\n-----------------------------------------------------");
        System.out.println(potentialLocation);
        System.out.println(velocity);
        System.out.println("\n-----------------------------------------------------");
        return new Point2D[]{location, velocity};
    }

    public Point2D[] moveGhostRandomly(Point2D location, Point2D velocity) {
        Random random = new Random();
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        while(grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL){
            int randomNum = random.nextInt( 4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        System.out.println("\n-----------------------------------------------------");
        System.out.println(potentialLocation);
        System.out.println(velocity);
        System.out.println("\n-----------------------------------------------------");
        return new Point2D[]{location, velocity};
    }


    public Point2D[] moveTowardsPacman(Point2D location, Point2D velocity) {
        if (location.getY() == pacmanLocation.getY())
            return moveInTheSameColumnTowardsPacman(location, velocity);
        else if (location.getX() == pacmanLocation.getX())
            return moveInTheSameRowTowardsPacman(location, velocity);
        else
            return moveGhostRandomly(location, velocity);
    }


    public Point2D[] getAwayFromPacman(Point2D location, Point2D velocity) {
        if (location.getY() == pacmanLocation.getY())
            return getAwayInTheSameColumnFromPacman(location, velocity);
        else if (location.getX() == pacmanLocation.getX())
            return getAwayInTheSameRowFromPacman(location, velocity);
        else
            return moveGhostRandomly(location, velocity);
    }


    public Point2D[] moveThisGhost(Point2D location, Point2D velocity) {
//        System.out.println(location);
//        System.out.println(velocity);
//        System.out.println("----------------------------------------------------------------");
//        if (!energyBombActive)
//            return moveTowardsPacman(location, velocity);
//        else
//            return getAwayFromPacman(location, velocity);
//        System.out.println(location);
//        System.out.println(velocity);
//        System.out.println("\n\n----------------------------------------------------------------\n\n");
//        return new Point2D[]{location, velocity};
        Random generator = new Random();
        //if the ghost is in the same row or column as PacMan and not in ghostEatingMode,
        // go in his direction until you get to a wall, then go a different direction
        //otherwise, go in a random direction, and if you hit a wall go in a different random direction
        if (!energyBombActive) {
            //check if ghost is in PacMan's column and move towards him
            if (location.getY() == pacmanLocation.getY()) {
                if (location.getX() > pacmanLocation.getX()) {
                    velocity = setVelocity(Direction.UP);
                } else {
                    velocity = setVelocity(Direction.DOWN);
                }
                Point2D potentialLocation = location.add(velocity);
                //if the ghost would go offscreen, wrap around
                potentialLocation = adjustColumnLocation(potentialLocation);
                //generate new random directions until ghost can move without hitting a wall
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = setVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            }
            //check if ghost is in PacMan's row and move towards him
            else if (location.getX() == pacmanLocation.getX()) {
                if (location.getY() > pacmanLocation.getY()) {
                    velocity = setVelocity(Direction.LEFT);
                } else {
                    velocity = setVelocity(Direction.RIGHT);
                }
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = adjustColumnLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = setVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            }
            //move in a consistent random direction until it hits a wall, then choose a new random direction
            else{
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = adjustColumnLocation(potentialLocation);
                while(grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL){
                    int randomNum = generator.nextInt( 4);
                    Direction direction = intToDirection(randomNum);
                    velocity = setVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            }
        }
        //if the ghost is in the same row or column as Pacman and in ghostEatingMode, go in the opposite direction
        // until it hits a wall, then go a different direction
        //otherwise, go in a random direction, and if it hits a wall go in a different random direction
        if (energyBombActive) {
            if (location.getY() == pacmanLocation.getY()) {
                if (location.getX() > pacmanLocation.getX()) {
                    velocity = setVelocity(Direction.DOWN);
                } else {
                    velocity = setVelocity(Direction.UP);
                }
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = adjustColumnLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = setVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            } else if (location.getX() == pacmanLocation.getX()) {
                if (location.getY() > pacmanLocation.getY()) {
                    velocity = setVelocity(Direction.RIGHT);
                } else {
                    velocity = setVelocity(Direction.LEFT);
                }
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = adjustColumnLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = intToDirection(randomNum);
                    velocity = setVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            }
            else{
                Point2D potentialLocation = location.add(velocity);
                potentialLocation = adjustColumnLocation(potentialLocation);
                while(grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL){
                    int randomNum = generator.nextInt( 4);
                    Direction direction = intToDirection(randomNum);
                    velocity = setVelocity(direction);
                    potentialLocation = location.add(velocity);
                }
                location = potentialLocation;
            }
        }
        Point2D[] data = {location, velocity};
        return data;

    }


    // ******Hard Mode******xxx
    public void moveGhosts() {
        Point2D[] ghost1Coordinates = moveThisGhost(ghost1Location,ghost1Velocity);
        Point2D[] ghost2Coordinates = moveThisGhost(ghost2Location,ghost2Velocity);
        Point2D[] ghost3Coordinates = moveThisGhost(ghost3Location,ghost3Velocity);
        Point2D[] ghost4Coordinates = moveThisGhost(ghost4Location,ghost4Velocity);
        ghost1Location = ghost1Coordinates[0];
        ghost1Velocity = ghost1Coordinates[1];
        ghost2Location = ghost2Coordinates[0];
        ghost2Velocity = ghost2Coordinates[1];
        ghost3Location = ghost3Coordinates[0];
        ghost3Velocity = ghost3Coordinates[1];
        ghost4Location = ghost4Coordinates[0];
        ghost4Velocity = ghost4Coordinates[1];
//        ghost1Location = ghost1Location.add(ghost1Velocity);
//        ghost2Location = ghost2Location.add(ghost2Velocity);
//        ghost3Location = ghost3Location.add(ghost3Velocity);
//        ghost4Location = ghost4Location.add(ghost4Velocity);
//        System.out.println(Arrays.toString(Arrays.stream(ghost1Coordinates).toArray()));
//        System.out.println(Arrays.toString(Arrays.stream(ghost2Coordinates).toArray()));
//        System.out.println(Arrays.toString(Arrays.stream(ghost3Coordinates).toArray()));
//        System.out.println(Arrays.toString(Arrays.stream(ghost4Coordinates).toArray()));
    }


    //TODO return all ghost
    public void returnGhostToInitialLocation()  {
        returnThisGhostToInitialLocation(Cell.GHOST_1_HOME);
        returnThisGhostToInitialLocation(Cell.GHOST_2_HOME);
        returnThisGhostToInitialLocation(Cell.GHOST_3_HOME);
        returnThisGhostToInitialLocation(Cell.GHOST_4_HOME);
    }


    public void returnThisGhostToInitialLocation(Cell ghost) {
        if (ghost == Cell.GHOST_1_HOME)
            ghost1Location = new Point2D(ghost1InitialLocation.getX(), ghost1InitialLocation.getY());
        else if (ghost == Cell.GHOST_2_HOME)
            ghost2Location = new Point2D(ghost2InitialLocation.getX(), ghost2InitialLocation.getY());
        else if (ghost == Cell.GHOST_3_HOME)
            ghost3Location = new Point2D(ghost3InitialLocation.getX(), ghost3InitialLocation.getY());
        else if (ghost == Cell.GHOST_4_HOME)
            ghost4Location = new Point2D(ghost4InitialLocation.getX(), ghost4InitialLocation.getY());
    }


    public void eatElement(int row, int column) {
        if (grid[row][column] == Cell.DOT) {
            grid[row][column] = Cell.EMPTY;
            dotCount--;
            score += 5;
            PacmanView.playDotEatingSound(true);
        }
        if (grid[row][column] == Cell.ENERGY_BOMB) {
            grid[row][column] = Cell.EMPTY;
            energyBombCount--;
            energyBombActive = true;
            GameController.setEnergyBombCounter();
        }
    }

    public void eatThisGhost(Cell ghost, Point2D ghostLocation) {
        if (pacmanLocation.equals(ghostLocation)) {
            eatenGhostCount++;
            score += 200 * eatenGhostCount;
            returnThisGhostToInitialLocation(ghost);
        }
    }

    public void eatGhosts() {
        eatThisGhost(Cell.GHOST_1_HOME, ghost1Location);
        eatThisGhost(Cell.GHOST_2_HOME, ghost2Location);
        eatThisGhost(Cell.GHOST_3_HOME, ghost3Location);
        eatThisGhost(Cell.GHOST_4_HOME, ghost4Location);
    }


    public void ghostEatPacman() {
        if (pacmanLocation.equals(ghost1Location) || pacmanLocation.equals(ghost2Location) ||
                pacmanLocation.equals(ghost3Location) || pacmanLocation.equals(ghost4Location)) {
            returnGhostToInitialLocation();
//            gameOver = true;
            if (lives != 0)
                lives--;
            pacmanVelocity = new Point2D(0,0);
            pacmanLocation = new Point2D(pacmanInitialLocation.getX(),pacmanInitialLocation.getY());
        }
    }


    //TODO eating and resetting ghosts
    public void makeMove(Direction direction) {
        this.movePacman(direction);
        eatElement((int) pacmanLocation.getX(), (int) pacmanLocation.getY());
        this.moveGhosts();
        if (energyBombActive)
            eatGhosts();
        else
            ghostEatPacman();
        startNextRound();

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

    public void setCurrentDirection(Direction direction) {
        currentDirection = direction;
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

    public static boolean isGameOver() {
        return gameOver;
    }
    public static boolean haveYouWon() {
        return hasWon;
    }

    public int getScore() {
        return this.score;
    }


    public int getLives() {
        return this.lives;
    }


    public void setGameOver(boolean gameOver) {
        Pacman.gameOver = gameOver;
    }

    public void setSavedGame(boolean savedGame) {
        this.savedGame = savedGame;
    }
}
