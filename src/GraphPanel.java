import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	public DataDiagram MainProgram() {
		PlannerProcesses planner = new PlannerProcesses();
		Random rnd = new Random();
		int countProcess = rnd.nextInt(5) + 1;
		// ������� ��������
		for (int i = 0; i < countProcess; i++) {
			Process process = new Process(i + 1, rnd.nextInt(3) + 1);
			planner.addProcess(process);
		}
		DataDiagram dataDiagam = new DataDiagram();
		planner.setDiagram(dataDiagam);
		planner.runProcesses();
		return dataDiagam;
	}
	
	@Override
	public void paint(Graphics g) {
		// ��������� �������� ��������� � �������� �� ��� ������
		DataDiagram dataDiagam = MainProgram();
		// �������� ������ ����� �� ��������
		ArrayList<PointDataDiagram> arrayListData = dataDiagam.GetArrayList();
		
		int curIdProcess = ((PointDataDiagram) arrayListData.get(0)).idProcess;
		for(int i=0; i < arrayListData.size(); i++)
		{
			// ���� ������� ��������: ������� �������� �������� �� ����� �����������
			if (curIdProcess != arrayListData.get(i).idProcess) {
				// ������ ������� ���������� ��������
				g.setColor(Color.BLACK);
				for (int j = 0; j < 900; j += 20)
					g.drawLine((i - 1) * 10 + 10, j, i * 10, j + 10);
				// ������������� ����� id
				curIdProcess = arrayListData.get(i).idProcess;
			}
			
			// ������ ����� ��������
			g.setColor(Color.RED);
			int startYProcess = (curIdProcess-1)*120 + 20;
			g.drawLine(i*10, startYProcess, i*10+10, startYProcess);
			
			// ������ ����� ������
			g.setColor(Color.BLUE);
			int curIdThread = ((PointDataDiagram) arrayListData.get(i)).idThread;
			g.drawLine(i*10, startYProcess + curIdThread*10, i*10+10, startYProcess + curIdThread*10);
		}
	}
}