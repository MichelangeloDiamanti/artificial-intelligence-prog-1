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
		EnvironmentState successorState = null;
		if (canGo() == true)
		{
			Coordinate nextLocationOfTheRobot = nextLocationOfTheRobot();
			successorState = new EnvironmentState(nextLocationOfTheRobot, orientationOfTheRobot, locationOfDirts,
					statusOfTheRobot);
		}
		return successorState;
	}

	public EnvironmentState turnLeft()
	{
		EnvironmentState successorState = null;
		switch (orientationOfTheRobot)
		{
		case NORTH:
			successorState = new EnvironmentState(locationOfTheRobot, Orientation.WEST, locationOfDirts,
					statusOfTheRobot);
			break;
		case SOUTH:
			successorState = new EnvironmentState(locationOfTheRobot, Orientation.EAST, locationOfDirts,
					statusOfTheRobot);
			break;
		case EAST:
			successorState = new EnvironmentState(locationOfTheRobot, Orientation.NORTH, locationOfDirts,
					statusOfTheRobot);
			break;
		case WEST:
			successorState = new EnvironmentState(locationOfTheRobot, Orientation.SOUTH, locationOfDirts,
					statusOfTheRobot);
			break;

		default:
			break;
		}

		return successorState;
	}

	public EnvironmentState turnRigth()
	{
		EnvironmentState successorState = null;
		switch (orientationOfTheRobot)
		{
		case NORTH:
			successorState = new EnvironmentState(locationOfTheRobot, Orientation.EAST, locationOfDirts,
					statusOfTheRobot);
			break;
		case SOUTH:
			successorState = new EnvironmentState(locationOfTheRobot, Orientation.WEST, locationOfDirts,
					statusOfTheRobot);
			break;
		case EAST:
			successorState = new EnvironmentState(locationOfTheRobot, Orientation.SOUTH, locationOfDirts,
					statusOfTheRobot);
			break;
		case WEST:
			successorState = new EnvironmentState(locationOfTheRobot, Orientation.NORTH, locationOfDirts,
					statusOfTheRobot);
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
		EnvironmentState successorState = null;
		locationOfDirts.remove(locationOfDirt);
		successorState = new EnvironmentState(locationOfTheRobot, orientationOfTheRobot, locationOfDirts,
				!statusOfTheRobot);
		return successorState;
	}

	public boolean isStatusOfTheRobot()
	{
		return statusOfTheRobot;
	}

	public EnvironmentState toogleStatusOfTheRobot()
	{
		EnvironmentState successorState = null;
		successorState = new EnvironmentState(locationOfTheRobot, orientationOfTheRobot, locationOfDirts,
				!statusOfTheRobot);
		return successorState;
	}

}
