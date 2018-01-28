import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import sun.reflect.generics.tree.Tree;

public class UniformCostSearch
{
	EnvironmentState currentState;
	PriorityTreeNode<EnvironmentState> root;
	PriorityQueue<PriorityTreeNode<EnvironmentState>> frontier;
	Set<EnvironmentState> visitedStates;
	
	public UniformCostSearch(EnvironmentState initialState) {
		super();
		this.currentState = initialState;
		
		root = new PriorityTreeNode<EnvironmentState>(currentState);
		
		Comparator<PriorityTreeNode<EnvironmentState>> comparator = new MoveComparator();
		frontier = new PriorityQueue<PriorityTreeNode<EnvironmentState>>(comparator);
		
		frontier.add(root);
		
		visitedStates = new HashSet<EnvironmentState>();
	}

	public PriorityTreeNode<EnvironmentState> ucs(){
		PriorityTreeNode<EnvironmentState> currentNode = null;

		while(true) 
		{
			if(frontier.isEmpty() == true) break;
			currentNode = frontier.poll();
			
			if(currentNode.getData().isFinalState()) return currentNode;
			visitedStates.add(currentNode.getData());
			
			for (Pair<String, Integer> weightedMove : currentNode.getData().weightedMoves()) {
				
				EnvironmentState childState = null;
				PriorityTreeNode<EnvironmentState> childNode = null;

				switch (weightedMove.getFirst())
				{
				case "TURN_ON":
					childState = currentState.toogleStatusOfTheRobot();
					break;
				case "TURN_OFF":
					childState = currentState.toogleStatusOfTheRobot();
					break;
				case "SUCK":
					childState = currentState.removeDirt(currentState.getLocationOfTheRobot());
					break;
				case "TURN_LEFT":
					childState = currentState.turnLeft();
					break;
				case "TURN_RIGHT":
					childState = currentState.turnRigth();
					break;
				case "GO":
					childState = currentState.go();
					break;

				default:
					break;
				}
			
				childNode = new PriorityTreeNode<EnvironmentState>(childState, weightedMove);
				currentNode.addChild(childNode, weightedMove);
				
				EnvironmentState childNodeState = childNode.getData();

				PriorityTreeNode<EnvironmentState> node = null;
				boolean nodeFound = false;
				List<PriorityTreeNode<EnvironmentState>> list = new ArrayList<PriorityTreeNode<EnvironmentState>>();
				
				// iterate the frontier to check if it contains the child node state
				while(nodeFound == false && frontier.isEmpty() == false) {
					node = frontier.poll();
					
					// as soon as it is found we are sure it is the one with the least path cost
					// because the frontier is a priority queue, and so we don't need to look further
					if(node.getData().equals(childNode.getData())) {
						nodeFound = true;
						// if we find the node we reinsert to the frontier every popped node
						// but not the found one, because it may need to be replaced
						for (PriorityTreeNode<EnvironmentState> priorityTreeNode : list) {
							frontier.add(priorityTreeNode);
						}
					}
					if(nodeFound == false) list.add(node);
				}

				// if the node is not found we must add back every node of the list to the frontier
				if(nodeFound == false) 
					frontier.addAll(list);
				
				
				if(nodeFound == false && visitedStates.contains(childNodeState) == false)
					frontier.add(childNode);
				else if(nodeFound == true) {
					if(node.getAction().getSecond() > childNode.getAction().getSecond()) 
						frontier.add(childNode);
				}
				else
					frontier.add(childNode);
			}

		}
		
		return currentNode;
	}

	
	
}
