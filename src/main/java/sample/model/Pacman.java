package sample.model;

import sample.controller.GameController;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import sample.view.PacmanView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Pacman {


    public enum Cell {
        EMPTY, DOT, ENERGY_BOMB, WALL, GHOST_1_HOME, GHOST_2_HOME, GHOST_3_HOME, GHOST_4_HOME, PACMAN_HOME
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    public enum Mode {
        EASY, HARD
    }

    private static Direction lastDirection;
    private static Direction currentDirection;
    private static boolean energyBombActive;
    private static boolean gameOver;
    private static boolean hasLost;
    private static boolean hasWon;
    public static int selectedLives = 3;
    public static boolean isMapDefault = true;
    public static String mapFileName;
    public static Mode staticMode = Mode.HARD;



    protected Mode mode;
    protected int thisPacmanSelectedLives;
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


    public void startNewGame() {
        this.mode = staticMode;
        gameOver = false;
        energyBombActive = false;
        hasLost = false;

        dotCount = 0;
        energyBombCount = 0;
        rowCount = 0;
        columnCount = 0;
        this.score = 0;
        thisPacmanSelectedLives = selectedLives;
        this.lives = thisPacmanSelectedLives;
        if (isMapDefault)
            mapFileName = "src/main/resources/sample/data/maps/level1.txt";
        initializeGame(mapFileName);
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
            }
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
            if (isMapDefault)
                mapFileName = "src/main/resources/Sample/Data/maps/level1.txt";
            initializeGame(mapFileName);
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


    public Point2D[] moveInTheSameColumnTowardsPacman(Point2D location, Point2D velocity, Random random) {
        if (location.getX() > pacmanLocation.getX()) {
            velocity = setVelocity(Direction.UP);
        } else {
            velocity = setVelocity(Direction.DOWN);
        }
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL) {
            int randomNum = random.nextInt(4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        return new Point2D[]{location, velocity};
    }


    public Point2D[] moveInTheSameRowTowardsPacman(Point2D location, Point2D velocity, Random random) {
        if (location.getY() > pacmanLocation.getY()) {
            velocity = setVelocity(Direction.LEFT);
        } else {
            velocity = setVelocity(Direction.RIGHT);
        }
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL) {
            int randomNum = random.nextInt(4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
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

    public Point2D[] getAwayInTheSameColumnFromPacman(Point2D location, Point2D velocity, Random random) {
        if (location.getX() > pacmanLocation.getX()) {
            velocity = setVelocity(Direction.DOWN);
        } else {
            velocity = setVelocity(Direction.UP);
        }
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL) {
            int randomNum = random.nextInt(4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        return new Point2D[]{location, velocity};
    }


    public Point2D[] getAwayInTheSameRowFromPacman(Point2D location, Point2D velocity, Random random) {
        if (location.getY() > pacmanLocation.getY()) {
            velocity = setVelocity(Direction.RIGHT);
        } else {
            velocity = setVelocity(Direction.LEFT);
        }
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL) {
            int randomNum = random.nextInt(4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        return new Point2D[]{location, velocity};
    }

    public Point2D[] moveGhostRandomly(Point2D location, Point2D velocity, Random random) {
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        while(grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL){
            int randomNum = random.nextInt( 4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        return new Point2D[]{location, velocity};
    }


    public Point2D[] moveTowardsPacman(Point2D location, Point2D velocity, Random random) {
        if (location.getY() == pacmanLocation.getY())
            return moveInTheSameColumnTowardsPacman(location, velocity, random);
        else if (location.getX() == pacmanLocation.getX())
            return moveInTheSameRowTowardsPacman(location, velocity, random);
        else
            return moveGhostRandomly(location, velocity, random);
    }


    public Point2D[] getAwayFromPacman(Point2D location, Point2D velocity, Random random) {
        if (location.getY() == pacmanLocation.getY())
            return getAwayInTheSameColumnFromPacman(location, velocity, random);
        else if (location.getX() == pacmanLocation.getX())
            return getAwayInTheSameRowFromPacman(location, velocity, random);
        else
            return moveGhostRandomly(location, velocity, random);
    }



    public Point2D[] moveThisGhostEasyMode(Point2D location, Point2D velocity) {
        Random generator = new Random();
        Point2D potentialLocation = location.add(velocity);
        potentialLocation = adjustColumnLocation(potentialLocation);
        while(grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == Cell.WALL){
            int randomNum = generator.nextInt( 4);
            Direction direction = intToDirection(randomNum);
            velocity = setVelocity(direction);
            potentialLocation = location.add(velocity);
        }
        location = potentialLocation;
        Point2D[] data = {location, velocity};
        return data;
    }




    public Point2D[] moveThisGhostHardMode(Point2D location, Point2D velocity) {
        Random generator = new Random();
        if (!energyBombActive)
            return moveTowardsPacman(location, velocity, generator);
        else
            return getAwayFromPacman(location, velocity, generator);
    }


    public void moveGhostsEasyMode() {
        Point2D[] ghost1Coordinates = moveThisGhostEasyMode(ghost1Location, ghost1Velocity);
        Point2D[] ghost2Coordinates = moveThisGhostEasyMode(ghost2Location, ghost2Velocity);
        Point2D[] ghost3Coordinates = moveThisGhostEasyMode(ghost3Location, ghost3Velocity);
        Point2D[] ghost4Coordinates = moveThisGhostEasyMode(ghost4Location, ghost4Velocity);
        ghost1Location = ghost1Coordinates[0];
        ghost1Velocity = ghost1Coordinates[1];
        ghost2Location = ghost2Coordinates[0];
        ghost2Velocity = ghost2Coordinates[1];
        ghost3Location = ghost3Coordinates[0];
        ghost3Velocity = ghost3Coordinates[1];
        ghost4Location = ghost4Coordinates[0];
        ghost4Velocity = ghost4Coordinates[1];
    }



    public void moveGhostsHardMode() {
        Point2D[] ghost1Coordinates = moveThisGhostHardMode(ghost1Location, ghost1Velocity);
        Point2D[] ghost2Coordinates = moveThisGhostHardMode(ghost2Location, ghost2Velocity);
        Point2D[] ghost3Coordinates = moveThisGhostHardMode(ghost3Location, ghost3Velocity);
        Point2D[] ghost4Coordinates = moveThisGhostHardMode(ghost4Location, ghost4Velocity);
        ghost1Location = ghost1Coordinates[0];
        ghost1Velocity = ghost1Coordinates[1];
        ghost2Location = ghost2Coordinates[0];
        ghost2Velocity = ghost2Coordinates[1];
        ghost3Location = ghost3Coordinates[0];
        ghost3Velocity = ghost3Coordinates[1];
        ghost4Location = ghost4Coordinates[0];
        ghost4Velocity = ghost4Coordinates[1];
    }




    public void returnGhostToInitialLocation()  {
        returnThisGhostToInitialLocation(Cell.GHOST_1_HOME);
        returnThisGhostToInitialLocation(Cell.GHOST_2_HOME);
        returnThisGhostToInitialLocation(Cell.GHOST_3_HOME);
        returnThisGhostToInitialLocation(Cell.GHOST_4_HOME);
    }


    public void returnThisGhostToInitialLocation(Cell ghost) {
        if (ghost == Cell.GHOST_1_HOME) {
            ghost1Location = new Point2D(ghost1InitialLocation.getX(), ghost1InitialLocation.getY());
        }
        else if (ghost == Cell.GHOST_2_HOME) {
            ghost2Location = new Point2D(ghost2InitialLocation.getX(), ghost2InitialLocation.getY());
        }
        else if (ghost == Cell.GHOST_3_HOME) {
            ghost3Location = new Point2D(ghost3InitialLocation.getX(), ghost3InitialLocation.getY());
        }
        else if (ghost == Cell.GHOST_4_HOME) {
            ghost4Location = new Point2D(ghost4InitialLocation.getX(), ghost4InitialLocation.getY());
        }
    }


    public void eatElement(int row, int column) {
        if (grid[row][column] == Cell.DOT) {
            grid[row][column] = Cell.EMPTY;
            dotCount--;
            score += 5;
            PacmanView.playDotEatingSound();
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
            PacmanView.playEatingGhostSound();
        }
    }

    public void eatGhosts() {
        eatThisGhost(Cell.GHOST_1_HOME, ghost1Location);
        eatThisGhost(Cell.GHOST_2_HOME, ghost2Location);
        eatThisGhost(Cell.GHOST_3_HOME, ghost3Location);
        eatThisGhost(Cell.GHOST_4_HOME, ghost4Location);
    }


    public void ghostEatPacman() {
        boolean canBeEatenByGhost1 = pacmanLocation.equals(ghost1Location);
        boolean canBeEatenByGhost2 = pacmanLocation.equals(ghost2Location);
        boolean canBeEatenByGhost3 = pacmanLocation.equals(ghost3Location);
        boolean canBeEatenByGhost4 = pacmanLocation.equals(ghost4Location);
        if (canBeEatenByGhost1 || canBeEatenByGhost2 || canBeEatenByGhost3 || canBeEatenByGhost4) {
            PacmanView.themeSound.setVolume(0.3);
            PacmanView.playPacmanDiesSound();
            PacmanView.themeSound.setVolume(2.5);
            returnGhostToInitialLocation();
            if (lives != 0)
                lives--;
            pacmanVelocity = new Point2D(0,0);
            pacmanLocation = new Point2D(pacmanInitialLocation.getX(),pacmanInitialLocation.getY());

        }
    }




    public void makeMove(Direction direction) {
        this.movePacman(direction);
        eatElement((int) pacmanLocation.getX(), (int) pacmanLocation.getY());
        if (mode == Mode.EASY)
            this.moveGhostsEasyMode();
        else
            this.moveGhostsHardMode();
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
