package com.orwlw.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.ProductAdapter;
import com.orwlw.comm.SyncHelper;
import com.orwlw.dal.ProductDAL;
import com.orwlw.model.ImageModel;
import com.orwlw.view.MyListView;
import com.orwlw.view.MyListView.OnRefreshListener;
import com.orwlw.view.Rotate3dAnimation;

/**
 * @author HYJ 2013-1-25 商品列表
 */
public class ProductViewActivity extends Fragment implements
		OnItemClickListener, OnRefreshListener, OnItemLongClickListener,
		OnCreateContextMenuListener, TextWatcher, OnClickListener {

	private MyListView myListView;
	LinearLayout linear_product;
	LinearLayout linear_product_data;
	LinearLayout linear_product_pic;
	GridView mGridView;
	RelativeLayout relativeLayout1;
	boolean flag = false;
	boolean showcategory = false;
	boolean bb = false;
	ImageButton shopButton;
	private Context mContext;
	private SimpleAdapter mySimperAdapter;
	public List<Map<String, Object>> list = null;
	Button topBtnshow_window;
	EditText et_search;
	Map<String, Object> itemMap;

	LinearLayout lay_scan;
	LinearLayout lay_cate;
	LinearLayout lay_used;
	LinearLayout lay_all;

	int PARAMS;
	// 标记商品的常用商品列表和所有商品列表，（‘1’代表常用，‘2’代表所有）
	String mark = Constants.ALLCOMM;
	// 记录查询结果
	boolean result = false;

	// 图片
	private HashMap<Integer, Bitmap> dataCache = new HashMap<Integer, Bitmap>();
	private List<ImageModel> resIds = new ArrayList<ImageModel>();

	//
	public ImageView helpImageView;
	public ImageView helpImageView2;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_product_view, null);
		mContext = view.getContext();

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initview();
		initCommList();
	}

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				helpImageView.setVisibility(View.GONE);
				mySimperAdapter = new SimpleAdapter(mContext, list,
						R.layout.list_product_item, new String[] {
								"F_COMM_CODE", "F_COMM_NAME" }, new int[] {
								R.id.list_comm_code, R.id.list_comm_name });
				myListView.setAdapter(mySimperAdapter);
				mGridView.setAdapter(new ProductAdapter(mContext, list));
				if (list.size() > 0) {
					SharedPreferences sharedata = getActivity()
							.getSharedPreferences("fristrun_comm", 0);
					Boolean isSoupon = sharedata.getBoolean("isSoupon", true);
					if (isSoupon) {
						helpImageView2.setVisibility(View.VISIBLE);
					} else {
						helpImageView2.setVisibility(View.GONE);
					}
				}

				break;
			case 2:
				helpImageView.setVisibility(View.VISIBLE);
				break;
			}
		};
	};

	private void initview() {

		try {

			lay_scan = (LinearLayout) getActivity().findViewById(R.id.lay_scan);
			lay_cate = (LinearLayout) getActivity().findViewById(R.id.lay_cate);
			lay_used = (LinearLayout) getActivity().findViewById(R.id.lay_used);
			lay_all = (LinearLayout) getActivity().findViewById(R.id.lay_all);
			lay_scan.setOnClickListener(this);
			lay_cate.setOnClickListener(this);
			lay_used.setOnClickListener(this);
			lay_all.setOnClickListener(this);

			shopButton = (ImageButton) getActivity().findViewById(
					R.id.topBtngo_order);
			View vv = (MyListView) getActivity().findViewById(
					R.id.myproductListView);
			myListView = (MyListView) getActivity().findViewById(
					R.id.myproductListView);
			myListView.setOnItemClickListener(this);
			myListView.setOnRefreshListener(this);
			myListView.setOnItemLongClickListener(this);
			myListView.setOnCreateContextMenuListener(this);

			mGridView = (GridView) getActivity().findViewById(R.id.productGrid);

			linear_product = (LinearLayout) getActivity().findViewById(
					R.id.linear_product);
			linear_product_data = (LinearLayout) getActivity().findViewById(
					R.id.linear_product_data);
			linear_product_pic = (LinearLayout) getActivity().findViewById(
					R.id.linear_product_pic);

			((SlidingActivity) getActivity()).myListView = myListView;
			((SlidingActivity) getActivity()).mGridView = mGridView;
			relativeLayout1 = (RelativeLayout) getActivity().findViewById(
					R.id.relativeLayout1);
			topBtnshow_window = (Button) getActivity().findViewById(
					R.id.topBtnshow_window);
			topBtnshow_window.setOnClickListener(this);
			shopButton.setOnClickListener(this);

			et_search = (EditText) getActivity().findViewById(
					R.id.et_searchComm);

			et_search.addTextChangedListener(this);

			mGridView.setOnItemClickListener(gridonitemClickListener);
			helpImageView = (ImageView) getActivity().findViewById(
					R.id.mycouponhelpTip);
			helpImageView.setOnClickListener(this);
			helpImageView2 = (ImageView) getActivity().findViewById(
					R.id.mycouponhelpTip2);
			helpImageView2.setOnClickListener(this);
		} catch (Exception e) {
			MyApplication.WriteLog("" + e.getMessage());
		}

	}

	/**
	 * @Title: initCommList
	 * @Description:TODO 初始化商品列表
	 * @author WangMin
	 * @date 2013-2-27 下午06:42:53设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initCommList() {
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					list = ProductDAL.getCOMMList(getActivity()
							.getApplication());
					if (list.size() <= 0) {
						message(2);
					}
					message(1);
					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	public void goOrder() {
		Intent intent = new Intent(getActivity(), OrderActivity.class);
		startActivity(intent);
	}

	// 设置标题栏右侧按钮的作用
	public void showwindow() {
		try {
			flag = !flag;
			if (flag) {
				relativeLayout1.setVisibility(0);
				Animation am = new TranslateAnimation(0, 0, -140, 0);
				// 动画开始到结束的执行时间(1000 = 1 秒)
				am.setDuration(200);
				relativeLayout1.setAnimation(am);
				am.startNow();
			} else {
				relativeLayout1.setVisibility(0);
				Animation am = new TranslateAnimation(0, 0, 0, -140);
				// 动画开始到结束的执行时间(1000 = 1 秒)
				am.setDuration(200);
				relativeLayout1.setAnimation(am);
				am.startNow();
				relativeLayout1.setVisibility(8);
			}
		} catch (Exception e) {
			Toast toast = Toast.makeText(getActivity().getApplicationContext(),
					e.getMessage() + "", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView tvCommCode = (TextView) view.findViewById(R.id.list_comm_code);
		TextView tvCommName = (TextView) view.findViewById(R.id.list_comm_name);
		if (((MyApplication) getActivity().getApplication()).Getlocaldata().CURRENT_PARAMS == Constants.SUVERY_ACTION_SELECT_COMMS) {
			Intent intent = new Intent(getActivity(), MainActivity.class);
			intent.putExtra("code", tvCommCode.getText() + "");
			intent.putExtra("name", tvCommName.getText() + "");
			getActivity().setResult(0, intent);
			getActivity().finish();
			return;
			// 返回选择的商品后关闭
		}
		Intent intent = new Intent(getActivity(), ProductEditNumActivity.class);

		((MyApplication) getActivity().getApplication()).Savelocaldata(
				"CURRENT_COMM_CODE", tvCommCode.getText() + "");
		((MyApplication) getActivity().getApplication()).Savelocaldata(
				"CURRENT_COMM_NAME", tvCommName.getText() + "");
		startActivity(intent);
		getActivity().finish();
	}

	@Override
	public void onRefresh() {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				if (((MyApplication) getActivity().getApplication())
						.Getlocaldata().LAST_SYSNC_DATE.equalsIgnoreCase("")) {
					list = new SyncHelper()
							.GetCommList(((MyApplication) getActivity()
									.getApplication()).Getlocaldata().personno);
					new SyncHelper().RelocateTable(mContext, "T_SCM_PRODUCT",
							list);
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String date = format.format(new Date());
					((MyApplication) getActivity().getApplication())
							.Savelocaldata("LAST_SYSNC_DATE", date);
					message(1);
				} else {
					SyncHelper
							.RelocateTable_Data(
									mContext,
									"T_SCM_PRODUCT",
									SyncHelper.GetCommList_byLastdate(
											((MyApplication) getActivity()
													.getApplication())
													.Getlocaldata().personno,
											((MyApplication) getActivity()
													.getApplication())
													.Getlocaldata().LAST_SYSNC_DATE));
					initCommList();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				myListView.onRefreshComplete();
			}
		}.execute();
	}

	/**
	 * @Title: onClickScanning
	 * @Description:TODO 点击“扫描”事件
	 * @author WangMin
	 * @date 2013-2-28 下午02:48:49
	 * @param v设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void onClickScanning() {
		Intent intent = new Intent(getActivity(), CameraTestActivity.class);
		startActivityForResult(intent, 0);
		topBtnshow_window.performClick();
	}

	/**
	 * @Title: onClickItems
	 * @Description:TODO 点击“品项”事件
	 * @author WangMin
	 * @date 2013-2-28 下午02:49:43
	 * @param v设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void onClickItems() {
		((SlidingActivity) getActivity()).showLeft();
		topBtnshow_window.performClick();
	}

	/**
	 * @Title: onClickItems
	 * @Description:TODO 点击“常用商品”事件
	 * @author WangMin
	 * @date 2013-2-28 下午02:49:43
	 * @param v设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void onClickOften() {
		// 标记为常用
		mark = Constants.USED;
		topBtnshow_window.performClick();
		initCommUsedList();
	}

	/**
	 * @Title: onClickItems
	 * @Description:TODO 点击“所有商品”事件
	 * @author WangMin
	 * @date 2013-2-28 下午02:49:43
	 * @param v设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void onClickAll() {
		// mark标记为所有
		mark = Constants.ALLCOMM;
		topBtnshow_window.performClick();
		initCommList();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		String comm_code = ((TextView) view.findViewById(R.id.list_comm_code))
				.getText().toString();
		String comm_name = ((TextView) view.findViewById(R.id.list_comm_name))
				.getText().toString();
		itemMap = new HashMap<String, Object>();
		itemMap.put("comm_code", comm_code);
		itemMap.put("comm_name", comm_name);
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo arg2) {
		String str = "从常用商品移除";
		if (mark.equals(Constants.ALLCOMM))
			str = "标记为常用商品";
		final List<Map<String, Object>> listHash = new ArrayList<Map<String, Object>>();
		menu.setHeaderTitle(itemMap.get("comm_name").toString());
		menu.add(0, 0, 0, str).setOnMenuItemClickListener(
				new MenuItem.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						String comm_code = itemMap.get("comm_code").toString();
						itemMap.clear();
						itemMap.put("F_COMM_CODE", comm_code);
						listHash.add(itemMap);
						Boolean isExist = ProductDAL.isDataExist(getActivity()
								.getApplication(), comm_code);
						if (isExist) {
							if (mark.equals(Constants.USED)) {
								result = ProductDAL.deleteData(getActivity()
										.getApplication(), comm_code);
								if (result) {
									showToast("移除成功！");
									// 重新加载常用商品列表
									initCommUsedList();
								}
							} else if (mark.equals(Constants.ALLCOMM)) {
								showToast("标记成功！");
							}
						} else {
							if (mark.equals(Constants.USED)) {
								showToast("移除成功！");
							} else if (mark.equals(Constants.ALLCOMM)) {
								result = ProductDAL.addData(getActivity()
										.getApplication(), "T_COMM_USED",
										listHash);
								if (result)
									showToast("标记成功！");
								else
									showToast("标记失败！");
							}
						}
						return false;
					}
				});
	}

	/**
	 * @Title: initCommUsedList
	 * @Description:TODO 加载常用商品列表
	 * @author WangMin
	 * @date 2013-3-5 下午05:57:45设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initCommUsedList() {
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					list = ProductDAL.getCommUsedList(getActivity()
							.getApplication());
					message(1);
					Looper.loop();
				} catch (Exception e) {
				}
			}
		}.start();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			// 返回的条形码数据
			String code = data.getStringExtra("Code");
			Map<String, Object> map = new HashMap<String, Object>();
			map = ProductDAL.getCOMM_by_scan(getActivity().getApplication(),
					code);
			if (map != null) {

				((MyApplication) getActivity().getApplication()).Savelocaldata(
						"CURRENT_COMM_CODE", map.get("F_COMM_CODE").toString());
				((MyApplication) getActivity().getApplication()).Savelocaldata(
						"CURRENT_COMM_NAME", map.get("F_COMM_NAME").toString());
				startActivity(new Intent(getActivity(),
						ProductEditNumActivity.class));
			} else {
				Toast.makeText(mContext, "[" + code + "]" + "内容未找到结果",
						Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		// 输入文本输入框中
		// Online_goods_Name.setText(code);

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * @Title: showToast
	 * @Description:TODO 显示TOAST
	 * @author WangMin
	 * @date 2013-3-6 上午11:13:01
	 * @param msg设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void showToast(String msg) {
		Toast.makeText(getActivity().getApplication(), msg, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String strText = et_search.getText().toString();
		list = ProductDAL.searchCommList(getActivity().getApplication(),
				strText);
		message(1);
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void onClick(View v) {
		if (v == topBtnshow_window) {
			showwindow();
		}
		if (v == shopButton) {
			// goOrder();
			if (isFirst) {
				turn(1);

			} else {
				turn(3);
			}

		}
		if (v == lay_scan) {
			onClickScanning();
		}
		if (v == lay_cate) {
			onClickItems();
		}
		if (v == lay_used) {
			onClickOften();
		}
		if (v == lay_all) {
			onClickAll();
		}
		if (v == helpImageView) {
			helpImageView.setVisibility(View.GONE);
		}
		if (v == helpImageView2) {
			Editor localdata = getActivity().getSharedPreferences(
					"fristrun_comm", 0).edit();
			localdata.putBoolean("isSoupon", false);
			localdata.commit();
			helpImageView2.setVisibility(View.GONE);
		}

	}

	private int currentMsg;
	boolean isFirst = false;
	protected static final int ROTATE_START = 1;
	protected static final int ROTATE_START_BACK = 2;
	protected static final int ROTATE_REBACK = 3;
	protected static final int ROTATE_REBACK_BACK = 4;
	private int front, back;
	private AnimationListener listener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (currentMsg == ROTATE_START_BACK
					|| currentMsg == ROTATE_REBACK_BACK) {
				return;
			}
			if (isFirst) {
				turn(ROTATE_START_BACK);
			} else {
				turn(ROTATE_REBACK_BACK);
			}
		}
	};

	private void applyRotation(float start, float end, float depth,
			boolean reserse) {
		// Find the center of the container
		final float centerX = linear_product.getWidth() / 2.0f;
		final float centerY = linear_product.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, depth, reserse);
		rotation.setDuration(400);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(listener);
		linear_product.startAnimation(rotation);
	}

	public void turn(int step) {
		currentMsg = step;
		switch (step) {
		case ROTATE_START:
			applyRotation(0, 90, 310.0f, true);
			break;
		case ROTATE_START_BACK:
			applyRotation(270, 360, 310.0f, false);
			break;
		case ROTATE_REBACK:
			applyRotation(360, 270, 310.0f, true);
			break;
		case ROTATE_REBACK_BACK:
			applyRotation(90, 0, 310.0f, false);
			bb = !bb;
			if (bb) {
				linear_product_data.setVisibility(8);
				linear_product_pic.setVisibility(0);
			} else {
				linear_product_pic.setVisibility(8);
				linear_product_data.setVisibility(0);
			}
			break;
		}
	}

	OnItemClickListener gridonitemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(getActivity(),
					ProductEditNumActivity.class);

			((MyApplication) getActivity().getApplication())
					.Savelocaldata("CURRENT_COMM_CODE",
							list.get(arg2).get("F_COMM_CODE") + "");
			((MyApplication) getActivity().getApplication())
					.Savelocaldata("CURRENT_COMM_NAME",
							list.get(arg2).get("F_COMM_NAME") + "");
			startActivity(intent);
			getActivity().finish();

		}
	};

}
