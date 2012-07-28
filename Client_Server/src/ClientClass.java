import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;

public class ClientClass {
	private JFrame frame;
	private JTextArea view;
	private JTextField msgF;
	private String nickName = "Unknown Name";
	private Socket clientSocket;
	private BufferedReader r;
	private BufferedWriter w;
	private Thread inputThread;
	private int index = -1;
	
	public ClientClass(){
		createGUI();
		nickName = JOptionPane.showInputDialog(frame, "Enter your nickName");
		frame.setTitle(String.format("Client: [%s]", nickName));
		try {
			clientSocket = new Socket("localhost", 8013);
			r = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			w = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			index = Integer.valueOf(r.readLine());
			inputThread = new Thread(new Receiver());
			inputThread.start();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(frame, "It is impossible to connect to the server. Unknown Host","Error!",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "It is impossible to connect to the server. IOError occured","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1); //чтобы не было NullPointerException
		}
		
	}
	

	public void createGUI() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		frame = new JFrame("Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLayout(new BorderLayout());
		view = new JTextArea();
		view.setFont(new Font("Consolas", Font.PLAIN, 12));
		view.setEditable(false);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(view);
		frame.add(scroll, BorderLayout.CENTER);
		msgF = new JTextField(10);
		msgF.setFont(new Font("Consolas", Font.PLAIN, 12));
		msgF.addActionListener(new MsgFActionListener());
		frame.add(msgF, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	private class Receiver implements Runnable{

		@Override
		public void run() {
			try {
				//сделаем демоном потом, поэтому можно true
				while(true){
					if(clientSocket.isConnected()){
						String str = r.readLine();
						str = String.format("%s\r\n", str);
						synchronized (view) {
							view.append(str);
						}
					} else
						break;//выход из цикла/блока кода
				}
			}catch(IOException ioe){
				//обработать JOptionePane!!!
			}
		}
		
	}

	private class MsgFActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			try{
				if(clientSocket.isConnected()){
					String str = msgF.getText().trim();  //чтобы не было пробелов
					if(!(str.isEmpty())) {
						//w.append(String.format("%d\r\n", index));
						w.append(String.format("[%s] %s\r\n", nickName, str));
						w.flush();
						view.append(String.format("[%s] %s\r\n", nickName, str));
						msgF.setText("");
					}
				}
				//IO - Input/Output
			}catch(IOException ioe){
				JOptionPane.showMessageDialog(frame, null);
			}
		}
		
	}
	private class WndListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			try{
				if((clientSocket!=null) && (clientSocket.isConnected()))
					//clientSocket.close();
				if(r != null)
					r.close();
				if(w != null)
					w.close();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new ClientClass();
			}
		});
	}

}
