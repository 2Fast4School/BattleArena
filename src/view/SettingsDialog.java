package view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.SettingsDialogInput;

public class SettingsDialog extends JDialog {
	private SettingsDialogInput settingsDialogInput;
	private JTextField nameTextField;
	public static String name = "Player";
	public static boolean toolTipsEnabled = false;
	private GameFrame frame;
	
	JButton settingsCloseBtn;
	JButton settingsSaveBtn;
	JCheckBox settingsToolTipsChk;
	public SettingsDialog(GameFrame frame) {
		this.frame = frame;
		initDialog();
	}
	public void initDialog() {
		SettingsDialogInput settingsDialogInput = new SettingsDialogInput(frame, this);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Settings");
		setLayout(null);
		setAlwaysOnTop(true);
		setLocation(300, 300);
		setPreferredSize(new Dimension(200, 200));		
		setResizable(false);
		pack();
		setVisible(true);

		// Save button under the IP textfield
		JButton settingsSaveBtn = new JButton("Save");
		settingsSaveBtn.setBounds(20, 20, 111, 21);
		settingsSaveBtn.setActionCommand("settingsSaveBtn");
		settingsSaveBtn.addActionListener(settingsDialogInput);
		settingsSaveBtn.setVisible(true);
		
		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nameTextField.setText(name);
		nameTextField.setBounds(20, 45, 111, 21);
		nameTextField.setColumns(10);
		
		JCheckBox settingsToolTipsChk = new JCheckBox("Show Tooltips");
		settingsToolTipsChk.setSelected(toolTipsEnabled);
		settingsToolTipsChk.setActionCommand("settingsToolTipsChk");
		settingsToolTipsChk.addActionListener(settingsDialogInput);
		settingsToolTipsChk.setBounds(20, 70, 111, 21);
		
		// Cancel button under the name textfield
		JButton settingsCloseBtn = new JButton("Cancel");
		settingsCloseBtn.setBounds(20, 95, 111, 21);
		settingsCloseBtn.setActionCommand("settingsCloseBtn");
		settingsCloseBtn.addActionListener(settingsDialogInput);
		settingsCloseBtn.setVisible(true);

		add(nameTextField);
		add(settingsCloseBtn);
		add(settingsSaveBtn);
		add(settingsToolTipsChk);
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public String getName() {
		return nameTextField.getText();
	}
	
	public void setToolTipsEnabled(boolean enable) {
		toolTipsEnabled = enable;
	}
	
	public boolean getToolTipsEnabled() {
		return toolTipsEnabled;
	}
	
}
