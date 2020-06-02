package FileSystemLogic;
//дисковой раздел
public class Disk {
	private Block[] blocks;
	private int diskSize;

	public Disk(int diskSize, int sizeSector) {
		Block.SizeBlock = sizeSector;
		this.diskSize = diskSize;
		blocks = new Block[diskSize / sizeSector];
		for (int i = 0; i < blocks.length; i++)
			blocks[i] = new Block(i);
	}

	public void writeDataIntoSector(int index, int data) {
		if (index >= 0 && index < blocks.length) {
			blocks[index].writeData(data);
		}
	}

	public int readDataFromSector(int index) {
		if (index >= 0 && index < blocks.length) {
			return blocks[index].readData();
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
}
