package com.orwlw.activity;

import java.util.ArrayList;
import java.util.List;
import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.dal.ExchangeBackDAL;
import com.orwlw.model.Order_Model;
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

public class back_commsActivity extends Activity implements
		OnItemClickListener, OnClickListener {
	private GridView myListView;
	private List<Order_Model> list = new ArrayList<Order_Model>();
	Button okbtnButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.back_comm_view);
		okbtnButton = (Button) findViewById(R.id.okbtn);
		okbtnButton.setOnClickListener(this);
		myListView = (GridView) findViewById(R.id.mystoreListView);
		myListView.setOnItemClickListener(this);

	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	@Override
	protected void onResume() {
		getLocalOrder(((MyApplication) getApplication()).Getlocaldata().current_custom_code);
		super.onResume();
	}

	void getLocalOrder(String custom_code) {
		list = ExchangeBackDAL.getBack_commsby_custom_code(getApplication(),
				custom_code);
		myListView.setAdapter(new MyAdatper(list));
	}

	void btn_submit() {
		if (list.size() > 0) {
			String comms = "";
			for (Order_Model model : list) {
				comms += model.F_COMM_CODE + "," + model.F_NUM1 + "."
						+ model.F_NUM2 + "," + model.F_REASON_ID + ";";
			}

			Intent intent = new Intent(back_commsActivity.this,
					Print_OrderActivity.class);
			intent.putExtra("str", comms.substring(0, comms.length() - 1));

			intent.putExtra(Constants.PRINT_TYPE, Constants.PRINT_BACK_COMM);// 传递参数为退货单的打印

			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "请添加商品！",
					Toast.LENGTH_SHORT).show();
		}
	}

	class MyAdatper extends BaseAdapter {
		List<Order_Model> list;

		public MyAdatper(List<Order_Model> list1) {
			list = list1;
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

			final Order_Model order_model = new Order_Model();
			order_model.F_COMM_CODE = comm_code;
			order_model.F_CUSTOM_CODE = ((MyApplication) getApplication())
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
			myview.text_reason.setText(REASON);
			myview.productname_Layout = (RelativeLayout) contentView
					.findViewById(R.id.productname_Layout);
			myview.productname_Layout
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							((MyApplication) getApplication()).SavelocalInt(
									"CURRENT_PARAMS",
									Constants.BACK_COMMS_ACTION_UPDATE);

							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_CODE", comm_code);
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_NAME", comm_name);
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_NUM1", num1);
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_NUM2", num2);
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_REASON", REASON);
							((MyApplication) getApplication()).Savelocaldata(
									"CURRENT_COMM_REASON_ID", REASON_ID);

							startActivity(new Intent(back_commsActivity.this,
									ProductEditNumActivity.class));
						}
					});
			myview.productname_Layout
					.setOnLongClickListener(new View.OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							Dialog dialog = new AlertDialog.Builder(
									back_commsActivity.this)
									.setMessage("删除该商品")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													ExchangeBackDAL
															.DeleteLocal_BackComms(
																	getApplication(),
																	order_model);
													getLocalOrder(((MyApplication) getApplication())
															.Getlocaldata().current_custom_code);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 == list.size()) {
			Intent intent = new Intent(back_commsActivity.this,
					SlidingActivity.class);
			((MyApplication) getApplication()).SavelocalInt("CURRENT_PARAMS",
					Constants.BACK_COMMS_ACTION_ADD);
			startActivity(intent);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == okbtnButton) {
			btn_submit();
		}

	}
}
