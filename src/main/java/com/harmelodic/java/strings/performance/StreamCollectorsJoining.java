package com.harmelodic.java.strings.performance;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCollectorsJoining implements SimpleStringConcatenation {
	@Override
	public String format(String string1, String string2) {
		return Stream.of("::", string1, "::", string2, "::")
				.collect(Collectors.joining(""));
	}
}
