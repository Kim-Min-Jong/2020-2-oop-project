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
		// 관리자
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
			System.out.println("------전체메뉴------");
			System.out.print("(1)스케줄 (2)버스 (3)기차 (0)종료 (종료시 수정된 리스트가 바로 출력) >> ");
			num = scan.nextInt();
			if (num < 0 || num > 3) {
				System.out.println("잘 못입력했습니다. 다시 입력하세요.");
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
				System.out.println("\n스케줄 메뉴 ");
				System.out.print("(1)전체 조회 ");
				System.out.print("(2)스케줄 삭제 ");
				System.out.print("(3)스케줄 수정 ");
				System.out.print("(4)스케줄 단일조회 ");
				System.out.print("(5)스케줄 멀티조회 ");
				System.out.print("(0)이전으로 ");
				System.out.print(">> ");
				num = scan.nextInt();
				boolean check = false;
				int id = 0;
				Schedule s = null;
				switch (num) {
				case 0:
					return;
				case 1:
					System.out.println("\n----------스캐줄 전체 조회----------");
					scheduleManager.printAll(false);
					break;
				case 2:
					System.out.print("\n삭제할 스케줄 id 입력(숫자만): ");
					id = scan.nextInt();
					for (Schedule schedule : scheduleManager.mList) {
						if (schedule.matcheId(id)) {
							s = schedule;
						}
					}
					check = scheduleManager.remove(s);

					if (check) {
						System.out.println("삭제 성공");
					} else {
						System.out.println("삭제 실패");
					}
					break;
				case 3:
					System.out.print("\n수정할 스케줄 id 입력(숫자만): ");
					id = scan.nextInt();
					for (Schedule schedule : scheduleManager.mList) {
						if (schedule.matcheId(id)) {
							s = schedule;
						}
					}
					check = scheduleManager.update(s, scan);

					if (check) {
						System.out.println("수정성공");
					} else {
						System.out.println("수정 실패");
					}
					break;
				case 4:
					scheduleManager.search(scan);
					break;
				case 5:
					scheduleManager.multiSearch(scan);
					break;
				default:
					System.out.println("잘못 입력하셨습니다.");
					break;
				}
				break;
			case 2:
				System.out.println("버스관리자 메뉴");
				System.out.print("(1)전체 조회 ");
				System.out.print("(2)버스정보 삭제 ");
				System.out.print("(3)버스정보 수정 ");
				System.out.print("(4)버스정보 검색 ");
				System.out.print("(0)이전으로>>");
				num = scan.nextInt();
				Bus b = null;
				switch (num) {
				case 0:
					return;
				case 1:
					System.out.println("---------버스정보 조회---------");
					busManager.printAll(false);
					break;
				case 2:
					System.out.print("\n삭제할 버스 id 입력: ");
					id = scan.nextInt();
					for (Bus bus : busManager.mList) {
						if (bus.matcheId(id)) {
							b = bus;
						}
					}

					check = busManager.remove(b);

					if (check) {
						System.out.println("삭제 성공");
					} else {
						System.out.println("삭제 실패");
					}
					break;
				case 3:
					System.out.print("\n수정할 스케줄 id 입력(숫자만): ");
					id = scan.nextInt();
					for (Bus bus : busManager.mList) {
						if (bus.matcheId(id)) {
							b = bus;
						}
					}
					check = busManager.update(b, scan);

					if (check) {
						System.out.println("수정 성공");
					} else {
						System.out.println("수정 실패");
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
				System.out.println("기차관리자 메뉴");
				System.out.print("(1)전체 조회 ");
				System.out.print("(2)기차정보 삭제 ");
				System.out.print("(3)기차정보 수정 ");
				System.out.print("(4)기차정보 검색 ");
				System.out.print("(0)이전으로>>");
				num = scan.nextInt();
				Train t = null;
				boolean check2 = true;
				switch (num) {
				case 0:
					return;
				case 1:
					System.out.println("---------기차정보 조회---------");
					trainManager.printAll(false);
					break;
				case 2:
					System.out.print("\n삭제할 기차 id 입력(숫자만): ");
					id = scan.nextInt();
					for (Train train : trainManager.mList) {
						if (train.matcheId(id)) {
							t = train;
						}
					}
					check = trainManager.remove(t);

					if (check && check2) {
						System.out.println("삭제 성공");
					} else {
						System.out.println("삭제 실패");
					}
					break;
				case 3:
					System.out.print("\n수정할 스케줄 id 입력(숫자만): ");
					id = scan.nextInt();
					for (Train train : trainManager.mList) {
						if (train.matcheId(id)) {
							t = train;
						}
					}
					check = trainManager.update(t, scan);

					if (check) {
						System.out.println("수정 성공");
					} else {
						System.out.println("수정 실패");
					}
					break;
				case 4:
					trainManager.search(scan);
					break;
				}
				break;
			default:
				System.out.println("잘못입력하였습니다!");
				break;
			}
		}
	}

	public static void main(String[] args) {
		Administer main = new Administer();
		main.run(true, true);
	}

}
