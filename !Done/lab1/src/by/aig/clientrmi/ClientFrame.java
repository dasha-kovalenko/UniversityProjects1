package by.aig.clientrmi;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

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

/**
 * Класс <code>ClientFrame</code> предоставляет графическую оболочку для
 * манипулирования объектами удаленной базы данных.
 * 
 * @author <i>Андреюк Илья, 3 курс, 1 группа</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class ClientFrame extends JFrame {

	private String host;
	private String port;

	private JRadioButton femaleButton;
	private JRadioButton maleButton;
	private JTextField minAgeField;
	private JTextField maxAgeField;
	private JComboBox countryBox;
	private JComboBox cityBox;
	private JButton findButton;

	private JRadioButton femaleCreateButton;
	private JRadioButton maleCreateButton;
	private JTextField ageCreateField;
	private JTextField nameCreateField;
	private JComboBox countryCreateBox;
	private JComboBox cityCreateBox;
	private JTextField phoneCreateField;
	private JButton createButton;

	private JTextField idUpdateField;
	private JButton idUpdateButton;
	private Candidate updateCandidate;
	private JRadioButton femaleUpdateButton;
	private JRadioButton maleUpdateButton;
	private JTextField ageUpdateField;
	private JTextField nameUpdateField;
	private JComboBox countryUpdateBox;
	private JComboBox cityUpdateBox;
	private JTextField phoneUpdateField;
	private JButton updateButton;
	private JPanel secondUpdatePanel;
	private JPanel updatePanel;

	private JTextField idDeleteField;
	private JButton deleteButton;

	private DefaultListModel listModel;

	private static final String[] COUNTRIES = { "Любая", "Беларусь", "Россия" };

	private static final String[][] CITIES = { { "Любой" },
			{ "Любой", "Минск", "Брест" },
			{ "Любой", "Москва", "Санкт-Петербург" } };

	private static final HashMap<String, Integer> placeMap;

	static {
		placeMap = new HashMap<String, Integer>();
		placeMap.put("Беларусь", 1);
		placeMap.put("Россия", 2);
		placeMap.put("Минск", 1);
		placeMap.put("Брест", 2);
		placeMap.put("Москва", 1);
		placeMap.put("Санкт-Петербург", 2);
	}

	public ClientFrame(String host, String port) {
		this.host = host;
		this.port = port;
		initComponents();
		initListeners();
		initProperties();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(new BorderLayout());

		JPanel selectParamsPanel = new JPanel();
		selectParamsPanel.setLayout(new GridLayout(2, 5));
		selectParamsPanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Введите параметры поиска: "));

		ButtonGroup sexGroup = new ButtonGroup();
		maleButton = new JRadioButton("Парень");
		maleButton.setSelected(true);
		sexGroup.add(maleButton);
		femaleButton = new JRadioButton("Девушка");
		sexGroup.add(femaleButton);

		JLabel fromAgeLabel = new JLabel("Возраст: от ");
		minAgeField = new JTextField("18");
		JLabel toAgeLabel = new JLabel(" до ");
		maxAgeField = new JTextField("35");

		countryBox = new JComboBox(COUNTRIES);
		cityBox = new JComboBox(CITIES[0]);

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

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(new BorderLayout());
		outputPanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Результаты поиска: "));

		listModel = new DefaultListModel();
		JList resultList = new JList(listModel);
		resultList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane centerPane = new JScrollPane(resultList);
		outputPanel.add(centerPane, BorderLayout.CENTER);

		selectPanel.add(outputPanel, BorderLayout.CENTER);

		////////////////////////////////////////////////
		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new GridLayout(2, 1));

		JPanel createPanel = new JPanel();
		createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.X_AXIS));
		createPanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Введите параметры для создания новой записи: "));

		ButtonGroup sexCreateGroup = new ButtonGroup();
		maleCreateButton = new JRadioButton("Парень");
		maleCreateButton.setSelected(true);
		sexCreateGroup.add(maleCreateButton);
		femaleCreateButton = new JRadioButton("Девушка");
		sexCreateGroup.add(femaleCreateButton);

		JLabel ageLabel = new JLabel("Возраст: ");
		ageCreateField = new JTextField("21");

		JLabel nameLabel = new JLabel("Имя: ");
		nameCreateField = new JTextField();

		countryCreateBox = new JComboBox(COUNTRIES);
		cityCreateBox = new JComboBox(CITIES[0]);

		JLabel phoneLabel = new JLabel("Номер телефона: ");
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

		JPanel deletePanel = new JPanel();
		deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.X_AXIS));
		deletePanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Введите идентификатор для удаления ненужной записи: "));
		JLabel deleteLabel = new JLabel("Удалить по id: ");
		idDeleteField = new JTextField();
		deleteButton = new JButton("Удалить");

		deletePanel.add(deleteLabel);
		deletePanel.add(idDeleteField);
		deletePanel.add(deleteButton);

		footerPanel.add(createPanel);
		footerPanel.add(deletePanel);

		selectPanel.add(footerPanel, BorderLayout.SOUTH);

		updatePanel = new JPanel();
		updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
		updatePanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Панель обновления: "));

		JPanel firstUpdatePanel = new JPanel();
		firstUpdatePanel.setLayout(new BoxLayout(firstUpdatePanel,
				BoxLayout.Y_AXIS));
		firstUpdatePanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Введите id: "));
		idUpdateField = new JTextField();
		idUpdateButton = new JButton("Получить данные");

		firstUpdatePanel.add(idUpdateField);
		firstUpdatePanel.add(idUpdateButton);

		secondUpdatePanel = new JPanel();
		secondUpdatePanel.setLayout(new BoxLayout(secondUpdatePanel,
				BoxLayout.Y_AXIS));
		secondUpdatePanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Введите параметры для обновления: "));

		ButtonGroup sexUpdateGroup = new ButtonGroup();
		maleUpdateButton = new JRadioButton("Парень");
		maleUpdateButton.setSelected(true);
		sexUpdateGroup.add(maleUpdateButton);
		femaleUpdateButton = new JRadioButton("Девушка");
		sexUpdateGroup.add(femaleUpdateButton);

		JLabel ageUpdateLabel = new JLabel("Возраст: ");
		ageUpdateField = new JTextField("21");

		JLabel nameUpdateLabel = new JLabel("Имя: ");
		nameUpdateField = new JTextField();

		countryUpdateBox = new JComboBox(COUNTRIES);
		cityUpdateBox = new JComboBox(CITIES[0]);

		JLabel phoneUpdateLabel = new JLabel("Номер телефона: ");
		phoneUpdateField = new JTextField();

		updateButton = new JButton("Обновить");

		secondUpdatePanel.add(maleUpdateButton);
		secondUpdatePanel.add(femaleUpdateButton);
		secondUpdatePanel.add(ageUpdateLabel);
		secondUpdatePanel.add(ageUpdateField);
		secondUpdatePanel.add(nameUpdateLabel);
		secondUpdatePanel.add(nameUpdateField);
		secondUpdatePanel.add(countryUpdateBox);
		secondUpdatePanel.add(cityUpdateBox);
		secondUpdatePanel.add(phoneUpdateLabel);
		secondUpdatePanel.add(phoneUpdateField);
		secondUpdatePanel.add(updateButton);

		updatePanel.add(firstUpdatePanel);
		// updatePanel.add(secondUpdatePanel);

		selectPanel.add(updatePanel, BorderLayout.EAST);
		add(selectPanel, BorderLayout.CENTER);
	}

	private void initListeners() {
		findButton.addActionListener(new ButtonListener());
		createButton.addActionListener(new ButtonListener());
		deleteButton.addActionListener(new ButtonListener());
		idUpdateButton.addActionListener(new ButtonListener());
		updateButton.addActionListener(new ButtonListener());
		countryBox.addActionListener(new CountyBoxChangeListener());
		countryCreateBox.addActionListener(new CountyBoxChangeListener());
		countryUpdateBox.addActionListener(new CountyBoxChangeListener());
	}

	private void initProperties() {
		setSize(960, 760);
		setVisible(true);
		setTitle("Поиск людей по заданным критериям");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void selectCandidates() throws RemoteException, NotBoundException, MalformedURLException {
		/*Registry registry = LocateRegistry
				.getRegistry();*/
		IMeetingDAO meetingDAO = (IMeetingDAO) Naming
				.lookup("rmi://" + host + ":" + port+"/server.meetingDAO");
		boolean male = true;
		if (femaleButton.isSelected())
			male = false;
		int minAge = Integer.parseInt(minAgeField.getText());
		int maxAge = Integer.parseInt(maxAgeField.getText());
		String country = countryBox.getSelectedItem().toString();
		if (country.equals(COUNTRIES[0]))
			country = null;
		String city = cityBox.getSelectedItem().toString();
		if (city.equals(CITIES[0][0]))
			city = null;
		setResultListModel(meetingDAO.selectCandidates(male, minAge, maxAge,
				country, city));
	}

	private void insertCandidate() throws RemoteException, NotBoundException, MalformedURLException {
		/*Registry registry = LocateRegistry.getRegistry(host);*/
		IMeetingDAO meetingDAO = (IMeetingDAO) Naming
				.lookup("rmi://" + host + ":" + port+"/server.meetingDAO");
		boolean male = true;
		if (femaleCreateButton.isSelected())
			male = false;
		String name = nameCreateField.getText();
		int age = Integer.parseInt(ageCreateField.getText());
		String country = countryCreateBox.getSelectedItem().toString();
		if (country.equals(COUNTRIES[0]))
			country = null;
		String city = cityCreateBox.getSelectedItem().toString();
		if (city.equals(CITIES[0][0]))
			city = null;
		String phone = phoneCreateField.getText();
		meetingDAO.insertCandidate(male, name, age, country, city, phone);
	}

	private void deleteCandidate() throws RemoteException, NotBoundException, MalformedURLException {
		/*Registry registry = LocateRegistry.getRegistry(host);*/
		IMeetingDAO meetingDAO = (IMeetingDAO) Naming
				.lookup("rmi://" + host + ":" + port+"/server.meetingDAO");
		int id = Integer.parseInt(idDeleteField.getText());
		meetingDAO.deleteCandidate(id);
	}

	public void updateCandidate() throws RemoteException, NotBoundException, MalformedURLException {
		/*Registry registry = LocateRegistry.getRegistry(host);*/
		IMeetingDAO meetingDAO = (IMeetingDAO) Naming
				.lookup("rmi://" + host + ":" + port+"/server.meetingDAO");
		boolean male = true;
		if (femaleUpdateButton.isSelected())
			male = false;
		String name = nameUpdateField.getText();
		int age = Integer.parseInt(ageUpdateField.getText());
		String country = countryUpdateBox.getSelectedItem().toString();
		if (country.equals(COUNTRIES[0]))
			country = null;
		String city = cityUpdateBox.getSelectedItem().toString();
		if (city.equals(CITIES[0][0]))
			city = null;
		String phone = phoneUpdateField.getText();
		updateCandidate.setAge(age);
		updateCandidate.setMale(male);
		updateCandidate.setName(name);
		updateCandidate.setCity(city);
		updateCandidate.setCountry(country);
		updateCandidate.setPhone(phone);
		meetingDAO.updateCandidate(updateCandidate);
		updatePanel.remove(secondUpdatePanel);
		updatePanel.updateUI();
	}

	public void getCandidate() throws AccessException, RemoteException,
			NotBoundException, MalformedURLException {
		updatePanel.remove(secondUpdatePanel);
		updatePanel.updateUI();
		/*Registry registry = LocateRegistry.getRegistry(host);*/
		IMeetingDAO meetingDAO = (IMeetingDAO) Naming
				.lookup("rmi://" + host + ":" + port+"/server.meetingDAO");
		int id = Integer.parseInt(idUpdateField.getText());
		updateCandidate = meetingDAO.getCandidate(id);
		if (updateCandidate != null) {
			updatePanel.add(secondUpdatePanel);
			updateSecondUpdatePanel();
			updatePanel.updateUI();
		}
	}

	private void updateSecondUpdatePanel() {
		if (updateCandidate.isMale()) {
			maleUpdateButton.setSelected(true);
			maleUpdateButton.updateUI();
		} else {
			femaleUpdateButton.setSelected(true);
			femaleUpdateButton.updateUI();
		}
		ageUpdateField.setText(Integer.toString(updateCandidate.getAge()));
		ageUpdateField.updateUI();
		nameUpdateField.setText(updateCandidate.getName());
		nameUpdateField.updateUI();
		phoneUpdateField.setText(updateCandidate.getPhone());
		phoneUpdateField.updateUI();
		if (updateCandidate.getCountry() == null) {
			countryUpdateBox.setSelectedIndex(0);
		} else {
			countryUpdateBox.setSelectedIndex(placeMap.get(updateCandidate
					.getCountry()));
		}
		countryUpdateBox.updateUI();
		if (updateCandidate.getCity() == null) {
			cityUpdateBox.setSelectedIndex(0);
		} else {
			cityUpdateBox.setSelectedIndex(placeMap.get(updateCandidate
					.getCity()));
		}
		cityUpdateBox.updateUI();
		secondUpdatePanel.updateUI();
	}

	private class CountyBoxChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox comboBox = (JComboBox) e.getSource();
			int number = comboBox.getSelectedIndex();
			DefaultComboBoxModel dm = new DefaultComboBoxModel(CITIES[number]);
			if (comboBox.equals(countryBox)) {
				cityBox.setModel(dm);
			} else if (comboBox.equals(countryCreateBox)) {
				cityCreateBox.setModel(dm);
			} else if (comboBox.equals(countryUpdateBox)) {
				cityUpdateBox.setModel(dm);
			}
		}

	}

	private void showServerErrorMessage() {
		JOptionPane.showMessageDialog(this, "Проблемы соединения с сервером",
				"Ошибка", JOptionPane.ERROR_MESSAGE);
	}

	private void showParamsIncorrectErrorMessage() {
		JOptionPane.showMessageDialog(this, "Некорректные данные", "Ошибка",
				JOptionPane.ERROR_MESSAGE);
	}

	private boolean isParamsSelectCorrect() {
		return minAgeField.getText().matches("[1-9][0-9]?")
				&& maxAgeField.getText().matches("[1-9][0-9]?");
	}

	private boolean isParamsCreateCorrect() {
		return ageCreateField.getText().matches("[1-9][0-9]?")
				&& phoneCreateField.getText().matches("[0-9]{1,15}")
				&& !nameCreateField.getText().equals("");
	}

	private boolean isParamsDeleteCorrect() {
		return idDeleteField.getText().matches("[1-9][0-9]{0,6}");
	}

	public boolean isIdUpdateCorrect() {
		return idUpdateField.getText().matches("[1-9][0-9]{0,6}");
	}

	public boolean isParamsUpdateCorrect() {
		return ageUpdateField.getText().matches("[1-9][0-9]?")
				&& phoneUpdateField.getText().matches("[0-9]{1,15}")
				&& !nameUpdateField.getText().equals("");
	}

	private void setResultListModel(List<Candidate> list) {
		listModel.clear();
		for (Candidate i : list)
			listModel.addElement(i);
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton but = (JButton) e.getSource();
			if (but.equals(findButton)) {
				try {
					if (isParamsSelectCorrect())
						selectCandidates();
					else
						showParamsIncorrectErrorMessage();
				} catch (NotBoundException e1) {
					showServerErrorMessage();
				} catch (RemoteException e1) {
					showServerErrorMessage();
				} catch (MalformedURLException e1) {
					showServerErrorMessage();
				}
			} else if (but.equals(createButton)) {
				try {
					if (isParamsCreateCorrect())
						insertCandidate();
					else
						showParamsIncorrectErrorMessage();
				} catch (NotBoundException e1) {
					showServerErrorMessage();
				} catch (RemoteException e1) {
					showServerErrorMessage();
				} catch (MalformedURLException e1) {
					showServerErrorMessage();
				}
			} else if (but.equals(deleteButton)) {
				try {
					if (isParamsDeleteCorrect())
						deleteCandidate();
					else
						showParamsIncorrectErrorMessage();
				} catch (NotBoundException e1) {
					showServerErrorMessage();
				} catch (RemoteException e1) {
					showServerErrorMessage();
				} catch (MalformedURLException e1) {
					showServerErrorMessage();
				}
			} else if (but.equals(idUpdateButton)) {
				try {
					if (isIdUpdateCorrect()) {
						getCandidate();
					} else
						showParamsIncorrectErrorMessage();
				} catch (NotBoundException e1) {
					showServerErrorMessage();
				} catch (RemoteException e1) {
					showServerErrorMessage();
				} catch (MalformedURLException e1) {
					showServerErrorMessage();
				}
			} else if (but.equals(updateButton)) {
				try {
					if (isParamsUpdateCorrect())
						updateCandidate();
					else
						showParamsIncorrectErrorMessage();
				} catch (NotBoundException e1) {
					showServerErrorMessage();
				} catch (RemoteException e1) {
					showServerErrorMessage();
				} catch (MalformedURLException e1) {
					showServerErrorMessage();
				}
			}
		}

	}

}
