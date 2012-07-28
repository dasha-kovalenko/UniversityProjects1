import java.io.*;
import java.util.*;
import java.util.regex.*;

public class CommentRemover {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("ServerClass.java"));
		PrintWriter writer = new PrintWriter("output.java");
		
		// //comment  OR /*comment*/
		Pattern pattern = Pattern.compile("(/{2,}.*)|(/{1}[*]{1,}(.|\\s)*?\\*/)++");
		
		//  "/**string*/"
		Pattern p1 = Pattern.compile("((.*)?[\"](.*)?/{1}[*]{1,}(.|\\s)*?\\*/[\"](.*)?)++");
		Pattern p1_1 = Pattern.compile(".*[\"].*[\"]");
		Pattern p1_2 = Pattern.compile("(.*)?[\"](.*)?/{2,}(.*)?[\"]");
		
		// /**Begin comment
		Pattern p2 = Pattern.compile("/{1}[*]{1,}(.*)?");
		//  End comment**/
		Pattern p3 = Pattern.compile("(.*)?[*]{1,}/{1}");
		
		
		//  "/**begin string ... end string*/"
		Pattern p4 = Pattern.compile("(.*)?[\"]/{1}[*]{1,}(.|\\s)*?[\"](.*)?");
		
		
		String tmp = "";
		while(scanner.hasNext()){
			tmp = scanner.nextLine();
			Matcher m = pattern.matcher(tmp);
			Matcher m1_2 = p1_2.matcher(tmp);
			System.out.println(tmp);
			while(m.find()){
				Matcher m1 = p1.matcher(tmp);
				System.out.println(tmp);
				
				if(!m1.find() && !m1_2.find())
					tmp = m.replaceAll("");
			}
			Matcher m1 = p1.matcher(tmp);
			Matcher m1_1 = p1_1.matcher(tmp);
			Matcher m2 = p2.matcher(tmp);
			if(!m1.find()&&!m1_1.find()){
				System.out.println(tmp);
				
				if(m2.find()){
					Matcher m4 = p4.matcher(tmp);
					if(!m4.find()){	
						tmp = "";
						while(tmp!=null){
							tmp = scanner.nextLine();
							System.out.println(tmp);
							
							Matcher m3 = p3.matcher(tmp);
							if(m3.find()){
								tmp = "";
								break;
							}
							else 
								tmp = "";
						}
					}
				}
			}
			System.out.println(tmp);
			
			writer.print(String.format("%s\r\n", tmp));
		}
		scanner.close();
		writer.close();
	}
}
