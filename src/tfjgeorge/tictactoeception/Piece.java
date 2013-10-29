package tfjgeorge.tictactoeception;

public class Piece {

	public int row;
	public int column;
	public int player;

	public Piece(int row, int column, int player) {
		this.row = row;
		this.column = column;
		this.player = player;
	}
	
	public Coordinate getCoordinate() {
		return new Coordinate(row, column);
	}

}
