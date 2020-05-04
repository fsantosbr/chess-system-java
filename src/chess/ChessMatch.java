package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	
	//constructors
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
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
	
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece)capturedPiece; //downcasting from superclass (Piece) to subclass (ChessPiece
	}
	
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target); //removing a possible piece already in the target position;
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) { //This '!'(not) returns a boolean
			throw new ChessException("There is no piece on source position!");
		}
		if (!board.piece(position).isThereAnyPossibleMove()){ //if not. We're negating
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
		
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
		//board.placePiece(new King(board, Color.WHITE), new Position(2, 0));
	}
	


}
