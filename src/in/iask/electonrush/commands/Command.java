package in.iask.electonrush.commands;

import java.util.HashSet;
import java.util.Set;

/**
 * The command class defines a command that can be submitted to the scheduler for execution.
 * Commands may require one or more subsystems.
 * When a command is started, all commands that requires one or more subsystems that this command requires will be interrupted.
 * @author ElectricFish
 *
 */
public abstract class Command {
	
	private boolean initialized = false;
	private Set<Subsystem> requiredSubsystems = new HashSet<Subsystem>();
	
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
	
	void interrupt() {
		interrupted();
		initialized = false;
	}
	
	Set<Subsystem> getRequiredSubsystems() {
		return this.requiredSubsystems;
	}
	
	
	
}
