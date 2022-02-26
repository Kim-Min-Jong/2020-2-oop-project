package BusTrainSystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import mgr.Manageable;

public class Schedule implements Manageable {
	int id;
	public String departure; // 출발지
	public String destination; // 도착지
	Date departureTime; // 출발시간
	Date arrivalTime; // 도착시간
	public Vehicle vehicle;
	public int price; // 가격
	public int leftSeat; // 남은자리
	public int movingTime[]; // 이동시간

	@Override
	public void read(Scanner scan) {
		id = scan.nextInt();
		departure = scan.next();
		destination = scan.next();

		Calendar cal = null;
		departureTime = readTime(scan, cal);
		arrivalTime = readTime(scan, cal);

		getVehicle(scan);

		price = scan.nextInt();
		leftSeat = scan.nextInt();
		movingTime = timeCal();

	}

	public void getVehicle(Scanner scan) {
		int checkVehicle = scan.nextInt();
		String vehicleId = scan.next();
		Vehicle v = null;
		if (checkVehicle == 0) {
			v = (Bus) v;
			v = Administer.busManager.find(vehicleId);
			if (v == null) {
				System.out.println("못찾음 " + vehicleId);
			}

		} else {
			v = (Train) v;
			v = Administer.trainManager.find(vehicleId);
			if (v == null) {
				System.out.println("못찾음 " + vehicleId);
			}
		}
		vehicle = v;
	}

	public boolean checkVehicle() {
		if (vehicle instanceof Bus) {
			return true; // Bus면 true
		} else {
			return false; // Train이면 false
		}
	}

	public Date readTime(Scanner scan, Calendar cal) {
		cal = Calendar.getInstance();
		int year = scan.nextInt();
		int month = scan.nextInt();
		int date = scan.nextInt();
		int hour = scan.nextInt();
		int minute = scan.nextInt();

		cal.set(year, month - 1, date, hour, minute);

		return cal.getTime();
	}

	@Override
	public void print() {
		print(true);
	}

	@Override
	public void print(boolean bDetail) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 a hh:mm");
		if (bDetail) {
			System.out.printf("\n출발지: %s   |  도착지: %s\n", departure, destination); // 출발지: 서울 도착지: 부산
			vehicle.print();
			System.out.println("출발시간: " + dateFormat.format(departureTime));
			System.out.println("도착시간: " + dateFormat.format(arrivalTime));
			System.out.printf("가격: %d원 ", price);
			System.out.printf("남은자리: %d석\n", leftSeat);
			System.out.printf("소요시간: %d시간 %d분\n", movingTime[0], movingTime[1]);

		} else {
			System.out.printf("\nScheduleId: %d\n", id);
			vehicle.print(false);
			System.out.printf("출발지: %s   |  도착지: %s\n", departure, destination); // 출발지: 서울 도착지: 부산
			System.out.println("출발시간: " + dateFormat.format(departureTime));
			System.out.println("도착시간: " + dateFormat.format(arrivalTime));
			System.out.printf("가격: %d원 ", price);
			System.out.printf("남은자리: %d석\n", leftSeat);
			System.out.printf("소요시간: %d시간 %d분\n", movingTime[0], movingTime[1]);
		}
	}

	@Override
	public boolean matches(String kwd) {
		if (kwd.length() >= 2 && departure.contains(kwd))
			return true;
		if (kwd.length() >= 2 && destination.contains(kwd))
			return true;
		if (isNumeric(kwd) && price < Integer.parseInt(kwd)) // 살수 있는 모든 티켓을 찾는다.
			return true;
		if (("" + leftSeat).equals(kwd)) // 좌석 수가 일치하면
			return true;
		if (vehicle.matches(kwd))
			return true;

		return false;
	}

	@Override
	public boolean matches(String[] kwdArr) { // ||이 아닌 && 여러 검색어 모두를 만족하는 것을 찾고싶다
		for (String kwd : kwdArr) {
			if (!matches(kwd)) {
				return false;
			}
		}
		return true;
	}

	public boolean matcheId(int kwdId) {
		if (kwdId != id) {
			return false;
		}
		return true;
	}

	public boolean matchDeparture(String kwd) {
		if (kwd.length() >= 2 && departure.contains(kwd)) {
			return true;
		}
		return false;
	}

	public boolean matchDestination(String kwd) {
		if (kwd.length() >= 2 && destination.contains(kwd)) {
			return true;
		}
		return false;
	}

	public boolean isNumeric(String kwd) {
		try {
			Double.parseDouble(kwd);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isAvailable() { // 이용가능한 좌석이 남아있는지
		if (leftSeat == 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	int[] timeCal() {
		int subHours = arrivalTime.getHours() - departureTime.getHours();
		int subMinute = arrivalTime.getMinutes() - departureTime.getMinutes();
		int[] result = new int[2];

		if (subMinute < 0) { // 분끼리 뺄셈시 음수가 되는 경우
			--subHours; // 미리 1시간 뺴야 1시간을 60분으로 넘겨주는 효과
			if (arrivalTime.getHours() == 0) {
				subHours += 24;
			}
			subMinute += 60; // 60분 더해 분 차이 맞추기
		} else { // 분 뺄셈시 양수 or 0
			if (subHours < 0) {
				subHours += 24;
			}
		}
		result[0] = subHours;
		result[1] = subMinute;

		return result;
	}

	@Override
	public void set(Scanner scan) {
		while (true) {
			System.out.println("변경을 원하는 필드  입력");
			System.out.print("(1)출발지 (2)도착지 (3)출발시간 (4)도착시간 (5)이동수단 (6)가격 (7)남은자리  (0)종료>> ");
			int num = scan.nextInt();
			Calendar cal = null;
			switch (num) {
			case 0:
				return;
			case 1:
				System.out.print("출발지: ");
				departure = scan.next();
				break;
			case 2:
				System.out.println("도착지: ");
				destination = scan.next();
				break;
			case 3:
				System.out.println("년 월 일 시 분 순서대로 띄어쓰기해서 입력");
				System.out.print("출발시간: ");
				departureTime = readTime(scan, cal);
				break;
			case 4:
				System.out.println("년 월 일 시 분 순서대로 띄어쓰기해서 입력");
				System.out.print("도착시간: ");
				arrivalTime = readTime(scan, cal);
				break;
			case 5:
				System.out.print("변경할 코드 입력: ");
				String kwd = scan.next();
				vehicle = Administer.busManager.find(kwd);
				break;
			case 6:
				System.out.print("가격: ");
				price = scan.nextInt();
				break;
			case 7:
				System.out.print("남은자리: ");
				leftSeat = scan.nextInt();
				break;
			default:
				System.out.println("입력 오류");
				break;
			}
		}
	}

	public String[] getUiTexts() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 a hh:mm");
		String[] texts = new String[10];

		texts[0] = departure;
		texts[1] = destination;
		texts[2] = "" + dateFormat.format(departureTime);
		texts[3] = "" + dateFormat.format(arrivalTime);
		texts[4] = "" + price;
		texts[5] = "" + leftSeat;
		texts[6] = vehicle.type;
		texts[7] = movingTime[0] + "시간 " + movingTime[1] + "분";

		return texts;
	}

}
