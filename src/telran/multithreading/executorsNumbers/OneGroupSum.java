package telran.multithreading.executorsNumbers;

import java.util.concurrent.atomic.AtomicLong;

public class OneGroupSum implements Runnable{

	private int[] group;
	private Long res;
	private AtomicLong  atomicLong= new AtomicLong(0);
	
	public OneGroupSum(int[] group) {
		this.group = group;
	}
	@Override
	public void run() {
		for(int i = 0; i < group.length; i++) {
				res = atomicLong.addAndGet(group[i]);
        }

	}
	public Long getRes() {

		return res;
	}
	
	
}
