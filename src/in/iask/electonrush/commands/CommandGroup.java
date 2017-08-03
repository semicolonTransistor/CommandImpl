package in.iask.electonrush.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The CommandGroup Class is a easy way to execute a group of commands together.
 * @author ElectricFish
 *
 */
public abstract class CommandGroup extends Command{
	
	protected class CommandEntry {
		Command command;
		boolean waitForPreviousCommandsCompletion;
	}
	
	Queue<CommandEntry> commandQueue = new LinkedList<CommandEntry>();
	Queue<CommandEntry> workingCommandQueue;
	List<Command> activeCommands;
	
	@Override
	protected void initialize() {
		//resets working command queue and active command list
		workingCommandQueue = new LinkedList<CommandEntry>(commandQueue);
		activeCommands = new ArrayList<Command>();
		
	}

	@Override
	protected void execute() {
		//if no command is currently active, then load new commands
		if(activeCommands.isEmpty()) {
			loadCommands();
		}
		
		for(int index = 0; index < activeCommands.size(); index++ ) {
			boolean finished = activeCommands.get(index).run();
			if(finished) {
				removeCommand(activeCommands.get(index));
			}
		}
		
		
	}

	@Override
	protected boolean isFinished() {
		return activeCommands.isEmpty() && workingCommandQueue.isEmpty();
	}

	@Override
	protected void end() {
		//nothing to do here as all commands would have ended before this is called
	}

	@Override
	protected void interrupted() {
		//interrupts all active commands is the entire group is interrupted.
		for(int index = 0; index < activeCommands.size(); index++ ) {
			activeCommands.get(index).interrupt();
		}
	}
	/**
	 * This function offers a new command to the command queue.
	 * This function should only be called in the constructor.
	 * 
	 * @param Command the command to add
	 * @param waitForPreviousCommandsCompletion should the command should wait for previous commands to be completed?
	 */
	protected void addCommand(Command command,boolean waitForPreviousCommandsCompletion) {
		CommandEntry entry = new CommandEntry();
		entry.command = command;
		for(Subsystem requiredSubsystem:command.getRequiredSubsystems()) {
			requires(requiredSubsystem);
		}
		entry.waitForPreviousCommandsCompletion = waitForPreviousCommandsCompletion;
		commandQueue.offer(entry);
	}
	
	/**
	 * This function offers a new command to the command queue, this command will wait for all previous commands to finish before executing.
	 * This function should only be called in the constructor.
	 * 
	 * @param Command the command to add
	 */
	protected void addSequential(Command command) {
		addCommand(command,true);
	}
	
	/**
	 * This function offers a new command to the command queue, this command will NOT wait for all previous commands to finish before executing.
	 * This function should only be called in the constructor.
	 * 
	 * @param Command the command to add
	 */
	protected void addParallel(Command command) {
		addCommand(command,false);
	}
	
	
	/*
	 * Loads command into the active command list.
	 * This function should only be called if the active command list is empty.
	 */
	private void loadCommands() {
		//always load one command
		CommandEntry nextEntry = workingCommandQueue.poll();
		if(nextEntry != null) {
			activeCommands.add(nextEntry.command);
			
		}
		
		//continue to load commands until one that requires previous commands to be completed before executing it.
		while(true) {
			nextEntry = workingCommandQueue.peek();
			if(nextEntry != null) {
				if(nextEntry.waitForPreviousCommandsCompletion) {
					break; //if the command requires that currently running commands be completed first, do not load it.
				}else {
					workingCommandQueue.poll(); //removes this command from the queue first.
					activeCommands.add(nextEntry.command); //load the command.
				}
			}else {
				break; //if nextEntry is null, the command queue is empty. Stop attempting to load commands.
			}
		}
	}
	
	private void removeCommand(Command command) {
		activeCommands.remove(command);
	}

}
