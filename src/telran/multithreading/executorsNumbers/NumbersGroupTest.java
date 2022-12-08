package telran.multithreading.executorsNumbers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

class NumbersGroupTest {
	private static final int N_RUNS = 5;
	private static final int N_GROUPS = 10000;
	private static final int N_NUMBERS_IN_GROUP = 10000;
	private final int MIN = 0;
	private final int MAX = 10000000;

	@Test
	void functionsTest() {
		int[][] array = {
				{5, 12, 8},
				{7, 3, 15},
				{15, 15, 20}
		};
		NumberGroups group = new NumberGroups(array);
		assertEquals(100, group.computeSum());
		
		NumberGroups group2 = new NumberGroups(array);
		assertEquals(4, group2.getNThreads());
		group2.setNThreads(10);
		assertEquals(10, group2.getNThreads());
	}
	@Test
	void perfomanceTest() {
		int nThreads = 1;
		for(int i = 0; i < N_RUNS; i++) {
			int[][] array = getGroups(N_GROUPS, N_NUMBERS_IN_GROUP);
			NumberGroups group = new NumberGroups(array);
			nThreads *= 2;
			group.setNThreads(nThreads);
			Instant start = Instant.now();
			long sum = group.computeSum();
			System.out.println("Group-" + i + "; Number of threads-" + group.getNThreads() +
					"; Time-" + ChronoUnit.MILLIS.between(start, Instant.now()) + 
					"; Sum-" + sum + "");
		}
	}
	private int[][]getGroups(int nGroups, int nNumbersInGroup) {
		int[][] groups = new int[nGroups][nNumbersInGroup];
		Arrays.stream(groups).forEach(g -> Arrays.setAll(g, i -> i = getRandomNumber()));
		return groups;
	}
	private int getRandomNumber() {
		
		return ThreadLocalRandom.current().nextInt(MIN, MAX);
	}
	
}
