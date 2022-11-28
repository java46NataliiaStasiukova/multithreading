package telran.race;

import java.util.concurrent.atomic.AtomicInteger;

public class Race {
	private int distance;
	private int minSleep;
	private int maxSleep;
	private static AtomicInteger winner = new AtomicInteger(-1);
	public Race(int distance, int minSleep, int maxSleep) {
		this.distance = distance;
		this.minSleep = minSleep;
		this.maxSleep = maxSleep;
	}
	public int getWinner() {

		return Race.winner.getAndSet(-1);
	}
	public void setWinner(int winner) {
		
		Race.winner.compareAndSet(-1, winner);
	}
	public int getDistance() {
		return distance;
	}
	public int getMinSleep() {
		return minSleep;
	}
	public int getMaxSleep() {
		return maxSleep;
	}

}