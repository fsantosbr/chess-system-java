package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //matrix - Relation "tem-muitos" with the Piece class
	
	
	//constructors
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	//Remind we can change or insert a value to an attribute even when it is not in the argument


	
	//getters and setters
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	
	
	//methods
	public Piece piece(int row, int column) {
		return pieces[row][column];
	}
	
	public Piece piece(Position position){
		return pieces[position.getRow()][position.getColumn()]; //the 'pieces matrix' was already set up (scroll up). And both '.getRow()' and '.getColumn()' are methods from the Position class accessible by the position variable that is being used locally here.
	}
	//Sobrecarga do mesmo método. Similar to constructors
	
	
	
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece; //Both '.getRow()' and '.getColumn()' are methods from the Position class accessible by the position variable that is being used locally here.
		piece.position = position; //We're accessing directly the position attribute from the Piece class because it is set as protected.
				
	}
}
