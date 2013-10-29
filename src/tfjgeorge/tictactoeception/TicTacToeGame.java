package tfjgeorge.tictactoeception;

import java.util.ArrayList;

public class TicTacToeGame {

	private BoardView boardView;
	private Board board;
	private int player = 1;
	private Coordinate playableBoard;
	private int finalWinner = 0;
	private Player player1, player2;
	private EndGameEventListener endListener;
	private ArrayList<Coordinate> possiblePlays;

	public TicTacToeGame(BoardView boardView) {

		// player1 = new HumanPlayer(boardView, this);
		player1 = new RandomPlayer(this, 1);
		player2 = new NotSoStupidPlayer(this, 2);

		this.boardView = boardView;

	}

	public void start() {
		this.board = new Board();
		updatePossiblePlays();
		player1.requestPlay();
	};

	public void play(Piece piece) {

		if (playable(piece)) {

			board.set(piece);

			boardView.addPiece(piece);
			int winner = checkWinSmall(piece);
			if (winner != 0) {
				board.setBig((int) piece.row / 3, (int) piece.column / 3,
						winner);
				System.out.println(winner + " won board (" + (int) piece.row
						/ 3 + "," + (int) piece.column / 3 + ")");

				Piece wonBoard = new Piece((int) piece.row / 3,
						(int) piece.column / 3, winner);
				boardView.addWonBoard(wonBoard);

				finalWinner = checkWinBig(wonBoard);
				if (finalWinner > 0) {
					System.out.println("Final winner " + finalWinner);

					if (endListener != null) {
						endListener.onEvent(finalWinner);
					}

					boardView.postInvalidate();
					return;
				}

			}

			playableBoard = new Coordinate(piece.row % 3, piece.column % 3);
			if (board.getBig(playableBoard) != 0) {
				playableBoard = null;
			}
			boardView.setPlayableSmallBoard(playableBoard);
			updatePossiblePlays();
			boardView.postInvalidate();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			player = 3 - piece.player;
			if (player == 2) {
				player2.requestPlay();
			} else {
				player1.requestPlay();
			}

		} else {
			// TODO This piece is not playable
		}

	}

	private void updatePossiblePlays() {
		possiblePlays = new ArrayList<Coordinate>();
		Coordinate coordinate;

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				coordinate = new Coordinate(row, col);
				if (playable(coordinate)) {
					possiblePlays.add(coordinate);
				}
			}
		}
	}

	public ArrayList<Coordinate> getPossiblePlays() {
		return possiblePlays;
	}

	// 0: no win
	// 1: 1 won
	// -1: -1 won
	public int checkWin(Piece piece, int[][] grid, int factor) {

		int savedState = grid[piece.row][piece.column];
		grid[piece.row][piece.column] = piece.player;

		int row = (int) piece.row / 3;
		int column = (int) piece.column / 3;
		int winner;

		for (int i = 0; i < 3; i++) {
			// Row
			if (grid[row * factor + i][column * 3] != 0
					&& grid[row * factor + i][column * factor] == grid[row
							* factor + i][column * factor + 1]
					&& grid[row * factor + i][column * factor + 1] == grid[row
							* factor + i][column * factor + 2]) {
				winner = grid[row * factor + i][column * factor];
				grid[piece.row][piece.column] = savedState;
				return winner;
			}
			// Column
			if (grid[row * factor][column * factor + i] != 0
					&& grid[row * factor][column * factor + i] == grid[row
							* factor + 1][column * factor + i]
					&& grid[row * factor + 1][column * factor + i] == grid[row
							* factor + 2][column * factor + i]) {
				winner = grid[row * factor][column * factor + i];
				grid[piece.row][piece.column] = savedState;
				return winner;
			}
		}

		// Diagonals
		if (grid[row * factor][column * factor] != 0
				&& grid[row * factor][column * factor] == grid[row * factor + 1][column
						* factor + 1]
				&& grid[row * factor + 2][column * factor + 2] == grid[row
						* factor + 1][column * factor + 1]) {
			winner = grid[row * factor][column * factor];
			grid[piece.row][piece.column] = savedState;
			return winner;
		}

		if (grid[row * factor + 2][column * factor] != 0
				&& grid[row * factor + 2][column * factor] == grid[row * factor
						+ 1][column * factor + 1]
				&& grid[row * factor][column * factor + 2] == grid[row * factor
						+ 1][column * factor + 1]) {
			winner = grid[row * factor + 1][column * factor + 1];
			grid[piece.row][piece.column] = savedState;
			return winner;
		}

		int empty = -1;

		for (int i = 0; i < 3 && empty == -1; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[row * factor + i][column * factor + j] == 0) {
					empty = 0;
					break;
				}
			}
		}

		grid[piece.row][piece.column] = savedState;
		
		return empty;
	}

	public int checkWinSmall(Piece piece) {
		return checkWin(piece, board.getGrid(), 3);
	}

	public int checkWinBig(Piece piece) {
		return checkWin(piece, board.getBigGrid(), 1);
	}

	public boolean playable(Piece piece) {
		return playable(piece.getCoordinate());
	}

	public boolean playable(Coordinate coordinate) {

		if (finalWinner != 0)
			return false;

		if (board.get(coordinate) != 0)
			return false;

		if (board.getBig((int) coordinate.row / 3, (int) coordinate.column / 3) != 0)
			return false;

		if (playableBoard != null
				&& ((int) (coordinate.row / 3) != playableBoard.row || (int) (coordinate.column / 3) != playableBoard.column))
			return false;

		return true;
	}

	public void setEndGameEventListener(EndGameEventListener listener) {
		endListener = listener;
	}
}
