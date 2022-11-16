package telran.synchronizing;

public class Printer extends Thread{
	private static final long TIMEOUT = 2000;
	Printer nextPrinter;
	private String symbol;
	private int amount;
	private int portion;

	public Printer(String string, int amount, int portion) {
		this.symbol = string;
		this.amount = amount;
		this.portion = portion;
	}

	public void setPrinter(Printer nextPrinter) {
		this.nextPrinter = nextPrinter;
	}
	@Override
	public void run() {
		int count = 0;
		while(amount != 0) {
			try {
				sleep(TIMEOUT);
			} catch (InterruptedException e) {
				while(count != portion) {
					System.out.print(symbol);
					try {
						sleep(TIMEOUT);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					amount--;
					count++;
				}
				count = 0;
				System.out.println();
				nextPrinter.interrupt();
			}
		}
	}

}