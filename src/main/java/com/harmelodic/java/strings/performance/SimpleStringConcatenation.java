package com.harmelodic.java.strings.performance;

public interface SimpleStringConcatenation {
	/// Formats the 4 string arguments to be in the equivalent format:
	///
	/// "::" + string1 "::"  + string2 + "::"
	///
	/// For example, if the strings were:
	///
	/// 1. "One"
	/// 2. "Two"
	///
	/// Then the result would be "::One::Two::".
	String format(String string1, String string2);
}
