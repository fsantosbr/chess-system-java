package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check; // false by default - No need to insert into constructor
	private boolean checkMate;
	private ChessPiece enPassantVulnerable; // null by default - No need to insert into constructor
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	
	//constructors
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
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
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
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
		
		ChessPiece movedPiece = ((ChessPiece)board.piece(target)); //Copying the piece moved to a new variable - We'll use this later for the "en passant"
		
		// Special move - Promotion
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if (movedPiece.getColor() == Color.WHITE && target.getRow() == 0 || movedPiece.getColor() == Color.BLACK && target.getRow() == 7) {
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q"); // O user will insert a string with the letter of the piece we want to replace. We're letting the Queen by default
			}
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) { //"If I made the opponent be in Check-Mate, the game must over". Testing the the check-mate of the opponent of the current player
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		// Special move - En passant
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		return (ChessPiece)capturedPiece; //downcasting from superclass (Piece) to subclass (ChessPiece
	}
	
	
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException("There's no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			return promoted;
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("N")) return new Knight(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}
	
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();	
		Piece capturedPiece = board.removePiece(target); //removing a possible piece already in the target position;
		board.placePiece(p, target); //Here will occur a natural upcasting
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);			
		}	
		
		// Special move - castling kingside Rook - Moving the Rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { //On the King class has the cods to allow the piece (King) make this exception of 2 moves.
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
				
		// Special move - castling Queenside Rook - Moving the Rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// Special move - En passant - The pawn
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) { // if the column is different and the pawn didn't captured a piece, means the pawn made a move in passant
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		return capturedPiece;
	}
	
	
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { //This method undone the movement made with the makeMove() method. Been used to avoid a player to put yourself in check.
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source); //Here will occur a natural upcasting
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// Undoing the Special move - castling kingside Rook - unMoving the Rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { 
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}	
						
		// Undoing the Special move - castling Queenside Rook - unMoving the Rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		// undoing Special move - En passant - The pawn
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				}
				else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
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
	
	
	
	private boolean testCheckMate(Color color) { //this method will check if the King is in Check-mate (false value means that is not in Check-Mate 
		if (!testCheck(color)) { //This first "if" tests if the piece itself is not in check. The King is also a piece.
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());	
		for (Piece p : list) { //These next cods will check if there's no piece for my player that can avoid the check-mate. If not, then the King is on Check-Mate. My pieces are taken by the color value above.
			boolean[][] mat = p.possibleMoves(); //possibleMoves() makes 'true' for the positions that a piece can go. These values are stored in the boolean matrix 'mat'
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					
					if(mat[i][j]) { //Remember: The 'if' itself test if an argument is true in the first place. Our variable mat has been received all the false/true positions for a piece. If it returns true, it's a possible move for the piece.
						
						//Now we're making the piece move to the possible position (tested above) and test (with "testCheck()") if the move avoid the check-mate
						Position source = ((ChessPiece)p).getChessPosition().toPosition(); //.toPosition() = convert a position | .getChessPosition() = take the position from the chess layer | But first we have to make a downcasting from Position to ChessPiece
						Position target = new Position(i, j); //The target position is the position that our "for" stopped because of the true in the "if".
						Piece capturedPiece = makeMove(source, target);
						
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece); //We need to unmade the move.
						
						if (!testCheck) { //if the piece is not in check anymore, it returns false
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); //When adding a new piece, the piece'll be set in the list of pieces created on the top
	}
	
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this)); //'this' means self reference to this class. The King constructor is waiting a value type ChessMatch class
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
        
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}