package Items;

import ChessGameClasses.Board;
import ChessGameClasses.ChessGameRunner;
import ChessPieces.*;

import java.util.ArrayList;

public class Reset extends DefinedItem
{

    public Reset(String name, String description, int cost)
    {
        super(name, description, cost);
    }
	
    public Reset()
    {
        super("Reset", "Reset The Board", 0);
    }

    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board)
    {
    	//Store the original chess piece set of both player.
    	ArrayList<ChessPiece> initialAllyChessPieces;
    	ArrayList<ChessPiece> initialOpponentChessPieces;
    
    	//Determine both player's original chess piece set depending on their color.
    	if (color.equals("White"))
    	{
    		initialAllyChessPieces = ChessGameRunner.createWhiteChessSet();
    		initialOpponentChessPieces = ChessGameRunner.createBlackChessSet();
    	}
    	else
    	{
    		initialAllyChessPieces = ChessGameRunner.createBlackChessSet();
    		initialOpponentChessPieces = ChessGameRunner.createWhiteChessSet();
    	}
    	
    	//Loop through the passed-in chess piece sets, reset all chess pieces that haven't been captured.
        for (int i = 0; i < initialAllyChessPieces.size(); i++)
        	if (!allyChessPieces.get(i).getIsCaptured())
        	{
        		allyChessPieces.get(i).setPosRow(initialAllyChessPieces.get(i).getPosRow());
        		allyChessPieces.get(i).setPosCol(initialAllyChessPieces.get(i).getPosCol());
        		allyChessPieces.get(i).setHasMoved(false);
        	}
        
        //Delete the extra pieces
        for (int i = initialAllyChessPieces.size(); i < allyChessPieces.size(); i++)
        {
        	allyChessPieces.remove(i);
        	i--;
        }
        
        for (int i = 0; i < initialOpponentChessPieces.size(); i++)
        	if (!opponentChessPieces.get(i).getIsCaptured())
        	{
        		opponentChessPieces.get(i).setPosRow(initialOpponentChessPieces.get(i).getPosRow());
        		opponentChessPieces.get(i).setPosCol(initialOpponentChessPieces.get(i).getPosCol());
        		opponentChessPieces.get(i).setHasMoved(false);
        	}
        
        //Delete the extra pieces
        for (int i = initialOpponentChessPieces.size(); i < opponentChessPieces.size(); i++)
        {
        	opponentChessPieces.remove(i);
        	i--;
        }
        
        return "The Board Has Been Resetted.";
    }
}
