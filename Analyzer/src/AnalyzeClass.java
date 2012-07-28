import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
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
import javax.swing.UIManager;


public class AnalyzeClass extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	JFrame frame = this;
	JTextArea programArea;
	LogPane colouredProgramPane;
	ArrayList<String>array = new ArrayList<String>();
	ArrayList<Integer>counts = new ArrayList<Integer>();
	PrintWriter writer;
	String className;
	

	public AnalyzeClass() throws HeadlessException, FileNotFoundException {
		super();
		createAndShowGUI();
	}
	
	public int getBranchCount(){
		return counts.size();
	}
	private void createAndShowGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(900,650));
		setTitle("Simple Analyzer");
		setLocation(200, 20);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		programArea = new JTextArea(42,48);
		programArea.setFont(new Font("Courier New", Font.PLAIN, 14));
		programArea.setEditable(false);
		JScrollPane scroll1 = new JScrollPane();
		scroll1.setViewportView(programArea);
		scroll1.setBorder(BorderFactory.createTitledBorder("Program Code"));
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		panel.setBackground(new Color(200, 230, 255));
		
		colouredProgramPane = new LogPane();
		colouredProgramPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		colouredProgramPane.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));

		JScrollPane scroll2 = new JScrollPane();
		scroll2.getViewport().add(colouredProgramPane);
		scroll2.setBorder(BorderFactory.createTitledBorder("Modyfied Program Code"));
		panel.add(scroll2, BorderLayout.CENTER);
		
		JButton analyzeButton = new JButton("Analyze");
		JButton executeButton = new JButton("Execute");
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(200,230,255));
		panel1.add(analyzeButton);
		panel1.add(executeButton);

        JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 220, 238));
		JMenu file = new JMenu("File");
		file.setForeground(new Color(139,0,139));
		file.setFont(new Font("Consolas", Font.ITALIC, 14));
		JMenuItem load = new JMenuItem("Load", new ImageIcon("open1.jpg"));
		load.setBackground(Color.CYAN);
		file.add(load);
		menuBar.add(file);
		setJMenuBar(menuBar);

		panel.add(panel1, BorderLayout.SOUTH);		
		this.add(panel,BorderLayout.CENTER);

		setVisible(true);
		load.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			colouredProgramPane.removeAll();
			array.clear();
			FileDialog fd = new FileDialog(frame,"Open File Dialog", FileDialog.LOAD);
			fd.setVisible(true);
			String filename = fd.getFile();
			String fname = fd.getDirectory() + filename;
			int a = 0;
			if (filename!= null) {
				a = filename.lastIndexOf('.');
				if(!filename.substring(a+1).equals("java"))
					JOptionPane.showMessageDialog(frame, "Choose file with \"java\"-extension!");
				else {
					try {
						array.add("import java.io.*;");
						Scanner scanner = new Scanner(new File(fname));
						StringBuilder stringBuilder = new StringBuilder();
						while(scanner.hasNext()){
							String tmp = scanner.nextLine();
							array.add(tmp);
							stringBuilder.append(tmp);
							stringBuilder.append("\n");
							if(tmp.contains("public class")){
								array.add("\n");
								StringTokenizer st = new StringTokenizer(tmp, " ");
								while(st.hasMoreTokens()){
									String token = st.nextToken();
									if(token.equals("class")){
										className = st.nextToken();
										break;
									}
								}
							}
						}
							JOptionPane.showMessageDialog(frame, "The file is successfully loaded. Press \"Analyze\" button.");
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
				}
			}
		}
	});
		
		analyzeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				colouredProgramPane.removeAll();
				
				Pattern pattern1 = Pattern.compile("(for[\\s]*[(][\\w]*[\\s]*[\\w]*[:]?[\\s]*[=]*[\\s]*[\\w]*[\\s]*[;]*[\\s]*[\\w]*" +
						"[\\s]*[<]?[>]?[\\s]*[\\w]*[\\s]*[\\w]*[;]?[\\.]?[\\s]*[\\w]*[(]?[)]?[;]?[\\s]*[\\w]*[+]*[-]*[)][\\s]*[{])|while[\\s]*[(]" +
						"[\\s]*[\\w]*[\\s]*[<]?[>]?[==]?[!=]?[\\.]?[\\s]*[\\w]*[(]?[)]?[)][\\s]*[{]|do[\\s]*[{]");
				int count = 0;
				counts = new ArrayList<Integer>(array.size());
				int i = 0;
				try {
					StringBuilder b = new StringBuilder();
					b.append(String.format("%s.java", className));
					writer = new PrintWriter(b.toString());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				while(i<array.size()){
					counts.add(0);
					i++;
				}
				for(String tmp : array){
					Matcher m = pattern1.matcher(tmp);
					while(m.find()){
						count++;
					}
				}
				String str = String.format("private static int[]counts = new int[%d];", count+1);
				StringBuilder b = new StringBuilder();
				b.append(String.format("public class %s {", className));
				StringBuilder b1 = new StringBuilder();
				b1.append("\tpublic static void getStatistics(){\r\n");
				b1.append("\t\tMyFrame frame = new MyFrame(counts);\r\n");
				b1.append("\t}\r\n");
				String function = b1.toString();

				if(array.contains(b.toString())){
					int ind = array.indexOf(b.toString());
					array.set(ind+1, str);
					array.set(ind+2, function);
				}
				count = 0;

				for(String tmp : array){
					Matcher m = pattern1.matcher(tmp);
					while(m.find()){
						count++;
						int a = counts.get(count);
						a++;
						counts.set(count, a);
						String s = m.group();
						tmp = m.replaceAll("");
						colouredProgramPane.appendStyledString(tmp, Color.BLACK);
						colouredProgramPane.appendStyledString(s, Color.RED);
						colouredProgramPane.appendStyledString("\n", Color.RED);
						writer.print(String.format("%s %s\r\n", tmp,s));
						String str1 = String.format("counts[%d]++;", count);
						colouredProgramPane.appendStyledString(String.format("\t%s", tmp), Color.BLACK);
						colouredProgramPane.appendStyledString(str1, Color.GREEN);
						writer.print(String.format("\t%s %s", tmp,str1));
					}
					if(tmp.equals(str)||tmp.equals(function)){
						if(tmp.equals(function))
							colouredProgramPane.appendStyledString(String.format("%s", tmp), Color.BLUE);
						else
							colouredProgramPane.appendStyledString(String.format("\t%s", tmp), Color.GREEN);
						writer.print(String.format("\t\t%s\r\n", tmp));
					}
					else {
						colouredProgramPane.appendStyledString(tmp, Color.BLACK);
						writer.print(String.format("%s\r\n", tmp));
					}
					colouredProgramPane.appendStyledString("\n", Color.WHITE);
				}
			writer.close();
			JOptionPane.showMessageDialog(frame, "Now run .bat-file to compile your .java-file and press \"Execute\"");
			}
			
		});
		
		executeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				Class<?> myClass;
				try {
					myClass = Class.forName(className);
					Method method = myClass.getMethod("main", String[].class);
					String[]args = null;
					method.invoke(null, new Object[] { args });
					method = myClass.getMethod("getStatistics", null);
					method.invoke(null);
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(frame, "Please, create .class-file!");
				}
				  catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					JOptionPane.showMessageDialog(frame, "Your file is impossible to execute! No method \"main\"");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) throws HeadlessException, FileNotFoundException {
		new AnalyzeClass();
	}
}
