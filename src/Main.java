import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("������� ������ ����������� ������");
		int sizeOfRAM = sc.nextInt();
		System.out.println("������� ������ �������� ����������� ������");
		int sizeOfPage = sc.nextInt();
		MemoryDispatcher md = new MemoryDispatcher(sizeOfRAM, sizeOfPage);
		System.out.println("������� ����������� �����");
		int virtualAddress = sc.nextInt();
		while(virtualAddress != -1)
		{
			md.Run(virtualAddress);
			System.out.println("������� ����������� �����");
			virtualAddress = sc.nextInt();
		}
		sc.close();
	}

}
