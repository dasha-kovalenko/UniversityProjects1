import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import sun.text.resources.FormatData;


public class ReflectionTest {
	public static int language = -1;
	public static void main(String[] args) {

		String title = "";
		String[] words = new String[12];
		String[] keys = {
			"menuFile",
			"menuOpen",
			"functions",
			"arguments",
			"answer",
			"calculate",
			"openFileDialog",
			"the_file_is_not_found",
			"error",
			"incorrect_number",
			"error_during_input/output",
			"invalid_parameters_types"
		};
		String country = "";
		Locale[] locales = {
				new Locale("en","GB"),
				new Locale("fr","FR"),
		};
		
		String s = JOptionPane.showInputDialog(null, "Enter language: English or French");
		if(s == null || s.isEmpty()) {
			s = "english";
			if(JOptionPane.CANCEL_OPTION != 1)
				System.exit(0);
			/**JOptionPane.showMessageDialog(null, "You haven't chosen any language. By default it " +
					"is English.", "Message!", JOptionPane.OK_CANCEL_OPTION);*/
		}
		if(s.toLowerCase().equals("english")) language = 0;
		else if(s.toLowerCase().equals("french")) language = 1;
		else JOptionPane.showMessageDialog(null, "Incorrect language", "Error", JOptionPane.ERROR_MESSAGE);
		if(language != -1){
			Locale current = locales[language];
			ResourceBundle rb = ResourceBundle.getBundle("text", current);
			try {
				String st = rb.getString("title");
				for  (int j = 0; j < words.length; j++){
					words[j] = rb.getString(keys[j]);
				}
				title = new String(st.getBytes("ISO-8859-1"), "UTF-8");
				DateFormat formatData = DateFormat.getDateInstance(DateFormat.MEDIUM,current);
			    NumberFormat format = NumberFormat.getCurrencyInstance(current);
			    Date date = new Date();
			    String str = formatData.format(new Date()) + " " + format.format(555);
			    JOptionPane.showMessageDialog(null, str, "Welcome!", JOptionPane.OK_CANCEL_OPTION);
			    ReflectionFrame frame = null;
			    frame = new ReflectionFrame(title,words);
			} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
			}
		}
	}
}

