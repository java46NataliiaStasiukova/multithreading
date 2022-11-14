package telran.race;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Race extends Thread{

	private int nRuns;
	public String name;
	private Instant start;
	public long runningTime = 0;
	
	Race(int nRuns, String name){
		this.nRuns = nRuns;
		this.name = name;
	}

	@Override
	public void run() {
		start = Instant.now();
		for(int i = 0; i < nRuns; i++) {
			try {
				sleep(getRandomSeconds());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(name);
		}
		runningTime = ChronoUnit.MILLIS.between(start, Instant.now());
	}
	private int getRandomSeconds() {

		return new Random().nextInt(2, 6);
	}
}

