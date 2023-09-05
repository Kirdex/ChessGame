package Players;
import java.util.ArrayList;

import ChessPieces.ChessPiece;
import ChessPieces.Pawn;
import Items.Item;
import ChessGameClasses.Board;

abstract public class Player 
{
	public static final String BuyItem = "Buy Item";
	public static final String MoveChessPiece = "Move Chess Piece";
	public static final String UpgradeChessPiece = "Upgrade Chess Piece";
	
	private ArrayList<ChessPiece> chessPieces;
	private String color;
	private int money;
	
	public Player(ArrayList<ChessPiece> chessPieces, String color, int money)
	{
		this.setChessPieces(chessPieces);
		this.setColor(color);
		this.setMoney(money);
	}
	
	public ArrayList<ChessPiece> getChessPieces() { return chessPieces; }
	public String getColor() { return color; }
	public int getMoney() { return money; }
	
	public void setChessPieces(ArrayList<ChessPiece> chessPieces) { this.chessPieces = chessPieces; }
	public void setColor(String color) { this.color = color; }
	public void setMoney(int money) { this.money = money; }

	//Make a decision. Buy items or move a chess piece
	abstract public String makeDecision(Board board);
	
	// Get and make a decision between three options of price to pay for different chances of items from different tiers. 
	//Check if player has enough money, if player has enough money, use the specified chances to give the player an item randomly. 
	//Then use the item by passing in the board and the player’s color.
	abstract public boolean buyItem(Board board, ArrayList<Item> items);

	//Select a piece that the player owns
	abstract public int selectPiece(Board board);
	
	//Calculate all of the possible moves the piece can make, then get or generate a decision to make it. Ignore inputs that are invalid. 
	abstract public ArrayList<Integer> selectMovement(int selectedPieceIndex, Board board);
	
	//Upgrade a piece
	abstract public boolean upgrade(Board board);
	
	//Promote Chess Pieces that reached the other side of the board
	abstract public ChessPiece promote(ChessPiece chessPiece);
	
	//Make decision, then buy item or move a chess piece. Return true if it successfully bought an item or moved a chess piece.
	public boolean run(Board board, ArrayList<ChessPiece> opponentChessPieces, ArrayList<Item> items)
	{
		//Set All Pawns' movedTwoUnitsUp variable to false.
		for (ChessPiece chessPiece : chessPieces)
			if (chessPiece.getName().equals("Pawn"))
			{
				Pawn pawn = (Pawn) chessPiece;
				pawn.setMovedTwoUnitsUp(false);
			}
		
		boolean moveWasMade = false;
		
		//Make decision.
		String decision = makeDecision(board);
		if (decision.equals(Player.BuyItem))
		{
			moveWasMade = buyItem(board, items);
		}
		else if(decision.equals(Player.UpgradeChessPiece))
		{
			moveWasMade = upgrade(board);
		}
		else //decision.equals(Player.MoveChessPiece)
		{

			//Get input to select a valid piece.
			int selectedPieceIndex = selectPiece(board);

			//Get input to make a movement.
			ArrayList<Integer> movementPosition = selectMovement(selectedPieceIndex, board);

			//Run the movement and return whether a movement was successfully made.
			moveWasMade = getChessPieces().get(selectedPieceIndex).makeMovement(board, movementPosition);
		}
		
		//Promote all pawns that have reached the other side of the board
		int promotionLine;
		if (color.equals("White")) promotionLine = 0;
		else promotionLine = Board.rowNum - 1;
		
		//If a Pawn is at the promotionLine, then promote it.
		for (int i = 0; i < chessPieces.size(); i++)
		{
			if (chessPieces.get(i) instanceof Pawn)
				if (chessPieces.get(i).getPosRow() == promotionLine)
					chessPieces.set(i, promote(chessPieces.get(i)));
		}
		return moveWasMade;
	}
}
