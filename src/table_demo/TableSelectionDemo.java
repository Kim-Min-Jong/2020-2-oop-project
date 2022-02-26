package table_demo;

import java.awt.*;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import BusTrainSystem.scheduleMgr;

public class TableSelectionDemo extends JPanel {
	private static final long serialVersionUID = 1L;
	TableController tableController;

	public TableSelectionDemo() {
		super(new BorderLayout());
	}

	public boolean addComponentsToPane(scheduleMgr mgr, String departure, String destination, Date departureTime,
			String vehicleStr, int paneNum) { // transferCheck true면 환승
		tableController = new TableController();

		boolean check = tableController.init(mgr, departure, destination, departureTime, vehicleStr, paneNum);
		JScrollPane center = new JScrollPane(tableController.table);
		add(center, BorderLayout.CENTER);

		return check;
	}

	public void sortComponentsToPane(String str) {
		if (str.equals("시간")) {
			tableController.minTime();
		} else if (str.contentEquals("가격")) {
			tableController.minPrice();
		}
	}

	public void updateComponentsToPane(String busKwd, String trainKwd) {
		if (busKwd != null && trainKwd != null) {
			tableController.resetBus(busKwd);
			tableController.resetTrain(trainKwd);
		} else if (busKwd != null && trainKwd == null) { // 버스만
			tableController.resetBus(busKwd);
		} else if (busKwd == null && trainKwd != null) { // 기차만
			tableController.resetTrain(trainKwd);
		}
	}

}