package ChessGameClasses;

import Movements.Movement;
import ChessPieces.*;
import java.util.ArrayList;

public class Board 
{
	//Board Dimensions
	public static final int rowNum = 10;
	public static final int colNum = 9;
	
	//Chess Piece Position Board Symbols.
	public static final Character whiteKingPieceSymbol = 'W';
	public static final Character whitePieceSymbol = 'w';
	public static final Character blackKingPieceSymbol = 'B';
	public static final Character blackPieceSymbol = 'b';
	
	private ArrayList<ChessPiece> whiteChessPieces;
	private ArrayList<ChessPiece> blackChessPieces;
	
	
	//The board marks the location of each piece, specifying their color and if they are Kings. 
	//White King Piece Symbol = 'W';
	//White Piece Symbol = 'w';
	//Black King Piece Symbol = 'B';
	//Black Piece Symbol = 'b';
	private ArrayList<ArrayList<Character>> positionBoard;
	private ArrayList<ArrayList<Character>> whiteControlBoard;
	private ArrayList<ArrayList<Character>> blackControlBoard;
	
	public Board(ArrayList<ChessPiece> whiteChessPieces, ArrayList<ChessPiece> blackChessPieces)
	{
		this.whiteChessPieces = whiteChessPieces;
		this.blackChessPieces = blackChessPieces;
		
		this.positionBoard = createAnEmpty2DCharacterArrayList();
		this.whiteControlBoard = createAnEmpty2DCharacterArrayList();
		this.blackControlBoard = createAnEmpty2DCharacterArrayList();
		
		updatePositionBoard();
		updateControlBoards();
	}
	
	//Copy constructor
	public Board(Board source)
	{
		this.whiteChessPieces = new ArrayList<ChessPiece>();
		this.blackChessPieces = new ArrayList<ChessPiece>();
		
		copyChessPieceArrayListByValue(this.whiteChessPieces, source.whiteChessPieces);
		copyChessPieceArrayListByValue(this.blackChessPieces, source.blackChessPieces);
		
		this.positionBoard = createAnEmpty2DCharacterArrayList();
		this.whiteControlBoard = createAnEmpty2DCharacterArrayList();
		this.blackControlBoard = createAnEmpty2DCharacterArrayList();
		
		updatePositionBoard();
		updateControlBoards();
	}
	
	public ArrayList<ChessPiece> getWhiteChessPieces() { return whiteChessPieces; }
	public ArrayList<ChessPiece> getBlackChessPieces() { return blackChessPieces; }
	public ArrayList<ArrayList<Character>> getPositionBoard() { return positionBoard; }
	public ArrayList<ArrayList<Character>> getWhiteControlBoard() { return whiteControlBoard; }
	public ArrayList<ArrayList<Character>> getBlackControlBoard() { return blackControlBoard; }
	
	//Deep copy.
	public void deepCopy(Board source)
	{
		this.whiteChessPieces.clear();
		this.blackChessPieces.clear();
		
		copyChessPieceArrayListByValue(this.whiteChessPieces, source.whiteChessPieces);
		copyChessPieceArrayListByValue(this.blackChessPieces, source.blackChessPieces);
			
		this.positionBoard = source.getPositionBoard();
		this.whiteControlBoard = source.getWhiteControlBoard();
		this.blackControlBoard = source.getBlackControlBoard();
	}
	
	//Using whiteChessPieces and blackChessPieces, 
	//label the location of each piece with the corresponding symbol
	public void updatePositionBoard()
	{
		//Create an empty ArrayList<ArrayList<Character>> to store the possible moves
		positionBoard.clear();
		positionBoard = new ArrayList<ArrayList<Character>>();
		for (int row = 0; row < rowNum; row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < colNum; column++)
				currentRow.add(Movement.invalidMoveSymbol);
				
			positionBoard.add(currentRow);
		}
			
