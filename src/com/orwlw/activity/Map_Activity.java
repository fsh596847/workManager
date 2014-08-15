package com.orwlw.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.dal.CustomDAL;
import com.orwlw.model.LocationModel;
import com.orwlw.model.ParamsInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Map_Activity extends Activity implements OnClickListener {
	private Context mContext;
	static MapView mMapView = null;
	OverlayTest tempitemOverlay;
	private LocationClient mLocClient;
	ImageView imageView_mlocation;
	MyLocationOverlay myLocationOverlay;
	LocationData locData = null;
	Drawable mark;
	public List<Map<String, Object>> mapcatchelist = null;
	private HashMap<String, Object> map = new HashMap<String, Object>();
	ArrayList<View> views = new ArrayList<View>();

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// ˢ�¿͑�list���¼��d�؈D
				initilizeCustomMap(mapcatchelist);
				break;
			case 2:
				// �ҵ�λ��
				if (locData != null) {
					locData.latitude = Double
							.parseDouble(((MyApplication) getApplication()).loc.LAT);
					locData.longitude = Double
							.parseDouble(((MyApplication) getApplication()).loc.LON);
					locData.accuracy = Float
							.parseFloat(((MyApplication) getApplication()).loc.RADIUS);
					if (myLocationOverlay != null) {
						myLocationOverlay.setData(locData);
						mMapView.refresh();
						mMapView.getController().animateTo(
								new GeoPoint((int) (locData.latitude * 1e6),
										(int) (locData.longitude * 1e6)));
					}
				}
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
		setContentView(R.layout.map_view);
		initlizeview();
		MyApplication.WriteLog("��ͼ��������oncreate");
	}

	@Override
	protected void onResume() {		
		MyApplication.WriteLog("��ͼ��������onResume");
		if (mMapView != null) {
			mMapView.setVisibility(View.VISIBLE);
			mMapView.onResume();
			mMapView.refresh();
			initilizeBaiDuMap();
		}

		if (!CustomViewActivity.CUSTOM_WHERE.equalsIgnoreCase("")) {
			getSearchCustoms(CustomViewActivity.CUSTOM_WHERE);
		} else {
			switch (CustomViewActivity.CUSTOM_TYPE) {
			case Constants.CUSTOM_TYPE_ALL:
				refresh_local_customs();
				break;
			case Constants.CUSTOM_TYPE_PLAN:
				refresh_local_customs_plan();
				break;
			}
		}
		mylocation();
		CustomViewActivity.send_handler = handler1;
		super.onResume();
	}

	protected void onRestoreInstanceState(Bundle paramBundle) {
		super.onRestoreInstanceState(paramBundle);
		this.mMapView.onRestoreInstanceState(paramBundle);
	}

	protected void onSaveInstanceState(Bundle paramBundle) {
		super.onSaveInstanceState(paramBundle);
		this.mMapView.onSaveInstanceState(paramBundle);
	}

	void initlizeview() {
		mContext = this;
		mMapView = (MapView) findViewById(R.id.bmapsView);
		imageView_mlocation = (ImageView) findViewById(R.id.imageView_mlocation);
		imageView_mlocation.setOnClickListener(this);
		mLocClient = ((MyApplication) getApplication()).mLocationClient;
		((MyApplication) getApplication()).handler = handler1;
		CustomViewActivity.send_handler = handler1;
		// initilizeBaiDuMap();
	}

	@Override
	public void onClick(View v) {
		if (v == imageView_mlocation) {
			mylocation();
		}
	}

	// ��ͼģʽ������ʼ���ٶȵ�ͼ
	void initilizeBaiDuMap() {
		mark = getResources().getDrawable(R.drawable.location);
		tempitemOverlay = new OverlayTest(mark, mMapView);
		mMapView.getOverlays().add(tempitemOverlay);
		// �����������õ����ſؼ�
		MapController mMapController = mMapView.getController();
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
				(int) (116.404 * 1E6));
		if (((MyApplication) getApplication()).loc != null) {
			if (!((MyApplication) getApplication()).loc.LAT
					.equalsIgnoreCase("0")) {
				point = new GeoPoint(
						(int) (Double
								.parseDouble(((MyApplication) getApplication()).loc.LAT) * 1E6),
						(int) (Double
								.parseDouble(((MyApplication) getApplication()).loc.LON) * 1E6));
			}
		} else if (!((MyApplication) getApplication()).Getlocaldata().lastlat
				.equalsIgnoreCase("")) {
			point = new GeoPoint(
					(int) (Double.parseDouble(((MyApplication) getApplication())
							.Getlocaldata().lastlat) * 1E6),
					(int) (Double
							.parseDouble(((MyApplication) getApplication())
									.Getlocaldata().lastlon) * 1E6));
		}
		mMapController.setCenter(point);// ���õ�ͼ���ĵ�
		mMapController.setZoom(17);// ���õ�ͼzoom����

		myLocationOverlay = new MyLocationOverlay(mMapView);
		locData = new LocationData();

		myLocationOverlay.setData(locData);
		if (!mMapView.getOverlays().contains(myLocationOverlay))
			mMapView.getOverlays().add(myLocationOverlay);
		mMapView.refresh();
	}

	// ��ͼģʽ�������ƿͻ������ٶȵ�ͼ
	void initilizeCustomMap(List<Map<String, Object>> list) {

		// ����ŵ�����views����
		for (View view : views) {
			mMapView.removeView(view);
		}
		views.clear();
		if (!mMapView.getOverlays().contains(tempitemOverlay)) {
			mMapView.getOverlays().add(tempitemOverlay);
		}

		// ����ŵ��עtempitemOverlay����
		if (tempitemOverlay != null) {
			tempitemOverlay.removeAll();
			// mMapView.getOverlays().clear();
		}
		mapcatchelist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			if (!map.get("F_LAT").toString().equalsIgnoreCase("")) {
				GeoPoint pt = new GeoPoint(
						(int) (Double.parseDouble(map.get("F_LAT").toString()) * 1E6),
						(int) (Double.parseDouble(map.get("F_LON").toString()) * 1E6));
				OverlayItem item1 = new OverlayItem(pt, "item1", "item1");
				tempitemOverlay.addItem(item1);
				mapcatchelist.add(map);// ��ͼ�ͻ�����list
				TextView textView = new TextView(this);
				textView.setTextColor(android.graphics.Color.BLUE);
				textView.setText(map.get("F_CUSTOM_NAME").toString());
				MapView.LayoutParams layoutParam = new MapView.LayoutParams(
				// �ؼ���,�̳���ViewGroup.LayoutParams
						MapView.LayoutParams.WRAP_CONTENT,
						// �ؼ���,�̳���ViewGroup.LayoutParams
						MapView.LayoutParams.WRAP_CONTENT,
						// ʹ�ؼ��̶���ĳ������λ��
						pt,
						// �ؼ����뷽ʽ
						MapView.LayoutParams.TOP);
				// ���View��MapView��
				mMapView.addView(textView, layoutParam);
				views.add(textView);
			}

		}

		mMapView.refresh();
	}

	void mylocation() {
		if (mLocClient != null) {
			mLocClient.requestLocation();
		}
	}

	class OverlayTest extends ItemizedOverlay<OverlayItem> {
		// ��MapView����ItemizedOverlay
		public OverlayTest(Drawable marker, MapView mapView) {
			super(marker, mapView);
		}

		protected boolean onTap(int index) {
			String strCode = mapcatchelist.get(index).get("F_CUSTOM_CODE")
					.toString();
			((MyApplication) getApplication()).Savelocaldata(
					"current_custom_code", strCode);

			MainActivity.setcurrent(1, map);

			return true;
		}

		public boolean onTap(GeoPoint pt, MapView mapView) {
			// �ڴ˴���MapView�ĵ���¼��������� trueʱ
			super.onTap(pt, mapView);
			return false;
		}
	}

	/**
	 * @Description:TODO ��ȡ���ݵ�ǰλ�û�ȡ����Ŀͻ���Ϣ
	 */
	void getCustomList_near(LocationModel loc1) {
		final LocationModel loc = loc1;
		new Thread() {
			public void run() {
				Looper.prepare();
				mapcatchelist = CustomDAL.getCustomList_near(getApplication(),
						loc);
				message(1);
				Looper.loop();
			};
		}.start();
	}

	/**
	 * @Description:TODO ��ȡ����Ӌ���ͻ���Ϣ
	 */
	void refresh_local_customs_plan() {
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					mapcatchelist = CustomDAL
							.getCustomList_plan(getApplication());
					message(1);

					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	/**
	 * @Description:TODO ��ȡ���ص����пͻ���Ϣ
	 */
	void refresh_local_customs() {
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					mapcatchelist = CustomDAL.getCustomList(getApplication());
					message(1);
					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	/**
	 * @Description:TODO ��ȡ����ɸѡ��Ŀͻ�
	 */
	void getSearchCustoms(String strValue) {
		mapcatchelist = CustomDAL.searchCusList(mContext, strValue);
		message(1);
	}

	// activity��ͣ
	protected void onPause() {
		if (tempitemOverlay != null) {
			tempitemOverlay.removeAll();
		}		
		mMapView.getOverlays().remove(tempitemOverlay);
		mMapView.setVisibility(View.INVISIBLE);
		mMapView.onPause();

		super.onPause();

	}

	@Override
	protected void onStop() {
		super.onStop();
		MyApplication.WriteLog("��ͼ��������onStop");
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		MyApplication.WriteLog("��ͼ��������onDestroy");
		super.onDestroy();
	}
}
