package BusTrainSystem;

import java.util.Scanner;

public class Train extends Vehicle {
	int num;

	@Override
	public void read(Scanner scan) {
		num = scan.nextInt();
		id = scan.next();
		int typeInt = scan.nextInt();
		if (typeInt == 0) {
			type = "SRT";
		} else if (typeInt == 1) {
			type = "무궁화호";
		} else {
			type = "KTX";
		}
		driver = scan.next();
	}

	@Override
	public void print() {
		print(true);
	}

	@Override
	public void print(boolean bDetail) {
		if (bDetail) { // 기차종류 조회 - 고객조회
			System.out.printf("기차 정보: %s\n", type);
		} else { // 기차 정보 전체조회 - 관리자 조회
			System.out.printf("TrainId: %d\n ", num);
			System.out.printf("기차 정보: %s %s %s\n", id, type, driver);
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
		System.out.println("(1)기차 코드 (2) 기차 타입 (3)기차 기사 (0)종료  >>");
		int num = scan.nextInt();
		switch (num) {
		case 0:
			return;
		case 1:
			System.out.print("코드:");
			id = scan.next();
			/*
			 * for(Schedule s: Administer.scheduleManager.mList) { for(Train t:
			 * Administer.trainManager.mList) { if(!t.id.equals(s.vehicleId)) s.vehicleId =
			 * id; } }
			 */
			break;
		case 2:
			System.out.print("기차 타입 (KTX or SRT or 무궁화호  중 택1): ");
			type = scan.next();
			break;
		case 3:
			System.out.println("기차 기사이름: ");
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
