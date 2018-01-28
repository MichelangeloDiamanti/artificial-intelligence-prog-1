import java.util.ArrayList;
import java.util.List;

public class PriorityTreeNode<T> {
	private T data = null;
	private List<PriorityTreeNode<T>> children = new ArrayList<>();
	private PriorityTreeNode<T> parent = null;
	private Pair<String, Integer> action;
	
	PriorityTreeNode(T data)
	{
		this.data = data;
	}
	
	PriorityTreeNode(T data, Pair<String, Integer> action)
	{
		this.data = data;
		this.action = action;
	}

	public void addChild(PriorityTreeNode<T> child, Pair<String, Integer> action)
	{
		child.setParent(this);
		this.children.add(child);
		child.action = action;
	}
	
	public List<PriorityTreeNode<T>> getChildren()
	{
		return children;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

	private void setParent(PriorityTreeNode<T> parent)
	{
		this.parent = parent;
	}

	public PriorityTreeNode<T> getParent()
	{
		return parent;
	}
	
	public Pair<String, Integer> getAction()
	{
		return action;
	}

	public void setAction(Pair<String, Integer> action)
	{
		this.action = action;
	}
	
	public boolean isRoot()
	{
		return (parent == null) ? true : false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriorityTreeNode other = (PriorityTreeNode) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	

	
	
}
