package chess;

import boardgame.Board;

public class ChessMatch {
	
	private Board board;
	
	//constructors
	public ChessMatch() {
		board = new Board(8, 8);
	}
	
	
	//methods
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; //using ChessPiece here is possible because the Board class (being used here) has a relation with the Piece class, the superclass of ChessPiece. 'cause of that, we will be able to perform a casting 
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); //downcasting from superclass (Piece) to subclass (ChessPiece).
			}
		}
		return mat;
	}
	


}
