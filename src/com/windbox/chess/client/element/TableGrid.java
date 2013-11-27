package com.windbox.chess.client.element;

import static com.windbox.chess.client.common.Constants.HIGHLIGHT;
import static com.windbox.chess.client.common.Constants.iInitGridLength;

import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragRepositionStopEvent;
import com.smartgwt.client.widgets.events.DragRepositionStopHandler;
import com.smartgwt.client.widgets.events.DragResizeStopEvent;
import com.smartgwt.client.widgets.events.DragResizeStopHandler;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.DropOutEvent;
import com.smartgwt.client.widgets.events.DropOutHandler;
import com.smartgwt.client.widgets.events.DropOverEvent;
import com.smartgwt.client.widgets.events.DropOverHandler;
import com.windbox.chess.client.Chess;


public class TableGrid extends Canvas {

	private String defaultColor = "";
	private Img pieceImg = new Img();

	private TableGrid(String id) {
		
		setID(id);
		
		setWidth(iInitGridLength);
		setHeight(iInitGridLength);
		setAlign(Alignment.CENTER);
		setCanDragScroll(false);
		setCanAcceptDrop(true);
		// setShowEdges(true);
		// setOverflow(Overflow.HIDDEN);
		
		// image for piece
		pieceImg.setLeft(18);
		pieceImg.setTop(18);
		pieceImg.setWidth(24);
		pieceImg.setHeight(24);
		pieceImg.setAppImgDir("pieces/24/");
		pieceImg.setSrc("star_red.png");
		pieceImg.hide();
		addChild(pieceImg);
		
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("onClick");
			}
		});
		
		addDropOverHandler(new DropOverHandler() {
			public void onDropOver(DropOverEvent event) {
				setBackgroundColor(HIGHLIGHT);
			}
		});
		
		addDropOutHandler(new DropOutHandler() {
			public void onDropOut(DropOutEvent event) {
				String defaultColor = getDefaultColor();
				setBackgroundColor(defaultColor);
			}
		});

		addDropHandler(new DropHandler() {
			public void onDrop(DropEvent event) {
				Canvas target = EventHandler.getDragTarget();
				
				if(target instanceof ChessPiece) {
					ChessPiece _target = (ChessPiece) target;
					String name = _target.getPieceName();
					String previousPosition = _target.getCurrentPosition();
					String currentPosition = getID();
					System.out.println("You dropped the " + name + " at position: " + getID());
					
					// SC.say("You dropped the " + name + " at position: " + chessBoardGrid.getID());
					
					boolean isPieceKilled = false;
					// check if it has been occupied by other piece
					List<ChessPiece> list = ChessBoard.getPieces();
					for(ChessPiece piece : list) {
						if(!piece.isKilled() && piece.getCurrentPosition().equals(currentPosition)) {
							
							//if(_target != piece) {
							if(_target.getSide() != piece.getSide()) {
								piece.killed();
								isPieceKilled = true;
								//chessBoardGrid.removeChild(piece);
							} else {
								event.cancel();
								_target.moveToGrid(ChessBoard.getGrid(previousPosition));
								return;
							}
							
							break;
							
						}
					}
					
					_target.setLastPosition(previousPosition);
					_target.setCurrentPosition(currentPosition);
					
					// Record move
					Chess.recordMove( _target.getSide(), name, previousPosition, currentPosition, isPieceKilled);
				}
				
			}
		});
		
		addDragRepositionStopHandler(new DragRepositionStopHandler() {
			public void onDragRepositionStop(DragRepositionStopEvent event) {
				sendToBack();
			}
		});

		addDragResizeStopHandler(new DragResizeStopHandler() {
			public void onDragResizeStop(DragResizeStopEvent event) {
				sendToBack();
			}
		});
	}
	

	public static TableGrid createChessBoardGrid(String id) {
		TableGrid chessBoardGrid = new TableGrid(id);
		return chessBoardGrid;
	}
	
	public String getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(String color) {
		defaultColor = color;
		setBackgroundColor(defaultColor);
	}
	
	public void resumeDefaultColor() {
		setBackgroundColor(defaultColor);
	}
	
	public void setPieceImg(String imgSrc) {
		pieceImg.setSrc(imgSrc);
	}
	
	public void hidePieceImg() {
		pieceImg.hide();
	}
	
	public void showPieceImg() {
		//pieceImg.bringToFront();
		pieceImg.show();
	}
	
}
