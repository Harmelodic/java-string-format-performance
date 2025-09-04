package com.harmelodic.java.strings.performance;

@SuppressWarnings("StringBufferReplaceableByString")
public class StringBuilderToString implements SimpleStringConcatenation {

	@Override
	public String format(String string1, String string2) {
		StringBuilder stringBuilder = new StringBuilder("::");
		stringBuilder.append(string1);
		stringBuilder.append("::");
		stringBuilder.append(string2);
		stringBuilder.append("::");
		return stringBuilder.toString();
	}
}
