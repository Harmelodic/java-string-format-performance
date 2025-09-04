package com.harmelodic.java.strings.performance;

public class StringJoin implements SimpleStringConcatenation {
	@Override
	public String format(String string1, String string2) {
		return String.join("", "::", string1, "::", string2, "::");
	}
}
