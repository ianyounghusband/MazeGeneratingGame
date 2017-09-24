package p1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MazeGraphics extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private int x;
    private int y;
    private boolean[][] mazeData = new boolean[MazeGeneratingGame.MAZESIZE][MazeGeneratingGame.MAZESIZE];
    private boolean win = false;

    public MazeGraphics() {
        addKeyListener(new DirectionListener());

        x = 900 / MazeGeneratingGame.MAZESIZE;
        y = x;

        setBackground(Color.white);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        for (int i = 0; i < MazeGeneratingGame.MAZESIZE; i++) {
            for (int j = 0; j < MazeGeneratingGame.MAZESIZE; j++) {
                mazeData[i][j] = MazeGeneratingGame.wall[i][j];
            }
        }

    }

    public void paintComponent(Graphics p) {
        super.paintComponent(p);

        p.setColor(Color.black);
        for (int i = 0; i < MazeGeneratingGame.MAZESIZE; i++) {
            for (int j = 0; j < MazeGeneratingGame.MAZESIZE; j++) {
                if (mazeData[i][j]) {
                    p.fillRect(i * 800 / MazeGeneratingGame.MAZESIZE, j * 800 / MazeGeneratingGame.MAZESIZE, 800 / MazeGeneratingGame.MAZESIZE, 800 / MazeGeneratingGame.MAZESIZE);
                }
            }
        }

        p.setColor(Color.green);
        p.fillRect(800 - 1600 / MazeGeneratingGame.MAZESIZE, 800 - 1600 / MazeGeneratingGame.MAZESIZE, 800 / MazeGeneratingGame.MAZESIZE, 800 / MazeGeneratingGame.MAZESIZE);

        p.setColor(Color.red);
        p.fillRect(x, y, 400 / MazeGeneratingGame.MAZESIZE, 400 / MazeGeneratingGame.MAZESIZE);

        if (x + 400 / MazeGeneratingGame.MAZESIZE >= 800 - 1600 / MazeGeneratingGame.MAZESIZE && y + 400 / MazeGeneratingGame.MAZESIZE >= 800 - 1600 / MazeGeneratingGame.MAZESIZE) {
            win = true;
            p.setColor(Color.white);
            p.fillRect(0, 0, 800, 800);
            p.setColor(Color.black);
            p.drawString("Win! Yay!", 400, 400);
        }
    }

    private class DirectionListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!win) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        y -= 10;
                        if (mazeData[x / (800 / MazeGeneratingGame.MAZESIZE)][y / (800 / MazeGeneratingGame.MAZESIZE)] || mazeData[(x + 400 / MazeGeneratingGame.MAZESIZE) / (800 / MazeGeneratingGame.MAZESIZE)][y / (800 / MazeGeneratingGame.MAZESIZE)]) {
                            y += 10;
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        x -= 10;
                        if (mazeData[x / (800 / MazeGeneratingGame.MAZESIZE)][y / (800 / MazeGeneratingGame.MAZESIZE)] || mazeData[x / (800 / MazeGeneratingGame.MAZESIZE)][(y + 400 / MazeGeneratingGame.MAZESIZE) / (800 / MazeGeneratingGame.MAZESIZE)]) {
                            x += 10;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        y += 10;
                        if (mazeData[(x + 400 / MazeGeneratingGame.MAZESIZE) / (800 / MazeGeneratingGame.MAZESIZE)][(y + 400 / MazeGeneratingGame.MAZESIZE) / (800 / MazeGeneratingGame.MAZESIZE)] || mazeData[x / (800 / MazeGeneratingGame.MAZESIZE)][(y + 400 / MazeGeneratingGame.MAZESIZE) / (800 / MazeGeneratingGame.MAZESIZE)]) {
                            y -= 10;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        x += 10;
                        if (mazeData[(x + 400 / MazeGeneratingGame.MAZESIZE) / (800 / MazeGeneratingGame.MAZESIZE)][(y + 400 / MazeGeneratingGame.MAZESIZE) / (800 / MazeGeneratingGame.MAZESIZE)] || mazeData[(x + 400 / MazeGeneratingGame.MAZESIZE) / (800 / MazeGeneratingGame.MAZESIZE)][y / (800 / MazeGeneratingGame.MAZESIZE)]) {
                            x -= 10;
                        }
                        break;
                }
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
