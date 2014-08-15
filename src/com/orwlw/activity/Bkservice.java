package com.orwlw.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import com.baidu.location.LocationClient;
import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;
import com.orwlw.dal.LoationDAL;
import com.orwlw.model.LocationModel;

import android.app.Notification;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ParseException;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * ��̨���еķ���ʱ��λ
 */
public class Bkservice extends Service {
	private int Cid;
	private int Count = 0;
	private String GPS_Time;
	private String GPS_LON = "";
	private String GPS_LAT = "";
	private float speed;
	private String IMEI;
	private String UID;
	private String IMSI;
	private String INFO;
	private Integer JG = Integer.valueOf(0);
	private int Lac;
	private int sysid;
	private int mcc;
	private int mnc;
	boolean iscmda = false;
	/**
	 * �ϴλ�ȡ�û���Ϣʱ��
	 */
	private long LastGetUserNumTime = 0L;

	/**
	 * �ϴι���
	 */
	private long LastWorkTime = 0L;

	/**
	 * �ϴδ�gpsʱ��
	 */
	private long OpenGpsTime = 0L;
	private String StartTime;
	private boolean Stoped = false;
	private String UserNum;
	private String VER;
	private MyThread WorkThread;
	private boolean b_getuserinfo = true;
	private boolean b_initgps = false;
	private boolean b_initsysinfo = false;
	private String inWorkTime = "1";
	private LocationListener locListener;
	private LocationManager locManager;
	private Handler myHandler;
	private static PowerManager.WakeLock wakeLock;
	public SimpleDateFormat formatter = new SimpleDateFormat(
			"yyy-MM-dd HH:mm:ss");
    
	TelephonyManager tm;
	String neiborstring = "";

	private LocationClient mLocClient;
	public LocationModel loc;

	/**
	 * ��ʼ��gps
	 */
	private boolean InitGPS() {
		try {
			this.locManager = ((LocationManager) getSystemService("location"));
			if (!this.locManager.isProviderEnabled("gps")) {
				MyApplication.WriteLog("GPS�����ã���λ��ʽΪ��վ");
				return false;
			} else {
				try {
					this.locListener = new LocationListener() {
						public void onLocationChanged(Location paramLocation) {
							MyApplication.SaveLog(Constants.GetTime()
									+ "GPS��λ�ɹ�");
							Bkservice.this.GPS_LON = Double
									.toString(paramLocation.getLongitude());
							Bkservice.this.GPS_LAT = Double
									.toString(paramLocation.getLatitude());
							Date localDate = new Date(paramLocation.getTime());
							SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Bkservice.this.GPS_Time = localSimpleDateFormat
									.format(localDate);
							Bkservice.this.OpenGpsTime = 1L;
							Bkservice.this.speed = paramLocation.getSpeed();
						}

						public void onProviderDisabled(String paramString) {
						}

						public void onProviderEnabled(String paramString) {
						}

						public void onStatusChanged(String paramString,
								int paramInt, Bundle paramBundle) {
						}
					};
					MyApplication.WriteLog("GPS��ʼ���ɹ�");
					return true;
				} catch (Exception localException2) {
					MyApplication.WriteLog("����Listener����");
					return false;
				}
			}
		} catch (Exception localException1) {
			return false;
		}
	}

	/**
	 * ��ȡϵͳ��Ϣ
	 */
	private boolean InitSYSINFO() {
		this.IMEI = "";
		this.IMSI = "";
		int j;
		try {
			TelephonyManager localTelephonyManager = (TelephonyManager) getSystemService("phone");
			this.IMEI = localTelephonyManager.getDeviceId();
			this.IMSI = localTelephonyManager.getSubscriberId();
			int i;
			if ((this.IMEI != null) && (this.IMEI.length() > 6)) {
				// if (this.IMSI.length() <= 8)
				this.IMSI += this.IMEI;
				MyApplication.WriteLog("��ȡϵͳ��Ϣ�ɹ�");
				return true;
			} else {
				this.IMEI = "";
				this.IMSI = "";
				MyApplication.WriteLog("ȡϵͳ��Ϣʧ�ܣ��Ƿ����ֻ���");
				return false;
			}
		} catch (Exception e) {
			this.IMEI = "";
			this.IMSI = "";
			MyApplication.WriteLog("ȡϵͳ��Ϣʱ����" + e.getMessage());
			return false;
		}

	}

	/**
	 * ������Ϣ��handler
	 */
	private void SendMsg(int paramInt) {
		try {
			Message localMessage = new Message();
			localMessage.what = paramInt;
			this.myHandler.sendMessage(localMessage);
			return;
		} catch (Exception localException) {
			while (true)
				MyApplication.WriteLog("SendMsg Error");
		}
	}

