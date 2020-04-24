public class RecordTablePage {
	public int id;
	// страница в физической памяти 
	public int PageFrameNumber = -1;
	
	// бит присутствия в памяти
	public boolean Existence = false;
	
	// бит ссылки/бит обращения
	public int R = 0;
}