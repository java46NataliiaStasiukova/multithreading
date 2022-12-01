package telran.multithreading.consumer;

import telran.multithreading.MessageBox;
import java.util.concurrent.atomic.AtomicInteger;

public class Receiver extends Thread {
	private MessageBox messageBox;
	static volatile boolean running = true;
	static String message;
	private static AtomicInteger messagesCounter = new AtomicInteger(0);
	private static AtomicInteger tmp = new AtomicInteger(-1);
	
	public Receiver(MessageBox messageBox) {
		//FIXME - thread should not be a Daemon one
		//setDaemon(true);
		this.messageBox = messageBox;
	}
	public static int getMessagesCounter() {
		return messagesCounter.get();
	}

	@Override
	public void run() {

		while(running) {
			try {
				message = messageBox.get();
				System.out.println(message + getName());
				messagesCounter.addAndGet(1);
			} catch (InterruptedException e) {

			} 
		} 
		if(tmp.compareAndSet(-1, 1)) {
			String takenMessage = messageBox.take();
			if(takenMessage != null && !takenMessage.equals(message)) {
					System.out.println(takenMessage + getName() + " taken message");
					messagesCounter.addAndGet(1);
			}
		}	
	}
    public static void stopReceiver() {
        running = false;
    }

}