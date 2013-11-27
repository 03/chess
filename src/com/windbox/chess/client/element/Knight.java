package com.windbox.chess.client.element;

import java.util.ArrayList;
import java.util.List;

import com.windbox.chess.client.common.Constants;

public class Knight extends ChessPiece {

	public Knight(int side) {
		super(side, Constants.KNIGHT, null);
	}
	
	public Knight(int side, String initPos) {
		this(side);
		this.setInitPosition(initPos);
	}

	/**
	 * The knight moves to the nearest square not on the same rank, file, or diagonal. 
	 * In other words, the knight moves two squares horizontally then one square vertically, 
	 * or one square horizontally then two squares vertically. Its move is not blocked by 
	 * other pieces: it jumps to the new location.
	 */
	@Override
	public List<String> getAvailableMoves() {
		
		String coord = ChessBoard.getCoordinate(getCurrentPosition());
		int x = ChessBoard.getX(coord);
		int y = ChessBoard.getY(coord);

		List<String> moves = new ArrayList<String>();
		String coordTmp = "";

		// northeast
		if ((y + 2) <= 8 && (x + 1) <= 8) {
			coordTmp = (x + 1) + "," + (y + 2);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		if ((y + 1) <= 8 && (x + 2) <= 8) {
			coordTmp = (x + 2) + "," + (y + 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		
		// northwest
		if ((y + 2) <= 8 && (x - 1) >= 1) {
			coordTmp = (x - 1) + "," + (y + 2);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		if ((y + 1) <= 8 && (x - 2) >= 1) {
			coordTmp = (x - 2) + "," + (y + 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		
		// southeast
		if ((y - 2) >= 1 && (x + 1) <= 8) {
			coordTmp = (x + 1) + "," + (y - 2);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		if ((y - 1) >= 1 && (x + 2) <= 8) {
			coordTmp = (x + 2) + "," + (y - 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		
		// southwest
		if ((y - 2) >= 1 && (x - 1) >= 1) {
			coordTmp = (x - 1) + "," + (y - 2);
			moves.add(ChessBoard.getGridName(coordTmp));
		}
		if ((y - 1) >= 1 && (x - 2) >= 1) {
			coordTmp = (x - 2) + "," + (y - 1);
			moves.add(ChessBoard.getGridName(coordTmp));
		}

		return moves;
	}
}
