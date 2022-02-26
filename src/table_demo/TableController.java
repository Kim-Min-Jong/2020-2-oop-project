package table_demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import BusTrainSystem.Schedule;
import BusTrainSystem.scheduleMgr;

public class TableController {
	DefaultTableModel tableModel;
	JTable table;
	JTableHeader headers;
	scheduleMgr dataMgr;
	ArrayList<Schedule> firstArr;
	ArrayList<Schedule> nowArr;

	@SuppressWarnings({ "serial", "unchecked" })
	boolean init(scheduleMgr mgr, String departure, String destination, Date departureTime, String vehicleStr,
			int paneNum) {
		dataMgr = mgr;
		tableModel = new DefaultTableModel(mgr.getColumnNames(), 0) { // �� ���� ���ϰ� �ϴ� �κ�
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// firstArr�� â ��ȯ��, QueryScreen�� ���Ǵ�� �����͸� �ε��� �� (*���� �ٲ�� �ȵǴ� ������)
		// now Arr�� ó������ firstArr�� ������������ ��ȸâ �ȿ���
		// ���͸� (minPrice() ��)�ؼ� ������ �� �ִ� (*���������� �����Ͱ� ��)

		// ������ �߰� �κ�
		firstArr = loadData(departure, destination, departureTime, vehicleStr, paneNum);
		if (firstArr.isEmpty()) {
			return false; // �����ε� �����Ͱ� ������ ȯ������ �Ѿ�� ����� �ܼ��� ��
		}
		nowArr = (ArrayList<Schedule>) firstArr.clone();

		// table ����
		table = new JTable(tableModel);

		headers = table.getTableHeader();
		table.setPreferredScrollableViewportSize(new Dimension(500, 220));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		headers.setOpaque(false);
		headers.setFont(new Font("����ü", Font.PLAIN, 15));
		headers.setBackground(Color.WHITE);
		headers.setForeground(Color.BLACK);
		table.setFont(new Font("����ü", Font.PLAIN, 12));
		table.setBackground(Color.WHITE);
		table.setForeground(new Color(255, 69, 0));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		// table�� ��߽ð�, �����ð� �÷� ���� �ø�
		table.getColumn("��߽ð�").setPreferredWidth(250);
		table.getColumn("�����ð�").setPreferredWidth(250);
		table.getColumn("�ܿ��¼�").setPreferredWidth(50);
		table.getColumn("�ҿ�ð�").setPreferredWidth(100);
		// ���̺� �� ������ �����͸� �� ����� ����
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}

		// ���� �÷����� Ŭ���� �� �ش� �÷��� �����͸� �������� ���̺� ���� ������������ ������
		table.setAutoCreateRowSorter(true); // �ڵ� �� ���ı��
		@SuppressWarnings("rawtypes")
		TableRowSorter<?> tablesorter = new TableRowSorter(table.getModel());
		table.setRowSorter(tablesorter);

		return true;
	}

	void resetBus(String str) {
		// setRowCount�� ��ȸ ������ ���� ��������� ������ ����� ��.
		tableModel.setRowCount(0);
		nowArr.clear();

		// ��ü(�Ϲ� ��� �Ѵ�)
		if (str.contentEquals("��ü")) {
			for (Schedule m : firstArr) {
				if (m.checkVehicle()) {
					nowArr.add(m);
					tableModel.addRow(m.getUiTexts());
				}
			}
			return;
		}

		// �Ϲ� or ���
		for (Schedule m : firstArr) {
			if (str.contentEquals("�Ϲ�") && m.vehicle.type.equals(str)) {
				nowArr.add(m);
			} else if (str.contentEquals("���") && m.vehicle.type.equals(str)) {
				nowArr.add(m);
			}
		}
		for (Schedule m : nowArr) {
			tableModel.addRow(m.getUiTexts());
		}
	}

