package FileSystemLogic;
import java.util.ArrayList;
import java.util.HashMap;

public class FileSystem {
	private Disk disk;
	private FileAllocationTable fat;
	private HashMap<String, Directory> directories;

	public FileSystem(int diskSize, int sectionSize, Directory root, String path) {
		disk = new Disk(diskSize, sectionSize);
		fat = new FileAllocationTable(diskSize / sectionSize);
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
					writeFileGroupBlocks(countBlocks, (int) sizeData, indexBegin);
					return true;
				} else {
					indexBegin = getFirstFreeBlock();
					File file = new File(name, extension, indexBegin, (int) sizeData);
					dir.addFile(file);
					writeFile(countBlocks, (int) sizeData, indexBegin);
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
		int prevIndex = file.getFirstBlock();
		int index = prevIndex;
		while (fat.getBlock(index) != -1) {
			index = fat.getBlock(prevIndex);
			fat.setBlock(prevIndex, -1);
			prevIndex = index;
		}
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
			;
			directories.remove(path);
		}
	}

	private boolean haveFreeBlocks(int countBlocksFile) {
		int countFreeBlocks = 0;
		for (int i = 0; i < fat.size(); i++)
			if (fat.getBlock(i) == -1)
				countFreeBlocks++;
		return countFreeBlocks >= countBlocksFile;
	}

	private int getBeginIndexGroupBlocks(int countBlocks) {
		int freeBlocks = 0;
		int indexBegin = -1;
		for (int i = 0; i < fat.size(); i++)
			if (fat.getBlock(i) == -1) {
				if (indexBegin == -1)
					indexBegin = i;
				freeBlocks++;
				if (freeBlocks == countBlocks)
					return indexBegin;
			} else {
				indexBegin = -1;
				freeBlocks = 0;
			}
		return -1;
	}

	private int getFirstFreeBlock() throws Exception {
		for (int i = 0; i < fat.size(); i++)
			if (fat.getBlock(i) == -1)
				return i;
		throw new Exception("На диске нет места! Метод getFirstFreeBlock");
	}

	private void writeFileGroupBlocks(int countBlocks, int sizeData, int index) {
		while (countBlocks > 1) {
			sizeData -= Block.SizeBlock;
			disk.writeDataIntoSector(index, Block.SizeBlock);
			fat.setBlock(index, index + 1);
			index++;
			countBlocks--;
		}
		disk.writeDataIntoSector(index, sizeData);
		fat.setBlock(index, -1);
	}

	private void writeFile(int countBlocks, int sizeData, int index) throws Exception {
		int prevIndex = index;
		while (countBlocks > 1) {
			index++;
			if (fat.getBlock(index) == -1) {
				sizeData -= Block.SizeBlock;
				disk.writeDataIntoSector(index, Block.SizeBlock);
				fat.setBlock(prevIndex, index);
				prevIndex = index;
			}
			countBlocks--;
		}
		index++;
		while (index - 1 < fat.size()) {
			if (fat.getBlock(index) == -1) {
				disk.writeDataIntoSector(index, sizeData);
				fat.setBlock(prevIndex, index);
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

	public ArrayList<Integer> blocksFile(File file) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int index = file.getFirstBlock();
		list.add(index);
		while (fat.getBlock(index) != -1) {
			index = fat.getBlock(index);
			list.add(index);
		}
		return list;
	}
}