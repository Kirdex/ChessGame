package Movements;

import ChessGameClasses.Board;

import java.util.ArrayList;

public class TwoUnitsUp implements Movement
{
    public static final String movementName = "Two Units Up";
    public static final int movementCost = 0;
    
	@Override
	public String getMovementName() { return movementName; }
	
	@Override
	public int getMovementCost() { return movementCost; }
	
	
    @Override
    public ArrayList<ArrayList<Character>> calculateMovement(int posRow, int posCol, String color, boolean moved, Board board) 
    {
    	//Create an empty ArrayList<ArrayList<Character>> to store the possible moves
        ArrayList<ArrayList<Character>> result = Board.createAnEmpty2DCharacterArrayList();

        if(moved)
        {
            return result;
        }

        int col = posCol, row;
        int direction = 0;

        if(color.compareTo("White") == 0)
            direction = -1;
        else
            direction = 1;
        
        //If the position is not out of range
	if (board.rowColWithinBound(posRow + direction, col) &&
		board.rowColWithinBound(posRow + direction + direction, col))
        {
	        	
	        row = posRow + direction;
	        Character pathCharacter = board.getPositionBoard().get(row).get(col);
	        
	        row += direction;
	        Character destinationCharacter = board.getPositionBoard().get(row).get(col);
	        
	        //If path and destination are not occupied, then this Movement can be made.
	        if (pathCharacter.equals(Movement.invalidMoveSymbol) &&
	        		destinationCharacter.equals(Movement.invalidMoveSymbol))
	        {
	            result.get(row).set(col, Movement.twoUnitsUpSymbol);
	        }
        }

        return result;
    }
}
