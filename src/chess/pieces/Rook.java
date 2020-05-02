package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece { //TORRE

	
	//constructors
	public Rook(Board board, Color color) {
		super(board, color);
	}

	
	//methods
	
	@Override
	public String toString() {
		return "R";
	}
}