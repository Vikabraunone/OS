import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PanelMemory extends JPanel {
	private int[] arrBlocks;
	int countBlocks;
	int rowCount = 10;

	public PanelMemory(int countBlocks) {
		this.countBlocks = countBlocks;
		arrBlocks = new int[countBlocks];
		for (int i = 0; i < countBlocks; i++)
			arrBlocks[i] = -1;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int j = 0;
		for (int i = 0; i < countBlocks; i++) {
			if (i > 10 && i % rowCount == 0)
				j++;
			if (arrBlocks[i] == -1) {
				g.setColor(new Color(77, 77, 77));
			} else {
				g.setColor(new Color(0, 0, 77));
			}
			g.fillRect((i % rowCount) * 20, j * 20, 20, 20);
			g.setColor(new Color(0, 0, 0));
			g.drawRect((i % rowCount) * 20, j * 20, 20, 20);

		}
	}

	public void setValues(ArrayList<Integer> blocks) {
		clear();
		for (int i = 0; i < blocks.size(); i++)
			arrBlocks[blocks.get(i)] = 1;
	}

	private void clear() {
		for (int i = 0; i < countBlocks; i++)
			arrBlocks[i] = -1;
	}
}
