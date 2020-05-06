package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	
	//constructor
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
		// moveCount = 0; - integer numbers by default receive the value "0"
	}

	
	//getters and setters
	public Color getColor() {
		return color;
	}

	public int getMoveCount() {
		return moveCount;
	}
	
		
	
	//methods
	public void increaseMoveCount() {
		moveCount++;
	}
	
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	
	
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color; //this returns (false/true) if an specific piece is different of other by opponent color  
	}
	
	
	
}
