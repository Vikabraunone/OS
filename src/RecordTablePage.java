public class RecordTablePage {
	public int id;
	// �������� � ���������� ������ 
	public int PageFrameNumber = -1;
	
	// ��� ����������� � ������
	public boolean Existence = false;
	
	// ��� ������/��� ���������
	public boolean[] R = {false, false, false, false};
	
	public boolean[] GetBitsR()
	{
		return R;
	}
}