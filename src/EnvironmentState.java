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
		// check if TURN_RIGHT/TURN_LEFT is legal
		if (statusOfTheRobot == true)
		{
			legalMoves.add("TURN_RIGHT");
			legalMoves.add("TURN_LEFT");
		}
		// check if GO action is legal
		if (canGo() == true)
			legalMoves.add("GO");

		return legalMoves;
	}

	public boolean canGo()
	{
		boolean canGo = false;
		Coordinate nextLocationOfTheRobot = nextLocationOfTheRobot();

		// check if the robot is powered on
		if (statusOfTheRobot == false)
			return false;

		// check if moving the robot keeps it inside the grid
		if (nextLocationOfTheRobot.x <= Environment.widthOfTheGrid
				&& nextLocationOfTheRobot.y <= Environment.heightOfTheGrid && nextLocationOfTheRobot.x >= 0
				&& nextLocationOfTheRobot.y >= 0)
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

}
