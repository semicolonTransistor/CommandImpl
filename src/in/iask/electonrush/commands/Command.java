package in.iask.electonrush.commands;

import java.util.HashSet;
import java.util.Set;

/**
 * The Command class defines a command that can be submitted to the scheduler for execution.
 * Commands may require one or more {@link Subsystem}.
 * When a Command is started, all Commands that requires one or more {@link Subsystem} that this command requires will be canceled.
 * A Timeout may be provided to the constructor or set by calling {@link Command#setTimeout(int)} or {@link Command#setTimeout(double)}.
 * This timeout has NO effect on the execution of the command, it only effects {@link Command#isTimedOut()}.
 * If running a command for a specific period of time is desired, use {@link TimedCommand} instead.
 * @author ElectricFish
 *
 */
public abstract class Command {
	
	private boolean initialized = false;
	private Set<Subsystem> requiredSubsystems = new HashSet<Subsystem>();
	private int timeout = Integer.MAX_VALUE; //in milliseconds;
	private long timeInitalized; //in milliseconds since Jan 1, 1970
	
	public Command() {
		/*
		 * Default Constructor, do nothing; 
		 */
	}
	
	/**
	 * Construct the command with a timeout.
	 * @param timeoutInMilliseconds the value of the timeout in milliseconds.
	 */
	public Command(int timeoutInMilliseconds) {
		this.timeout = timeoutInMilliseconds;
	}
	
	/**
	 * Construct the command with a timeout.
	 * @param timeout the value of the timeout in seconds.
	 */
	public Command(double timeout) {
		this.timeout = (int) (timeout*1000);
	}
	
	/**
	 * This function is called once at the start of the command.
	 */
	protected abstract void initialize();
	
	/**
	 * This function is repeated while the command is active.
	 */
	protected abstract void execute();
	
	/**
	 * make this return true when the command no longer needs to run.
	 * @return is the command finished
	 */
	protected abstract boolean isFinished();
	
	/**
	 * This function is called once after the command ends.
	 */
	protected abstract void end();
	
	/**
	 * This function is called once after the command interrupted.
	 */
	protected abstract void interrupted();
	
	/**
	 * Adds a subsystem to the list subsystems that this command requires.
	 * This function should only be called in the constructor.
	 * @param subsystem the subsystem to add
	 */
	protected void requires(Subsystem subsystem) {
		requiredSubsystems.add(subsystem);
	}
	
	/**
	 * submits the command to the Scheduler for execution
	 */
	public void start() {
		Scheduler.getInstance().startCommand(this);
	}
	/*
	 * this method is called by the scheduler every time Schdueler.run() is called while the command is active. 
	 * Returns true if command is finished, otherwise returns false.
	 */
	boolean run() {
		if(!initialized) {
			initialize();
			timeInitalized = System.currentTimeMillis();
			initialized = true;
		}
		execute();
		if(isFinished()) {
			end();
			initialized = false;
			return true;
		}else {
			return false;
		}
	}
	
	public void cancel() {
		interrupted();
		initialized = false;
	}
	
	/**
	 * Returns how many milliseconds has elapsed since the command was initialized according to the system clock.
	 * @return milliseconds since the command was initialized;
	 */
	public long millisecondsSinceInitalized() {
		return System.currentTimeMillis() - timeInitalized;
	}
	
	/**
	 * Returns how many seconds has elapsed since the command was initialized according to the system clock.
	 * @return seconds since the command was initialized
	 */
	public double timeSinceInitialized() {
		return millisecondsSinceInitalized()/1000.0;
	}
	
	/**
	 * sets the command timeout, it does not affect anything other than the isTimedOut Method.
	 * @param timeoutInMilliseconds the timeout to set, in milliseconds.
	 */
	public void setTimeout(int timeoutInMilliseconds) {
		this.timeout = timeoutInMilliseconds;
	}
	
	/**
	 * sets the command timeout, it does not affect anything other than the isTimedOut Method.
	 * @param timeout the timeout to set, in seconds.
	 */
	public void setTimeout(double timeout) {
		setTimeout((int)(timeout*1000));
	}
	
	/**
	 * returns true if more time has passed since the command was initialized than the timeout.
	 * @return if more time has passed since the command initialized than the timeout.
	 */
	public boolean isTimedOut() {
		return millisecondsSinceInitalized() >= timeout;
	}
	
	/**
	 * returns a set of subsystems that this command requires.
	 * @return a set of subsystems that this command requires.
	 */
	Set<Subsystem> getRequiredSubsystems() {
		return this.requiredSubsystems;
	}
	
	
	
}
