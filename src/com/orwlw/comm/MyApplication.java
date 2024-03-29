package com.orwlw.comm;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.androidpn.client.ServiceManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.orwlw.activity.Bkservice;
import com.orwlw.activity.Checkserver;
import com.orwlw.activity.CrashHandler;
import com.orwlw.activity.InitializeServer;
import com.orwlw.activity.MainActivity;
import com.orwlw.activity.R;
import com.orwlw.dal.CustomDAL;
import com.orwlw.dal.LoationDAL;
import com.orwlw.model.Localdata;
import com.orwlw.model.LocationModel;
import com.orwlw.net.Image_Comm_Satue;
import com.orwlw.view.MyListView;

import android.R.integer;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyApplication extends Application {
	public static boolean Debug = true;
	public boolean SubLocation = false;
	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener;
	public LocationModel loc;
	public TextView maddress;
	public Application mapplicaiton;
	public BMapManager mBMapMan;
	public Handler handler;
	private static MyApplication instance;

	@Override
	public void onCreate() {

		try {
			MyApplication.WriteLog("Myapplication 创建");
			if (myListener == null) {
				myListener = new MyLocationListenner();
			}
		} catch (Exception e) {
			this.WriteLog(e.getMessage());
		}

		if (mLocationClient == null) {
			mLocationClient = new LocationClient(getApplicationContext());
			setLocationOption();
			mLocationClient.registerLocationListener(myListener);
			mLocationClient.start();
		}

		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());
		crashHandler.sendPreviousReportsToServer();

		mapplicaiton = this;
		if (this.mBMapMan == null) {
			mBMapMan = new BMapManager(this);
			mBMapMan.init(Constants.MAP_KEY, null);
			mBMapMan.start();
		}

		ServiceManager serviceManager = new ServiceManager(this);
		serviceManager.setNotificationIcon(R.drawable.ic_launcher);
		serviceManager.startService();

		startService(new Intent(this, InitializeServer.class));
		startService(new Intent(Constants.CORE_SERVICE));

		Intent intent1 = new Intent(this, Bkservice.class);
		startService(intent1);
		super.onCreate();
		MyApplication.WriteLog("Myapplication 创建完成");
	}

	@Override
	public void onLowMemory() {
		MyApplication.WriteLog("Myapplication 内存低了");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		MyApplication.WriteLog("Myapplication 终止");
		if (this.mBMapMan != null) {
			this.mBMapMan.destroy();
			this.mBMapMan = null;
		}
		super.onTerminate();
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// MyApplication.WriteLog("定位开始时间：" + Constants.GetTime());
			if (location == null)
				return;
			loc = new LocationModel();
			if (location.getLatitude() != 4.9E-324) {
				loc.LAT = location.getLatitude() + "";
				loc.LON = location.getLongitude() + "";
				Savelocaldata("lastlat", loc.LAT);
				Savelocaldata("lastlon", loc.LON);
			} else {
				loc.LAT = "0";
				loc.LON = "0";
			}
			loc.RADIUS = location.getRadius() + "";
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				loc.SPEED = location.getSpeed() + "";
				loc.FLAG = "3";
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				loc.ADDRESS = location.getAddrStr();
				loc.FLAG = "1";
				if (maddress != null) {
					maddress.setText(location.getAddrStr());
				}

			}
			if (loc.LAT != null && !loc.LAT.equalsIgnoreCase("")
					&& !loc.LAT.equalsIgnoreCase("0")) {
				if (handler != null)
					message(handler, 2);// 命令排序，标注当前位置
			}

			loc.IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
					.getDeviceId();
			loc.IMSI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
					.getSubscriberId();
			loc.PERSONNO = ((MyApplication) mapplicaiton).Getlocaldata().personno;
			loc.DATETIME = location.getTime();
			if (SubLocation) {
				submit_location(loc);// 提交位置
				MyApplication.SaveLog(Constants.GetTime() + "已提交BAIDU定位数据");
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setAddrType("all"); // 设置地址信息，仅设置为“all”时有地址信息，默认无地址信息

		String ss = Getlocaldata().Space;
		int space = Integer.parseInt(((Getlocaldata().Space
				.equalsIgnoreCase("") ? 5 : Getlocaldata().Space)) + "");
		// option.setScanSpan(space * 60000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		option.setScanSpan(100); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		mLocationClient.setLocOption(option);
	}

	public void message(Handler handler, int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		if (handler != null)
			handler.sendMessage(attaget);
	}

	void submit_location(LocationModel loc) {
		final LocationModel loc1 = loc;
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					boolean b = SyncHelper.submit_location(
							((MyApplication) mapplicaiton).Getlocaldata().app,
							loc1);
					if (!b) {
						loc1.ID = UUID.randomUUID().toString();
						LoationDAL.Insert(mapplicaiton, loc1);
					} else {
						List<LocationModel> list = LoationDAL
								.getLocationList(mapplicaiton);
						for (int i = 0; i < list.size(); i++) {
							LocationModel loc = list.get(i);
							SyncHelper.submit_location(
									((MyApplication) mapplicaiton)
											.Getlocaldata().app, loc);
							LoationDAL.Delete(mapplicaiton, loc.ID);
						}
					}
					((MyApplication) mapplicaiton).SubLocation = false;
					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	/**
	 * 输出屏幕
	 */
	public void ShowLog(String paramString) {
		if (Debug)
			Toast.makeText(getApplicationContext(), paramString + "",
					Toast.LENGTH_LONG).show();
	}

	/**
	 * 输出日志
	 */
	public static void WriteLog(String paramString) {
		if (Debug) {
			Log.e("WorkManager_log：", paramString);
		}
	}

	/**
	 * 记录日志
	 */
	public static void SaveLog(String paramString) {
		if (Debug) {
			LogCommandHelper.SaveLog(paramString);
		}
	}

	public Localdata Getlocaldata() {//????什么意思
		Localdata localdata = new Localdata();
		SharedPreferences localsharedata = getSharedPreferences("localdata", 0);
		localdata.app = localsharedata.getString("app", "");
		localdata.personno = localsharedata.getString("personno", "");
		localdata.personname = localsharedata.getString("personname", "");
		localdata.currentphototype = localsharedata.getString(
				"currentphototype", "");
		localdata.lastlat = localsharedata.getString("lastlat", "0");
		localdata.lastlon = localsharedata.getString("lastlon", "0");
		localdata.current_custom_code = localsharedata.getString(
				"current_custom_code", "");
		// localdata.cus_code = localsharedata.getString("cus_code", "");
		localdata.print_device = localsharedata.getString("print_device", "");
		localdata.Space = localsharedata.getString("Space", "5");
		localdata.StartWorktime = localsharedata.getString("StartWorktime",
				"08:00");
		localdata.EndWorktime = localsharedata
				.getString("EndWorktime", "18:00");

		localdata.CURRENT_PARAMS = localsharedata.getInt("CURRENT_PARAMS", -1);
		localdata.CURRENT_COMM_CODE = localsharedata.getString(
				"CURRENT_COMM_CODE", "");
		localdata.CURRENT_COMM_NAME = localsharedata.getString(
				"CURRENT_COMM_NAME", "");

		localdata.CURRENT_COMM_NUM1 = localsharedata.getString(
				"CURRENT_COMM_NUM1", "");
		localdata.CURRENT_COMM_NUM2 = localsharedata.getString(
				"CURRENT_COMM_NUM2", "");
		localdata.CURRENT_COMM_DATE = localsharedata.getString(
				"CURRENT_COMM_DATE", "");
		localdata.CURRENT_COMM_REASON = localsharedata.getString(
				"CURRENT_COMM_REASON", "");
		localdata.CURRENT_COMM_REASON_ID = localsharedata.getString(
				"CURRENT_COMM_REASON_ID", "");

		localdata.LAST_SYSNC_DATE = localsharedata.getString("LAST_SYSNC_DATE",
				"");
		localdata.CURRENT_FUNC = localsharedata.getString("CURRENT_FUNC", "");
		localdata.lastlat = localsharedata.getString("lastlat", "0");
		localdata.lastlon = localsharedata.getString("lastlon", "0");
		localdata.current_custom_attended = localsharedata.getString(
				"current_custom_attended", "");
		return localdata;
	}

	public void Savelocaldata(String key, String value) {
		Editor localdata = getSharedPreferences("localdata", 0).edit();
		localdata.putString(key, value);
		localdata.commit();
	}

	public void SavelocalInt(String key, int value) {
		Editor localdata = getSharedPreferences("localdata", 0).edit();
		localdata.putInt(key, value);
		localdata.commit();
	}

	public void setWifi(boolean isEnable) {

		WifiManager mWm = (WifiManager) this
				.getSystemService(Context.WIFI_SERVICE);
		if (mWm == null) {
			mWm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
			return;
		}

		System.out.println("wifi====" + mWm.isWifiEnabled());
		if (isEnable) {// 开启wifi

			if (!mWm.isWifiEnabled()) {

				mWm.setWifiEnabled(true);

			}
		} else {
			// 关闭 wifi
			if (mWm.isWifiEnabled()) {
				mWm.setWifiEnabled(false);
			}
		}

	}

	public void togglegps() {
		Intent intent = new Intent();
		intent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		intent.addCategory("android.intent.category.ALTERNATIVE");
		intent.setData(Uri.parse("custom:3"));
		this.sendBroadcast(intent);

	}

	// 单例模式中获取唯一的MyApplication实例
	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

}
