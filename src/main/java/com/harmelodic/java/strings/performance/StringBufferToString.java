package com.harmelodic.java.strings.performance;

@SuppressWarnings({"StringBufferReplaceableByString", "StringBufferMayBeStringBuilder"})
public class StringBufferToString implements SimpleStringConcatenation {
	@Override
	public String format(String string1, String string2) {
		StringBuffer stringBuffer = new StringBuffer("::");
		stringBuffer.append(string1);
		stringBuffer.append("::");
		stringBuffer.append(string2);
		stringBuffer.append("::");
		return stringBuffer.toString();
	}
}
