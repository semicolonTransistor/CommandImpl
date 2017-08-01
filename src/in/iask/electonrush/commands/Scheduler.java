package in.iask.electonrush.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scheduler {
	
	private static Scheduler schedulerInstance = new Scheduler();
	
	List<Command> activeCommands;
	Map<Subsystem,Command> currentCommands;
	Map<Subsystem,Command> defaultCommands;
	
	private Scheduler() {
		currentCommands = new HashMap<Subsystem,Command>();
		defaultCommands = new HashMap<Subsystem,Command>();
		activeCommands = new ArrayList<Command>(); 
	}
	
	public void setSubsystemDefaultCommand(Subsystem subsystem,Command defaultCommand){
		this.defaultCommands.put(subsystem, defaultCommand);
	}
	
	public void startCommand(Command command) {
		startCommand(command,command.getRequiredSubsystems());
	}
	
	protected void startCommand(Command command, List<Subsystem> requiredSubsystems) {
		for(Subsystem subsystem:requiredSubsystems) {
			Command competingCommand = currentCommands.get(subsystem);
			if(competingCommand != null) {
				interruptCommand(competingCommand);
			}
			currentCommands.put(subsystem, command);
		}
		activeCommands.add(command);
	}
	
	public void interruptCommand(Command command) {
		command.interrupt();
		removeCommand(command);
	}
	
	protected void removeCommand(Command command) {
		Set<Map.Entry<Subsystem,Command>> entries = currentCommands.entrySet();
		for(Map.Entry<Subsystem,Command> entry : entries) {
			if(entry.getValue() == command) {
				currentCommands.remove(entry.getKey());
			}
		}
		activeCommands.remove(command);
	}
	
	public void run() {
		for (int index = 0; index < activeCommands.size(); index ++) {
			boolean finished = activeCommands.get(index).run();
			if(finished) {
				removeCommand(activeCommands.get(index));
			}
		}
		for(Subsystem subsystem:defaultCommands.keySet()) {
			if(!currentCommands.containsKey(subsystem)) {
				Command command = defaultCommands.get(subsystem);
				if(command != null) {
					List<Subsystem> subsystemList = new ArrayList<Subsystem>();
					subsystemList.add(subsystem);
					startCommand(command,subsystemList);
				}
			}
		}
	}
	
	public static Scheduler getInstance() {
		return schedulerInstance;
	}
	
}
