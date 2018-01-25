import java.util.ArrayList;
import java.util.List;

public class EnvironmentState
{

	private Coordinate locationOfTheRobot;
	private Orientation orientationOfTheRobot;
	private List<Coordinate> locationOfDirts;
	private boolean statusOfTheRobot;

	public EnvironmentState(Coordinate locationOfTheRobot, Orientation orientationOfTheRobot,
			List<Coordinate> locationOfDirts, boolean statusOfTheRobot)
	{
		super();
		this.locationOfTheRobot = locationOfTheRobot;
		this.orientationOfTheRobot = orientationOfTheRobot;
		this.locationOfDirts = locationOfDirts;
		this.statusOfTheRobot = statusOfTheRobot;
	}

	public List<String> legalMoves()
	{
		List<String> legalMoves = new ArrayList<String>();
		// check if TURN_ON is legal
		if (statusOfTheRobot == false)
			legalMoves.add("TURN_ON");

		// check if TURN_OFF is legal
		if (canTurnOff() == true)
			legalMoves.add("TURN_OFF");

		// check if SUCK action is legal
		if (canSuck() == true)
			legalMoves.add("SUCK");
		if (canGo() == true)
			legalMoves.add("GO");
		// check if TURN_RIGHT/TURN_LEFT is legal
		if (statusOfTheRobot == true)
		{
			legalMoves.add("TURN_RIGHT");
			legalMoves.add("TURN_LEFT");
		}
		// check if GO action is legal
		

		return legalMoves;
	}

	public boolean canGo()
	{
		// check if the robot is powered on
		if (statusOfTheRobot == false)
			return false;
		
		boolean canGo = false;

		Coordinate nextLocationOfTheRobot = nextLocationOfTheRobot();
		// check if moving the robot keeps it inside the grid
		if (nextLocationOfTheRobot.x <= Environment.widthOfTheGrid
				&& nextLocationOfTheRobot.y <= Environment.heightOfTheGrid && nextLocationOfTheRobot.x > 0
				&& nextLocationOfTheRobot.y > 0)
		{

			// check if moving the robot will make it bump into an obstacle
			if (Environment.locationOfObstacles.contains(nextLocationOfTheRobot) == false)
				canGo = true;
		}

		return canGo;
	}

	public Coordinate nextLocationOfTheRobot()
	{
		Coordinate nextLocationOfTheRobot = new Coordinate(locationOfTheRobot.x, locationOfTheRobot.y);
		switch (orientationOfTheRobot)
		{
		case NORTH:
			nextLocationOfTheRobot.y += 1;
			break;

		case SOUTH:
			nextLocationOfTheRobot.y -= 1;
			break;

		case EAST:
			nextLocationOfTheRobot.x += 1;
			break;

		case WEST:
			nextLocationOfTheRobot.x -= 1;
			break;

		default:
			break;
		}
		return nextLocationOfTheRobot;
	}

	public boolean canTurnOff()
	{
		boolean canTurnOff = false;
		// if robot is ON, there is no dirt around and robot is at home location
		if (statusOfTheRobot == true && locationOfDirts.isEmpty()
				&& locationOfTheRobot.equals(Environment.homeLocation))
			canTurnOff = true;
		return canTurnOff;
	}

	public boolean canSuck()
	{
		boolean canSuck = false;
		// if robot is ON and there is dirt in the current location
		if (statusOfTheRobot == true && locationOfDirts.contains(locationOfTheRobot))
			canSuck = true;
		return canSuck;
	}

	public EnvironmentState go()
	{
		EnvironmentState successorState = this.clone();
		successorState.locationOfTheRobot = nextLocationOfTheRobot();
		return successorState;
	}

	public EnvironmentState turnLeft()
	{
		EnvironmentState successorState = this.clone();
		switch (orientationOfTheRobot)
		{
		case NORTH:
			successorState.orientationOfTheRobot = Orientation.WEST;
			break;
		case SOUTH:
			successorState.orientationOfTheRobot = Orientation.EAST;
			break;
		case EAST:
			successorState.orientationOfTheRobot = Orientation.NORTH;
			break;
		case WEST:
			successorState.orientationOfTheRobot = Orientation.SOUTH;
			break;

		default:
			break;
		}

		return successorState;
	}

