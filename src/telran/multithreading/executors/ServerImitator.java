package telran.multithreading.executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImitator extends Thread {
	private static final int N_THREADS = 10;
	BlockingQueue<Request> queue;
	public ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
	boolean running = true;
	
	public ServerImitator(BlockingQueue<Request> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Request request = queue.take();
				executor.execute(request);
			} catch (InterruptedException e) {
				processRequests();
				break;
			}
		}
	}

	private void processRequests() {
		Request request = null;
		while((request = queue.poll()) != null) {
			executor.execute(request);
		}
		
	}



}
