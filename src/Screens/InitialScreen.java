package Screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import BusTrainSystem.Administer;
import javax.swing.SwingConstants;

public class InitialScreen extends JPanel {

	ImageIcon icon = new ImageIcon("./image/자산 1.png"); // 배경화면 설정할 img파일 경로 설정
	ImageIcon icon01 = new ImageIcon("./image/자산 4.png"); // 배경화면 설정할 img파일 경로 설정

	MainFrame mainFrame;
	private static final long serialVersionUID = 1L;

	// 생성자
	public InitialScreen(MainFrame frame) {
		mainFrame = frame;
		init();
	}

	public void init() {
		// lblNewLabel.setFont(new Font("HY헤드라인M", Font.PLAIN, 30)); 글씨체 변경 방법

		// GridBagLayout => 컨테이너를 열과 행으로 나누어 컴포넌트들을 배치
		// column 4개, row 9개로 컨테이너를 나누고 배치
		GridBagLayout gbl_p = new GridBagLayout();
		gbl_p.columnWidths = new int[] { 95, 330, 95, 0 };
		gbl_p.rowHeights = new int[] { 450, 190, 22, 30, 17, 19, 0, 30, 0 };
		gbl_p.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_p.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gbl_p);

		// 전체 바탕화면
		JLabel background = new JLabel(icon01);
		background.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_background = new GridBagConstraints(); // 크기를 맞춰준다.
		gbc_background.insets = new Insets(0, 0, 5, 5);
		gbc_background.gridx = 1;
		gbc_background.gridy = 0;
		add(background, gbc_background);

		// 검색버튼
		JButton startButton = new JButton("검색시작");
		startButton.setForeground(new Color(255, 255, 255));
		startButton.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.fill = GridBagConstraints.BOTH; // 크기를 맞춰준다.
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 1;
		gbc_startButton.gridy = 1;
		add(startButton, gbc_startButton);
		startButton.setBackground(new Color(255, 69, 0));

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("검색시작")) {
					mainFrame.getContentPane().add("QueryScreen", new QueryScreen(mainFrame));
					mainFrame.getCardLayout().show(mainFrame.getContentPane(), "QueryScreen");
				}
			}
		});

		// 관리자 비밀번호 label
		JLabel administerLabel = new JLabel("관리자 비밀번호");
		administerLabel.setForeground(Color.LIGHT_GRAY);
		administerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		administerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_administerLabel = new GridBagConstraints();
		gbc_administerLabel.fill = GridBagConstraints.VERTICAL;
		gbc_administerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_administerLabel.gridx = 1;
		gbc_administerLabel.gridy = 3;
		add(administerLabel, gbc_administerLabel);

		// 비밀번호 입력 textField
		TextField pwd = new TextField();
		pwd.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		pwd.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_pwd = new GridBagConstraints();
		gbc_pwd.fill = GridBagConstraints.BOTH;
		gbc_pwd.insets = new Insets(0, 0, 5, 5);
		gbc_pwd.gridx = 1;
		gbc_pwd.gridy = 4;
		add(pwd, gbc_pwd);

		pwd.setEchoChar('*');
		JButton administerButton = new JButton("관리자 모드 실행(콘솔)");
		administerButton.setBackground(Color.GRAY);
		administerButton.setForeground(Color.WHITE);
		administerButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		GridBagConstraints gbc_administerButton = new GridBagConstraints();
		gbc_administerButton.insets = new Insets(0, 0, 5, 5);
		gbc_administerButton.fill = GridBagConstraints.VERTICAL;
		gbc_administerButton.gridx = 1;
		gbc_administerButton.gridy = 5;

		add(administerButton, gbc_administerButton);

		administerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("관리자 모드 실행(콘솔)")) {
					if (pwd.getText().equals("1234")) {
						MainFrame m = new MainFrame();
						Administer.getInstance().run(false, true); // 관리자 모드 실행
						// 다시 GUI 실행 -> 관리자모드에서 바뀐게 반영되어 있음
						removeAll();
						m.dispose();
						m.initMain();
						revalidate();
						repaint();
					}
					if (!pwd.getText().equals("1234") && !pwd.getText().equals(""))
						JOptionPane.showMessageDialog(null, "비밀번호 틀림!");
					if (pwd.getText().equals(""))
						JOptionPane.showMessageDialog(null, "비밀번호 입력!");

				}
			}
		});

	}

	// 스윙 컴포넌트가 자신의 모양을 그리는 메소드. 언제 호출? 컴포넌트가 그려져야하는 시점마다.
	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}
}
