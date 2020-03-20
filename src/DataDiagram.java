import java.util.ArrayList;

public class DataDiagram {
	ArrayList<PointDataDiagram> dataArrayList;
	
	public DataDiagram() {
		dataArrayList = new ArrayList<PointDataDiagram>();
	}
	
	public void AddData(PointDataDiagram data) {
		dataArrayList.add(data);
	}
	
	public ArrayList<PointDataDiagram> GetArrayList(){
		return dataArrayList;
	}
}