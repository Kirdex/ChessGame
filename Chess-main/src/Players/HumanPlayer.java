package Players;

import ChessGameClasses.Board;
import ChessPieces.*;
import Items.Item;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer extends Player
{
	public HumanPlayer(ArrayList<ChessPiece> chessPieces, String color, int money)
	{
		super(chessPieces, color, money);
	}
	
	//Make a decision. Buy items or move a chess piece
	public String makeDecision(Board board)
	{
		if(board.isInCheck(getColor())){
			return Player.MoveChessPiece;
		}

		System.out.println("Make A Decision (0 - Buy Item) (1 - Move Chess Piece) (2 - Upgrade Chess Piece)");
		int decision = takeIntegerInput("Input: ", 0, 3);
		System.out.println();
		
		if (decision == 0)
			return Player.BuyItem;
		else if (decision == 1)
			return Player.MoveChessPiece;
		else 
			return Player.UpgradeChessPiece;
	}
	
	// Get and make a decision between three options of price to pay for different chances of items from different tiers. 
	//Check if player has enough money, if player has enough money, use the specified chances to give the player an item randomly. 
	//Then use the item by passing in the board and the player’s color.
	public boolean buyItem(Board board, ArrayList<Item> items)
	{
		int choice = -1;
		System.out.println("Player Items: ");
		for(int i = 0; i < items.size(); i++)
		{
			System.out.println(i + ") " + items.get(i).getItemName() + " - $" + items.get(i).getItemCost());
		}
		choice = takeIntegerInput("Choose an item: ", 0, items.size());
		if(getMoney() >= items.get(choice).getItemCost())
		{
			items.get(choice).run(getColor(), board);
			return true;
		}
		else
		{
			System.out.println("Not enough money");
			return false;
		}
	}
	
	//Take user input until a piece that the player owns is selected.  
	public int selectPiece(Board board)
	{
		ArrayList<Integer> piecePosition;
		int row;
		int col;
		int selectedPieceIndex = -1;
		
		//Prompt user to select a chess piece.
		System.out.println("Select A Piece That You Own");
		
		//Take user input until a piece that the player owns is selected.
		do
		{
			piecePosition = takeUserInputForBoardPosition(board);
			row = piecePosition.get(0);
			col = piecePosition.get(1);
			
			for (int i = 0; i < getChessPieces().size(); i++)
			{
				//Piece is found.
				if (getChessPieces().get(i).getPosRow() == row && getChessPieces().get(i).getPosCol() == col)
				{
					selectedPieceIndex = i;
					break;
				}
			}
		}
		while (selectedPieceIndex == -1);
		System.out.println();
		
		return selectedPieceIndex;
	}
		
	//Calculate all of the possible moves the piece can make, then get or generative decision to make it. Ignore inputs that are invalid. 
	//There should be a reselect mechanism. 
	public ArrayList<Integer> selectMovement(int selectedPieceIndex, Board board)
	{
		ArrayList<Integer> movementPosition = new ArrayList<Integer>();
		
		//Display the movement board of the piece.
		System.out.println("Possible Movements This Piece Can Make");
		ArrayList<ArrayList<Character>> potentialMovements = getChessPieces().get(selectedPieceIndex).calculatePotentialMovements(board);
		ArrayList<ArrayList<Character>> possibleMovements = getChessPieces().get(selectedPieceIndex).calculatePossibleMovements(potentialMovements, board);
		Board.displayBoard(possibleMovements);
		System.out.println();
		
		//Prompt user to select a move.
		System.out.println("Select A Move");
		
		//Get user input
		ArrayList<Integer> userInput = takeUserInputForBoardPosition(board);
		movementPosition.add(userInput.get(0));
		movementPosition.add(userInput.get(1));
		
		System.out.println();
		
		return movementPosition;
	}
	
	public boolean upgrade(Board board)
	{
		int pieceIndex = selectPiece(board);
		ChessPiece currentPiece = getChessPieces().get(pieceIndex);

		currentPiece.displayUpgrades();
		if(currentPiece.getAvailableUpgrades().size() == 0){
			return false;
		}
		int upgradeChoice = takeIntegerInput("Select the upgrade: ", 0, currentPiece.getAvailableUpgrades().size());

		if(getMoney() >= currentPiece.getAvailableUpgrades().get(upgradeChoice).getMovementCost() * currentPiece.getMaterialWorth())
		{
			currentPiece.upgrade(upgradeChoice);
			return true;
		}
		else
		{
			System.out.println("Not enough money");
			return false;
		}
	}
	
	public ChessPiece promote(ChessPiece chessPiece)
	{
		ChessPiece newChessPiece = null;
		
		System.out.println("Choose a piece to promote the Pawn At (" + chessPiece.getPosRow() + ", " + chessPiece.getPosCol() + ")");
		System.out.println("1) Queen");
		System.out.println("2) Bishop");
		System.out.println("3) Knight");
		System.out.println("4) Rook");
		System.out.println("5) Cannon");
		System.out.println("6) Ninja");
		System.out.println("7) Wizard");
		
		int input = takeIntegerInput("Input: ", 1, 8);
		
		int row = chessPiece.getPosRow();
		int col = chessPiece.getPosCol();
		String color = chessPiece.getColor();
		
		switch(input)
		{
			case 1: 
				if (getColor().equals("White")) 
					newChessPiece = new Queen(row, col, color, 'Q');
				else 
					newChessPiece = new Queen(row, col, color, 'q');
				break;
			case 2:
				if (getColor().equals("White")) 
					newChessPiece = new Bishop(row, col, color, 'B');
				else 
					newChessPiece = new Bishop(row, col, color, 'b');
				break;
			case 3: 
				if (getColor().equals("White")) 
					newChessPiece = new Knight(row, col, color, 'H');
				else 
					newChessPiece = new Knight(row, col, color, 'h');
				break;
			case 4: 
				if (getColor().equals("White")) 
					newChessPiece = new Rook(row, col, color, 'R');
				else 
					newChessPiece = new Rook(row, col, color, 'r');
				break;
			case 5: 
				if (getColor().equals("White")) 
					newChessPiece = new Cannon(row, col, color, 'C');
				else 
					newChessPiece = new Cannon(row, col, color, 'c');
				break;
			case 6: 
				if (getColor().equals("White")) 
					newChessPiece = new Ninja(row, col, color, 'N');
				else 
					newChessPiece = new Ninja(row, col, color, 'n');
				break;
			case 7: 
				if (getColor().equals("White")) 
					newChessPiece = new Wizard(row, col, color, 'W');
				else 
					newChessPiece = new Wizard(row, col, color, 'w');
				break;
		}
		return newChessPiece;
	}
	
	//Lower bound inclusive, upperbound exclusive
	public Integer takeIntegerInput(String prompt, int lowerbound, int upperbound)
	{
		Scanner scanner = new Scanner(System.in);
		
		//Take an input. Ignore out of bound and non integer inputs.
		int input;
		do 
		{
			System.out.print(prompt);
			try
			{
				input = scanner.nextInt();	
				scanner.nextLine(); //Remove the "\n".
			} 
			catch(InputMismatchException e) //Catch exception for non integer inputs.
			{
				input = lowerbound - 1; //Out of bound to restart loop. 
				scanner.nextLine(); //Remove non integer inputs.
			}
		} while (input < lowerbound || input >= upperbound); //Try again if input was out of bound.
		return input;
	}
	
	//Get user input for a valid board position. 
	public ArrayList<Integer> takeUserInputForBoardPosition(Board board)
	{
		ArrayList<Integer> boardPositionInput = new ArrayList<Integer>();
		
		//Get valid row.
		int inputRow = takeIntegerInput("Input The Row: ", 0, Board.rowNum);
		
		//Get valid column.
		int inputColumn = takeIntegerInput("Input The Column: ", 0, Board.colNum);
		
		//Prepare userInput to be returned.
		boardPositionInput.add(inputRow);
		boardPositionInput.add(inputColumn);
		
		return boardPositionInput;
	}
}
