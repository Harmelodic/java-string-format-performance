package com.harmelodic.java.strings.performance;

public class UsingPlusOperatorWithConstants implements SimpleStringConcatenation {
	private static final String DOUBLE_COLON = "::";

	@Override
	public String format(String string1, String string2) {
		return DOUBLE_COLON + string1 + DOUBLE_COLON + string2 + DOUBLE_COLON;
	}
}
