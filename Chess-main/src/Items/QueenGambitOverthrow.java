package Items;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;
import ChessPieces.Queen;

import java.util.ArrayList;

public class QueenGambitOverthrow extends DefinedItem
{

    public QueenGambitOverthrow(String name, String description, int cost)
    {
        super(name, description, cost);
    }
	
    public QueenGambitOverthrow() 
    {
        super("Queen Gambit Overthrow", "Eliminate All Of Your Opponent’s Queen." ,5000);
    }

    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board)
    {
        for(ChessPiece potentialQueen : opponentChessPieces)
        {
        	if (!potentialQueen.getIsCaptured())
        	{
	            if(potentialQueen instanceof Queen)
	            {
	                potentialQueen.chessPieceGetsCaptured();
	            }
        	}
        }
        
        String opponentColor;
        if (color.equals("White")) opponentColor = "Black";
        else opponentColor = "White";
        return "All Of " + opponentColor + "'s Queens Were Eliminated.";
    }
}
