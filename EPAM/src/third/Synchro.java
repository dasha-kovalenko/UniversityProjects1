package third;

import java.io.*;

public class Synchro {
	FileWriter fileWriter;

	public Synchro(String file) throws IOException {
		fileWriter = new FileWriter(file, true);
	}

	public void closeFileWriter() {
		try {

			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void writing(String str, int i) {
		try {
			System.out.print(str + i);
			fileWriter.append(str + i);
			Thread.sleep((long) (Math.random() * 20));
			System.out.print ("->" + i + " ");
			fileWriter.append("->" + i + " ");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
