package tfjgeorge.tictactoeception;

public class RandomPlayer implements Player {

	private TicTacToeGame game;

	public RandomPlayer(TicTacToeGame game) {
		this.game = game;
	}

	@Override
	public void requestPlay() {
		Piece piece = new Piece(rand(), rand(), -1);
		while (!game.playable(piece)) {
			piece = new Piece(rand(), rand(), -1);
		}
		game.play(piece);
	}

	private int rand() {
		return (int) (Math.random() * 9);
	}

}
