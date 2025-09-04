package com.harmelodic.java.strings.performance;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

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
		LOGGER.info("{} ms for formatter: {}", timeTaken.toMillis(), formatter.getClass().getSimpleName());
		return timeTaken;
	}

	@Test
	@Order(0)
	void warmUp() {
		class WarmUp implements SimpleStringConcatenation {
			@Override
			public String format(String string1, String string2) {
				return "";
			}
		}

		simplePerformanceTest(new WarmUp());
	}

	@Test
	@Order(1)
	void testUsingPlusOperator() {
		Duration timeTaken = simplePerformanceTest(new PlusOperator());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	@Order(2)
	void testUsingPlusOperatorWithConstants() {
		Duration timeTaken = simplePerformanceTest(new PlusOperatorWithConstant());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	@Order(3)
	void testUsingStringBuilder() {
		Duration timeTaken = simplePerformanceTest(new StringBuilderToString());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	/// Temperamental - can sometimes be faster or slower than StringBuilder.
	@Test
	@Order(4)
	void testUsingStringBuffer() {
		Duration timeTaken = simplePerformanceTest(new StringBufferToString());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	@Order(5)
	void testUsingStringFormat() {
		Duration timeTaken = simplePerformanceTest(new StringFormat());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}
}
