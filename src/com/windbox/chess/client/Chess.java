package com.windbox.chess.client;


import static com.windbox.chess.client.common.Constants.A1;
import static com.windbox.chess.client.common.Constants.A2;
import static com.windbox.chess.client.common.Constants.A7;
import static com.windbox.chess.client.common.Constants.A8;
import static com.windbox.chess.client.common.Constants.B1;
import static com.windbox.chess.client.common.Constants.B2;
import static com.windbox.chess.client.common.Constants.B7;
import static com.windbox.chess.client.common.Constants.B8;
import static com.windbox.chess.client.common.Constants.C1;
import static com.windbox.chess.client.common.Constants.C2;
import static com.windbox.chess.client.common.Constants.C7;
import static com.windbox.chess.client.common.Constants.C8;
import static com.windbox.chess.client.common.Constants.D1;
import static com.windbox.chess.client.common.Constants.D2;
import static com.windbox.chess.client.common.Constants.D7;
import static com.windbox.chess.client.common.Constants.D8;
import static com.windbox.chess.client.common.Constants.E1;
import static com.windbox.chess.client.common.Constants.E2;
import static com.windbox.chess.client.common.Constants.E7;
import static com.windbox.chess.client.common.Constants.E8;
import static com.windbox.chess.client.common.Constants.F1;
import static com.windbox.chess.client.common.Constants.F2;
import static com.windbox.chess.client.common.Constants.F7;
import static com.windbox.chess.client.common.Constants.F8;
import static com.windbox.chess.client.common.Constants.G1;
import static com.windbox.chess.client.common.Constants.G2;
import static com.windbox.chess.client.common.Constants.G7;
import static com.windbox.chess.client.common.Constants.G8;
import static com.windbox.chess.client.common.Constants.H1;
import static com.windbox.chess.client.common.Constants.H2;
import static com.windbox.chess.client.common.Constants.H7;
import static com.windbox.chess.client.common.Constants.H8;
import static com.windbox.chess.client.common.Constants.SIDE_BLACK;
import static com.windbox.chess.client.common.Constants.SIDE_WHITE;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.rpc.HandleErrorCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.windbox.chess.client.element.Bishop;
import com.windbox.chess.client.element.ChessBoard;
import com.windbox.chess.client.element.ChessPiece;
import com.windbox.chess.client.element.King;
import com.windbox.chess.client.element.Knight;
import com.windbox.chess.client.element.Pawn;
import com.windbox.chess.client.element.Queen;
import com.windbox.chess.client.element.Rook;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Chess implements EntryPoint {

	private static ListGrid stepList = new ListGrid();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		initPRCManager();
		
		final Canvas frame = new Canvas();
		frame.setWidth100();
		frame.setHeight100();
		
		final HLayout hLayout = new HLayout(5);
		hLayout.setHeight("30%");
		//frameCanvas.addChild(hLayout);

		final VLayout vLayout = new VLayout(5);
		vLayout.setWidth("70%");
		vLayout.setLeft(80);
		vLayout.setTop(36);
		
		Img pawnImg = new Img("pieces/48/pawn_green.png");
		pawnImg.setImageType(ImageStyle.CENTER);
		pawnImg.setDragAppearance(DragAppearance.TARGET);
		pawnImg.setCanDrop(true);
		pawnImg.setCanDrag(true);
		pawnImg.setLeft(80);
		pawnImg.setTop(80);
		
		//vLayout.addMember(pawnImg);
//		vLayout.addMember(getStackBlack());
		vLayout.addMember(ChessBoard.getInstance());
//		vLayout.addMember(getStackWhite());
//		frameCanvas.addChild(vLayout);
		
		hLayout.addMember(vLayout);
		
		initPieces();
		
		// gadget's position can only be calculated when you place it in canvas
		/*final ChessPiece pawn = new Pawn(Constants.SIDE_WHITE, "d2");
		ChessBoard.addHiddenPiece(pawn);
		
		final ChessPiece king = new King(Constants.SIDE_WHITE, "e1");
		ChessBoard.addHiddenPiece(king);*/
		
		VLayout innerVLayout = new VLayout(5);
		
		Img logoImg = new Img("logo.gif", 128, 128);
		logoImg.setImageHeight(100);
		logoImg.setImageWidth(100);
		logoImg.setImageType(ImageStyle.CENTER);
		logoImg.setShowResizeBar(true);
		innerVLayout.addMember(logoImg);
		
		//
		IButton initPosBtn = new IButton("New");
		initPosBtn.setLeft(120);
		initPosBtn.setTop(10);
		initPosBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				List<ChessPiece> list = ChessBoard.getHiddenPieces();
				for(ChessPiece piece : list) {
					piece.initPos();
				}
	
				getStepList().setData(new RecordList());
				
			}
		});
		innerVLayout.addMember(initPosBtn);

		IButton moveBtn = new IButton("Move");
		moveBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				/*pawn.moveToGrid(ChessBoard.getGrid("d4"), true);
				king.moveToGrid(ChessBoard.getGrid("e2"));*/
			}
		});
		innerVLayout.addMember(moveBtn);
		
		IButton resetBtn = new IButton("<b>Test</b>");
		resetBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				/*pawnImg2.setRect(100, 60, Constants.iInitGridLength,
						Constants.iInitGridLength);*/
				
				//moveToGrid(ChessBoard.getGrid("d4"));
				
				StepTestRecord[] records = StepTestData.getRecords();
				stepList.setData(records);
				
				List<ChessPiece> list = ChessBoard.getHiddenPieces();
				for(ChessPiece piece : list) {
					piece.initPos();
				}
				
				for(final StepTestRecord record : records) {
					
					makeMove(record.getWhiteStep());
					makeMove(record.getBlackStep());

					/*new Timer() {
						@Override
						public void run() {
							System.out.println("Silent! getWhiteStep!!");
							makeMove(record.getWhiteStep());
						}
					}.schedule(10);

					new Timer() {
						@Override
						public void run() {
							System.out.println("Silent! getBlackStep!!");
							makeMove(record.getBlackStep());
						}
					}.schedule(1000);*/
				}
				
			}
		});
		innerVLayout.addMember(resetBtn);
		

		initStepListGrid();
        innerVLayout.addMember(stepList);
		
        hLayout.addMember(innerVLayout);
		frame.addChild(hLayout);
		frame.draw();
	}
	
	public static void makeMove(String move) {

		String prevPos = "";
		String current = move.split("[-x]")[0];
		String nextPos = move.split("[-x]")[1];

		if (current.length() == 3) {
			// Pawn
			prevPos = current.substring(1);
		} else {
			prevPos = current;
		}

		List<ChessPiece> pieces = ChessBoard.getPieces();
		for (ChessPiece piece : pieces) {
			if (piece.getCurrentPosition().equals(prevPos)) {
				piece.moveToPosition(nextPos);
				break;
			}
		}
	    
	}

	private void initPRCManager() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable exception) {
				exception.printStackTrace();
			}
		});

		/*RPCManager.setShowPrompt(true);
		RPCManager.setDefaultTimeout(5000);
		RPCManager.setTimeoutErrorMessage("Sorry, server didn't respond in given time :(");
		RPCManager.setDefaultPrompt("hello, i'm contacting the server");
		RPCManager.setPromptStyle(PromptStyle.CURSOR);
		RPCRequest request = new RPCRequest();*/

		RPCManager.setHandleErrorCallback(new HandleErrorCallback() {

			@Override
			public void handleError(DSResponse response, DSRequest request) {

			}
		});

	}

	private void initPieces() {
		
		// White
		ChessBoard.addHiddenPiece(new Rook(SIDE_WHITE, A1));
		ChessBoard.addHiddenPiece(new Knight(SIDE_WHITE, B1));
		ChessBoard.addHiddenPiece(new Bishop(SIDE_WHITE, C1));
		ChessBoard.addHiddenPiece(new Queen(SIDE_WHITE, D1));
		ChessBoard.addHiddenPiece(new King(SIDE_WHITE, E1));
		ChessBoard.addHiddenPiece(new Bishop(SIDE_WHITE, F1));
		ChessBoard.addHiddenPiece(new Knight(SIDE_WHITE, G1));
		ChessBoard.addHiddenPiece(new Rook(SIDE_WHITE, H1));
		
		ChessBoard.addHiddenPiece(new Pawn(SIDE_WHITE, A2));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_WHITE, B2));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_WHITE, C2));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_WHITE, D2));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_WHITE, E2));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_WHITE, F2));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_WHITE, G2));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_WHITE, H2));
		
		// Black
		ChessBoard.addHiddenPiece(new Rook(SIDE_BLACK, A8));
		ChessBoard.addHiddenPiece(new Knight(SIDE_BLACK, B8));
		ChessBoard.addHiddenPiece(new Bishop(SIDE_BLACK, C8));
		ChessBoard.addHiddenPiece(new Queen(SIDE_BLACK, D8));
		ChessBoard.addHiddenPiece(new King(SIDE_BLACK, E8));
		ChessBoard.addHiddenPiece(new Bishop(SIDE_BLACK, F8));
		ChessBoard.addHiddenPiece(new Knight(SIDE_BLACK, G8));
		ChessBoard.addHiddenPiece(new Rook(SIDE_BLACK, H8));
		
		ChessBoard.addHiddenPiece(new Pawn(SIDE_BLACK, A7));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_BLACK, B7));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_BLACK, C7));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_BLACK, D7));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_BLACK, E7));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_BLACK, F7));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_BLACK, G7));
		ChessBoard.addHiddenPiece(new Pawn(SIDE_BLACK, H7));
		
	}
	
	private void initStepListGrid() {
		
		stepList.setWidth(200);
		stepList.setHeight(300);
		stepList.setShowAllRecords(true);
		stepList.setShowRowNumbers(true);
		stepList.setLeaveScrollbarGap(false);
		stepList.setEmptyMessage("(new game)");

		ListGridField stepNumField = new ListGridField("stepNum", "No");
		stepNumField.setHidden(true);
		ListGridField whiteStepField = new ListGridField("whiteStep", "White");
		ListGridField blackStepField = new ListGridField("blackStep", "Black");
		stepList.setFields(stepNumField, whiteStepField, blackStepField);
		stepList.setCanResizeFields(true);
		//stepList.setData(StepTestData.getRecords());
	}
	
	@SuppressWarnings("unused")
	private Canvas getStackBlack() {
		
		HStack stackBlack = new HStack(5);
		
		stackBlack.setLayoutMargin(2);
		stackBlack.setShowEdges(true);
		stackBlack.setEdgeImage("edges/blue/6.png");
		stackBlack.setCanAcceptDrop(true);
		stackBlack.setAnimateMembers(true);
		stackBlack.setShowDragPlaceHolder(true);
		//hStack.setBorder("1px solid yellow");
		stackBlack.addMember(new Pawn(SIDE_BLACK));
		stackBlack.addMember(new Knight(SIDE_BLACK));
		stackBlack.addMember(new Knight(SIDE_BLACK));
		stackBlack.addMember(new Bishop(SIDE_BLACK));
		stackBlack.addMember(new Bishop(SIDE_BLACK));
		stackBlack.addMember(new Rook(SIDE_BLACK));
		stackBlack.addMember(new Rook(SIDE_BLACK));
		stackBlack.addMember(new Queen(SIDE_BLACK));
		stackBlack.addMember(new King(SIDE_BLACK));
		
		return stackBlack;
	}

	@SuppressWarnings("unused")
	private Canvas getStackWhite() {
		
		HStack stackWhite = new HStack(5);
		
		stackWhite.setLayoutMargin(2);
		stackWhite.setShowEdges(true);
		stackWhite.setEdgeImage("edges/yellow/5.png");
		stackWhite.setCanAcceptDrop(true);
		stackWhite.setAnimateMembers(true);
		stackWhite.setShowDragPlaceHolder(true);
		//vStack.setBorder("1px solid blue");
		stackWhite.addMember(new Pawn(SIDE_WHITE));
		stackWhite.addMember(new Knight(SIDE_WHITE));
		stackWhite.addMember(new Knight(SIDE_WHITE));
		stackWhite.addMember(new Bishop(SIDE_WHITE));
		stackWhite.addMember(new Bishop(SIDE_WHITE));
		stackWhite.addMember(new Rook(SIDE_WHITE));
		stackWhite.addMember(new Rook(SIDE_WHITE));
		stackWhite.addMember(new Queen(SIDE_WHITE));
		stackWhite.addMember(new King(SIDE_WHITE));
		
		return stackWhite;
	}

	public static ListGrid getStepList() {
		return stepList;
	}

	public void setStepList(ListGrid stepListAssigned) {
		stepList = stepListAssigned;
	}
	
	public static void recordMove(int side, String pieceName,
			String PreviousPosition, String position, boolean killPiece) {

		String notationName = getNotationName(pieceName) + PreviousPosition;

		ListGrid listGrid = getStepList();
		if (SIDE_WHITE == side) {

			ListGridRecord record = new ListGridRecord();
			record.setAttribute("whiteStep", notationName
					+ (killPiece ? "x" : "-") + position);
			listGrid.addData(record);

		} else {

			ListGridRecord[] records = listGrid.getRecords();
			if (records.length > 0) {
				records[records.length - 1].setAttribute("blackStep",
						notationName + (killPiece ? "x" : "-") + position);
			}
			listGrid.setData(records);
		}

	}
	
	public static String getNotationName(String pieceName) {
		String notationName = "";
		if ("Pawn".equals(pieceName)) {
			notationName = "";
		} else if ("Knight".equals(pieceName)) {
			notationName = "N";
		} else {
			notationName = pieceName.substring(0, 1);
		}

		return notationName;
	}
	
}
