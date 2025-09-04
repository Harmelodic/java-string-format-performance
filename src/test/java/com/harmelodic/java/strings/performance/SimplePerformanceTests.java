package com.harmelodic.java.strings.performance;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/// Ordering based on speed as of JDK 24.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SimplePerformanceTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceTests.class);

	Duration simplePerformanceTest(SimpleStringConcatenation formatter) {
		Instant start = Instant.now();

		for (int i = 0; i < 10_000_000; i++) {
			formatter.format(String.valueOf(i), String.valueOf(i + 1));
		}
		Duration timeTaken = Duration.between(start, Instant.now());

		assertEquals("::one::two::", formatter.format("one", "two"));
		LOGGER.info("{} ms for formatter: {}", timeTaken.toMillis(), formatter.getClass().getSimpleName());
		return timeTaken;
	}

	@Test
	@Order(0)
	void warmUp() {
		class WarmUp implements SimpleStringConcatenation {
			@Override
			public String format(String string1, String string2) {
				return "::" + string1 + "::" + string2 + "::";
			}
		}

		simplePerformanceTest(new WarmUp());
	}

	@Test
	@Order(1)
	void testPlusOperator() {
		Duration timeTaken = simplePerformanceTest(new PlusOperator());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	@Order(2)
	void testPlusOperatorWithConstants() {
		Duration timeTaken = simplePerformanceTest(new PlusOperatorWithConstant());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - when before StringBuilder it is slower, but when after StringBuilder it's faster.
	@Test
	@Order(3)
	void testApacheStringUtils() {
		Duration timeTaken = simplePerformanceTest(new ApacheStringUtilsWrapWithPlusOperator());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - can sometimes be faster or slower than StringBuffer and/or StringJoinWithPlusOperator.
	@Test
	@Order(4)
	void testStringBuilder() {
		Duration timeTaken = simplePerformanceTest(new StringBuilderToString());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - can sometimes be faster or slower than StringBuilder and/or StringJoinWithPlusOperator.
	@Test
	@Order(5)
	void testStringBuffer() {
		Duration timeTaken = simplePerformanceTest(new StringBufferToString());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - can sometimes be faster or slower than StringBuffer and/or StringBuilder.
	@Test
	@Order(6)
	void testStringJoin() {
		Duration timeTaken = simplePerformanceTest(new StringJoinWithPlusOperator());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	@Order(7)
	void testStringFormat() {
		Duration timeTaken = simplePerformanceTest(new StringFormat());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}
}
