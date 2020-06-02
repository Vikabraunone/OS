package FileSystemLogic;

public class Block {
	public static int SizeBlock;
	private int data = -1;
	private int id = -1;
	
	public Block(int id)
	{
		this.id = id;
	}
	
	public void writeData(int data)
	{
		this.data = data;
	}
	
	public int readData()
	{
		return data;
	}
	
	public int idBlock()
	{
		return id;
	}
}
