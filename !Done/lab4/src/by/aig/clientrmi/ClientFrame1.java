package by.aig.clientrmi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import by.aig.common.Candidate;
import by.aig.common.IMeetingDAO;

public class ClientFrame1 extends JFrame {

	private ClientFrameBundle clientFrameBundle;

	private JRadioButton femaleButton;
	private JRadioButton maleButton;
	private JTextField minAgeField;
	private JTextField maxAgeField;
	private JComboBox countryBox;
	private JComboBox cityBox;
	private JButton findButton;

	private JButton russianButton;
	private JButton englishButton;

	private TitledBorder selectParamsPanelTitledBorder;
	private TitledBorder outputPanelTitledBorder;
	private TitledBorder createPanelTitledBorder;
	private TitledBorder deletePanelTitledBorder;
	private TitledBorder langPanelTitledBorder;

	private JLabel fromAgeLabel;
	private JLabel toAgeLabel;

	private JPanel selectParamsPanel;
	private JPanel outputPanel;
	private JPanel footerPanel;

	private JRadioButton femaleCreateButton;
	private JRadioButton maleCreateButton;
	private JTextField ageCreateField;
	private JTextField nameCreateField;
	private JComboBox countryCreateBox;
	private JComboBox cityCreateBox;
	private JTextField phoneCreateField;
	private JButton createButton;

	private JTextField idDeleteField;
	private JButton deleteButton;

	private DefaultListModel listModel;

	private JLabel ageLabel;
	private JLabel nameLabel;
	private JLabel phoneLabel;
	private JLabel deleteLabel;

	private JPanel createPanel;
	private JPanel deletePanel;

	private List<Candidate> lastResult = new ArrayList<Candidate>();

	private static final String[] COUNTRIES_RU = { "Любая", "Беларусь",
			"Россия" };

	private static final String[][] CITIES_RU = { { "Любой" },
			{ "Любой", "Минск", "Брест" },
			{ "Любой", "Москва", "Санкт-Петербург" } };

	private static String[] countries = { "Любая", "Беларусь", "Россия" };

	private static String[][] cities = { { "Любой" },
			{ "Любой", "Минск", "Брест" },
			{ "Любой", "Москва", "Санкт-Петербург" } };

	private static HashMap<String, String> placeMap;
	static {
		placeMap = new HashMap<String, String>();
		placeMap.put("Любая", "country.any");
		placeMap.put("Беларусь", "country.belarus");
		placeMap.put("Россия", "country.russia");
		placeMap.put("Любой", "city.any");
		placeMap.put("Минск", "city.minsk");
		placeMap.put("Брест", "city.brest");
		placeMap.put("Москва", "city.moscow");
		placeMap.put("Санкт-Петербург", "city.sanct-petersburg");
	}

	private String host;
	private String port;

