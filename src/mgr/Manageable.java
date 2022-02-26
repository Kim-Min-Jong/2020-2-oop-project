package mgr;

import java.util.Scanner;

public interface Manageable {
	void read(Scanner scan);

	void print();

	void print(boolean bDetail);

	boolean matches(String kwd);

	boolean matches(String[] kwdArr);

	void set(Scanner scan);
}
