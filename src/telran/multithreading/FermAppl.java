package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.IntStream;

public class FermAppl {

	private static final int N_TRUCKS = 200;
	private static final int N_LOADS = 500;

	public static void main(String[] args) {
		
		Truck[] trucks = new Truck[N_TRUCKS];
		Instant start = Instant.now();
		startTrucks(trucks);
		waitingForFinishing(trucks);
		System.out.println("*".repeat(15));
		System.out.println("With one lock");
		System.out.println("*".repeat(15));
		System.out.printf("Report: elevator1 contains %d tons; elevator2 contains %d tons." 
				+ "\nrunning time %d\n", Truck.getElevator1(), 
				Truck.getElevator2(), ChronoUnit.MILLIS.between(start, Instant.now()));
		System.out.println("Total iterations at waiting for unlocking: " + Truck.getWaitingCounter());
		
		

		Truck2[] trucks2 = new Truck2[N_TRUCKS];
		Instant start2 = Instant.now();
		startTrucks2(trucks2);
		waitingForFinishing(trucks2);
		System.out.println();
		System.out.println("*".repeat(15));
		System.out.println("With two locks");
		System.out.println("*".repeat(15));
		System.out.printf("Report: elevator1 contains %d tons; elevator2 contains %d tons." 
				+ "\nrunning time %d\n", Truck2.getElevator1(), 
				Truck2.getElevator2(), ChronoUnit.MILLIS.between(start2, Instant.now()));
		System.out.println("Total iterations at waiting for unlocking: " + Truck2.getWaitingCounter());

	}

	private static void waitingForFinishing(Truck[] trucks) {
		Arrays.stream(trucks).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {

			}
		});
		
	}

	private static void startTrucks(Truck[] trucks) {
		IntStream.range(0, trucks.length)
		.forEach(i -> {
			trucks[i] = new Truck(1, N_LOADS);
			trucks[i].start();
		});
		
	}
	private static void startTrucks2(Truck2[] trucks) {
		IntStream.range(0, trucks.length)
		.forEach(i -> {
			trucks[i] = new Truck2(1, N_LOADS);
			trucks[i].start();
		});
		
	}

}
