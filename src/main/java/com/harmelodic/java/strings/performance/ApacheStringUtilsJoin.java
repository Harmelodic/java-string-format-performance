package com.harmelodic.java.strings.performance;

import org.apache.commons.lang3.StringUtils;

public class ApacheStringUtilsJoin implements SimpleStringConcatenation {
	@Override
	public String format(String string1, String string2) {
		return StringUtils.join("::", string1, "::", string2, "::");
	}
}
