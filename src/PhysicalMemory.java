import java.util.ArrayList;

public class PhysicalMemory {
	private ArrayList<Boolean> blocks;
	private int sizeOfRAM;
	private int sizeOfPage;
	private int index = -1;

	public PhysicalMemory(int sizeOfRAM, int sizeOfPage) {
		this.sizeOfRAM = sizeOfRAM;
		this.sizeOfPage = sizeOfPage;
		blocks = new ArrayList<Boolean>(sizeOfRAM / sizeOfPage);
		for (int i = 0; i < sizeOfRAM / sizeOfPage; i++)
			blocks.add(false);
	}

	public void WritePage() {
		index++;
		blocks.set(index, true);
	}
	
	public int GetLastIndex() {
		return index;
	}

	public int GetCountPages() {
		return blocks.size();
	}

	public int GetFrameAddress(int indexPage, int shift) {
		return indexPage * sizeOfPage + shift;
	}
}