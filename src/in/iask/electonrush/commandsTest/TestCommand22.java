package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.Command;

public class TestCommand22 extends Command {
	long delay = 2000;
	
	long timeStarted;
	
	TestCommand22(){
		requires(CommandsTest.testSubsystem2);
	}

	@Override
	protected void initialize() {
		timeStarted = System.currentTimeMillis();
		System.out.println("Test Command 1-3 initialized");

	}

	@Override
	protected void execute() {
		System.out.println("Test Command 1-3 executed");

	}

	@Override
	protected boolean isFinished() {
		return System.currentTimeMillis() - timeStarted > delay;
	}

	@Override
	protected void end() {
		System.out.println("Test Command 1-3 ended");

	}

	@Override
	protected void interrupted() {
		System.out.println("Test Command 1-3 interrupted");

	}

}
