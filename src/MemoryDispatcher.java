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
		// алгоритм старения
		AlgorithmOfAging(idVirtualPage);
		int frameIndex = tablePages.GetRecord(idVirtualPage).PageFrameNumber;
		System.out.println("Виртуальная страница: " + idVirtualPage);
		System.out.println("Физический адрес: " + memory.GetFrameAddress(frameIndex, getShift(virtualAddress)));
		printTables();
	}

	private void AlgorithmNFU(int id) {
		RecordTablePage page = tablePages.GetRecord(id);
		int minCount = 1;
		int minIndex = -1;
		for (int i = 1; i < TablePages.bitRLength; i++)
			minCount = minCount * 10 + 1;
		for (int i = 0; i < tablePages.GetTable().size(); i++)
			if (tablePages.GetRecord(i).Existence && getCountTrueR(tablePages.GetRecord(i)) < minCount) {
				minCount = getCountTrueR(tablePages.GetRecord(i));
				minIndex = i;
			}
		// если страница использовалась
		if (minCount != 0) {
			for (int i = 0; i < tablePages.GetTable().size(); i++) {
				if (minIndex == i)
					continue;
				if (tablePages.GetRecord(i).Existence && getCountTrueR(tablePages.GetRecord(i)) == minCount) {
					minIndex = getPageLongTimeAgoR(tablePages.GetRecord(i), tablePages.GetRecord(i));
				}
			}
		}
		RecordTablePage minPage = tablePages.GetRecord(minIndex);
		page.PageFrameNumber = minPage.PageFrameNumber;
		page.Existence = true;
		minPage.Existence = false;
		minPage.PageFrameNumber = -1;
	}

	private void AlgorithmOfAging(int idPage) {
		for (RecordTablePage page : tablePages.GetTable()) {
			if (!page.Existence)
				continue;
			page.R /= 10;
			if (page.id == idPage)
				page.R += Math.pow(10, TablePages.bitRLength - 1);
		}
	}

	private int getIdVirtualPage(int virtualAddress) {
		return virtualAddress / tablePages.GetSizeOfPage();
	}

	private int getShift(int virtualAddress) {
		return virtualAddress % tablePages.GetSizeOfPage();
	}

	private int getCountTrueR(RecordTablePage page) {
		int count = 0;
		int bufR = page.R;
		while (bufR > 0) {
			if (bufR % 10 == 1)
				count++;
			bufR /= 10;
		}
		return count;
	}

	private int getPageLongTimeAgoR(RecordTablePage minPage, RecordTablePage page) {
		return minPage.R <= page.R ? minPage.id : page.id;
	}

	private void printTables() {
		System.out.println("Info");
		for (int i = 0; i < tablePages.GetTable().size(); i++)
			System.out.println("Id: " + tablePages.GetRecord(i).id + " | Физическая страница: "
					+ tablePages.GetRecord(i).PageFrameNumber);

	}
}
