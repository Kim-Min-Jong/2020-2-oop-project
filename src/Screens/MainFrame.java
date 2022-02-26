package Screens;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import BusTrainSystem.Administer;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private CardLayout cards = new CardLayout();

	public CardLayout getCardLayout() {
		return cards;
	}

	public MainFrame mainFrame = null;

	// 디자인
	public void initMain() {
		// GUI 디자인 변경
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Nimbus테마를 작동할 수 없습니다.");
		}

		getContentPane().setLayout(cards); // mainframe을 cardlayout으로 설정
		setResizable(false); // 창크기 조절 x

		// frame에 첫화면 panel 추가
		getContentPane().add("InitialScreen", new InitialScreen(this));
		revalidate(); // 새 컨텐츠를 추가하고 호출하는 역할
		repaint(); 

		setSize(550, 900); // frame의 크기 지정
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("버스/기차 통합조회");
		setVisible(true);
	}

	// Frame크기 변경
	public void changeSize(int width, int height) {
		Dimension resultSize = new Dimension(width, height);
		this.setPreferredSize(resultSize);
		this.pack();
	}

	public static void main(String[] args) {
		MainFrame main = new MainFrame();
		Administer.getInstance().run(true, false);
		main.initMain();
	}
}
