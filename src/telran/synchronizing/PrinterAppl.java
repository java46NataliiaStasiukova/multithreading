package telran.synchronizing;

public class PrinterAppl {

	public static void main(String[] args) {
		final int N_PRINTERS = 4;
		final int N_NUMBERS = 100;
		final int N_PORTIONS = 10;
		Printer[] printers = createPrinters(N_PRINTERS, N_NUMBERS, N_PORTIONS);
		setPrinters(printers);
		startPrinters(printers);
		printers[0].interrupt();	
	}
	private static void startPrinters(Printer[] printers) {
		for(int i = 0; i < printers.length; i++) {
			printers[i].start();
		}		
	}
	private static void setPrinters(Printer[] printers) {
		for(int i = 0; i < printers.length; i++) {
			if(i == printers.length - 1) {
				printers[i].setPrinter(printers[0]);
			} else {
				printers[i].setPrinter(printers[i + 1]);
			}
		}		
	}
	private static Printer[] createPrinters(int n_PRINTERS, int n_NUMBERS, int n_PORTIONS) {
		Printer[] res = new Printer[n_PRINTERS];	
		for(int i = 0; i < res.length; i++) {
			res[i] = new Printer(String.valueOf(i + 1), n_NUMBERS, n_PORTIONS);
		}	
		return res;
	}

}