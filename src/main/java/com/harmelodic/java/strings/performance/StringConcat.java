package com.harmelodic.java.strings.performance;

public class StringConcat implements SimpleStringConcatenation {
	@Override
	public String format(String string1, String string2) {
		return "::".concat(string1).concat("::").concat(string2).concat("::");
	}
}
