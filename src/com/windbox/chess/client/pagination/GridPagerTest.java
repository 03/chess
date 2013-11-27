package com.windbox.chess.client.pagination;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.HeaderClickEvent;
import com.smartgwt.client.widgets.grid.events.HeaderClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GridPagerTest implements EntryPoint {

	public void onModuleLoad() {

		final ListGrid countryGrid = new ListGrid();
		countryGrid.setWidth(350);
		// countryGrid.setHeight(200);
		countryGrid.setAlternateRecordStyles(true);

		ListGridField indexField = new ListGridField("id", "id", 50);
		indexField.setShowSelectedIcon(true);
		indexField.setCanToggle(true);
		// indexField.set
		ListGridField textField = new ListGridField("text", "text", 150);
		countryGrid.setFields(indexField, textField);

		countryGrid.setCanResizeFields(true);

		countryGrid.setShowAllRecords(false);
		countryGrid.setSelectionType(SelectionStyle.SIMPLE);
		countryGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		countryGrid.setDataSource(new TestRPCDataSource());
		countryGrid.addHeaderClickHandler(new HeaderClickHandler() {

			public void onHeaderClick(HeaderClickEvent event) {
				if (event.getFieldNum() != 0) {
					return;
				}
				if (countryGrid.getSelectedRecords().length != 0) {
					countryGrid.deselectAllRecords();
					event.cancel();
					return;
				}
				Integer[] selected = countryGrid.getVisibleRows();

				int[] selects = new int[selected[1] - selected[0] + 1];
				for (int i = selected[0]; i <= selected[1]; i++) {
					selects[i - selected[0]] = i;
				}
				countryGrid.selectRecords(selects, true);
				event.cancel();
			}
		});

		GridPager gridPager = new GridPager(countryGrid, 10);
		Label reload = new Label();
		reload.setWidth(40);
		reload.setStyleName("fakelink");
		reload.setContents("reload");
		reload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TestRPCDataSource.total = TestRPCDataSource.total - 10;
				countryGrid.invalidateCache();
				countryGrid.fetchData();
			}
		});
		gridPager.addMember(reload);
		gridPager.setDeselect(false);

		VLayout v = new VLayout();
		v.setWidth(360);
		v.setHeight100();
		v.addMember(countryGrid);
		gridPager.setWidth(350);
		v.addMember(gridPager);
		v.draw();
		countryGrid.fetchData();

	}
}

