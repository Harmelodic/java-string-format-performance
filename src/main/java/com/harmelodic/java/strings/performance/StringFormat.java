package com.harmelodic.java.strings.performance;

public class StringFormat implements SimpleStringConcatenation {

	@Override
	public String format(String string1, String string2) {
		return String.format("::%s::%s::", string1, string2);
	}
}
