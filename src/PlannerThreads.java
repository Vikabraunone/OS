import java.util.ArrayList;
import java.util.Random;

public class PlannerThreads {
	private int countThreads;
	private ArrayList<Thread> threads;
	private int pId;
	private DataDiagram tableTime;

	public PlannerThreads(int pId) {
		this.pId = pId;
		Random rnd = new Random();
		this.countThreads = rnd.nextInt(5) + 1;
		threads = new ArrayList<Thread>();
		for (int i = 0; i < countThreads; i++)
			threads.add(new Thread(i + 1, rnd.nextInt(20) + 1, rnd.nextInt(3) + 1, pId));
	}

	private int idThread = -1;
	private int currentThreadMaxTime = -1;

	public void RunThreads(int maxTimeProcess) {
		if (idThread == -1) {
			idThread = (idThread + 1) % countThreads;
			currentThreadMaxTime = this.giveMaxTimeThread(threads.get(idThread).getPriority());
		}
		Thread curThread = threads.get(idThread);
		while (maxTimeProcess > 0) {
			maxTimeProcess--;
			curThread.timeDecrease();
			setState();
			currentThreadMaxTime--;
			if (curThread.threadIsExecute()) {
				threads.remove(idThread);
				if (threads.isEmpty()) return;
				if (idThread >= threads.size()) {
					idThread = 0;
				}
				currentThreadMaxTime = this.giveMaxTimeThread(threads.get(idThread).getPriority());
				curThread = threads.get(idThread);
			} else if (currentThreadMaxTime == 0) {
				idThread = (idThread + 1) % threads.size();
				currentThreadMaxTime = this.giveMaxTimeThread(threads.get(idThread).getPriority());
				curThread = threads.get(idThread);
			}
		}
	}

	private int giveMaxTimeThread(int priority) {
		if (priority == 1)
			return 7;
		else if (priority == 2)
			return 5;
		else
			return 3;
	}

	public void setTableTime(DataDiagram tableTime) {
		this.tableTime = tableTime;
	}

	private void setState() {
		tableTime.AddData(pId, idThread+1);
	}
	
	public boolean processIsComplete() {
		if (threads.isEmpty()) return true;
		return false;
	}
}
