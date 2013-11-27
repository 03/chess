/**
 * 
 */
package com.windbox.chess.client.element;

import static com.windbox.chess.client.common.Constants.HIGHLIGHT;
import static com.windbox.chess.client.common.Constants.SIDE_WHITE;
import static com.windbox.chess.client.common.Constants.STATUS_KILLED;
import static com.windbox.chess.client.common.Constants.STATUS_NORMAL;

import java.util.List;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragRepositionStartEvent;
import com.smartgwt.client.widgets.events.DragRepositionStartHandler;
import com.smartgwt.client.widgets.events.DragRepositionStopEvent;
import com.smartgwt.client.widgets.events.DragRepositionStopHandler;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.DropOutEvent;
import com.smartgwt.client.widgets.events.DropOutHandler;
import com.smartgwt.client.widgets.events.DropOverEvent;
import com.smartgwt.client.widgets.events.DropOverHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.windbox.chess.client.Chess;

/**
 * @author Luke
 *
 */
public abstract class ChessPiece extends Img {

	// pawn, queen, king ...
	private String pieceName = "undefined";
	// black or white
	private int side = 0;
	// image
	private String imageSrc = "";
	private String currentPosition = "", initPosition = "", lastPosition = "";
	// normal, killed
	private int status = STATUS_NORMAL;
	
	// has moved
	protected boolean hasMoved = false;
	
