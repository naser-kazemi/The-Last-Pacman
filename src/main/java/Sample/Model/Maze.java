package Sample.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Maze {

    static ArrayList<Maze> mazes = new ArrayList<>();
    private final int height;
    private final int width;
    public int[][] map;
    private final int row;
    private final int column;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        row = 2 * height + 1;
        column = 2 * width + 1;
        map = new int[row][column];
        this.init();
        mazes.add(this);
    }

    public void init() {
        Random random = new Random();
        int direction;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                if (i % 2 == 1 && j % 2 == 1) map[i][j] = 2;
                else map[i][j] = 0;
            }
        }
        for (int i = row - 2; i > 0; --i) {
            for (int j = column - 2; j > 0; --j) {
                if (map[i][j] == 2) {
                    direction = random.nextInt(2);
                    if (j == 1) direction = 1;
                    if (i == 1) direction = 0;
                    if (i == 1 && j == 1) break;
                    if (direction == 1) map[i - 1][j] = 1;
                    else map[i][j - 1] = 1;
                }
            }
        }
        for (int i = 2 * height - 1; i > 0; --i) {
            for (int j = 2 * width - 1; j > 0; --j) {
                if (map[i][j] == 2) map[i][j] = 1;
            }
        }

        int count1 = 0, count2 = width * height / 10;
        for (int i = 2 * height - 1; i > 0; --i) {
            for (int j = 2 * width - 1; j > 0; --j) {
                if (j < 2 * count2 && map[i][j] == 0 && count1 == 0 && (2 * width - 1) % 2 == 1 && map[i - 1][j] == 1 && map[i + 1][j] == 1) {
                    map[i][j] = 1;
                    count1 = 1;
                    count2--;

                }
            }
            count1 = 0;
        }
        map[2 * height - 1][2 * width - 1] = 3;
        map[2 * height - 1][1] = 3;
        map[1][2 * width - 1] = 3;
        map[1][height] = 3;

        printMaze(map);
    }


    private void putEnergyBombs() {
        Random random = new Random();
        for (int s = 1; s <= (height + width) / 10; s++) {
            int i = random.nextInt();
            int j = random.nextInt();
            if (map[i][j] == 1) {
                map[i][j] = 4;
            }
        }
    }

    public void printMaze(int[][] mazeMap) {
        System.out.println(Arrays.deepToString(mazeMap).
                replace("], ", "]\n").replace("[[", "[").
                replace("]]", "]").replace("[", "").
                replace("]", "").replace(", ", ""));
        System.out.println();

    }


    public static void main(String[] args) {
        Maze maze = new Maze(10, 10);
    }
}
