package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.Command;

public class TestCommand11 extends Command {
	
	TestCommand11(){
		requires(CommandsTest.testSubsystem1);
	}

	@Override
	protected void initialize() {
		System.out.println("Test Command 1-1 initialized");

	}

	@Override
	protected void execute() {
		System.out.println("Test Command 1-1 executed");

	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		System.out.println("Test Command 1-1 ended");

	}

	@Override
	protected void interrupted() {
		System.out.println("Test Command 1-1 interrupted");

	}

}
