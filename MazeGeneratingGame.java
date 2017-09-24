package p1;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;

public class MazeGeneratingGame {

    public static final int MAZESIZE = 20;
    protected static boolean[][] wall;

    public static void main(String args[]) throws IOException{

        generation();

        JFrame frame = new JFrame("Direction");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MazeGraphics());
        frame.pack();
        frame.setVisible(true);

        System.out.println("Hello");
    }

    private static void generation() throws IOException{
        wall = new boolean[MAZESIZE][MAZESIZE];

        // set all wall spaces to true
        for (int i = 0; i < MAZESIZE; i++) {
            for (int j = 0; j < MAZESIZE; j++) {
                wall[i][j] = true;
            }
        }

        wall = generateMainPath(wall);
        wall = generateSidePaths(wall);
    }

    private static boolean[][] generateMainPath(boolean[][] wall) throws IOException {
        boolean[][] mainWall = new boolean[MAZESIZE][MAZESIZE];

        for (int i = 0; i < MAZESIZE; i++) {
            for (int j = 0; j < MAZESIZE; j++) {
                mainWall[i][j] = wall[i][j];
            }
        }

        int currentx = 1;
        int currenty = 1;
        int tempx;
        int tempy;
        int direction;
        Random rand = new Random();
        int lastDirection;
        boolean possible;

        System.out.println("Called generateMainPath");

        mainWall[1][1] = false;

        lastDirection = rand.nextInt(2); // 0-1

        while(currentx != MAZESIZE - 2 || currenty != MAZESIZE - 2) {

            System.out.println("generateMainPath while loop executed");

            tempx = currentx;
            tempy = currenty;

            direction = rand.nextInt(6);

            if (direction == 4 || direction == 5) {
                direction = lastDirection;
            }

            switch (direction) {
                case 0: // right
                    tempx++;
                    // check validity
                    if (tempx != MAZESIZE - 1 && mainWall[tempx][tempy - 1] && mainWall[tempx][tempy + 1] && mainWall[tempx + 1][tempy]) {
                        //check if possible
                        possible = possibilityCheck(mainWall, tempx, tempy);
                        if (possible) {
                            lastDirection = 0;
                            currentx = tempx;
                            currenty = tempy;
                            mainWall[currentx][currenty] = false;
                        }
                    }
                    break;
                case 1: // down
                    tempy++;
                    // check validity
                    if (tempy != MAZESIZE - 1 && mainWall[tempx][tempy + 1] && mainWall[tempx - 1][tempy] && mainWall[tempx + 1][tempy]) {
                        //check if possible
                        possible = possibilityCheck(mainWall, tempx, tempy);
                        if (possible) {
                            lastDirection = 1;
                            currentx = tempx;
                            currenty = tempy;
                            mainWall[currentx][currenty] = false;
                        }
                    }
                    break;
                case 2: // left
                    tempx--;
                    // check validity
                    if (tempx != 0 && mainWall[tempx][tempy - 1] && mainWall[tempx][tempy + 1] && mainWall[tempx - 1][tempy]) {
                        //check if possible
                        possible = possibilityCheck(mainWall, tempx, tempy);
                        if (possible) {
                            lastDirection = 2;
                            currentx = tempx;
                            currenty = tempy;
                            mainWall[currentx][currenty] = false;
                        }
                    }
                    break;
                case 3: // up
                    tempy--;
                    // check validity
                    if (tempy != 0 && mainWall[tempx][tempy - 1] && mainWall[tempx - 1][tempy] && mainWall[tempx + 1][tempy]) {
                        //check if possible
                        possible = possibilityCheck(mainWall, tempx, tempy);
                        if (possible) {
                            lastDirection = 3;
                            currentx = tempx;
                            currenty = tempy;
                            mainWall[currentx][currenty] = false;
                        }
                    }
                    break;

            }
        }



        return mainWall;
    }

    private static boolean possibilityCheck(boolean[][] wall, int currentx, int currenty) {
        boolean[][] possWall = new boolean[MAZESIZE][MAZESIZE];

        for (int i = 0; i < MAZESIZE; i++) {
            for (int j = 0; j < MAZESIZE; j++) {
                possWall[i][j] = wall[i][j];
            }
        }

        boolean possible = false;
        possWall[currentx][currenty] = false;

        System.out.println("possibilityCheck called");

        if (currentx == MAZESIZE - 2 && currenty == MAZESIZE - 2) {
            possible = true;
        }

        // right
        if (!possible) {
            if (currentx + 1 != MAZESIZE - 1) {
                if (possWall[currentx + 1][currenty - 1] && possWall[currentx + 2][currenty] && possWall[currentx + 1][currenty + 1]) {
                    possible = possibilityCheck(possWall, currentx + 1, currenty);
                }
            }
        }

        // down
        if (!possible) {
            if (currenty + 1 != MAZESIZE - 1) {
                if (possWall[currentx + 1][currenty + 1] && possWall[currentx][currenty + 2] && possWall[currentx - 1][currenty + 1]) {
                    possible = possibilityCheck(possWall, currentx, currenty + 1);
                }
            }
        }

        // left
        if (!possible) {
            if (currentx - 1 != 0) {
                if (possWall[currentx - 1][currenty + 1] && possWall[currentx - 2][currenty] && possWall[currentx - 1][currenty - 1]) {
                    possible = possibilityCheck(possWall, currentx - 1, currenty);
                }
            }
        }

        // up
        if (!possible) {
            if (currenty - 1 != 0) {
                if (possWall[currentx - 1][currenty - 1] && possWall[currentx][currenty - 2] && possWall[currentx + 1][currenty - 1]) {
                    possible = possibilityCheck(possWall, currentx, currenty - 1);
                }
            }
        }

        return possible;
    }

    private static boolean[][] generateSidePaths(boolean[][] wall) throws IOException {
        boolean[][] sideWall = new boolean[MAZESIZE][MAZESIZE];

        for (int i = 0; i < MAZESIZE; i++) {
            for (int j = 0; j < MAZESIZE; j++) {
                sideWall[i][j] = wall[i][j];
            }
        }

        Random rand = new Random();
        boolean finished = false;
        int x;
        int y;
        int count;
        boolean found;

        System.out.println("generateSidePaths called");

        while (!finished) {
            x = rand.nextInt(MAZESIZE - 2) + 1;
            y = rand.nextInt(MAZESIZE - 2) + 1;
            count = 0;
            found = false;

            System.out.println("generateSidePaths while loop executed");

            while (!found && count < (MAZESIZE - 2) * (MAZESIZE - 2)) {
                if (sideWall[x][y] && (x != MAZESIZE - 1 && y != MAZESIZE - 2) && (x != MAZESIZE - 2 && y != MAZESIZE - 1) && (
                        sideWall[x + 1][y] &&
                                sideWall[x][y + 1] &&
                                sideWall[x - 1][y] &&
                            !sideWall[x][y - 1] ||
                                sideWall[x + 1][y] &&
                                        sideWall[x][y + 1] &&
                            !sideWall[x - 1][y] &&
                                        sideWall[x][y - 1] ||
                                sideWall[x + 1][y] &&
                            !sideWall[x][y + 1] &&
                                        sideWall[x - 1][y] &&
                                        sideWall[x][y - 1] ||
                        !sideWall[x + 1][y] &&
                                sideWall[x][y + 1] &&
                                sideWall[x - 1][y] &&
                                sideWall[x][y - 1])) {
                    found = true;
                } else {
                    y++;
                    count++;
                }

                if (y == MAZESIZE - 1) {
                    y = 1;
                    x++;
                }
                if (x >= MAZESIZE - 1) {
                    x = 1;
                }

            }

            if (!found) {
                finished = true;
            } else {
                sideWall = generatePath(sideWall, x, y);
            }


        }

        return sideWall;
    }

    private static boolean[][] generatePath(boolean[][] sideWall, int x, int y) throws IOException {
        boolean[][] genWall = new boolean[MAZESIZE][MAZESIZE];

        for (int i = 0; i < MAZESIZE; i++) {
            for (int j = 0; j < MAZESIZE; j++) {
                genWall[i][j] = sideWall[i][j];
            }
        }

        int genCap = (MAZESIZE - 2) * 2 / 4 * (MAZESIZE - 2) * 2 / 4;
        int genCur = 0;
        int direction;
        Random rand = new Random();
        int lastDirection = rand.nextInt(4);
        genWall[x][y] = false;

        System.out.println("generatePath executed");

        while (genCur < genCap) {

            System.out.println("generatePath while loop executed");

            direction = rand.nextInt(6);

            if (direction == 4 || direction == 5) {
                direction = lastDirection;
            }

            switch (direction) {
                case 0: // right
                    // check validity
                    if (x + 1 != MAZESIZE - 1 && genWall[x + 1][y - 1] && genWall[x + 1][y + 1] && genWall[x + 2][y]) {
                        lastDirection = 0;
                        x++;
                        genWall[x][y] = false;
                    }
                    break;
                case 1: // down
                    // check validity
                    if (y + 1 != MAZESIZE - 1 && genWall[x + 1][y + 1] && genWall[x][y + 2] && genWall[x - 1][y + 1]) {
                        lastDirection = 1;
                        y++;
                        genWall[x][y] = false;
                    }
                    break;
                case 2: // left
                    // check validity
                    if (x - 1 != 0 && genWall[x - 1][y + 1] && genWall[x - 2][y] && genWall[x - 1][y - 1]) {
                        lastDirection = 2;
                        x--;
                        genWall[x][y] = false;
                    }
                    break;
                case 3: // up
                    // check validity
                    if (y - 1 != 0 && genWall[x - 1][y - 1] && genWall[x][y - 2] && genWall[x + 1][y - 1]) {
                        lastDirection = 3;
                        y--;
                        genWall[x][y] = false;
                    }
                    break;

            }

            genCur = genCur + rand.nextInt(MAZESIZE / 4) + 1;
        }

        return genWall;
    }
}
