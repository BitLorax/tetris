import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Run {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.setSize(Display.CELL_SIZE * Game.BOARD_WIDTH, Display.CELL_SIZE * Game.BOARD_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Display display = new Display();
        frame.add(display);

        JLabel status = new JLabel();
        status.setFont(new Font("Monospaced", Font.BOLD, 12));
        status.setBackground(new Color(30, 30, 30));
        status.setForeground(new Color(230, 230, 230));
        status.setOpaque(true);
        status.setText(" Score: 0");
        frame.add(status, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        display.rotateLeft();
                        break;
                    case KeyEvent.VK_DOWN:
                        display.rotateRight();
                        break;
                    case KeyEvent.VK_LEFT:
                        display.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        display.moveRight();
                        break;
                    case KeyEvent.VK_SPACE:
                        display.placePiece();
                        break;
                }
                status.setText(" Score: " + display.getScore());
            }
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });

        new Thread() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        display.moveDown();
                    } catch (InterruptedException e) { }
                }
            }
        }.start();
    }
}
