import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch
{
	EnvironmentState currentState;
	TreeNode<EnvironmentState> root;
	Queue<TreeNode<EnvironmentState>> frontier;
	TreeNode<EnvironmentState> cState;
	List<TreeNode<EnvironmentState>> visitedNodes;
	
	public BreadthFirstSearch(EnvironmentState initialState)
	{
		this.currentState = initialState;
		root = new TreeNode<EnvironmentState>(currentState);
		frontier = new LinkedList<TreeNode<EnvironmentState>>();
		visitedNodes = new ArrayList<TreeNode<EnvironmentState>>();
//		System.out.println("init: " + initialState.toString());
	}

	public TreeNode<EnvironmentState> bfs(){
		TreeNode<EnvironmentState> finalState = this.root;
		if(finalState.getData().isFinalState()) return finalState;
		
		frontier.add(finalState);
		visitedNodes.add(finalState);
		
		while (true) {
			if(frontier.isEmpty()) {
				finalState = null;
				break;
			}
			System.out.println("current frontier size: " + frontier.size());
//			for (TreeNode<EnvironmentState> treeNode : frontier) {
//				System.out.println(treeNode.getData());
//			}
			
			cState = frontier.poll();
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
				if(nextState.isFinalState()) return nextNode;
				if(visitedNodes.contains(nextNode) == false) {
					visitedNodes.add(nextNode);
					frontier.add(nextNode);					
				}
			}			
		}	
		return finalState;
	}
	
	public List<String> searchSolution()
	{
		while (!currentState.canTurnOff())
		{
			cState = frontier.poll();
			currentState = cState.getData();
			List<String> legalMoves = currentState.legalMoves();
			List<TreeNode<EnvironmentState>> childs = new ArrayList<TreeNode<EnvironmentState>>();
			for (String move : legalMoves)
			{
				EnvironmentState nextState = null;
				TreeNode<EnvironmentState> nextNode = null;
				switch (move)
				{
				case "TURN_ON":
					nextState = currentState.toogleStatusOfTheRobot();
					nextNode = new TreeNode<EnvironmentState>(nextState, move);
					break;
				case "TURN_OFF":
					nextState = currentState.toogleStatusOfTheRobot();
					nextNode = new TreeNode<EnvironmentState>(nextState, move);
					break;
				case "SUCK":
					nextState = currentState.removeDirt(currentState.getLocationOfTheRobot());
					nextNode = new TreeNode<EnvironmentState>(nextState, move);
					break;
				case "TURN_LEFT":
					nextState = currentState.turnLeft();
					nextNode = new TreeNode<EnvironmentState>(nextState, move);
					break;
				case "TURN_RIGHT":
					nextState = currentState.turnRigth();
					nextNode = new TreeNode<EnvironmentState>(nextState, move);
					break;
				case "GO":
					nextState = currentState.go();
					nextNode = new TreeNode<EnvironmentState>(nextState, move);
					break;

				default:
					break;
				}
				if (nextState != null)
				{
					childs.add(nextNode);
					frontier.add(nextNode);
				}
			}
			cState.addChildren(childs);
		}
		System.out.println(currentState.toString());
		List<String> solution = new ArrayList<String>();
		while (!cState.isRoot())
		{
			solution.add(cState.getAction());
			cState = cState.getParent();
		}
		List<String> orderedSolution = new ArrayList<String>();
		for(int i = solution.size(); i > 0; i--) {
			orderedSolution.add(solution.get(i-1));
		}
		System.out.println(orderedSolution);
		
		return orderedSolution;
	}
}
