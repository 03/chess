package com.windbox.chess.client.pagination;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class GridPager extends ToolStrip {

	boolean deselect = true;

	public boolean isDeselect() {
		return deselect;
	}

	public void setDeselect(boolean deselect) {
		this.deselect = deselect;
	}

	// ---------------------- interal state
	/**
	 * How many row in each pager.
	 */
	private int pageSize = 50;
	/**
	 * Current page.
	 */
	private int pageNum;
	/**
	 * The actual grid.
	 */
	private final ListGrid grid;

	// visual component
	protected Label totalRecordCountLabel;
	protected Label firstLabel;
	protected Label nextLabel;
	DynamicForm pageForm;
	protected TextItem pageText;
	protected Label previousLabel;
	protected Label lastLabel;
	protected Label totalLabel;

	public GridPager(ListGrid listGrid, int pageSize) {
		this(listGrid, pageSize, 20, 22);
	}

	/**
	 * 
	 * @param listGrid
	 *            the listGrid
	 * @param pageSize
	 *            how many rows in each page.
	 * @param cellHeight
	 *            cellHeight for the listGrid.
	 * @param headerHeight
	 *            headerHeight for the listGrid.
	 */
	public GridPager(ListGrid listGrid, int pageSize, int cellHeight,
			int headerHeight) {
		this.grid = listGrid;
		this.pageSize = pageSize;

		firstLabel = new Label();
		firstLabel.setWidth(40);
		firstLabel.setStyleName("fakelink");
		firstLabel.setContents("First");
		firstLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				goToPage(1);
			}
		});

		previousLabel = new Label();
		previousLabel.setWidth(20);
		previousLabel.setContents("&lt;&lt;");
		previousLabel.setStyleName("fakelink");
		previousLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				goToPage(pageNum - 1);
			}
		});

		pageText = new TextItem();
		pageText.setWidth(30);
		pageText.setShowTitle(false);
		pageText.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null) {
					int value = Integer.parseInt(event.getValue().toString());
					goToPage(value);
				}
			}
		});

		nextLabel = new Label();
		nextLabel.setContents("&gt;&gt;");
		nextLabel.setWidth(20);
		nextLabel.setStyleName("fakelink");
		nextLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				goToPage(pageNum + 1);
			}
		});

		lastLabel = new Label();
		lastLabel.setWidth(40);
		lastLabel.setStyleName("fakelink");
		lastLabel.setContents("Last");
		lastLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				goToPage(getTotalPages());
			}
		});

		totalLabel = new Label();
		totalLabel.setWrap(false);
		totalLabel.setWidth(50);

		setHeight(20);
		setOverflow(Overflow.VISIBLE);
		setStyleName("normal");

		pageForm = new DynamicForm();
		pageForm.setNumCols(1);
		pageForm.setFields(pageText);
		pageForm.setWidth(30);

		addMember(firstLabel);
		addMember(previousLabel);

		addMember(pageForm);
		addMember(nextLabel);
		addMember(lastLabel);
		addMember(totalLabel);
		setAlign(Alignment.RIGHT);

		// --------------set grid height
		grid.setCellHeight(cellHeight);
		grid.setHeaderHeight(headerHeight);
		grid.setHeight(cellHeight * pageSize + headerHeight);
		grid.setBodyOverflow(Overflow.HIDDEN);
		grid.setDataPageSize(pageSize);
		grid.setDrawAheadRatio(2.0f);
		// listen grid events
		grid.addDataArrivedHandler(new DataArrivedHandler() {
			public void onDataArrived(DataArrivedEvent event) {
				GWT.log("onDataArrived is called", null);
				goToPage(pageNum);
			}
		});
		// grid.addDrawHandler(new DrawHandler()
		// {
		//
		// public void onDraw(DrawEvent event)
		// {
		// goToPage(1);
		//
		// }
		// });

	}

	public void goToPage(int pageNum) {
		GWT.log("go to page " + pageNum, null);
		// clamp to the end of the possible set of pages
		int pages = getTotalPages();
		if (pageNum > pages)
			pageNum = pages;
		if (pageNum < 1)
			pageNum = 1;

		if (pageNum == this.pageNum) {
			updatePagerControls(pages);
			return;
		}
		// update the buttons
		this.pageNum = pageNum;
		updatePagerControls(pages);
		if (deselect)
			grid.deselectAllRecords();
		int cellHeight = grid.getCellHeight();
		int rowNum = (pageNum - 1) * pageSize;

		// here: give extra 2 pixes. This is a hack. Otherwise,
		// listGrid think the last row from previous page is visible although it
		// is not visible
		grid.scrollBodyTo(null, rowNum * cellHeight + 2);

	}

	private int getTotalPages() {
		int total = this.grid.getTotalRows();
		int pages = (int) Math.ceil(((float) total) / ((float) pageSize));
		// never return zero pages
		if (pages == 0)
			pages = 1;
		return pages;
	}

	protected void updatePagerControls(int total) {
		// pageText.setValue(pageNum);
		if (pageNum == 1) {
			if (firstLabel.isVisible())
				firstLabel.hide();
			if (previousLabel.isVisible())
				previousLabel.hide();
		} else {
			if (!firstLabel.isVisible())
				firstLabel.show();
			if (!previousLabel.isVisible())
				previousLabel.show();
		}
		if (pageNum == total) {
			if (lastLabel.isVisible())

				lastLabel.hide();
			if (nextLabel.isVisible())
				nextLabel.hide();
		} else {
			if (!lastLabel.isVisible())
				lastLabel.show();
			if (!nextLabel.isVisible())
				nextLabel.show();
		}
		if (total >= 4) {
			if (!pageForm.isVisible())
				pageForm.show();
		} else {
			if (pageForm.isVisible())
				pageForm.hide();
		}

		totalLabel.setContents("(" + pageNum + "/" + total + ")"
				+ this.grid.getTotalRows() + "Records ");

	}

}
