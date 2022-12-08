package telran.multithreading.executorsNumbers;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NumberGroups {

	private int[][] groups;
	private int nThreads = 4;
	
	public NumberGroups(int[][] groups) {
		this.groups = groups;
	} 

	public int getNThreads() {
		
		return this.nThreads;
	}
	public void setNThreads(int nThreads) {
		
		this.nThreads = nThreads;
	}

	public long computeSum() {
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		List<Future<Long>> groupSums =
				Arrays.stream(groups).map(OneGroupSum::new)//(group -> new OneGroupSum(group))
				.map(executor::submit).toList();//.map(ogs -> executor.submit(ogs))
		return groupSums.stream().mapToLong(value -> {
			try {
				return value.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new IllegalStateException();
			}
		}).sum();
	}
	
	
}