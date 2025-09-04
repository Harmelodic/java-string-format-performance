package com.harmelodic.java.strings.performance;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SimplePerformanceTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceTests.class);

	Duration simplePerformanceTest(SimpleStringConcatenation formatter) {
		Instant start = Instant.now();

		for (int i = 0; i < 10_000_000; i++) {
			formatter.format(UUID.randomUUID().toString(), UUID.randomUUID().toString());
		}
		Duration timeTaken = Duration.between(start, Instant.now());
		LOGGER.info("Formatter {} took: {} ms", formatter.getClass().getSimpleName(), timeTaken.toMillis());
		return timeTaken;
	}

	@Test
	void testPlusOperator() {
		Duration timeTaken = simplePerformanceTest(new PlusOperator());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testStringFormat() {
		Duration timeTaken = simplePerformanceTest(new StringFormat());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}
}
