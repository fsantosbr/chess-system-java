package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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

	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor(); //Or the position is empty or the position has an opponent
	}
	
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//above - The above move
			p.setValues(position.getRow() - 1, position.getColumn()); //"position.getRow() - 1" means we will work with the row above the piece
			
			//This programming checks only the free position (up/above) - It will return false (stop) when a piece is there.
			if (getBoard().positionExists(p) && canMove(p)) { //if the position 'p' exists, we make the position true.
				mat[p.getRow()][p.getColumn()] = true;				
			}
						
		//below - The below move
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
		//Left - The left move
			p.setValues(position.getRow(), position.getColumn() - 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
		//Right - The right move
			p.setValues(position.getRow(), position.getColumn() + 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;				
			}
						
		//Diagonal - Northwest
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
		//Diagonal - Northeast
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
		//Diagonal - SW
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
		//Diagonal - SE
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;				
			}	
	
			
		return mat;
	}
}
