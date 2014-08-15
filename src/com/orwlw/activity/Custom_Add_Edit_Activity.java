package com.orwlw.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidu.platform.comapi.map.Projection;
import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;
import com.orwlw.dal.CustomDAL;

public class Custom_Add_Edit_Activity extends Activity implements
		OnClickListener {
	private EditText et_custom_name;
	private EditText et_custom_code;
	private EditText et_address;
	private EditText et_person;
	private EditText et_phone;
	private Intent intent;
	private Spinner price_raid;
	private Spinner drow_level;
	private Spinner down_area;
	private TextView textView;
	private TextView textView_level;
	private TextView textView_area;
	private List<Map<String, Object>> dropdownList;
	private List<Map<String, Object>> dropdownList_level;
	private List<Map<String, Object>> dropdownList_area;
	private List<Map<String, Object>> InfoList;
	String price_raid_code = "";
	String custom_level = "";
	String custom_area = "";
	private Context mContext;
	ProgressDialog pbar;
	boolean result;

	private LocationClient mLocClient;
	// BMapManager mBMapMan = null;
	static MapView mMapView = null;
	private OverlayTest tempitemOverlay;
	String currentlon;
	String currentlat;
	int tempx = 0;
	int tempy = 0;
	boolean flag = false;
	LinearLayout linearLayout1;
	TextView status;
	MyLocationOverlay myLocationOverlay;

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				SimpleAdapter simpleAdapter = new SimpleAdapter(mContext,
						dropdownList, R.layout.price_raid, new String[] {
								"F_PRICE_RAID_CODE", "F_PRICE_RAID" },
						new int[] { R.id.F_type_ID, R.id.F_type_name });
				price_raid.setAdapter(simpleAdapter);
				price_raid.performClick();
				break;
			case 2:
				SimpleAdapter simpleAdapter1 = new SimpleAdapter(mContext,
						dropdownList_level, R.layout.price_raid, new String[] {
								"F_KEY_CODE", "F_KEY_VALUE" }, new int[] {
								R.id.F_type_ID, R.id.F_type_name });
				drow_level.setAdapter(simpleAdapter1);
				drow_level.performClick();
				break;
			case 3:
				// 赋值
				if (InfoList == null)
					break;
				if (InfoList.size() > 0) {
					if (!InfoList.get(0).get("F_PRICE_RAID").toString()
							.equalsIgnoreCase(""))
						textView.setText(InfoList.get(0).get("F_PRICE_RAID")
								.toString());
					if (!InfoList.get(0).get("F_KEY_VALUE").toString()
							.equalsIgnoreCase(""))
						textView_level.setText(InfoList.get(0)
								.get("F_KEY_VALUE").toString());
					if (!InfoList.get(0).get("F_AREA_NAME").toString()
							.equalsIgnoreCase(""))
						textView_area.setText(InfoList.get(0)
								.get("F_AREA_NAME").toString());
				}
				break;

			case 4:
				if (result) {
					finish();
				}
				break;
			case 5:
				SimpleAdapter simpleAdapter2 = new SimpleAdapter(mContext,
						dropdownList_area, R.layout.price_raid, new String[] {
								"F_AREA_CODE", "F_AREA_NAME" }, new int[] {
								R.id.F_type_ID, R.id.F_type_name });
				down_area.setAdapter(simpleAdapter2);
				down_area.performClick();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.custom_detail_view);
		mContext = this;

		pbar = new ProgressDialog(Custom_Add_Edit_Activity.this);
		pbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		status = (TextView) findViewById(R.id.status);

		et_custom_code = (EditText) this.findViewById(R.id.et_CustomCode);
		et_custom_name = (EditText) this.findViewById(R.id.et_CustomName);
		et_address = (EditText) this.findViewById(R.id.et_Address);
		et_person = (EditText) this.findViewById(R.id.et_Person);
		et_phone = (EditText) this.findViewById(R.id.et_Phone);
		textView = (TextView) this.findViewById(R.id.txtview_Location);
		textView.setOnClickListener(this);
		textView_level = (TextView) this.findViewById(R.id.txtview_level);
		textView_level.setOnClickListener(this);
		textView_area = (TextView) this.findViewById(R.id.txtview_area);
		textView_area.setOnClickListener(this);

		price_raid = (Spinner) this.findViewById(R.id.spinner1);
		price_raid.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				textView.setText(dropdownList.get(position).get("F_PRICE_RAID")
						.toString());
				price_raid_code = dropdownList.get(position)
						.get("F_PRICE_RAID_CODE").toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		drow_level = (Spinner) this.findViewById(R.id.spinner2);
		drow_level.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				textView_level.setText(dropdownList_level.get(position)
						.get("F_KEY_VALUE").toString());
				custom_level = dropdownList_level.get(position)
						.get("F_KEY_CODE").toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		down_area = (Spinner) this.findViewById(R.id.spinner3);
		down_area.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				textView_area.setText(dropdownList_area.get(position)
						.get("F_AREA_NAME").toString());
				custom_area = dropdownList_area.get(position)
						.get("F_AREA_CODE").toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		mMapView = (MapView) findViewById(R.id.bmapsView1);
		myLocationOverlay = new MyLocationOverlay(mMapView);
		mMapView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					tempx = (int) event.getX();
					tempy = (int) event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					// String bString = "";
					break;
				case MotionEvent.ACTION_UP:
					if (tempx != 0) {

						if (Math.abs((int) event.getX() - tempx) < 5
								|| Math.abs((int) event.getY() - tempy) < 5) {
							// 将像素坐标转为地址坐标
							Projection getProjection = mMapView.getProjection();
							GeoPoint pt = getProjection.fromPixels(
									(int) event.getX(), (int) event.getY());

							mMapView.getOverlays().remove(tempitemOverlay);
							mMapView.refresh();

							currentlat = pt.getLatitudeE6() / 1e6 + "";
							currentlon = pt.getLongitudeE6() / 1e6 + "";

							Drawable mark = getResources().getDrawable(
									R.drawable.location);
							OverlayItem item1 = new OverlayItem(pt, "item1",
									"item1");
							tempitemOverlay = new OverlayTest(mark, mMapView);
							tempitemOverlay.removeAll();

							mMapView.getOverlays().add(tempitemOverlay);
							tempitemOverlay.addItem(item1);

							mMapView.refresh();
						}
					}
					break;

				}

				return false;
			}
		});
		// mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();
		mLocClient = ((MyApplication) getApplication()).mLocationClient;
		if (mLocClient != null)
			mLocClient.requestLocation();
		GeoPoint point;
		if (((MyApplication) getApplication()).loc != null) {
			point = new GeoPoint(
					(int) (Double
							.parseDouble(((MyApplication) getApplication()).loc.LAT) * 1E6),
					(int) (Double
							.parseDouble(((MyApplication) getApplication()).loc.LON) * 1E6));
			// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		} else if (!((MyApplication) getApplication()).Getlocaldata().lastlat
				.equalsIgnoreCase("")) {
			point = new GeoPoint(
					(int) (Double.parseDouble(((MyApplication) getApplication())
							.Getlocaldata().lastlat) * 1E6),
					(int) (Double
							.parseDouble(((MyApplication) getApplication())
									.Getlocaldata().lastlon) * 1E6));
		} else {
			point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
		}
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(17);// 设置地图zoom级别

	}

	/**
	 * @Title: init
	 * @Description:TODO 初始化数据
	 * @author WangMin
	 * @date 2013-2-22 下午05:31:12设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void init() {
		mMapView.getOverlays().clear();
		intent = getIntent();
		if (intent.getStringExtra("tv_cus_code") != null) {
			et_custom_code.setText(intent.getStringExtra("tv_cus_code"));
			et_custom_name
					.setText(intent.getStringExtra("tv_cus_name") == null ? ""
							: intent.getStringExtra("tv_cus_name"));
			et_address.setText(intent.getStringExtra("tv_address"));
			et_person.setText(intent.getStringExtra("tv_person"));
			et_phone.setText(intent.getStringExtra("tv_tel"));

			currentlat = intent.getStringExtra("tv_lat");
			currentlon = intent.getStringExtra("tv_lon");
			if (!currentlat.equalsIgnoreCase("")) {
				GeoPoint geoPoint = new GeoPoint(
						(int) (Double.parseDouble(currentlat) * 1E6),
						(int) (Double.parseDouble(currentlon) * 1E6));
				OverlayItem item1 = new OverlayItem(geoPoint, "item1", "item1");
				Drawable mark = getResources().getDrawable(
						R.drawable.newlocation);

				// tempitemOverlay = new OverlayTest(mark, mMapView);
				// // tempitemOverlay.removeAll();
				// mMapView.getOverlays().add(tempitemOverlay);
				// tempitemOverlay.addItem(item1);
				ImageView imgview = new ImageView(this);
				imgview.setImageDrawable(mark);
				MapView.LayoutParams layoutParam = new MapView.LayoutParams(
						MapView.LayoutParams.WRAP_CONTENT,
						MapView.LayoutParams.WRAP_CONTENT, geoPoint,
						MapView.LayoutParams.TOP);
				MapView.LayoutParams layoutParam1 = new MapView.LayoutParams(
						MapView.LayoutParams.WRAP_CONTENT,
						MapView.LayoutParams.WRAP_CONTENT, geoPoint,
						MapView.LayoutParams.BOTTOM);
				TextView textView = new TextView(this);
				textView.setTextColor(android.graphics.Color.BLUE);
				textView.setText(intent.getStringExtra("tv_cus_name"));
				// 添加View到MapView中
				mMapView.addView(imgview, layoutParam1);
				mMapView.addView(textView, layoutParam);
				mMapView.refresh();
			}
			getInfo();
		}
		if (mLocClient != null) {
			mLocClient.requestLocation();

			LocationData locData = new LocationData();

			if (((MyApplication) getApplication()).loc != null) {
				// 手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要
				// 使用百度经纬度坐标（bd09ll）
				locData.latitude = Double
						.parseDouble(((MyApplication) getApplication()).loc.LAT);
				locData.longitude = Double
						.parseDouble(((MyApplication) getApplication()).loc.LON);
				locData.accuracy = Float
						.parseFloat(((MyApplication) getApplication()).loc.RADIUS);
			} else if (!((MyApplication) getApplication()).Getlocaldata().lastlat
					.equalsIgnoreCase("")) {
				locData.latitude = Double
						.parseDouble(((MyApplication) getApplication())
								.Getlocaldata().lastlat);
				locData.longitude = Double
						.parseDouble(((MyApplication) getApplication())
								.Getlocaldata().lastlat);
			} else {
				locData.latitude = 39.915 * 1E6;
				locData.longitude = 116.404 * 1E6;
			}

			myLocationOverlay.setData(locData);
			mMapView.getOverlays().add(myLocationOverlay);
			mMapView.refresh();
			mMapView.getController().animateTo(
					new GeoPoint((int) (locData.latitude * 1e6),
							(int) (locData.longitude * 1e6)));

		}

	}

	@Override
	protected void onResume() {
		mMapView.getOverlays().clear();
		mMapView.onResume();
		mMapView.refresh();
		init();
		MyApplication.WriteLog("客户编辑界面 onResume");
		super.onResume();

	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	public void onSave(View v) {
		saveCustom();
	}

	void saveCustom() {
		if (et_custom_code.getText().toString().equalsIgnoreCase("")) {
			if (custom_area.equalsIgnoreCase("")) {
				show();
				return;
			}
		}
		if (et_custom_name.getText().toString().equalsIgnoreCase("")) {
			show();
			return;
		}
		if (currentlat == null || currentlat.equalsIgnoreCase("")) {
			show();
			Toast toast = Toast.makeText(Custom_Add_Edit_Activity.this,
					"请在地图标注位置！", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		pbar.show();
		new Thread() {
			public void run() {
				Looper.prepare();
				try {
					String personno = ((MyApplication) getApplication())
							.Getlocaldata().personno;
					Map<String, String> map = new HashMap<String, String>();
					map.put("app",
							((MyApplication) getApplication()).Getlocaldata().app);
					map.put("customCode", et_custom_code.getText().toString());
					map.put("customName", et_custom_name.getText().toString());
					map.put("address", et_address.getText().toString());
					map.put("person", et_person.getText().toString());
					map.put("phone", et_phone.getText().toString());
					map.put("personno", personno);
					map.put("time", Constants.GetTime());
					map.put("app",
							((MyApplication) getApplication()).Getlocaldata().app);
					map.put("price_raid_code", price_raid_code
							.equalsIgnoreCase("") ? "" : price_raid_code);
					map.put("custom_levle",
							custom_level.equalsIgnoreCase("") ? ""
									: custom_level);
					map.put("area", custom_area);
					map.put("lat", currentlat);
					map.put("lon", currentlon);
					result = SyncHelper.onSaveCustom(map);
					if (result) {
						if (!et_custom_code.getText().toString()
								.equalsIgnoreCase(""))
							CustomDAL.updatecustom(getApplication(), map);
						Toast toast = Toast.makeText(
								Custom_Add_Edit_Activity.this, "成功！",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					} else {
						Toast toast = Toast.makeText(
								Custom_Add_Edit_Activity.this, "失败！",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					message(4);
					pbar.cancel();
					Looper.loop();
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	@Override
	public void onClick(View v) {
		if (v == textView) {
			pbar.show();
			new Thread() {
				public void run() {
					try {
						Looper.prepare();
						dropdownList = SyncHelper
								.GetPRICE_RAID(((MyApplication) getApplication())
										.Getlocaldata().personno);
						message(1);
						pbar.cancel();
						Looper.loop();
					} catch (SQLiteException e) {
						MyApplication.WriteLog(e.getMessage());
					}
				};
			}.start();

		}
		if (v == textView_level) {
			pbar.show();
			new Thread() {
				public void run() {
					try {
						Looper.prepare();
						dropdownList_level = SyncHelper
								.GetCUSTOM_LEVEL(((MyApplication) getApplication())
										.Getlocaldata().personno);
						message(2);
						pbar.cancel();
						Looper.loop();
					} catch (SQLiteException e) {
						MyApplication.WriteLog(e.getMessage());
					}
				};
			}.start();

		}
		if (v == textView_area) {
			pbar.show();
			new Thread() {
				public void run() {
					try {
						Looper.prepare();
						dropdownList_area = SyncHelper
								.GetCUSTOM_AREA(((MyApplication) getApplication())
										.Getlocaldata().personno);
						message(5);
						pbar.cancel();
						Looper.loop();
					} catch (SQLiteException e) {
						MyApplication.WriteLog(e.getMessage());
					}
				};
			}.start();
		}
	}

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	/**
	 * @Title:
	 * @Description: 获取客户等级和价格级别等数据
	 * @author:HYJ
	 * @date:2013.4.28
	 * @param:
	 * @return:
	 */
	void getInfo() {
		new Thread() {
			public void run() {
				try {
					Looper.prepare();
					InfoList = SyncHelper.GetInfo_by_custom_code(et_custom_code
							.getText().toString());
					message(3);
					Looper.loop();
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	@Override
	protected void onDestroy() {
		if (mMapView != null)
			mMapView.destroy();
		MyApplication.WriteLog("客户编辑界面 onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mMapView != null)
			mMapView.onPause();
		MyApplication.WriteLog("客户编辑界面 onPause");
		super.onPause();
	}

	class OverlayTest extends ItemizedOverlay<OverlayItem> {
		// 用MapView构造ItemizedOverlay
		public OverlayTest(Drawable marker, MapView mapView) {
			super(marker, mapView);
		}

		protected boolean onTap(int index) {
			// 在此处理item点击事件
			System.out.println("item onTap: " + index);
			return true;
		}

		public boolean onTap(GeoPoint pt, MapView mapView) {
			// 在此处理MapView的点击事件，当返回 true时
			super.onTap(pt, mapView);
			return false;
		}
		// 自2.1.1 开始，使用 add/remove 管理overlay , 无需重写以下接口
		/*
		 * @Override protected OverlayItem createItem(int i) { return
		 * mGeoList.get(i); }
		 * 
		 * @Override public int size() { return mGeoList.size(); }
		 */
	}

	/**
	 * 内部类实现MKSearchListener接口,用于实现异步搜索服务
	 * 
	 * @author
	 */
	public class MySearchListener implements MKSearchListener {
		/**
		 * 根据经纬度搜索地址信息结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		public void onGetAddrResult(MKAddrInfo result, int iError) {
			if (result == null) {
				return;
			}
			// 将地址信息显示在TextView上
			et_address.setText(result.strAddr);
		}

		/**
		 * 驾车路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
		}

		/**
		 * POI搜索结果（范围检索、城市POI检索、周边检索）
		 * 
		 * @param result
		 *            搜索结果
		 * @param type
		 *            返回结果类型（11,12,21:poi列表 7:城市列表）
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
		}

		/**
		 * 公交换乘路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
		}

		/**
		 * 步行路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}
	}

	// 设置标题栏右侧按钮的作用
	public void btnmainright(View v) {
		show();
	}

	void show() {
		try {
			flag = !flag;
			if (flag) {
				status.setText("收起∨∨");
				linearLayout1.setVisibility(0);
				Animation am = new TranslateAnimation(0, 0, 240, 0);
				// 动画开始到结束的执行时间(1000 = 1 秒)
				am.setDuration(200);
				linearLayout1.setAnimation(am);
				am.startNow();
			} else {
				status.setText("客户信息∧∧");
				linearLayout1.setVisibility(0);
				Animation am = new TranslateAnimation(0, 0, 0, 240);
				// 动画开始到结束的执行时间(1000 = 1 秒)
				am.setDuration(200);
				linearLayout1.setAnimation(am);
				am.startNow();
				linearLayout1.setVisibility(8);
			}

		} catch (Exception e) {
		}

	}
}
