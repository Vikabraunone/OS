import java.util.ArrayList;

public class DataDiagram {
	ArrayList<PointDataDiagram> dataArrayList;
	
	public DataDiagram() {
		dataArrayList = new ArrayList<PointDataDiagram>();
	}
	
	public void AddData(int processorId, int threadId) {
		PointDataDiagram data = new PointDataDiagram();
		data.idProcess = processorId;
		data.idThread = threadId;
		dataArrayList.add(data);
	}
	
	public ArrayList<PointDataDiagram> GetArrayList(){
		return dataArrayList;
	}
}