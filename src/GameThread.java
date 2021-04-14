/**
 * Game loop code for the moon lander game.
 * @author Will Brown
 * @version 1.0-alpha
 * Spring 2021
 */
public class GameThread extends Thread {
	
	private final double ACCEL = 98; // acceleration rate of the rocket (in units/s)
	private boolean gameStarted, gameOver, keyPressed;
	private double position, velocity, accelPerTick, startingFuel, currentFuel;
	private int tickRate, refreshInterval;
	
	/**
	 * Constructor for the game thread. Initialize all variables to the proper 
	 * @param tickRate how many times the game logic runs in one second
	 * @param fuel the amount of fuel the rocket has
	 */
	public GameThread(int tickRate, int fuel) {
		this.tickRate = tickRate;
		refreshInterval = 1000 / tickRate;
		accelPerTick = ACCEL / tickRate; // Set the actual acceleration rate based on the tickrate.
		currentFuel = startingFuel = fuel; // Set the current fuel level and the starting fuel level to the fuel parameter.
		gameStarted = false;
		gameOver = false;
		keyPressed = false;
		position = 0.1; // Making this 0.1 makes the game logic run, but the visible rocket position will be 0.
		velocity = 0.0;
	}
	
	/**
	 * Code run when the thread starts.
	 */
	public void run() {
		while (true) {
			try {
				runGameLogic();
				sleep(refreshInterval);
			} catch (InterruptedException e) {
				// This should never happen
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Actual game logic.
	 */
	private void runGameLogic() {
		// Only run game logic if the game is in the default state
		if (gameStarted && !gameOver) {
			// Set velocity and fuel values
			if (keyPressed && currentFuel > 0.0) {
				velocity += accelPerTick;
				currentFuel -= accelPerTick;
			} else {
				velocity -= accelPerTick;
			}
			
			// If the rocket is below 0.0, then the game is over.
			if (position > 0.0) {
				position += velocity / tickRate;
			} else {
				gameStarted = false;
				gameOver = true;
			}
		} else if (!gameStarted) {
			gameStarted = keyPressed;
		}
	}

	/**
	 * Reset all values to default so you can replay the game.
	 */
	public void resetGame() {
		gameStarted = false;
		gameOver = false;
		currentFuel = startingFuel;
		velocity = 0.0;
		position = 0.1;
	}

	/**
	 * @param keyPressed tell the game whether the spacebar is pressed
	 */
	public void setKeyPressed(boolean keyPressed) {
		this.keyPressed = keyPressed;
	}

	/**
	 * @return the speed of the rocket
	 */
	public int getSpeed() {
		return (int) Math.abs(velocity);
	}

	/**
	 * @return the current fuel level
	 */
	public int getCurrentFuel() {
		return (int) currentFuel;
	}

	/**
	 * @return whether the game has ended
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * @return the rocket's position on screen
	 */
	public int getPosition() {
		return (int) position;
	}

}
