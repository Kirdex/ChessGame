package Items;

import java.util.ArrayList;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;

public abstract class DefinedItem implements Item
{
    private String itemName;
    private String itemDescription;
    private int itemCost;

    public DefinedItem(String name, String description, int cost)
    {
        setItemName(name);
        setItemDescription(description);
        setItemCost(cost);
    }
    
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
	public String getItemDescription() { return itemDescription; }
	public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
    public int getItemCost() { return itemCost; }
    public void setItemCost(int itemCost) { this.itemCost = itemCost; }
    
    public String toString()
    {
    	return "Name: " + getItemName() + "\n" +
    			"Description: " + getItemDescription() + "\n";
    }
    
    public String run(String color, Board board)
    {
    	//The item's message.
    	String message;
    	
    	//Create a copy of the current board.
    	Board copyBoard = new Board(board);
    			
    	//Determine the allyChessPieces and the enemyChessPieces
    	ArrayList<ChessPiece> opponentChessPieces;
    	ArrayList<ChessPiece> allyChessPieces;
    	if (color.equals("White"))
    	{
    		opponentChessPieces = copyBoard.getBlackChessPieces();
    		allyChessPieces = copyBoard.getWhiteChessPieces();
    	}
    	else //color == "Black"
    	{
    		opponentChessPieces = copyBoard.getWhiteChessPieces();
    		allyChessPieces = copyBoard.getBlackChessPieces();
    	}
    	
    	message = use(color, allyChessPieces, opponentChessPieces, copyBoard);
    	
    	copyBoard.updatePositionBoard();
		copyBoard.updateControlBoards();
		
		//Allow the item to influence the board if it does not result in a check for both Players.
		if (!copyBoard.isInCheck("White") && !copyBoard.isInCheck("Black"))
		{
			board.deepCopy(copyBoard);
			return message;
		}
		else
		{
			return "Item Has Been Burned Because The Item Results In A Check.";
		}
    }
}
