package telran.multithreading.consumer;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;
	
	public Receiver(MessageBox messageBox) {
		//FIXME - thread should not be a Daemon one
		setDaemon(true);
		this.messageBox = messageBox;
	}

	@Override
	public void run() {
		while(true) {
			try {
				String message = messageBox.get();
				System.out.println(message + getName());
			} catch (InterruptedException e) {

			}
		}
	}
}
