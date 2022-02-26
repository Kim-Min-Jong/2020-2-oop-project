package ListCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class hourMinute extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;
	// desired format for the date
	private SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");

	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Object item = value;

		// if the item to be rendered is date then format it
		if (item instanceof Date) {
			item = dateFormat.format((Date) item);
		}

		Component c = super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
		if (isSelected) {
			c.setBackground(Color.ORANGE);
			c.setForeground(Color.red);
		}
		return c;
	}
}
