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
	
	public BreadthFirstSearch(EnvironmentState initialState)
	{
		this.currentState = initialState;
		root = new TreeNode<EnvironmentState>(currentState);
		frontier = new LinkedList<TreeNode<EnvironmentState>>();
		explored = new HashSet<EnvironmentState>();
	}

	public TreeNode<EnvironmentState> bfs(){
		TreeNode<EnvironmentState> finalState = this.root;
		if(finalState.getData().isFinalState()) return finalState;
		
		frontier.add(finalState);
		
		while (true) {
			if(frontier.isEmpty()) {
				finalState = null;
				break;
			}
			
			System.out.println("current frontier size: " + frontier.size());
			
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
				if(explored.contains(nextState) == false && frontier.contains(nextNode) == false) {
					if(nextState.isFinalState()) return nextNode;
					frontier.add(nextNode);					
				}

			}			
		}	
		return finalState;
	}	
}
