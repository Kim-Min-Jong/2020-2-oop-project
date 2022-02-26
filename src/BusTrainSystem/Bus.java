package BusTrainSystem;

import java.util.Scanner;

public class Bus extends Vehicle {
	int num;

	@Override
	public void read(Scanner scan) {
		num = scan.nextInt();
		id = scan.next();
		int typeInt = scan.nextInt();
		if (typeInt == 0) {
			type = "일반";
		} else {
			type = "우등";
		}
		driver = scan.next();
	}

	@Override
	public void print() {
		print(true);
	}

	@Override
	public void print(boolean bDetail) {
		if (bDetail) { // 버스종류 조회 - 고객 조회
			System.out.printf("버스정보: %s\n", type);
		} else { // 버스 정보 전체조회 - 관리자 조회
			System.out.printf("BusID: %d\n", num);
			System.out.printf("버스정보: %s %s  %s\n", id, type, driver);
		}
	}

	@Override
	public boolean matches(String kwd) {
		if (id.contentEquals(kwd)) {
			return true;
		}
		if (type.contentEquals(kwd)) {
			return true;
		}
		if (driver.contentEquals(kwd)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean matches(String[] kwdArr) {
		for (String kwd : kwdArr) {
			if (!matches(kwd)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void set(Scanner scan) {
		// TODO Auto-generated method stub
		System.out.println("변경을 원하는 필드  입력");
		System.out.println("(1)버스 코드 (2) 버스타입 (3)버스 기사 (0)종료  >>");
		int num = scan.nextInt();
		switch (num) {
		case 0:
			return;
		case 1:
			System.out.print("코드:");
			id = scan.next();
			/*
			 * for(Schedule s: Administer.scheduleManager.mList) { for(Bus b:
			 * Administer.busManager.mList) { if(!b.id.equals(s.vehicleId)) s.vehicleId =
			 * id; } }
			 */
			break;
		case 2:
			System.out.print("버스 타입 (우등 or 일반  선택): ");
			type = scan.next();
			break;
		case 3:
			System.out.println("버스기사이름: ");
			driver = scan.next();
			break;
		default:
			System.out.println("입력오류!");
		}

	}

	public boolean matcheId(int kwdId) {
		if (kwdId != num) {
			return false;
		}
		return true;
	}
}
