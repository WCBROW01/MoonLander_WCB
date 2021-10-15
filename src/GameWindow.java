import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * Main game window.
 * @author Will Brown
 * @version 1.0-alpha
 * Spring 2021
 */
public class GameWindow extends JFrame {

	private final int ROCKETHEIGHT = 32;
	private final int ROCKETWIDTH = ROCKETHEIGHT / 2;
	private int rocketX, rocketY;

	/**
	 * Set up the game. All code is in the constructor since this class only handles graphics.
	 * All actual game logic is done in another thread.
	 * @param tickRate how many times the game logic runs in one second
	 * @param fuel the amount of fuel the rocket has
	 */
	public GameWindow(int tickRate, int fuel) {
		setTitle("Moon Lander");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		
		// Instantiate class for game loop thread
		GameLogic gameLogic = new GameLogic(tickRate, fuel);
		
		// Drawing code for window panel 
		JPanel contentPane = new JPanel() {
			public void paintComponent(Graphics g) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(0, getHeight() - 32, getWidth(), 32);
				g.setColor(Color.GRAY);
				g.fillRect(rocketX, rocketY, ROCKETWIDTH, ROCKETHEIGHT);
			}
		};
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblSpeed = new JLabel("Speed: 0");
		lblSpeed.setForeground(Color.WHITE);
		lblSpeed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblFuel = new JLabel("Fuel: 0");
		lblFuel.setForeground(Color.WHITE);
		lblFuel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblGameWin = new JLabel("You landed!");
		lblGameWin.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameWin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblGameWin.setForeground(Color.WHITE);
		lblGameWin.setVisible(false);
		
		JLabel lblResetInfo = new JLabel("Press R to reset the game.");
		lblResetInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblResetInfo.setForeground(Color.WHITE);
		lblResetInfo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSpeed, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFuel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGameWin, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
						.addComponent(lblResetInfo, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
					.addGap(5))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblSpeed, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblFuel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(118)
					.addComponent(lblGameWin)
					.addGap(11)
					.addComponent(lblResetInfo))
		);
		contentPane.setLayout(gl_contentPane);
		lblResetInfo.setVisible(false);
		
		// Key listener code
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					gameLogic.setKeyPressed(true);
					break;
				case KeyEvent.VK_R:
					gameLogic.resetGame();
					lblGameWin.setVisible(false);
					lblResetInfo.setVisible(false);
					break;
				}
			}
			
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameLogic.setKeyPressed(false);
				}
			}
		});
		
		// Code executed by timer
		ActionListener updateWindow = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameLogic.runGameLogic();
				
				// Update the screen with new information.
				rocketX = contentPane.getWidth() / 2 - ROCKETWIDTH / 2;
				rocketY = contentPane.getHeight() - 32 - ROCKETHEIGHT - gameLogic.getPosition(); 
				repaint();
				lblSpeed.setText("Speed: " + gameLogic.getSpeed());
				lblFuel.setText("Fuel: " + gameLogic.getCurrentFuel());
				
				// Display the win/loss text if the game is over.
				if (gameLogic.isGameOver()) {
					if (gameLogic.getGameTime() < 1000) {
						lblGameWin.setText("Nice try.");
					} else {
						lblGameWin.setText(gameLogic.getSpeed() < 25 ? "You landed!" : "You crashed.");						
					}
					lblGameWin.setVisible(true);
					lblResetInfo.setVisible(true);
				}
			}
		};
		
		// Start the game thread and create timer
		//gameThread.start();
		Timer timer = new Timer(1000 / tickRate, updateWindow);
		timer.start();
	}
}