	/**
	 * ��ʼ��handler
	 */
	private void initHandler() {
		this.myHandler = new Handler() {
			public void handleMessage(Message paramMessage) {
				switch (paramMessage.what) {
				case 1000:
					if (!Bkservice.this.b_initgps)
						break;
					Bkservice.this.locManager.requestLocationUpdates("gps", 0L,
							0.0F, Bkservice.this.locListener);
					break;
				case 2000:
					if (!Bkservice.this.b_initgps)
						break;
					Bkservice.this.locManager
							.removeUpdates(Bkservice.this.locListener);
					break;
				case 3000:
					// Bkservice.this.CheckVerUpdate();
					break;
				}
				super.handleMessage(paramMessage);
			}
		};
	}

	/**
	 * �̵߳ȴ�ʱ��
	 */
	private void waittime(int paramInt) {
		long l = paramInt;
		try {
			Thread.sleep(l);
		} catch (InterruptedException localInterruptedException) {
			// break;
		}
	}

	/**
	 * ������־�����ļ�
	 */
	public void DebugLog(String paramString) {
		try {
			Object localObject1 = new Date(System.currentTimeMillis());
			localObject1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format((Date) localObject1)
					+ " ["
					+ String.valueOf(this.Count) + "] " + paramString + "\r\n";
			Object localObject2 = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/LttGpsLog.txt";
			File localFile = new File((String) localObject2);
			if (!localFile.exists())
				localFile.createNewFile();
			try {
				localObject2 = new FileOutputStream((String) localObject2, true);
				((FileOutputStream) localObject2).write(((String) localObject1)
						.getBytes());
				((FileOutputStream) localObject2).flush();
				((FileOutputStream) localObject2).close();
				return;
			} catch (Exception localException1) {
				while (true)
					localException1.printStackTrace();
			}
		} catch (Exception localException2) {
			while (true)
				MyApplication.WriteLog("д�ļ�����" + localException2.getMessage());
		}
	}

	private String newRandomUUID() {
		String uuidRaw = UUID.randomUUID().toString();
		return uuidRaw.replaceAll("-", "");
	}

	/**
	 * ��ȡλ�����ݲ��ύ
	 */
	public void GetLocationToSubmit() {
		try {
			if (!Bkservice.this.GPS_LON.equalsIgnoreCase("")) {
				loc = new LocationModel();
				loc.ID = newRandomUUID();
				loc.LAT = Bkservice.this.GPS_LAT;
				loc.LON = Bkservice.this.GPS_LON;
				loc.SPEED = Bkservice.this.speed + "";
				loc.IMEI = this.IMEI;
				loc.PERSONNO = ((MyApplication) getApplication())
						.Getlocaldata().personno;
				loc.DATETIME = Bkservice.this.GPS_Time;
				loc.FLAG = "2";
				submit_location(loc);
			} else if (mLocClient != null) {
				((MyApplication) getApplication()).SubLocation = true;
				mLocClient.requestLocation();
			}
		} catch (Exception e) {
			MyApplication.WriteLog("��ȡλ�����ݲ��ύ�����쳣��" + e.getMessage());
		}
	}

	void submit_location(LocationModel loc) {
		final LocationModel loc1 = loc;
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					boolean b = SyncHelper
							.submit_location(((MyApplication) getApplication())
									.Getlocaldata().app, loc1);
					if (!b) {
						loc1.ID = UUID.randomUUID().toString();
						LoationDAL.Insert(getApplication(), loc1);
						MyApplication.SaveLog(Constants.GetTime()
								+ "�ύʧ�ܱ���GPS���Ƕ�λ����");
					} else {
						MyApplication.SaveLog(Constants.GetTime()
								+ "���ύGPS���Ƕ�λ����");
						List<LocationModel> list = LoationDAL
								.getLocationList(getApplication());
						for (int i = 0; i < list.size(); i++) {
							LocationModel loc = list.get(i);
							SyncHelper.submit_location(
									((MyApplication) getApplication())
											.Getlocaldata().app, loc);
							LoationDAL.Delete(getApplication(), loc.ID);
							MyApplication.SaveLog(Constants.GetTime()
									+ "�ύһ����ʷ����");
						}
					}
					Bkservice.this.GPS_LON = "";
					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	public IBinder onBind(Intent paramIntent) {
		return null;
	}

	public void onCreate() {
		try {
			MyApplication.WriteLog("Service onCreate");
			Date localDate = new Date(System.currentTimeMillis());
			this.StartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(localDate);
			initHandler();

			this.WorkThread = new MyThread();
			this.WorkThread.start();
			startForeground(1, new Notification());

			MyApplication.SaveLog("���������߳�");

		} catch (Exception e) {
			MyApplication.SaveLog("����bkservice create�쳣");
		}

	}

	public void onDestroy() {
		releaseWakeLock();
		if (this.WorkThread != null) {
			this.Stoped = true;
			MyApplication.WriteLog("Stop WorkThread");
		}
		MyApplication.WriteLog("Serviceֹͣ");
		Intent localIntent = new Intent();
		localIntent.setClass(this, Bkservice.class); // ����ʱ��������Service
		this.startService(localIntent);
	}

	public void onStart(Intent paramIntent, int paramInt) {
	}

	public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
		// MyApplication.SaveLog("Service onStartCommand");
		int space = Integer.parseInt(((MyApplication) getApplication())
				.Getlocaldata().Space);
		JG = 60 * space;// ��λ���
		this.startForeground(0, new Notification());
		mLocClient = ((MyApplication) getApplication()).mLocationClient;
		return Service.START_STICKY;
	}

