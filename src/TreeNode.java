import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class TreeNode<T>
{
	private T data = null;
	private List<TreeNode> children = new ArrayList<>();
	private TreeNode<T> parent = null;
	private String action;

	public TreeNode(T data)
	{
		this.data = data;
	}
	
	public TreeNode(T data, String action)
	{
		this.data = data;
		this.action = action;
	}

	public void addChild(TreeNode<T> child, String action)
	{
		child.setParent(this);
		this.children.add(child);
		child.action = action;
	}

	public void addChild(T data)
	{
		TreeNode<T> newChild = new TreeNode<>(data);
		newChild.setParent(this);
		children.add(newChild);
	}

	public void addChildren(List<TreeNode<T>> children)
	{
		for (TreeNode<T> t : children)
		{
			t.setParent(this);
		}
		this.children.addAll(children);
	}

	public List<TreeNode> getChildren()
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

	private void setParent(TreeNode<T> parent)
	{
		this.parent = parent;
	}

	public TreeNode<T> getParent()
	{
		return parent;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
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
		TreeNode other = (TreeNode) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
	
}