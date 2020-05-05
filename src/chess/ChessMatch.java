package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	
	//constructors
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		//check = false; a boolean property is false by default. This does not makes difference;
		initialSetup();
	}
	
	
	//getters and setters	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	
	public boolean getCheck() {
		return check;
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
	
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validadeTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		nextTurn();
		return (ChessPiece)capturedPiece; //downcasting from superclass (Piece) to subclass (ChessPiece
	}
	
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target); //removing a possible piece already in the target position;
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);			
		}		
		return capturedPiece;
	}
	
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { //This method undone the movement made with the makeMove() method. Been used to avoid a player to put yourself in check.
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) { //This '!'(not) returns a boolean
			throw new ChessException("There is no piece on source position!");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { //We're taking the board piece in a position [ex: board.piece(position)], then making a downcasting to ChessPiece [ex: ((ChessPiece)board.piece(position))], then taking the color of the ChessPiece type in order to compare
			throw new ChessException("The chosen piece is not yours");
		}		
		if (!board.piece(position).isThereAnyPossibleMove()){ //if not. We're negating
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	
	private void validadeTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //Ternary condition
	}
	
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; //ternary condition in order to get the opponent of the current player
	}
	
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) { //instanceof is useful to check if an object is the same type of other before making a downcasting
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There's no " + color + " King on the board"); //This exception is from Java. We won't work with it because is meant to never happen cause in the game there are 2 colors only
	}
	
	
	
	private boolean testCheck(Color color) { //this method will scan all pieces from the opponent and check if they have free movements in order to catch the King being tested here
		Position kingPosition = king(color).getChessPosition().toPosition(); //getting the position of the King. king() method filters what pieces might be the King.
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList()); //this list lists all opponent pieces
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves(); //this boolean matrix catches all possible moves from a piece (opponent) and save the position as "true"
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { //Here we are validating if the King position is one of the possible moves above, if true, the King might be captured by an opponent
				return true;
			}
		}
		return false;
	}
	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); //When adding a new piece, the piece'll be set in the list of pieces created on the top
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
		
	}
	
}
