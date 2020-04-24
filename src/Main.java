import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("¬ведите размер оперативной пам€ти");
		int sizeOfRAM = sc.nextInt();
		System.out.println("¬ведите размер страницы оперативной пам€ти");
		int sizeOfPage = sc.nextInt();
		MemoryDispatcher md = new MemoryDispatcher(sizeOfRAM, sizeOfPage);
		System.out.println("¬ведите виртуальный адрес");
		int virtualAddress = sc.nextInt();
		while(virtualAddress != -1)
		{
			md.Run(virtualAddress);
			System.out.println("¬ведите виртуальный адрес");
			virtualAddress = sc.nextInt();
		}
		sc.close();
	}

}
