package in.iask.electonrush.commands;

/**
 * The waitCommand just waits until the timeout expires. 
 * It is useful in delaying command execution in a command Group.
 * @author ElectricFish
 *
 */
public class WaitCommand extends TimedCommand {

	public WaitCommand(int timeoutInMilliseconds) {
		super(timeoutInMilliseconds);
	}

	public WaitCommand(double timeout) {
		super(timeout);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
