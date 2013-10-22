package tfjgeorge.tictactoeception;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {

	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private ArrayList<Piece> won = new ArrayList<Piece>();
	private PlayEventListener playEventListener;
	private Coordinate playableSmallBoard;

	public BoardView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BoardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		drawGrid(canvas);
		drawPieces(canvas);
		drawPlayableBoard(canvas);
		drawWonBoards(canvas);

	}

	private void drawGrid(Canvas canvas) {
		for (int i = 0; i < 10; i++) {

			Paint paint = new Paint();
			int width = canvas.getWidth() - 2;

			paint.setStrokeWidth(1 + (i % 3 == 0 ? 1 : 0));
			canvas.drawLine(1 + i * width / 9, 0, 1 + i * width / 9, width,
					paint);
			canvas.drawLine(0, 1 + i * width / 9, width, 1 + i * width / 9,
					paint);
		}
	}

	private void drawPieces(Canvas canvas) {
		for (Piece piece : pieces) {
			Paint paint = new Paint();

			paint.setARGB(255, 255, 127 + 50 * piece.player, 0);
			paint.setAntiAlias(true);

			float squareWidth = canvas.getWidth() / 9;

			canvas.drawCircle((piece.column + 0.5f) * squareWidth + 1.5f,
					(piece.row + 0.5f) * squareWidth + 1.5f,
					squareWidth / 2 - 1, paint);
		}
	}

	private void drawPlayableBoard(Canvas canvas) {
		if (playableSmallBoard != null) {
			Paint paint = new Paint();
			paint.setARGB(30, 100, 100, 100);
			float squareWidth = canvas.getWidth() / 9;

			canvas.drawRect(playableSmallBoard.column * 3 * squareWidth,
					playableSmallBoard.row * 3 * squareWidth,
					(playableSmallBoard.column + 1) * 3 * squareWidth,
					(playableSmallBoard.row + 1) * 3 * squareWidth, paint);
		}
	}

	private void drawWonBoards(Canvas canvas) {
		Paint paint = new Paint();
		float squareWidth = canvas.getWidth() / 9;

		for (Piece board : won) {

			paint.setARGB(30, 255, 127 + 50 * board.player, 0);

			canvas.drawRect(board.column * 3 * squareWidth, board.row * 3
					* squareWidth, (board.column + 1) * 3 * squareWidth,
					(board.row + 1) * 3 * squareWidth, paint);
		}

	}

	public void addPiece(Piece piece) {
		pieces.add(piece);
	}

	public void addWonBoard(Piece piece) {
		won.add(piece);
	}

	public void setPlayableSmallBoard(Coordinate playableBoard) {
		playableSmallBoard = playableBoard;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int column = (int) (event.getX() / getWidth() * 9);
		int row = (int) (event.getY() / getWidth() * 9);

		if (playEventListener != null) {
			playEventListener.onEvent(row, column);
		}

		return super.onTouchEvent(event);
	};

	public void setPlayEventListener(PlayEventListener eventListener) {
		playEventListener = eventListener;

	}

}
