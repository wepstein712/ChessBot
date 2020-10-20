import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;

import java.util.HashMap;

public class QLearningAgent implements ChessBot {
    HashMap<SimplifiedBoardState, Double> qValues;
    GameHistory historian;

    QLearningAgent() {
        this.qValues = new HashMap<>();
    }


    @Override
    public void setHistorian(GameHistory historian) {
        this.historian = historian;
    }

    @Override
    public Move getNextMove(Board b) throws MoveGeneratorException {
        return null;
    }
    
}