	public ClientFrame1(String host, String port) {
		this.host = host;
		this.port = port;
		clientFrameBundle = ClientFrameBundle.getInstance();
		initComponents();
		initListeners();
		initProperties();
		try {
			refreshUILanguage();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(new BorderLayout());

		selectParamsPanel = new JPanel();
		selectParamsPanel.setLayout(new GridLayout(2, 5));
		selectParamsPanelTitledBorder = new TitledBorder(new EtchedBorder(),
				"Введите параметры поиска: ");
		selectParamsPanel.setBorder(selectParamsPanelTitledBorder);

		ButtonGroup sexGroup = new ButtonGroup();
		maleButton = new JRadioButton("Парень");
		maleButton.setSelected(true);
		sexGroup.add(maleButton);
		femaleButton = new JRadioButton("Девушка");
		sexGroup.add(femaleButton);

		fromAgeLabel = new JLabel("Возраст: от ");
		minAgeField = new JTextField("18");
		toAgeLabel = new JLabel(" до ");
		maxAgeField = new JTextField("35");

		countryBox = new JComboBox(countries);
		cityBox = new JComboBox(cities[0]);

		findButton = new JButton("Поиск");

		selectParamsPanel.add(maleButton);
		selectParamsPanel.add(femaleButton);
		selectParamsPanel.add(countryBox);
		selectParamsPanel.add(cityBox);
		selectParamsPanel.add(new JPanel());
		selectParamsPanel.add(fromAgeLabel);
		selectParamsPanel.add(minAgeField);
		selectParamsPanel.add(toAgeLabel);
		selectParamsPanel.add(maxAgeField);
		selectParamsPanel.add(findButton);

		selectPanel.add(selectParamsPanel, BorderLayout.NORTH);

		outputPanel = new JPanel();
		outputPanel.setLayout(new BorderLayout());
		outputPanelTitledBorder = new TitledBorder(new EtchedBorder(),
				"Результаты поиска: ");
		outputPanel.setBorder(outputPanelTitledBorder);

		listModel = new DefaultListModel();
		JList resultList = new JList(listModel);
		resultList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane centerPane = new JScrollPane(resultList);
		outputPanel.add(centerPane, BorderLayout.CENTER);

		selectPanel.add(outputPanel, BorderLayout.CENTER);
		// ///////////////////////////
		footerPanel = new JPanel();
		footerPanel.setLayout(new GridLayout(3, 1));

		createPanel = new JPanel();
		createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.X_AXIS));
		createPanelTitledBorder = new TitledBorder(new EtchedBorder(),
				"Введите параметры для создания новой записи: ");
		createPanel.setBorder(createPanelTitledBorder);

		ButtonGroup sexCreateGroup = new ButtonGroup();
		maleCreateButton = new JRadioButton("Парень");
		maleCreateButton.setSelected(true);
		sexCreateGroup.add(maleCreateButton);
		femaleCreateButton = new JRadioButton("Девушка");
		sexCreateGroup.add(femaleCreateButton);

		ageLabel = new JLabel("Возраст: ");
		ageCreateField = new JTextField("21");

		nameLabel = new JLabel("Имя: ");
		nameCreateField = new JTextField();

		countryCreateBox = new JComboBox(countries);
		cityCreateBox = new JComboBox(cities[0]);

		phoneLabel = new JLabel("Номер телефона: ");
		phoneCreateField = new JTextField();

		createButton = new JButton("Создать");

		createPanel.add(maleCreateButton);
		createPanel.add(femaleCreateButton);
		createPanel.add(ageLabel);
		createPanel.add(ageCreateField);
		createPanel.add(nameLabel);
		createPanel.add(nameCreateField);
		createPanel.add(countryCreateBox);
		createPanel.add(cityCreateBox);
		createPanel.add(phoneLabel);
		createPanel.add(phoneCreateField);
		createPanel.add(createButton);

