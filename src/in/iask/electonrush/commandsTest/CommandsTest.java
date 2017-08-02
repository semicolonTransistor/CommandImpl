package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.Scheduler;

public class CommandsTest {
	
	static TestSubsystem testSubsystem1;
	static TestSubsystem testSubsystem2;

	public static void main(String[] args) throws InterruptedException {
		testSubsystem1 = new TestSubsystem();
		Scheduler.getInstance().setSubsystemDefaultCommand(testSubsystem1, new TestCommand11());
		
		long timeStarted = System.currentTimeMillis();
		boolean command2run = false;
		while(true) {
			Thread.sleep(500);
			if(System.currentTimeMillis() - timeStarted >= 5000 && !command2run) {
				new TestCommandGroup1().start();
				command2run = true;
			}
			Scheduler.getInstance().run();
		}
		
	}

}

