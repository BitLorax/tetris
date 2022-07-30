import java.math.BigInteger;

public class AI {
    public int score(Game game, int linesCleared) {
        BigInteger board = game.getBoard();
        BigInteger columnMask = BigInteger.ONE.shiftLeft(Game.BOARD_HEIGHT).subtract(BigInteger.ONE);
        int[] columns = new int[Game.BOARD_WIDTH];
        for (int i = 0; i < Game.BOARD_WIDTH; i++) {
            columns[i] = board.and(columnMask).shiftRight(i * Game.BOARD_HEIGHT).intValue();
            columnMask = columnMask.shiftLeft(Game.BOARD_HEIGHT);
        }
        int[] heights = new int[Game.BOARD_WIDTH];
        for (int i = 0; i < Game.BOARD_WIDTH; i++) {
            heights[i] = (int)Math.ceil(Math.log(columns[i]) / Math.log(2));
        }

        int totalHeight = 0;
        int numHoles = 0;
        int colHoles = 0;
        int bumpiness = 0;
        int pits = 0;
        for (int i = 0; i < Game.BOARD_WIDTH; i++) {
            totalHeight += heights[i];
            int holes = Integer.bitCount((1 << heights[i]) - 1 - columns[i]);
            numHoles += holes;
            colHoles += colHoles > 0 ? 1 : 0;
            if (i > 0) {
                bumpiness += Math.abs(heights[i] - heights[i - 1]);
            }
            pits += heights[i] == 0 ? 1 : 0;
        }


        return 0;
    }
}
