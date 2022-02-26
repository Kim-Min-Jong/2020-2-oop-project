package BusTrainSystem;

import java.util.ArrayList;
import java.util.Date;

public class scheduleMgr {
	private static scheduleMgr mgr = null;

	private scheduleMgr() {
	};

	public static scheduleMgr getInstance() {
		if (mgr == null)
			mgr = new scheduleMgr();
		return mgr;
	}

	private String[] headers = { "�����", "������", "��߽ð�", "�����ð�", "����", "�ܿ��¼�", "�������", "�ҿ�ð�" };

	public int getColumnCount() {
		return 6;
	}

	// ���̺��� �� ������ ��Ʈ�� �迭�� ������
	public String[] getColumnNames() {
		return headers;
	}

	// GUI���� ȣ���� �޼ҵ��

	// ���� ������ �ε�
	public ArrayList<Schedule> initDirectData(String departure, String destination, Date departureTime,
			String vehicleStr) {
		ArrayList<Schedule> tempSchedules = vehicleFiltering(vehicleStr);
		ArrayList<Schedule> foundSchedules = new ArrayList<Schedule>();

		for (Schedule s : tempSchedules) {
			boolean checkAll = false;
			// �����ٿ��� ����� ������ Ȯ��
			if (departure.equals("") && destination.equals("")) {
				// ����� x, ������x
				checkAll = true; // ��ü �������� ��ȸ�Ѵ�.
			} else if (departure.contentEquals("") && s.matchDestination(destination)) {
				// ����� �Է�x, ������ �Է��Ѱ��
				checkAll = true;
			} else if (s.matchDeparture(departure) && destination.contentEquals("")) {
				// ����� �Է��Ѱ��, ������x
				checkAll = true;
			} else if (s.matchDeparture(departure) && s.matchDestination(destination)) {
				// �Ѵ� �Է��� ���
				checkAll = true;
			}

			// �ð� Ȯ��
			if (checkAll && checkDate(departureTime, s.departureTime)) {
				foundSchedules.add(s);
			}
		}
		return foundSchedules;

	}

	// ȯ�� ������ �ε�
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> initTransferData(String departure, String destination, Date departureTime,
			String vehicleStr) {
		ArrayList<Schedule> filteredSchedules = vehicleFiltering(vehicleStr);
		ArrayList<Schedule> tempSchedules1 = new ArrayList<Schedule>();
		ArrayList<Schedule> tempSchedules2 = new ArrayList<Schedule>();
		ArrayList<Schedule> foundList1 = new ArrayList<Schedule>();
		ArrayList<Schedule> foundList2 = new ArrayList<Schedule>();

		// ����� ��ġ�ϴ� ������ ã��
		for (Schedule s : filteredSchedules) {
			if (s.matchDeparture(departure) && checkDate(departureTime, s.departureTime)) {
				tempSchedules1.add(s);
			}
		}

		// ������ ��ġ�ϴ� ������ ã��
		for (Schedule s : filteredSchedules) {
			if (s.matchDestination(destination) && checkDate(departureTime, s.departureTime)) {
				tempSchedules2.add(s);
			}
		}

		// ��ġ��
		for (Schedule s1 : tempSchedules1) {
			for (Schedule s2 : tempSchedules2) {
				// s1.�� �����ð��� s2�� ��߽ð����� �� ���̴�.
				if (s1.destination.contentEquals(s2.departure) && s1.arrivalTime.before(s2.departureTime)) {
					if (!foundList1.contains(s1)) {
						foundList1.add(s1);
					}
					if (!foundList2.contains(s2)) {
						foundList2.add(s2);
					}
				}
			}
		}
		ArrayList<ArrayList> transferArray = new ArrayList<>();
		transferArray.add(foundList1);
		transferArray.add(foundList2);

		return transferArray;

	}

	// ������� ���ÿ� ���� ����
	@SuppressWarnings("unchecked")
	public ArrayList<Schedule> vehicleFiltering(String vehicleStr) {
		ArrayList<Schedule> filteredSchedules;
		if (vehicleStr.contentEquals("����")) { // �����ΰ��
			filteredSchedules = new ArrayList<Schedule>();
			for (Schedule s : Administer.scheduleManager.mList) {
				if (s.checkVehicle()) { // checkVehicle�� �����̸� true
					filteredSchedules.add(s);
				}
			}
		} else if (vehicleStr.contentEquals("����")) { // �����ΰ��
			filteredSchedules = new ArrayList<Schedule>();
			for (Schedule s : Administer.scheduleManager.mList) {
				if (!s.checkVehicle()) { // checkVehicle�� �����̸� false
					filteredSchedules.add(s);
				}
			}
		} else { // ��ü�� ���
			filteredSchedules = (ArrayList<Schedule>) Administer.scheduleManager.mList.clone();
		}
		return filteredSchedules;
	}

	// �����ٳ�¥�� ����� �Է��� �ð����� �� ���� �̰�, �� ���� �̾�ߵ�(���ڴ� ����)
	@SuppressWarnings("deprecation")
	public boolean checkDate(Date userDepartureTime, Date scheduleDepartureTime) {
		if (userDepartureTime.before(scheduleDepartureTime) // ����� �Է½ð��� ������ǥ�� �ð��麸�� �� ���̴�
				&& userDepartureTime.getDate() == scheduleDepartureTime.getDate())
			return true;
		return false;
	}
}
