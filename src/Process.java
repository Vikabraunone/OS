public class Process {
	private int id;
	private int priority;
	private int maxTimeProcess;
	int quant = 10;
	
	private PlannerThreads plannerThreads;

	public Process(int id, int priority) {
		this.id = id;
		this.priority = priority;
		plannerThreads = new PlannerThreads(id);
	}

	public void setMaxTime(int quant) {
		if (this.priority == 1)
			this.maxTimeProcess = quant + 7;
		else if (this.priority == 1)
			this.maxTimeProcess = quant + 3;
		else
			this.maxTimeProcess = quant;
	}

	public int getPriority() {
		return priority;
	}

	public int getId() {
		return id;
	}

	public void Run() {
		plannerThreads.RunThreads(maxTimeProcess);
	}

	public boolean processIsComplete() {
		if (plannerThreads.processIsComplete()) return true;
		else return false;
	}
	
	public void setTableTime(DataDiagram tableTime) {
		plannerThreads.setTableTime(tableTime);
	}
}