	public ChessPiece() {
		
		setWidth(48);
		setHeight(48);
		setCursor(Cursor.HAND);
		setDragAppearance(DragAppearance.TARGET);
		setCanDragReposition(true);
		setCanDrag(true);
		setCanDrop(true);
		
		setCanAcceptDrop(true);
		//setKeepInParentRect(true);
		setSmoothFade(true);
		setAppImgDir("Emoticons/PNG/48x48/");
		// setOpacity(50);
		
//		setLayoutAlign(Alignment.CENTER);
//		setBorder("1px solid red");
//		setBackgroundColor("#C3D9FF");
//		setImageType(ImageStyle.CENTER);
		
		addShowContextMenuHandler(new ShowContextMenuHandler() {
			public void onShowContextMenu(ShowContextMenuEvent event) {
				// destroy();
				event.cancel();
			}
		});
		
		addDragRepositionStartHandler(new DragRepositionStartHandler() {

			@Override
			public void onDragRepositionStart(DragRepositionStartEvent event) {
				
				ChessPiece target = (ChessPiece) EventHandler.getDragTarget();
				System.out.println("------- starts dragging ------> " + target.getPieceName());
				
				List<String> moves = getAvailableMoves();
				// show available moves
				for (String pos : moves) {
					//ChessBoard.getGrid(pos).animateFade(30);
					ChessBoard.getGrid(pos).showPieceImg();
				}
				
			}
			
		});
		
		addDragRepositionStopHandler(new DragRepositionStopHandler() {

			@Override
			public void onDragRepositionStop(DragRepositionStopEvent event) {
				System.out.println("------- ends dragging ------");
				if (!lastPosition.equals(currentPosition)) {
					hasMoved = true;
				}
				
				ChessBoard.hideTips();
			}
			
		});
		
		addDropOverHandler(new DropOverHandler() {
			public void onDropOver(DropOverEvent event) {
				ChessBoard.getGrid(currentPosition).setBackgroundColor(HIGHLIGHT);
			}
		});
		
		addDropOutHandler(new DropOutHandler() {
			public void onDropOut(DropOutEvent event) {
				ChessBoard.getGrid(currentPosition).resumeDefaultColor();
			}
		});
		
		// if piece drops on another piece
		addDropHandler(new DropHandler() {
			public void onDrop(DropEvent event) {
				System.out.println("Piece on Piece!! ");
				
				Canvas target = EventHandler.getDragTarget();
				if(target instanceof ChessPiece) {
					ChessPiece _target = (ChessPiece) target;
					String name = _target.getPieceName();
					String previousPosition = _target.getCurrentPosition();
					String currentPosition = getCurrentPosition();
					System.out.println("You dropped the " + name + " at position: " + currentPosition);
					
					if (!isKilled()
							&& getCurrentPosition().equals(currentPosition)) {
						
						if(_target.getSide() != getSide()) {
							killed();
						} else {
							event.cancel();
							_target.moveToGrid(ChessBoard.getGrid(previousPosition));
							return;
						}
						
					}
					
					_target.setLastPosition(previousPosition);
					_target.setCurrentPosition(currentPosition);
					
					// Record move
					Chess.recordMove(_target.getSide(), name, previousPosition, currentPosition, true);
					
				}
			}
		});
		
		addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				List<String> moves = getAvailableMoves();
				System.out.println("--click--available moves-> " + moves);
			}
		});
		
	}
	
	public ChessPiece(int side) {
		this();
		setSide(side);
	}
	
	public ChessPiece(int side, String pieceName) {
		this(side);
		this.setPieceName(pieceName);
	}

	public ChessPiece(int side, String pieceName, String imageSrc) {
		this(side, pieceName);
		if(imageSrc == null) {
			setDefaultImageSrc(side, pieceName);
		} else {
			setSrc(imageSrc);
		}
	}
	
	/**
	 * http://en.wikipedia.org/wiki/Rules_of_chess
	 * 
	 * @return
	 */
	public abstract List<String> getAvailableMoves();
	
	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
		setSrc(imageSrc);
	}
	
	public String getPieceName() {
		return pieceName;
	}

	public void setPieceName(String pieceName) {
		this.pieceName = pieceName;
	}
	
	public String getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(String lastPosition) {
		this.lastPosition = lastPosition;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	// Set current logical position and Adjust location in grid
	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
		TableGrid grid = ChessBoard.getGrid(currentPosition);
		
		// System.out.println(grid.getOffsetX());
		// System.out.println(grid.getOffsetY());
		int iOffset = (grid.getWidth() - getWidth()) / 2;
		// System.out.println("iOffset "+iOffset);
		setLeft(grid.getAbsoluteLeft() + iOffset);
		setTop(grid.getAbsoluteTop() + iOffset);
	}
	
	public String getInitPosition() {
		return initPosition;
	}

	public void setInitPosition(String initPosition) {
		this.lastPosition = initPosition;
		this.initPosition = initPosition;
	}

	public void setDefaultImageSrc(int side, String pieceName) {
		if("".equals(getImageSrc())) {
			if (SIDE_WHITE == side) {
				setImageSrc(pieceName+"Yellow"+".png");
			} else {
				setImageSrc(pieceName+"Blue"+".png");
			}
		}
	}
	
	public void moveToPosition(String nextPosition) {
		hasMoved = true;
		setLastPosition(getCurrentPosition());
		setCurrentPosition(nextPosition);
		moveToGrid(ChessBoard.getGrid(nextPosition));
	}
	
	public void moveToGrid(TableGrid grid) {
		moveToGrid(grid, true);
	}
	
	public void moveToGrid(TableGrid grid, boolean animated) {
		int iOffset = (grid.getWidth() - getWidth()) / 2 / 2;
		int left = grid.getAbsoluteLeft() + iOffset;
		int top = grid.getAbsoluteTop() + iOffset;
		
		if(animated) {
			animateMove(left, top);
			// can be self-adjusted to its container's size
			/*animateRect(left, top, Constants.iInitGridLength,
					Constants.iInitGridLength, null, (int) 1500);*/
		} else {
			// moveTo(grid.getLeft() + iOffset, grid.getTop() + iOffset);
			moveTo(left, top);
		}
		
	}
	
	// back to init position
	public void initPos() {
		
		hasMoved = false;
		setStatus(STATUS_NORMAL);
		setCurrentPosition(initPosition);
		
		TableGrid grid = ChessBoard.getGrid(initPosition);
		int iOffset = (grid.getWidth() - getWidth()) / 2 / 2;
		//moveTo(grid.getAbsoluteLeft() + iOffset, grid.getAbsoluteTop() + iOffset);
		
		setLeft(grid.getAbsoluteLeft() + iOffset);
		setTop(grid.getAbsoluteTop() + iOffset);
		
		//grid.setPieceImg(imageSrc);
		moveAbove(grid);
		show();
		//bringToFront();
	}
	
	public void killed() {
		setStatus(STATUS_KILLED);
		hide();
	}
	
	public boolean isKilled() {
		return (status == STATUS_KILLED);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
