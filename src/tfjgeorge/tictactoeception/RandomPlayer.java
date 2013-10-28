package tfjgeorge.tictactoeception;

public class RandomPlayer implements Player {

	private TicTacToeGame game;
	int id;

	public RandomPlayer(TicTacToeGame game, int id) {
		this.id = id;
		this.game = game;
	}

	@Override
	public void requestPlay() {
		Piece piece = new Piece(rand(), rand(), id);
		while (!game.playable(piece)) {
			piece = new Piece(rand(), rand(), id);
		}
		game.play(piece);
	}

	private int rand() {
		return (int) (Math.random() * 9);
	}

}
