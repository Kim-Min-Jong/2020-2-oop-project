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

	ImageIcon icon = new ImageIcon("./image/�ڻ� 1.png"); // ���ȭ�� ������ img���� ��� ����
	ImageIcon icon01 = new ImageIcon("./image/�ڻ� 4.png"); // ���ȭ�� ������ img���� ��� ����

	MainFrame mainFrame;
	private static final long serialVersionUID = 1L;

	// ������
	public InitialScreen(MainFrame frame) {
		mainFrame = frame;
		init();
	}

	public void init() {
		// lblNewLabel.setFont(new Font("HY������M", Font.PLAIN, 30)); �۾�ü ���� ���

		// GridBagLayout => �����̳ʸ� ���� ������ ������ ������Ʈ���� ��ġ
		// column 4��, row 9���� �����̳ʸ� ������ ��ġ
		GridBagLayout gbl_p = new GridBagLayout();
		gbl_p.columnWidths = new int[] { 95, 330, 95, 0 };
		gbl_p.rowHeights = new int[] { 450, 190, 22, 30, 17, 19, 0, 30, 0 };
		gbl_p.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_p.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gbl_p);

		// ��ü ����ȭ��
		JLabel background = new JLabel(icon01);
		background.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_background = new GridBagConstraints(); // ũ�⸦ �����ش�.
		gbc_background.insets = new Insets(0, 0, 5, 5);
		gbc_background.gridx = 1;
		gbc_background.gridy = 0;
		add(background, gbc_background);

		// �˻���ư
		JButton startButton = new JButton("�˻�����");
		startButton.setForeground(new Color(255, 255, 255));
		startButton.setFont(new Font("���� ���", Font.BOLD, 40));
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.fill = GridBagConstraints.BOTH; // ũ�⸦ �����ش�.
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 1;
		gbc_startButton.gridy = 1;
		add(startButton, gbc_startButton);
		startButton.setBackground(new Color(255, 69, 0));

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("�˻�����")) {
					mainFrame.getContentPane().add("QueryScreen", new QueryScreen(mainFrame));
					mainFrame.getCardLayout().show(mainFrame.getContentPane(), "QueryScreen");
				}
			}
		});

		// ������ ��й�ȣ label
		JLabel administerLabel = new JLabel("������ ��й�ȣ");
		administerLabel.setForeground(Color.LIGHT_GRAY);
		administerLabel.setFont(new Font("���� ���", Font.BOLD, 20));
		administerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_administerLabel = new GridBagConstraints();
		gbc_administerLabel.fill = GridBagConstraints.VERTICAL;
		gbc_administerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_administerLabel.gridx = 1;
		gbc_administerLabel.gridy = 3;
		add(administerLabel, gbc_administerLabel);

		// ��й�ȣ �Է� textField
		TextField pwd = new TextField();
		pwd.setFont(new Font("���� ���", Font.BOLD, 15));
		pwd.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_pwd = new GridBagConstraints();
		gbc_pwd.fill = GridBagConstraints.BOTH;
		gbc_pwd.insets = new Insets(0, 0, 5, 5);
		gbc_pwd.gridx = 1;
		gbc_pwd.gridy = 4;
		add(pwd, gbc_pwd);

		pwd.setEchoChar('*');
		JButton administerButton = new JButton("������ ��� ����(�ܼ�)");
		administerButton.setBackground(Color.GRAY);
		administerButton.setForeground(Color.WHITE);
		administerButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		GridBagConstraints gbc_administerButton = new GridBagConstraints();
		gbc_administerButton.insets = new Insets(0, 0, 5, 5);
		gbc_administerButton.fill = GridBagConstraints.VERTICAL;
		gbc_administerButton.gridx = 1;
		gbc_administerButton.gridy = 5;

		add(administerButton, gbc_administerButton);

		administerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("������ ��� ����(�ܼ�)")) {
					if (pwd.getText().equals("1234")) {
						MainFrame m = new MainFrame();
						Administer.getInstance().run(false, true); // ������ ��� ����
						// �ٽ� GUI ���� -> �����ڸ�忡�� �ٲ�� �ݿ��Ǿ� ����
						removeAll();
						m.dispose();
						m.initMain();
						revalidate();
						repaint();
					}
					if (!pwd.getText().equals("1234") && !pwd.getText().equals(""))
						JOptionPane.showMessageDialog(null, "��й�ȣ Ʋ��!");
					if (pwd.getText().equals(""))
						JOptionPane.showMessageDialog(null, "��й�ȣ �Է�!");

				}
			}
		});

	}

	// ���� ������Ʈ�� �ڽ��� ����� �׸��� �޼ҵ�. ���� ȣ��? ������Ʈ�� �׷������ϴ� ��������.
	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}
}
