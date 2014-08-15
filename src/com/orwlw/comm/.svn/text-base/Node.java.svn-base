package com.orwlw.comm;

import java.util.ArrayList;
import java.util.List;

public class Node
{
	public Node parent;// 父节点
	public List<Node> children = new ArrayList<Node>();// 子节点
	public String text;// 节点显示的文字
	public String value;// 节点的值
	public int icon = -1;// 是否显示小图标,-1表示隐藏图标
	public boolean isExpanded = true;// 是否处于展开状态
	/**
	 * Node构造函数
	 * 
	 * @param text
	 *            节点显示的文字
	 * @param value
	 *            节点的值
	 */
	public Node(String text, String value)
	{
		this.text = text;
		this.value = value;
	}

	/**
	 * 设置父节点
	 * 
	 * @param node
	 */
	public void setParent(Node node)
	{
		this.parent = node;
	}

	/**
	 * 获得父节点
	 * 
	 * @return
	 */
	public Node getParent()
	{
		return this.parent;
	}

	/**
	 * 设置节点文本
	 * 
	 * @param text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * 获得节点文本
	 * 
	 * @return
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * 设置节点值
	 * 
	 * @param value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * 获得节点值
	 * 
	 * @return
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * 设置节点图标文件
	 * 
	 * @param icon
	 */
	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	/**
	 * 获得图标文件
	 * 
	 * @return
	 */
	public int getIcon()
	{
		return icon;
	}

	/**
	 * 是否根节点
	 * 
	 * @return
	 */
	public boolean isRoot()
	{
		return parent == null ? true : false;
	}

	/**
	 * 获得子节点
	 * 
	 * @return
	 */
	public List<Node> getChildren()
	{
		return this.children;
	}

	/**
	 * 添加子节点
	 * 
	 * @param node
	 */
	public void add(Node node)
	{
		if (!children.contains(node))
		{
			children.add(node);
		}
	}

	/**
	 * 清除所有子节点
	 */
	public void clear()
	{
		children.clear();
	}

	/**
	 * 删除一个子节点
	 * 
	 * @param node
	 */
	public void remove(Node node)
	{
		if (!children.contains(node))
		{
			children.remove(node);
		}
	}

	/**
	 * 删除指定位置的子节点
	 * 
	 * @param location
	 */
	public void remove(int location)
	{
		children.remove(location);
	}

	/**
	 * 获得节点的级数,根节点为0
	 * 
	 * @return
	 */
	public int getLevel()
	{
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	/**
	 * 是否叶节点,即没有子节点的节点
	 * 
	 * @return
	 */
	public boolean isLeaf()
	{
		return children.size() < 1 ? true : false;
	}

	/**
	 * 当前节点是否处于展开状态
	 * 
	 * @return
	 */
	public boolean isExpanded()
	{
		return isExpanded;
	}

	/**
	 * 设置节点展开状态
	 * 
	 * @return
	 */
	public void setExpanded(boolean isExpanded)
	{
		this.isExpanded = isExpanded;
	}

}
