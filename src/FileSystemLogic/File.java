package FileSystemLogic;
public class File {
	private String name;
	private String extension;
	private int size;
	private int firstBlock;
	
	public File(String name, String extension, int firstBlock, int size)
	{
		this.name = name;
		this.extension = extension;
		this.firstBlock = firstBlock;
		this.size = size;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getExtension()
	{
		return extension;
	}
	
	public int getFirstBlock()
	{
		return firstBlock;
	}
	
	public int getSize()
	{
		return size;
	}
}