import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Initial configuration window that displays when you boot up the game.
 * @author Will Brown
 * @version 1.0-alpha
 * Spring 2021
 */
public class ConfigWindow extends JFrame {

	private JCheckBoxMenuItem chckbxmntmAdvancedOptions;
	private JTextField tickRateTextField;
	private JLabel lblTickRate;
	private int tickRate, fuel;

	/**
	 * Create the frame.
	 */
	public ConfigWindow() {
		setTitle("Start Moon Lander");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 284, 262);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		// Show more options if this checkbox is checked.
		chckbxmntmAdvancedOptions = new JCheckBoxMenuItem("Show Advanced Options");
		chckbxmntmAdvancedOptions.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblTickRate.setVisible(chckbxmntmAdvancedOptions.isSelected());
				tickRateTextField.setVisible(chckbxmntmAdvancedOptions.isSelected());
			}
		});
		mnTools.add(chckbxmntmAdvancedOptions);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show an about dialog.
				JOptionPane.showMessageDialog(rootPane, "Basic moon lander game created by Will Brown (wcbrow01)", "About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblGameTitle = new JLabel("Moon Lander");
		lblGameTitle.setFont(new Font("Tahoma", Font.PLAIN, 32));
		GridBagConstraints gbc_lblGameTitle = new GridBagConstraints();
		gbc_lblGameTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblGameTitle.gridx = 0;
		gbc_lblGameTitle.gridy = 0;
		contentPane.add(lblGameTitle, gbc_lblGameTitle);
		
		JLabel lblFuel = new JLabel("Starting Fuel");
		GridBagConstraints gbc_lblFuel = new GridBagConstraints();
		gbc_lblFuel.insets = new Insets(0, 0, 5, 0);
		gbc_lblFuel.gridx = 0;
		gbc_lblFuel.gridy = 1;
		contentPane.add(lblFuel, gbc_lblFuel);
		
		JTextField fuelTextField = new JTextField();
		fuelTextField.setText("1000");
		GridBagConstraints gbc_fuelTextField = new GridBagConstraints();
		gbc_fuelTextField.insets = new Insets(0, 0, 5, 0);
		gbc_fuelTextField.gridx = 0;
		gbc_fuelTextField.gridy = 2;
		contentPane.add(fuelTextField, gbc_fuelTextField);
		fuelTextField.setColumns(20);
		
		lblTickRate = new JLabel("Tickrate");
		GridBagConstraints gbc_lblTickRate = new GridBagConstraints();
		gbc_lblTickRate.insets = new Insets(0, 0, 5, 0);
		gbc_lblTickRate.gridx = 0;
		gbc_lblTickRate.gridy = 3;
		contentPane.add(lblTickRate, gbc_lblTickRate);
		lblTickRate.setVisible(false);
		
		tickRateTextField = new JTextField();
		tickRateTextField.setText("100");
		GridBagConstraints gbc_tickRateTextField = new GridBagConstraints();
		gbc_tickRateTextField.insets = new Insets(0, 0, 5, 0);
		gbc_tickRateTextField.gridx = 0;
		gbc_tickRateTextField.gridy = 4;
		contentPane.add(tickRateTextField, gbc_tickRateTextField);
		tickRateTextField.setColumns(20);
		tickRateTextField.setVisible(false);
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start the game with the user's specified fuel and tickrate values.
				tickRate = Integer.parseInt(tickRateTextField.getText());
				fuel = Integer.parseInt(fuelTextField.getText());
				new GameWindow(tickRate, fuel).setVisible(true);
				dispose();
			}
		});
		GridBagConstraints gbc_btnStartGame = new GridBagConstraints();
		gbc_btnStartGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartGame.gridx = 0;
		gbc_btnStartGame.gridy = 5;
		contentPane.add(btnStartGame, gbc_btnStartGame);
		
	}

}
