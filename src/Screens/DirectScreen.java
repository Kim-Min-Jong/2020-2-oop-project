package Screens;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BusTrainSystem.scheduleMgr;
import table_demo.TableSelectionDemo;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;

public class DirectScreen extends JPanel {

	MainFrame mainFrame;
	ImageIcon icon = new ImageIcon("./image/�ڻ� 11.png");

	private static final long serialVersionUID = 1L;
	private TableSelectionDemo schedulePane;

	String departure;
	String destination;
	Date departureTime;
	String vehicleStr;
	static boolean check;

	JComboBox<String> busComboBox;
	JComboBox<String> trainComboBox;
	String busType;
	String trainType;

	public DirectScreen(MainFrame frame, String departure, String destination, Date departureTime, String vehicleStr) {
		mainFrame = frame;
		this.departure = departure;
		this.destination = destination;
		this.departureTime = departureTime;
		this.vehicleStr = vehicleStr;
		init();
	}

	public void init() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 50, 850, 45, 180, 15, 0 };
		gbl_contentPane.rowHeights = new int[] { 36, 18, 39, 50, 34, 50, 330, 30, 33, 93, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gbl_contentPane);

		// ����Ʈ â ���ֱ�
		schedulePane = new TableSelectionDemo();

		// �߿� !! ����Ʈ�� ���� ���� �� addComponentsToPane�� ȣ��-> tableController�� init()�� ȣ��
		// init()���� loadData�� ȣ�� -> scheduleMgr�� initDirectData Ȥ�� init TransferData ȣ��
		// load����Ÿ�� ���ϵ� ArrayList�� ��������� init������ false�� ����, addComponentsToPane�� false����

		// check�� �ᱹ �ش�Ǵ� ���� �������� �ִ��� �������� �˷���
		check = schedulePane.addComponentsToPane(scheduleMgr.getInstance(), departure, destination, departureTime,
				vehicleStr, 0);

		// �ڷΰ��� ��ư
		JButton goBackBtn = new JButton("�ڷ�");
		goBackBtn.setForeground(Color.LIGHT_GRAY);
		goBackBtn.setBackground(new Color(128, 0, 0));
		goBackBtn.setFont(new Font("12�Ե���Ʈ�ູLight", Font.PLAIN, 12));
		GridBagConstraints gbc_goBackBtn = new GridBagConstraints();
		gbc_goBackBtn.insets = new Insets(0, 0, 5, 5);
		gbc_goBackBtn.anchor = GridBagConstraints.NORTH;
		gbc_goBackBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_goBackBtn.gridx = 0;
		gbc_goBackBtn.gridy = 0;
		add(goBackBtn, gbc_goBackBtn);
		goBackBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getActionCommand().equals("�ڷ�")) {
					mainFrame.changeSize(550, 900);
					mainFrame.getCardLayout().show(mainFrame.getContentPane(), "QueryScreen");
				}
			}
		});

		// ������ȸ ���̺�
		JLabel directLabel = new JLabel("������ȸ");
		directLabel.setForeground(new Color(255, 99, 71));
		directLabel.setFont(new Font("���� ���", Font.BOLD, 25));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(directLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_schedulePane = new GridBagConstraints();
		gbc_schedulePane.fill = GridBagConstraints.BOTH;
		gbc_schedulePane.insets = new Insets(0, 0, 5, 5);
		gbc_schedulePane.gridheight = 6;
		gbc_schedulePane.gridx = 1;
		gbc_schedulePane.gridy = 3;
		add(schedulePane, gbc_schedulePane);

		// �ּҽð� ��ư
		JButton minTimeBtn = new JButton("�ּҽð�");
		minTimeBtn.setBackground(new Color(255, 127, 80));
		minTimeBtn.setForeground(Color.WHITE);
		minTimeBtn.setFont(new Font("���� ���", Font.BOLD, 20));
		minTimeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().contentEquals("�ּҽð�")) {
					schedulePane.sortComponentsToPane("�ð�");
				}
			}
		});

		// �ּҺ�� ��ư
		JButton minPriceBtn = new JButton("�ּҺ��");
		minPriceBtn.setBackground(new Color(255, 127, 80));
		minPriceBtn.setForeground(Color.WHITE);
		minPriceBtn.setFont(new Font("���� ���", Font.BOLD, 20));
		minPriceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				schedulePane.sortComponentsToPane("����");
			}
		});
		GridBagConstraints gbc_minPriceBtn = new GridBagConstraints();
		gbc_minPriceBtn.fill = GridBagConstraints.BOTH;
		gbc_minPriceBtn.insets = new Insets(0, 0, 5, 5);
		gbc_minPriceBtn.gridx = 3;
		gbc_minPriceBtn.gridy = 3;
		add(minPriceBtn, gbc_minPriceBtn);

		GridBagConstraints gbc_minTimeBtn = new GridBagConstraints();
		gbc_minTimeBtn.fill = GridBagConstraints.BOTH;
		gbc_minTimeBtn.insets = new Insets(0, 0, 5, 5);
		gbc_minTimeBtn.gridx = 3;
		gbc_minTimeBtn.gridy = 5;
		add(minTimeBtn, gbc_minTimeBtn);

		// ���� �޺��ڽ�
		String[] txt = { "��ü", "�Ϲ�", "���" };

		// ���� �޺��ڽ�
		String[] txt2 = { "��ü", "SRT", "KTX", "����ȭȣ" };

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 127, 80));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 6;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 76, 0, 0 };
		gbl_panel.rowHeights = new int[] { 197, 15, 21, 15, 0, 0, 21, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		if (vehicleStr.contentEquals("��ü") || vehicleStr.contentEquals("����")) {
			// ���� ���̺�
			JLabel busLabel = new JLabel("����");
			busLabel.setForeground(Color.WHITE);
			busLabel.setFont(new Font("���� ���", Font.PLAIN, 15));
			GridBagConstraints gbc_busLabel = new GridBagConstraints();
			gbc_busLabel.anchor = GridBagConstraints.NORTH;
			gbc_busLabel.insets = new Insets(0, 0, 5, 5);
			gbc_busLabel.gridx = 1;
			gbc_busLabel.gridy = 1;
			panel.add(busLabel, gbc_busLabel);

			// ���� �޺��ڽ�
			busComboBox = new JComboBox<String>(txt);
			busComboBox.setBackground(new Color(255, 160, 122));
			busComboBox.setFont(new Font("���� ���", Font.PLAIN, 15));
			GridBagConstraints gbc_busComboBox = new GridBagConstraints();
			gbc_busComboBox.anchor = GridBagConstraints.NORTH;
			gbc_busComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_busComboBox.insets = new Insets(0, 0, 5, 5);
			gbc_busComboBox.gridx = 1;
			gbc_busComboBox.gridy = 2;
			panel.add(busComboBox, gbc_busComboBox);
		}
		if (vehicleStr.contentEquals("��ü") || vehicleStr.contentEquals("����")) {
			// ���� ���̺�
			JLabel trainLabel = new JLabel("����");
			trainLabel.setForeground(Color.WHITE);
			trainLabel.setFont(new Font("���� ���", Font.PLAIN, 15));
			GridBagConstraints gbc_trainLabel = new GridBagConstraints();
			gbc_trainLabel.anchor = GridBagConstraints.NORTH;
			gbc_trainLabel.insets = new Insets(0, 0, 5, 5);
			gbc_trainLabel.gridx = 1;
			gbc_trainLabel.gridy = 3;
			panel.add(trainLabel, gbc_trainLabel);

			// ���� �޺��ڽ�
			trainComboBox = new JComboBox<String>(txt2);
			trainComboBox.setBackground(new Color(255, 160, 122));
			trainComboBox.setFont(new Font("���� ���", Font.PLAIN, 15));
			GridBagConstraints gbc_trainComboBox = new GridBagConstraints();
			gbc_trainComboBox.fill = GridBagConstraints.BOTH;
			gbc_trainComboBox.insets = new Insets(0, 0, 5, 5);
			gbc_trainComboBox.gridx = 1;
			gbc_trainComboBox.gridy = 4;
			panel.add(trainComboBox, gbc_trainComboBox);
		}

		// ���ΰ�ħ ��ư
		JButton resetBtn = new JButton("���ΰ�ħ");
		resetBtn.setBackground(new Color(255, 127, 80));
		resetBtn.setForeground(Color.WHITE);
		resetBtn.setFont(new Font("���� ���", Font.PLAIN, 18));
		GridBagConstraints gbc_resetBtn = new GridBagConstraints();
		gbc_resetBtn.fill = GridBagConstraints.BOTH;
		gbc_resetBtn.insets = new Insets(0, 0, 5, 5);
		gbc_resetBtn.gridx = 3;
		gbc_resetBtn.gridy = 7;
		add(resetBtn, gbc_resetBtn);
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getActionCommand().equals("���ΰ�ħ")) {
					if (vehicleStr.equals("��ü")) {
						busType = busComboBox.getSelectedItem().toString();
						trainType = trainComboBox.getSelectedItem().toString();
						schedulePane.updateComponentsToPane(busType, trainType);
					}
					if (vehicleStr.equals("����")) {
						busType = busComboBox.getSelectedItem().toString();
						schedulePane.updateComponentsToPane(busType, null);
					}
					if (vehicleStr.equals("����")) {
						trainType = trainComboBox.getSelectedItem().toString();
						schedulePane.updateComponentsToPane(null, trainType);
					}
				}
			}

		});
	}

	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}
}
