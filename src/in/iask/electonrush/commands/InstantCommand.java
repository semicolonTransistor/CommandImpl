package in.iask.electonrush.commands;

/**
 * The InstantCommand is a convenient way of writing a command that runs only once.
 * Its {@link InstantCommand#isFinished()} method always returns true, thus {@link InstantCommand#execute()} ran only once.
 * 
 * @see Command
 * 
 * @author ElectricFish
 *
 */
public abstract class InstantCommand extends Command {
	
	/**
	 * default method, does nothing.
	 * 
	 * @see Command#initialize()
	 */
	@Override
	protected void initialize() {
		//Do nothing here

	}
	/**
	 * This method is called once in InstantCommand. implement this method to perform a command only once.
	 */
	protected abstract void execute();
	
	/**
	 * As this command is designed to only run once, this method always returns true.
	 * 
	 * @see Command#isFinished()
	 * 
	 * @return true.
	 */
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	/**
	 * default method, does nothing.
	 * 
	 * @see Command#end()
	 */
	@Override
	protected void end() {
		//Do nothing here also

	}

	/**
	 * default method, does nothing.
	 * Honestly I don't think this method will ever be called.
	 * 
	 * @see Command#interrupted()
	 */
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
