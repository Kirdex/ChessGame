package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

public class Plague extends RandomItem
{
    public Plague(String name, String description, int cost)
    {
        super(name, description, cost);
    }

    public Plague()
    {
        super("Plague", "Randomly Pick A Chess Piece On The Board And Eliminates All Chess Pieces Of The Same Type.", 0);
    }

    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) 
    {
        //All of the Possible Targets
        ArrayList<String> possibleTargets = new ArrayList<String>();
        
        //Record all valid targets into possibleTargets
        //1) They are valid if the chess piece hasn't been captured, 
        //2) It is not the king.
        //3) It doesn't already exist in possibleTargets.
        for (int i = 0; i < allyChessPieces.size(); i++)
        	if (!allyChessPieces.get(i).getIsCaptured())
        		if (!allyChessPieces.get(i).getName().equals(King.name)) 
        			if (!targetAlreadyExists(allyChessPieces.get(i).getName(), possibleTargets))
        				possibleTargets.add(allyChessPieces.get(i).getName());
        
        if (possibleTargets.size() > 0)
        {
	        int randomIndex;
	        String target;
	        
		    randomIndex = getRandomNum(possibleTargets.size());
		    target = possibleTargets.get(randomIndex);
	        
	        
	        for (ChessPiece chessPiece : allyChessPieces)
	        	if (!chessPiece.getIsCaptured())
		        	if (chessPiece.getName().equals(target))
		        		chessPiece.chessPieceGetsCaptured();
	        
	        for (ChessPiece chessPiece : opponentChessPieces)
	        	if (!chessPiece.getIsCaptured())
		        	if (chessPiece.getName().equals(target))
		        		chessPiece.chessPieceGetsCaptured();
	        
	        return "A Plague Has Fallen On All Of The " + target + " Pieces.";
        }
        else
        {
        	return "Not Enough Victims For The Plague To Spread.";
        }
    }
    
    private boolean targetAlreadyExists(String target, ArrayList<String> possibleTargets)
    {
    	for (String name : possibleTargets)
    		if (name.equals(target))
    				return true;
    	return false;
    }
}
