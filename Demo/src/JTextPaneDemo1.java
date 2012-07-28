import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class JTextPaneDemo1 extends JFrame

{
	JTextPane textPane;
	JTextArea programArea;
	JFrame jf;
	ArrayList<String>array = new ArrayList<String>();
 

 public void init() throws Exception

 {

  final int w = 200;

  final int h = 200;

  //textPane = new JTextPane();

  textPane = new JTextPane()

  {

   public void setSize(Dimension d)

   {

    if(d.width <= getParent().getSize().width)

    {

     d.width = getParent().getSize().width;

    }

    super.setSize(d);

   }

 
   public void appendStyledString(String str, Color c) {
		setEditable(true);
		Document doc = getDocument();
		StyleContext sc = StyleContext.getDefaultStyleContext();
	    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
	    setCaretPosition(doc.getLength()); 
	    setCharacterAttributes(aset, false);
	   
	    try {
			doc.insertString(doc.getLength(), str, aset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	    setEditable(false);
	}

   public boolean getScrollableTracksViewportWidth()

   {

    return false;

   }

  };

  
  textPane.setEditable(false);
  //textPane.setPreferredSize(new Dimension(490,490));
 

  jf = new JFrame("JTextPaneDemo1");

  Container contentPane = jf.getContentPane();

 // contentPane.setLayout(null);

  JScrollPane scroll2 = new JScrollPane();

//  scroll2.setBounds(new Rectangle(30, 30, w, h));

  scroll2.getViewport().add(textPane);

  //contentPane.add(scroll2);
  
  
///////////////////////  
 
	JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	jf.setLayout(new BorderLayout());
	//panel.add(scroll1, BorderLayout.WEST);
	panel.add(scroll2, BorderLayout.EAST);
	
	JButton analyzeButton = new JButton("Analyze");
	JPanel panel1 = new JPanel();
	panel1.setBackground(new Color(200,230,255));
	panel1.add(analyzeButton, BorderLayout.CENTER);
	
    JMenuBar menuBar = new JMenuBar();
	menuBar.setBackground(new Color(255, 220, 238));
	JMenu file = new JMenu("File");
	file.setForeground(new Color(139,0,139));
	file.setFont(new Font("Consolas", Font.ITALIC, 14));
	JMenuItem load = new JMenuItem("Load", new ImageIcon("open1.jpg"));
	load.setBackground(Color.CYAN);
	file.add(load);
	menuBar.add(file);
	jf.setJMenuBar(menuBar);

	panel.add(panel1, BorderLayout.CENTER);		
	jf.add(panel);
	jf.setSize(400,400);
	jf.setVisible(true);

  load.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			FileDialog fd = new FileDialog(jf,"Open File Dialog", FileDialog.LOAD);
			fd.setVisible(true);
			String filename = fd.getFile();
			//File file = new File(filename);
			String fname = fd.getDirectory() + filename;
			//File file = new File(fname);
			int a = 0;
			if (filename!= null) {
				a = filename.lastIndexOf('.');
				if(!filename.substring(a+1).equals("java"))
					JOptionPane.showMessageDialog(jf, "Choose file with \"java\"-extension!");
				else {
					//if(filename != null && file != null){
						
						try {
							Scanner scanner = new Scanner(new File(fname));
							StringBuilder stringBuilder = new StringBuilder();
							while(scanner.hasNext()){
								String tmp = scanner.nextLine();
								array.add(tmp);
								stringBuilder.append(tmp);
								stringBuilder.append("\n");
								if(tmp.contains("main(String[]args)"))
									array.add("\n");
							}
							String programString = stringBuilder.toString();
							textPane.setText(programString);
							
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					//}
					/*else {
						JOptionPane.showMessageDialog(frame, "Choose file with \"java\"-\"extension!");
					}*/
				}
			}
			
		}
		
	});
	
	analyzeButton.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			

		}
		
	});
 }

 

 public static void main(String args[]) throws Exception

 {

  JTextPaneDemo1 jc = new JTextPaneDemo1();

  jc.addWindowListener(new WindowAdapter()

  {

   public void windowClosing(WindowEvent e)

   {

    System.exit(0);

   }

  });

  jc.init();

 }

 

}