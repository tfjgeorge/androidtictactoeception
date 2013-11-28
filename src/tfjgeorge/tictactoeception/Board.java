package tfjgeorge.tictactoeception;

public class Board {

	private int[][] grid = new int[9][9];
	private int[][] bigGrid = new int[3][3];

	public Board() {
	}

	public int get(int row, int column) {
		return grid[row][column];
	};

	public int get(Coordinate coordinate) {
		return get(coordinate.row, coordinate.column);
	};

	public int getBig(int row, int column) {
		return bigGrid[row][column];
	};

	public int getBig(Coordinate coordinate) {
		return getBig(coordinate.row, coordinate.column);
	};

	public int[][] getGrid() {
		return grid;
	}

	public int[][] getBigGrid() {
		return bigGrid;
	}

	public void set(int row, int column, int player) {
		grid[row][column] = player;
	}

	public void set(Piece piece) {
		set(piece.row, piece.column, piece.player);
	};

	public void setBig(int row, int column, int player) {
		bigGrid[row][column] = player;
	}

	public void setBig(Piece piece) {
		setBig(piece.row, piece.column, piece.player);
	};

}
