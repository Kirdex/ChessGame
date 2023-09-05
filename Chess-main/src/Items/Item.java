package Items;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;

import java.util.ArrayList;

public interface Item 
{
    public String run( String color, Board board);
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board);
    
    public String getItemName();
    public void setItemName(String itemName);
    public String getItemDescription();
    public void setItemDescription(String itemDescription);
    public int getItemCost();
    public void setItemCost(int itemCost);
}
