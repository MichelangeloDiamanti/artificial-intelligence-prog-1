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
		PriorityTreeNode<EnvironmentState> finalState = this.root;

		frontier.add(finalState);

		while (true)
		{
			if (frontier.isEmpty())
			{
				finalState = null;
				break;
			}

			//System.out.println("current frontier size: " + frontier.size());

			cState = frontier.poll();
			if (cState.getData().isFinalState())
				return cState;

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
		return finalState;
	}
}
