package BusTrainSystem;

import mgr.Manageable;

public abstract class Vehicle implements Manageable {
	String id; // ���� ��ȣ
	public String type; // ���� ����. �Ϲ�: 0, ���: 1
	String driver;
}
