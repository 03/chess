package com.windbox.chess.client.element;

import static com.windbox.chess.client.common.Constants.BLACK;
import static com.windbox.chess.client.common.Constants.ChessBoardGridNames;
import static com.windbox.chess.client.common.Constants.WHITE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/*
 * ChessBoard
 * 
 */
public class ChessBoard extends Canvas {

	private static HashMap<String, TableGrid> chessBoardGridMap = new HashMap<String, TableGrid>();
	private static ChessBoard instance = initInstance();
	private static List<ChessPiece> hiddenPieces = new ArrayList<ChessPiece>();
	
	private static ChessBoard initInstance() {
		
		chessBoardGridMap.clear();
		
		ChessBoard chessBoard = new ChessBoard();
		chessBoard.setShowEdges(true);
		
		// Create ChessPanel
		VLayout boardVLayout = new VLayout();
		
		String[][] chessBoardGridNames = ChessBoardGridNames;
		String blackBoardGridStrings = "a1,a3,a5,a7,c1,c3,c5,c7,e1,e3,e5,e7,g1,g3,g5,g7,b2,b4,b6,b8,d2,d4,d6,d8,f2,f4,f6,f8,h2,h4,h6,h8";
		
		for (int i = 0; i < chessBoardGridNames.length; i++) {
			
			HLayout panelHLayout = new HLayout();
			for (int j = 0; j < chessBoardGridNames[i].length; j++) {
				//System.out.println("chessPanelGrids[" + i + "][" + j + "]=" + chessPanelGrid[i][j]);
				String name = chessBoardGridNames[i][j];
				TableGrid cpg = TableGrid.createChessBoardGrid(name);
				
				if(blackBoardGridStrings.indexOf(name)>=0) {
					cpg.setDefaultColor(BLACK);
				} else {
					cpg.setDefaultColor(WHITE);
				}
				
				panelHLayout.addMember(cpg);
				chessBoardGridMap.put(name, cpg);
			}
			
			boardVLayout.addMember(panelHLayout);
		}
		
		chessBoard.addChild(boardVLayout);
		return chessBoard;
	}

	private ChessBoard() {
		super();
	}
	
	public static ChessBoard getInstance() {
		return instance;
	}

	public static void setInstance(ChessBoard instance) {
		ChessBoard.instance = instance;
	}
	
	public static Collection<TableGrid> getChessBoardGrids() {
		return chessBoardGridMap.values();
	}

	public static HashMap<String, TableGrid> getChessBoardGridMap() {
		return chessBoardGridMap;
	}
	
	public static TableGrid getGrid(String pos) {
		return chessBoardGridMap.get(pos);
	}

	public static void setChessBoardGridMap(
			HashMap<String, TableGrid> chessBoardGridMap) {
		ChessBoard.chessBoardGridMap = chessBoardGridMap;
	}
	
	public static void addHiddenPiece(ChessPiece piece) {
		piece.hide();
		hiddenPieces.add(piece);
		instance.addChild(piece);
	}

	public static List<ChessPiece> getHiddenPieces() {
		return hiddenPieces;
	}
	
	public static List<ChessPiece> getPieces() {
		return hiddenPieces;
	}
	
