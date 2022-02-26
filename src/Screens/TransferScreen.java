package Screens;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import BusTrainSystem.scheduleMgr;
import table_demo.TableSelectionDemo;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;

public class TransferScreen extends JPanel {

	MainFrame mainFrame;
	ImageIcon icon = new ImageIcon("./image/�ڻ� 11.png");
	ImageIcon back01 = new ImageIcon("./image/back (1).png");
	ImageIcon back02 = new ImageIcon("./image/back (2).png");
	ImageIcon back03 = new ImageIcon("./image/back (3).png");

	String departure;
	String destination;
	Date departureTime;
	String vehicleStr;
	private static final long serialVersionUID = 1L;
	private TableSelectionDemo firstSchedulePane;
	private TableSelectionDemo secondSchedulePane;
	private JComboBox<String> busComboBox_1 = null;
	private JComboBox<String> busComboBox_2 = null;
	private JComboBox<String> trainComboBox_1 = null;
	private JComboBox<String> trainComboBox_2 = null;
	String busType;
	String trainType;
	ArrayList<String> trancferStationList = new ArrayList<String>();
	String[] txt = { "��ü", "�Ϲ�", "���" };
	String[] txt2 = { "��ü", "SRT", "KTX", "����ȭȣ" };

	public void makingSchPane_both(TableSelectionDemo schedulePane, JComboBox<String> busComboBox,
			JComboBox<String> trainComboBox) {
		busType = busComboBox.getSelectedItem().toString();
		trainType = trainComboBox.getSelectedItem().toString();
		schedulePane.updateComponentsToPane(busType, trainType);
	}

	public void makingSchPane_busOnly(TableSelectionDemo schedulePane, JComboBox<String> busComboBox) {
		busType = busComboBox.getSelectedItem().toString();
		schedulePane.updateComponentsToPane(busType, null);
	}

	public void makingSchPane_trainOnly(TableSelectionDemo schedulePane, JComboBox<String> trainComboBox) {
		trainType = trainComboBox.getSelectedItem().toString();
		schedulePane.updateComponentsToPane(null, trainType);
	}

