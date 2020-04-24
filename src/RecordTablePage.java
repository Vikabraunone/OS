public class RecordTablePage {
	public int id;
	// страница в физической памяти 
	public int PageFrameNumber = -1;
	
	// бит присутствия в памяти
	public boolean Existence = false;
	
	// бит ссылки/бит обращения
	public boolean[] R = {false, false, false, false};
	
	public boolean[] GetBitsR()
	{
		return R;
	}
}