		//Add all of the white chess pieces to the Position Board if they haven't been captured yet.
		//And differentiate between Kings and non-King pieces.
		for (ChessPiece whitePiece : whiteChessPieces)
		{
			if (!whitePiece.getIsCaptured())
			{
				//If it is the White King
				if (whitePiece instanceof King)
					positionBoard.get(whitePiece.getPosRow()).set(whitePiece.getPosCol(), whiteKingPieceSymbol);
				else
					positionBoard.get(whitePiece.getPosRow()).set(whitePiece.getPosCol(), whitePieceSymbol);
			}
		}
			
		//Add all of the black chess pieces to the Position Board if they haven't been captured yet.
		//And differentiate between Kings and non-King pieces.
		for (ChessPiece blackPiece : blackChessPieces)
		{
			if (!blackPiece.getIsCaptured())
			{
				//If it is the Black King
				if (blackPiece instanceof King)
					positionBoard.get(blackPiece.getPosRow()).set(blackPiece.getPosCol(), blackKingPieceSymbol);
				else
					positionBoard.get(blackPiece.getPosRow()).set(blackPiece.getPosCol(), blackPieceSymbol);
			}
		}
	}
	
	//Loop through all of the Player's Chess Pieces and combine their movement board to
	//create the Player's control board.
	public void calculateControlBoard(ArrayList<ChessPiece> chessPieces, ArrayList<ArrayList<Character>> controlBoard)
	{
		//clear controlBoard so that the Player's board control can be updated.
		controlBoard.clear();
		for (int row = 0; row < Board.rowNum; row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < Board.colNum; column++)
				currentRow.add(Movement.invalidMoveSymbol);
				
			controlBoard.add(currentRow);
		}
		
		//Loop through all of the Player's Chess Pieces.
		for (ChessPiece chessPiece : chessPieces)
		{
			//If the piece wasn't captured.
			if (!chessPiece.getIsCaptured())
			{
				ArrayList<ArrayList<Character>> currentPieceMovements = chessPiece.calculatePotentialMovements(this);
				for (int row = 0; row < currentPieceMovements.size(); row++)
				{
					for (int column = 0; column < currentPieceMovements.get(row).size(); column++)
					{
						Character charInAll = controlBoard.get(row).get(column);
						Character charInCurrent = currentPieceMovements.get(row).get(column);
						
						//Precedence Order from greatest to lowest
						//(capture&move, capture) to move to invalid move.
						if (charInAll.equals(Movement.moveAndCaptureSymbol) || charInCurrent.equals(Movement.moveAndCaptureSymbol))
							controlBoard.get(row).set(column, Movement.moveAndCaptureSymbol); 
						else if (charInAll.equals(Movement.captureSymbol) || charInCurrent.equals(Movement.captureSymbol))
							controlBoard.get(row).set(column, Movement.captureSymbol); 
						else if (charInAll.equals(Movement.moveSymbol) || charInCurrent.equals(Movement.moveSymbol))
							controlBoard.get(row).set(column, Movement.moveSymbol); 
						else
							controlBoard.get(row).set(column, Movement.invalidMoveSymbol); 
					}
				}
			}
		}
	}
	
	public void updateControlBoards()
	{
		calculateControlBoard(this.whiteChessPieces, this.whiteControlBoard);
		calculateControlBoard(this.blackChessPieces, this.blackControlBoard);
	}
	
	//Return true if the piece is in check
	public boolean isInCheck(String color)
	{
		ArrayList<ChessPiece> allyChessPieces;
		ArrayList<ArrayList<Character>> enemyControlBoard;
		if (color.equals("White"))
		{
			allyChessPieces = whiteChessPieces;
			enemyControlBoard = blackControlBoard;
		}
		else
		{
			allyChessPieces = blackChessPieces;
			enemyControlBoard = whiteControlBoard;
		}
		
		int kingRow = -1;
		int kingCol = -1;
		for (int i = 0; i < allyChessPieces.size(); i++)
		{
			if (allyChessPieces.get(i) instanceof King)
			{
				kingRow = allyChessPieces.get(i).getPosRow();
				kingCol = allyChessPieces.get(i).getPosCol();
			}
			
		}
		
		if (kingRow == -1 && kingCol == -1) 
			return false;
		else
			return (enemyControlBoard.get(kingRow).get(kingCol).equals(Movement.captureSymbol)) || 
				(enemyControlBoard.get(kingRow).get(kingCol).equals(Movement.moveAndCaptureSymbol));
	}
	
	//The board that labels every piece for which specific piece they are and what color they have.
	public ArrayList<ArrayList<Character>> getDisplayBoard() 
	{ 
		//Create an empty 2d array list to store the display board.
		ArrayList<ArrayList<Character>> displayBoard = new ArrayList<ArrayList<Character>>();
		for (int row = 0; row < rowNum; row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < colNum; column++)
				currentRow.add(Movement.invalidMoveSymbol);
			displayBoard.add(currentRow);
		}
		
		//Record the piece onto the displayBoard if it hasn't been captured yet.
		for (ChessPiece whitePiece : whiteChessPieces)
		{
			if (!whitePiece.getIsCaptured())
				displayBoard.get(whitePiece.getPosRow()).set(whitePiece.getPosCol(), whitePiece.getImage());
		}
		
		//Record the piece onto the displayBoard if it hasn't been captured yet.
		for (ChessPiece blackPiece : blackChessPieces)
		{
			if (!blackPiece.getIsCaptured())
				displayBoard.get(blackPiece.getPosRow()).set(blackPiece.getPosCol(), blackPiece.getImage());
		}
		
		return displayBoard;
	}
	
	public static void copyChessPieceArrayListByValue(ArrayList<ChessPiece> destination, ArrayList<ChessPiece> source)
	{
		for (ChessPiece chessPiece : source)
		{
			if (chessPiece instanceof Archer) destination.add(new Archer(chessPiece));
			else if (chessPiece instanceof Bishop) destination.add(new Bishop(chessPiece));
			else if (chessPiece instanceof Cannon) destination.add(new Cannon(chessPiece));
			else if (chessPiece instanceof King) destination.add(new King(chessPiece));
			else if (chessPiece instanceof Knight) destination.add(new Knight(chessPiece));
			else if (chessPiece instanceof Ninja) destination.add(new Ninja(chessPiece));
			else if (chessPiece instanceof Pawn) destination.add(new Pawn(chessPiece));
			else if (chessPiece instanceof Queen) destination.add(new Queen(chessPiece));
			else if (chessPiece instanceof Rook) destination.add(new Rook(chessPiece));
			else if (chessPiece instanceof Wizard) destination.add(new Wizard(chessPiece));
		}
	}
	
	public static void displayBoard(ArrayList<ArrayList<Character>> array)
	{
		System.out.print("  ");
		for (int column = 0; column < array.get(0).size(); column++)
			System.out.print(column + " ");
		System.out.println();
		
		for (int row = 0; row < array.size(); row++)
		{
			System.out.print(row + " "); //Row label
			for (int column = 0; column < array.get(row).size(); column++)
			{
				if (array.get(row).get(column).equals(' '))
					System.out.print("-");
				else
					System.out.print(array.get(row).get(column));
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	//Create an empty 2d ArrayList of Character.
	public static ArrayList<ArrayList<Character>> createAnEmpty2DCharacterArrayList()
	{
		ArrayList<ArrayList<Character>> result = new ArrayList<ArrayList<Character>>();
		for (int row = 0; row < Board.rowNum; row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < Board.colNum; column++)
				currentRow.add(Movement.invalidMoveSymbol);
			result.add(currentRow);
		}
		return result;
	}
	
	//Check if the row and column values are valid.
	public boolean rowColWithinBound(int row, int col)
	{
		return ((row >= 0) && (row < rowNum) && (col >= 0) && (col < colNum));
	}
}
