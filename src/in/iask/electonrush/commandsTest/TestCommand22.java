package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.TimedCommand;

public class TestCommand22 extends TimedCommand {
	
	TestCommand22(){
		super(2.0);
		requires(CommandsTest.testSubsystem2);
	}

	@Override
	protected void initialize() {
		System.out.println("Test Command 1-3 initialized");

	}

	@Override
	protected void execute() {
		System.out.println("Test Command 1-3 executed");

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
