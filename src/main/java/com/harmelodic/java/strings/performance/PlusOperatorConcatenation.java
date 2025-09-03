package com.harmelodic.java.strings.performance;

public class PlusOperatorConcatenation implements SimpleStringFormatter {
	@Override
	public String format(String string1, String string2, String string3, String string4) {
		return string1 + string2 + string3 + string3 + string4;
	}
}
