package com.harmelodic.java.strings.performance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimplePerformanceTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceTests.class);

	private static final List<Result> results = new ArrayList<>();

	private record Result(String formatterName, Duration timeTaken) {
	}

	@BeforeAll
	static void warmUp() {
		class WarmUp implements SimpleStringConcatenation {
			@Override
			public String format(String string1, String string2) {
				return "::" + string1 + "::" + string2 + "::";
			}
		}

		simplePerformanceTest(new WarmUp());
	}

	@AfterAll
	static void report() {
		results.stream()
				.sorted(Comparator.comparing(o -> o.timeTaken))
				.forEach(result ->
						LOGGER.info("{} ms for formatter: {}", result.timeTaken.toMillis(), result.formatterName));
	}

	static Duration simplePerformanceTest(SimpleStringConcatenation formatter) {
		Instant start = Instant.now();

		for (int i = 0; i < 10_000_000; i++) {
			formatter.format(String.valueOf(i), String.valueOf(i + 1));
		}
		Duration timeTaken = Duration.between(start, Instant.now());

		assertEquals("::one::two::", formatter.format("one", "two"));
		results.add(new Result(formatter.getClass().getSimpleName(), timeTaken));
		return timeTaken;
	}

	@Test
	void testPlusOperator() {
		Duration timeTaken = simplePerformanceTest(new PlusOperator());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testPlusOperatorWithConstants() {
		Duration timeTaken = simplePerformanceTest(new PlusOperatorWithConstant());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testStringConcat() {
		Duration timeTaken = simplePerformanceTest(new StringConcat());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testStringJoin() {
		Duration timeTaken = simplePerformanceTest(new StringJoin());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testStringFormat() {
		Duration timeTaken = simplePerformanceTest(new StringFormat());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testStringBuilder() {
		Duration timeTaken = simplePerformanceTest(new StringBuilderToString());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testStringBuffer() {
		Duration timeTaken = simplePerformanceTest(new StringBufferToString());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testStringJoiner() {
		Duration timeTaken = simplePerformanceTest(new StringJoinerToString());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testStreamCollectorsJoining() {
		Duration timeTaken = simplePerformanceTest(new StreamCollectorsJoining());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testApacheStringUtilsWrapWithPlusOperator() {
		Duration timeTaken = simplePerformanceTest(new ApacheStringUtilsWrapWithPlusOperator());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}

	@Test
	void testApacheStringUtilsJoin() {
		Duration timeTaken = simplePerformanceTest(new ApacheStringUtilsJoin());
		assertTrue(timeTaken.isPositive());
		assertTrue(timeTaken.toMillis() < 5000);
	}
}
