package com.windbox.chess.client.element;

import java.util.ArrayList;
import java.util.List;

import static com.windbox.chess.client.common.Constants.*;

public class Pawn extends ChessPiece {

	
	public Pawn(int side) {
		super(side, PAWN, null);
	}
	
	public Pawn(int side, String initPos) {
		this(side);
		this.setInitPosition(initPos);
	}

	/**
	 * Pawns have the most complex rules of movement:
	 * 	A pawn can move forward one square, if that square is unoccupied. 
	 * 		If it has not yet moved, each pawn has the option of moving two squares 
	 * 		forward provided both squares in front of the pawn are unoccupied. 
	 * 		A pawn cannot move backwards.
	 * 	Pawns are the only pieces that capture differently from how they move. They can 
	 * 		capture an enemy piece on either of the two spaces adjacent to the space in 
	 * 		front of them (i.e., the two squares diagonally in front of them) but cannot 
	 * 		move to these spaces if they are vacant.
	 * 	The pawn is also involved in the two special moves en passant and promotion
	 */
	@Override
	public List<String> getAvailableMoves() {

		String coord = ChessBoard.getCoordinate(getCurrentPosition());
		int x = ChessBoard.getX(coord);
		int y = ChessBoard.getY(coord);

		List<String> moves = new ArrayList<String>();
		String coordTmp = "";

		if (SIDE_WHITE == getSide()) {
			
			if (!hasMoved) {
				coordTmp = x + "," + (y + 2);
				moves.add(ChessBoard.getGridName(coordTmp));
			}

			if ((y + 1) <= 8) {
				coordTmp = x + "," + (y + 1);
				moves.add(ChessBoard.getGridName(coordTmp));
			}
			
		} else {
			
			if (!hasMoved) {
				coordTmp = x + "," + (y - 2);
				moves.add(ChessBoard.getGridName(coordTmp));
			}

			if ((y + 1) <= 8) {
				coordTmp = x + "," + (y - 1);
				moves.add(ChessBoard.getGridName(coordTmp));
			}
			
		}
		
		
		/*
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
		*/

		return moves;
	}

}
