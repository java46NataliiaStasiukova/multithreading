package telran.multithreading;


public class Worker extends Thread {
static Object resource1 = new Object();
static Object resource2 = new Object();
static Object resource3 = new Object();
static int nRuns = 1;

void f1() {
	synchronized (resource1) {
		
		synchronized (resource2) {
			synchronized (resource3) {
				System.out.println("f1");
			
			}
		}
	}
}
void f2() {
	synchronized (resource3) {
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (resource2) {
			System.out.println("f2");
		
		}
	}
}
void f3() {
	synchronized (resource2) {
		synchronized (resource3) {
			System.out.println("f3");
		}
	}
}
@Override
public void run() {
	for(int i = 0; i < nRuns; i++) {
		f1();
		f2();
		f3();
	}
}

}