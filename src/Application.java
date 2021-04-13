import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Entry-point for the moon lander game.
 * @author Will Brown
 * @version 1.0-alpha
 * Spring 2021
 */
public class Application {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		// Try to use the system theme.	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// If this happens, we can't run the program anyways since we don't have a GUI.
			e1.printStackTrace();
		}
		
		// Start the game configuration window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigWindow frame = new ConfigWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
