import java.util.ArrayList;
import java.util.Random;

public class MainProgram {
	DataDiagram dataDiagram;

	public MainProgram() {
		dataDiagram = new DataDiagram();
		PlannerProcesses planner = new PlannerProcesses();
		Random rnd = new Random();
		int countProcess = rnd.nextInt(5) + 1;
		for (int i = 0; i < countProcess; i++) {
			Process process = new Process(i + 1, rnd.nextInt(3) + 1);
			planner.addProcess(process);
			planner.setTableTime(process, dataDiagram);
		}
		planner.runProcesses();
	}
	
	public ArrayList<PointDataDiagram> getArrayList() {
		return dataDiagram.GetArrayList();
	}
}
