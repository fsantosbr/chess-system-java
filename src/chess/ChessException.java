package chess;

import boardgame.BoardException;

public class ChessException extends BoardException { //capturing both ChessException and BoardException
	private static final long serialVersionUID = 1L;
	
	public ChessException(String msg) {
		super(msg);
	}

}
