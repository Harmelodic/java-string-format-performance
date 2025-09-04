package com.harmelodic.java.strings.performance;

import java.util.StringJoiner;

public class StringJoinerToString implements SimpleStringConcatenation {
	@Override
	public String format(String string1, String string2) {
		StringJoiner stringJoiner = new StringJoiner("::");
		stringJoiner.add(string1);
		stringJoiner.add(string2);
		return "::" + stringJoiner + "::";
	}
}
