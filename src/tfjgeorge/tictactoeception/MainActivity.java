package tfjgeorge.tictactoeception;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BoardView boardView = (BoardView) findViewById(R.id.boardView);
		final TicTacToeGame game = new TicTacToeGame(boardView);

		final Activity context = this;
		game.setEndGameEventListener(new EndGameEventListener() {

			@Override
			public void onEvent(final int winner) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast toast = Toast.makeText(context, "Player " + winner
								+ " won.", Toast.LENGTH_LONG);
						toast.show();						
					}
				});
				
			}
		});

		Button startButton = (Button) findViewById(R.id.button1);

		startButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new AsyncPlay().execute(game);
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class AsyncPlay extends AsyncTask<TicTacToeGame, Integer, Integer> {

		@Override
		protected Integer doInBackground(TicTacToeGame... game) {
			// TODO Auto-generated method stub
			game[0].start();

			return null;
		}
	}

}
