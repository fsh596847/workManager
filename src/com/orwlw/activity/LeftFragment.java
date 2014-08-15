package com.orwlw.activity;

import java.util.List;
import java.util.Map;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.Node;
import com.orwlw.comm.ProductAdapter;
import com.orwlw.comm.SyncHelper;
import com.orwlw.comm.TreeAdapter;
import com.orwlw.dal.ProductDAL;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class LeftFragment extends Fragment {
	ListView listView_category;
	Context mContext;
	Node rootNode = null;
	ProgressBar pBar;
	List<Map<String, String>> category_list = null;
	public List<Map<String, Object>> list = null;
	private SimpleAdapter mySimperAdapter;

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				mySimperAdapter = new SimpleAdapter(mContext, list,
						R.layout.list_product_item, new String[] {
								"F_COMM_CODE", "F_COMM_NAME" }, new int[] {
								R.id.list_comm_code, R.id.list_comm_name });
				((SlidingActivity) getActivity()).myListView
						.setAdapter(mySimperAdapter);
				((SlidingActivity) getActivity()).mGridView
						.setAdapter(new ProductAdapter(mContext, list));
				break;
			case 2:
				pBar.setVisibility(8);
				getcategorylist();
				break;
			}
		};
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left, null);
		mContext = view.getContext();
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initview();
		if (ProductDAL.getCategorys_count(getActivity().getApplication()) > 0) {
			getcategorylist();
		} else {
			refreshCategorys();
		}
	}

	private void initview() {
		listView_category = (ListView) getActivity().findViewById(
				R.id.listView_categroy);
		listView_category.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((TreeAdapter) parent.getAdapter()).ExpandOrCollapse(position);
				Node n = ((TreeAdapter) parent.getAdapter()).allNodes
						.get(position);
				if (n.isLeaf()) {
					n = jiazaitree(n);
					n.setExpanded(true);
					if (n.children.size() < 1) {
						list = ProductDAL.getCOMMList_by_Cate(getActivity()
								.getApplication(), n.value);
						message(1);
					} else {
						TreeAdapter adapterd = new TreeAdapter(mContext,
								rootNode);
						// adapterd.ExpanderLevel(1);
						listView_category.setAdapter(adapterd);
					}
				}
				n = Expandnode(((TreeAdapter) parent.getAdapter()), n);

			}
		});
		pBar = (ProgressBar) getActivity().findViewById(R.id.pbar_category);

	}

	void getcategorylist() {
		try {
			rootNode = new Node("品项", "");
			rootNode = jiazaitree(rootNode);
			TreeAdapter adapterd = new TreeAdapter(mContext, rootNode);
			adapterd.ExpanderLevel(1);
			listView_category.setAdapter(adapterd);
		} catch (Exception e) {
		}
	}

	Node jiazaitree(Node n) {
		category_list = ProductDAL.getCategorys_by_parent(getActivity()
				.getApplication(), n.value);
		for (int i = 0; i < category_list.size(); i++) {
			Node node = new Node(category_list.get(i).get("F_COMM_CATEGORY")
					.toString(), category_list.get(i)
					.get("F_COMM_CATEGORY_CODE").toString());
			node.setParent(n);
			n.add(node);
		}
		return n;
	}

	public Node Expandnode(TreeAdapter m, Node n) {
		try {
			if (n.parent != null) {
				int ii = n.parent.children.size();
				for (int i = 0; i < ii; i++) {
					if (!n.equals(n.parent.children.get(i)))
						m.ExpandOrCollapsebyNode(n.parent.children.get(i));
				}
				n = Expandnode(m, n.parent);
			}
		} catch (Exception e) {
		}
		return n;
	}

	private void refreshCategorys() {
		pBar.setVisibility(0);
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					// 同步品项数据到本地后通知加载本地品项数据
					SyncHelper
							.RelocateTable(
									mContext,
									"T_SCM_CATEGORY",
									SyncHelper
											.GetCategorys((((MyApplication) (getActivity()
													.getApplication())))
													.Getlocaldata().personno));
					message(2);
					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	// 品项listview的单击事件
	public OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			((TreeAdapter) parent.getAdapter()).ExpandOrCollapse(position);
			Node n = ((TreeAdapter) parent.getAdapter()).allNodes.get(position);
			if (n.isLeaf()) {
				n = jiazaitree(n);
				n.setExpanded(true);
				if (n.children.size() < 1) {
					list = ProductDAL.getCOMMList_by_Cate(getActivity()
							.getApplication(), n.value);
					message(1);
				} else {
					TreeAdapter adapterd = new TreeAdapter(mContext, rootNode);
					// adapterd.ExpanderLevel(1);
					listView_category.setAdapter(adapterd);
				}
			}
			n = Expandnode(((TreeAdapter) parent.getAdapter()), n);

		}
	};

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

}
