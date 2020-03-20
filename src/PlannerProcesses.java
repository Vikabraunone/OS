import java.util.ArrayList;

// класс планировщика
public class PlannerProcesses {
	private ArrayList<Process> processes = new ArrayList<>();
	public static final int quant = 10;
	private DataDiagram dataArrayList;
	
	public void addProcess(Process process) {
		processes.add(process);
	}
	
	public void setDiagram(DataDiagram dataArrayList) {
		this.dataArrayList = dataArrayList;
	}
	
	public void runProcesses() {
		int idProcess = 0;
		while (!processes.isEmpty()) {
			processes.get(idProcess).setMaxTime(quant);
			processes.get(idProcess).RunThreads(dataArrayList);
			if (processes.get(idProcess).ProcessIsComplete()) {
				processes.remove(idProcess);
				if (idProcess >= processes.size())
					idProcess = 0;
			}
			else
			{
				idProcess = (idProcess+1) % processes.size();
			}
		}
		return;
	}
}