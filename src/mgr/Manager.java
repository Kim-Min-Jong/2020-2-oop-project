package mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager<T extends Manageable> {
	public ArrayList<T> mList = new ArrayList<>();

	public T find(String kwd) {
		for (T m : mList)
			if (m.matches(kwd))
				return m;
		return null;
	}

	public ArrayList<T> findMany(String kwd) {
		ArrayList<T> foundList = new ArrayList<>();
		for (T m : mList)
			if (m.matches(kwd))
				foundList.add(m);

		return foundList;
	}

	public ArrayList<T> findMany(String kwd, String kwd2) {
		// TODO Auto-generated method stub
		ArrayList<T> foundList = new ArrayList<>();
		for (T m : mList) {
			if (m.matches(kwd) && m.matches(kwd2))
				foundList.add(m);
		}
		return foundList;
	}

	public void readAll(String filename, Factory<T> fac) {
		Scanner filein = openFile(filename);
		T m = null;
		while (filein.hasNext()) {
			m = fac.create();
			m.read(filein);
			mList.add(m);
		}
		filein.close();
	}

	public void printAll() {
		printAll(true);
	}

	public void printAll(boolean bDetail) {
		if (bDetail) {
			for (T m : mList) {
				m.print();
				System.out.println();
			}
		} else {
			for (T m : mList) {
				m.print(false);
				System.out.println();
			}
		}
	}

	public void search(Scanner keyScanner) {
		String kwd = null;
		boolean check = false;
		System.out.println("Ż��: end");
		while (true) {
			System.out.print(">> ");
			kwd = keyScanner.next();
			if (kwd.equals("end"))
				break;
			for (T m : mList) {
				if (m.matches(kwd)) {
					m.print(false);
					check = true;
				}
			}
			if (!check) {
				System.out.println("�˻��� ����� �����ϴ�.");
			}
		}
	}

	public void multiSearch(Scanner scan) {
		String kwdArr[];
		String kwd;
		scan.nextLine();
		System.out.print("��ƼŰ���� �˻�: ");
		kwd = scan.nextLine();
		kwdArr = kwd.split(" ");
		ArrayList<T> foundList = findMany(kwdArr[0]);
		for (int i = 1; i < kwdArr.length; i++) {
			foundList = findInTempList(foundList, kwdArr[i]);
		}
		for (T m : foundList) {
			m.print(false);
		}
		foundList = null;
	}

	public ArrayList<T> findInTempList(ArrayList<T> tempList, String kwd) {
		// mList�� �ƴ� �Ű����� tempList���� kwd�� �´� �ν��Ͻ��� ã�� �� �迭�� �����ؼ� ������
		ArrayList<T> foundList = new ArrayList<>();
		for (T m : tempList) {
			if (m.matches(kwd)) {
				foundList.add(m);
			}
		}
		tempList = null;
		return foundList;
	}

	public boolean remove(T m) {
		if (m == null || !mList.contains(m))
			return false;
		mList.remove(m);
		return true;
	}

	public boolean update(T m, Scanner scan) {
		if (m == null) {
			return false;
		}
		m.set(scan);
		return true;
	}

	public Scanner openFile(String filename) {
		Scanner filein = null;
		try {
			filein = new Scanner(new File(filename));
		} catch (Exception e) {
			System.out.println(filename + ": ���� ����");
			System.exit(0);
		}
		return filein;
	}

}