	public class MyThread extends Thread {
		public MyThread() {
		}

		public void run() {
			while (!Bkservice.this.Stoped) {
				Bkservice locallttGPSService = Bkservice.this;
				locallttGPSService.Count = (1 + locallttGPSService.Count);
				Bkservice.this.waittime(5000);
				if (Bkservice.this.b_initsysinfo)// ��ʼ��ϵͳ��Ϣ
				{
					if (!Bkservice.this.b_initgps)
						Bkservice.this.b_initgps = Bkservice.this.InitGPS();

					if (Bkservice.this.b_getuserinfo) {
						long l = System.currentTimeMillis();
						if (Bkservice.this.OpenGpsTime != 0L) {
							if (!Bkservice.this.locManager
									.isProviderEnabled("gps"))
								Bkservice.this.OpenGpsTime = 1L;
							MyApplication.SaveLog(Constants.GetTime()
									+ "����ʱ3���ӿ�ʼ");
							MyApplication.WriteLog("�ȴ�GPS���ݣ�"
									+ String.valueOf(l
											- Bkservice.this.OpenGpsTime)
									+ "->" + String.valueOf(3 * 60000));
							if (l - Bkservice.this.OpenGpsTime <= 60000 * 3)
								continue;
							MyApplication.SaveLog(Constants.GetTime()
									+ "����ʱ3���ӽ���");
							MyApplication.WriteLog("�ȴ�GPS����ʱ�䵽��"
									+ String.valueOf(l
											- Bkservice.this.OpenGpsTime)
									+ "->" + String.valueOf(3 * 60000));
							MyApplication.SaveLog("�����ύλ������...");
							Bkservice.this.OpenGpsTime = 0L;
							Bkservice.this.LastWorkTime = System
									.currentTimeMillis();

							Bkservice.this.SendMsg(2000);

							Bkservice.this.GetLocationToSubmit();// ��ȡ��λ���ϱ�λ��
							continue;
						}
						if (isDateBefore()) {
							MyApplication.WriteLog("��λ����ʱ��"
									+ String.valueOf(l
											- Bkservice.this.LastWorkTime)
									+ "->"
									+ String.valueOf(1000 * Bkservice.this.JG
											.intValue()));
							if (l - Bkservice.this.LastWorkTime <= 1000 * Bkservice.this.JG
									.intValue())
								continue;
							MyApplication.SaveLog(Constants.GetTime()
									+ "�������ʱ�䵽");

							acquireWakeLock(getApplicationContext());// ����cpu
							isGPSEnable();// ��gps
							Bkservice.this.OpenGpsTime = System
									.currentTimeMillis();
							Bkservice.this.SendMsg(1000);
							continue;
						}

						// MyApplication.SaveLog(Constants.GetTime() +
						// "��ǰ�ǹ���ʱ�Σ�");
						// String.valueOf(60L - (l -
						// Bkservice.this.LastGetUserNumTime) / 60000L
						continue;
					}
					if (!Bkservice.this.b_getuserinfo)
						continue;
					Bkservice.this.OpenGpsTime = 0L;
					Bkservice.this.LastWorkTime = System.currentTimeMillis();
					Bkservice.this.LastGetUserNumTime = System
							.currentTimeMillis();
					continue;
				}
				Bkservice.this.b_initsysinfo = Bkservice.this.InitSYSINFO();
			}
			MyApplication.SaveLog("�����߳���ֹ");
		}
	}

