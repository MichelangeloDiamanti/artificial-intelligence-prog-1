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

	public void addChild(TreeNode<T> child)
	{
		child.setParent(this);
		this.children.add(child);
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
}