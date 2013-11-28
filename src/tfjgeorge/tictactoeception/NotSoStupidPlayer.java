package tfjgeorge.tictactoeception;

import java.util.ArrayList;

public class NotSoStupidPlayer implements Player {

	private TicTacToeGame game;
	int id;

	public NotSoStupidPlayer(TicTacToeGame game, int id) {
		this.id = id;
		this.game = game;
	}

	@Override
	public void requestPlay() {
		Piece piece;
		ArrayList<Coordinate> possiblePlays = game.getPossiblePlays();

		for (Coordinate coordinate : possiblePlays) {
			piece = new Piece(coordinate.row, coordinate.column, id);
			if (game.checkWinSmall(piece) == id) {
				game.play(piece);
				return;
			}
		}

		if (possiblePlays.size() > 0) {
			Coordinate emptyCoordinate = possiblePlays.get(rand(possiblePlays
					.size()));
			piece = new Piece(emptyCoordinate.row, emptyCoordinate.column, id);

			game.play(piece);
		}
	}

	private int rand(int max) {
		return (int) (Math.random() * max);
	}

}
