package view;

import java.awt.EventQueue;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class FrameTest2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameTest2 frame = new FrameTest2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameTest2() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Fred\\Documents\\GitHub\\BattleArena\\res\\testa.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 464, 239);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblMap = new JLabel("Map: ");
		lblMap.setHorizontalAlignment(SwingConstants.CENTER);
		lblMap.setBounds(26, 11, 277, 25);
		panel.add(lblMap);
		
		JTextArea textArea = new JTextArea();
		textArea.setRows(8);
		textArea.setBounds(332, 11, 100, 166);
		panel.add(textArea);
		
		JCheckBox chckbxReady = new JCheckBox("READY");
		chckbxReady.setBounds(352, 184, 59, 23);
		panel.add(chckbxReady);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(65, 32, 200, 200);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Game starting in 3.. 2.. 1..");
		lblNewLabel_1.setBounds(319, 203, 135, 29);
		panel.add(lblNewLabel_1);
		
		textArea.append("Player 1");
		textArea.append("Player 2");
		

	}
}
