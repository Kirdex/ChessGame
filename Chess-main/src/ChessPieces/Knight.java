package ChessPieces;

import Movements.*;
import java.util.ArrayList;

public class Knight extends ChessPiece{
	public static String name = "Knight";	//Piece Name
	public static int materialWorth = 3;	//How much the piece is worth

	/**
	 * Class Constructor.
	 * @param posRow - int, row position.
	 * @param posCol - int, column position.
	 * @param color - String, piece color.
	 * @param image - char, piece symbol.
	 */
	public Knight(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new LMovement());

		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Knight(ChessPiece source){
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

	/**
	 * Method to set the available upgrades of a Bishop when the piece is created.
	 */
	private void setUpgrades(){
		ArrayList<Movement> knightUpgrades = getAvailableUpgrades();

		knightUpgrades.add(new SquareMovement());
		knightUpgrades.add(new DiagonalMovement());
		knightUpgrades.add(new OrthogonalMovement());
		knightUpgrades.add(new LMovement());
		knightUpgrades.add(new HopCaptureMovement());
		knightUpgrades.add(new RangeCaptureMovement());
		knightUpgrades.add(new TeleportationMovement());
		knightUpgrades.add(new RestrictedTeleportationMovement());
		knightUpgrades.add(new LeftRightCaptureMovement());
	}

}
