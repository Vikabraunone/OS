import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class FileManager {
	private FileSystem fileSystem;
	JFrame frame;
	PanelMemory panel;
	Directory currentDir;
	String currentPath;
	private JTextField textFieldName;
	private JTextField textFieldExtension;
	private JTextField textFieldDir;
	private JTextField textFieldSize;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileManager window = new FileManager();
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
	public FileManager() {
		initialize();
	}

	public void initializeFileSystem(int diskSize, int blockSize) {
		currentDir = new Directory("root");
		currentPath = ">" + "root";
		fileSystem = new FileSystem(diskSize, blockSize, currentDir, currentPath);
		panel = new PanelMemory(diskSize / blockSize);
		panel.setBounds(297, 11, 406, 334);
		frame.getContentPane().add(panel);
		panel.setBackground(new Color(0, 0, 0));
		panel.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 821, 533);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(10, 11, 256, 315);
		frame.getContentPane().add(textArea);

		JButton buttonAdd = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0444\u0430\u0439\u043B");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					double fileSize = Double.parseDouble(textFieldSize.getText());
					fileSystem.createFile(currentPath, textFieldName.getText(), textFieldExtension.getText(), fileSize);
					updateFiles();
				} catch (Exception e) {
				}
			}
		});
		buttonAdd.setBounds(235, 356, 147, 23);
		frame.getContentPane().add(buttonAdd);

		JButton buttonCopy = new JButton(
				"\u0414\u0443\u0431\u043B\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u0444\u0430\u0439\u043B");
		buttonCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = textArea.getSelectedText();
				int lastIndex = text.lastIndexOf(".");
				File file = currentDir.isExistFile(text.substring(0, lastIndex),
						text.substring(lastIndex + 1, text.length()));
				if (file != null) {
					try {
						fileSystem.copyFile(currentPath, file, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
					updateFiles();
				}
			}
		});
		buttonCopy.setBounds(235, 392, 147, 23);
		frame.getContentPane().add(buttonCopy);

		JButton buttonRemove = new JButton(
				"\u041F\u0435\u0440\u0435\u043C\u0435\u0441\u0442\u0438\u0442\u044C \u0444\u0430\u0439\u043B");
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textArea.getSelectedText();
				int lastIndex = text.lastIndexOf(".");
				File file = currentDir.isExistFile(text.substring(0, lastIndex),
						text.substring(lastIndex + 1, text.length()));
				ChooseDirectoryApplication chooseFrame = new ChooseDirectoryApplication();
				chooseFrame.frame.setVisible(true);
				chooseFrame.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				chooseFrame.setFileSystem(fileSystem, currentPath, file);
			}
		});
		buttonRemove.setBounds(235, 426, 147, 23);
		frame.getContentPane().add(buttonRemove);

		JButton buttonDelete = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0444\u0430\u0439\u043B");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textArea.getSelectedText();
				int lastIndex = text.lastIndexOf(".");
				File file = currentDir.isExistFile(text.substring(0, lastIndex),
						text.substring(lastIndex + 1, text.length()));
				if (file != null) {
					try {
						fileSystem.deleteFile(currentPath, file);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					updateFiles();
				}
			}
		});
		buttonDelete.setBounds(235, 460, 147, 23);
		frame.getContentPane().add(buttonDelete);

		JButton buttonAddDir = new JButton(
				"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u043A\u0430\u0442\u0430\u043B\u043E\u0433");
		buttonAddDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Directory dir = fileSystem.getDirectory(currentPath + ">" + textFieldDir.getText());
				if (dir == null) {
					try {
						fileSystem.createDirectory(currentPath, textFieldDir.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					updateFiles();
				}
			}
		});
		buttonAddDir.setBounds(624, 356, 147, 23);
		frame.getContentPane().add(buttonAddDir);

		JButton buttonCopyDir = new JButton(
				"\u0414\u0443\u0431\u043B\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u043A\u0430\u0442\u0430\u043B\u043E\u0433");
		buttonCopyDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textArea.getSelectedText();
				Directory dir = fileSystem.getDirectory(currentPath + ">" + textFieldDir.getText());
				if (dir != null) {
					try {
						fileSystem.copyDirectory(currentPath + ">" + textFieldDir.getText(), dir, false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					updateFiles();
				}
			}
		});
		buttonCopyDir.setBounds(624, 390, 147, 23);
		frame.getContentPane().add(buttonCopyDir);

		JButton buttonRemoveDir = new JButton(
				"\u041F\u0435\u0440\u0435\u043C\u0435\u0441\u0442\u0438\u0442\u044C \u043A\u0430\u0442\u0430\u043B\u043E\u0433");
		buttonRemoveDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Directory dir = fileSystem.getDirectory(currentPath + ">" + textFieldDir.getText());
				if (dir != null) {
					ChooseDirectoryApplication chooseFrame = new ChooseDirectoryApplication();
					chooseFrame.frame.setVisible(true);
					chooseFrame.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					chooseFrame.setFileSystem(fileSystem, currentPath, dir);
				}
			}
		});
		buttonRemoveDir.setBounds(624, 426, 147, 23);
		frame.getContentPane().add(buttonRemoveDir);

		JButton buttonDeleteDir = new JButton(
				"\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u043A\u0430\u0442\u0430\u043B\u043E\u0433");
		buttonDeleteDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonDeleteDir.setBounds(624, 460, 147, 23);
		frame.getContentPane().add(buttonDeleteDir);

		JButton buttonDown = new JButton(
				"\u041D\u0430 \u0443\u0440\u043E\u0432\u0435\u043D\u044C \u0432\u043D\u0438\u0437");
		buttonDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Directory dir = fileSystem.getDirectory(currentPath + ">" + textFieldDir.getText());
				if (dir != null) {
					currentDir = dir;
					currentPath = currentPath + ">" + textFieldDir.getText();
					updateFiles();
				}
			}
		});
		buttonDown.setBounds(437, 420, 147, 29);
		frame.getContentPane().add(buttonDown);

		JButton buttonUp = new JButton(
				"\u041D\u0430 \u0443\u0440\u043E\u0432\u0435\u043D\u044C \u0432\u0435\u0440\u0445");
		buttonUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lastIndex = currentPath.lastIndexOf(">");
				currentPath = currentPath.substring(0, lastIndex);
				currentDir = fileSystem.getDirectory(currentPath);
				updateFiles();
			}
		});
		buttonUp.setBounds(437, 452, 147, 29);
		frame.getContentPane().add(buttonUp);

		textFieldName = new JTextField();
		textFieldName.setBounds(105, 356, 120, 20);
		frame.getContentPane().add(textFieldName);
		textFieldName.setColumns(10);

		textFieldExtension = new JTextField();
		textFieldExtension.setBounds(105, 387, 120, 20);
		frame.getContentPane().add(textFieldExtension);
		textFieldExtension.setColumns(10);

		JLabel labelFileName = new JLabel("\u0418\u043C\u044F \u0444\u0430\u0439\u043B\u0430:");
		labelFileName.setBounds(10, 356, 85, 20);
		frame.getContentPane().add(labelFileName);

		JLabel labelExtension = new JLabel("\u0420\u0430\u0441\u0448\u0438\u0440\u0435\u043D\u0438\u0435:");
		labelExtension.setBounds(10, 390, 85, 20);
		frame.getContentPane().add(labelExtension);

		textFieldDir = new JTextField();
		textFieldDir.setColumns(10);
		textFieldDir.setBounds(437, 393, 147, 20);
		frame.getContentPane().add(textFieldDir);

		JLabel labelDir = new JLabel(
				"\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u043A\u0430\u0442\u0430\u043B\u043E\u0433\u0430:");
		labelDir.setBounds(447, 362, 124, 20);
		frame.getContentPane().add(labelDir);

		JLabel label = new JLabel("\u0420\u0430\u0437\u043C\u0435\u0440 \u0444\u0430\u0439\u043B\u0430:");
		label.setBounds(10, 418, 97, 20);
		frame.getContentPane().add(label);

		textFieldSize = new JTextField();
		textFieldSize.setColumns(10);
		textFieldSize.setBounds(105, 418, 120, 20);
		frame.getContentPane().add(textFieldSize);

		JButton buttonChoose = new JButton("\u0412\u044B\u0431\u0440\u0430\u0442\u044C \u0444\u0430\u0439\u043B");
		buttonChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = textArea.getSelectedText();
				int lastIndex = text.lastIndexOf(".");
				File file = currentDir.isExistFile(text.substring(0, lastIndex),
						text.substring(lastIndex + 1, text.length()));
				if (file != null) {
					ArrayList<Integer> idBlocks = fileSystem.blocksFile(file);
					panel.setValues(idBlocks);
					panel.repaint();
				}
			}
		});
		buttonChoose.setBounds(33, 449, 147, 23);
		frame.getContentPane().add(buttonChoose);
	}
	
	private void updateFiles()
	{
		ArrayList<Object> files = currentDir.getFiles();
		textArea.setText(null);
		if (files.size() != 0) {
			if ((files.get(0) instanceof Directory))
				textArea.append("[Каталог]" + ((Directory) files.get(0)).getName());
			else
				textArea.append(
						((File) files.get(0)).getName() + "." + ((File) files.get(0)).getExtension());
			for (int i = 1; i < files.size(); i++) {
				if ((files.get(i) instanceof Directory))
					textArea.append("\n" + "[Каталог]" + ((Directory) files.get(i)).getName());
				else
					textArea.append("\n" + ((File) files.get(i)).getName() + "."
							+ ((File) files.get(i)).getExtension());
			}
		}
	}
}