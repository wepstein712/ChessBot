import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;

import java.util.Dictionary;
import java.util.Hashtable;

public class PieceValues {
    private static Dictionary<PieceType, Integer> pieceValues;

    static  {
        pieceValues = new Hashtable<>();
        pieceValues.put(PieceType.PAWN, 1);
        pieceValues.put(PieceType.BISHOP, 3);
        pieceValues.put(PieceType.KNIGHT, 3);
        pieceValues.put(PieceType.ROOK, 5);
        pieceValues.put(PieceType.QUEEN, 9);
        pieceValues.put(PieceType.KING, 1000);

    }

    public static Integer getValue(PieceType pt) {
        return pieceValues.get(pt);
    }

}
