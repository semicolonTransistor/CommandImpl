package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.CommandGroup;

public class TestCommandGroup1 extends CommandGroup {
	public TestCommandGroup1() {
		addSequential(new TestCommand12());
		addParallel(new TestCommand22());
		addSequential(new TestCommand22());
	}
}
