import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.MoveBackup;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Utils {
    public static ArrayList<Square> rank1 = new ArrayList<>() {{
        add(Square.A1);
        add(Square.B1);
        add(Square.C1);
        add(Square.D1);
        add(Square.E1);
        add(Square.F1);
        add(Square.G1);
        add(Square.H1);
    }};

    public static ArrayList<Square> rank2  = new ArrayList<>() {{
        add(Square.A2);
        add(Square.B2);
        add(Square.C2);
        add(Square.D2);
        add(Square.E2);
        add(Square.F2);
        add(Square.G2);
        add(Square.H2);
    }};

    public static ArrayList<Square> rank3 = new ArrayList<>() {{
        add(Square.A3);
        add(Square.B3);
        add(Square.C3);
        add(Square.D3);
        add(Square.E3);
        add(Square.F3);
        add(Square.G3);
        add(Square.H3);
    }};
    public static ArrayList<Square> rank4 = new ArrayList<>() {{
        add(Square.A4);
        add(Square.B4);
        add(Square.C4);
        add(Square.D4);
        add(Square.E4);
        add(Square.F4);
        add(Square.G4);
        add(Square.H4);
    }};
    public static ArrayList<Square> rank5 = new ArrayList<>() {{
        add(Square.A5);
        add(Square.B5);
        add(Square.C5);
        add(Square.D5);
        add(Square.E5);
        add(Square.F5);
        add(Square.G5);
        add(Square.H5);
    }};
    public static ArrayList<Square> rank6 = new ArrayList<>() {{
        add(Square.A6);
        add(Square.B6);
        add(Square.C6);
        add(Square.D6);
        add(Square.E6);
        add(Square.F6);
        add(Square.G6);
        add(Square.H6);
    }};
    public static ArrayList<Square> rank7 = new ArrayList<>() {{
        add(Square.A7);
        add(Square.B7);
        add(Square.C7);
        add(Square.D7);
        add(Square.E7);
        add(Square.F7);
        add(Square.G7);
        add(Square.H7);
    }};
    public static ArrayList<Square> rank8 = new ArrayList<>() {{
        add(Square.A8);
        add(Square.B8);
        add(Square.C8);
        add(Square.D8);
        add(Square.E8);
        add(Square.F8);
        add(Square.G8);
        add(Square.H8);
    }};
    public static ArrayList<Square> fileA = new ArrayList<>() {{
        add(Square.A1);
        add(Square.A2);
        add(Square.A3);
        add(Square.A4);
        add(Square.A5);
        add(Square.A6);
        add(Square.A7);
        add(Square.A8);
    }};
    public static ArrayList<Square> fileB = new ArrayList<>() {{
        add(Square.B1);
        add(Square.B2);
        add(Square.B3);
        add(Square.B4);
        add(Square.B5);
        add(Square.B6);
        add(Square.B7);
        add(Square.B8);
    }};
    public static ArrayList<Square> fileC = new ArrayList<>() {{
        add(Square.C1);
        add(Square.C2);
        add(Square.C3);
        add(Square.C4);
        add(Square.C5);
        add(Square.C6);
        add(Square.C7);
        add(Square.C8);

    }};
    public static ArrayList<Square> fileD = new ArrayList<>() {{
        add(Square.D1);
        add(Square.D2);
        add(Square.D3);
        add(Square.D4);
        add(Square.D5);
        add(Square.D6);
        add(Square.D7);
        add(Square.D8);
    }};
    public static ArrayList<Square> fileE = new ArrayList<>() {{
        add(Square.E1);
        add(Square.E2);
        add(Square.E3);
        add(Square.E4);
        add(Square.E5);
        add(Square.E6);
        add(Square.E7);
        add(Square.E8);
    }};
    public static ArrayList<Square> fileF = new ArrayList<>() {{
        add(Square.F1);
        add(Square.F2);
        add(Square.F3);
        add(Square.F4);
        add(Square.F5);
        add(Square.F6);
        add(Square.F7);
        add(Square.F8);
    }};
    public static  ArrayList<Square> fileG = new ArrayList<>() {{
        add(Square.G1);
        add(Square.G2);
        add(Square.G3);
        add(Square.G4);
        add(Square.G5);
        add(Square.G6);
        add(Square.G7);
        add(Square.G8);
    }};
    public static ArrayList<Square> fileH = new ArrayList<>() {{
        add(Square.H1);
        add(Square.H2);
        add(Square.H3);
        add(Square.H4);
        add(Square.H5);
        add(Square.H6);
        add(Square.H7);
        add(Square.H8);

    }};

    public static boolean checkDraw(Board b) {
        LinkedList<MoveBackup> moves = b.getBackup();
        int count = 3;
        Move lastUs = null;
        Move lastThem = null;
        MoveBackup moveBackup;
        boolean isUs = true;
        Iterator<MoveBackup> recent = moves.descendingIterator();
        while (recent.hasNext() && count > 0) {
            moveBackup = recent.next();
            if (isUs) {
                if (lastUs == null) {
                    lastUs = moveBackup.getMove();
                } else {
                    if (lastUs != moveBackup.getMove()) {
                        return false;
                    }
                }
            } else {
                if (lastThem == null) {
                    lastThem = moveBackup.getMove();
                } else {
                    if (lastThem != moveBackup.getMove()) {
                        return false;
                    }
                }
            }
            count --;
        }
        return count <= 1;
    }

}
