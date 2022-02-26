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
	ImageIcon icon = new ImageIcon("./image/자산 2.png");

	private static final long serialVersionUID = 1L;

	public QueryScreen(MainFrame frame) {
		mainFrame = frame;
		init();
	}

	public void init() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// TextField 출발지와 도착지
		JTextField departureTextField = new JTextField();
		departureTextField.setToolTipText("출발지");
		departureTextField.setForeground(Color.DARK_GRAY);
		departureTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		departureTextField.setBackground(Color.WHITE);
		departureTextField.setBounds(143, 261, 314, 41);
		add(departureTextField);
		departureTextField.setColumns(10);

		JTextField destinationTextField = new JTextField();
		destinationTextField.setToolTipText("도착지");
		destinationTextField.setForeground(Color.DARK_GRAY);
		destinationTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		destinationTextField.setBackground(Color.WHITE);
		destinationTextField.setBounds(143, 316, 314, 41);
		add(destinationTextField);
		destinationTextField.setColumns(10);

		// 콤보박스 출발시간(년, 월, 일)
		JComboBox<Date> departureDateCbBox = new JComboBox<Date>();
		departureDateCbBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		departureDateCbBox.setBounds(143, 410, 314, 41);
		add(departureDateCbBox);

		GregorianCalendar calendar = new GregorianCalendar();
		departureDateCbBox.addItem(calendar.getTime());

		for (int i = 0; i < 31; i++) { // 콤보박스 안에 데이터 추가
			calendar.roll(GregorianCalendar.DAY_OF_MONTH, 1);
			if (calendar.get(Calendar.DATE) == 1) {
				calendar.roll(GregorianCalendar.MONTH, 1);
			}
			departureDateCbBox.addItem(calendar.getTime());
		}
		departureDateCbBox.setRenderer(new yearMonthDate()); // 이때 ListCellRenderer.yearMonthData.java형태로 만들겠다는 것

		// 콤보박스 출발시간(시간, 분)
		JComboBox<Date> departureTimeCbBox = new JComboBox<Date>();
		departureTimeCbBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		departureTimeCbBox.setBounds(143, 461, 204, 47);
		add(departureTimeCbBox);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		departureTimeCbBox.addItem(calendar.getTime());
		for (int i = 0; i < 23; i++) {
			calendar.roll(GregorianCalendar.HOUR_OF_DAY, 1);
			departureTimeCbBox.addItem(calendar.getTime());
		}
		departureTimeCbBox.setRenderer(new hourMinute()); // 이때 ListCellRenderer.hourMinute.java형태로 만들겠다는 것

		// 운송수단 콤보박스
		String[] txt = { "전체", "버스", "기차" };
		JComboBox<?> vehicleCbBox = new JComboBox<String>(txt);
		vehicleCbBox.setForeground(Color.DARK_GRAY);
		vehicleCbBox.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		vehicleCbBox.setBounds(143, 562, 204, 47);
		add(vehicleCbBox);

		// 검색버튼 누르면 다음화면인 DirectScreen화면으로 넘어간다.
		JButton goNextButton = new JButton("검색");
		goNextButton.setBorderPainted(false);
		goNextButton.setForeground(Color.DARK_GRAY);
		goNextButton.setFont(new Font("맑은 고딕", Font.BOLD, 32));
		goNextButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("검색")) {
					// 다음화면으로 넘어가기전 날짜 데이터 전처리
					Date date1 = (Date) departureDateCbBox.getSelectedItem();
					Date date2 = (Date) departureTimeCbBox.getSelectedItem();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date1);
					cal.set(Calendar.HOUR_OF_DAY, date2.getHours());
					cal.set(Calendar.MINUTE, date2.getMinutes());
					cal.set(Calendar.MILLISECOND, 0);

					// DirectScreen화면에 데이터를 전달하고 생성
					mainFrame.getContentPane().add("DirectScreen",
							new DirectScreen(mainFrame, departureTextField.getText(), destinationTextField.getText(),
									cal.getTime(), vehicleCbBox.getSelectedItem().toString()));

					// DirectScreen에 로드할 데이터가 있다면 -> DirectScreen
					if (DirectScreen.check) {
						mainFrame.changeSize(1200, 900);
						mainFrame.getCardLayout().show(mainFrame.getContentPane(), "DirectScreen");
					}
					// DirectScreen에 로드할 데이터가 없으면 -> 환승노선 찾을 건지 물어봄
					else {
						int option = JOptionPane.showConfirmDialog(null,
								"직행노선이 없습니다! \n다시 검색하려면 '아니오' \n환승노선을 찾으려면 '예'를 누르세요.", "선택",
								JOptionPane.YES_NO_OPTION);
						if (option == 0) { // 대답을 YES
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

		// 뒤로 버튼
		JButton goBackBtn = new JButton("뒤로");
		goBackBtn.setBackground(new Color(102, 0, 0));
		goBackBtn.setForeground(Color.WHITE);
		goBackBtn.setFont(new Font("12롯데마트행복Light", Font.PLAIN, 12));
		goBackBtn.setBounds(0, 0, 98, 27);
		add(goBackBtn);
		goBackBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("뒤로")) {
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