package com.windbox.chess.client.element;

import java.util.ArrayList;
import java.util.List;

import com.windbox.chess.client.common.Constants;

public class King extends ChessPiece {

	public King(int side) {
		super(side, Constants.KING, null);
	}

	public King(int side, String initPos) {
		this(side);
		this.setInitPosition(initPos);
	}

	/**
	 * The king can move exactly one square horizontally, vertically, or
	 * diagonally. Only once per player, per game, is a king allowed to make a
	 * special move known as castling
	 */
	@Override
	public List<String> getAvailableMoves() {

		String coord = ChessBoard.getCoordinate(getCurrentPosition());
		int x = ChessBoard.getX(coord);
		int y = ChessBoard.getY(coord);

		List<String> moves = new ArrayList<String>();
		String coordTmp = "";

		// up(north)
		if ((y + 1) <= 8) {
			coordTmp = x + "," + (y + 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		// down(south)
		if ((y - 1) >= 1) {
			coordTmp = x + "," + (y - 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		// right(east)
		if ((x + 1) <= 8) {
			coordTmp = (x + 1) + "," + y;
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		// left(west)
		if ((x - 1) >= 1) {
			coordTmp = (x - 1) + "," + y;
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		// northeast
		if ((y + 1) <= 8 && (x + 1) <= 8) {
			coordTmp = (x + 1) + "," + (y + 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		// northwest
		if ((y + 1) <= 8 && (x - 1) >= 1) {
			coordTmp = (x - 1) + "," + (y + 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		// southeast
		if ((y - 1) >= 1 && (x + 1) <= 8) {
			coordTmp = (x + 1) + "," + (y - 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		// southwest
		if ((y - 1) >= 1 && (x - 1) >= 1) {
			coordTmp = (x - 1) + "," + (y - 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}

		return moves;
	}

}
