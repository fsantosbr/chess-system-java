package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //matrix - Relation "tem-muitos" with the Piece class
	
	
	//constructors
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating the board: There must be at least 1 row and 1 column");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	//Remind we can change or insert a value to an attribute even when it is not in the argument


	
	//getters and setters
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	
	
	
	//methods
	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		
		return pieces[row][column];
	}
	
	
	public Piece piece(Position position){
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		
		return pieces[position.getRow()][position.getColumn()]; //the 'pieces matrix' was already set up (scroll up). And both '.getRow()' and '.getColumn()' are methods from the Position class accessible by the position variable that is being used locally here.
	} //Sobrecarga do mesmo método. Similar to constructors
	
	
	
	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece; //Both '.getRow()' and '.getColumn()' are methods from the Position class accessible by the position variable that is being used locally here.
		piece.position = position; //We're accessing directly the position attribute from the Piece class because it is set as protected.
	}
	
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) { //This '!'(not) returns a boolean
			throw new BoardException("Position not on the board");
		}
		
		if(piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns; //private: only this class can use it. Check if the position is inside of the column and row of the board
	}	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		
		return piece(position) != null; //Return false/true for the piece() method in this class
	}
	
	
	
}
