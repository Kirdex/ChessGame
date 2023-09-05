package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

public class UniqueSlayer extends DefinedItem
{

    public UniqueSlayer(String name, String description, int cost)
    {
        super(name, description, cost);
    }
	
    public UniqueSlayer()
    {
        super("Unique Slayer", "Eliminate All Unique Pieces(Archers, Cannons, Ninjas, And Wizards).", 2500);
    }

    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board)
    {
        for(ChessPiece piece : opponentChessPieces)
        {
        	if (!piece.getIsCaptured())
        	{
	            if(piece instanceof Wizard || piece instanceof Ninja || piece instanceof Cannon || piece instanceof Archer)
	            {
	                piece.chessPieceGetsCaptured();
	            }
        	}
        }
        for(ChessPiece piece : allyChessPieces)
        {
        	if (!piece.getIsCaptured())
        	{
	            if(piece instanceof Wizard || piece instanceof Ninja || piece instanceof Cannon || piece instanceof Archer)
	            {
	                piece.chessPieceGetsCaptured();
	            }
        	}
        }
        
        return "Archers, Cannons, Ninjas, And Wizards Have All Been Eliminated.";
    }
}
