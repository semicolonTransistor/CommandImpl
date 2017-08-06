package in.iask.electonrush.commands;

/**
 * TimedCommand will wait for timeout before ending. It is used to run a Command for a specified time.
 * 
 * @author ElectricFish
 */


public abstract class TimedCommand extends Command {
	
	/**
	 * construct the command with the specified timeout.
	 * @param timeoutInMilliseconds the value of the timeout in milliseconds.
	 */
	public TimedCommand(int timeoutInMilliseconds) {
		super(timeoutInMilliseconds);
	}
	
	/**
	 * Construct the command with a timeout.
	 * @param timeout the value of the timeout in seconds.
	 */
	public TimedCommand(double timeout) {
		super(timeout);
	}
	
	/**
	 * returns true when isTimedOut returns true.
	 * DO NOT OVERRIDE.
	 */
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
}
