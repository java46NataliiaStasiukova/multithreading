package telran.multithreading;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Truck extends Thread{
	private int load;
	private int nLoads;
	private static long elevator1;
	private static long elevator2;
	private static AtomicLong counter= new AtomicLong(0);
	private static final Lock lock = new ReentrantLock(true);
	
	public Truck(int load, int nLoads) {
		this.load = load;
		this.nLoads = nLoads;
	}

	@Override
	public void run() {
		for(int i = 0; i < nLoads; i++) {
			loadElevator1(load);
			loadElevator2(load);
		}
	}
	
	public static long getWaitingCounter() {

		return counter.get();
	}

	static private void loadElevator2(int load) {
		 boolean done = false;
		 while(!done) {
			 if(lock.tryLock()) {
				 try {
					 elevator2 += load;
				 } finally {
					 lock.unlock();
					 done = true;
				 }
			 }
			 if(!done) {
				 counter.addAndGet(1); 
			 }
		 }
	}

	static private void loadElevator1(int load) {
		boolean done = false;
		 while(!done) {
			 if(lock.tryLock()) {
				 try {
					 elevator1 += load;
				 } finally {
					 lock.unlock();
					 done = true;
				 }
			 }
			 if(!done) {
				 counter.addAndGet(1); 
			 }
			 
		 }
	
	}

	public static long getElevator1() {
		return elevator1;
	}

	public static long getElevator2() {
		return elevator2;
	}
	
	
}