	private static HashMap<String, String> coordinateMap = new HashMap<String, String>();
	private static HashMap<String, String> gridNameMap = new HashMap<String, String>();
	static {
		
		coordinateMap.put("a1", "1,1");
		coordinateMap.put("a2", "1,2");
		coordinateMap.put("a3", "1,3");
		coordinateMap.put("a4", "1,4");
		coordinateMap.put("a5", "1,5");
		coordinateMap.put("a6", "1,6");
		coordinateMap.put("a7", "1,7");
		coordinateMap.put("a8", "1,8");
		
		coordinateMap.put("b1", "2,1");
		coordinateMap.put("b2", "2,2");
		coordinateMap.put("b3", "2,3");
		coordinateMap.put("b4", "2,4");
		coordinateMap.put("b5", "2,5");
		coordinateMap.put("b6", "2,6");
		coordinateMap.put("b7", "2,7");
		coordinateMap.put("b8", "2,8");
		
		coordinateMap.put("c1", "3,1");
		coordinateMap.put("c2", "3,2");
		coordinateMap.put("c3", "3,3");
		coordinateMap.put("c4", "3,4");
		coordinateMap.put("c5", "3,5");
		coordinateMap.put("c6", "3,6");
		coordinateMap.put("c7", "3,7");
		coordinateMap.put("c8", "3,8");
		
		coordinateMap.put("d1", "4,1");
		coordinateMap.put("d2", "4,2");
		coordinateMap.put("d3", "4,3");
		coordinateMap.put("d4", "4,4");
		coordinateMap.put("d5", "4,5");
		coordinateMap.put("d6", "4,6");
		coordinateMap.put("d7", "4,7");
		coordinateMap.put("d8", "4,8");
		
		coordinateMap.put("e1", "5,1");
		coordinateMap.put("e2", "5,2");
		coordinateMap.put("e3", "5,3");
		coordinateMap.put("e4", "5,4");
		coordinateMap.put("e5", "5,5");
		coordinateMap.put("e6", "5,6");
		coordinateMap.put("e7", "5,7");
		coordinateMap.put("e8", "5,8");
		
		coordinateMap.put("f1", "6,1");
		coordinateMap.put("f2", "6,2");
		coordinateMap.put("f3", "6,3");
		coordinateMap.put("f4", "6,4");
		coordinateMap.put("f5", "6,5");
		coordinateMap.put("f6", "6,6");
		coordinateMap.put("f7", "6,7");
		coordinateMap.put("f8", "6,8");
		
		coordinateMap.put("g1", "7,1");
		coordinateMap.put("g2", "7,2");
		coordinateMap.put("g3", "7,3");
		coordinateMap.put("g4", "7,4");
		coordinateMap.put("g5", "7,5");
		coordinateMap.put("g6", "7,6");
		coordinateMap.put("g7", "7,7");
		coordinateMap.put("g8", "7,8");
		
		coordinateMap.put("h1", "8,1");
		coordinateMap.put("h2", "8,2");
		coordinateMap.put("h3", "8,3");
		coordinateMap.put("h4", "8,4");
		coordinateMap.put("h5", "8,5");
		coordinateMap.put("h6", "8,6");
		coordinateMap.put("h7", "8,7");
		coordinateMap.put("h8", "8,8");
		
		gridNameMap.put("1,1", "a1");
		gridNameMap.put("1,2", "a2");
		gridNameMap.put("1,3", "a3");
		gridNameMap.put("1,4", "a4");
		gridNameMap.put("1,5", "a5");
		gridNameMap.put("1,6", "a6");
		gridNameMap.put("1,7", "a7");
		gridNameMap.put("1,8", "a8");
				           
		gridNameMap.put("2,1", "b1");
		gridNameMap.put("2,2", "b2");
		gridNameMap.put("2,3", "b3");
		gridNameMap.put("2,4", "b4");
		gridNameMap.put("2,5", "b5");
		gridNameMap.put("2,6", "b6");
		gridNameMap.put("2,7", "b7");
		gridNameMap.put("2,8", "b8");
				           
		gridNameMap.put("3,1", "c1");
		gridNameMap.put("3,2", "c2");
		gridNameMap.put("3,3", "c3");
		gridNameMap.put("3,4", "c4");
		gridNameMap.put("3,5", "c5");
		gridNameMap.put("3,6", "c6");
		gridNameMap.put("3,7", "c7");
		gridNameMap.put("3,8", "c8");
				           
		gridNameMap.put("4,1", "d1");
		gridNameMap.put("4,2", "d2");
		gridNameMap.put("4,3", "d3");
		gridNameMap.put("4,4", "d4");
		gridNameMap.put("4,5", "d5");
		gridNameMap.put("4,6", "d6");
		gridNameMap.put("4,7", "d7");
		gridNameMap.put("4,8", "d8");
				           
		gridNameMap.put("5,1", "e1");
		gridNameMap.put("5,2", "e2");
		gridNameMap.put("5,3", "e3");
		gridNameMap.put("5,4", "e4");
		gridNameMap.put("5,5", "e5");
		gridNameMap.put("5,6", "e6");
		gridNameMap.put("5,7", "e7");
		gridNameMap.put("5,8", "e8");
				           
		gridNameMap.put("6,1", "f1");
		gridNameMap.put("6,2", "f2");
		gridNameMap.put("6,3", "f3");
		gridNameMap.put("6,4", "f4");
		gridNameMap.put("6,5", "f5");
		gridNameMap.put("6,6", "f6");
		gridNameMap.put("6,7", "f7");
		gridNameMap.put("6,8", "f8");
				           
		gridNameMap.put("7,1", "g1");
		gridNameMap.put("7,2", "g2");
		gridNameMap.put("7,3", "g3");
		gridNameMap.put("7,4", "g4");
		gridNameMap.put("7,5", "g5");
		gridNameMap.put("7,6", "g6");
		gridNameMap.put("7,7", "g7");
		gridNameMap.put("7,8", "g8");
				           
		gridNameMap.put("8,1", "h1");
		gridNameMap.put("8,2", "h2");
		gridNameMap.put("8,3", "h3");
		gridNameMap.put("8,4", "h4");
		gridNameMap.put("8,5", "h5");
		gridNameMap.put("8,6", "h6");
		gridNameMap.put("8,7", "h7");
		gridNameMap.put("8,8", "h8");

	}
	
	public static String getCoordinate(String gridName) {
		return coordinateMap.get(gridName);
	}
	
	public static String getGridName(String coord) {
		return gridNameMap.get(coord);
	}
	
	public static int getX(String coord) {

		try {
			return Integer.parseInt(coord.split(",")[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static int getY(String coord) {
		try {
			return Integer.parseInt(coord.split(",")[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static void hideTips() {
		Collection<TableGrid> grids = getChessBoardGrids();
		for (TableGrid grid : grids) {
			grid.hidePieceImg();
		}
	}
	
}
