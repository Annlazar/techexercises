package folders;

import java.util.List;

public class TreeItem
{
	public String name;
	public List<TreeItem> children;

	public TreeItem(String name, List<TreeItem> children)
	{
		this.name = name;
		this.children = children;
	}

	public String toString()
	{
		return name + children;
	}
}
