package folders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FolderSelector
{

	static List<String> readableFolders = null;
	static List<String> writableFolders = null;
	static List<TreeItem> treeItems = null;


	static
	{
		try
		{
			//init with test data from files (testtree.txt, readable.txt, writable.txt)
			System.out.println("init with test data from file: ");

			generateTestTreeItem();
			readableFolders = readFromFile("readable.txt");
			writableFolders = readFromFile("writable.txt");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		if (treeItems != null && !treeItems.isEmpty())
		{
			isWritablePath("", treeItems.get(0).children);
		}
		//the result: lists the writable treeItem.
		System.out.println("Only writable treeitem: " + treeItems);
	}

	/*
	 * checks the treeItem recursively if the items can be removed from the treeItem or not
	 */
	private static boolean isWritablePath(String parentName, List<TreeItem> partTreeItems)
	{
		boolean writablePath = false;
		for (Iterator<TreeItem> iterator = partTreeItems.iterator(); iterator.hasNext();)
		{
			TreeItem treeItem = iterator.next();
			String path = parentName + "/" + treeItem.name;
			//The item can be removed if the path is not in the readabe nor in the writable path
			if (!readableFolders.contains(path) && !writableFolders.contains(path))
			{
				iterator.remove();
			}

			else if (readableFolders.contains(path) && !writableFolders.contains(path))
			{
				if (treeItem.children == null || treeItem.children.isEmpty())
				{
					iterator.remove();
				}
				else
				{
					boolean innerWritablePath = isWritablePath(path, treeItem.children);
					if (innerWritablePath)
					{
						writablePath = true;

					}
					else
					{
						//the item can be removed only if the item has no writable sub-folders
						iterator.remove();
					}
				}
			}
			else if (writableFolders.contains(path))
			{
				if (treeItem.children != null && !treeItem.children.isEmpty())
				{
					isWritablePath(path, treeItem.children);
				}
				writablePath = true;

			}

		}
		return writablePath;

	}

	/*
	 * generates test treeItem from testtree.txt
	 * 
	 */
	private static void generateTestTreeItem() throws IOException
	{

		List<String> paths = readFromFile("testtree.txt");

		treeItems = new ArrayList<TreeItem>();
		for (Iterator<String> iterator = paths.iterator(); iterator.hasNext();)
		{
			String path = iterator.next();
			List<String> pathItems = new ArrayList<String>(Arrays.asList(path.split("/")));

			addTestItem(pathItems, treeItems);

		}
		System.out.println("test treeitem: " + treeItems);

	}

	/*
	 * generates test treeItem from testtree.txt
	 * 
	 */
	private static void addTestItem(List<String> pathItems, List<TreeItem> partTreeItems)
	{
		
		boolean itemExist=false;
		for (Iterator<TreeItem> iterator = partTreeItems.iterator(); iterator.hasNext() && !itemExist;)
		{
			TreeItem treeItem = iterator.next();
			if (treeItem.name.equals(pathItems.get(0))) {
				pathItems.remove(0);
				addTestItem(pathItems, treeItem.children);
				itemExist=true;
			}
			
		}
		if (!itemExist){
			
			TreeItem treeItem = new TreeItem(pathItems.get(0), new ArrayList<TreeItem>());
			pathItems.remove(0);
			partTreeItems.add(treeItem);
			if (!pathItems.isEmpty())
			{
				addTestItem(pathItems, treeItem.children);
			}
		}
		
		
	}

	/*
	 * reads test data from the 3 files: testtree.txt, readable.txt and writable.txt
	 */
	private static List<String> readFromFile(String fileName) throws IOException
	{
		File file = new File(fileName);
		System.out.println(file.getAbsolutePath());
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String pathString = br.readLine();
		System.out.println("file content: " + pathString);

		List<String> splitString = new ArrayList<String>();
		if (pathString != null)
		{
			splitString = Arrays.asList(pathString.split(";"));

		}
		return splitString;
	}
}
