package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

public class KingTeleport extends RandomItem{

    public KingTeleport(String name, String description, int cost){
        super(name, description, cost);
    }

    public KingTeleport(){
        super("King Teleport", "Moves Both Kings To A Random Location On The Board.", 1000);
    }
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) {
        int allyKingIndex = findKing(allyChessPieces);
        int oppKingIndex = findKing(opponentChessPieces);

        ArrayList<Integer> newAllyPos = findEmptySpot(board);
        allyChessPieces.get(allyKingIndex).updateMove(newAllyPos.get(0), newAllyPos.get(1));

        ArrayList<Integer> newOppPos = findEmptySpot(board);
        opponentChessPieces.get(oppKingIndex).updateMove(newOppPos.get(0), newOppPos.get(1));
        
        return "Both Kings Were Teleported.";
    }

    private int findKing(ArrayList<ChessPiece> playerPieces){
        int kingIndex = -1;

        for(int i = 0; i < playerPieces.size(); i++){
            if(playerPieces.get(i) instanceof King){
                kingIndex = i;
            }
        }

        return kingIndex;
    }

    private ArrayList<Integer> findEmptySpot(Board board){
        boolean emptySpot = false;
        int randomRow = getRandomNum(board.getPositionBoard().size());
        int randomCol = getRandomNum(board.getPositionBoard().get(0).size());
        while(!emptySpot){
            if(board.getPositionBoard().get(randomRow).get(randomCol).equals(' ')){
                emptySpot = true;
            }else{
                randomRow = getRandomNum(board.getPositionBoard().size());
                randomCol = getRandomNum(board.getPositionBoard().get(0).size());
            }
        }
        ArrayList<Integer> emptyPosition = new ArrayList<>();
        emptyPosition.add(randomRow);
        emptyPosition.add(randomCol);
        return emptyPosition;
    }
}
