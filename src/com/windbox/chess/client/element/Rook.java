package com.windbox.chess.client.element;

import java.util.ArrayList;
import java.util.List;

import com.windbox.chess.client.common.Constants;

public class Rook extends ChessPiece {

	public Rook(int side) {
		super(side, Constants.ROOK, null);
	}

	public Rook(int side, String initPos) {
		this(side);
		this.setInitPosition(initPos);
	}

	/**
	 * The rook moves any number of vacant squares vertically or horizontally. It also is moved while castling.
	 */
	@Override
	public List<String> getAvailableMoves() {

		String coord = ChessBoard.getCoordinate(getCurrentPosition());
		int x = ChessBoard.getX(coord);
		int y = ChessBoard.getY(coord);

		List<String> moves = new ArrayList<String>();
		String coordTmp = "";

		// 1 vertically (x stays the same)
		for (int i = 1; i <= 8; i++) {
			if (i == y)
				continue;

			coordTmp = x + "," + i;
			moves.add(ChessBoard.getGridName(coordTmp));
		}

		// 2 horizontally (y stays the same)
		for (int i = 1; i <= 8; i++) {
			if (i == x)
				continue;

			coordTmp = i + "," + y;
			moves.add(ChessBoard.getGridName(coordTmp));
		}

		return moves;
	}
}