	public boolean isDateBefore() {
		try {
			// ��ǰʱ��
			java.util.Date xban = null;
			java.util.Date sban = null;
			java.util.Date dqianDate = null;

			String time1 = ((MyApplication) getApplication()).Getlocaldata().EndWorktime;
			String time2 = ((MyApplication) getApplication()).Getlocaldata().StartWorktime;

			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
			String time = df.format(curDate);
			ContentResolver cv = this.getContentResolver();
			String strTimeFormat = android.provider.Settings.System.getString(
					cv, android.provider.Settings.System.TIME_12_24);
			// Log.e("��ʾ��", "��ǰΪ" + strTimeFormat + "Сʱ��");
			// if (strTimeFormat.equals("12"))
			// {
			// Log.e("��ʾtime��", "" + time);
			// Log.e("��ʾСʱ��", "" + Integer.parseInt(time.substring(0, 2)));
			// String hour = (Integer.parseInt(time.substring(0, 2)) + 12) + "";
			// Log.e("��ʾhour��", "" + hour);
			// time = hour + ":" + time.substring(3, 4);
			// Log.e("��ʾtime��", "" + time);
			// }

			try {
				dqianDate = df.parse(time);
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				MyApplication.WriteLog("ʱ��ת������" + e1.getMessage().toString());
			}

			try {
				xban = df.parse(time1);
				sban = df.parse(time2);
			} catch (java.text.ParseException e) {
				MyApplication.WriteLog("����" + e.getMessage());
			}
			if (dqianDate.before(xban) && !dqianDate.before(sban)) {
				MyApplication.WriteLog("��ȷ��Χ�ڣ�");
				return true;
			} else {
				MyApplication.WriteLog("������Χ��");
				return false;
			}
		} catch (ParseException e) {
			MyApplication.WriteLog("�жϴ���" + e.getMessage());
			return false;
		}
	}

	public static void acquireWakeLock(Context context) {
		if (wakeLock == null) {
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, context
					.getClass().getCanonicalName());
			wakeLock.acquire();
			MyApplication.SaveLog(Constants.GetTime() + "����cpu");
		} else {
			wakeLock.acquire();
			MyApplication.SaveLog(Constants.GetTime() + "����cpu");
		}
	}

	public static void releaseWakeLock() {
		if (wakeLock != null && wakeLock.isHeld()) {
			wakeLock.release();
			wakeLock = null;
			MyApplication.SaveLog(Constants.GetTime() + "�ͷ�cpu");
		}
	}

	public void togglegps() {
		// try {
		// Settings.Secure.setLocationProviderEnabled(getContentResolver(),
		// LocationManager.GPS_PROVIDER, true);
		// } catch (ParseException e) {
		// MyApplication.WriteLog("togglegps" + e.getMessage());
		// }

		Intent intent = new Intent();
		intent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		intent.addCategory("android.intent.category.ALTERNATIVE");
		intent.setData(Uri.parse("custom:3"));
		this.sendBroadcast(intent);

	}

	/**
	 * �Ƿ��� wifi true������ false���ر�
	 * 
	 * һ��Ҫ����Ȩ�ޣ� <uses-permission
	 * android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
	 * <uses-permission
	 * android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
	 * 
	 * 
	 * @param isEnable
	 */
	public void setWifi(boolean isEnable) {

		WifiManager mWm = (WifiManager) this
				.getSystemService(Context.WIFI_SERVICE);
		if (mWm == null) {
			mWm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
			return;
		}

		System.out.println("wifi====" + mWm.isWifiEnabled());
		if (isEnable) {// ����wifi

			if (!mWm.isWifiEnabled()) {

				mWm.setWifiEnabled(true);

			}
		} else {
			// �ر� wifi
			if (mWm.isWifiEnabled()) {
				mWm.setWifiEnabled(false);
			}
		}

	}

	// ��ʱ����
	public void isWIFIEnable() {
		WifiManager mWm = (WifiManager) this
				.getSystemService(Context.WIFI_SERVICE);

		switch (mWm.getWifiState()) {
		case WifiManager.WIFI_STATE_DISABLED:
			System.out.println("wifi state --->" + "WIFI�ѹر�");
			Editor localdata = getSharedPreferences("autocontrol", 0).edit();
			localdata.putBoolean("handwifi", false);
			localdata.commit();
			MyApplication.WriteLog("�����Զ�����wifi");
			setWifi(true);// �����Զ���wifi
			break;
		case WifiManager.WIFI_STATE_DISABLING:
			System.out.println("wifi state --->" + "WIFI���ڹر���");
			break;
		case WifiManager.WIFI_STATE_ENABLED:
			System.out.println("wifi state --->" + "WIFI������");
			break;
		case WifiManager.WIFI_STATE_ENABLING:
			System.out.println("wifi state --->" + "WIFI����������");
			break;
		case WifiManager.WIFI_STATE_UNKNOWN:
			System.out.println("wifi state --->" + "δ֪WIFI״̬");
			break;
		}

	}

	public boolean isGPSEnable() {
		String str = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!str.equalsIgnoreCase("")) {
			MyApplication.SaveLog("gps�ѿ��������");
			return str.contains("gps");
		} else {
			togglegps();// �����Զ���gps
			MyApplication.SaveLog("�����Զ�����gps");
			return false;
		}
	}

}
