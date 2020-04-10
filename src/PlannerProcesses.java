import java.util.ArrayList;

// класс планировщика
public class PlannerProcesses {
	private ArrayList<Process> processes = new ArrayList<>();
	public static final int quant = 10;
	
	public void addProcess(Process process) {
		processes.add(process);
		process.setMaxTime(quant);
	}
	
	public void setTableTime(Process process, DataDiagram tableTime) {
		process.setTableTime(tableTime);
	}
	
	public void runProcesses() {
		int idProcess = 0;
		while (!processes.isEmpty()) {
			processes.get(idProcess).Run();
			if (processes.get(idProcess).processIsComplete()) {
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