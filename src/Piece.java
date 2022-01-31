import java.util.HashMap;
import java.util.Map;

import java.math.BigInteger;
import java.util.Random;

public class Piece {
    char type;
    int len, rot;
    int x, y; // bottom-left corner of bounding box

    private final static char[] types = {'I', 'J', 'L', 'O', 'S', 'T', 'Z'};

    private static Map<String, Integer> pieceMask = new HashMap<String, Integer>() {{
            put("I0", 17476);
            put("I1", 3840);
            put("I2", 8738);
            put("I3", 240);

            put("J0", 150);
            put("J1", 312);
            put("J2", 210);
            put("J3", 57);

            put("L0", 402);
            put("L1", 120);
            put("L2", 147);
            put("L3", 60);

            put("O0", 15);
            put("O1", 15);
            put("O2", 15);
            put("O3", 15);

            put("S0", 306);
            put("S1", 240);
            put("S2", 153);
            put("S3", 30);

            put("T0", 178);
            put("T1", 184);
            put("T2", 154);
            put("T3", 58);

            put("Z0", 180);
            put("Z1", 408);
            put("Z2", 90);
            put("Z3", 51);
        }};

    public Piece() {
        type = types[new Random().nextInt(types.length)];
        if (type == 'I') {
            len = 4;
        } else if (type == 'O') {
            len = 2;
        } else {
            len = 3;
        }
        rot = 0;
        x = Game.BOARD_WIDTH / 2 - (len + 1) / 2;
        y = Game.BOARD_HEIGHT - len;
    }

    public boolean moveLeft(BigInteger pieces) {
        if (offLeft()) {
            return false;
        }
        x--;
        if (collides(pieces)) {
            x++;
            return false;
        }
        return true;
    }

    public boolean moveRight(BigInteger pieces) {
        if (offRight()) {
            return false;
        }
        x++;
        if (collides(pieces)) {
            x--;
            return false;
        }
        return true;
    }

    public boolean moveDown(BigInteger pieces) {
        if (offBottom()) {
            return false;
        }
        y--;
        if (collides(pieces)) {
            y++;
            return false;
        }
        return true;
    }

    public boolean rotateLeft(BigInteger pieces) {
        rot = (rot - 1 + 4) % 4;
        if (offRight() || offLeft() || offBottom() || collides(pieces)) {
            rot = (rot + 1) % 4;
            return false;
        }
        return true;
    }

    public boolean rotateRight(BigInteger pieces) {
        rot = (rot + 1) % 4;
        if (offRight() || offLeft() || offBottom() || collides(pieces)) {
            rot = (rot - 1 + 4) % 4;
            return false;
        }
        return true;
    }

    private boolean offLeft() {
        int leftCol = ((1 << (len * (-x + 1))) - 1) - ((1 << (len * (-x))) - 1);
        if (x > 0) {
            return false;
        }
        return (getShape() & leftCol) != 0;
    }

    private boolean offRight() {
        int rightCol = ((1 << (len * (Game.BOARD_WIDTH - x))) - 1) - ((1 << (len * (Game.BOARD_WIDTH - x - 1))) - 1);
        if ((x + len) < Game.BOARD_WIDTH) {
            return false;
        }
        return (getShape() & rightCol) != 0;
    }

    private boolean offBottom() {
        int bottomRow = 0;
        for (int i = -y; i < len * len; i += len) {
            bottomRow += (1 << i);
        }
        if (y > 0) {
            return false;
        }
        return (getShape() & bottomRow) != 0;
    }

    private int getShape() {
        return pieceMask.get(type + Integer.toString(rot));
    }

    public BigInteger overlay() {
        int val = pieceMask.get(type + Integer.toString(rot));
        BigInteger ret = BigInteger.ZERO;
        int i = 0;
        while (val > 0) {
            if ((val & (1 << i)) > 0) {
                int px = i / len;
                int py = i % len;
                int nx = px + x;
                int ny = py + y;
                ret = ret.add(BigInteger.ONE.shiftLeft(ny + Game.BOARD_HEIGHT * nx));
                val -= (1 << i);
            }
            i++;
        }
        return ret;
    }

    public boolean collides(BigInteger pieces) {
        BigInteger curPiece = overlay();
        return (curPiece.and(pieces).signum() != 0);
    }
}
