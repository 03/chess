package com.windbox.chess.client;

public class StepTestData {

	private static StepTestRecord[] records;

	public static StepTestRecord[] getRecords() {
		if (records == null) {
			records = getNewRecords();
		}
		return records;
	}

	public static StepTestRecord[] getNewRecords() {
		return new StepTestRecord[] { new StepTestRecord(1, "e2-e4", "e7-e5"),
				new StepTestRecord(2, "Ng1-f3", "Nb8-c6"),
				new StepTestRecord(3, "c2-c4", "Qd8-e7") };
	}
}
