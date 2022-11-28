package telran.multithreading.producer;

import telran.multithreading.MessageBox;

public class Sender extends Thread {
	private static final long TIME_OUT = 10;
	private MessageBox messageBox;
	private int nMessages;
	
	public Sender(MessageBox messageBox, int nMessages) {
		this.messageBox = messageBox;
		this.nMessages = nMessages;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < nMessages; i++) {
			try {
				messageBox.put(String.format("message%d from thread ", i));
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	
}
