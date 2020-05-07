package boardgame;

public abstract class Piece {
	
	protected Position position;
	private Board board; 
	
	
	//Constructor
	public Piece(Board board) {
		this.board = board;
		position = null; //by default the value of an object is null;
	}	//We did not use the position attribute 'cause the actual position of a new piece will set as null

		
	//getters and setters
	protected Board getBoard() {
		return board;
	}
	
	
	//methods
	public abstract boolean[][] possibleMoves(); //abstract classes don't have a body, just the signature.
	
	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	} //A concrete method using an abstract method - Hook métodos - This will only make sense when a subclass implement the abstract method
	
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) { //It checks if this position is true
					return true;
				}
			}
		}
		return false;
	} //This concrete method also depends of an abstract method which will only make sense when a subclass implement the abstract method 
}
