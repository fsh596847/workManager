package com.orwlw.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * @author HYJ 2013-1-25 单店的历史订单
 */
public class HistoryOrderActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener {
	private GridView mygridView;
	ProgressBar pbar;
	List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
	private int lastItem;
	private int firstItem;
	private int curPage = 1;

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				mygridView.setAdapter(new MyAdatper(li));
				pbar.setVisibility(8);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_historyorder_view);

		mygridView = (GridView) findViewById(R.id.myorderListView);
		mygridView.setOnItemClickListener(this);
		mygridView.setOnItemLongClickListener(this);
		pbar = (ProgressBar) findViewById(R.id.progressBar1);
		Getorders(0);
	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	void Getorders(int i) {
		pbar.setVisibility(0);
		final int num = i;
		new Thread() {
			public void run() {
				try {
					Looper.prepare();
					List<Map<String, Object>> templi = SyncHelper
							.GetHistory_Order(
									((MyApplication) getApplication())
											.Getlocaldata().personno,
									((MyApplication) getApplication())
											.Getlocaldata().current_custom_code,
									num);
					if (templi != null) {
						for (Map<String, Object> map : templi) {
							li.add(map);
						}
					}
					message(1);
					Looper.loop();
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	class MyAdatper extends BaseAdapter {
		List<Map<String, Object>> li;

		public MyAdatper(List<Map<String, Object>> li1) {
			li = li1;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return li.size() + 1;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup arg2) {

			try {
				final Myview myview;

				if (position == li.size()) {
					contentView = LayoutInflater.from(getApplicationContext())
							.inflate(R.layout.item_more, null);
					contentView.setPadding(5, 5, 5, 5); // 每格的间距
					return contentView;
				}

				if (contentView == null) {
					myview = new Myview();
					contentView = LayoutInflater.from(getApplicationContext())
							.inflate(R.layout.list_historyorder_item, null);
					contentView.setTag(myview);
					contentView.setPadding(5, 5, 5, 5); // 每格的间距
				} else {
					if (contentView.getTag() == null) {
						myview = new Myview();
						contentView = LayoutInflater.from(
								getApplicationContext()).inflate(
								R.layout.list_historyorder_item, null);
						contentView.setTag(myview);
						contentView.setPadding(5, 5, 5, 5); // 每格的间距
					} else {
						myview = (Myview) contentView.getTag();
					}

				}

				final Map<String, Object> map = li.get(position);

				myview.go_order_detail_Layout = (RelativeLayout) contentView
						.findViewById(R.id.go_orderditial_layout);
				myview.order_detail_Layout = (RelativeLayout) contentView
						.findViewById(R.id.orderditial_layout);
				myview.detailview = (TextView) contentView
						.findViewById(R.id.textView1);
				myview.bbartextview = (TextView) contentView
						.findViewById(R.id.bbar_textview);
				myview.textview_orderdate = (TextView) contentView
						.findViewById(R.id.textview_orderdate);
				String title = "";
				if (map.get("F_DELETED") != null) {
					if (map.get("F_DELETED").toString()
							.equalsIgnoreCase("True")) {
						title = map.get("F_KEY_VALUE").toString()
								.equalsIgnoreCase("") ? "" : "["
								+ map.get("F_KEY_VALUE").toString() + "]";

					} else {
						title = "[已撤销]";
					}
				}
				myview.textview_orderdate.setText(map.get("F_SELL_ORDER_DATE")
						.toString() + title);
				myview.order_detail_Layout.setVisibility(8);
				myview.bbartextview.setText("订单明细>>");
				myview.go_order_detail_Layout
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								myview.flag = !myview.flag;
								if (myview.flag) {
									myview.detailview.setText(map.get(
											"F_ORDER_CONTENT").toString());
									myview.order_detail_Layout.setVisibility(0);
									myview.bbartextview.setText("<<收起");

								} else {
									myview.order_detail_Layout.setVisibility(8);
									myview.bbartextview.setText("订单明细>>");
								}
							}
						});

			} catch (Exception e) {
				MyApplication.WriteLog("" + e.getLocalizedMessage());
			}
			return contentView;
		}

		class Myview {
			RelativeLayout go_order_detail_Layout;
			RelativeLayout order_detail_Layout;
			TextView detailview;
			TextView bbartextview;
			TextView textview_orderdate;
			boolean flag;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		try {
			if (arg2 == li.size()) {
				Getorders(li.size());
			}
		} catch (Exception e) {
			MyApplication.WriteLog("" + e.getMessage());
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg2 != li.size()) {
			final int i = arg2;
			Dialog dialog = new AlertDialog.Builder(HistoryOrderActivity.this)
					.setMessage("撤销该订单")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									cancelOrder(li.get(i)
											.get("F_SELL_ORDER_NO").toString());
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
								}
							}).create();
			dialog.show();
		}

		return false;
	}

	void cancelOrder(String order_id) {
		pbar.setVisibility(0);
		final String order = order_id;
		new Thread() {
			public void run() {
				try {
					SyncHelper
							.cancelOrder(((MyApplication) getApplication())
									.Getlocaldata().personno, order);
					li.clear();
					Getorders(0);
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}
}
