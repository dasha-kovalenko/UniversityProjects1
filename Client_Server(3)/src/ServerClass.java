import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ServerClass {

	private JFrame frame;
	private JTextArea chatTextArea;
	private Thread acceptingThread;
	private ServerSocket serverSocket;
    private TreeMap<Integer, Socket> socketsMap;
	private TreeMap<Integer, Thread> threadsMap;
	private Integer counter = 0;
	
	public ServerClass() {
		createAndShowGUI();
		try {
			threadsMap = new TreeMap<Integer, Thread>();
			socketsMap = new TreeMap<Integer, Socket>();
			serverSocket = new ServerSocket(8013);
			acceptingThread = new Thread(new ClientAccepter());
			acceptingThread.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "IO error occured during creating server socket!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	public void createAndShowGUI() {
		frame = new JFrame("Chat Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 500));
		frame.setLocation(700, 100);
		frame.setLayout(new BorderLayout());
		
		chatTextArea = new JTextArea();
		JScrollPane chatTextAreaScrollPane = new JScrollPane();
		chatTextArea.setEditable(false);
		chatTextArea.setBackground(new Color(240, 255, 255));
		chatTextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		chatTextAreaScrollPane.setViewportView(chatTextArea);
		frame.add(chatTextAreaScrollPane);
		
		frame.setVisible(true);
		frame.addWindowListener(new WndServerListener());
	}
	
	
	private class ClientAccepter implements Runnable {
		public void run() {
			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					socketsMap.put(counter, clientSocket);
					Thread t = new Thread(new ClientHandler(counter));
					threadsMap.put(counter, t);
					t.start();
					counter++;
				} catch (IOException e) {
					JOptionPane.showMessageDialog(frame, "IO error occured during accepting client socket!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	private class ClientHandler implements Runnable {
		private int index;
		private boolean welcomeFlag = false;
		private Socket s = null;
		private BufferedReader in = null;
		private BufferedWriter out = null;
		private String nickName = "Unknown Name";
		
		public ClientHandler(int index) {
			this.index = index;
		}
		
		public void run() {
			s = socketsMap.get(index);
			if(s != null){
				try {
					//иниц-ть потоки, послать приветствие клиенту
					
					if (s.isConnected() && !s.isClosed()) {
						out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
						in = new BufferedReader(new InputStreamReader(s.getInputStream()));
						nickName = in.readLine();
						out.append(String.format("%d\r\n", index));
						out.append("Greetings from server!\r\n");
						out.flush();//выталкиваем
						
						for (Integer i: socketsMap.keySet()) {
							if (i != index) {
								BufferedWriter outc = new BufferedWriter(new 
										OutputStreamWriter(socketsMap.get(i).getOutputStream()));
								outc.append(String.format("Client <%s> is online", nickName));
								outc.flush();
							}
						}
						
						while (s.isConnected() && !s.isClosed()) {
							if(in != null){
								String str = String.format("%s\r\n", in.readLine());
								if(!str.isEmpty()){
									chatTextArea.append(str);
									for (Integer i: socketsMap.keySet()) {
										if (i != index) {
											BufferedWriter outc = new BufferedWriter(new 
													OutputStreamWriter(socketsMap.get(i).getOutputStream()));
											outc.append(str);
											outc.flush();
										}
									}
								}
							}
						}
					}
					
					
				} catch (IOException ioe) {
					try{
						
						s.close();
						in.close();
						chatTextArea.append(String.format("Client <%s> is offline\r\n", nickName));

						for (Integer i: socketsMap.keySet()) {
							if (i != index) {
								BufferedWriter outc = new BufferedWriter(new 
										OutputStreamWriter(socketsMap.get(i).getOutputStream()));
								
								outc.append(String.format("Client <%s> is offline\r\n", nickName));
								outc.flush();
							}
						}
						socketsMap.remove(index);

					}catch(IOException e){}
				}
			}
		}
	}
	
	private class WndServerListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			if(!serverSocket.isClosed())
				try {
					
					for (Integer i: socketsMap.keySet()) {
							BufferedWriter outc = new BufferedWriter(new OutputStreamWriter(socketsMap.get(i).getOutputStream()));
							outc.append(String.format("Server is not connected!\r\n"));
							outc.flush();
					}
					serverSocket.close();
//					threadsMap.clear();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ServerClass sc = new ServerClass();
			}
		});

	}

}
