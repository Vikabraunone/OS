import java.util.ArrayList;
import java.util.Random;

// класс процесса
public class Process {
	private int id;
	private int priority;
	private int maxTimeProcess;
	private int countThreads;
	private ArrayList<Thread> threads;
	private PlannerThreads plannerThreads;

	public Process(int id, int prioritet) {
		this.id = id;
		this.priority = prioritet;
		Random rnd = new Random();
		this.countThreads = rnd.nextInt(5) + 1;
		threads = new ArrayList<Thread>();
		// добавление потока в процесс
		for (int i = 0; i < countThreads; i++)
			threads.add(new Thread(i + 1, rnd.nextInt(20) + 1, rnd.nextInt(3) + 1, id)); // id, runtime, priority,
																							// idProcess
		plannerThreads = new PlannerThreads();
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

	public int getThreads() {
		return threads.size();
	}

	public int getId() {
		return id;
	}

	public void RunThreads(DataDiagram dataArrayList) {
		plannerThreads.RunThreads(dataArrayList);
	}

	public boolean ProcessIsComplete() {
		if (threads.isEmpty())
			return true;
		else
			return false;
	}

	private class PlannerThreads {
		// данные для восстановления потока после блокировки процесса
		private int idThread = -1;
		// данные для восстановления потока после блокировки процесса
		private int currentThreadMaxTime = -1;

		public void RunThreads(DataDiagram dataArrayList) {
			// если планировщик ещё не запускался, выбираем поток
			if (idThread == -1) {
				idThread = (idThread + 1) % countThreads;
				currentThreadMaxTime = this.giveMaxTimeThread(threads.get(idThread).getPriority());
			}
			Thread curThread = threads.get(idThread);
			while (maxTimeProcess > 0) {
				int max = maxTimeProcess;
				maxTimeProcess--;
				curThread.timeDecrease();
				// добавляем в диаграмме запись о потоке
				curThread.writeDiagram(dataArrayList);
				currentThreadMaxTime--;
				// если поток завершил выполнение или его время кончилось
				if (curThread.threadIsExecute()) {
					threads.remove(idThread);
					if (threads.isEmpty())
						return;
					// берем следующий поток - индекс след. потока равен текущему
					if (idThread >= threads.size())
						idThread = 0;
					// ArrayList thr = threads;
					currentThreadMaxTime = this.giveMaxTimeThread(threads.get(idThread).getPriority());
					curThread = threads.get(idThread);
				}
				// если макс. время потока кончилось
				else if (currentThreadMaxTime == 0) {
					// берем следующий поток
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
	}
}