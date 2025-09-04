package com.harmelodic.java.strings.performance;

import org.junit.jupiter.api.BeforeAll;
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

	static Duration simplePerformanceTest(SimpleStringConcatenation formatter, boolean log) {
		Instant start = Instant.now();

		for (int i = 0; i < 10_000_000; i++) {
			formatter.format(String.valueOf(i), String.valueOf(i + 1));
		}
		Duration timeTaken = Duration.between(start, Instant.now());

		assertEquals("::one::two::", formatter.format("one", "two"));
		if (log) {
			LOGGER.info("{} ms for formatter: {}", timeTaken.toMillis(), formatter.getClass().getSimpleName());
		}
		return timeTaken;
	}

	@BeforeAll
	static void warmUp() {
		class WarmUp implements SimpleStringConcatenation {
			@Override
			public String format(String string1, String string2) {
				return "::" + string1 + "::" + string2 + "::";
			}
		}

		simplePerformanceTest(new WarmUp(), false);
	}


	@Test
	@Order(1)
	void testPlusOperator() {
		Duration timeTaken = simplePerformanceTest(new PlusOperator(), true);
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	@Order(2)
	void testPlusOperatorWithConstants() {
		Duration timeTaken = simplePerformanceTest(new PlusOperatorWithConstant(), true);
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	@Order(3)
	void testStringConcat() {
		Duration timeTaken = simplePerformanceTest(new StringConcat(), true);
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - when before StringBuilder it is slower, but when after StringBuilder it's faster.
	@Test
	@Order(4)
	void testApacheStringUtils() {
		Duration timeTaken = simplePerformanceTest(new ApacheStringUtilsWrapWithPlusOperator(), true);
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - can sometimes be faster or slower than StringBuffer and/or StringJoinWithPlusOperator.
	@Test
	@Order(5)
	void testStringBuilder() {
		Duration timeTaken = simplePerformanceTest(new StringBuilderToString(), true);
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - can sometimes be faster or slower than StringBuilder and/or StringJoinWithPlusOperator.
	@Test
	@Order(6)
	void testStringBuffer() {
		Duration timeTaken = simplePerformanceTest(new StringBufferToString(), true);
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - can sometimes be faster or slower than StringBuffer and/or StringBuilder.
	@Test
	@Order(7)
	void testStringJoin() {
		Duration timeTaken = simplePerformanceTest(new StringJoinWithPlusOperator(), true);
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	@Order(8)
	void testStringFormat() {
		Duration timeTaken = simplePerformanceTest(new StringFormat(), true);
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}
}
