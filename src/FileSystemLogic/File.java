package FileSystemLogic;

import java.util.ArrayList;

public class File {
	private String name;
	private String extension;
	private int size;
	private Link head;
	private Link tail;
	
	public File(String name, String extension, int idBlock, int size)
	{
		this.name = name;
		this.extension = extension;
		this.size = size;
		head = new Link();
		head.idBlock = idBlock;
		head.nextBlock = null;
		tail = head;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getExtension()
	{
		return extension;
	}
	
	public void addBlock(int idBlock)
	{
		Link item = new Link();
		item.idBlock = idBlock;
		item.nextBlock = null;
		if (tail == head)
		{
			tail = item;
			head.nextBlock = tail;
		}
		else
		{
			tail.nextBlock = item;
			tail = tail.nextBlock;
		}
	}
	
	public ArrayList<Integer> getBlocks()
	{
		ArrayList<Integer> blocks = new ArrayList<Integer>();
		Link item = new Link();
		item.idBlock = head.idBlock;
		item.nextBlock = head.nextBlock;
		while(item != tail)
		{
			blocks.add(item.idBlock);
			item = item.nextBlock;
		}
		blocks.add(tail.idBlock);
		return blocks;
	}
	
	public int getSize()
	{
		return size;
	}
}