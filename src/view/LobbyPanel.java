package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controller.LobbyInput;

public class LobbyPanel extends JPanel {
	private JLabel lblNewLabel_1;
	private LobbyInput lobbyinput;
	private BufferedImage mapImage;
	private Image preGameArt;
	
	public LobbyPanel(LobbyInput lobbyinput, Image preGameArt) {
		this.lobbyinput = lobbyinput;
		this.preGameArt = preGameArt;
		initDialog();
	}
	public void initDialog() {

		
		JLabel lblMap = new JLabel("Map: " + "VAD MAPPEN HETER");
		lblMap.setHorizontalAlignment(SwingConstants.CENTER);
		lblMap.setBounds(26, 11, 277, 25);
		add(lblMap);
		
		JTextArea textArea = new JTextArea();
		textArea.setRows(8);
		textArea.setBounds(332, 11, 100, 166);
		add(textArea);
		
		JCheckBox chckbxReady = new JCheckBox("READY");
		chckbxReady.setActionCommand("chckbxReady");
		chckbxReady.addActionListener(lobbyinput);
		chckbxReady.setBounds(352, 184, 70, 23);
		add(chckbxReady);
		
		
		
		try {
			mapImage = ImageIO.read(new File("res/pregameart.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
			mapImage = new BufferedImage(800,800, BufferedImage.TYPE_INT_ARGB);
		}
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(mapImage.getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
		lblNewLabel.setBounds(65, 32, 200, 200);
		
		
		add(lblNewLabel);
		
		if (preGameArt != null) {
			JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
			add(picLabel);
			picLabel.setBounds(0, 0, 800, 800);
		}
		
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
		}	
	}

}
