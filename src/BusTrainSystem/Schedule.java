package BusTrainSystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import mgr.Manageable;

public class Schedule implements Manageable {
	int id;
	public String departure; // �����
	public String destination; // ������
	Date departureTime; // ��߽ð�
	Date arrivalTime; // �����ð�
	public Vehicle vehicle;
	public int price; // ����
	public int leftSeat; // �����ڸ�
	public int movingTime[]; // �̵��ð�

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
				System.out.println("��ã�� " + vehicleId);
			}

		} else {
			v = (Train) v;
			v = Administer.trainManager.find(vehicleId);
			if (v == null) {
				System.out.println("��ã�� " + vehicleId);
			}
		}
		vehicle = v;
	}

	public boolean checkVehicle() {
		if (vehicle instanceof Bus) {
			return true; // Bus�� true
		} else {
			return false; // Train�̸� false
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� E���� a hh:mm");
		if (bDetail) {
			System.out.printf("\n�����: %s   |  ������: %s\n", departure, destination); // �����: ���� ������: �λ�
			vehicle.print();
			System.out.println("��߽ð�: " + dateFormat.format(departureTime));
			System.out.println("�����ð�: " + dateFormat.format(arrivalTime));
			System.out.printf("����: %d�� ", price);
			System.out.printf("�����ڸ�: %d��\n", leftSeat);
			System.out.printf("�ҿ�ð�: %d�ð� %d��\n", movingTime[0], movingTime[1]);

		} else {
			System.out.printf("\nScheduleId: %d\n", id);
			vehicle.print(false);
			System.out.printf("�����: %s   |  ������: %s\n", departure, destination); // �����: ���� ������: �λ�
			System.out.println("��߽ð�: " + dateFormat.format(departureTime));
			System.out.println("�����ð�: " + dateFormat.format(arrivalTime));
			System.out.printf("����: %d�� ", price);
			System.out.printf("�����ڸ�: %d��\n", leftSeat);
			System.out.printf("�ҿ�ð�: %d�ð� %d��\n", movingTime[0], movingTime[1]);
		}
	}

	@Override
	public boolean matches(String kwd) {
		if (kwd.length() >= 2 && departure.contains(kwd))
			return true;
		if (kwd.length() >= 2 && destination.contains(kwd))
			return true;
		if (isNumeric(kwd) && price < Integer.parseInt(kwd)) // ��� �ִ� ��� Ƽ���� ã�´�.
			return true;
		if (("" + leftSeat).equals(kwd)) // �¼� ���� ��ġ�ϸ�
			return true;
		if (vehicle.matches(kwd))
			return true;

		return false;
	}

	@Override
	public boolean matches(String[] kwdArr) { // ||�� �ƴ� && ���� �˻��� ��θ� �����ϴ� ���� ã��ʹ�
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

	public boolean isAvailable() { // �̿밡���� �¼��� �����ִ���
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

		if (subMinute < 0) { // �г��� ������ ������ �Ǵ� ���
			--subHours; // �̸� 1�ð� ���� 1�ð��� 60������ �Ѱ��ִ� ȿ��
			if (arrivalTime.getHours() == 0) {
				subHours += 24;
			}
			subMinute += 60; // 60�� ���� �� ���� ���߱�
		} else { // �� ������ ��� or 0
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
			System.out.println("������ ���ϴ� �ʵ�  �Է�");
			System.out.print("(1)����� (2)������ (3)��߽ð� (4)�����ð� (5)�̵����� (6)���� (7)�����ڸ�  (0)����>> ");
			int num = scan.nextInt();
			Calendar cal = null;
			switch (num) {
			case 0:
				return;
			case 1:
				System.out.print("�����: ");
				departure = scan.next();
				break;
			case 2:
				System.out.println("������: ");
				destination = scan.next();
				break;
			case 3:
				System.out.println("�� �� �� �� �� ������� �����ؼ� �Է�");
				System.out.print("��߽ð�: ");
				departureTime = readTime(scan, cal);
				break;
			case 4:
				System.out.println("�� �� �� �� �� ������� �����ؼ� �Է�");
				System.out.print("�����ð�: ");
				arrivalTime = readTime(scan, cal);
				break;
			case 5:
				System.out.print("������ �ڵ� �Է�: ");
				String kwd = scan.next();
				vehicle = Administer.busManager.find(kwd);
				break;
			case 6:
				System.out.print("����: ");
				price = scan.nextInt();
				break;
			case 7:
				System.out.print("�����ڸ�: ");
				leftSeat = scan.nextInt();
				break;
			default:
				System.out.println("�Է� ����");
				break;
			}
		}
	}

	public String[] getUiTexts() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� E���� a hh:mm");
		String[] texts = new String[10];

		texts[0] = departure;
		texts[1] = destination;
		texts[2] = "" + dateFormat.format(departureTime);
		texts[3] = "" + dateFormat.format(arrivalTime);
		texts[4] = "" + price;
		texts[5] = "" + leftSeat;
		texts[6] = vehicle.type;
		texts[7] = movingTime[0] + "�ð� " + movingTime[1] + "��";

		return texts;
	}

}
