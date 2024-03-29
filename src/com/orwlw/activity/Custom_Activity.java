package com.orwlw.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.orwlw.comm.Constants;
import com.orwlw.comm.CustomAdapter;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;
import com.orwlw.dal.CustomDAL;
import com.orwlw.model.LocationModel;
import com.orwlw.model.ParamsInfo;
import com.orwlw.view.MyListView;
import com.orwlw.view.MyListView.OnRefreshListener;

public class Custom_Activity extends Activity implements OnItemClickListener {
	public MyListView myListView;
	private Context mContext;
	public List<Map<String, Object>> list = null;
	private LocationClient mLocClient;
	private HashMap<String, Object> map = new HashMap<String, Object>();
	

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				System.out.println("------");
				myListView.setAdapter(new CustomAdapter(mContext, list));
				System.out.println("--===-");
				break;
			case 2:
				getCustomList_near(((MyApplication) getApplication()).loc);
				break;  
			case 3:
				ParamsInfo pi = (ParamsInfo) msg.obj;
				if (pi.CUSTOM_TYPE == Constants.CUSTOM_TYPE_PLAN) {
					refresh_local_customs_plan();
				} else if (pi.CUSTOM_TYPE == Constants.CUSTOM_TYPE_ALL) {
					refresh_local_customs();
				}
				break;
			case 4:
				ParamsInfo pi1 = (ParamsInfo) msg.obj;
				getSearchCustoms(pi1.CUSTOM_WHERE);
				break;
			}

		};
	};

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_view);
		initlizeview();
		MyApplication.WriteLog("列表界面启动oncreate");
		
	}

	@Override
	protected void onStart() {
		refresh_local_customs();
		super.onStart();
	}

	@Override
	protected void onResume() {
		mylocation();		
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		MyApplication.WriteLog("列表界面 onDestroy");
		super.onDestroy();
	}

	void initlizeview() {
		mContext = this;
		myListView = (MyListView) findViewById(R.id.myexam_listview);
		((MyApplication) getApplication()).handler = handler1;
		myListView.setOnItemClickListener(this);
		myListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {

						list = SyncHelper
								.GetCustomlist(((MyApplication) getApplication())
										.Getlocaldata().personno);
						
						SyncHelper
								.RelocateTable(mContext, "T_SCM_CUSTOM", list);
						mylocation();// 获取定位后排序list
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						myListView.onRefreshComplete();
					}
				}.execute();
			}
		});
		mLocClient = ((MyApplication) getApplication()).mLocationClient;
		CustomViewActivity.send_handler = handler1;
	}

	void mylocation() {
		if (mLocClient != null) {
			mLocClient.requestLocation();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TextView tv_code = (TextView) arg1.findViewById(R.id.tv_code);
		String strCode = (String) tv_code.getText();
		((MyApplication) getApplication()).Savelocaldata("current_custom_code",
				strCode);
		MainActivity.setcurrent(1, map);
	}

	/**
	 * @Description:TODO 获取根据当前位置获取排序的客户信息
	 */
	void getCustomList_near(LocationModel loc1) {
		final LocationModel loc = loc1;
		new Thread() {
			public void run() {
				Looper.prepare();
				list = CustomDAL.getCustomList_near(getApplication(), loc);
				message(1);
				Looper.loop();
			};
		}.start();
	}

	/**
	 * @Description:TODO 获取本地計劃客户信息
	 */
	void refresh_local_customs_plan() {
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					list = CustomDAL.getCustomList_plan(getApplication());
					message(1);

					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	/**
	 * @Description:TODO 获取本地的所有客户信息
	 */
	void refresh_local_customs() {
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					list = CustomDAL.getCustomList(getApplication());
					message(1);
					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	/**
	 * @Description:TODO 获取条件筛选后的客户
	 */
	void getSearchCustoms(String strValue) {
		list = CustomDAL.searchCusList(mContext, strValue);
		message(1);
	}
}
