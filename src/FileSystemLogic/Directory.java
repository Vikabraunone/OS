package FileSystemLogic;
import java.util.ArrayList;

public class Directory {
	private ArrayList<Object> files;
	private String name;

	public Directory(String name) {
		this.name = name;
		files = new ArrayList<Object>();
	}

	public void addFile(File file) {
		files.add(file);
	}

	public File isExistFile(String name, String extension) {
		for (int i = 0; i < files.size(); i++)
			if (((File) files.get(i)).getName().equals(name) && ((File) files.get(i)).getExtension().equals(extension))
				return (File) files.get(i);
		return null;
	}

	public void deleteFile(File file) {
		for (int i = 0; i < files.size(); i++) {
			if (((File) files.get(i)).getName().equals(file.getName())
					&& ((File) files.get(i)).getExtension().equals(file.getExtension())) {
				files.remove(i);
				break;
			}
		}
	}

	public String getName() {
		return name;
	}

	public void addDirectory(Directory directory) {
		files.add(directory);
	}

	public boolean getDirectory(Directory directory) {
		for (int i = 0; i < files.size(); i++)
			if (((Directory) files.get(i)).getName().equals(directory.getName()))
				return true;
		return false;
	}

	public void deleteDirectory(Directory directory) {
		for (int i = 0; i < files.size(); i++) {
			if (((Directory) files.get(i)).getName().equals(directory.getName())) {
				files.remove(i);
				break;
			}
		}
	}

	public ArrayList<Object> getFiles() {
		return files;
	}
}
