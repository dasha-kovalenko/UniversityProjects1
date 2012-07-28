import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ServerClass {

	private JFrame frame;
	private JTextArea chatTextArea;
	private Thread acceptingThread;
	private ServerSocket serverSocket;
	//private Socket clientSocket;
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
			e.printStackTrace();
		}
	}
	
	public void createAndShowGUI() {
		frame = new JFrame("Chat Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 500));
		frame.setBackground(new Color(255,255,0));
		frame.setLayout(new BorderLayout());
		
		chatTextArea = new JTextArea();
		JScrollPane chatTextAreaScrollPane = new JScrollPane();
		chatTextArea.setEditable(false);
		chatTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
		chatTextAreaScrollPane.setViewportView(chatTextArea);
		frame.add(chatTextAreaScrollPane);
		
		frame.setVisible(true);
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
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private class ClientHandler implements Runnable {
		private int index;
		private boolean welcomeFlag = false;
	/**	BufferedWriter out;
		BufferedReader in;*/
		
		public ClientHandler(int index) {
			this.index = index;
		}
		
		public void run() {
			try {
				//то, что в ифе, отработает 1 раз. иниц-ть потоки, послать приветствие клиенту
				if (socketsMap.get(index).isConnected()) {
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socketsMap.get(index).getOutputStream()));
					BufferedReader in = new BufferedReader(new InputStreamReader(socketsMap.get(index).getInputStream()));
					out.append(String.format("%d\r\n", index));
					out.append("Greetings from server!\r\n");
					out.flush();//выталкиваем
					
					while (true) {
						if(in != null){
							String s = String.format("%s\r\n", in.readLine());
							chatTextArea.append(s);
							for (Integer i: socketsMap.keySet()) {
							if (i != index) {
								BufferedWriter outc = new BufferedWriter(new OutputStreamWriter(socketsMap.get(i).getOutputStream()));
								outc.append(s);
								outc.flush();
							}
						}
					}
				}
			}
				
				
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(frame, String.format("IO error occured during sending or recieving messages to client #%d!", index), "Error", JOptionPane.ERROR_MESSAGE);
				ioe.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ServerClass sc = new ServerClass();
			}
		});

	}

}
