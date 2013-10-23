package tfjgeorge.tictactoeception;

public class HumanPlayer implements Player {

	TicTacToeGame game;
	boolean myTurn = false;

	public HumanPlayer(BoardView boardView, TicTacToeGame game) {

		this.game = game;

		boardView.setPlayEventListener(new PlayEventListener() {

			@Override
			public void onEvent(int row, int column) {
				play(row, column);

			}
		});
	}

	@Override
	public void requestPlay() {
		myTurn = true;
		// TODO Auto-generated method stub

	}

	public void play(int row, int column) {
		Piece piece = new Piece(row, column, 1);
		if (myTurn && game.playable(piece)) {
			myTurn = false;
			game.play(piece);
		}
	}

}
