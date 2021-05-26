package Sample.Model;

public class Ghost extends Thread{

    protected final int initialRow;
    protected final int initialColumn;
    protected Map startMap;
    protected Pacman.Cell[][] cells;

    public Ghost(int row, int column ,Map startMap ,String ghostGraphic) {
        this.initialRow = row;
        this.initialColumn = column;
        this.startMap = startMap;
        cells = startMap.getCells();
    }

}
