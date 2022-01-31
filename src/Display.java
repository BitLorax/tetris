import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    public final static int CELL_SIZE = 20;
    private static Game game;

    public Display() {
        game = new Game();
        this.setBackground(new Color(30, 30, 30));
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
                    g.fillRect(CELL_SIZE * x, CELL_SIZE * (grid[0].length - 1 - y), CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CELL_SIZE * Game.BOARD_WIDTH, CELL_SIZE * Game.BOARD_HEIGHT);
    }

    public void rotateLeft() {
        game.getPiece().rotateLeft(game.getBoard());
        repaint();
    }

    public void rotateRight() {
        game.getPiece().rotateRight(game.getBoard());
        repaint();
    }

    public void moveLeft() {
        game.getPiece().moveLeft(game.getBoard());
        repaint();
    }

    public void moveRight() {
        game.getPiece().moveRight(game.getBoard());
        repaint();
    }

    public void moveDown() {
        if (!game.getPiece().moveDown(game.getBoard())) {
            game.placePiece();
        }
        repaint();
    }

    public void placePiece() {
        game.placePiece();
        repaint();
    }

    public int getScore() {
        return game.getScore();
    }
}
