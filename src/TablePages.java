import java.util.ArrayList;

public class TablePages {
	private ArrayList<RecordTablePage> tablePages;
	private int sizeOfTable;
	private int sizeOfPage;
	public static int bitRLength = 8;

	public TablePages(int sizeOfTable, int sizeOfPage) {
		this.sizeOfTable = sizeOfTable;
		this.sizeOfPage = sizeOfPage;
		tablePages = new ArrayList<RecordTablePage>(sizeOfTable / sizeOfPage);
		for (int i = 0; i < sizeOfTable / sizeOfPage; i++) {
			RecordTablePage rec = new RecordTablePage();
			rec.id = i;
			tablePages.add(rec);
		}
	}

	public RecordTablePage GetRecord(int index) {
		return tablePages.get(index);
	}

	public ArrayList<RecordTablePage> GetTable() {
		return tablePages;
	}

	public int GetSizeOfPage()
	{
		return sizeOfPage;
	}
}