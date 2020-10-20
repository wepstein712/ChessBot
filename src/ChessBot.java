import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;

public interface ChessBot {
    public void setHistorian(GameHistory historian);
    public Move getNextMove(Board b) throws MoveGeneratorException;

}
