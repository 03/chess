package com.windbox.chess.client;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StepTestRecord extends ListGridRecord {

	public StepTestRecord() {
	}
	
	public StepTestRecord(int stepNum, String whiteStep, String blackStep) {
		setStepNum(stepNum);
		setWhiteStep(whiteStep);
		setBlackStep(blackStep);
	}

	public void setStepNum(int stepNum) {
		setAttribute("stepNum", stepNum);
	}

	public int getStepNum() {
		return getAttributeAsInt("stepNum");
	}

	public void setWhiteStep(String whiteStep) {
		setAttribute("whiteStep", whiteStep);
	}

	public String getWhiteStep() {
		return getAttributeAsString("whiteStep");
	}

	public void setBlackStep(String blackStep) {
		setAttribute("blackStep", blackStep);
	}

	public String getBlackStep() {
		return getAttributeAsString("blackStep");
	}
	

//	public String getFieldValue(String field) {
//		return getAttributeAsString(field);
//	}
}
