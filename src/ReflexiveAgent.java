import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;

import java.util.*;


public class ReflexiveAgent implements ChessBot {

    Side s;
    int depth;
    Random r;
    GameHistory historian = null;
    double lookSize = 1;
    boolean isCastled = false;

    ReflexiveAgent(Side s) {
        this.s = s;
        this.depth = 1;
        r = new Random();
    }
    ReflexiveAgent(Side s, int depth) {
        this.s = s;
        this.depth = depth;
        r = new Random();

    }

    public void setHistorian(GameHistory h) {
        this.historian = h;
    }

    public Move getNextMove(Board b) throws MoveGeneratorException {

        Move m =  minimax(b, this.depth, (this.s == Side.WHITE), true, -1000000, 1000000);
        if (b.getPiece(m.getFrom()).getPieceType() == PieceType.KING) {
            if (b.getContext().isKingSideCastle(m) || b.getContext().isQueenSideCastle(m)) {
                this.isCastled = true;
            }
        }
        return m;
    }

    private Move minimax(Board b, int depth, boolean isWhite, boolean shouldMax, double alpha, double beta) throws MoveGeneratorException {
        MoveList moves = MoveGenerator.generateLegalMoves(b);
        if (moves.size() == 1) {
            return moves.getFirst();
        }
        if (this.lookSize == 0) {
            this.lookSize = moves.size();
        } else {
            if (this.lookSize > 4 * moves.size()) {
                depth++;
            }
        }
        double bestVal = -100000, moveVal;
        Move bestMove = null;
        for (Move m : moves) {
            b.doMove(m);
            moveVal = minimaxHelp(b, depth - 1, !isWhite, !shouldMax, alpha, beta);
            if (moveVal > bestVal) {
                bestVal = moveVal;
                bestMove = m;
            }
            alpha = Math.max(moveVal, bestVal);
            b.undoMove();
            if (alpha >= beta) {
                break;
            }
        }
        System.out.print("Best val : " + bestVal);
        return bestMove;

    }

    private double minimaxHelp(Board b, int depth, boolean isWhite, boolean shouldMax, double alpha, double beta) {
        MoveList moves;
        if (depth == 0) {
            return evalBoardState(b, (isWhite) ? Side.WHITE : Side.BLACK);
        } else {
            try {
                 moves = MoveGenerator.generateLegalMoves(b);
                if (moves.isEmpty()) {
                    return evalBoardState(b, (isWhite) ? Side.WHITE : Side.BLACK);
                }
            } catch( Exception e) {
                return evalBoardState(b, (isWhite) ? Side.WHITE : Side.BLACK);
            }
        }
        double val = 0;
        if (shouldMax) {
            val = -100000;
            for (Move m : moves) {
                b.doMove(m);
                val = Math.max(val, minimaxHelp(b, depth - 1, !isWhite, !shouldMax, alpha, beta));
                alpha = Math.max(alpha, val);
                b.undoMove();
                if (alpha >= beta) {
                    break;
                }
            }

        } else {
            val = 100000;
            for (Move m : moves) {
                b.doMove(m);

                val = Math.min(val, minimaxHelp(b, depth - 1, !isWhite, !shouldMax, alpha, beta));
                b.undoMove();
                beta = Math.min(beta, val);
                if (beta <= alpha) {
                    break;
                }

            }
        }
        return val;



    }

    public double evalBoardState(Board b, Side side) {

        Piece pieces[] = b.boardToArray();
        double ourPieces = 0;
        double theirPieces = 0;
        for (Piece p : pieces) {
            if (p.getPieceType() == null || p.getPieceSide() == null) {
                continue;
            } else if (p.getPieceSide() == side) {
                ourPieces += PieceValues.getValue(p.getPieceType());
            } else {
                theirPieces += PieceValues.getValue(p.getPieceType());
            }
        }
        double diff = ourPieces - theirPieces;

        if (b.isMated()) {
            System.out.println("mated loop???");
            if (b.getSideToMove() != side) {
                return -100000;
            } else {
                return 100000;
            }
        } else {
            if (this.historian != null)  {

                if (this.historian.willBoardBeDraw(b.getZobristKey())) {
                    System.out.println(">>>> Choosing to draw at as " + side.toString() + " and value of : " + this.evalBoardState(b, side));
                    return 0;
                }
            }

        }


        diff += nuancedEval(b, side);
        return diff;
    }

