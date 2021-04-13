import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * Main game window.
 * @author Will Brown
 * @version 1.0-alpha
 * Spring 2021
 */
public class GameWindow extends JFrame {

	private final int ROCKETHEIGHT = 32;
	private final int ROCKETWIDTH = ROCKETHEIGHT / 2;

	/**
	 * Set up the game. All code is in the constructor since this class only handles graphics.
	 * All actual game logic is done in another thread.
	 * @param tickRate how many times the game logic runs in one second
	 * @param fuel the amount of fuel the rocket has
	 */
	public GameWindow(int tickRate, int fuel) {
		// Set up the game window
		setResizable(false);
		setTitle("Moon Lander");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		
		// Instantiate class for game loop thread
		GameThread gameThread = new GameThread(tickRate, fuel);
		
		// Drawing code for window panel 
		int rocketX = getWidth() / 2 - ROCKETWIDTH;
		JPanel contentPane = new JPanel() {
			public void paintComponent(Graphics g) {
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, getWidth(), getHeight());
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(0, getHeight() - 32, getWidth(), 32);
				g.fillRect(0, getHeight() - 32, getWidth(), 32);
				g.setColor(Color.GRAY);
				g.drawRect(rocketX, gameThread.getScreenPosition(), ROCKETWIDTH, ROCKETHEIGHT);
				g.fillRect(rocketX, gameThread.getScreenPosition(), ROCKETWIDTH, ROCKETHEIGHT);
			}
		};
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSpeed = new JLabel("Speed: 0");
		lblSpeed.setForeground(Color.WHITE);
		lblSpeed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSpeed.setBounds(10, 11, 120, 30);
		contentPane.add(lblSpeed);
		
		JLabel lblFuel = new JLabel("Fuel: 0");
		lblFuel.setForeground(Color.WHITE);
		lblFuel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFuel.setBounds(10, 52, 120, 30);
		contentPane.add(lblFuel);
		
		JLabel lblGameWin = new JLabel("You landed!");
		lblGameWin.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameWin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblGameWin.setForeground(Color.WHITE);
		lblGameWin.setBounds(10, 200, 364, 29);
		contentPane.add(lblGameWin);
		lblGameWin.setVisible(false);
		
		JLabel lblResetInfo = new JLabel("Press R to reset the game.");
		lblResetInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblResetInfo.setForeground(Color.WHITE);
		lblResetInfo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblResetInfo.setBounds(10, 240, 364, 29);
		contentPane.add(lblResetInfo);
		lblResetInfo.setVisible(false);
		
		// Key listener code
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					gameThread.setKeyPressed(true);
					break;
				case KeyEvent.VK_R:
					gameThread.resetGame();
					lblGameWin.setVisible(false);
					lblResetInfo.setVisible(false);
					break;
				}
			}
			
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameThread.setKeyPressed(false);
				}
			}
		});
		
		// Code executed by timer
		ActionListener updateWindow = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Update the screen with new information.
				repaint();
				lblSpeed.setText("Speed: " + gameThread.getSpeed());
				lblFuel.setText("Fuel: " + gameThread.getCurrentFuel());
				
				// Display the win/loss text if the game is over.
				if (gameThread.isGameOver()) {
					lblGameWin.setText(gameThread.getSpeed() < 25 ? "You landed!" : "You crashed.");
					lblGameWin.setVisible(true);
					lblResetInfo.setVisible(true);
				}
			}
		};
		
		// Start the game thread and create timer
		gameThread.start();
		Timer timer = new Timer(1000 / tickRate, updateWindow);
		timer.start();
	}
}
