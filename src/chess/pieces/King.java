package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	
	//constructors
	public King(Board board, Color color) {
		super(board, color);
	}

	
	//methods
	
	@Override
	public String toString() {
		return "K";
	}
}
