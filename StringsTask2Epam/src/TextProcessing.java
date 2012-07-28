
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextProcessing {
	String p1 = "(.*?)([\\.\\!\\?])+(\\s)*";
	Pattern p2 = Pattern.compile(p1);
	static String split = "\\s";
	private Pattern pattern1 = Pattern.compile(split);
	
	ArrayList<Sentence> getSentences(String input){
		
		Matcher m = p2.matcher(input);
		ArrayList<String> arr = new ArrayList<String>();
		while(m.find()){
			String s = m.group();
			arr.add(s);
		}

		ArrayList<Sentence> sens = new ArrayList<Sentence>();
		int i = 0;
		for(String str : arr){
			//формируем массив слов - параметр конструктора класса  Sentence
			String[] s1 = pattern1.split(str);
			Sentence s = new Sentence(s1,str);
			String[] s2 = new String[s1.length-1];
			if(i != 0){
				for(int j = 1; j < s1.length; j++)
					{s2[j-1] = s1[j];}
				s = new Sentence(s2,str);
			}
			else
				s = new Sentence(s1,str);
			sens.add(s);
			i++;
		}
		return sens;
	}
	
}
