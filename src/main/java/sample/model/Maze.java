package sample.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Maze {

    static ArrayList<User> mazes = new ArrayList<>();
    private int width;
    private int length;
    public int[][] map;
    private int row;
    private int column;


    public Maze() {
        this.width = 10;
        this.length = 9;
        row = 2 * width + 1;
        column = 2 * length + 1;
        map = new int[row][column];
        init();
    }

    public void init() {
        Random random = new Random();
        int direction;
        for (int i = 0; i < 2 * width + 1; ++i) {
            for (int j = 0; j < 2 * length + 1; ++j) {
                if (i % 2 == 1 && j % 2 == 1) map[i][j] = 2;
                else map[i][j] = 0;
            }
        }
        for (int i = 2 * width - 1; i > 0; --i) {
            for (int j = 2 * length - 1; j > 0; --j) {
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
        for (int i = 2 * width - 1; i > 0; --i) {
            for (int j = 2 * length - 1; j > 0; --j) {
                if (map[i][j] == 2) map[i][j] = 1;
            }
        }

        int count1 = 0, count2 = length * width / 10;
        for (int i = 2 * width - 1; i > 0; --i) {
            for (int j = 2 * length - 1; j > 0; --j) {
                if (j < 2 * count2 && map[i][j] == 0 && count1 == 0 && (2 * length - 1) % 2 == 1 && map[i - 1][j] == 1 && map[i + 1][j] == 1) {
                    map[i][j] = 1;
                    count1 = 1;
                    count2--;

                }
            }
            count1 = 0;
        }
        map[2 * width - 1][2 * length - 1] = 3;
        map[2 * width - 1][1] = 3;
        map[1][2 * length - 1] = 3;
        map[1][width] = 3;

        //printMaze(map);
    }

    public String[][] printMaze() {
        int[][] mazeMap = this.map;
        String[][] mazeString = new String[21][19];

        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 19; j++) {
                switch (mazeMap[i][j]) {
                    case 0:
                        mazeString[i][j] = "W";
                        break;
                    case 1:
                        mazeString[i][j] = "S";
                        break;
                    default:
                        mazeString[i][j] = "3";
                }
            }
        }

        Random random = new Random();
        boolean pacmanHasBeenSet = false;
        while (!pacmanHasBeenSet) {
            int i = random.nextInt(20);
            int j = random.nextInt(18);
            if (mazeMap[i][j] == 1) {
                mazeString[i][j] = "P";
                pacmanHasBeenSet = true;
            }
        }

        int counter = 1;
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 19; j++) {
                if (mazeMap[i][j] == 3) {
                    mazeString[i][j] = "S";
                    counter++;
                }
                if (counter > 4)
                    break;
            }
        }

        mazeString[1][1] = "1";
        mazeString[1][17] = "2";
        mazeString[19][1] = "3";
        mazeString[19][17] = "4";

        counter = 1;
        while(counter <= 4) {
            int i = random.nextInt(20);
            int j = random.nextInt(18);
            if (mazeMap[i][j] == 1) {
                mazeString[i][j] = "B";
                counter++;
            }
        }


        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 18; j++) {
                System.out.print(mazeString[i][j] + " ");
            }
            System.out.println(mazeString[i][18]);
        }

        return mazeString;
    }

    public static void main(String[] args) {
        Maze map = new Maze();
        map.printMaze();
    }

}
