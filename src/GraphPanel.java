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
		// создаем процессы
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
		// запускаем основную программу и получаем из нее данные
		DataDiagram dataDiagam = MainProgram();
		// получаем список точек на диаграме
		ArrayList<PointDataDiagram> arrayListData = dataDiagam.GetArrayList();
		
		int curIdProcess = ((PointDataDiagram) arrayListData.get(0)).idProcess;
		for(int i=0; i < arrayListData.size(); i++)
		{
			// если процесс сменился: текущее значение процесса не равен полученному
			if (curIdProcess != arrayListData.get(i).idProcess) {
				// рисуем границы выполнения процесса
				g.setColor(Color.BLACK);
				for (int j = 0; j < 900; j += 20)
					g.drawLine((i - 1) * 10 + 10, j, i * 10, j + 10);
				// устанавливаем новый id
				curIdProcess = arrayListData.get(i).idProcess;
			}
			
			// рисуем линию процесса
			g.setColor(Color.RED);
			int startYProcess = (curIdProcess-1)*120 + 20;
			g.drawLine(i*10, startYProcess, i*10+10, startYProcess);
			
			// рисуем линию потока
			g.setColor(Color.BLUE);
			int curIdThread = ((PointDataDiagram) arrayListData.get(i)).idThread;
			g.drawLine(i*10, startYProcess + curIdThread*10, i*10+10, startYProcess + curIdThread*10);
		}
	}
}