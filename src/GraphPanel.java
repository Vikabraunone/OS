import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	@Override
	public void paint(Graphics g) {
		MainProgram program = new MainProgram();
		ArrayList<PointDataDiagram> arrayListData = program.getArrayList();
		
		int curIdProcess = ((PointDataDiagram) arrayListData.get(0)).idProcess;
		for(int i=0; i < arrayListData.size(); i++)
		{
			if (curIdProcess != arrayListData.get(i).idProcess) {
				g.setColor(Color.BLACK);
				for (int j = 0; j < 900; j += 20)
					g.drawLine((i - 1) * 10 + 10, j, i * 10, j + 10);
				curIdProcess = arrayListData.get(i).idProcess;
			}
			
			g.setColor(Color.RED);
			int startYProcess = (curIdProcess-1)*120 + 20;
			g.drawLine(i*10, startYProcess, i*10+10, startYProcess);
			

			g.setColor(Color.BLUE);
			int curIdThread = ((PointDataDiagram) arrayListData.get(i)).idThread;
			g.drawLine(i*10, startYProcess + curIdThread*10, i*10+10, startYProcess + curIdThread*10);
		}
	}
}