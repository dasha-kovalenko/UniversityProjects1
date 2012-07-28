import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Visualisation extends JFrame implements Runnable {

	private static final long serialVersionUID = 6793329991387118619L;
	JMenu fileMenu = new JMenu("File");
	JMenuBar menuBar = new JMenuBar();
	JMenuItem openFileItem = new JMenuItem("Open");
	DrawingPanel drawingPanel = new DrawingPanel();
	JButton buttonStart = new JButton("Start");
	JButton buttonStop = new JButton("Stop");
	JButton buttonNext = new JButton("Next result");
	JButton buttonPrev = new JButton("Prev. result");
	String []s={"S","P"};
	DefaultTableModel tableModel = new DefaultTableModel();
	JTable jTable = new JTable(tableModel);
	
	Algo algo = null;
	int RecordCount = 0;
	JScrollPane jScrollPane = new JScrollPane(jTable);
	JLabel recordLabel = new JLabel("Record =", JLabel.RIGHT);
	JLabel rezLabel = new JLabel("   ", JLabel.RIGHT);
	JTextArea recordCountArea = new JTextArea();
	
	JPanel infoPanel = new JPanel();
	JTextArea recordArea = new JTextArea();
	int elapse = 1000;
	Visualisation mfr;
	GridBagConstraints gridBagConstraints;
	ExecutorService exec;
	Vector<int[][]> recordMatrices = new Vector<int[][]>();
	int kolkol=0;

	Visualisation() {

		this.setSize(900, 500);
		this.setTitle("Visualization");
		this.setResizable(false);
		Container c = this.getContentPane();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout layout = new GridBagLayout();
		c.setLayout(layout);
		placeBasicPanels(c);
		placeMenu();
		mfr = this;
		buttonStart.setEnabled(false);
		buttonStop.setEnabled(false);
		initInfoPanel();
		addActionListeners();

	}

	private void initInfoPanel() {
		infoPanel.setLayout(new GridLayout(4, 1));
		JPanel p4 = new JPanel(new BorderLayout());
		infoPanel.add(p4);
		
		
		p4.setBorder(new TitledBorder("Data"));
		p4.add(jScrollPane);
		jTable.setEnabled(false);
		
		JPanel startPanel = new JPanel();
		startPanel.add(buttonStart);
		startPanel.add(buttonStop);
		
		infoPanel.add(startPanel);
		
		startPanel=new JPanel();
		startPanel.add(recordLabel);
		startPanel.add(rezLabel);

		infoPanel.add(startPanel);
		startPanel=new JPanel();
		startPanel.add(buttonPrev);
		startPanel.add(buttonNext);
		buttonNext.setEnabled(false);
		buttonPrev.setEnabled(false);
		infoPanel.add(startPanel);
	}

	int max(int a,int b){
		return ((a>b)?a:b);
	}
	void func(int id,int ss,int p1,int p2) {
		try {
			Thread.sleep(elapse);
			if (id==algo.N){
				kolkol++;
				algo.pp=(p1>p2)?p1:p2;
				if (algo.pp==algo.RECORD){
					algo.count++;
					int [][]ar=new int[3][algo.N];
					for (int i=0;i<3; i++)
						for (int j=0; j<algo.N; j++)
							ar[i][j]=algo.p12[i][j];
					recordMatrices.addElement(ar);
					
				}
				else
				if (algo.pp<algo.RECORD){
					algo.RECORD=algo.pp;
					for (int i=0; i<algo.N; i++)
						algo.num[i]=algo.rez[i];
					algo.count=1;
					rezLabel.setText(algo.RECORD+"");
					recordMatrices.clear();
					int [][]ar=new int[3][algo.N];
					for (int i=0;i<3; i++)
						for (int j=0; j<algo.N; j++)
							ar[i][j]=algo.p12[i][j];
					recordMatrices.addElement(ar);
				}
				

				if ( elapse != 0 )
					drawingPanel.paint();
			}
			else{
				int tp;
			for (int k=0; k<algo.N; k++)
				if (algo.o[k]==0){
					algo.pp=(p1<p2)?p1:p2;
					tp=(p1<p2)?0:1;
					if (algo.pp+algo.s[k]+algo.p[k]>algo.RECORD) continue;
					algo.o[k]=1;
					algo.rez[id]=k; algo.kp++;
					algo.p12[0][id]=tp;
					algo.p12[1][id]=max(ss,algo.pp);
					algo.p12[2][id]=k;
					if (elapse != 0){
						algo.pp=max(ss,algo.pp)+algo.s[k]+algo.p[k];
						drawingPanel.paint();
					}
					if (p1<p2)
						func(id+1,max(p1,ss)+algo.s[k],max(ss,p1)+algo.s[k]+algo.p[k],p2);
					else func(id+1,max(p2,ss)+algo.s[k],p1,max(ss,p2)+algo.s[k]+algo.p[k]); 
					algo.o[k]=0; algo.kp--;
				}
			
			
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Visualisation fr1 = new Visualisation();

		fr1.setVisible(true);

	}

	public class DrawingPanel extends JPanel {

		private static final long serialVersionUID = -3842510366934034562L;

		public void paint() {
			this.setDoubleBuffered(true);
			Graphics g = this.getGraphics();
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawRect(0, 0, this.getWidth(), this.getHeight());
			if (algo != null) {
				int colWidth = (this.getWidth() - 40) / algo.pp;
				int h1=(this.getHeight()/6-10)/2;
				for (int i = 0; i < algo.kp; i++) {
					g.setColor(new Color(255,255,0));
					g.fill3DRect(20+algo.p12[1][i]* colWidth, 15+h1/2,(algo.s[algo.p12[2][i]])* colWidth,h1, true);
					g.setColor(Color.black);
					g.drawString((algo.p12[2][i]+1) + "",20+algo.p12[1][i]* colWidth, 25+h1/2);
					if (algo.p12[0][i]==0){
						g.setColor(new Color(255, 187, 255));
						g.fill3DRect(20+algo.p12[1][i]* colWidth,15+h1+h1/2,(algo.p[algo.p12[2][i]]+algo.s[algo.p12[2][i]])* colWidth,h1, true);
						g.setColor(Color.black);
						g.drawString((algo.p12[2][i]+1) + "",20+algo.p12[1][i]* colWidth,25+h1+h1/2);
						g.setColor(algo.colors[algo.p12[2][i]]);
					}
					else{
						g.setColor(new Color(148, 0, 211));
						g.fill3DRect(20+algo.p12[1][i]* colWidth,15+2*h1+h1/2,(algo.p[algo.p12[2][i]]+algo.s[algo.p12[2][i]])* colWidth,h1, true);
						g.setColor(Color.yellow);
						g.setFont(new Font("Comic Sans MS", Font.BOLD,12));
						g.drawString((algo.p12[2][i]+1) + "",20+algo.p12[1][i]* colWidth,25+2*h1+h1/2);
						g.setColor(algo.colors[algo.p12[2][i]]);
					}
					
					g.setColor(Color.black);
					g.drawString(algo.p12[1][i] + "",20+algo.p12[1][i]* colWidth,20+4*h1);
					for (int j = 0; 5 * j < 20+3*h1+h1/2; j += 2) {
						g.drawLine( 20+algo.p12[1][i]* colWidth,j * 5,20+algo.p12[1][i]* colWidth, (j + 1) * 5);
					}		
					
				}
				g.setColor(Color.black);
				g.drawString(((algo.p12[1][algo.kp-1]+algo.p[algo.p12[2][algo.kp-1]]+algo.s[algo.p12[2][algo.kp-1]])) + "",20+(algo.p12[1][algo.kp-1]+algo.p[algo.p12[2][algo.kp-1]]+algo.s[algo.p12[2][algo.kp-1]])* colWidth,20+4*h1);
				for (int j = 0; 5 * j < 20+3*h1+h1/2; j += 2) {
					g.drawLine( 20+((algo.p12[1][algo.kp-1]+algo.p[algo.p12[2][algo.kp-1]]+algo.s[algo.p12[2][algo.kp-1]]))* colWidth,j * 5,
							20+((algo.p12[1][algo.kp-1]+algo.p[algo.p12[2][algo.kp-1]]+algo.s[algo.p12[2][algo.kp-1]]))* colWidth, (j + 1) * 5);
				}	
				g.drawString("S",0,15+h1/2);
				g.drawString("P1",0,15+h1+h1/2);
				g.drawString("P2",0,15+2*h1+h1/2);
				}
				g.setColor(Color.BLACK);


			
			this.setBorder(getBorder());
		}
	}

	@Override
	public void run() {
		func(0,0,0,0);
		if (recordMatrices.size()>1) buttonNext.setEnabled(true);
		algo.cur=0;
		algo.p12 = recordMatrices.elementAt(algo.cur); algo.kp=algo.N;
		drawingPanel.paint();
		buttonStop.setEnabled(false);
	}

	public void addActionListeners() {
		
		openFileItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.getName().toLowerCase().endsWith(".txt");
					}

					@Override
					public String getDescription() {
						return "Text files file";
					}
				});

				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						if (exec != null && exec.isShutdown() == false) {
							exec.shutdown();
						}
						algo = null;
						elapse = 1000;
						buttonStart.setEnabled(true);
						File f = new File(chooser.getSelectedFile().getAbsolutePath());
						Scanner in = new Scanner(f);
						algo = new Algo(in);
						Object []ob={"S","P"};
						tableModel.setColumnCount(2);
						tableModel.setColumnIdentifiers(ob);
						tableModel.setRowCount(algo.N);
						for (int i = 0; i < algo.N; i++) {
								tableModel.setValueAt(algo.s[i],i,0);
								tableModel.setValueAt(algo.p[i],i,1);
						}
						
						buttonStop.setEnabled(false);
					} catch (FileNotFoundException fnfe) {
						JOptionPane.showMessageDialog(null, "File not found!");
						buttonStart.setEnabled(false);
					} catch (IOException fnfe) {
						JOptionPane.showMessageDialog(null,
								"Erratic input from file!");
						algo = null;
						recordMatrices.clear();
						buttonStart.setEnabled(false);
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null,
								"Erratic input from file!");
						algo = null;
						buttonStart.setEnabled(false);
						recordMatrices.clear();
					} catch (Exception e) {
						algo = null;
						buttonStart.setEnabled(false);
						recordMatrices.clear();
					}

				}
			}

		});
		buttonStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (algo != null) {
					
					buttonStart.setEnabled(false);
					buttonStop.setEnabled(true);

					exec = Executors.newCachedThreadPool();
					exec.execute(mfr);
					exec.shutdown();

				}
			}
		});
		
		buttonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				elapse = 0;
				buttonStart.setEnabled(false);
				buttonStop.setEnabled(false);
			}
		});

		buttonNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			//	algo.cur = (algo.cur < recordMatrices.size()-1 ? algo.cur+1 : 0);
				algo.cur++;
				algo.p12 = recordMatrices.elementAt(algo.cur);
				if (algo.cur==recordMatrices.size()-1) buttonNext.setEnabled(false);
				
				buttonPrev.setEnabled(true);
				drawingPanel.paint();
			}
		});
		buttonPrev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				algo.cur--;
				algo.p12 =  recordMatrices.elementAt(algo.cur);
				if (algo.cur==0) buttonPrev.setEnabled(false);
				
				drawingPanel.paint();
				buttonNext.setEnabled(true);
			}
		});
	}

	public void placeBasicPanels(Container c) {
		JPanel panDr = new JPanel();
		panDr.setLayout(new BorderLayout());
		panDr.setBorder(BorderFactory.createTitledBorder("visualization"));
		panDr.add(drawingPanel, BorderLayout.CENTER);
		

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 6;
		gridBagConstraints.gridheight = 22;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.weightx = 40;
		gridBagConstraints.weighty = 100;
		c.add(infoPanel, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 22;
		gridBagConstraints.gridheight = 22;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.weightx = gridBagConstraints.weighty = 100;
		c.add(panDr, gridBagConstraints);

	}

	public void placeMenu() {
		menuBar.add(fileMenu);
		fileMenu.add(openFileItem);
		this.setJMenuBar(menuBar);

	}

}
class Algo {
	public int N;
	public int []s;
	public int []p;
	public int [][] p12;
	public int [] t,o,num,rez;
	public int RECORD = Integer.MAX_VALUE,pp,kp,count=0,cur;
	public Color [] colors;
	Algo(Scanner in) throws IOException, NumberFormatException
	{
		kp=0;
		RECORD = Integer.MAX_VALUE;
		count=0; cur=-1;
		 N = in.nextInt();
		 s = new int [N];
		 p = new int [N];
		 p12 = new int [3][N];
		 t = new int [N];
		 o = new int [N];
		 rez = new int [N];
		 num = new int [N];
		 colors = new Color[N];
		for (int i = 0; i < N; i++)
		{
			s[i]=in.nextInt();
			p[i]=in.nextInt();
			t[i]=o[i]=0;
			num[i]=i;
			colors[i] = new Color((int)(Math.random()* 255),(int)(Math.random()* 255),(int)(Math.random()* 255));
			
		}
	}

	

}
