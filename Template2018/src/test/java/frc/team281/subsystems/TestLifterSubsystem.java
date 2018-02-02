package frc.team281.subsystems;

import frc.team281.robot.subsystems.BaseLifterSubsystem;

/**
 * Test lifter system. This system keeps track of the position, and uses a
 * datalogger to report when it moves.
 * 
 * Note that testsubsystems should be defined in src/test, so that they are not
 * deployed to the robot.
 * 
 * @author dcowden
 *
 */
public class TestLifterSubsystem extends BaseLifterSubsystem {

	public static final int POSITION_BOTTOM = 0;

	private int position = POSITION_BOTTOM;
	private int maxPosition = 0;

	public TestLifterSubsystem(int maxPosition) {
		super();
		this.maxPosition = maxPosition;
	}

	private void moveUp() {
		if (position < maxPosition) {
			this.position += 1;
		}
	}

	private void moveDown() {
		if (position > POSITION_BOTTOM) {
			this.position -= 1;
		}
	}

	@Override
	public void raise() {
		moveUp();
		dataLogger.log("raise:position", this.position);

	}

	@Override
	public void lower() {
		moveDown();
		dataLogger.log("lower:position", this.position);
	}

	@Override
	public boolean isAtTop() {
		return this.position >= this.maxPosition;
	}

	@Override
	public boolean isAtBottom() {
		return this.position <= POSITION_BOTTOM;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
