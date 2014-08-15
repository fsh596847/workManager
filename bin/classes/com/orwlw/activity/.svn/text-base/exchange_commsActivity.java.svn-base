package com.orwlw.activity;

import java.util.ArrayList;
import java.util.List;

import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;
import com.orwlw.dal.ExchangeBackDAL;
import com.orwlw.model.Order_Model;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class exchange_commsActivity extends Activity implements OnClickListener {
	private GridView myListView_old;
	private GridView myListView_new;
	private List<Order_Model> list_old = new ArrayList<Order_Model>();
	private List<Order_Model> list_new = new ArrayList<Order_Model>();
	Button okbtnButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exchange_comm_view);
		myListView_old = (GridView) findViewById(R.id.gridview1);
		myListView_old.setOnItemClickListener(onitem1);

		myListView_new = (GridView) findViewById(R.id.gridview2);
		myListView_new.setOnItemClickListener(onitem2);

		okbtnButton = (Button) findViewById(R.id.okbtn);
		okbtnButton.setOnClickListener(this);
	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	@Override
	protected void onResume() {
		getLocalprocuct_old(((MyApplication) getApplication()).Getlocaldata().current_custom_code);
		getLocalprocuct_new(((MyApplication) getApplication()).Getlocaldata().current_custom_code);
		super.onResume();
	}

	void getLocalprocuct_old(String custom_code) {
		list_old = ExchangeBackDAL.getold_commsby_custom_code(getApplication(),
				custom_code);
		myListView_old.setAdapter(new MyAdatper(list_old, 1));
	}

	void getLocalprocuct_new(String custom_code) {
		list_new = ExchangeBackDAL.getnew_commsby_custom_code(getApplication(),
				custom_code);
		myListView_new.setAdapter(new MyAdatper(list_new, 2));
	}

	void btn_submit() {
		if (list_old.size() > 0 && list_new.size() > 0) {
			String comms_old = "";
			for (Order_Model model : list_old) {
				comms_old += model.F_COMM_CODE + "," + model.F_NUM1 + "."
						+ model.F_NUM2 + "," + model.F_REASON_ID + ";";
			}
			String comms_new = "";
			for (Order_Model model : list_new) {
				comms_new += model.F_COMM_CODE + "," + model.F_NUM1 + "."
						+ model.F_NUM2 + ";";
			}

			Intent intent = new Intent(exchange_commsActivity.this,
					Print_OrderActivity.class);
			intent.putExtra("str",
					comms_old.substring(0, comms_old.length() - 1));
			intent.putExtra("str1",
					comms_new.substring(0, comms_new.length() - 1));
			intent.putExtra(Constants.PRINT_TYPE, Constants.PRINT_EXCHANGE_COMM);// 传递参数为换货单的打印

			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "请添加商品！",
					Toast.LENGTH_SHORT).show();
		}
	}

	class MyAdatper extends BaseAdapter {
		List<Order_Model> list;
		int flag = -1;

		public MyAdatper(List<Order_Model> list1, int f) {
			list = list1;
			flag = f;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size() + 1;
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

			Myview myview;

			if (position == list.size()) {
				contentView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_add, null);
				return contentView;
			}

			if (contentView == null) {
				myview = new Myview();
				contentView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.list_store_item, null);
				contentView.setTag(myview);
				// contentView.setPadding(15, 15, 15, 15); // 每格的间距
			} else {
				myview = (Myview) contentView.getTag();
			}

			final String comm_code = list.get(position).F_COMM_CODE;
			final String comm_name = list.get(position).F_COMM_NAME;
			final String num1 = list.get(position).F_NUM1;
			final String num2 = list.get(position).F_NUM2;
			final String REASON = list.get(position).F_REASON;
			final String REASON_ID = list.get(position).F_REASON_ID;

			final Order_Model model = new Order_Model();
			model.F_COMM_CODE = comm_code;
			model.F_CUSTOM_CODE = ((MyApplication) getApplication())
					.Getlocaldata().current_custom_code;

			myview.text_comm_name = (TextView) contentView
					.findViewById(R.id.comm_name);
			myview.text_comm_name.setText(comm_name);

			myview.text_num1 = (TextView) contentView.findViewById(R.id.num1);
			myview.text_num1.setText(num1);

			myview.text_num2 = (TextView) contentView.findViewById(R.id.num2);
			myview.text_num2.setText(num2);

			myview.text_reason = (TextView) contentView
					.findViewById(R.id.comm_date);
			if (flag == 1) {
				myview.text_reason.setVisibility(0);
			} else {
				myview.text_reason.setVisibility(8);
			}
			myview.text_reason.setText(REASON);

			myview.productname_Layout = (RelativeLayout) contentView
					.findViewById(R.id.productname_Layout);
			myview.productname_Layout
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							if (flag == 1) {
								((MyApplication) getApplication())
										.SavelocalInt(
												"CURRENT_PARAMS",
												Constants.EXCHANGE_COMMSOLD_ACTION_UPDATE);
								((MyApplication) getApplication())
										.Savelocaldata("CURRENT_COMM_REASON",
												REASON);
								((MyApplication) getApplication())
										.Savelocaldata(
												"CURRENT_COMM_REASON_ID",
												REASON_ID);
							}
							if (flag == 2) {
								((MyApplication) getApplication())
										.SavelocalInt(
												"CURRENT_PARAMS",
												Constants.EXCHANGE_COMMSNEW_ACTION_UPDATE);
							}
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_CODE", comm_code);
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_NAME", comm_name);
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_NUM1", num1);
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_NUM2", num2);

							startActivity(new Intent(
									exchange_commsActivity.this,
									ProductEditNumActivity.class));
						}
					});
			myview.productname_Layout
					.setOnLongClickListener(new View.OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							Dialog dialog = new AlertDialog.Builder(
									exchange_commsActivity.this)
									.setMessage("删除该商品")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													if (flag == 1) {
														ExchangeBackDAL
																.DeleteLocal_ExchangeComms_old(
																		getApplication(),
																		model);
														getLocalprocuct_old(((MyApplication) getApplication())
																.Getlocaldata().current_custom_code);
													}
													if (flag == 2) {
														ExchangeBackDAL
																.DeleteLocal_ExchangeComms_new(
																		getApplication(),
																		model);
														getLocalprocuct_new(((MyApplication) getApplication())
																.Getlocaldata().current_custom_code);
													}

												}

											})
									.setNegativeButton(
											"取消",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													dialog.cancel();
												}
											}).create();
							dialog.show();
							return false;
						}
					});

			return contentView;
		}

		class Myview {
			RelativeLayout productname_Layout;
			TextView text_comm_name;
			TextView text_num1;
			TextView text_num2;
			TextView text_reason;
		}
	}

	OnItemClickListener onitem1 = new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 == list_old.size()) {
				Intent intent = new Intent(exchange_commsActivity.this,
						SlidingActivity.class);
				((MyApplication) getApplication()).SavelocalInt(
						"CURRENT_PARAMS",
						Constants.EXCHANGE_COMMSOLD_ACTION_ADD);
				startActivity(intent);
			}
		}
	};

	OnItemClickListener onitem2 = new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 == list_new.size()) {
				Intent intent = new Intent(exchange_commsActivity.this,
						SlidingActivity.class);
				((MyApplication) getApplication()).SavelocalInt(
						"CURRENT_PARAMS",
						Constants.EXCHANGE_COMMSNEW_ACTION_ADD);
				startActivity(intent);
			}
		}
	};

	@Override
	public void onClick(View v) {
		if (v == okbtnButton) {
			btn_submit();
		}

	}
}
