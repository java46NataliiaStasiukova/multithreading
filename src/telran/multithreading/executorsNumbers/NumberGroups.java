package telran.multithreading.executorsNumbers;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class NumberGroups {

	private int[][] groups;
	private int nThreads = 4;
	public ExecutorService executor;
	
	public BlockingQueue<OneGroupSum> queue = new LinkedBlockingQueue<>();
	private AtomicLong res = new AtomicLong(0);
	
	
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
		executor = Executors.newFixedThreadPool(this.nThreads);
		for(int i = 0; i < groups.length; i++) {
			OneGroupSum group = new OneGroupSum(groups[i]);
			try {
				queue.put(group);
				executor.execute(group);
				
			} catch (InterruptedException e) {

			}
		}
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		
		}
		while(queue.size() != 0) {
			res.addAndGet(queue.element().getRes());
			queue.remove();
		}
		 return res.get();
	}
	
	
}