package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import arenaFighter.Main;
import controller.LobbyDialogInput;

public class LobbyDialog extends JDialog {
	private LobbyDialogInput lobbyDialogInput;
	private JTextArea playersTextArea;
	private GameFrame frame;
	private JPanel contentPane;
	private JLabel lblNewLabel_1;
	private BufferedImage mapImage;
	private int numberReady;
	
	public LobbyDialog(GameFrame frame) {
		this.frame = frame;
		numberReady=0;
		initDialog();
	}
	public void initDialog() {
		LobbyDialogInput lobbyDialogInput = new LobbyDialogInput(frame, this);
		setTitle("Lobby");
		setLayout(null);
		setLocation(160, 160);
		setPreferredSize(new Dimension(470, 240));		
		setResizable(false);
		pack();
		setVisible(true);

		setIconImage(Toolkit.getDefaultToolkit().getImage("res/testa.png"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 470, 240);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMap = new JLabel("Map: " + "VAD MAPPEN HETER");
		lblMap.setHorizontalAlignment(SwingConstants.CENTER);
		lblMap.setBounds(26, 11, 277, 25);
		contentPane.add(lblMap);
		
		JTextArea textArea = new JTextArea();
		textArea.setRows(8);
		textArea.setBounds(332, 11, 100, 166);
		contentPane.add(textArea);
		
		JCheckBox chckbxReady = new JCheckBox("READY");
		chckbxReady.setActionCommand("chckbxReady");
		chckbxReady.addActionListener(lobbyDialogInput);
		chckbxReady.setBounds(352, 184, 70, 23);
		contentPane.add(chckbxReady);
		
		
		
		try {
			mapImage = ImageIO.read(new File("res/pregameart.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
			mapImage = new BufferedImage(800,800, BufferedImage.TYPE_INT_ARGB);
		}
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(mapImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
		lblNewLabel.setBounds(65, 32, 200, 200);
		
		
		contentPane.add(lblNewLabel);
		
		textArea.append("Player 1");
		textArea.append("Player 2");
	}
	
	public void countDown(int counter){
		if (counter == 3) {
			System.out.println("test");
			JLabel lblNewLabel_1 = new JLabel("Game starts in 3..");
			lblNewLabel_1.setBounds(319, 203, 135, 29);
			add(lblNewLabel_1);
		}
		else if (counter == 2) {
			lblNewLabel_1.setText("Game starts in 3.. 2..");
		}
		else if(counter == 1){
			lblNewLabel_1.setText("Game starts in 3.. 2.. 1..");
		}
		else{
			Main.lobbyProtocol();
		}	
	}
	public void readyUp(int increment){numberReady+=increment;}
	public int getIsReady(){return numberReady;}
}
