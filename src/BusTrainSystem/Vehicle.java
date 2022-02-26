package BusTrainSystem;

import mgr.Manageable;

public abstract class Vehicle implements Manageable {
	String id; // 버스 번호
	public String type; // 버스 종류. 일반: 0, 우등: 1
	String driver;
}
