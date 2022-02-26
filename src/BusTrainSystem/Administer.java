package BusTrainSystem;

import java.util.Scanner;

import mgr.Factory;
import mgr.Manager;

public class Administer {
	static Manager<Bus> busManager = new Manager<>();
	static Manager<Train> trainManager = new Manager<>();
	public static Manager<Schedule> scheduleManager = new Manager<>();
	Scanner scan = new Scanner(System.in);
	private static Administer a = null;

	private Administer() {
	};

	public static Administer getInstance() {
		if (a == null)
			a = new Administer();
		return a;
	}

	public void run(boolean checkGettingData, boolean checkPlayAdminister) {

		if (checkGettingData) {
			busManager.readAll("buses.txt", new Factory<Bus>() {
				public Bus create() {
					return new Bus();
				}
			});
			trainManager.readAll("trains.txt", new Factory<Train>() {
				public Train create() {
					return new Train();
				}
			});
			scheduleManager.readAll("schedules.txt", new Factory<Schedule>() {
				public Schedule create() {
					return new Schedule();
				}
			});
		}
		// ������
		if (checkPlayAdminister == true) {
			while (true) {
				int num = this.inputOuterMenu();
				this.inputInnerMenu(num);
				if (num == 0)
					break;
			}
		}

	}

	int inputOuterMenu() {
		int num;
		while (true) {
			System.out.println("------��ü�޴�------");
			System.out.print("(1)������ (2)���� (3)���� (0)���� (����� ������ ����Ʈ�� �ٷ� ���) >> ");
			num = scan.nextInt();
			if (num < 0 || num > 3) {
				System.out.println("�� ���Է��߽��ϴ�. �ٽ� �Է��ϼ���.");
				continue;
			}
			return num;
		}
	}

	void inputInnerMenu(int outerNum) {
		int num;
		while (true) {
			if (outerNum == 0)
				break;
			switch (outerNum) {
			case 1:
				System.out.println("\n������ �޴� ");
				System.out.print("(1)��ü ��ȸ ");
				System.out.print("(2)������ ���� ");
				System.out.print("(3)������ ���� ");
				System.out.print("(4)������ ������ȸ ");
				System.out.print("(5)������ ��Ƽ��ȸ ");
				System.out.print("(0)�������� ");
				System.out.print(">> ");
				num = scan.nextInt();
				boolean check = false;
				int id = 0;
				Schedule s = null;
				switch (num) {
				case 0:
					return;
				case 1:
					System.out.println("\n----------��ĳ�� ��ü ��ȸ----------");
					scheduleManager.printAll(false);
					break;
				case 2:
					System.out.print("\n������ ������ id �Է�(���ڸ�): ");
					id = scan.nextInt();
					for (Schedule schedule : scheduleManager.mList) {
						if (schedule.matcheId(id)) {
							s = schedule;
						}
					}
					check = scheduleManager.remove(s);

					if (check) {
						System.out.println("���� ����");
					} else {
						System.out.println("���� ����");
					}
					break;
				case 3:
					System.out.print("\n������ ������ id �Է�(���ڸ�): ");
					id = scan.nextInt();
					for (Schedule schedule : scheduleManager.mList) {
						if (schedule.matcheId(id)) {
							s = schedule;
						}
					}
					check = scheduleManager.update(s, scan);

					if (check) {
						System.out.println("��������");
					} else {
						System.out.println("���� ����");
					}
					break;
				case 4:
					scheduleManager.search(scan);
					break;
				case 5:
					scheduleManager.multiSearch(scan);
					break;
				default:
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					break;
				}
				break;
			case 2:
				System.out.println("���������� �޴�");
				System.out.print("(1)��ü ��ȸ ");
				System.out.print("(2)�������� ���� ");
				System.out.print("(3)�������� ���� ");
				System.out.print("(4)�������� �˻� ");
				System.out.print("(0)��������>>");
				num = scan.nextInt();
				Bus b = null;
				switch (num) {
				case 0:
					return;
				case 1:
					System.out.println("---------�������� ��ȸ---------");
					busManager.printAll(false);
					break;
				case 2:
					System.out.print("\n������ ���� id �Է�: ");
					id = scan.nextInt();
					for (Bus bus : busManager.mList) {
						if (bus.matcheId(id)) {
							b = bus;
						}
					}

					check = busManager.remove(b);

					if (check) {
						System.out.println("���� ����");
					} else {
						System.out.println("���� ����");
					}
					break;
				case 3:
					System.out.print("\n������ ������ id �Է�(���ڸ�): ");
					id = scan.nextInt();
					for (Bus bus : busManager.mList) {
						if (bus.matcheId(id)) {
							b = bus;
						}
					}
					check = busManager.update(b, scan);

					if (check) {
						System.out.println("���� ����");
					} else {
						System.out.println("���� ����");
					}
					break;
				case 4:
					busManager.search(scan);
				default:
					for (Schedule s3 : Administer.scheduleManager.mList)
						s3.print(false);
				}
				break;
			case 3:
				System.out.println("���������� �޴�");
				System.out.print("(1)��ü ��ȸ ");
				System.out.print("(2)�������� ���� ");
				System.out.print("(3)�������� ���� ");
				System.out.print("(4)�������� �˻� ");
				System.out.print("(0)��������>>");
				num = scan.nextInt();
				Train t = null;
				boolean check2 = true;
				switch (num) {
				case 0:
					return;
				case 1:
					System.out.println("---------�������� ��ȸ---------");
					trainManager.printAll(false);
					break;
				case 2:
					System.out.print("\n������ ���� id �Է�(���ڸ�): ");
					id = scan.nextInt();
					for (Train train : trainManager.mList) {
						if (train.matcheId(id)) {
							t = train;
						}
					}
					check = trainManager.remove(t);

					if (check && check2) {
						System.out.println("���� ����");
					} else {
						System.out.println("���� ����");
					}
					break;
				case 3:
					System.out.print("\n������ ������ id �Է�(���ڸ�): ");
					id = scan.nextInt();
					for (Train train : trainManager.mList) {
						if (train.matcheId(id)) {
							t = train;
						}
					}
					check = trainManager.update(t, scan);

					if (check) {
						System.out.println("���� ����");
					} else {
						System.out.println("���� ����");
					}
					break;
				case 4:
					trainManager.search(scan);
					break;
				}
				break;
			default:
				System.out.println("�߸��Է��Ͽ����ϴ�!");
				break;
			}
		}
	}

	public static void main(String[] args) {
		Administer main = new Administer();
		main.run(true, true);
	}

}
