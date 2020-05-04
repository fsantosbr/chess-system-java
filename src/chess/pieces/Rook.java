package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//above - The above moves
			p.setValues(position.getRow() - 1, position.getColumn()); //"position.getRow() - 1" means we will start working with the row above the piece
			
			//This programming checks only the free limit (up/above) this piece can go - It will return false (stop) when a piece is there.
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //while the position 'p' exists and there's no piece in it, we make the position true and decrease one row.
				mat[p.getRow()][p.getColumn()] = true;
				p.setRow(p.getRow() - 1);
			}			
			//after checking the free positions, this programming will check if the occupied position has an opponent piece (making capturing available).
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
		//left - The left moves
			p.setValues(position.getRow(), position.getColumn() -1);
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
				p.setColumn(p.getColumn() - 1);
			}			
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		
			
		//left - The right moves
			p.setValues(position.getRow(), position.getColumn() +1);
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
				p.setColumn(p.getColumn() + 1);
			}			
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
		//below - The below moves
			p.setValues(position.getRow() + 1, position.getColumn());
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
				p.setRow(p.getRow() + 1);
			}			
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
						
		return mat;
	}
}
