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
		if(nickName.isEmpty()) nickName = "Unknown Name";
		frame.setTitle(String.format("Client: [%s]", nickName));
		try {
			clientSocket = new Socket("localhost", 8013);
			r = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			w = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			w.append(String.format("%s\r\n", nickName));
			w.flush();
			index = Integer.valueOf(r.readLine());
			inputThread = new Thread(new Receiver());
			inputThread.start();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(frame, "It is impossible to connect to the server. Unknown Host","Error!",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "It is impossible to connect to the server. IOError occured","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(1); 
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
		frame.setSize(300, 200);
		frame.setLayout(new BorderLayout());
		view = new JTextArea();
		view.setBackground(new Color(255, 245, 238));
		view.setForeground(new Color(139, 0, 139));
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
		frame.addWindowListener(new WndListener());
	}
	private class Receiver implements Runnable{

		@Override
		public void run() {
			try {
				while(true){
					if(clientSocket.isConnected()){
						String str = r.readLine();
						str = String.format("%s\r\n", str);
						synchronized (view) {
							view.append(str);
						}
					} else
						break;
				}
			}catch(IOException ioe){
				//обработать JOptionePane!!!
			}
		}
		
	}

	private class MsgFActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			try{
				if(clientSocket.isConnected()){
					String str = msgF.getText().trim();  
					if(!(str.isEmpty())) {
						w.append(String.format("[%s] %s\r\n", nickName, str));
						w.flush();
						view.append(String.format("[%s] %s\r\n", nickName, str));
						msgF.setText("");
					}
				}
			}catch(IOException ioe){
				JOptionPane.showMessageDialog(frame, "You can not send any messages, because server is not" +
						" connected","Error",JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		
	}
	private class WndListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent e) {
			try{
				if((clientSocket!=null) && (clientSocket.isConnected()) && !clientSocket.isClosed())
					clientSocket.close();
				if(r != null)
					r.close();
				if(w != null)
					w.close();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
	}

		@Override
		public void windowClosing(WindowEvent arg0) {}

		@Override
		public void windowDeactivated(WindowEvent arg0) {}

		@Override
		public void windowDeiconified(WindowEvent arg0) {}

		@Override
		public void windowIconified(WindowEvent arg0) {}

		@Override
		public void windowOpened(WindowEvent arg0) {}
		
	}
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new ClientClass();
			}
		});
	}

}
