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

	// ������
	public void initMain() {
		// GUI ������ ����
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Nimbus�׸��� �۵��� �� �����ϴ�.");
		}

		getContentPane().setLayout(cards); // mainframe�� cardlayout���� ����
		setResizable(false); // âũ�� ���� x

		// frame�� ùȭ�� panel �߰�
		getContentPane().add("InitialScreen", new InitialScreen(this));
		revalidate(); // �� �������� �߰��ϰ� ȣ���ϴ� ����
		repaint(); 

		setSize(550, 900); // frame�� ũ�� ����
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("����/���� ������ȸ");
		setVisible(true);
	}

	// Frameũ�� ����
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
