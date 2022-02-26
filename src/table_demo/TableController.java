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
		tableModel = new DefaultTableModel(mgr.getColumnNames(), 0) { // 셀 수정 못하게 하는 부분
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// firstArr은 창 전환시, QueryScreen의 조건대로 데이터를 로드한 값 (*절대 바뀌면 안되는 데이터)
		// now Arr은 처음에는 firstArr의 복제값이지만 조회창 안에서
		// 필터링 (minPrice() 등)해서 보여줄 수 있는 (*가공가능한 데이터가 됨)

		// 데이터 추가 부분
		firstArr = loadData(departure, destination, departureTime, vehicleStr, paneNum);
		if (firstArr.isEmpty()) {
			return false; // 직행인데 데이터가 없으면 환승으로 넘어갈까 물어보는 단서가 됨
		}
		nowArr = (ArrayList<Schedule>) firstArr.clone();

		// table 생성
		table = new JTable(tableModel);

		headers = table.getTableHeader();
		table.setPreferredScrollableViewportSize(new Dimension(500, 220));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		headers.setOpaque(false);
		headers.setFont(new Font("굴림체", Font.PLAIN, 15));
		headers.setBackground(Color.WHITE);
		headers.setForeground(Color.BLACK);
		table.setFont(new Font("굴림체", Font.PLAIN, 12));
		table.setBackground(Color.WHITE);
		table.setForeground(new Color(255, 69, 0));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		// table의 출발시간, 도착시간 컬럼 길이 늘림
		table.getColumn("출발시간").setPreferredWidth(250);
		table.getColumn("도착시간").setPreferredWidth(250);
		table.getColumn("잔여좌석").setPreferredWidth(50);
		table.getColumn("소요시간").setPreferredWidth(100);
		// 테이블 셀 내부의 데이터를 셀 가운데로 정렬
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}

		// 셀의 컬럼명을 클릭할 시 해당 컬럼의 데이터를 기준으로 테이블 행을 오름차순으로 재정렬
		table.setAutoCreateRowSorter(true); // 자동 행 정렬기능
		@SuppressWarnings("rawtypes")
		TableRowSorter<?> tablesorter = new TableRowSorter(table.getModel());
		table.setRowSorter(tablesorter);

		return true;
	}

	void resetBus(String str) {
		// setRowCount는 조회 갱신을 위해 이전목록의 내용을 지우는 것.
		tableModel.setRowCount(0);
		nowArr.clear();

		// 전체(일반 우등 둘다)
		if (str.contentEquals("전체")) {
			for (Schedule m : firstArr) {
				if (m.checkVehicle()) {
					nowArr.add(m);
					tableModel.addRow(m.getUiTexts());
				}
			}
			return;
		}

		// 일반 or 우등
		for (Schedule m : firstArr) {
			if (str.contentEquals("일반") && m.vehicle.type.equals(str)) {
				nowArr.add(m);
			} else if (str.contentEquals("우등") && m.vehicle.type.equals(str)) {
				nowArr.add(m);
			}
		}
		for (Schedule m : nowArr) {
			tableModel.addRow(m.getUiTexts());
		}
	}

	void resetTrain(String str) {
		// nowArr에 버스가 한대라도 있는지 조사
		boolean checkOnlyTrain = true;
		for (Schedule m : nowArr) {
			if (m.checkVehicle()) {
				checkOnlyTrain = false; // 버스가 있으면 false
				break;
			}
		}

		// 데이터 채우기
		ArrayList<Schedule> resetArr = null;

		if (checkOnlyTrain) { // 버스 데이터 없는경우
			// 전체는 그냥 그대로 리턴
			if (str.contentEquals("전체")) {
				return;
			}

			// 전체가 아닌경우, nowArr을 비우고 firstArr에서 조건값에 맞는 기차를 찾아서 다시채움
			tableModel.setRowCount(0);

			nowArr.clear();
			resetArr = new ArrayList<Schedule>();
			for (Schedule m : firstArr) {
				if (str.contentEquals("SRT") && m.vehicle.type.equals(str)) {
					resetArr.add(m);
				} else if (str.contentEquals("무궁화호") && m.vehicle.type.equals(str)) {
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
		// 버스데이터가 있으면, 기차데이터 추가
		else {
			if (str.contentEquals("전체")) {
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
				} else if (str.contentEquals("무궁화호") && m.vehicle.type.equals(str)) {
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
		// setRowCount는 조회 갱신을 위해 이전목록의 내용을 지우는 것.
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
		// setRowCount는 조회 갱신을 위해 이전목록의 내용을 지우는 것.
		tableModel.setRowCount(0);
		for (Schedule s : nowArr) {
			tableModel.addRow(s.getUiTexts());
		}
	}

	// 직행버전
	@SuppressWarnings("unchecked")
	ArrayList<Schedule> loadData(String departure, String destination, Date departureTime, String vehicleStr,
			int paneNum) {
		ArrayList<Schedule> result = null;
		@SuppressWarnings("rawtypes")
		ArrayList<ArrayList> tempArr = null;

		if (paneNum == 0) { // 직행 조회의 창이면
			result = dataMgr.initDirectData(departure, destination, departureTime, vehicleStr);
		} else if (paneNum == 1) { // 환승조회의 1번째 창
			tempArr = dataMgr.initTransferData(departure, destination, departureTime, vehicleStr);
			result = tempArr.get(0);
		} else if (paneNum == 2) { // 환승조회의 2번째 창
			tempArr = dataMgr.initTransferData(departure, destination, departureTime, vehicleStr);
			result = tempArr.get(1);
		}
		// setRowCount는 조회 갱신을 위해 이전목록의 내용을 지우는 것.
		tableModel.setRowCount(0);
		for (Schedule s : result)
			tableModel.addRow(s.getUiTexts());
		return result;
	}

}