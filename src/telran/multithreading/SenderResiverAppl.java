package telran.multithreading;

import java.util.stream.IntStream;

import telran.multithreading.consumer.Receiver;
import telran.multithreading.producer.Sender;

public class SenderResiverAppl {

	private static final int N_RECEIVERS = 10;
	private static final int N_MESSAGES = 20;

	public static void main(String[] args) throws InterruptedException {
		Receiver receivers[] = new Receiver[N_RECEIVERS];
		MessageBox messageBox = new MessageBox();
		startReceivers(receivers, messageBox);
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		sender.join();
		Thread.sleep(100);//FIXME remove this statement

	}

	private static void startReceivers(Receiver[] receivers, MessageBox messageBox) {
		IntStream.range(0, N_RECEIVERS).forEach(i -> {
			receivers[i] = new Receiver(messageBox);
			receivers[i].start();
		});	
	}

}
