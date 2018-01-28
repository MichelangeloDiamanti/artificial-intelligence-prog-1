import java.util.Comparator;

public class MoveComparator implements Comparator<PriorityTreeNode<EnvironmentState>> {

	// compare moves depending on their cost and return the cheapest
	@Override
	public int compare(PriorityTreeNode<EnvironmentState> node1, PriorityTreeNode<EnvironmentState> node2) {
		if(node1.getAction().getSecond() < node2.getAction().getSecond()) return -1;
		if(node2.getAction().getSecond() < node1.getAction().getSecond()) return -1;
		return 0;
	}	

}
