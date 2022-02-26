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
			type = "����ȭȣ";
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
		if (bDetail) { // �������� ��ȸ - ����ȸ
			System.out.printf("���� ����: %s\n", type);
		} else { // ���� ���� ��ü��ȸ - ������ ��ȸ
			System.out.printf("TrainId: %d\n ", num);
			System.out.printf("���� ����: %s %s %s\n", id, type, driver);
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
		System.out.println("������ ���ϴ� �ʵ�  �Է�");
		System.out.println("(1)���� �ڵ� (2) ���� Ÿ�� (3)���� ��� (0)����  >>");
		int num = scan.nextInt();
		switch (num) {
		case 0:
			return;
		case 1:
			System.out.print("�ڵ�:");
			id = scan.next();
			/*
			 * for(Schedule s: Administer.scheduleManager.mList) { for(Train t:
			 * Administer.trainManager.mList) { if(!t.id.equals(s.vehicleId)) s.vehicleId =
			 * id; } }
			 */
			break;
		case 2:
			System.out.print("���� Ÿ�� (KTX or SRT or ����ȭȣ  �� ��1): ");
			type = scan.next();
			break;
		case 3:
			System.out.println("���� ����̸�: ");
			driver = scan.next();
			break;
		default:
			System.out.println("�Է¿���!");
		}
	}

	public boolean matcheId(int kwdId) {
		if (kwdId != num) {
			return false;
		}
		return true;
	}
}
