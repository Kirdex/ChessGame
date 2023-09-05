package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

public class InstantPromotion extends RandomItem{

    public InstantPromotion(String name, String description, int cost){
        super(name, description, cost);
    }

    public InstantPromotion(){
        super("Instant Promotion", "Instantly Upgrade A Random Pawn Of The Item User To A Queen.", 2500);
    }

    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) {
        ArrayList<Integer> pawns = new ArrayList<>();

        for(int i = 0; i < allyChessPieces.size(); i++){
            if(allyChessPieces.get(i) instanceof Pawn && !allyChessPieces.get(i).getIsCaptured()){
                pawns.add(i);
            }
        }

        if(pawns.size() == 0)
        {
            return "No Pawns Were Promoted Because There Are No Pawns.";
        }

        int randomIndex = getRandomNum(pawns.size());

        int oldPawnIndex = pawns.get(randomIndex);
        ChessPiece oldPiece = allyChessPieces.get(oldPawnIndex);
        int row = oldPiece.getPosRow();
        int col = oldPiece.getPosCol();

        ChessPiece newQueen = null;

        if(color.equals("White")){
            newQueen = new Queen(row, col, color, 'Q');
        }else{
            newQueen = new Queen(row, col, color, 'q');
        }

        allyChessPieces.remove(oldPiece);
        allyChessPieces.add(oldPawnIndex, newQueen);
        
        return "The Pawn At (" + row + ", " + col + ") Was Promoted To A Queen."; 
    }
}
