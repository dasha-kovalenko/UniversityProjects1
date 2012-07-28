import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import sun.text.resources.FormatData;

public class ReflectionFrame extends JFrame{

	private JFrame frame = this;
	private JList list;
	private DefaultListModel model;
	private JTextField argTextField;
	private JTextArea answerTextArea;
	private JButton buttonCalculate;
	private ArrayList<ArrayList<String>> matrix = 
		new ArrayList<ArrayList<String>>();
	public String[] words = new String[12];
	public ReflectionFrame(String title, String[]words){
		super(title);
		this.words = words;
		createAndShowGUI(/*words*/);
	}
	private void createAndShowGUI(/*String[]words*/) {
       try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
        JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 245, 238));
		JMenu file = new JMenu(words[0]);
		file.setForeground(new Color(0,0,255));
		file.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
		JMenuItem open = new JMenuItem(words[1], new ImageIcon("open1.jpg"));
		open.setBackground(Color.CYAN);
		file.add(open);
		menuBar.add(file);
		setJMenuBar(menuBar);
		
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane scroll1 = new JScrollPane();
        scroll1.setViewportView(list);
        scroll1.setBorder(BorderFactory.createTitledBorder(words[2]));
        Container content = getContentPane();
        content.setLayout(new BorderLayout());
        content.add(scroll1, BorderLayout.WEST);
        argTextField = new JTextField(30);
        argTextField.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        argTextField.setEditable(true);
        JScrollPane scroll2 = new JScrollPane(argTextField);
        scroll2.setBorder(BorderFactory.createTitledBorder(words[3]));
        content.add(scroll2, BorderLayout.SOUTH);
        answerTextArea = new JTextArea(20,30);
        answerTextArea.setEditable(false);
        answerTextArea.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        answerTextArea.setForeground(new Color(139,0,139));
        JScrollPane scroll3 = new JScrollPane(answerTextArea);
        scroll3.setBorder(BorderFactory.createTitledBorder(words[4]));
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel p = new JPanel();
       
        buttonCalculate = new JButton(words[5]);
        panel.setLayout(new BorderLayout());
        panel1.add(buttonCalculate);
        panel2.add(scroll3);
        panel.add(panel1, BorderLayout.SOUTH);
        panel.add(panel2, BorderLayout.CENTER);
        content.add(panel, BorderLayout.CENTER);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 550);
        this.setVisible(true);
  
        
        buttonCalculate.addActionListener(new ButtonCalculateListener());
        
        open.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				model.clear();
				FileDialog fd = new FileDialog(frame,words[6], FileDialog.LOAD);
				fd.setVisible(true);
				String fname = fd.getDirectory() + fd.getFile();
				if(fname != null){
					try {
						BufferedReader r = new BufferedReader(new FileReader(fname));
						int n = Integer.parseInt(r.readLine()); 
						for(int i = 0; i < n; i++){
							String className = r.readLine();
							String methodName = r.readLine();
							int numberOfParams = Integer.parseInt(r.readLine());
							ArrayList<String> arr = new ArrayList<String>();
							for(int j = 0; j < numberOfParams; j++){
								arr.add(r.readLine());
							}
							matrix.add(arr);
							model.addElement(String.format("%s: %s (%s)", className,methodName,arr.toString()));
						}
						list.updateUI();
						r.close();
					}catch(FileNotFoundException fne){
						JOptionPane.showMessageDialog(frame, /*"The file is not found"*/words[7], 
								/*"Error"*/words[8], JOptionPane.ERROR_MESSAGE);
					}catch(NumberFormatException nfe){
						JOptionPane.showMessageDialog(frame, /*"Incorrect number"*/words[9], 
								/*"Error"*/words[8], JOptionPane.ERROR_MESSAGE);
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(frame, /*"Error during input/output"*/words[10], 
								/*"Error"*/words[8], JOptionPane.ERROR_MESSAGE);
					}
				}
			}
        	
        });
	}
	private class ButtonCalculateListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			int index = list.getSelectedIndex();
			String methodName = "";
			String className = "";
			ArrayList<String> arr = new ArrayList<String>();
			String paramString = "";
			ArrayList<String> arrParam = new ArrayList<String>();
			if(index != -1){
				StringTokenizer st = new StringTokenizer((String) list.getSelectedValue(),": ");
				className = st.nextToken();
				methodName = st.nextToken();
				arr = matrix.get(index);
				paramString = argTextField.getText();
				StringTokenizer st1 = new StringTokenizer(paramString," ");
				while(st1.hasMoreTokens()){
					arrParam.add(st1.nextToken());
				}
				if(arrParam.isEmpty())
					JOptionPane.showMessageDialog(frame, "Enter arguments!", "Error!", JOptionPane.ERROR_MESSAGE);
				try {
					Class c = Class.forName(className);
					Method[] methods = c.getMethods();
					Method method = null;
					int size = 0;
					for(Method m: methods){
						size = 0;
						if(m.getName().equals(methodName)){
							Class[] params = m.getParameterTypes();
							if(arr.size() == params.length){
								for(int i = 0; i < arr.size(); i++)
									if(arr.get(i).equals(params[i].getName()))
										size++;
								if(size == params.length &&   arrParam.size() != 0){
									method = m;
									Class[] parameterTypes = m.getParameterTypes();
									Object[]  obj = new Object[parameterTypes.length];
									for(int i = 0; i < parameterTypes.length; i++){
										String element = arr.get(i);
										String argument = arrParam.get(i);
										if(element.equals("int")|| element.equals("Integer"))
											obj[i] = Integer.parseInt(arrParam.get(i));
										else if(element.equals("double")|| element.equals("Double"))
											obj[i] = Double.parseDouble(argument);
										else if(element.equals("float")|| element.equals("Float"))
											obj[i] = Float.parseFloat(argument);
										else if(element.equals("long")|| element.equals("Long"))
											obj[i] = Long.parseLong(argument);
										else if(element.equals("char")|| element.equals("Char"))
											obj[i] = String.valueOf(argument);
										else if(element.equals("boolean")|| element.equals("Boolean"))
											obj[i] = Boolean.parseBoolean(argument);
										else if(element.equals("short")|| element.equals("Short"))
											obj[i] = Short.parseShort(argument);
										else if(element.equals("byte")|| element.equals("Byte"))
											obj[i] = argument.getBytes()[0];
										else if(element.equals("String"))
											obj[i] = argument;
										
									}
									method.setAccessible(true);
									method = c.getMethod(methodName, parameterTypes);
//									System.out.println(method.invoke(null, obj));
									answerTextArea.setText((method.invoke(null, obj)).toString());
									break;
								}
							}
						}
					}
				}catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}catch (SecurityException se) {
					se.printStackTrace();
				}catch (IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(frame, /*"Invalid parameters types!"*/words[11], 
						/*"Error"*/words[8], JOptionPane.ERROR_MESSAGE);
				} catch (IllegalAccessException e1) {
				e1.printStackTrace();
				} catch (InvocationTargetException e1) {
				e1.printStackTrace();
				}
				catch (NoSuchMethodException e1) {
				e1.printStackTrace();
				}
			}
		}
	}
}
	




