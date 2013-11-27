package com.windbox.chess.client.element;

import java.util.ArrayList;
import java.util.List;

import com.windbox.chess.client.common.Constants;

public class Bishop extends ChessPiece {

	public Bishop(int side) {
		super(side, Constants.BISHOP, null);
	}

	public Bishop(int side, String initPos) {
		this(side);
		this.setInitPosition(initPos);
	}

	/**
	 * The bishop moves any number of vacant squares in any diagonal direction.
	 * 
	 * TODO: loop count can be improved later
	 */
	@Override
	public List<String> getAvailableMoves() {

		String coord = ChessBoard.getCoordinate(getCurrentPosition());
		int x = ChessBoard.getX(coord);
		int y = ChessBoard.getY(coord);

		List<String> moves = new ArrayList<String>();
		String coordTmp = "";

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
