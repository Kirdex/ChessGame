package Movements;

import ChessGameClasses.Board;
import java.util.ArrayList;

public class RangeCaptureMovement implements Movement
{
	public static final String movementName = "Range Capture";
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
		
		//Logic
		final int range = 2;
		int currentRow = posRow;
		int currentColumn = posCol;
		
		if (color.equals("White")) currentRow -= range; //Assuming White is on the bottom
		else currentRow += range;
		
		//Left side
		checkPosition(color, board, result, currentRow, currentColumn - 2);
		
		//Center side
		checkPosition(color, board, result, currentRow, currentColumn);
		
		//Right side
		checkPosition(color, board, result, currentRow, currentColumn + 2);
		
		//To mark itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		return result;
	}
	
	public void checkPosition(String color, Board board, ArrayList<ArrayList<Character>> result, int row, int column)
	{
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
				
		int currentRow = row;
		int currentColumn = column;
		
		//If the position is not out of range
		if (board.rowColWithinBound(currentRow, currentColumn))
		{
			Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);

			//If position is occupied by enemy, it can capture. Otherwise it can't move nor capture there.
			if (currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
				result.get(currentRow).set(currentColumn, captureSymbol); 
		}
	}
}
