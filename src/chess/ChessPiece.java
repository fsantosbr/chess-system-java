package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {

	private Color color;

	
	//constructor
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	
	//getters and setters
	public Color getColor() {
		return color;
	}

	
	
	
	
	
}
