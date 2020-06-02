import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class ChooseDirectoryApplication {
	public JFrame frame;
	JList<Object> jListDirectory;
	private FileSystem fileSystem;
	private String pathFrom;
	private Object fileFrom;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseDirectoryApplication window = new ChooseDirectoryApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChooseDirectoryApplication() {
		initialize();
	}

	public void setFileSystem(FileSystem fileSystem, String pathFrom, Object fileFrom) {
		this.fileSystem = fileSystem;
		this.pathFrom = pathFrom;
		this.fileFrom = fileFrom;
		HashMap<String, Directory> directories = fileSystem.getDirectories();
		ArrayList<String> keys = new ArrayList<String>(directories.keySet());
		ArrayList<String> dirList = new ArrayList<String>();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Directory value = directories.get(key);
			dirList.add(value.getName() + "\n");
		}
		jListDirectory.setListData(dirList.toArray());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 356, 397);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("\u0412\u044B\u0431\u0440\u0430\u0442\u044C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int indexDir = jListDirectory.getSelectedIndex();
					HashMap<String, Directory> directories = fileSystem.getDirectories();
					ArrayList<String> keys = new ArrayList<String>(directories.keySet());
					if (fileFrom instanceof File)
						fileSystem.relocateFile(pathFrom, keys.get(indexDir), (File) fileFrom);
					else
						fileSystem.relocateDirectory(pathFrom, keys.get(indexDir), (Directory) fileFrom);
					frame.dispose();
				} catch (Exception e) {
				}
			}
		});
		btnNewButton.setBounds(61, 297, 211, 36);
		frame.getContentPane().add(btnNewButton);

		jListDirectory = new JList();
		jListDirectory.setBounds(36, 23, 266, 246);
		jListDirectory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jListDirectory.setSelectedIndex(0);
		frame.getContentPane().add(jListDirectory);
	}
}