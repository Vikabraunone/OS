import java.util.ArrayList;

public class ConsoleProgram {

	public static void main(String[] args) {
		MainProgram program = new MainProgram();
		ArrayList<PointDataDiagram> arrayListData = program.getArrayList();
		for(int i=0; i< arrayListData.size(); i++)
			System.out.println("������� � " + arrayListData.get(i).idProcess + " : ����� " + arrayListData.get(i).idThread);
	}

}
