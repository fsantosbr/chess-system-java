package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch; //This class has an association with ChessMatch class | 
	
	
	//constructors
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
	
	
	private boolean testRookCastling(Position position) { //Method to test if a Rook is OK to make the special move with the King
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0; //'instanceof' helps us identify if a piece in a certain position is a Rook | The remain conditions check if is friend piece and so on  
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
	
			// Special move castling 
			if (getMoveCount() == 0 && !chessMatch.getCheck()) { //'!chessMatch.getCheck()' means "and the piece is not in check"
				
				//Special move for kingside rook
				Position posT1 = new Position(position.getRow(), position.getColumn() + 3); //getting the position of the Rook beside the King
				if (testRookCastling(posT1));{
					Position p1 = new Position(position.getRow(), position.getColumn() + 1); //getting the 2 positions next to the king
					Position p2 = new Position(position.getRow(), position.getColumn() + 2);
					if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
						mat[position.getRow()][position.getColumn() + 2] = true; // making the position available for a move
					}
				}
				
				//Special move for Queengside rook
				Position posT2 = new Position(position.getRow(), position.getColumn() - 4); //getting the position of the Rook beside the King
				if (testRookCastling(posT2));{
					Position p1 = new Position(position.getRow(), position.getColumn() - 1); //getting the 3 positions next to the king
					Position p2 = new Position(position.getRow(), position.getColumn() - 2);
					Position p3 = new Position(position.getRow(), position.getColumn() - 3);
					if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
						mat[position.getRow()][position.getColumn() - 2] = true;
					}
				}
			}
			
		return mat;
	}
}
