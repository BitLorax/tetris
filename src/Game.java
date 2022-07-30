import java.math.BigInteger;
import java.util.Scanner;

public class Game {
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;

    private BigInteger board;
    private Piece piece;

    private int score;
    private boolean gameOver;

    public Game() {
        board = BigInteger.ZERO;
        piece = new Piece();
        gameOver = false;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameLoop();
    }

    public void gameLoop() {
        Scanner sc = new Scanner(System.in);
        label:
        while (true) {
            printBoard();
            String inp = sc.nextLine();
            switch (inp) {
                case "a":
                    piece.moveLeft(board);
                    break;
                case "d":
                    piece.moveRight(board);
                    break;
                case "q":
                    piece.rotateLeft(board);
                    break;
                case "e":
                    piece.rotateRight(board);
                    break;
                case " ":
                    placePiece();
                    break;
                default:
                    if (inp.equals("quit")) {
                        break label;
                    }
                    if (!piece.moveDown(board)) {
                        setPiece();
                    }
                    break;
            }
        }
    }

    public void setPiece() {
        board = board.or(piece.overlay());
        score += 1;
        clearRows();
        piece = new Piece();
        if (piece.collides(board)) {
            gameOver = true;
        }
    }

    public void placePiece() {
        int moves = 0;
        while (piece.moveDown(board)) {
            moves++;
        }
        score += moves;
        setPiece();
    }

    private void clearRows() {
        int count = 0;
        int curRow = 0;
        BigInteger rowMask = BigInteger.ZERO;
        BigInteger belowMask = BigInteger.ZERO;
        for (int x = 0; x < BOARD_WIDTH; x++) {
            rowMask = rowMask.add(BigInteger.ONE.shiftLeft(x * BOARD_HEIGHT));
        }
        while (curRow < BOARD_HEIGHT) {
            if (board.and(rowMask).equals(rowMask)) {
                board = board.subtract(rowMask);
                BigInteger below = board.and(belowMask);
                BigInteger aboveMask = BigInteger.ONE.shiftLeft(BOARD_HEIGHT * BOARD_WIDTH).
                        subtract(BigInteger.ONE).subtract(belowMask).subtract(rowMask);
                board = board.and(aboveMask);
                board = board.shiftRight(1);
                board = board.or(below);
                count++;
            } else {
                curRow++;
                belowMask = belowMask.add(rowMask);
                rowMask = rowMask.shiftLeft(1);
            }
        }
        switch (count) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
            default:
                break;
        }
    }

    public void printBoard() {
        BigInteger overlay = piece.overlay();
        BigInteger display = board.or(overlay);
        String s = String.format("%" + (BOARD_WIDTH * BOARD_HEIGHT) + "s",
                display.toString(2)).replaceAll(" ", "0");
        for (int row = BOARD_HEIGHT - 1; row >= 0; row--) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                int idx = row + col * BOARD_HEIGHT;
                System.out.print(s.charAt(s.length() - 1 - idx) == '1' ? "#" : ".");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean[][] toArray() {
        boolean[][] ret = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
        BigInteger overlay = piece.overlay();
        BigInteger display = board.or(overlay);
        String s = String.format("%" + (BOARD_WIDTH * BOARD_HEIGHT) + "s",
                display.toString(2)).replaceAll(" ", "0");
        for (int row = BOARD_HEIGHT - 1; row >= 0; row--) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                int idx = row + col * BOARD_HEIGHT;
                ret[col][row] = s.charAt(s.length() - 1 - idx) == '1';
            }
        }
        return ret;
    }

    public Piece getPiece() {
        return piece;
    }

    public BigInteger getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