		deletePanel = new JPanel();
		deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.X_AXIS));
		deletePanelTitledBorder = new TitledBorder(new EtchedBorder(),
				"Введите идентификатор для удаления ненужной записи: ");
		deletePanel.setBorder(deletePanelTitledBorder);
		deleteLabel = new JLabel("Удалить по id: ");
		idDeleteField = new JTextField();
		deleteButton = new JButton("Удалить");

		deletePanel.add(deleteLabel);
		deletePanel.add(idDeleteField);
		deletePanel.add(deleteButton);

		// ////
		JPanel langPanel = new JPanel();
		langPanel.setLayout(new FlowLayout());
		langPanelTitledBorder = new TitledBorder(new EtchedBorder(),
				"Выберите язык интерфейса: ");
		langPanel.setBorder(langPanelTitledBorder);

		russianButton = new JButton("Русский");
		englishButton = new JButton("English");

		langPanel.add(russianButton);
		langPanel.add(englishButton);

		footerPanel.add(createPanel);
		footerPanel.add(deletePanel);
		footerPanel.add(langPanel);

		selectPanel.add(footerPanel, BorderLayout.SOUTH);

		add(selectPanel, BorderLayout.CENTER);

	}

	private void initListeners() {
		ButtonListener l1 = new ButtonListener();
		findButton.addActionListener(l1);
		createButton.addActionListener(l1);
		deleteButton.addActionListener(l1);
		russianButton.addActionListener(l1);
		englishButton.addActionListener(l1);
		ActionListener l2 = new CountryBoxChangeListener();
		countryBox.addActionListener(l2);
		countryCreateBox.addActionListener(l2);
	}

	private void initProperties() {
		setSize(800, 600);
		setVisible(true);
		setTitle("Поиск людей по заданным критериям");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void selectCandidates() throws RemoteException,
			MalformedURLException, NotBoundException {
		IMeetingDAO meetingDAO = (IMeetingDAO) Naming.lookup("rmi://" + host
				+ ":" + port + "/server.meetingDAO");
		boolean male = true;
		if (femaleButton.isSelected())
			male = false;
		int minAge = Integer.parseInt(minAgeField.getText());
		int maxAge = Integer.parseInt(maxAgeField.getText());
		String country = COUNTRIES_RU[countryBox.getSelectedIndex()];
		if (country.equals(COUNTRIES_RU[0]))
			country = null;
		String city = CITIES_RU[countryBox.getSelectedIndex()][cityBox
				.getSelectedIndex()];
		if (city.equals(CITIES_RU[0][0]))
			city = null;
		setResultListModel(meetingDAO.selectCandidates(male, minAge, maxAge,
				country, city));
	}

	// /////////////////////////

	private void insertCandidate() throws RemoteException, NotBoundException,
			MalformedURLException {
		/* Registry registry = LocateRegistry.getRegistry(host); */
		IMeetingDAO meetingDAO = (IMeetingDAO) Naming.lookup("rmi://" + host
				+ ":" + port + "/server.meetingDAO");
		boolean male = true;
		if (femaleCreateButton.isSelected())
			male = false;
		String name = nameCreateField.getText();
		int age = Integer.parseInt(ageCreateField.getText());
		String country = COUNTRIES_RU[countryCreateBox.getSelectedIndex()];
		if (country.equals(COUNTRIES_RU[0]))
			country = null;
		String city = CITIES_RU[countryCreateBox.getSelectedIndex()][cityCreateBox
				.getSelectedIndex()];
		if (city.equals(CITIES_RU[0][0]))
			city = null;
		String phone = phoneCreateField.getText();
		meetingDAO.insertCandidate(male, name, age, country, city, phone);
	}

	private void deleteCandidate() throws RemoteException, NotBoundException,
			MalformedURLException {
		/* Registry registry = LocateRegistry.getRegistry(host); */
		IMeetingDAO meetingDAO = (IMeetingDAO) Naming.lookup("rmi://" + host
				+ ":" + port + "/server.meetingDAO");
		int id = Integer.parseInt(idDeleteField.getText());
		meetingDAO.deleteCandidate(id);
	}

	// ////////////////////////

	private class CountryBoxChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox comboBox = (JComboBox) e.getSource();
			int number = comboBox.getSelectedIndex();
			DefaultComboBoxModel dm = new DefaultComboBoxModel(cities[number]);
			if (comboBox.equals(countryBox)) {
				cityBox.setModel(dm);
			} else if (comboBox.equals(countryCreateBox)) {
				cityCreateBox.setModel(dm);
			}
		}

	}

	private void showParamsIncorrectErrorMessage() throws HeadlessException,
			UnsupportedEncodingException {
		JOptionPane
				.showMessageDialog(this, clientFrameBundle
						.getString("showParamsIncorrectErrorMessage"),
						clientFrameBundle.getString("error"),
						JOptionPane.ERROR_MESSAGE);
	}

	private boolean isParamsCreateCorrect() {
		return ageCreateField.getText().matches("[1-9][0-9]?")
				&& phoneCreateField.getText().matches("[0-9]{1,15}")
				&& !nameCreateField.getText().equals("");
	}

	private boolean isParamsDeleteCorrect() {
		return idDeleteField.getText().matches("[1-9][0-9]{0,6}");
	}

	private void showSaxErrorMessage() throws HeadlessException,
			UnsupportedEncodingException {
		JOptionPane
				.showMessageDialog(this,
						clientFrameBundle.getString("showSaxErrorMessage"),
						clientFrameBundle.getString("error"),
						JOptionPane.ERROR_MESSAGE);
	}

	private boolean isParamsSelectCorrect() {
		return minAgeField.getText().matches("[1-9][0-9]?")
				&& maxAgeField.getText().matches("[1-9][0-9]?");
	}

	private void setResultListModel(List<Candidate> list) {
		listModel.clear();
		for (Candidate i : list)
			try {
				listModel.addElement(getCandidateInformation(i));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	}

	private String getCandidateInformation(Candidate i)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(clientFrameBundle.getString("name") + i.getName());
		sb.append(clientFrameBundle.getString("age") + i.getAge());
		sb.append(clientFrameBundle.getString("phone") + i.getPhone());
		sb.append(i.isMale() ? clientFrameBundle.getString("male.guy")
				: clientFrameBundle.getString("male.girl"));
		sb.append(i.getCountry() != null ? clientFrameBundle
				.getString("country")
				+ clientFrameBundle.getString(placeMap.get(i.getCountry()))
				: "");
		sb.append(i.getCity() != null ? clientFrameBundle.getString("city")
				+ clientFrameBundle.getString(placeMap.get(i.getCity())) : "");
		sb.append(", id: " + i.getId());
		return sb.toString();
	}

	private void refreshUILanguage() throws UnsupportedEncodingException {
		countries = new String[] { clientFrameBundle.getString("country.any"),
				clientFrameBundle.getString("country.belarus"),
				clientFrameBundle.getString("country.russia") };
		cities = new String[][] {
				{ clientFrameBundle.getString("city.any") },
				{ clientFrameBundle.getString("city.any"),
						clientFrameBundle.getString("city.minsk"),
						clientFrameBundle.getString("city.brest") },
				{ clientFrameBundle.getString("city.any"),
						clientFrameBundle.getString("city.moscow"),
						clientFrameBundle.getString("city.sanct-petersburg") } };

		selectParamsPanelTitledBorder.setTitle(clientFrameBundle
				.getString("selectParamsPanelTitledBorder"));
		selectParamsPanel.updateUI();

		outputPanelTitledBorder.setTitle(clientFrameBundle
				.getString("outputPanelTitledBorder"));
		outputPanel.updateUI();

		langPanelTitledBorder.setTitle(clientFrameBundle
				.getString("footerPanelTitledBorder"));
		footerPanel.updateUI();

		deletePanelTitledBorder.setTitle(clientFrameBundle
				.getString("deletePanelTitledBorder"));
		deletePanel.updateUI();

		maleButton.setText(clientFrameBundle.getString("maleButton"));
		maleButton.updateUI();

		femaleButton.setText(clientFrameBundle.getString("femaleButton"));
		femaleButton.updateUI();

		fromAgeLabel.setText(clientFrameBundle.getString("fromAgeLabel"));
		fromAgeLabel.updateUI();

		toAgeLabel.setText(clientFrameBundle.getString("toAgeLabel"));
		toAgeLabel.updateUI();

		findButton.setText(clientFrameBundle.getString("findButton"));
		findButton.updateUI();

		DefaultComboBoxModel aModel = new DefaultComboBoxModel(countries);
		countryBox.setModel(aModel);
		countryBox.updateUI();

		DefaultComboBoxModel bModel = new DefaultComboBoxModel(cities[0]);
		cityBox.setModel(bModel);
		// cityBox.updateUI();

		setTitle(clientFrameBundle.getString("title"));

		setResultListModel(lastResult);

		// ///////

		femaleCreateButton.setText(clientFrameBundle
				.getString("femaleCreateButton"));
		femaleCreateButton.updateUI();
		maleCreateButton.setText(clientFrameBundle
				.getString("maleCreateButton"));
		maleCreateButton.updateUI();
		createButton.setText(clientFrameBundle.getString("createButton"));
		createButton.updateUI();
		deleteButton.setText(clientFrameBundle.getString("deleteButton"));
		deleteButton.updateUI();
		ageLabel.setText(clientFrameBundle.getString("ageLabel"));
		ageLabel.updateUI();
		nameLabel.setText(clientFrameBundle.getString("nameLabel"));
		nameLabel.updateUI();
		phoneLabel.setText(clientFrameBundle.getString("phoneLabel"));
		phoneLabel.updateUI();
		deleteLabel.setText(clientFrameBundle.getString("deleteLabel"));
		deleteLabel.updateUI();

		DefaultComboBoxModel cModel = new DefaultComboBoxModel(countries);
		countryCreateBox.setModel(cModel);
		// countryCreateBox.updateUI();

		DefaultComboBoxModel dModel = new DefaultComboBoxModel(cities[0]);
		cityCreateBox.setModel(dModel);
		// cityCreateBox.updateUI();

		createPanelTitledBorder.setTitle(clientFrameBundle
				.getString("createPanelTitledBorder"));
		createPanel.updateUI();

	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton but = (JButton) e.getSource();
			if (but.equals(findButton)) {
				if (isParamsSelectCorrect())
					try {
						selectCandidates();
					} catch (IOException e1) {
						try {
							showSaxErrorMessage();
						} catch (HeadlessException e2) {
							e2.printStackTrace();
						} catch (UnsupportedEncodingException e2) {
							e2.printStackTrace();
						}
					} catch (NotBoundException e1) {
						try {
							showSaxErrorMessage();
						} catch (HeadlessException e2) {
							e2.printStackTrace();
						} catch (UnsupportedEncodingException e2) {
							e2.printStackTrace();
						}
					}
				else {
					try {
						showParamsIncorrectErrorMessage();
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
			} else if (but.equals(createButton)) {
				if (isParamsCreateCorrect())
					try {
						insertCandidate();
					} catch (IOException e1) {
						try {
							showSaxErrorMessage();
						} catch (HeadlessException e2) {
							e2.printStackTrace();
						} catch (UnsupportedEncodingException e2) {
							e2.printStackTrace();
						}
					} catch (NotBoundException e1) {
						try {
							showSaxErrorMessage();
						} catch (HeadlessException e2) {
							e2.printStackTrace();
						} catch (UnsupportedEncodingException e2) {
							e2.printStackTrace();
						}
					}
				else {
					try {
						showParamsIncorrectErrorMessage();
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
			} else if (but.equals(deleteButton)) {
				if (isParamsDeleteCorrect())
					try {
						deleteCandidate();
					} catch (IOException e1) {
						try {
							showSaxErrorMessage();
						} catch (HeadlessException e2) {
							e2.printStackTrace();
						} catch (UnsupportedEncodingException e2) {
							e2.printStackTrace();
						}
					} catch (NotBoundException e1) {
						try {
							showSaxErrorMessage();
						} catch (HeadlessException e2) {
							e2.printStackTrace();
						} catch (UnsupportedEncodingException e2) {
							e2.printStackTrace();
						}
					}
				else {
					try {
						showParamsIncorrectErrorMessage();
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
			} else if (but.equals(russianButton)) {
				clientFrameBundle.setLocale(new Locale("ru"));
				try {
					refreshUILanguage();
				} catch (UnsupportedEncodingException e1) {
				}
			} else if (but.equals(englishButton)) {
				clientFrameBundle.setLocale(Locale.ENGLISH);
				try {
					refreshUILanguage();
				} catch (UnsupportedEncodingException e1) {
				}
			}
		}

	}
}
