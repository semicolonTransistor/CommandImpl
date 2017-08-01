package in.iask.electonrush.commandsTest;

import in.iask.electonrush.commands.Scheduler;

public class CommandsTest {
	
	static TestSubsystem1 testSubsystem1;

	public static void main(String[] args) throws InterruptedException {
		testSubsystem1 = new TestSubsystem1();
		Scheduler.getInstance().setSubsystemDefaultCommand(testSubsystem1, new TestCommand11());
		
		long timeStarted = System.currentTimeMillis();
		boolean command2run = false;
		while(true) {
			Thread.sleep(500);
			if(System.currentTimeMillis() - timeStarted >= 5000 && !command2run) {
				new TestCommand12().start();
				command2run = true;
			}
			Scheduler.getInstance().run();
		}
		
	}

}

