package in.iask.electonrush.commands;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
	
	private boolean initialized = false;
	private ArrayList<Subsystem> requiredSubsystems = new ArrayList<Subsystem>();
	
	protected abstract void initialize();
	
	protected abstract void execute();
	
	protected abstract boolean isFinished();
	
	protected abstract void end();
	
	protected abstract void interrupted();
	
	protected void requires(Subsystem subsystem) {
		requiredSubsystems.add(subsystem);
	}
	
	public void start() {
		Scheduler.getInstance().startCommand(this);
	}
	
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
	
	List<Subsystem> getRequiredSubsystems() {
		return this.requiredSubsystems;
	}
	
	
	
}
