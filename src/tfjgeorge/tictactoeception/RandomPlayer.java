package tfjgeorge.tictactoeception;

import java.util.ArrayList;

public class RandomPlayer implements Player {

	private TicTacToeGame game;
	int id;

	public RandomPlayer(TicTacToeGame game, int id) {
		this.id = id;
		this.game = game;
	}

	@Override
	public void requestPlay() {
		ArrayList<Coordinate> possiblePlays = game.getPossiblePlays();

		if (possiblePlays.size() > 0) {
			Coordinate emptyCoordinate = possiblePlays.get(rand(possiblePlays
					.size()));
			Piece piece = new Piece(emptyCoordinate.row,
					emptyCoordinate.column, id);

			game.play(piece);
		}
	}

	private int rand(int max) {
		return (int) (Math.random() * max);
	}

}
