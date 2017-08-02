package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.CommandGroup;

public class TestCommandGroup1 extends CommandGroup {
	public TestCommandGroup1() {
		addCommand(new TestCommand12(), true);
		addCommand(new TestCommand22(), false);
	}
}
