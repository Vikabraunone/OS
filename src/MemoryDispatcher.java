public class MemoryDispatcher {
	private TablePages tablePages;

	private PhysicalMemory memory;

	public MemoryDispatcher(int sizeOfRAM, int sizeOfPage) {
		tablePages = new TablePages(2 * sizeOfRAM, sizeOfPage);
		memory = new PhysicalMemory(sizeOfRAM, sizeOfPage);
		System.out.println("Диапазон виртуальных адресов: 0 .. " + (2 * sizeOfRAM - 1));
	}

	public void Run(int virtualAddress) {
		int idVirtualPage = getIdVirtualPage(virtualAddress);
		// если страница не представлена в физической памяти
		if (tablePages.GetRecord(idVirtualPage).Existence == false) {
			if (memory.GetCountPages() == memory.GetLastIndex() + 1)
				AlgorithmNFU(idVirtualPage);
			else {
				memory.WritePage();
				tablePages.GetRecord(idVirtualPage).PageFrameNumber = memory.GetLastIndex();
				tablePages.GetRecord(idVirtualPage).Existence = true;
			}
		}
		tablePages.GetRecord(idVirtualPage).R++;
		int frameIndex = tablePages.GetRecord(idVirtualPage).PageFrameNumber;
		System.out.println("Виртуальная страница: " + idVirtualPage);
		System.out.println("Физический адрес: " + memory.GetFrameAddress(frameIndex, getShift(virtualAddress)));
		printTables();
	}

	private void AlgorithmNFU(int id) {
		RecordTablePage page = tablePages.GetRecord(id);
		int minCount = -1;
		int minIndex = -1;
		for (int i = 0; i < tablePages.GetTable().size(); i++)
			if (tablePages.GetRecord(i).Existence && (tablePages.GetRecord(i).R < minCount || minCount == -1)) {
				minCount = tablePages.GetRecord(i).R;
				minIndex = i;
			}
		RecordTablePage minPage = tablePages.GetRecord(minIndex);
		page.PageFrameNumber = minPage.PageFrameNumber;
		page.Existence = true;
		minPage.Existence = false;
		minPage.PageFrameNumber = -1;
		minPage.R = 0;
	}

	private int getIdVirtualPage(int virtualAddress) {
		return virtualAddress / tablePages.GetSizeOfPage();
	}

	private int getShift(int virtualAddress) {
		return virtualAddress % tablePages.GetSizeOfPage();
	}

	private void printTables() {
		System.out.println("Info");
		for (int i = 0; i < tablePages.GetTable().size(); i++)
			System.out.println("Id: " + tablePages.GetRecord(i).id + " | Физическая страница: "
					+ tablePages.GetRecord(i).PageFrameNumber + " | R: " + tablePages.GetRecord(i).R);
	}
}
