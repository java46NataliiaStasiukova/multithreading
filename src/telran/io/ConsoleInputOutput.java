package telran.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputOutput implements InputOutput {
	BufferedReader reader;
	public ConsoleInputOutput()  {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public String readString(String prompt) {
		writeLine(prompt);
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void writeObject(Object obj) {
		System.out.print(obj);

	}

}