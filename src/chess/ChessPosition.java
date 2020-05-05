package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	
	//constructors
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8");
		}
		
		this.column = column;
		this.row = row;
	}


	//getters and setters
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
		
	//methods	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow()); //This method works to let the player sees only the row and column from the chess and not from our matrix number 
	}

	
	@Override
	public String toString() {
		return "" + column + row; //The "" is here only to let the toString() method joins (concactenar) Strings. It won't work without it.
	}
}
