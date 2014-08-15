package com.orwlw.comm;

import java.util.ArrayList;
import java.util.List;

import com.orwlw.activity.R;

import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TreeAdapter extends BaseAdapter
{
	public List<Node> allNodes;
	private List<Node> allNodesCache;
	private Context context;
	private LayoutInflater lif;
	private TreeAdapter oThis = this;
	private int expanderIcon = -1;
	private int collsapsedIcon = -1;

	public TreeAdapter(Context convertView, Node rootNode)
	{
		allNodes = new ArrayList<Node>();
		allNodesCache = new ArrayList<Node>();
		this.context = convertView;
		this.lif = (LayoutInflater) convertView.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		addAllnode(rootNode);
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return allNodes.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return allNodes.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	/**** View显示区 *****/
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			convertView = this.lif.inflate(R.layout.tree_item_layout, null);
			// 得到页面控件
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.image2);
			holder.tView = (TextView) convertView.findViewById(R.id.text2);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		// 将节点的值赋给他们
		Node node = allNodes.get(position);
		if (node != null)
		{
			holder.tView.setText(node.getText());
			if (!node.isLeaf())
			{
				holder.imageView.setVisibility(View.VISIBLE);
				if (node.isExpanded)
				{
					// holder.imageView.setImageResource(R.drawable.icon);
				} else
				{
					// holder.imageView.setImageResource(R.drawable.icon);
				}
			} else
			{
				holder.imageView.setVisibility(View.GONE);

			}
		}
		convertView.setPadding(10 * node.getLevel(), 0, 0, 0);
		// Log.i("level", allNodes.get(position).getLevel()+"");
		return convertView;
	}

	// 根据传入的根节点 得到当前所有节点
	public void addAllnode(Node node)
	{
		allNodes.add(node);
		allNodesCache.add(node);
		if (node.isLeaf())
			return;
		for (int i = 0; i < node.getChildren().size(); i++)
		{
			addAllnode(node.getChildren().get(i));
		}
	}

	public void removeAllSubNodes(Node n)
	{
		if (!n.isLeaf())
		{
			List<Node> childrenList = n.getChildren();
			for (int i = 0; i < childrenList.size(); i++)
			{
				Node node = childrenList.get(i);
				removeAllSubNodes(node);
				allNodes.remove(node);
				node.setExpanded(!n.isExpanded);
			}
		}
	}

	// 当用户点击某项LIST的时候 控制节点收缩
	public void ExpandOrCollapse(int position)
	{
		Node n = allNodes.get(position);
		// Log.i("我们点击的是", n.text + " isExpanded:" + n.isExpanded());
		if (n != null)
		{
			if (!n.isLeaf())// 不是叶子节点的进入
			{
				// n.setExpanded(!n.isExpanded());
				// filterNode();
				if (!n.isExpanded)
				{
					for (int i = 0; i < n.getChildren().size(); i++)
					{
						// allNodes.add(n.getChildren().get(i));
						// 存在一个数据显示顺序的问题 要求显示在他的父节点下
						// 点击的节点位置
						allNodes.add(position + 1 + i, n.getChildren().get(i));
					}
				} else
				{
					for (int i = 0; i < n.getChildren().size(); i++)
					{
						removeAllSubNodes(n);
						// allNodes.remove(n.getChildren().get(i));
					}
				}
				n.setExpanded(!n.isExpanded);
				this.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 根据node控制节点收缩
	 * 
	 * @param position
	 */
	public void ExpandOrCollapsebyNode(Node n)
	{
		// Log.i("我们点击的是", n.text + " isExpanded:" + n.isExpanded());
		if (n != null)
		{
			if (!n.isLeaf())// 不是叶子节点的进入
			{
				// n.setExpanded(!n.isExpanded());
				// filterNode();
				// if (!n.isExpanded)
				// {
				// for (int i = 0; i < n.getChildren().size(); i++)
				// {
				// // allNodes.add(n.getChildren().get(i));
				// // 存在一个数据显示顺序的问题 要求显示在他的父节点下
				// // 点击的节点位置
				// // allNodes.add(position + 1 + i,
				// // n.getChildren().get(i));
				// }
				// } else
				// {
				for (int i = 0; i < n.getChildren().size(); i++)
				{
					removeAllSubNodes(n);
					// allNodes.remove(n.getChildren().get(i));
				}
				// }
				n.setExpanded(false);
				this.notifyDataSetChanged();
			}
		}
	}

	// 设置默认打开时展开级别
	public void ExpanderLevel(int level)
	{
		allNodes.clear();
		for (int i = 0; i < allNodesCache.size(); i++)
		{
			// 得到每一个节点
			Node n = allNodesCache.get(i);
			if (n.getLevel() <= level)
			{
				if (n.getLevel() < level)
				{
					n.setExpanded(true);

				} else
				{
					n.setExpanded(false);
				}
				allNodes.add(n);
			} else
			{
				n.setExpanded(false);
			}
		}
		oThis.notifyDataSetChanged();
	}

	// 设置图标
	public void setIcon(int expandedIcon, int collsapsedIcon)
	{
		this.expanderIcon = expandedIcon;
		this.collsapsedIcon = collsapsedIcon;
	}

	public class ViewHolder
	{
		ImageView imageView;// 图标
		TextView tView;// 文本〉〉〉
	}
}
