package ChessPieces;

import Movements.CastleMovement;
import Movements.SquareMovement;

public class King extends ChessPiece{
	public static String name = "King";		//Piece Name
	public static int materialWorth = 10;	//How much the piece is worth

	/**
	 * Class Constructor.
	 * @param posRow - int, row position.
	 * @param posCol - int, column position.
	 * @param color - String, piece color.
	 * @param image - char, piece symbol.
	 */
	public King(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new SquareMovement());
		getMovements().add(new CastleMovement());
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public King(ChessPiece source){
		super(source);
	}

	/**
	 * Method to get the materialWorth of the piece.
	 * @return materialWorth - int
	 */
	public int getMaterialWorth() { return materialWorth; }

	/**
	 * Method to get the piece name.
	 * @return name - String
	 */
	public String getName() { return name; }
}
