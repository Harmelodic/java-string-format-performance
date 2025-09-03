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

	Duration simplePerformanceTest(SimpleStringFormatter formatter) {
		Instant start = Instant.now();

		for (int i = 0; i < 1_000_000; i++) {
			formatter.format(
					UUID.randomUUID().toString(),
					UUID.randomUUID().toString(),
					UUID.randomUUID().toString(),
					UUID.randomUUID().toString());
		}
		Duration timeTaken = Duration.between(start, Instant.now());
		LOGGER.info("Took: {} ms", timeTaken.toMillis());
		return timeTaken;
	}

	@Test
	void testPlusOperatorConcatenation() {
		Duration timeTaken = simplePerformanceTest(new PlusOperatorConcatenation());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 2000);
	}
}
