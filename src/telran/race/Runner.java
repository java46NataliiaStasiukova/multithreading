package telran.race;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class Runner extends Thread {
private Map<Integer, Long >winnersList;
private Race race;
private int runnerId;
public Runner(Race race, int runnerId) {
	this.race = race;
	this.runnerId = runnerId;
	winnersList = race.getWinner();
}
@Override
public void run() {
	int sleepRange = race.getMaxSleep() - race.getMinSleep() + 1;
	int minSleep = race.getMinSleep();
	int distance = race.getDistance();
	Instant time = race.getTime();
	
	for (int i = 0; i < distance; i++) {
		try {
			sleep((long) (minSleep + Math.random() * sleepRange));
		} catch (InterruptedException e) {
			throw new IllegalStateException();
		}
		System.out.println(runnerId);
	}
	Race.setWinners(runnerId, ChronoUnit.MILLIS.between(time, Instant.now()), winnersList);
}


}