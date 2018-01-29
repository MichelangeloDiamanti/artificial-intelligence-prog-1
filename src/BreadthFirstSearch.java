import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch
{
	EnvironmentState currentState;
	TreeNode<EnvironmentState> root;
	Queue<TreeNode<EnvironmentState>> frontier;
	TreeNode<EnvironmentState> cState;
	HashSet<EnvironmentState> explored;
	int timeComplexity = 0;

	public BreadthFirstSearch(EnvironmentState initialState)
	{
		this.currentState = initialState;
		root = new TreeNode<EnvironmentState>(currentState);
		frontier = new LinkedList<TreeNode<EnvironmentState>>();
		explored = new HashSet<EnvironmentState>();
	}

	public TreeNode<EnvironmentState> bfs()
	{
		int max = 0;
		long startTime = System.currentTimeMillis();
		TreeNode<EnvironmentState> finalState = this.root;
		if (finalState.getData().isFinalState())
			return finalState;

		frontier.add(finalState);

		while (true)
		{
			if (frontier.isEmpty())
			{
				finalState = null;
				break;
			}
			if (frontier.size() > max)
			{
				max = frontier.size();
			}
			cState = frontier.poll();
			currentState = cState.getData();

			explored.add(currentState);

			List<String> legalMoves = currentState.legalMoves();
			
			for (String move : legalMoves)
			{
				EnvironmentState nextState = null;
				TreeNode<EnvironmentState> nextNode = null;
				switch (move)
				{
				case "TURN_ON":
					nextState = currentState.toogleStatusOfTheRobot();
					break;
				case "TURN_OFF":
					nextState = currentState.toogleStatusOfTheRobot();
					break;
				case "SUCK":
					nextState = currentState.removeDirt(currentState.getLocationOfTheRobot());
					break;
				case "TURN_LEFT":
					nextState = currentState.turnLeft();
					break;
				case "TURN_RIGHT":
					nextState = currentState.turnRigth();
					break;
				case "GO":
					nextState = currentState.go();
					break;

				default:
					break;
				}
				nextNode = new TreeNode<EnvironmentState>(nextState, move);
				cState.addChild(nextNode, move);
				if (explored.contains(nextState) == false && frontier.contains(nextNode) == false)
				{
					if (nextState.isFinalState())
					{
						long stopTime = System.currentTimeMillis();
						long elapsedTime = stopTime - startTime;
						System.err.println("Execution time: " + elapsedTime);
						System.err.println("Time Complexity: " + timeComplexity);
						System.err.println("Max frontier size: " + max);
						return nextNode;
					}
					frontier.add(nextNode);
					timeComplexity ++;
				}

			}
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Execution time: " + elapsedTime);
		System.out.println("Time Complexity: "+ timeComplexity);
		System.out.println("Max frontier size: "+ max);
		return finalState;
	}
}