	public TransferScreen(MainFrame frame, String departure, String destination, Date departureTime,
			String vehicleStr) {
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
		gbl_contentPane.columnWidths = new int[] { 70, 100, 420, 300, 100, 0 };
		gbl_contentPane.rowHeights = new int[] { 32, 50, 0, 40, 250, 40, 40, 250, 40, 40, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gbl_contentPane);

		// �ڷΰ��� ��ư
		JButton goBackBtn = new JButton("�ڷ�");
		goBackBtn.setFont(new Font("���� ���", Font.BOLD, 15));
		goBackBtn.setForeground(Color.WHITE);
		goBackBtn.setBackground(new Color(128, 0, 0));
		GridBagConstraints gbc_goBackBtn = new GridBagConstraints();
		gbc_goBackBtn.fill = GridBagConstraints.BOTH;
		gbc_goBackBtn.insets = new Insets(0, 0, 5, 5);
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

		// ȯ����ȸ ���̺�
		JLabel transferLabel = new JLabel("ȯ����ȸ");
		transferLabel.setFont(new Font("���� ���", Font.BOLD, 32));
		transferLabel.setForeground(new Color(255, 69, 0));
		GridBagConstraints gbc_transferLabel = new GridBagConstraints();
		gbc_transferLabel.fill = GridBagConstraints.BOTH;
		gbc_transferLabel.insets = new Insets(0, 0, 5, 5);
		gbc_transferLabel.gridx = 1;
		gbc_transferLabel.gridy = 1;
		add(transferLabel, gbc_transferLabel);

		// ���� ȯ����ȸ �г� (�׺���̼�)
		JPanel naviPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(back03.getImage(), 0, 0, d.width, d.height, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		naviPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_naviPanel = new GridBagConstraints();
		gbc_naviPanel.gridwidth = 2;
		gbc_naviPanel.insets = new Insets(0, 0, 5, 5);
		gbc_naviPanel.fill = GridBagConstraints.BOTH;
		gbc_naviPanel.gridx = 2;
		gbc_naviPanel.gridy = 1;
		add(naviPanel, gbc_naviPanel);

		GridBagLayout gbl_naviPanel = new GridBagLayout();
		gbl_naviPanel.columnWidths = new int[] { 0, 103, 103, 351, 110, 0, 0 };
		gbl_naviPanel.rowHeights = new int[] { 0, 34, 0, 0 };
		gbl_naviPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_naviPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		naviPanel.setLayout(gbl_naviPanel);

		// �ּҺ�� ��ư
		JButton minPriceButton = new JButton("�ּҺ��");
		minPriceButton.setBackground(new Color(255, 69, 0));
		minPriceButton.setForeground(Color.WHITE);
		minPriceButton.setFont(new Font("���� ���", Font.BOLD, 18));
		GridBagConstraints gbc_minPriceButton = new GridBagConstraints();
		gbc_minPriceButton.fill = GridBagConstraints.BOTH;
		gbc_minPriceButton.insets = new Insets(0, 0, 5, 5);
		gbc_minPriceButton.gridx = 1;
		gbc_minPriceButton.gridy = 1;
		naviPanel.add(minPriceButton, gbc_minPriceButton);
		minPriceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				firstSchedulePane.sortComponentsToPane("����");
				secondSchedulePane.sortComponentsToPane("����");
			}
		});

		// �ּҽð� ��ư
		JButton minTimeButton = new JButton("�ּҽð�");
		minTimeButton.setBackground(new Color(255, 69, 0));
		minTimeButton.setForeground(Color.WHITE);
		minTimeButton.setFont(new Font("���� ���", Font.BOLD, 18));
		GridBagConstraints gbc_minTimeButton = new GridBagConstraints();
		gbc_minTimeButton.fill = GridBagConstraints.BOTH;
		gbc_minTimeButton.insets = new Insets(0, 0, 5, 5);
		gbc_minTimeButton.gridx = 2;
		gbc_minTimeButton.gridy = 1;
		naviPanel.add(minTimeButton, gbc_minTimeButton);
		minTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().contentEquals("�ּҽð�")) {
					firstSchedulePane.sortComponentsToPane("�ð�");
					secondSchedulePane.sortComponentsToPane("�ð�");
				}
			}
		});

		// ���ΰ�ħ ��ư
		JButton resetBtn = new JButton("���ΰ�ħ");
		resetBtn.setBackground(new Color(255, 69, 0));
		resetBtn.setForeground(Color.WHITE);
		resetBtn.setFont(new Font("���� ���", Font.BOLD, 18));
		GridBagConstraints gbc_resetBtn = new GridBagConstraints();
		gbc_resetBtn.insets = new Insets(0, 0, 5, 5);
		gbc_resetBtn.fill = GridBagConstraints.BOTH;
		gbc_resetBtn.gridx = 4;
		gbc_resetBtn.gridy = 1;

		naviPanel.add(resetBtn, gbc_resetBtn);
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getActionCommand().equals("���ΰ�ħ")) {
					if (vehicleStr.equals("��ü")) {
						makingSchPane_both(firstSchedulePane, busComboBox_1, trainComboBox_1);
						makingSchPane_both(secondSchedulePane, busComboBox_2, trainComboBox_2);
					}
					if (vehicleStr.equals("����")) {
						makingSchPane_busOnly(firstSchedulePane, busComboBox_1);
						makingSchPane_busOnly(secondSchedulePane, busComboBox_2);
					}
					if (vehicleStr.equals("����")) {
						makingSchPane_trainOnly(firstSchedulePane, trainComboBox_1);
						makingSchPane_trainOnly(secondSchedulePane, trainComboBox_2);
					}
				}
			}

		});

		// ����Ʈ â ���ֱ� (����)
		firstSchedulePane = new TableSelectionDemo();
		firstSchedulePane.addComponentsToPane(scheduleMgr.getInstance(), departure, destination, departureTime,
				vehicleStr, 1);

		// ��� - ȯ�� ��
		JLabel departureTransferLabel = new JLabel("��� - ȯ��");
		departureTransferLabel.setForeground(Color.DARK_GRAY);
		departureTransferLabel.setFont(new Font("���� ���", Font.BOLD, 18));
		GridBagConstraints gbc_DTLabel = new GridBagConstraints();
		gbc_DTLabel.fill = GridBagConstraints.BOTH;
		gbc_DTLabel.insets = new Insets(0, 0, 5, 5);
		gbc_DTLabel.gridx = 1;
		gbc_DTLabel.gridy = 3;
		add(departureTransferLabel, gbc_DTLabel);

		GridBagConstraints gbc_firstSchedulePane = new GridBagConstraints();
		gbc_firstSchedulePane.fill = GridBagConstraints.BOTH;
		gbc_firstSchedulePane.insets = new Insets(0, 0, 5, 5);
		gbc_firstSchedulePane.gridwidth = 3;
		gbc_firstSchedulePane.gridx = 1;
		gbc_firstSchedulePane.gridy = 4;
		add(firstSchedulePane, gbc_firstSchedulePane);

		// �߰���, ������ ���� ���� ���͸� ������ �г�
		JPanel middlePanel1 = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(back02.getImage(), 0, 0, d.width, d.height, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		middlePanel1.setBackground(new Color(255, 127, 80));
		GridBagConstraints gbc_middlePanel = new GridBagConstraints();
		gbc_middlePanel.insets = new Insets(0, 0, 5, 5);
		gbc_middlePanel.fill = GridBagConstraints.BOTH;
		gbc_middlePanel.gridx = 2;
		gbc_middlePanel.gridy = 5;
		add(middlePanel1, gbc_middlePanel);
		GridBagLayout gbl_middlePanel = new GridBagLayout();
		gbl_middlePanel.columnWidths = new int[] { 0, 57, 86, 0, 57, 86, 0, 0 };
		gbl_middlePanel.rowHeights = new int[] { 0, 25, 0, 0 };
		gbl_middlePanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_middlePanel.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		middlePanel1.setLayout(gbl_middlePanel);

		// ���� ���̺�
		JLabel busLabel = new JLabel("����");
		busLabel.setHorizontalAlignment(SwingConstants.CENTER);
		busLabel.setForeground(Color.WHITE);
		busLabel.setFont(new Font("���� ���", Font.PLAIN, 15));
		GridBagConstraints gbc_busLabel = new GridBagConstraints();
		gbc_busLabel.fill = GridBagConstraints.BOTH;
		gbc_busLabel.insets = new Insets(0, 0, 5, 5);
		gbc_busLabel.gridx = 1;
		gbc_busLabel.gridy = 1;
		middlePanel1.add(busLabel, gbc_busLabel);

		// ���� �޺�����
		busComboBox_1 = new JComboBox<String>(txt);
		busComboBox_1.setFont(new Font("���� ���", Font.PLAIN, 12));
		GridBagConstraints gbc_busComboBox_1 = new GridBagConstraints();
		gbc_busComboBox_1.fill = GridBagConstraints.BOTH;
		gbc_busComboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_busComboBox_1.gridx = 2;
		gbc_busComboBox_1.gridy = 1;
		middlePanel1.add(busComboBox_1, gbc_busComboBox_1);

		// ���� ���̺�
		JLabel trainLabel = new JLabel("����");
		trainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trainLabel.setForeground(Color.WHITE);
		trainLabel.setFont(new Font("���� ���", Font.PLAIN, 15));
		GridBagConstraints gbc_trainLabel = new GridBagConstraints();
		gbc_trainLabel.fill = GridBagConstraints.BOTH;
		gbc_trainLabel.insets = new Insets(0, 0, 5, 5);
		gbc_trainLabel.gridx = 4;
		gbc_trainLabel.gridy = 1;
		middlePanel1.add(trainLabel, gbc_trainLabel);

		trainComboBox_1 = new JComboBox<String>(txt2);
		trainComboBox_1.setFont(new Font("���� ���", Font.PLAIN, 12));
		GridBagConstraints gbc_trainComboBox_1 = new GridBagConstraints();
		gbc_trainComboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_trainComboBox_1.fill = GridBagConstraints.BOTH;
		gbc_trainComboBox_1.gridx = 5;
		gbc_trainComboBox_1.gridy = 1;
		middlePanel1.add(trainComboBox_1, gbc_trainComboBox_1);

		// ȯ��-���� ��
		JLabel transferDestinatinoLabel = new JLabel("ȯ�� - ����");
		transferDestinatinoLabel.setForeground(Color.DARK_GRAY);
		transferDestinatinoLabel.setFont(new Font("���� ���", Font.BOLD, 18));
		GridBagConstraints gbc_TDLabel = new GridBagConstraints();
		gbc_TDLabel.fill = GridBagConstraints.BOTH;
		gbc_TDLabel.insets = new Insets(0, 0, 5, 5);
		gbc_TDLabel.gridx = 1;
		gbc_TDLabel.gridy = 6;
		add(transferDestinatinoLabel, gbc_TDLabel);

		// ����Ʈ â ���ֱ� (�Ʒ���)
		secondSchedulePane = new TableSelectionDemo();
		secondSchedulePane.addComponentsToPane(scheduleMgr.getInstance(), departure, destination, departureTime,
				vehicleStr, 2);

		GridBagConstraints gbc_secondSchedulePane = new GridBagConstraints();
		gbc_secondSchedulePane.insets = new Insets(0, 0, 5, 5);
		gbc_secondSchedulePane.fill = GridBagConstraints.BOTH;
		gbc_secondSchedulePane.gridwidth = 3;
		gbc_secondSchedulePane.gridx = 1;
		gbc_secondSchedulePane.gridy = 7;
		add(secondSchedulePane, gbc_secondSchedulePane);

		// �߰� �г�2
		JPanel middlePanel2 = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(back02.getImage(), 0, 0, d.width, d.height, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		middlePanel2.setBackground(new Color(255, 127, 80));
		GridBagConstraints gbc_middlePanel2 = new GridBagConstraints();
		gbc_middlePanel2.insets = new Insets(0, 0, 5, 5);
		gbc_middlePanel2.fill = GridBagConstraints.BOTH;
		gbc_middlePanel2.gridx = 2;
		gbc_middlePanel2.gridy = 8;
		add(middlePanel2, gbc_middlePanel2);
		GridBagLayout gbl_middlePanel2 = new GridBagLayout();
		gbl_middlePanel2.columnWidths = new int[] { 0, 57, 86, 0, 57, 86, 0, 0 };
		gbl_middlePanel2.rowHeights = new int[] { 0, 28, 0, 0 };
		gbl_middlePanel2.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_middlePanel2.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		middlePanel2.setLayout(gbl_middlePanel2);

		// �������̺� �ι�°
		JLabel busLabel2 = new JLabel("����");
		busLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		busLabel2.setForeground(Color.WHITE);
		busLabel2.setFont(new Font("���� ���", Font.PLAIN, 15));
		GridBagConstraints gbc_busLabel2 = new GridBagConstraints();
		gbc_busLabel2.fill = GridBagConstraints.BOTH;
		gbc_busLabel2.insets = new Insets(0, 0, 5, 5);
		gbc_busLabel2.gridx = 1;
		gbc_busLabel2.gridy = 1;
		middlePanel2.add(busLabel2, gbc_busLabel2);

		// ���� �޺��ڽ� �ι�°
		busComboBox_2 = new JComboBox<String>(txt);
		busComboBox_2.setFont(new Font("���� ���", Font.PLAIN, 12));
		GridBagConstraints gbc_busComboBox_2 = new GridBagConstraints();
		gbc_busComboBox_2.fill = GridBagConstraints.BOTH;
		gbc_busComboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_busComboBox_2.gridx = 2;
		gbc_busComboBox_2.gridy = 1;
		middlePanel2.add(busComboBox_2, gbc_busComboBox_2);

		// ���� ���̺� �ι�°
		JLabel trainLabel2 = new JLabel("����");
		trainLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		trainLabel2.setForeground(Color.WHITE);
		trainLabel2.setFont(new Font("���� ���", Font.PLAIN, 15));
		GridBagConstraints gbc_trainLabel2 = new GridBagConstraints();
		gbc_trainLabel2.insets = new Insets(0, 0, 5, 5);
		gbc_trainLabel2.fill = GridBagConstraints.BOTH;
		gbc_trainLabel2.gridx = 4;
		gbc_trainLabel2.gridy = 1;
		middlePanel2.add(trainLabel2, gbc_trainLabel2);

		// ���� �޺��ڽ� �ι�°
		trainComboBox_2 = new JComboBox<String>(txt2);
		GridBagConstraints gbc_trainComboBox_2 = new GridBagConstraints();
		gbc_trainComboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_trainComboBox_2.fill = GridBagConstraints.BOTH;
		gbc_trainComboBox_2.gridx = 5;
		gbc_trainComboBox_2.gridy = 1;
		middlePanel2.add(trainComboBox_2, gbc_trainComboBox_2);
		trainComboBox_2.setFont(new Font("���� ���", Font.PLAIN, 12));
	}

	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
		setOpaque(false);
		super.paintComponent(g);
	};
}
