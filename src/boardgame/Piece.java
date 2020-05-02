package boardgame;

public class Piece {
	
	protected Position position;
	private Board board; 
	
	//Constructor
	public Piece(Board board) {
		this.board = board;
		position = null; //by default the value of an object is null;
	}
	//We did not use the position attribute 'cause the actual position of a new piece will set as null

	
	//getters and setters
	protected Board getBoard() {
		return board;
	}
	
	
	
	
	
	
	
}

