import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    public final static int CELL_SIZE = 20;
    private static Game game;

    private boolean paused;

    public Display() {
        game = new Game();
        this.setBackground(new Color(30, 30, 30));
        paused = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean[][] grid = game.toArray();
        g.setColor(new Color(60, 60, 60));
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                g.fillRect(CELL_SIZE * x, CELL_SIZE * y, 2, 2);
            }
        }
        g.setColor(new Color(230, 230, 230));
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y]) {
                    g.fillRect(CELL_SIZE * x, CELL_SIZE * (grid[0].length - 1 - y),
                            CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CELL_SIZE * Game.BOARD_WIDTH, CELL_SIZE * Game.BOARD_HEIGHT);
    }

    public void rotateLeft() {
        if (paused) {
            return;
        }
        game.getPiece().rotateLeft(game.getBoard());
        repaint();
    }

    public void rotateRight() {
        if (paused) {
            return;
        }
        game.getPiece().rotateRight(game.getBoard());
        repaint();
    }

    public void moveLeft() {
        if (paused) {
            return;
        }
        game.getPiece().moveLeft(game.getBoard());
        repaint();
    }

    public void moveRight() {
        if (paused) {
            return;
        }
        game.getPiece().moveRight(game.getBoard());
        repaint();
    }

    public void moveDown() {
        if (paused) {
            return;
        }
        if (!game.getPiece().moveDown(game.getBoard())) {
            game.placePiece();
        }
        repaint();
    }

    public void placePiece() {
        if (paused) {
            return;
        }
        game.placePiece();
        repaint();
    }

    public int getScore() {
        return game.getScore();
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isGameOver() {
        return game.isGameOver();
    }
}
