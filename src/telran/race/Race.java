package telran.race;

import java.time.Instant;
import java.util.*;

public class Race extends Thread{
	private int distance;
	private int minSleep;
	private int maxSleep;
	private Instant time;
	private Map<Integer, Long> winnersList;
	
	public Race(int distance, int minSleep, int maxSleep) {
		this.distance = distance;
		this.minSleep = minSleep;
		this.maxSleep = maxSleep;
		winnersList = new LinkedHashMap<>();
	}
	public Map<Integer, Long> getWinner() {
		return winnersList;
	}
	public synchronized static void setWinners(int winner, long time, Map<Integer, Long> winnersList) {
		winnersList.put(winner, time);
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	public Instant getTime() {
		return time;
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
