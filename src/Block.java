public class Block {
	public static int SizeBlock;
	private int data = 0;
	private int id;
	
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
}
