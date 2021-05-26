package Sample.View;

import Sample.Controller.GameController;
import Sample.Model.Pacman;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PacmanView extends Group {

    public final static double CELL_WIDTH = 20.0;

    @FXML
    private int rowCount;
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
    private Image energyBomdImage;
    private Image dotImage;




    //TODO ghost images
    public PacmanView() {
        this.pacmanRightImage = new Image(getClass().getResourceAsStream("/res/pacmanRight.gif"));
        this.pacmanUpImage = new Image(getClass().getResourceAsStream("/res/pacmanUp.gif"));
        this.pacmanDownImage = new Image(getClass().getResourceAsStream("/res/pacmanDown.gif"));
        this.pacmanLeftImage = new Image(getClass().getResourceAsStream("/res/pacmanLeft.gif"));
        this.ghost1Image = new Image(getClass().getResourceAsStream("/res/redghost.gif"));
        this.ghost2Image = new Image(getClass().getResourceAsStream("/res/ghost2.gif"));
        this.ghost3Image = new Image(getClass().getResourceAsStream("/res/ghost2.gif"));
        this.ghost4Image = new Image(getClass().getResourceAsStream("/res/ghost2.gif"));
        this.blueGhostImage = new Image(getClass().getResourceAsStream("/res/blueghost.gif"));
        this.wallImage = new Image(getClass().getResourceAsStream("/res/wall.png"));
        this.energyBomdImage = new Image(getClass().getResourceAsStream("/res/whitedot.png"));
        this.dotImage = new Image(getClass().getResourceAsStream("/res/smalldot.png"));
    }


    public void initializeGrid() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.cellViews = new ImageView[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double) column * CELL_WIDTH);
                    imageView.setY((double) row * CELL_WIDTH);
                    imageView.setFitHeight((double) CELL_WIDTH);
                    imageView.setFitWidth((double) CELL_WIDTH);
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
            cell.setImage(this.energyBomdImage);
        }
        else if (value == Pacman.Cell.DOT) {
            cell.setImage(this.dotImage);
        }
        else {
            cell.setImage(null);
        }
    }

    public boolean isPacmanInLocation(int row, int column, Pacman pacman) {
        return (row == pacman.getLocation().getX() && column == pacman.getLocation().getY());
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
        return (row == ghostLocation.getX() && column == ghostLocation.getY());
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
            if (isGhostInLocation(row, column, pacman.getGhost1Location()))
                cell.setImage(this.blueGhostImage);
            if (isGhostInLocation(row, column, pacman.getGhost1Location()))
                cell.setImage(this.blueGhostImage);
        }
    }


    public void setGhostsImage(int row, int column, ImageView cell, Pacman pacman) {
        if (isGhostInLocation(row, column, pacman.getGhost1Location()))
            cell.setImage(this.ghost1Image);
        if (isGhostInLocation(row, column, pacman.getGhost2Location()))
            cell.setImage(this.ghost2Image);
        if (isGhostInLocation(row, column, pacman.getGhost1Location()))
            cell.setImage(this.ghost3Image);
        if (isGhostInLocation(row, column, pacman.getGhost1Location()))
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

}