    /*
        Hope is that this will add more nuance to the evaluation of the board state.
        This should be less the worth of a pawn ideally, may need to magic # some stuff for now
        probably should be worth .5 total if everything is hot and spicy
     */
    private static double nuancedEval(Board b, Side s) {
        double goodVal = 0;
        double badVal = 0;
        double attackingSquareModifier = .3;
        double kingCenterPenalty = .1;
        double bishopPairModifier = .2;


        // controlling the center 2 rows is slightly good
        ArrayList<Square> targets = new ArrayList<>();
        targets.addAll(0, Utils.fileD);
        targets.addAll(0, Utils.fileE);
        targets.addAll(0, Utils.rank4);
        targets.addAll(0, Utils.rank5);

        // controlling the center 2 columns is slightly good
        // attacking their high value pieces is slightly good

        int usAttackingCentralSquares = b.countSquaresAttackedBy(targets, s);
        int themAttackingCentralSquares = b.countSquaresAttackedBy(targets, s.flip());

        goodVal += (double)(usAttackingCentralSquares) * attackingSquareModifier;
        badVal += (double)(themAttackingCentralSquares) * attackingSquareModifier;
//        return ((double)(usAttackingCentralSquares - themAttackingCentralSquares)) * attackingSquareModifier;

        // defending pieces is slightly good

        // the king in the center is slightly dangerous
        if (b.getKingSquare(s).getFile() == File.FILE_D || b.getKingSquare(s).getFile() == File.FILE_E) {
            badVal += kingCenterPenalty;
        } else {
            goodVal += kingCenterPenalty;
        }


        //its better for us if their king isnt castled and worse if it is
        if (b.getKingSquare(s.flip()).getFile() == File.FILE_D || b.getKingSquare(s.flip()).getFile() == File.FILE_E) {
            goodVal += kingCenterPenalty;
        } else {
            badVal += kingCenterPenalty;
        }

        // developing is slightly good

        // attacking lots of squares is slightly good


        // pawn stuff : isolated is bad, connected/supporting is good

        // having both bishops
        if (s == Side.WHITE) {
            if (b.getPieceLocation(Piece.WHITE_BISHOP).size() > 1) {
                goodVal += bishopPairModifier;
            }
            if (b.getPieceLocation(Piece.BLACK_BISHOP).size() > 1) {
                badVal += bishopPairModifier;
            }
        } else {
            if (b.getPieceLocation(Piece.WHITE_BISHOP).size() > 1) {
                badVal += bishopPairModifier;
            }
            if (b.getPieceLocation(Piece.BLACK_BISHOP).size() > 1) {
                goodVal += bishopPairModifier;
            }
        }

        // checks that limit moves are good?


        return goodVal - badVal;
    }


    private static double evaluate(Move m, Board b) {
        /*
            1. taking a piece this move
            2. setting up a piece to take next move
            3. defending your own pieces
            ....
            - holding good squares
            - controlling the center
            - trading when ahead on material fairly
            - seeing forcing moves
            - sacrifices


            scrore for now is how many pieces do I have vs how many pieces do they have
         */
        Side us = b.getSideToMove();

        b.doMove(m);

        Piece pieces[] = b.boardToArray();

        double ourPieces = 0;
        double theirPieces = 0;
        for (Piece p : pieces) {


            if (p.getPieceType() == null || p.getPieceSide() == null) {
                continue;
            } else if (p.getPieceSide() == us) {

                ourPieces += PieceValues.getValue(p.getPieceType());
            } else {

                theirPieces += PieceValues.getValue(p.getPieceType());
            }
        }
        b.undoMove();
        return ourPieces - theirPieces;
    }


}
