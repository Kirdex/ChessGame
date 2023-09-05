package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;
import java.util.Collections;

public class Scrambler extends RandomItem{

	public Scrambler(String name, String description, int cost)
	{
        super(name, description, cost);
    }
	
	public Scrambler() 
	{
		super("Scrambler", "Scramble The Entire Board", 0);
	}

	@Override
	public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) 
	{
	
		//Set all the chess pieces to has moved
		for (ChessPiece chessPiece : board.getBlackChessPieces())
			chessPiece.setHasMoved(true);
			
		for (ChessPiece chessPiece : board.getWhiteChessPieces())
			chessPiece.setHasMoved(true);
		
		//Add the whole board into the ArrayList. ArrayList will take row and col and add it into the arraylist together.
		ArrayList<IntPair> allPosition = new ArrayList<IntPair>();
		for(int row=0;row<10;row++)
		{
			for(int col=0;col<9;col++)
			{
				allPosition.add(new IntPair(row,col));
			}
		}
		//It will shuffle the elements inside of allPosition
		Collections.shuffle(allPosition);
		//Set black chess pieces row and col to a random one on the board. To ensure two pieces doesn't go to the same spot, once one position one is added, it will be deleted.
		for(int i=0;i<board.getBlackChessPieces().size();i++)
		{
			if (!board.getBlackChessPieces().get(i).getIsCaptured())
			{
			  board.getBlackChessPieces().get(i).setPosRow(allPosition.get(i).getFirst());
			  board.getBlackChessPieces().get(i).setPosCol(allPosition.get(i).getSecond());
			  allPosition.remove(i);
			}
		}
		
		//shuffle the elements again
		Collections.shuffle(allPosition);
		//set the white pieces row and col to the left over positions in allPosition
		for(int i=0;i<board.getWhiteChessPieces().size();i++)
		{
			 
			if (!board.getWhiteChessPieces().get(i).getIsCaptured())
			{
			   board.getWhiteChessPieces().get(i).setPosRow(allPosition.get(i).getFirst());
			   board.getWhiteChessPieces().get(i).setPosCol(allPosition.get(i).getSecond());
			}
			 
		}
		
		return "The Board Has Been Scrambled.";
	 }
}
