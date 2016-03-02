package view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import controller.SettingsInput;

public class SettingsPanel extends JPanel {
	private SettingsInput settingsinput;
	private JTextField nameTextField;
	public static String name = "Player";
	public static boolean toolTipsEnabled = false;
	private Image preGameArt;
	
	JButton settingsCloseBtn;
	JButton settingsSaveBtn;
	JCheckBox settingsToolTipsChk;
	public SettingsPanel(SettingsInput settingsinput, Image preGameArt) {
		this.settingsinput = settingsinput;
		this.preGameArt = preGameArt;
		initDialog();
	}
	public void initDialog() {
		setLayout(null);
		// Save button under the IP textfield
		JButton settingsSaveBtn = new JButton("Save");
		settingsSaveBtn.setBounds(20, 20, 111, 21);
		settingsSaveBtn.setActionCommand("settingsSaveBtn");
		settingsSaveBtn.addActionListener(settingsinput);
		settingsSaveBtn.setVisible(true);
		
		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nameTextField.setText(name);
		nameTextField.setBounds(20, 45, 111, 21);
		nameTextField.setColumns(10);
		
		JCheckBox settingsToolTipsChk = new JCheckBox("Show Tooltips");
		settingsToolTipsChk.setSelected(toolTipsEnabled);
		settingsToolTipsChk.setActionCommand("settingsToolTipsChk");
		settingsToolTipsChk.addActionListener(settingsinput);
		settingsToolTipsChk.setBounds(20, 70, 111, 21);
		
		// Cancel button under the name textfield
		JButton settingsCloseBtn = new JButton("Cancel");
		settingsCloseBtn.setBounds(20, 95, 111, 21);
		settingsCloseBtn.setActionCommand("settingsCloseBtn");
		settingsCloseBtn.addActionListener(settingsinput);
		settingsCloseBtn.setVisible(true);

		add(nameTextField);
		add(settingsCloseBtn);
		add(settingsSaveBtn);
		add(settingsToolTipsChk);
		
		if (preGameArt != null) {
			JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
			add(picLabel);
			picLabel.setBounds(0, 0, 800, 800);
		}
		
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public String getName() {
		return nameTextField.getText();
	}
	
}
