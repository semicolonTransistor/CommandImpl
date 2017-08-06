package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.TimedCommand;

public class TestCommand12 extends TimedCommand {
	
	TestCommand12(){
		super(4.0);
		requires(CommandsTest.testSubsystem1);
	}

	@Override
	protected void initialize() {
		System.out.println("Test Command 1-2 initialized");

	}

	@Override
	protected void execute() {
		System.out.println("Test Command 1-2 executed ," + timeSinceInitialized() + "seconds has passed since initialized.");

	}

	@Override
	protected void end() {
		System.out.println("Test Command 1-2 ended");

	}

	@Override
	protected void interrupted() {
		System.out.println("Test Command 1-2 interrupted");

	}

}
