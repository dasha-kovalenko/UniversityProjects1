import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args){
		TextProcessing p = new TextProcessing();
		File file = new File("d:" + File.separator + "text.txt");

		String str = "";
		PrintWriter writer;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			writer = new PrintWriter("output.txt");
			Scanner sc = new Scanner(reader);
			StringBuilder sb = new StringBuilder();
			while(sc.hasNext()){
				String s = sc.nextLine();
				sb.append(s);
			}
			str = sb.toString();
			ArrayList<Sentence> s = p.getSentences(str);
			Collections.sort(s, new SizeComparator());
			for(Sentence sent: s){
				System.out.println(sent.toString());
				writer.print(String.format("%s\r\n", sent.toString()));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
