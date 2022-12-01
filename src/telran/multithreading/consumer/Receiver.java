package telran.multithreading.consumer;

import telran.multithreading.MessageBox;
import java.util.concurrent.atomic.AtomicInteger;

public class Receiver extends Thread {
	private MessageBox messageBox;
	static volatile boolean running = true;
	static String message;
	private static AtomicInteger messagesCounter = new AtomicInteger(0);
	
	public Receiver(MessageBox messageBox) {
		this.messageBox = messageBox;
	}
	public static int getMessagesCounter() {
		return messagesCounter.get();
	}
	@Override
	public void run() {
		message = null;
		while(running) {
			try {
				message = messageBox.get();
				System.out.println(message + getName());
				messagesCounter.addAndGet(1);
			} catch (InterruptedException e) {

			} 
		} 
		while((message = messageBox.take()) != null) {
			System.out.println(message + getName() + " taken");
			messagesCounter.addAndGet(1);
		}
	}
    public static void stopReceiver() {
        running = false;
    }

}