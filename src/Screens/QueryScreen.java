package Screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ListCellRenderer.hourMinute;
import ListCellRenderer.yearMonthDate;

import java.awt.Font;

public class QueryScreen extends JPanel {

	MainFrame mainFrame;
	ImageIcon icon = new ImageIcon("./image/�ڻ� 2.png");

	private static final long serialVersionUID = 1L;

	public QueryScreen(MainFrame frame) {
		mainFrame = frame;
		init();
	}

	public void init() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// TextField ������� ������
		JTextField departureTextField = new JTextField();
		departureTextField.setToolTipText("�����");
		departureTextField.setForeground(Color.DARK_GRAY);
		departureTextField.setFont(new Font("���� ���", Font.PLAIN, 18));
		departureTextField.setBackground(Color.WHITE);
		departureTextField.setBounds(143, 261, 314, 41);
		add(departureTextField);
		departureTextField.setColumns(10);

		JTextField destinationTextField = new JTextField();
		destinationTextField.setToolTipText("������");
		destinationTextField.setForeground(Color.DARK_GRAY);
		destinationTextField.setFont(new Font("���� ���", Font.PLAIN, 18));
		destinationTextField.setBackground(Color.WHITE);
		destinationTextField.setBounds(143, 316, 314, 41);
		add(destinationTextField);
		destinationTextField.setColumns(10);

		// �޺��ڽ� ��߽ð�(��, ��, ��)
		JComboBox<Date> departureDateCbBox = new JComboBox<Date>();
		departureDateCbBox.setFont(new Font("���� ���", Font.PLAIN, 14));
		departureDateCbBox.setBounds(143, 410, 314, 41);
		add(departureDateCbBox);

		GregorianCalendar calendar = new GregorianCalendar();
		departureDateCbBox.addItem(calendar.getTime());

		for (int i = 0; i < 31; i++) { // �޺��ڽ� �ȿ� ������ �߰�
			calendar.roll(GregorianCalendar.DAY_OF_MONTH, 1);
			if (calendar.get(Calendar.DATE) == 1) {
				calendar.roll(GregorianCalendar.MONTH, 1);
			}
			departureDateCbBox.addItem(calendar.getTime());
		}
		departureDateCbBox.setRenderer(new yearMonthDate()); // �̶� ListCellRenderer.yearMonthData.java���·� ����ڴٴ� ��

		// �޺��ڽ� ��߽ð�(�ð�, ��)
		JComboBox<Date> departureTimeCbBox = new JComboBox<Date>();
		departureTimeCbBox.setFont(new Font("���� ���", Font.PLAIN, 14));
		departureTimeCbBox.setBounds(143, 461, 204, 47);
		add(departureTimeCbBox);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		departureTimeCbBox.addItem(calendar.getTime());
		for (int i = 0; i < 23; i++) {
			calendar.roll(GregorianCalendar.HOUR_OF_DAY, 1);
			departureTimeCbBox.addItem(calendar.getTime());
		}
		departureTimeCbBox.setRenderer(new hourMinute()); // �̶� ListCellRenderer.hourMinute.java���·� ����ڴٴ� ��

		// ��ۼ��� �޺��ڽ�
		String[] txt = { "��ü", "����", "����" };
		JComboBox<?> vehicleCbBox = new JComboBox<String>(txt);
		vehicleCbBox.setForeground(Color.DARK_GRAY);
		vehicleCbBox.setFont(new Font("���� ���", Font.PLAIN, 18));
		vehicleCbBox.setBounds(143, 562, 204, 47);
		add(vehicleCbBox);

		// �˻���ư ������ ����ȭ���� DirectScreenȭ������ �Ѿ��.
		JButton goNextButton = new JButton("�˻�");
		goNextButton.setBorderPainted(false);
		goNextButton.setForeground(Color.DARK_GRAY);
		goNextButton.setFont(new Font("���� ���", Font.BOLD, 32));
		goNextButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("�˻�")) {
					// ����ȭ������ �Ѿ���� ��¥ ������ ��ó��
					Date date1 = (Date) departureDateCbBox.getSelectedItem();
					Date date2 = (Date) departureTimeCbBox.getSelectedItem();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date1);
					cal.set(Calendar.HOUR_OF_DAY, date2.getHours());
					cal.set(Calendar.MINUTE, date2.getMinutes());
					cal.set(Calendar.MILLISECOND, 0);

					// DirectScreenȭ�鿡 �����͸� �����ϰ� ����
					mainFrame.getContentPane().add("DirectScreen",
							new DirectScreen(mainFrame, departureTextField.getText(), destinationTextField.getText(),
									cal.getTime(), vehicleCbBox.getSelectedItem().toString()));

					// DirectScreen�� �ε��� �����Ͱ� �ִٸ� -> DirectScreen
					if (DirectScreen.check) {
						mainFrame.changeSize(1200, 900);
						mainFrame.getCardLayout().show(mainFrame.getContentPane(), "DirectScreen");
					}
					// DirectScreen�� �ε��� �����Ͱ� ������ -> ȯ�³뼱 ã�� ���� ���
					else {
						int option = JOptionPane.showConfirmDialog(null,
								"����뼱�� �����ϴ�! \n�ٽ� �˻��Ϸ��� '�ƴϿ�' \nȯ�³뼱�� ã������ '��'�� ��������.", "����",
								JOptionPane.YES_NO_OPTION);
						if (option == 0) { // ����� YES
							mainFrame.changeSize(1182, 861);
							mainFrame.getContentPane().add("TransferScreen",
									new TransferScreen(mainFrame, departureTextField.getText(),
											destinationTextField.getText(), cal.getTime(),
											vehicleCbBox.getSelectedItem().toString()));
							mainFrame.getCardLayout().show(mainFrame.getContentPane(), "TransferScreen");
						}
					}
				}
			}
		});
		goNextButton.setBounds(100, 659, 350, 116);
		add(goNextButton);

		// �ڷ� ��ư
		JButton goBackBtn = new JButton("�ڷ�");
		goBackBtn.setBackground(new Color(102, 0, 0));
		goBackBtn.setForeground(Color.WHITE);
		goBackBtn.setFont(new Font("12�Ե���Ʈ�ູLight", Font.PLAIN, 12));
		goBackBtn.setBounds(0, 0, 98, 27);
		add(goBackBtn);
		goBackBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("�ڷ�")) {
					mainFrame.getCardLayout().show(mainFrame.getContentPane(), "InitialScreen");
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