	void resetTrain(String str) {
		// nowArr�� ������ �Ѵ�� �ִ��� ����
		boolean checkOnlyTrain = true;
		for (Schedule m : nowArr) {
			if (m.checkVehicle()) {
				checkOnlyTrain = false; // ������ ������ false
				break;
			}
		}

		// ������ ä���
		ArrayList<Schedule> resetArr = null;

		if (checkOnlyTrain) { // ���� ������ ���°��
			// ��ü�� �׳� �״�� ����
			if (str.contentEquals("��ü")) {
				return;
			}

			// ��ü�� �ƴѰ��, nowArr�� ���� firstArr���� ���ǰ��� �´� ������ ã�Ƽ� �ٽ�ä��
			tableModel.setRowCount(0);

			nowArr.clear();
			resetArr = new ArrayList<Schedule>();
			for (Schedule m : firstArr) {
				if (str.contentEquals("SRT") && m.vehicle.type.equals(str)) {
					resetArr.add(m);
				} else if (str.contentEquals("����ȭȣ") && m.vehicle.type.equals(str)) {
					resetArr.add(m);
				} else if (str.contentEquals("KTX") && m.vehicle.type.equals(str)) {
					resetArr.add(m);
				}
			}
			nowArr = resetArr;
			for (Schedule m : resetArr) {
				tableModel.addRow(m.getUiTexts());
			}
		}
		// ���������Ͱ� ������, ���������� �߰�
		else {
			if (str.contentEquals("��ü")) {
				for (Schedule m : firstArr) {
					if (!m.checkVehicle()) {
						nowArr.add(m);
						tableModel.addRow(m.getUiTexts());
					}
				}
				return;
			}

			resetArr = new ArrayList<Schedule>();
			for (Schedule m : firstArr) {
				if (str.contentEquals("SRT") && m.vehicle.type.equals(str)) {
					resetArr.add(m);
				} else if (str.contentEquals("����ȭȣ") && m.vehicle.type.equals(str)) {
					resetArr.add(m);
				} else if (str.contentEquals("KTX") && m.vehicle.type.equals(str)) {
					resetArr.add(m);
				}
			}
			nowArr.addAll(resetArr);
			for (Schedule m : resetArr) {
				tableModel.addRow(m.getUiTexts());
			}
		}

	}

	void minTime() {
		Collections.sort(nowArr, new Comparator<Schedule>() {
			@Override
			public int compare(Schedule s1, Schedule s2) {
				if (s1.movingTime[0] > s2.movingTime[0]) {
					return 1;
				} else if (s1.movingTime[0] < s2.movingTime[0]) {
					return -1;
				} else {
					if (s1.movingTime[1] > s2.movingTime[1]) {
						return 1;
					} else if (s1.movingTime[1] < s2.movingTime[1]) {
						return -1;
					}
					return 0;
				}
			}
		});
		// setRowCount�� ��ȸ ������ ���� ��������� ������ ����� ��.
		tableModel.setRowCount(0);
		for (Schedule s : nowArr) {
			tableModel.addRow(s.getUiTexts());
		}
	}

	void minPrice() {
		Collections.sort(nowArr, new Comparator<Schedule>() {
			@Override
			public int compare(Schedule s1, Schedule s2) {
				if (s1.price > s2.price) {
					return 1;
				} else if (s1.price < s2.price) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		// setRowCount�� ��ȸ ������ ���� ��������� ������ ����� ��.
		tableModel.setRowCount(0);
		for (Schedule s : nowArr) {
			tableModel.addRow(s.getUiTexts());
		}
	}

	// �������
	@SuppressWarnings("unchecked")
	ArrayList<Schedule> loadData(String departure, String destination, Date departureTime, String vehicleStr,
			int paneNum) {
		ArrayList<Schedule> result = null;
		@SuppressWarnings("rawtypes")
		ArrayList<ArrayList> tempArr = null;

		if (paneNum == 0) { // ���� ��ȸ�� â�̸�
			result = dataMgr.initDirectData(departure, destination, departureTime, vehicleStr);
		} else if (paneNum == 1) { // ȯ����ȸ�� 1��° â
			tempArr = dataMgr.initTransferData(departure, destination, departureTime, vehicleStr);
			result = tempArr.get(0);
		} else if (paneNum == 2) { // ȯ����ȸ�� 2��° â
			tempArr = dataMgr.initTransferData(departure, destination, departureTime, vehicleStr);
			result = tempArr.get(1);
		}
		// setRowCount�� ��ȸ ������ ���� ��������� ������ ����� ��.
		tableModel.setRowCount(0);
		for (Schedule s : result)
			tableModel.addRow(s.getUiTexts());
		return result;
	}

}