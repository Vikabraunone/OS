import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InitializeWindow {

	private JFrame frame;
	private JTextField textFieldDisk;
	private JTextField textFieldBlock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitializeWindow window = new InitializeWindow();
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
	public InitializeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 389, 192);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u0414\u0438\u0441\u043A\u043E\u0432\u043E\u0439 \u0440\u0430\u0437\u0434\u0435\u043B:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(39, 21, 159, 35);
		frame.getContentPane().add(label);
		
		textFieldDisk = new JTextField();
		textFieldDisk.setBounds(208, 30, 86, 20);
		frame.getContentPane().add(textFieldDisk);
		textFieldDisk.setColumns(10);
		
		JLabel label_1 = new JLabel("\u0421\u0435\u043A\u0442\u043E\u0440 \u0434\u0438\u0441\u043A\u0430:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(39, 67, 159, 35);
		frame.getContentPane().add(label_1);
		
		textFieldBlock = new JTextField();
		textFieldBlock.setColumns(10);
		textFieldBlock.setBounds(208, 76, 86, 20);
		frame.getContentPane().add(textFieldBlock);
		
		JButton button = new JButton("\u0417\u0430\u043F\u0443\u0441\u0442\u0438\u0442\u044C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int diskSize = Integer.parseInt(textFieldDisk.getText());
				int blockSize = Integer.parseInt(textFieldBlock.getText());
				
				FileManager fm = new FileManager();
				fm.frame.setVisible(true);
				fm.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				fm.initializeFileSystem(diskSize, blockSize);
			}
		});
		button.setBounds(123, 113, 122, 29);
		frame.getContentPane().add(button);
	}
}
