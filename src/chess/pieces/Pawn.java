package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	
	private ChessMatch chessMatch;
	
	//Constructors
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	
	//Methods
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		if (getColor() == Color.WHITE) { //Checking the color. Each color has a different move on the matrix 
			
			//WHITE - The pawns are on the bottom of the board.
			// MOVE UP
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //if the position exists on the board and there's no piece there.
				mat[p.getRow()][p.getColumn()] = true; //the pawn can go to this position
			}
			//This next programming is for 2 moves (The pawn can move 2 position if it is its first move)
			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) { //if these positions exist on the board and there's no pieces there and it's the first move.
				mat[p.getRow()][p.getColumn()] = true; //the pawn can go to this position
			}
			
			// MOVE Diagonals to capture piece (Rule = The pawn can only move diagonal if it desires to capture a piece
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //.NEW
				
				/* maybe the cod to avoid capture the King
				ChessPiece a = (ChessPiece)getBoard().piece(p);
				if (!(a instanceof King)) {
					mat[p.getRow()][p.getColumn()] = true;
				}*/
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// Special move en passant
			if (position.getRow() == 3) { //The white pawn can only make a passant move on the row 5 (3 on the matrix).
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
		}
		else {
			//BLACK - The pawns are on the top of the board
			// MOVE DOWN
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//This next programming is for 2 moves (The pawn can move 2 position if it is its first move)
			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) { //if these positions exist on the board and there's no pieces there and it's the first move.
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// MOVE Diagonals to capture piece (Rule = The pawn can only move diagonal if it desires to capture a piece
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //.
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// Special move en passant
			if (position.getRow() == 4) { //The black pawn can only make a passant move on the row 4 (4 on the matrix).
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		return mat;
	}
	
	
	
	@Override
	public String toString() {
		return "P";
	}

}
