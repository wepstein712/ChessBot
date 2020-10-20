import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

import java.util.ArrayList;


public class main {
    //
    public static void main(String[] args) throws MoveConversionException {
        Board board = new Board();
        ArrayList<Move> gameMoves = new ArrayList();
        boolean flag = true;
        int j = 0;
        int depth = 2;
        ChessBot white = new ReflexiveAgent(Side.WHITE, depth);
        ChessBot black = new ReflexiveAgent(Side.BLACK, depth);

        //to keep track of the board states so as to stop a repetition
        GameHistory gh = new GameHistory();
        // give bots access to the game history
        white.setHistorian(gh);
        black.setHistorian(gh);
        boolean whiteTurn = true;
        Move m;

        //!board.isMated() && !board.isDraw() && flag &&

        while (gh.addKey(board.getZobristKey()) ) {
            System.out.println(board.getFen());
           j++;
           if (j > 60) {
            board.checkCastle();
           }
           try {
//               System.out.println(">> \n\n " + board.getFen());

               if (whiteTurn) {
                   m = white.getNextMove(board);
                   if (m == null) {
                       System.out.println("\nWhite NULL MOVE");
                       break;
                   }
                   System.out.println("\n White Move: " + m.toString());

                   gameMoves.add(m);
                   board.doMove(m);
//                   whiteTurn = false;
               } else {
                   m = black.getNextMove(board);
                   if (m == null) {
                       System.out.println("\nBlack NULL MOVE");

                       break;
                   }
                   System.out.println("\nBlack Move: " + m.toString());
                   gameMoves.add(m);
                   board.doMove(m);
//                   whiteTurn = true;

               }
               whiteTurn = !whiteTurn;
           } catch (Exception e) {
               flag = false;

               System.out.println(e.getStackTrace()[0].getFileName());
               System.out.println(e.getStackTrace()[0].getMethodName());
               System.out.println(e.getStackTrace()[0].getLineNumber());
               System.out.println("FINISHED: " + gameMoves.size());
               System.out.println("ERROR: " + e.toString() + "\n\n");
               System.out.println(e.getStackTrace()[0]);
               break;
           }


        }
        System.out.println("");

        System.out.println("WE ENDED: ");
        System.out.println("Mated? " + board.isMated());
        System.out.println("flag? " + flag);
        System.out.println("key? " + gh.willBoardBeDraw(board.getZobristKey()));
        System.out.println("Draw? " + board.isDraw());
        System.out.println(">> \n\n " + board.getFen());



        int i = 1;
        System.out.println("\n\n\n\n");
       for (Move ms : gameMoves) {
           System.out.print(i++ + ". " + ms.toString() + " ");
       }



    }


//    public static boolean checkRepition(ArrayList<Move> moves) {
//        if (moves.size() < 10) {
//            return false;
//        }
//        int length = moves.size();
//        if (moves.get(length - 5).equals(moves.get(length - 4)) &&
//                    moves.get(length - 3).equals(moves.get(length - 4)) &&
//                    moves.get(length - 3).equals(moves.get(length - 2)) &&
//                    moves.get(length - 1).equals(moves.get(length - 2))) {
//            return true;
//        }
//        return false;
//    }


}
