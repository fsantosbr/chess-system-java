package boardgame;

public class Position {
	
	private int row;
	private int column;
	
	
	
	//constructors
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}


	
	//getters and setters
	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}
	
	
	//Methods
	
	@Override
	public String toString() {
		return row + ", " + column;
	}
	
	
	

}