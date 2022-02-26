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

	private String[] headers = { "출발지", "도착지", "출발시간", "도착시간", "가격", "잔여좌석", "교통수단", "소요시간" };

	public int getColumnCount() {
		return 6;
	}

	// 테이블의 열 제목을 스트링 배열로 돌려줌
	public String[] getColumnNames() {
		return headers;
	}

	// GUI에서 호출할 메소드들

	// 직행 데이터 로드
	public ArrayList<Schedule> initDirectData(String departure, String destination, Date departureTime,
			String vehicleStr) {
		ArrayList<Schedule> tempSchedules = vehicleFiltering(vehicleStr);
		ArrayList<Schedule> foundSchedules = new ArrayList<Schedule>();

		for (Schedule s : tempSchedules) {
			boolean checkAll = false;
			// 스케줄에서 출발지 도착지 확인
			if (departure.equals("") && destination.equals("")) {
				// 출발지 x, 도착지x
				checkAll = true; // 전체 스케줄을 조회한다.
			} else if (departure.contentEquals("") && s.matchDestination(destination)) {
				// 출발지 입력x, 도착지 입력한경우
				checkAll = true;
			} else if (s.matchDeparture(departure) && destination.contentEquals("")) {
				// 출발지 입력한경우, 도착지x
				checkAll = true;
			} else if (s.matchDeparture(departure) && s.matchDestination(destination)) {
				// 둘다 입력한 경우
				checkAll = true;
			}

			// 시간 확인
			if (checkAll && checkDate(departureTime, s.departureTime)) {
				foundSchedules.add(s);
			}
		}
		return foundSchedules;

	}

	// 환승 데이터 로드
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> initTransferData(String departure, String destination, Date departureTime,
			String vehicleStr) {
		ArrayList<Schedule> filteredSchedules = vehicleFiltering(vehicleStr);
		ArrayList<Schedule> tempSchedules1 = new ArrayList<Schedule>();
		ArrayList<Schedule> tempSchedules2 = new ArrayList<Schedule>();
		ArrayList<Schedule> foundList1 = new ArrayList<Schedule>();
		ArrayList<Schedule> foundList2 = new ArrayList<Schedule>();

		// 출발지 일치하는 스케줄 찾기
		for (Schedule s : filteredSchedules) {
			if (s.matchDeparture(departure) && checkDate(departureTime, s.departureTime)) {
				tempSchedules1.add(s);
			}
		}

		// 도착지 일치하는 스케줄 찾기
		for (Schedule s : filteredSchedules) {
			if (s.matchDestination(destination) && checkDate(departureTime, s.departureTime)) {
				tempSchedules2.add(s);
			}
		}

		// 합치기
		for (Schedule s1 : tempSchedules1) {
			for (Schedule s2 : tempSchedules2) {
				// s1.의 도착시간이 s2의 출발시간보다 더 전이다.
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

	// 교통수단 선택에 따른 정리
	@SuppressWarnings("unchecked")
	public ArrayList<Schedule> vehicleFiltering(String vehicleStr) {
		ArrayList<Schedule> filteredSchedules;
		if (vehicleStr.contentEquals("버스")) { // 버스인경우
			filteredSchedules = new ArrayList<Schedule>();
			for (Schedule s : Administer.scheduleManager.mList) {
				if (s.checkVehicle()) { // checkVehicle은 버스이면 true
					filteredSchedules.add(s);
				}
			}
		} else if (vehicleStr.contentEquals("기차")) { // 기차인경우
			filteredSchedules = new ArrayList<Schedule>();
			for (Schedule s : Administer.scheduleManager.mList) {
				if (!s.checkVehicle()) { // checkVehicle은 기차이면 false
					filteredSchedules.add(s);
				}
			}
		} else { // 전체인 경우
			filteredSchedules = (ArrayList<Schedule>) Administer.scheduleManager.mList.clone();
		}
		return filteredSchedules;
	}

	// 스케줄날짜가 사용자 입력한 시간보다 더 이후 이고, 그 당일 이어야됨(일자는 같다)
	@SuppressWarnings("deprecation")
	public boolean checkDate(Date userDepartureTime, Date scheduleDepartureTime) {
		if (userDepartureTime.before(scheduleDepartureTime) // 사용자 입력시간이 스케줄표의 시간들보다 더 전이다
				&& userDepartureTime.getDate() == scheduleDepartureTime.getDate())
			return true;
		return false;
	}
}