	public EnvironmentState turnRigth()
	{
		EnvironmentState successorState = this.clone();
		switch (orientationOfTheRobot)
		{
		case NORTH:
			successorState.orientationOfTheRobot = Orientation.EAST;
			break;
		case SOUTH:
			successorState.orientationOfTheRobot = Orientation.WEST;
			break;
		case EAST:
			successorState.orientationOfTheRobot = Orientation.SOUTH;
			break;
		case WEST:
			successorState.orientationOfTheRobot = Orientation.NORTH;
			break;

		default:
			break;
		}

		return successorState;
	}

	public Coordinate getLocationOfTheRobot()
	{
		return locationOfTheRobot;
	}

	public void setLocationOfTheRobot(Coordinate locationOfTheRobot)
	{
		this.locationOfTheRobot = locationOfTheRobot;
	}

	public Orientation getOrientationOfTheRobot()
	{
		return orientationOfTheRobot;
	}

	public void setOrientationOfTheRobot(Orientation orientationOfTheRobot)
	{
		this.orientationOfTheRobot = orientationOfTheRobot;
	}

	public List<Coordinate> getLocationOfDirts()
	{
		return locationOfDirts;
	}

	public void setLocationOfDirts(List<Coordinate> locationOfDirts)
	{
		this.locationOfDirts = locationOfDirts;
	}

	public EnvironmentState removeDirt(Coordinate locationOfDirt)
	{
		EnvironmentState successorState = this.clone();
		successorState.locationOfDirts.remove(locationOfDirt);
		return successorState;
	}

	public boolean isStatusOfTheRobot()
	{
		return statusOfTheRobot;
	}

	public EnvironmentState toogleStatusOfTheRobot()
	{
		EnvironmentState successorState = this.clone();
		successorState.statusOfTheRobot = !successorState.statusOfTheRobot;
		return successorState;
	}

	public boolean isFinalState() {
		boolean isFinalState = false;
		// if robot is ON, there is no dirt around and robot is at home location
		if (statusOfTheRobot == false && locationOfDirts.isEmpty()
				&& locationOfTheRobot.equals(Environment.homeLocation))
			isFinalState = true;
		return isFinalState;	
	}
	
	@Override
	public String toString()
	{
		return "EnvironmentState [locationOfTheRobot=" + locationOfTheRobot + ", orientationOfTheRobot="
				+ orientationOfTheRobot + ", locationOfDirts=" + locationOfDirts + ", statusOfTheRobot="
				+ statusOfTheRobot + "]";
	}

	@Override
	protected EnvironmentState clone()
	{
		Coordinate newLocationOfTheRobot;
		List<Coordinate> newLocationOfDirts;
		newLocationOfTheRobot = new Coordinate(this.locationOfTheRobot.x, this.locationOfTheRobot.y);
		newLocationOfDirts = new ArrayList<Coordinate>();
		for (Coordinate coordinate : locationOfDirts)
		{
			newLocationOfDirts.add(new Coordinate(coordinate.x, coordinate.y));
		}
		return new EnvironmentState(newLocationOfTheRobot, orientationOfTheRobot, newLocationOfDirts, statusOfTheRobot);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locationOfDirts == null) ? 0 : locationOfDirts.hashCode());
		result = prime * result + ((locationOfTheRobot == null) ? 0 : locationOfTheRobot.hashCode());
		result = prime * result + ((orientationOfTheRobot == null) ? 0 : orientationOfTheRobot.hashCode());
		result = prime * result + (statusOfTheRobot ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvironmentState other = (EnvironmentState) obj;
		if (locationOfDirts == null) {
			if (other.locationOfDirts != null)
				return false;
		} else if (!locationOfDirts.equals(other.locationOfDirts))
			return false;
		if (locationOfTheRobot == null) {
			if (other.locationOfTheRobot != null)
				return false;
		} else if (!locationOfTheRobot.equals(other.locationOfTheRobot))
			return false;
		if (orientationOfTheRobot != other.orientationOfTheRobot)
			return false;
		if (statusOfTheRobot != other.statusOfTheRobot)
			return false;
		return true;
	}

	
	
}
