package telran.multithreading.executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ClientImitator extends Thread{
	private static final int MIN_REQUEST_TIMEOUT = 2000;
	private static final int MAX_REQUEST_TIMEOUT = 3000;
	int nRequests;
	BlockingQueue<Request> queue;
	
	public ClientImitator(int nRequests, BlockingQueue<Request> queue) {
		this.nRequests = nRequests;
		this.queue = queue;
	}
	
	@Override
	public void run(){
		for(int i = 0; i < nRequests; i++) {
			Request request = new Request(getRequestTimeout());
			try {
				queue.put(request);
			} catch (InterruptedException e) {

			}
		}
	}

	private long getRequestTimeout() {
		
		return ThreadLocalRandom.current().nextInt(MIN_REQUEST_TIMEOUT, MAX_REQUEST_TIMEOUT);
	}

}
