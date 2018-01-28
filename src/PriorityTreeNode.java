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
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}	
	
}
