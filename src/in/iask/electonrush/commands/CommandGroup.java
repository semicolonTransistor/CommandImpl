package in.iask.electonrush.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class CommandGroup extends Command{
	
	protected class CommandEntry {
		Command command;
		boolean waitForPreviousCommandsCompletion;
	}
	
	Queue<CommandEntry> commandQueue = new LinkedList<CommandEntry>();
	List<Command> activeCommands = new ArrayList<Command>();
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
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
		return activeCommands.isEmpty() && commandQueue.isEmpty();
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}
	/**
	 * This function offers a new command to the command queue. This function should only be called in the constructor.
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
	
	
	/*
	 * Loads command into the active command list.
	 * This function should only be called if the active command list is empty.
	 */
	private void loadCommands() {
		//always load one command
		CommandEntry nextEntry = commandQueue.poll();
		if(nextEntry != null) {
			activeCommands.add(nextEntry.command);
			
		}
		
		//continue to load commands until one that requires previous commands to be completed before executing it.
		while(true) {
			nextEntry = commandQueue.peek();
			if(nextEntry != null) {
				if(nextEntry.waitForPreviousCommandsCompletion) {
					break; //if the command requires that currently running commands be completed first, do not load it.
				}else {
					commandQueue.poll(); //removes this command from the queue first.
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