/**
 * Data source with ability to communicate with server by GWT RPC.
 * <p/>
 * SmartClient natively supports data protocol "clientCustom". This protocol
 * means that communication with server should be implemented in
 * <code>transformRequest (DSRequest request)</code> method. Here is a few
 * things to note on <code>transformRequest</code> implementation:
 * <ul>
 * <li><code>DSResponse</code> object has to be created and
 * <code>processResponse (requestId, response)</code> must be called to finish
 * data request. <code>requestId</code> should be taken from original
 * <code>DSRequest.getRequestId ()</code>.</li>
 * <li>"clientContext" attribute from <code>DSRequest</code> should be copied to
 * <code>DSResponse</code>.</li>
 * <li>In case of failure <code>DSResponse</code> should contain at least
 * "status" attribute with error code (&lt;0).</li>
 * <li>In case of success <code>DSResponse</code> should contain at least "data"
 * attribute with operation type specific data:
 * <ul>
 * <li>FETCH - <code>ListGridRecord[]</code> retrieved records.</li>
 * <li>ADD - <code>ListGridRecord[]</code> with single added record. Operation
 * is called on every newly added record.</li>
 * <li>UPDATE - <code>ListGridRecord[]</code> with single updated record.
 * Operation is called on every updated record.</li>
 * <li>REMOVE - <code>ListGridRecord[]</code> with single removed record.
 * Operation is called on every removed record.</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
abstract class GwtRpcDataSource extends DataSource {

	/**
	 * Creates new data source which communicates with server by GWT RPC. It is
	 * normal server side SmartClient data source with data protocol set to
	 * <code>DSProtocol.CLIENTCUSTOM</code> ("clientCustom" - natively supported
	 * by SmartClient but should be added to smartGWT) and with data format
	 * <code>DSDataFormat.CUSTOM</code>.
	 */
	public GwtRpcDataSource() {
		setDataProtocol(DSProtocol.CLIENTCUSTOM);
		setDataFormat(DSDataFormat.CUSTOM);
		setClientOnly(false);
	}

	/**
	 * Executes request to server.
	 * 
	 * @param request
	 *            <code>DSRequest</code> being processed.
	 * @return <code>Object</code> data from original request.
	 */
	@Override
	protected Object transformRequest(DSRequest request) {
		String requestId = request.getRequestId();
		DSResponse response = new DSResponse();
		response.setAttribute("clientContext", request
				.getAttributeAsObject("clientContext"));
		// Asume success
		response.setStatus(0);
		switch (request.getOperationType()) {
		case FETCH:
			executeFetch(requestId, request, response);
			break;
		case ADD:
			executeAdd(requestId, request, response);
			break;
		case UPDATE:
			executeUpdate(requestId, request, response);
			break;
		case REMOVE:
			executeRemove(requestId, request, response);
			break;
		default:
			// Operation not implemented.
			break;
		}
		return request.getData();
	}

	/**
	 * Executed on <code>FETCH</code> operation.
	 * <code>processResponse (requestId, response)</code> should be called when
	 * operation completes (either successful or failure).
	 * 
	 * @param requestId
	 *            <code>String</code> extracted from
	 *            <code>DSRequest.getRequestId ()</code>.
	 * @param request
	 *            <code>DSRequest</code> being processed.
	 * @param response
	 *            <code>DSResponse</code>. <code>setData (list)</code> should be
	 *            called on successful execution of this method.
	 *            <code>setStatus (&lt;0)</code> should be called on failure.
	 */
	protected abstract void executeFetch(String requestId, DSRequest request,
			DSResponse response);

	/**
	 * Executed on <code>ADD</code> operation.
	 * <code>processResponse (requestId, response)</code> should be called when
	 * operation completes (either successful or failure).
	 * 
	 * @param requestId
	 *            <code>String</code> extracted from
	 *            <code>DSRequest.getRequestId ()</code>.
	 * @param request
	 *            <code>DSRequest</code> being processed.
	 *            <code>request.getData ()</code> contains record should be
	 *            added.
	 * @param response
	 *            <code>DSResponse</code>. <code>setData (list)</code> should be
	 *            called on successful execution of this method. Array should
	 *            contain single element representing added row.
	 *            <code>setStatus (&lt;0)</code> should be called on failure.
	 */
	protected abstract void executeAdd(String requestId, DSRequest request,
			DSResponse response);

	/**
	 * Executed on <code>UPDATE</code> operation.
	 * <code>processResponse (requestId, response)</code> should be called when
	 * operation completes (either successful or failure).
	 * 
	 * @param requestId
	 *            <code>String</code> extracted from
	 *            <code>DSRequest.getRequestId ()</code>.
	 * @param request
	 *            <code>DSRequest</code> being processed.
	 *            <code>request.getData ()</code> contains record should be
	 *            updated.
	 * @param response
	 *            <code>DSResponse</code>. <code>setData (list)</code> should be
	 *            called on successful execution of this method. Array should
	 *            contain single element representing updated row.
	 *            <code>setStatus (&lt;0)</code> should be called on failure.
	 */
	protected abstract void executeUpdate(String requestId, DSRequest request,
			DSResponse response);

	/**
	 * Executed on <code>REMOVE</code> operation.
	 * <code>processResponse (requestId, response)</code> should be called when
	 * operation completes (either successful or failure).
	 * 
	 * @param requestId
	 *            <code>String</code> extracted from
	 *            <code>DSRequest.getRequestId ()</code>.
	 * @param request
	 *            <code>DSRequest</code> being processed.
	 *            <code>request.getData ()</code> contains record should be
	 *            removed.
	 * @param response
	 *            <code>DSResponse</code>. <code>setData (list)</code> should be
	 *            called on successful execution of this method. Array should
	 *            contain single element representing removed row.
	 *            <code>setStatus (&lt;0)</code> should be called on failure.
	 */
	protected abstract void executeRemove(String requestId, DSRequest request,
			DSResponse response);

	protected ListGridRecord getEditedRecord(DSRequest request) {
		// Retrieving values before edit
		JavaScriptObject oldValues = request
				.getAttributeAsJavaScriptObject("oldValues");
		// Creating new record for combining old values with changes
		ListGridRecord newRecord = new ListGridRecord();
		// Copying properties from old record
		JSOHelper.apply(oldValues, newRecord.getJsObj());
		// Retrieving changed values
		JavaScriptObject data = request.getData();
		// Apply changes
		JSOHelper.apply(data, newRecord.getJsObj());
		return newRecord;
	}

}

class TestRPCDataSource extends GwtRpcDataSource {
	public static int total = 991;

	public TestRPCDataSource() {
		DataSourceTextField idField = new DataSourceTextField("id");
		idField.setPrimaryKey(true);

		DataSourceTextField textField = new DataSourceTextField("text");

		setFields(idField, textField);
	}

	@Override
	protected void executeFetch(final String requestId,
			final DSRequest request, final DSResponse response) {

		GWT.log(" called from " + request.getStartRow() + " to "
				+ request.getEndRow(), null);
		// assume we have 1000 items.
		response.setTotalRows(total);
		int end = request.getEndRow();
		if (end > total) {
			end = total;
		}

		ListGridRecord records[] = new ListGridRecord[end
				- request.getStartRow()];
		for (int i = request.getStartRow(); i < end; i++) {
			ListGridRecord r = new ListGridRecord();
			r.setAttribute("id", i);
			r.setAttribute("text", "text" + (i + 1));
			records[i - request.getStartRow()] = r;
		}
		GWT.log(" called from " + request.getStartRow() + " to "
				+ request.getEndRow() + " result " + records.length, null);
		response.setData(records);
		processResponse(requestId, response);
	}

	@Override
	protected void executeAdd(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void executeRemove(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void executeUpdate(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub

	}

}
