package FileSystemLogic;
import java.util.ArrayList;
import java.util.HashMap;

public class FileSystem {
	private Disk disk;
	private HashMap<String, Directory> directories;

	public FileSystem(int diskSize, int sectionSize, Directory root, String path) {
		disk = new Disk(diskSize, sectionSize);
		directories = new HashMap<String, Directory>();
		directories.put(path, root);
	}

	public boolean createFile(String path, String name, String extension, double sizeData) throws Exception {
		Directory dir = directories.get(path);
		if (dir.isExistFile(name, extension) == null) {
			int countBlocks = (int) Math.ceil(sizeData / Block.SizeBlock);
			if (haveFreeBlocks(countBlocks)) {

				int indexBegin = getBeginIndexGroupBlocks(countBlocks);
				// если можно записать файл в несколько блоков подряд
				if (indexBegin != -1) {
					File file = new File(name, extension, indexBegin, (int) sizeData);
					dir.addFile(file);
					writeFileGroupBlocks(file, countBlocks, (int) sizeData, indexBegin);
					return true;
				} else {
					indexBegin = getFirstFreeBlock();
					File file = new File(name, extension, indexBegin, (int) sizeData);
					dir.addFile(file);
					writeFile(file, countBlocks, (int) sizeData, indexBegin);
					return true;
				}
			}
			throw new Exception("Нет места на диске!");
		}
		throw new Exception("Такой файл существует!");
	}

	public boolean copyFile(String path, File file, boolean isRelocate) throws Exception {
		if (isRelocate)
			return createFile(path, new String(file.getName()), new String(file.getExtension()), file.getSize());
		else
			return createFile(path, new String(file.getName() + "копия"), new String(file.getExtension()),
					file.getSize());
	}

	public void relocateFile(String pathFrom, String pathTo, File file) throws Exception {
		if (copyFile(pathTo, file, true))
			deleteFile(pathFrom, file);
	}

	public void deleteFile(String path, File file) {
		ArrayList<Integer> idBlocks = file.getBlocks();
		for(int i = 0; i < idBlocks.size(); i++)
			disk.writeDataIntoSector(idBlocks.get(i), -1);
		directories.get(path).deleteFile(file);
	}

	public void createDirectory(String parentPath, String name) {
		Directory dir = new Directory(name);
		String currentPath = parentPath + ">" + name;
		directories.put(currentPath, dir);
		directories.get(parentPath).addDirectory(dir);
	}

	public void copyDirectory(String path, Directory directory, boolean isRelocate) {
		if (isRelocate)
			createDirectory(path, new String(directory.getName()));
		else
			createDirectory(path, new String(directory.getName() + "копия"));
	}

	public void relocateDirectory(String pathFrom, String pathTo, Directory directory) throws Exception {
		copyDirectory(pathTo, directory, true);
		deleteDirectory(pathFrom, directory);
	}

	public void deleteDirectory(String path, Directory directory) {
		if (directories.get(path).getDirectory(directory)) {
			directories.get(path).deleteDirectory(directory);
			directories.remove(path);
		}
	}

	private boolean haveFreeBlocks(int countBlocksFile) {
		int countFreeBlocks = 0;
		for (int i = 0; i < disk.countBlocks(); i++)
			if (disk.readDataFromSector(i) == -1)
				countFreeBlocks++;
		return countFreeBlocks >= countBlocksFile;
	}

	private int getBeginIndexGroupBlocks(int countBlocks) {
		int freeBlocks = 0;
		int indexBegin = -1;
		for (int i = 0; i < disk.countBlocks(); i++) {
			if (disk.readDataFromSector(i) == -1)
			{
				if (indexBegin == -1)
					indexBegin = i;
				freeBlocks++;
				if (freeBlocks == countBlocks)
					return indexBegin;
			}
			else
			{
				indexBegin = -1;
				freeBlocks = 0;
			}
		}
		return -1;
	}

	private int getFirstFreeBlock() throws Exception {
		for (int i = 0; i < disk.countBlocks(); i++)
			if (disk.readDataFromSector(i) == -1)
				return i;
		throw new Exception("На диске нет места! Метод getFirstFreeBlock");
	}

	private void writeFileGroupBlocks(File file, int countBlocks, int sizeData, int index) {
		if (sizeData > Block.SizeBlock) {
			sizeData -= Block.SizeBlock;
			disk.writeDataIntoSector(index, Block.SizeBlock);
			index++;
			countBlocks--;
			while (countBlocks > 1) {
				sizeData -= Block.SizeBlock;
				disk.writeDataIntoSector(index, Block.SizeBlock);
				file.addBlock(index);
				index++;
				countBlocks--;
			}
		}
		disk.writeDataIntoSector(index, sizeData);
		file.addBlock(index);
	}

	private void writeFile(File file, int countBlocks, int sizeData, int index) throws Exception {
		if (sizeData > Block.SizeBlock) {
			sizeData -= Block.SizeBlock;
			disk.writeDataIntoSector(index, Block.SizeBlock);
			index++;
			countBlocks--;

			while (countBlocks > 1 && index < disk.countBlocks()) {
				if (disk.readDataFromSector(index) == -1) {
					sizeData -= Block.SizeBlock;
					disk.writeDataIntoSector(index, Block.SizeBlock);
					file.addBlock(index);
					index++;
					countBlocks--;
				}
			}
		}
		while (index < disk.countBlocks()) {
			if (disk.readDataFromSector(index) == -1) {
				file.addBlock(index);
				disk.writeDataIntoSector(index, sizeData);
				return;
			}
			index++;
		}
		throw new Exception("На диске нет места! Метод writeFile");
	}

	public HashMap<String, Directory> getDirectories() {
		return directories;
	}

	public Directory getDirectory(String path) {
		if (directories.containsKey(path))
			return directories.get(path);
		else
			return null;
	}
	
	public ArrayList<Integer> getFileBlocks(File file)
	{
		return file.getBlocks();
	}
}