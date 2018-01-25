import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch
{
	EnvironmentState currentState;
	TreeNode<EnvironmentState> root;
	Stack<TreeNode<EnvironmentState>> frontier;
	TreeNode<EnvironmentState> cState;
	List<TreeNode<EnvironmentState>> visitedNodes;

	public DepthFirstSearch(EnvironmentState initialState)
	{
		this.currentState = initialState;
		root = new TreeNode<EnvironmentState>(currentState);
		frontier = new Stack<TreeNode<EnvironmentState>>();
		visitedNodes = new ArrayList<TreeNode<EnvironmentState>>();
		// System.out.println("init: " + initialState.toString());
	}

	public TreeNode<EnvironmentState> dfs()
	{
		TreeNode<EnvironmentState> finalState = this.root;
		if (finalState.getData().isFinalState())
			return finalState;

		frontier.push(finalState);
		visitedNodes.add(finalState);

		while (true)
		{
			if (frontier.isEmpty())
			{
				finalState = null;
				break;
			}
			// for (TreeNode<EnvironmentState> treeNode : frontier) {
			// System.out.println(treeNode.getData());
			// }

			cState = frontier.pop();
			int length = legthOfTree(cState);
			System.out.println("current frontier size: " + frontier.size() + " Length: " + length);
			if (length > 1100)
			{
				cState = frontier.pop();
			}
			currentState = cState.getData();

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
				if (nextState.isFinalState())
					return nextNode;
				if (visitedNodes.contains(nextNode) == false)
				{
					visitedNodes.add(nextNode);
					frontier.push(nextNode);
				}
			}
		}
		return finalState;
	}

	private int legthOfTree(TreeNode<EnvironmentState> node)
	{
		int number = 0;
		TreeNode<EnvironmentState> currentNode = node.getParent();
		number++;
		if (currentNode != null)
		{
			while (currentNode.getParent() != null)
			{
				number++;
				currentNode = currentNode.getParent();
			}
		}

		return number;
	}
}
