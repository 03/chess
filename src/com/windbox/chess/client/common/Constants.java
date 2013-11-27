package com.windbox.chess.client.common;

public interface Constants {

	/**
	 * Position
	 */
	public static final String A1 = "a1";
	public static final String B1 = "b1";
	public static final String C1 = "c1";
	public static final String D1 = "d1";
	public static final String E1 = "e1";
	public static final String F1 = "f1";
	public static final String G1 = "g1";
	public static final String H1 = "h1";
	public static final String A2 = "a2";
	public static final String B2 = "b2";
	public static final String C2 = "c2";
	public static final String D2 = "d2";
	public static final String E2 = "e2";
	public static final String F2 = "f2";
	public static final String G2 = "g2";
	public static final String H2 = "h2";
	public static final String A3 = "a3";
	public static final String B3 = "b3";
	public static final String C3 = "c3";
	public static final String D3 = "d3";
	public static final String E3 = "e3";
	public static final String F3 = "f3";
	public static final String G3 = "g3";
	public static final String H3 = "h3";
	public static final String A4 = "a4";
	public static final String B4 = "b4";
	public static final String C4 = "c4";
	public static final String D4 = "d4";
	public static final String E4 = "e4";
	public static final String F4 = "f4";
	public static final String G4 = "g4";
	public static final String H4 = "h4";
	public static final String A5 = "a5";
	public static final String B5 = "b5";
	public static final String C5 = "c5";
	public static final String D5 = "d5";
	public static final String E5 = "e5";
	public static final String F5 = "f5";
	public static final String G5 = "g5";
	public static final String H5 = "h5";
	public static final String A6 = "a6";
	public static final String B6 = "b6";
	public static final String C6 = "c6";
	public static final String D6 = "d6";
	public static final String E6 = "e6";
	public static final String F6 = "f6";
	public static final String G6 = "g6";
	public static final String H6 = "h6";
	public static final String A7 = "a7";
	public static final String B7 = "b7";
	public static final String C7 = "c7";
	public static final String D7 = "d7";
	public static final String E7 = "e7";
	public static final String F7 = "f7";
	public static final String G7 = "g7";
	public static final String H7 = "h7";
	public static final String A8 = "a8";
	public static final String B8 = "b8";
	public static final String C8 = "c8";
	public static final String D8 = "d8";
	public static final String E8 = "e8";
	public static final String F8 = "f8";
	public static final String G8 = "g8";
	public static final String H8 = "h8";
	
	/**
	 * Side
	 */
	static final int SIDE_WHITE = 0;
	static final int SIDE_BLACK = 1;
	
	static final String WHITE = "white";
	static final String BLACK = "gray";
	static final String HIGHLIGHT = "yellow";
	
	static final String PAWN = "Pawn";
	static final String KNIGHT = "Knight";
	static final String BISHOP = "Bishop";
	static final String ROOK = "Rook";
	static final String QUEEN = "Queen";
	static final String KING = "King";
	
	static final int STATUS_NORMAL = 0;
	static final int STATUS_KILLED = 1;
	
	static int iInitGridLength = 64; // 64

	static String[][] ChessBoardGridNames = new String[][] {
		{ A8, B8, C8, D8, E8, F8, G8, H8 },
		{ A7, B7, C7, D7, E7, F7, G7, H7 },
		{ A6, B6, C6, D6, E6, F6, G6, H6 },
		{ A5, B5, C5, D5, E5, F5, G5, H5 },
		{ A4, B4, C4, D4, E4, F4, G4, H4 },
		{ A3, B3, C3, D3, E3, F3, G3, H3 },
		{ A2, B2, C2, D2, E2, F2, G2, H2 },
		{ A1, B1, C1, D1, E1, F1, G1, H1 } };
	
	
	/*for (int i = 0; i < chessPanelGrid.length; i++) {
		for (int j = 0; j < chessPanelGrid[i].length; j++) {
			System.out.println("chessPanelGrids[" + i + "][" + j + "]=" + chessPanelGrid[i][j]);
		}
	}*/
}
