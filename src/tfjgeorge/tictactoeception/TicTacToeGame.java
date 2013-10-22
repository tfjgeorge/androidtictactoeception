package tfjgeorge.tictactoeception;

public class TicTacToeGame {

	private BoardView boardView;
	private int player = 1;
	private Coordinate playableBoard;
	private int[][] grid = new int[9][9];
	private int[][] bigGrid = new int[3][3];
	private int finalWinner = 0;

	public TicTacToeGame(BoardView boardView) {

		this.boardView = boardView;
		boardView.setPlayEventListener(new PlayEventListener() {

			@Override
			public void onEvent(int row, int column) {
				play(row, column);

			}
		});

	}

	private void play(int row, int column) {

		Piece piece = new Piece(row, column, player);

		if (playable(piece)) {

			grid[row][column] = player;

			boardView.addPiece(piece);
			int winner = checkWinSmall(piece);
			if (winner != 0) {
				bigGrid[(int) row / 3][(int) column / 3] = winner;
				System.out.println(winner + " won board (" + (int) row / 3
						+ "," + (int) column / 3 + ")");

				Piece wonBoard = new Piece((int) row / 3, (int) column / 3,
						winner);
				boardView.addWonBoard(wonBoard);

				finalWinner = checkWinBig(wonBoard);
				if (finalWinner != 0) {
					System.out.println("Final winner " + finalWinner);
				}
			}

			playableBoard = new Coordinate(row % 3, column % 3);
			if (bigGrid[playableBoard.row][playableBoard.column] != 0) {
				playableBoard = null;
			}
			boardView.setPlayableSmallBoard(playableBoard);

			player = -player;

			boardView.postInvalidate();
		} else {
			// TODO This piece is not playable
		}
	}

	// 0: no win
	// 1: 1 won
	// -1: -1 won
	private int checkWin(Piece piece, int[][] grid, int factor) {

		int row = (int) piece.row / 3;
		int column = (int) piece.column / 3;

		for (int i = 0; i < 3; i++) {
			if (grid[row * factor + i][column * 3] != 0
					&& grid[row * factor + i][column * factor] == grid[row
							* factor + i][column * factor + 1]
					&& grid[row * factor + i][column * factor + 1] == grid[row
							* factor + i][column * factor + 2])
				return grid[row * factor + i][column * factor];
			if (grid[row * factor][column * factor + i] != 0
					&& grid[row * factor][column * factor + i] == grid[row
							* factor + 1][column * factor + i]
					&& grid[row * factor + 1][column * factor + i] == grid[row
							* factor + 2][column * factor + i])
				return grid[row * factor][column * factor + i];
		}

		if (grid[row * factor][column * factor] != 0
				&& grid[row * factor][column * factor] == grid[row * factor + 1][column
						* factor + 1]
				&& grid[row * factor + 2][column * factor + 2] == grid[row
						* factor + 1][column * factor + 1])
			return grid[row * factor][column * factor];

		if (grid[row * factor + 2][column * factor] != 0
				&& grid[row * factor + 2][column * factor] == grid[row * factor
						+ 1][column * factor + 1]
				&& grid[row * factor][column * factor + 2] == grid[row * factor
						+ 1][column * factor + 1])
			return grid[row * factor + 1][column * factor + 1];

		return 0;
	}

	private int checkWinSmall(Piece piece) {
		return checkWin(piece, grid, 3);
	}

	private int checkWinBig(Piece piece) {
		return checkWin(piece, bigGrid, 1);
	}

	private boolean playable(Piece piece) {

		if (finalWinner != 0)
			return false;

		if (grid[piece.row][piece.column] != 0)
			return false;
		if (bigGrid[(int) piece.row / 3][(int) piece.column / 3] != 0)
			return false;

		if (playableBoard != null
				&& ((int) (piece.row / 3) != playableBoard.row || (int) (piece.column / 3) != playableBoard.column))
			return false;

		return true;
	}
}
