import java.util.ArrayList;

public class ConsoleProgram {

	public static void main(String[] args) {
		MainProgram program = new MainProgram();
		ArrayList<PointDataDiagram> arrayListData = program.getArrayList();
		for(int i=0; i< arrayListData.size(); i++)
			System.out.println("Процесс № " + arrayListData.get(i).idProcess + " : поток " + arrayListData.get(i).idThread);
	}

}
