// класс потока
public class Thread {
	private int id;
	private int runtimeThread;
	private int priorityThread;
	private int idProcess;
	
	public Thread(int id, int runtime, int priority, int idProcess) {
		this.id = id;
		this.runtimeThread = runtime;
		this.priorityThread = priority;
		this.idProcess = idProcess;
	}
	
	public int getId() {
		return id;
	}
	
	public int getRuntime() {
		return runtimeThread;
	}
	
	public int getPriority() {
		return priorityThread;
	}
	
	// поток завершил выполнение?
	public boolean threadIsExecute() {
		if (runtimeThread <= 0)
			return true;
		return false;
	}
	
	// уменьшаем время выполнения потока
	public void timeDecrease(){
		runtimeThread--;
	}
	
	public void writeDiagram(DataDiagram dataArrayList) {
		PointDataDiagram data = new PointDataDiagram();
		data.idThread = id;
		data.idProcess = idProcess;
		dataArrayList.AddData(data);
	}
}