package Movements;

import ChessGameClasses.Board;
import java.util.ArrayList;

public class SquareMovement implements Movement
{
	public static final String movementName = "Square Movement";
	public static final int movementCost = 300;
	
	@Override
	public String getMovementName() { return movementName; }
	
	@Override
	public int getMovementCost() { return movementCost; }
	
	
	//posRow and posCol are the row and column that the chess piece is currently in.
	@Override
	public ArrayList<ArrayList<Character>> calculateMovement(int posRow, int posCol, String color, boolean hasMoved, Board board)
	{
		//Create an empty ArrayList<ArrayList<Character>> to store the possible moves
		ArrayList<ArrayList<Character>> result = Board.createAnEmpty2DCharacterArrayList();
		
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character allyKingSymbol = pieceSymbols.get(0);
		Character allyPieceSymbol = pieceSymbols.get(1);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
		
		//Logic	
		int range = 3;
		int startingRow = posRow - 1;
		int startingColumn = posCol - 1;

		int currentRow = startingRow;
		int currentColumn = startingColumn;
		for (int i = 0; i < range; i++)
		{
			currentRow = startingRow;
			for (int j = 0; j < range; j++)
			{
				 //If the position is not out of range
				if (board.rowColWithinBound(currentRow, currentColumn))
				{
					Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
					
					//If position is occupied by ally, can't move there.
					if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol))
						result.get(currentRow).set(currentColumn, invalidMoveSymbol);
					
					//If position is occupied by enemy, can move&capture.
					else if (currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
						result.get(currentRow).set(currentColumn, moveAndCaptureSymbol); 
					
					//If position is empty, can move there.
					else
						result.get(currentRow).set(currentColumn, moveSymbol); 
				}
				currentRow++;
			}
			currentColumn++;
		}
		
		//The piece can't move onto itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		
		return result;
	}
}
