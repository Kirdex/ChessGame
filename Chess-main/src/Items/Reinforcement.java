package Items;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;
import ChessPieces.Pawn;

import java.util.ArrayList;

public class Reinforcement extends DefinedItem{
    public Reinforcement(String name, String description, int cost){
        super(name, description, cost);
    }

    public Reinforcement(){
        super("Reinforcement", "Summon a new line of pawns", 5000);
    }

    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) {
        int colNums = board.getPositionBoard().get(0).size();
        int row = 2;
        if(color.equals("White")){
            row = board.getPositionBoard().size() - 3;
            for(int col = 0; col < colNums; col++){
                if(board.getPositionBoard().get(row).get(col).equals(' ')){
                    allyChessPieces.add(new Pawn(row,col, "White", 'P'));
                }
            }
        }
        else{
            for(int col = 0; col < colNums; col++){
                if(board.getPositionBoard().get(row).get(col).equals(' ')){
                    allyChessPieces.add(new Pawn(row,col, "Black", 'p'));
                }
            }
        }
        
        return "A New Line Of Pawns Was Summoned For " + color + ".";
    }
}
