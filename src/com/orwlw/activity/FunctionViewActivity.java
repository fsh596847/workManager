package com.orwlw.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orwlw.comm.Constants;
import com.orwlw.comm.FunctionAdapter;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;
import com.orwlw.dal.CustomDAL;
import com.orwlw.dal.FunctionDAL;
import com.orwlw.model.FunctionModel;
import com.orwlw.model.Localdata;

/**
 * @author HYJ 2013-1-25 拜访功能
 */
public class FunctionViewActivity extends BaseActivity implements
		OnItemClickListener {

	GridView MainActivityGrid;
	RelativeLayout custom_detail_layout;
	LinearLayout linear_salesman;
	LinearLayout linear_driverman;
	LinearLayout linearLayout1;
	TextView txtview_show;
	TextView go_edit;
	TextView tv_cus_code;
	TextView tv_cus_name;
	TextView tv_address;
	TextView tv_person;
	TextView tv_tel;
	private Intent intent = null;
	Context mcontext;
	List<FunctionModel> list = new ArrayList<FunctionModel>();
	List<Map<String, Object>> salesManList;
	double distance = 0;

	String strCode = "";
	String strName = "";
	String strAddress = "";
	String strPerson = "";
	String strTel = "";
	String strLat = "";
	String strLon = "";
	String strIsvisit = "";
	String strArea = "";
	private int mSelectedMenu = -1;
	Map<String, Object> mapCus;

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				FunctionAdapter functionAdapter = new FunctionAdapter(mcontext,
						list);
				MainActivityGrid.setAdapter(functionAdapter);
				((FunctionAdapter) MainActivityGrid.getAdapter())
						.notifyDataSetChanged();
				break;
			case 2:
				// 根据获取的数据动态创建业务和司机的textview控件
				if (salesManList == null)
					break;
				if (salesManList.size() > 0) {
					linear_salesman.removeAllViews();
					linear_driverman.removeAllViews();
					for (int i = 0; i < salesManList.size(); i++) {
						if (salesManList.get(i).get("F_STATIONNO").toString()
								.equalsIgnoreCase("GW0002")) {
							// 创建业务
							TextView tv = getNewTextView(salesManList.get(i)
									.get("F_PSNNAME").toString(), salesManList
									.get(i).get("F_MOBILE").toString());
							linear_salesman.addView(tv);
						}
						if (salesManList.get(i).get("F_STATIONNO").toString()
								.equalsIgnoreCase("GW0004")) {
							// 创建司机
							TextView tv = getNewTextView(salesManList.get(i)
									.get("F_PSNNAME").toString(), salesManList
									.get(i).get("F_MOBILE").toString());
							LayoutParams lp = new LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT);
							lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
							lp.addRule(RelativeLayout.CENTER_VERTICAL, 0);
							lp.setMargins(80, 0, 0, 0);
							linear_driverman.addView(tv, lp);
						}
					}
				}
				break;
			}
		};
	};

	public TextView getNewTextView(String name, String number) {
		TextView tv = new TextView(mcontext);
		tv.setText(name + "   ");
		tv.setTextSize(14);
		tv.setGravity(Gravity.CENTER);
		// tv.setTextColor(R.color.textview_color);

		final String num = number;
		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.CALL");
					intent.setData(Uri.parse("tel:" + num));
					startActivity(intent);
				} catch (Exception e) {
					MyApplication.WriteLog(e.getMessage());
				}

			}
		});
		return tv;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.function);
		mcontext = this;
		initView();
		MainActivityGrid.setOnItemClickListener(this);
		MyApplication.WriteLog("FunctionActivity onCreate");
	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	protected void onResume() {
		MyApplication.WriteLog("FunctionActivity onResume start");
		getLocal_Funcs();
		String dataCode = ((MyApplication) getApplication()).Getlocaldata().current_custom_code;
		// intent = getIntent();
		// if (null != intent.getExtras()) {
		// strCode = intent.getStringExtra("tv_code");
		// strName = intent.getStringExtra("tv_name");
		// strAddress = intent.getStringExtra("tv_address");
		// strPerson = intent.getStringExtra("tv_person");
		// strTel = intent.getStringExtra("tv_tel");
		// strLat = intent.getStringExtra("tv_lat");
		// strLon = intent.getStringExtra("tv_lon");
		// strIsvisit = intent.getStringExtra("tv_isvisit");
		// distance = Double.parseDouble(intent.getStringExtra("Distance"));
		// } else
		if (!dataCode.equalsIgnoreCase("")) {
			mapCus = new HashMap<String, Object>();
			mapCus = CustomDAL.getCustom(getApplication(), dataCode);
			strCode = dataCode;
			strName = (String) mapCus.get("F_CUSTOM_NAME");
			strAddress = (String) mapCus.get("F_CO_ADDRESS");
			strPerson = (String) mapCus.get("F_CONTACT_PERSON");
			strTel = (String) mapCus.get("F_CONTACT_TEL");
			strLat = (String) mapCus.get("F_LAT");
			strLon = (String) mapCus.get("F_LON");
			strIsvisit = (String) mapCus.get("F_ISVISIT");
			distance = Double.parseDouble(mapCus.get("Distance") + "");
			strArea = (String) mapCus.get("F_AREA_NAME");
			// 获取本店签到状态
			((MyApplication) getApplication()).Savelocaldata(
					"current_custom_attended",
					(String) mapCus.get("F_ATTENDED"));
		}
		if (null != strName && strName.trim().length() > 0) {
			tv_cus_code.setText(strCode);
			tv_cus_name.setText(strName + "   [" + strArea + "]");
			tv_address.setText(strAddress);
			tv_person.setText(strPerson);
			tv_tel.setText(strTel);
		} else {
			tv_cus_code.setText("");
			tv_cus_name.setText("");
			tv_address.setText("");
			tv_person.setText("");
			tv_tel.setText("");
		}
		MyApplication.WriteLog("FunctionActivity onResume end");
		super.onResume();

	}

	private void initView() {
		custom_detail_layout = (RelativeLayout) findViewById(R.id.custom_detail_layout);
		linear_salesman = (LinearLayout) findViewById(R.id.linear_salesman);
		linear_driverman = (LinearLayout) findViewById(R.id.linear_driverman);
		linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		txtview_show = (TextView) findViewById(R.id.txtview_show);
		go_edit = (TextView) findViewById(R.id.go_edit);
		MainActivityGrid = (GridView) findViewById(R.id.MainActivityGrid);
        
		tv_cus_code = (TextView) findViewById(R.id.tv_cus_code);
		tv_cus_name = (TextView) findViewById(R.id.tv_cus_name);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_person = (TextView) findViewById(R.id.tv_person);
		tv_tel = (TextView) findViewById(R.id.tv_tel);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (showToast())
			return;
		try {
			if (Constants.ATTEND_FUNCTION
					.equalsIgnoreCase(((FunctionAdapter) MainActivityGrid
							.getAdapter()).li.get(arg2).Functionname)) {

				if (((MyApplication) getApplicationContext()).Getlocaldata().current_custom_attended
						.equalsIgnoreCase("")) {
					// 签到
					Attend(1);
				} else if (((MyApplication) getApplicationContext())
						.Getlocaldata().current_custom_attended
						.equalsIgnoreCase("1")) {
					// 签退
					Attend(2);
				}
				//
				// // 刷新功能按鈕
				// ((FunctionAdapter) MainActivityGrid.getAdapter())
				// .notifyDataSetChanged();
				return;
			}

			String aa = ((FunctionAdapter) MainActivityGrid.getAdapter()).li
					.get(arg2).Functionintent;

			// 拍照判断是否在范围内，暂定距离超3千米不可拍照
			// if (Constants.PHOTO_FUNCTION
			// .equalsIgnoreCase(((FunctionAdapter) MainActivityGrid
			// .getAdapter()).li.get(arg2).Functionname)
			// && distance > 3000) {
			// Toast.makeText(getApplicationContext(), "该店不在范围内，请到店拍照",
			// Toast.LENGTH_SHORT).show();
			// return;
			// }
			Intent intent = new Intent(aa);
			intent.putExtra("from", ((FunctionAdapter) MainActivityGrid
					.getAdapter()).li.get(arg2).Functionname);
			// 把当前选择的功能保存
			((MyApplication) getApplication()).Savelocaldata("CURRENT_FUNC",
					((FunctionAdapter) MainActivityGrid.getAdapter()).li
							.get(arg2).Functionname);
			FunctionViewActivity.this.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void show_detail(View view) {
		if (linearLayout1.isShown()) {
			linearLayout1.setVisibility(8);
			txtview_show.setText("详细信息>>");
			go_edit.setVisibility(8);
		} else {
			if (linear_salesman.getChildCount() < 1
					|| linear_driverman.getChildCount() < 1) {
				getSalesmanDriver();
			}

			linearLayout1.setVisibility(0);
			txtview_show.setText("<<收起");
			go_edit.setVisibility(0);
		}

	}

	void getSalesmanDriver() {
		new Thread() {
			public void run() {
				try {
					Looper.prepare();
					salesManList = SyncHelper
							.GetSalesMan_Driver(((MyApplication) getApplication())
									.Getlocaldata().current_custom_code);
					message(2);
					Looper.loop();
					// 远程获取业务员和司机
					// salesManList = new ArrayList<Map<String, String>>();
					// Map<String, String> map = new HashMap<String, String>();
					// map.put("F_NAME", "张三");
					// map.put("F_NUMBER", "110");
					// map.put("F_TYPE", "1");
					// salesManList.add(map);
					// map = new HashMap<String, String>();
					// map.put("F_NAME", "张师傅");
					// map.put("F_NUMBER", "112");
					// map.put("F_TYPE", "2");
					// salesManList.add(map);
					// message(2);
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	public void go_edit(View view) {
		if (showToast())
			return;
		intent = new Intent(FunctionViewActivity.this,
				Custom_Add_Edit_Activity.class);
		intent.putExtra("tv_cus_code", tv_cus_code.getText());
		intent.putExtra("tv_cus_name", strName);
		intent.putExtra("tv_address", tv_address.getText());
		intent.putExtra("tv_person", tv_person.getText());
		intent.putExtra("tv_tel", tv_tel.getText());

		intent.putExtra("tv_lat", strLat);
		intent.putExtra("tv_lon", strLon);

		startActivity(intent);
	}

	void getLocal_Funcs() {
		new Thread() {
			public void run() {
				try {
 					list = FunctionDAL.getFUNCList(getApplication(), "1");
					if (list.size() < 1) {
						getFunc_sync();
					} else {
						message(1);
					}
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();

	}
    //获取功能  保存到数据库
	void getFunc_sync() {
		new Thread() {
			public void run() {
				try {
					SyncHelper.RelocateTable(getApplicationContext(),
							"T_SYS_APPFUNC", SyncHelper.GetAPP_FUNC(
									((MyApplication) getApplication())
											.Getlocaldata().app,
									((MyApplication) getApplication())
											.Getlocaldata().personno));
					getLocal_Funcs();
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

	/**
	 * @Title: showToast
	 * @Description:TODO 通过客户编码判断是否有客户信息
	 * @author WangMin
	 * @date 2013-2-27 下午06:13:56
	 * @return true||false
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean showToast() {
		if (tv_cus_code.getText().toString().trim().length() == 0) {
			Toast.makeText(getApplicationContext(), "无客户信息，不可操作！",
					Toast.LENGTH_SHORT).show();
			return true;
		} else
			return false;
	}

	void Attend(int type) {
		final int flag = type;
		new Thread() {
			public void run() {
				try {
					Localdata loc = ((MyApplication) getApplication())
							.Getlocaldata();
					if (SyncHelper.Attend(loc.current_custom_code,
							loc.personno, loc.lastlat, loc.lastlon, flag + "")) {
						CustomDAL.markcustom_attend(getApplication(),
								loc.current_custom_code, flag + "");
						((MyApplication) getApplicationContext())
								.Savelocaldata("current_custom_attended", flag
										+ "");
						MyApplication.WriteLog("成功！");
					}
					message(1);
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();

	}
}
