import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class AStarSearch
{
	EnvironmentState currentState;
	PriorityTreeNode<EnvironmentState> root;
	PriorityQueue<PriorityTreeNode<EnvironmentState>> frontier;
	Comparator<PriorityTreeNode<EnvironmentState>> comparator;
	PriorityTreeNode<EnvironmentState> cState;
	HashSet<EnvironmentState> explored;
	int timeComplexity = 0;

	public AStarSearch(EnvironmentState initialState)
	{
		this.currentState = initialState;
		root = new PriorityTreeNode<EnvironmentState>(currentState);

		comparator = new MoveComparator();
		frontier = new PriorityQueue<PriorityTreeNode<EnvironmentState>>(comparator);
		explored = new HashSet<EnvironmentState>();
	}

	public PriorityTreeNode<EnvironmentState> ass()
	{
		int max = 0;
		long startTime = System.currentTimeMillis();

		EnvironmentState initialState = this.root.getData().toogleStatusOfTheRobot();

		PriorityTreeNode<EnvironmentState> finalState = new PriorityTreeNode<EnvironmentState>(initialState,
				new Pair<String, Integer>("TURN_ON", 1));
		root.addChild(finalState, finalState.getAction());
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
			if (cState.getData().isFinalStateWithCost())
			{
				long stopTime = System.currentTimeMillis();
				long elapsedTime = stopTime - startTime;
				System.err.println("Execution time: " + elapsedTime);
				System.err.println("Time Complexity: " + timeComplexity);
				System.err.println("Max frontier size: " + max);
				return cState;
			}
//			System.out.println(frontier.size());
			currentState = cState.getData();
			explored.add(currentState);

			List<Pair<String, Integer>> legalMoves = currentState.euristicWeightedMoves();
			
			for (Pair<String, Integer> move : legalMoves)
			{
				EnvironmentState nextState = null;
				PriorityTreeNode<EnvironmentState> nextNode = null;
				switch (move.getFirst())
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

				nextNode = new PriorityTreeNode<EnvironmentState>(nextState, move);
				cState.addChild(nextNode, move);

				boolean frontierContainsChild = frontier.contains(nextNode);

				if (explored.contains(nextState) == false && frontierContainsChild == false)
				{
					frontier.add(nextNode);
					timeComplexity ++;
				}
				else if (frontierContainsChild == true)
				{
					PriorityQueue<PriorityTreeNode<EnvironmentState>> pq = new PriorityQueue<PriorityTreeNode<EnvironmentState>>(
							comparator);
					PriorityTreeNode<EnvironmentState> node = null;
					while (frontier.isEmpty() == false)
					{
						node = frontier.poll();
						if (node.equals(nextNode) && node.getAction().getSecond() > nextNode.getAction().getSecond())
							pq.add(nextNode);
						else
							pq.add(node);
					}
					frontier.addAll(pq);
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
