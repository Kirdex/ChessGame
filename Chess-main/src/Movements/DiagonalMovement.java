package Movements;

import ChessGameClasses.Board;
import java.util.ArrayList;

public class DiagonalMovement implements Movement
{
	public static final String movementName = "Diagonal Movement";
	public static final int movementCost = 500;
	
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
		
		//Upper Left Direction
		moveInDirection(posRow, posCol, color, board, result, -1, -1);
		
		//Upper Right Direction
		moveInDirection(posRow, posCol, color, board, result, -1, 1);
		
		//Lower Left Direction
		moveInDirection(posRow, posCol, color, board, result, 1, -1);
		
		//Lower Right Direction
		moveInDirection(posRow, posCol, color, board, result, 1, 1);
		
		
		//The piece can't move onto itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		
		return result;
	}
	
	public void moveInDirection(int posRow, int posCol, String color, Board board, ArrayList<ArrayList<Character>> result, int directionRow, int directionColumn)
	{
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character allyKingSymbol = pieceSymbols.get(0);
		Character allyPieceSymbol = pieceSymbols.get(1);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
		
		//Move in specified direction
		int currentRow = posRow + directionRow;
		int currentColumn = posCol + directionColumn;
		
		//If the position is not out of range
		while (board.rowColWithinBound(currentRow, currentColumn))
		{
			Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
			//If position is occupied by ally, can't move there and is blocked from moving further
			if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol))
			{
				result.get(currentRow).set(currentColumn, invalidMoveSymbol);
				break;
			}
			
			//If position is occupied by enemy, can move&capture and is blocked from moving further
			else if (currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
			{
				result.get(currentRow).set(currentColumn, moveAndCaptureSymbol); 
				break;
			}
			
			//If position is empty, can move there
			else
				result.get(currentRow).set(currentColumn, moveSymbol); 
			
			//Move in specified direction
			currentRow += directionRow;
			currentColumn += directionColumn;
		}
	}
}
