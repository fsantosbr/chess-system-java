package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

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

	
	//methods
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	
	
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color; //this returns (false/true) if an specific piece is different of other by opponent color  
	}
	
	
	
}
