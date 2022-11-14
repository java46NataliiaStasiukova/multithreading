package telran.race;


public class ThreadsRace {

	public static void main(String[] args) throws InterruptedException {
		InputOutput io = new ConsoleInputOutput();
		Integer nRaces = io.readInt("Enter number of Threads in range [3, 10]", "no number ", 3, 10);
		io.writeLine(nRaces);
		Integer distance = io.readInt("Enter number of Races in range [100, 3500]", "no number ", 100, 3500);
		io.writeLine(nRaces);
		Race[] races = createRaces(nRaces, distance);
		runRaces(races);
		System.out.printf("Congratulations to thread %s (winner)", getWinner(races));	
	}

private static void runRaces(Race[] races) throws InterruptedException {
	for(int i = 0; i < races.length; i++) {
		races[i].start();
	}	
	for(int i = 0; i < races.length; i++) {
		races[i].join();
	}	
		
	}

private static Race[] createRaces(int nRaces, int distance) {
		Race[] res = new Race[nRaces];
		for(int i = 0; i < nRaces; i++) {
			res[i] = new Race(distance, "race-" + (i + 1));
		}
		return res;
	}

private static String getWinner(Race[] races) {
	long tmp = Long.MAX_VALUE;
	String res = "";
	for(int i = 0; i < races.length; i++) {
		if(races[i].runningTime < tmp) {
			tmp = races[i].runningTime;
			res = races[i].name;
		}
	}
	return res;
}
	
	
	


}