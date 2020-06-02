package FileSystemLogic;
public class FileAllocationTable {
	private int[] fat;
	
	public FileAllocationTable(int size) {
		fat = new int[size];
		for(int i =0; i< fat.length; i++)
			fat[i] = -1;
	}
	
	public void setBlock(int idBlock, int idNextBlock) {
		fat[idBlock] = idNextBlock;
	}
	
	public void deleteBlock(int idBlock) {
		fat[idBlock] = -1;
	}
	
	public int getBlock(int idBlock)
	{
		return fat[idBlock];
	}
	
	public int size()
	{
		return fat.length;
	}
}