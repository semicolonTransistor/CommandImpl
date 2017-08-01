package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.Command;

public class TestCommand12 extends Command {
	long delay = 4000;
	
	long timeStarted;
	
	TestCommand12(){
		requires(CommandsTest.testSubsystem1);
	}

	@Override
	protected void initialize() {
		timeStarted = System.currentTimeMillis();
		System.out.println("Test Command 1-2 initialized");

	}

	@Override
	protected void execute() {
		System.out.println("Test Command 1-2 executed");

	}

	@Override
	protected boolean isFinished() {
		return System.currentTimeMillis() - timeStarted > delay;
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
