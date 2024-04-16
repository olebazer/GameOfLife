package gameoflife;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, MouseListener {
    private final int WIDTH = 1200;
    private final int HEIGHT = 900;
    private final int CELL_WIDTH = 15;
    private final int CELL_HEIGHT = 15;
    private final int ROWS = HEIGHT / CELL_HEIGHT;
    private final int COLUMNS = WIDTH / CELL_WIDTH;
    private final int SPEED = 800;
    private final Timer timer = new Timer(SPEED, this);
    private boolean[][] cells = new boolean[ROWS][COLUMNS];
    private boolean running = false;

    GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        g2D.setPaint(new Color(21, 21, 21));

        for (int i = 0; i < COLUMNS; i++) {
            g2D.drawLine(i * CELL_WIDTH, 0, i * CELL_WIDTH, HEIGHT);
        }

        for (int i = 0; i < ROWS; i++) {
            g2D.drawLine(0, i * CELL_HEIGHT, WIDTH, i * CELL_HEIGHT);
        }

        g2D.setPaint(new Color(39, 125, 255));

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == true) {
                    g2D.fillRect(j * CELL_WIDTH, i * CELL_HEIGHT, CELL_WIDTH,
                        CELL_WIDTH);
                }
            }
        }
    }

    private int getLivingNeighbors(int row, int column) {
        int counter = 0;

        if (row > 0 && column > 0){
            if (cells[row - 1][column - 1] == true) {
                counter++;
            }
        }

        if (row > 0) {
            if (cells[row - 1][column] == true) {
                counter++;
            }
        }

        if (row > 0 && column < COLUMNS - 1) {
            if (cells[row - 1][column + 1]) {
                counter++;
            }
        }

        if (column > 0) {
            if (cells[row][column - 1] == true) {
                counter++;
            }
        }

        if (column < COLUMNS - 1) {
            if (cells[row][column + 1] == true) {
                counter++;
            }
        }

        if (row < ROWS - 1 && column > 0) {
            if (cells[row + 1][column - 1] == true) {
                counter++;
            }
        }

        if (row < ROWS - 1) {
            if (cells[row + 1][column] == true) {
                counter++;
            }
        }

        if (row < ROWS - 1 && column < COLUMNS - 1) {
            if (cells[row + 1][column + 1] == true) {
                counter++;
            }
        }

        return counter;
    }

    private void performGameCycle() {
        if (!running) {
            return;
        }

        ArrayList<int[]> livingCells = new ArrayList<>();
        ArrayList<int[]> deadCells = new ArrayList<>();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                int livingNeighbors = getLivingNeighbors(i, j);
                int[] cellPosition = {i, j};

                if (livingNeighbors < 2 || livingNeighbors > 2) {
                    deadCells.add(cellPosition);
                } else {
                    livingCells.add(cellPosition);
                }
            }
        }

        for (int i = 0; i < livingCells.size(); i++) {
            cells[livingCells.get(i)[0]][livingCells.get(i)[1]] = true;
        }

        for (int i = 0; i < deadCells.size(); i++) {
            cells[deadCells.get(i)[0]][deadCells.get(i)[1]] = false;
        }

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (!running) {
                running = true;
            } else {
                running = false;

                for (int i = 0; i < cells.length; i++) {
                    for (int j = 0; j < cells[i].length; j++) {
                        cells[i][j] = false;
                    }
                }
            }
        } else {
            int row = e.getY() / CELL_HEIGHT;
            int column = e.getX() / CELL_WIDTH;
            cells[row][column] = true;
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        performGameCycle();
    }
}
