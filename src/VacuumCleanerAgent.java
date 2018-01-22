import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.Tool;

@SuppressWarnings("unused")
public class VacuumCleanerAgent implements Agent
{

	/*
	 * init(Collection<String> percepts) is called once before you have to select
	 * the first action. Use it to find a plan. Store the plan and just execute it
	 * step by step in nextAction.
	 */
	private static String OS = System.getProperty("os.name").toLowerCase();

	private Coordinate locationOfTheRobot;
	private Orientation orientationOfTheRobot;
	private List<Coordinate> locationOfDirts = new ArrayList<Coordinate>();
	private boolean statusOfTheRobot;

	Queue<String> orderedSolution = new LinkedList<String>();
	
	private EnvironmentState environmentState;

	public void init(Collection<String> percepts)
	{
		setupEnvironment(percepts);
		BreadthFirstSearch bFirstSearch = new BreadthFirstSearch(environmentState);
		//List<String> solution = bFirstSearch.searchSolution();
		TreeNode<EnvironmentState> solution = bFirstSearch.bfs();
		if(solution == null) System.out.println("There is no solution");
		else {
			System.out.println("Solution node: " + solution.getData());
			
			List<String> solutionStrings = new ArrayList<String>();
			while (!solution.isRoot())
			{
				solutionStrings.add(solution.getAction());
				solution = solution.getParent();
			}
			for(int i = solutionStrings.size(); i > 0; i--) {
				orderedSolution.add(solutionStrings.get(i-1));
			}
			System.out.println(orderedSolution);
		}
		
	}

	public String nextAction(Collection<String> percepts)
	{
		return orderedSolution.poll();
	}

	public static boolean isMac()
	{
		return (OS.indexOf("mac") >= 0);
	}

	private void setupEnvironment(Collection<String> percepts)
	{
		/*
		 * Possible percepts are: - "(SIZE x y)" denoting the size of the environment,
		 * where x,y are integers - "(HOME x y)" with x,y >= 1 denoting the initial
		 * position of the robot - "(ORIENTATION o)" with o in {"NORTH", "SOUTH",
		 * "EAST", "WEST"} denoting the initial orientation of the robot - "(AT o x y)"
		 * with o being "DIRT" or "OBSTACLE" denoting the position of a dirt or an
		 * obstacle Moving north increases the y coordinate and moving east increases
		 * the x coordinate of the robots position. The robot is turned off initially,
		 * so don't forget to turn it on.
		 */
		Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
		for (String percept : percepts)
		{
			Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
			if (perceptNameMatcher.matches())
			{
				String perceptName = perceptNameMatcher.group(1);
				if (perceptName.equals("HOME"))
				{
					Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if (m.matches())
					{
						System.out.println("robot is at " + m.group(1) + "," + m.group(2));
						Environment.homeLocation = new Coordinate(Integer.parseInt(m.group(1)),
								Integer.parseInt(m.group(2)));
						locationOfTheRobot = Environment.homeLocation.clone();
					}
				}
				else if (perceptName.equals("ORIENTATION"))
				{
					Matcher m = Pattern.compile("\\(\\s*ORIENTATION\\s(NORTH|SUD|EAST|WEST)*\\)").matcher(percept);
					if (m.matches())
					{
						switch (m.group(1))
						{
						case "NORTH":
							orientationOfTheRobot = Orientation.NORTH;
							break;
						case "EAST":
							orientationOfTheRobot = Orientation.EAST;
							break;
						case "WEST":
							orientationOfTheRobot = Orientation.WEST;
							break;
						case "SOUTH":
							orientationOfTheRobot = Orientation.SOUTH;
							break;
						default:
							break;
						}
						System.out.println("robot is orientated at " + orientationOfTheRobot);
					}
				}
				else if (perceptName.equals("SIZE"))
				{
					Matcher m = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if (m.matches())
					{
						System.out.println("The size of the grid is " + m.group(1) + "," + m.group(2));
						Environment.widthOfTheGrid = Integer.parseInt(m.group(1));
						Environment.heightOfTheGrid = Integer.parseInt(m.group(2));
					}
				}
				else
				{
					if (percept.contains("DIRT"))
					{
						Matcher m1 = Pattern.compile("\\(\\s*AT\\sDIRT\\s+([0-9]+)\\s+([0-9]+)\\s*\\)")
								.matcher(percept);
						if (m1.matches())
						{
							System.out.println("dirt is at " + m1.group(1) + "x" + m1.group(2));
							locationOfDirts
									.add(new Coordinate(Integer.parseInt(m1.group(1)), Integer.parseInt(m1.group(2))));
						}
					}
					else if (percept.contains("OBSTACLE"))
					{
						Matcher m1 = Pattern.compile("\\(\\s*AT\\sOBSTACLE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)")
								.matcher(percept);
						if (m1.matches())
						{
							System.out.println("obstacle is at " + m1.group(1) + "," + m1.group(2));
							Environment.locationOfObstacles
									.add(new Coordinate(Integer.parseInt(m1.group(1)), Integer.parseInt(m1.group(2))));
						}
					}
					else
					{
						System.out.println("other percept:" + percept);
					}
				}
			}
			else
			{
				System.err.println("strange percept that does not match pattern: " + percept);
			}
		}
		environmentState = new EnvironmentState(locationOfTheRobot, orientationOfTheRobot, locationOfDirts,
				statusOfTheRobot);
	}
}
