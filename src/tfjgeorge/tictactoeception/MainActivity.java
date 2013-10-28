package tfjgeorge.tictactoeception;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BoardView boardView = (BoardView) findViewById(R.id.boardView);
		TicTacToeGame game = new TicTacToeGame(boardView);

		final Activity context = this;
		game.setEndGameEventListener(new EndGameEventListener() {

			@Override
			public void onEvent(int winner) {
				Toast toast = Toast.makeText(context, "Player " + winner
						+ " won.", Toast.LENGTH_LONG);
				toast.show();
			}
		});
		
		game.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
