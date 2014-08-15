package com.orwlw.activity;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.model.ParamsInfo;
import com.orwlw.view.Rotate3dAnimation;

/**
 * @author HYJ 2013-1-25 客户列表
 */
public class CustomViewActivity extends ActivityGroup implements TextWatcher,
		OnClickListener {

	RelativeLayout relativeLayout1;
	boolean flag = false;
	private Context mContext;
	Button typebtn;
	EditText etSearch;
	int listshow = 2;
	ImageButton mapButton;
	boolean bb = false;
	boolean ifshowmap = true;
	public ImageView helpImageView;
	public FrameLayout container = null;
	static Handler send_handler = null;
	static int CUSTOM_TYPE = Constants.CUSTOM_TYPE_ALL;
	static String CUSTOM_WHERE = "";

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if (container != null) {
					container.removeAllViews();
					container.addView(getLocalActivityManager().startActivity(
							"list",
							new Intent(CustomViewActivity.this,
									Custom_Activity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
							.getDecorView());
				}
				break;
			case 2:
				if (container != null) {
					container.removeAllViews();
					container.addView(getLocalActivityManager().startActivity(
							"map",
							new Intent(CustomViewActivity.this,
									Map_Activity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
							.getDecorView());
				}
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		try {
			super.onCreate(savedInstanceState);
			if (((MyApplication) getApplication()).mBMapMan == null) {
				((MyApplication) getApplication()).mBMapMan = new BMapManager(
						this);
				((MyApplication) getApplication()).mBMapMan.init(
						Constants.MAP_KEY, null);
			}
			setContentView(R.layout.list_custom_view);
			// android.os.Debug.startMethodTracing("123");// 先建/data/tmp目录
			mContext = this;
			typebtn = (Button) findViewById(R.id.right_btn);

			((MyApplication) getApplication()).handler = handler1;

			etSearch = (EditText) findViewById(R.id.et_searchCus);

			mapButton = (ImageButton) findViewById(R.id.topBtngo_order);
			mapButton.setOnClickListener(this);

			etSearch.addTextChangedListener(this);
			relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);

			helpImageView = (ImageView) findViewById(R.id.mycouponhelpTip);
			helpImageView.setOnClickListener(this);
			SharedPreferences sharedata = getSharedPreferences("fristrun_main",
					0);
			Boolean isSoupon = sharedata.getBoolean("isSoupon", true);
			if (isSoupon) {
				helpImageView.setVisibility(View.VISIBLE);
			} else {
				helpImageView.setVisibility(View.GONE);
			}
			container = (FrameLayout) findViewById(R.id.frameLayout1);
			container.removeAllViews();
			container.addView(getLocalActivityManager().startActivity("map",
					new Intent(CustomViewActivity.this, Map_Activity.class))
					.getDecorView());
			MyApplication.WriteLog("customview oncreate");
		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					e.getMessage() + "", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}

	}

	// 设置标题栏右侧按钮的作用
	public void btnmainright(View v) {
		try {
			flag = !flag;
			if (flag) {
				relativeLayout1.setVisibility(0);
				Animation am = new TranslateAnimation(0, 0, -240, 0);
				// 动画开始到结束的执行时间(1000 = 1 秒)
				am.setDuration(200);
				relativeLayout1.setAnimation(am);
				am.startNow();
			} else {
				relativeLayout1.setVisibility(0);
				Animation am = new TranslateAnimation(0, 0, 0, -240);
				// 动画开始到结束的执行时间(1000 = 1 秒)
				am.setDuration(200);
				relativeLayout1.setAnimation(am);
				am.startNow();
				relativeLayout1.setVisibility(8);
			}

		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					e.getMessage() + "", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}

	}

	public void Add_custom(View view) {
		startActivity(new Intent(CustomViewActivity.this,
				Custom_Add_Edit_Activity.class));
	}

	/**
	 * @Description:TODO 点击“计划门店”事件
	 */
	public void refresh_plan(View view) {
		typebtn.performClick();
		listshow = 1;
		ParamsInfo pi = new ParamsInfo();
		pi.CUSTOM_TYPE = Constants.CUSTOM_TYPE_PLAN;
		CUSTOM_TYPE = Constants.CUSTOM_TYPE_PLAN;
		if (send_handler != null) {
			Message m = send_handler.obtainMessage();
			m.obj = pi;
			m.what = 3;
			send_handler.sendMessage(m);
		}
	}

	/**
	 * @Description:TODO 点击“所有门店”事件
	 */
	public void refresh_all(View view) {
		typebtn.performClick();
		listshow = 2;
		ParamsInfo pi = new ParamsInfo();
		pi.CUSTOM_TYPE = Constants.CUSTOM_TYPE_ALL;
		CUSTOM_TYPE = Constants.CUSTOM_TYPE_ALL;
		if (send_handler != null) {
			Message m = send_handler.obtainMessage();
			m.obj = pi;
			m.what = 3;
			send_handler.sendMessage(m);
		}
	}

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		MyApplication.WriteLog("customvie onResume");
		// if (container != null) {
		// container.removeAllViews();
		// container.addView(getLocalActivityManager().startActivity("map",
		// new Intent(CustomViewActivity.this, Map_Activity.class))
		// .getDecorView());
		// }
		// android.os.Debug.stopMethodTracing();
		super.onResume();
	}

	// activity暂停
	protected void onPause() {
		super.onPause();
		// 隐藏软键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String strValue = etSearch.getText().toString();
		CUSTOM_WHERE = strValue;
		ParamsInfo pi = new ParamsInfo();
		pi.CUSTOM_WHERE = strValue;
		if (send_handler != null) {
			Message m = send_handler.obtainMessage();
			m.obj = pi;
			m.what = 4;
			send_handler.sendMessage(m);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		if (v == mapButton) {
			// if (!bb) {
			// container.removeAllViews();
			// container.addView(getLocalActivityManager().startActivity(
			// "list",
			// new Intent(CustomViewActivity.this,
			// Custom_Activity.class)).getDecorView());
			// //message(1);
			// } else {
			// container.removeAllViews();
			// container.addView(getLocalActivityManager().startActivity(
			// "map",
			// new Intent(CustomViewActivity.this,
			// Map_Activity.class)).getDecorView());
			// //message(2);
			// }
			// bb = !bb;

			if (isFirst) {
				turn(1);

			} else {
				turn(3);
			}
		}
		if (v == helpImageView) {
			helpImageView.setVisibility(View.GONE);
			Editor sharedata = getSharedPreferences("fristrun_main", 0).edit();
			sharedata.putBoolean("isSoupon", false);
			sharedata.commit();
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
		final float centerX = container.getWidth() / 2.0f;
		final float centerY = container.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, depth, reserse);
		rotation.setDuration(400);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(listener);
		container.startAnimation(rotation);
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
			if (bb) {
				message(2);
			} else {
				message(1);
			}
			bb = !bb;
			break;
		}
	}

}
