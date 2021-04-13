
public class GameThread extends Thread {
	
	private final double ACCEL = 98;
	private int screenStartPosition, tickRate, refreshInterval;
	private boolean gameStarted, gameOver, keyPressed;
	private double position, velocity, accelPerTick;

	public GameThread(int tickRate, int fuel) {
		screenStartPosition = 496; // I have no idea where this value came from, it just happened to work.
		this.tickRate = tickRate;
		refreshInterval = 1000 / tickRate;
		accelPerTick = ACCEL / tickRate;
		gameStarted = false;
		gameOver = false;
		keyPressed = false;
		position = 0.1;
		velocity = 0.0;
	}
	
	public void run() {

		while (true) {
			try {
				Thread.sleep(refreshInterval);
				if (gameStarted && !gameOver) {
					velocity = keyPressed ? velocity + accelPerTick : velocity - accelPerTick;
					
					if (position > 0.0) {
						position += velocity / tickRate;
					} else {
						gameStarted = false;
						gameOver = true;
					}
				} else if (!gameStarted) {
					gameStarted = keyPressed;
				}
				
				
			} catch (InterruptedException e) {
				// This should never happen
				e.printStackTrace();
			}
		}
	}

	public void resetGame() {
		gameStarted = false;
		gameOver = false;
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
	 * @return the rocket's position on screen
	 */
	public int getScreenPosition() {
		return -1 * (int) position + screenStartPosition;
	}

}
