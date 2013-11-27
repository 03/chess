package com.windbox.chess.client.element;

import java.util.ArrayList;
import java.util.List;

import com.windbox.chess.client.common.Constants;

public class Queen extends ChessPiece {

	public Queen(int side) {
		super(side, Constants.QUEEN, null);
	}

	public Queen(int side, String initPos) {
		this(side);
		this.setInitPosition(initPos);
	}

	/**
	 * The queen can move any number of vacant squares diagonally, horizontally,
	 * or vertically.
	 */
	@Override
	public List<String> getAvailableMoves() {

		String coord = ChessBoard.getCoordinate(getCurrentPosition());
		int x = ChessBoard.getX(coord);
		int y = ChessBoard.getY(coord);

		List<String> moves = new ArrayList<String>();
		String coordTmp = "";

		// vertically (x stays the same)
		for (int i = 1; i <= 8; i++) {
			if (i == y)
				continue;

			coordTmp = x + "," + i;
			moves.add(ChessBoard.getGridName(coordTmp));
		}

		// horizontally (y stays the same)
		for (int i = 1; i <= 8; i++) {
			if (i == x)
				continue;

			coordTmp = i + "," + y;
			moves.add(ChessBoard.getGridName(coordTmp));
		}

		// northeast (x+,y+)
		for (int i = 1; i <= 8; i++) {
			if ((y + i) <= 8 && (x + i) <= 8) {
				coordTmp = (x + i) + "," + (y + i);
				moves.add(ChessBoard.getGridName(coordTmp));
			}
		}
		// northwest (x-,y+)
		for (int i = 1; i <= 8; i++) {
			if ((y + i) <= 8 && (x - i) >= 1) {
				coordTmp = (x - i) + "," + (y + i);
				moves.add(ChessBoard.getGridName(coordTmp));
			}
		}
		// southeast (x+,y-)
		for (int i = 1; i <= 8; i++) {
			if ((y - i) >= 1 && (x + i) <= 8) {
				coordTmp = (x + i) + "," + (y - i);
				moves.add(ChessBoard.getGridName(coordTmp));
			}
		}
		// southwest (x-,y-)
		for (int i = 1; i <= 8; i++) {
			if ((y - i) >= 1 && (x - i) >= 1) {
				coordTmp = (x - i) + "," + (y - i);
				moves.add(ChessBoard.getGridName(coordTmp));
			}
		}

		return moves;
	}
}
