package view;

import java.awt.Font;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.SettingsInput;
import model.GameState;

public class SettingsPanel extends JPanel {
	private SettingsInput settingsinput;
	private JTextField nameTextField;
	private String[] name =
			{"M�rta", "Ann-Britt", "Karin", "Majken", "Kerstin", "Viola", "G�ta", "Inga", "Dagny", "Gerd", "Sixten", "Nils", "Birger",
			"Sune", "Ture", "Harry", "Lars", "Per", "Ernst", "Gunnar", "Eva", "Gunnel", "Gunborg", "Birgit", "Astrid", "Ulla-Britt",
			"Berit", "Sonja", "Vanja", "Jan", "�ke", "Sven", "Olof", "Allan", "Harald", "Bertil", "G�sta", "Bengt", "Birgitta", "Margareta",
			"Christina", "Marianne", "Ingrid", "Inger", "Gertrud", "Doris", "Kerstin", "Elisabeth", "Maj-Britt", "Anita", "Britt-Marie",
			"Barbro", "Lars", "Per-Erik", "Kjell", "Kurt", "H�kan", "Leif", "Roland", "Bert", "�ke", "G�ran", "Monica", "Katarina",
			"Eva", "Christina", "Agneta", "Lena", "Peter", "Krister", "Stefan", "Mats", "Patrik", "Niklas", "Henrik", "Susanne",
			"Jeanette", "Christine", "Maria", "Helena", "Caroline", "Annika", "Louise", "Malin", "Eva", "Fredrik", "Joachim", "Johan",
			"Patrik", "Jimmy", "Jonas", "Christoffer", "Mattias", "Markus", "Johan", "Niklas", "Oskar", "Malin", "Helena", "Sofia",
			"Johanna", "Anna", "Frida", "Emma", "Lisa", "Jannike", "Therese", "�sa", "Elin", "Pernilla", "Josefine", "Jenny"};
	private Image preGameArt;
	
	JButton settingsCloseBtn;
	JButton settingsSaveBtn;
	JCheckBox settingsToolTipsChk;
	public SettingsPanel(SettingsInput settingsinput, Image preGameArt) {
		this.settingsinput = settingsinput;
		this.preGameArt = preGameArt;
		initDialog();
	}
	private void initDialog() {
		setLayout(null);
		
		Font font1 = new Font("Comic Sans MS", Font.PLAIN, 21);
		Font font2 = new Font("Comic Sans MS", Font.PLAIN, 14);
		
		JButton settingsCloseBtn = new JButton("Cancel");
		settingsCloseBtn.setFont(font1);
		settingsCloseBtn.setBounds(300, 320, 200, 60);
		settingsCloseBtn.setActionCommand("settingsCloseBtn");
		settingsCloseBtn.addActionListener(settingsinput);
		add(settingsCloseBtn);
		
		JPanel panel = new JPanel();
		panel.setBounds(300, 240, 200, 60);
		panel.setLayout(null);
		add(panel);
		
		nameTextField = new JTextField();
		nameTextField.setFont(font2);
		nameTextField.setBounds(70, 0, 130, 60);
		nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		System.out.println(name.length);
		nameTextField.setText(name[new Random().nextInt(name.length+1)]);
		nameTextField.setColumns(10);
		panel.add(nameTextField);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(font1);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(5, 0, 65, 60);
		panel.add(nameLabel);
		
		if (preGameArt != null) {
			JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
			add(picLabel);
			picLabel.setBounds(0, 0, 800, 800);
		}
		
	}
	
	public String getName() {
		return nameTextField.getText();
	}
	
}
