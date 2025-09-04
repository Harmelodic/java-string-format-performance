package com.harmelodic.java.strings.performance;

public class UsingPlusOperator implements SimpleStringConcatenation {
	@Override
	public String format(String string1, String string2) {
		return "::" + string1 + "::" + string2 + "::";
	}
}
