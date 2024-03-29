package com.orwlw.activity;

import java.util.HashMap;

import com.orwlw.comm.MyApplication;
import com.orwlw.dal.PushDataDAL;
import com.orwlw.view.MyListView;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * @author HYJ 2013-1-25 main view
 */
public class MainActivity extends TabActivity {
	/** Called when the activity is first created. */
	private static TabHost tabHost;
	private TextView main_tab_new_message;
	static RadioGroup radioGroup;
	static Intent intentCustom, intentVisit, intentPerCen;
	Context context;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		context = this;
		PushDataDAL pushdal = new PushDataDAL(getApplication());
		int cou = pushdal.getCount();
		main_tab_new_message = (TextView) ((MainActivity) context)
				.findViewById(R.id.main_tab_new_message);

		if (cou > 0) {
			main_tab_new_message.setVisibility(View.VISIBLE);
			main_tab_new_message.setText(cou + "");
		} else {
			main_tab_new_message.setVisibility(8);
		}

		intentCustom = new Intent().setClass(this, CustomViewActivity.class);
		intentVisit = new Intent().setClass(this, FunctionViewActivity.class);
		intentPerCen = new Intent().setClass(this, CenterViewActivity.class);

		tabHost = this.getTabHost();
		tabHost.addTab(tabHost.newTabSpec("客户").setIndicator("客户")
				.setContent(intentCustom));
		tabHost.addTab(tabHost.newTabSpec("拜访").setIndicator("拜访")
				.setContent(intentVisit));
		tabHost.addTab(tabHost.newTabSpec("个人中心").setIndicator("个人中心")
				.setContent(intentPerCen));
		tabHost.setCurrentTab(0);

		radioGroup = (RadioGroup) this.findViewById(R.id.main_tab_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_tab_addExam:// 客户列表
					tabHost.setCurrentTabByTag("客户");
					break;
				case R.id.main_tab_myExam:// 拜访工作
					if (((MyApplication) getApplication()).Getlocaldata().current_custom_code
							.equalsIgnoreCase("")) {
						Toast.makeText(context, "请选择客户后拜访", Toast.LENGTH_SHORT)
								.show();
						MainActivity.setcurrent(0, null);
						break;
					} else {
						tabHost.setCurrentTabByTag("拜访");
						break;
					}

					// case R.id.main_tab_message:// 添加客户
					// tabHost.setCurrentTabByTag("添加客户");
					// break;
				case R.id.main_tab_settings:// 报表中心
					tabHost.setCurrentTabByTag("个人中心");
					break;
				default:
					// tabHost.setCurrentTabByTag("我的考试");
					break;
				}
			}
		});
		Intent intent2 = new Intent(MainActivity.this, Checkserver.class);
		startService(intent2);
		MyApplication.WriteLog("mainActivity onCreate");

	}

	public static void setcurrent(int i, HashMap<String, Object> map) {
		switch (i) {
		case 0:// 客户列表
			tabHost.setCurrentTab(0);
			RadioButton rb = (RadioButton) radioGroup.getChildAt(0);// 我设置第一个按钮是选中的
			rb.setChecked(true);
			break;
		case 1:// 拜访工作
				// setIntentValue(intentVisit, map);
			tabHost.setCurrentTab(1);
			RadioButton rb2 = (RadioButton) radioGroup.getChildAt(1);// 我设置第二个按钮是选中的
			rb2.setChecked(true);
			break;
		case 2:// 个人中心
			tabHost.setCurrentTab(2);
			RadioButton rb3 = (RadioButton) radioGroup.getChildAt(2);// 我设置第三个按钮是选中的
			rb3.setChecked(true);
			break;
		default:
			break;
		}

		// radioGroup.set
	}

	/**
	 * tab之间传值
	 * 
	 * @param intent
	 * @param map
	 */
	@SuppressWarnings("unused")
	private static void setIntentValue(Intent intent,
			HashMap<String, Object> map) {
		if (map != null) {
			intent.putExtra("tv_code", map.get("tv_code").toString());
			intent.putExtra("tv_name", map.get("tv_name").toString());
			intent.putExtra("tv_address", map.get("tv_address").toString());
			intent.putExtra("tv_person", map.get("tv_person").toString());
			intent.putExtra("tv_tel", map.get("tv_tel").toString());
			intent.putExtra("tv_lat", map.get("tv_lat").toString());
			intent.putExtra("tv_lon", map.get("tv_lon").toString());
			intent.putExtra("tv_isvisit", map.get("tv_isvisit").toString());
			intent.putExtra("Distance", map.get("Distance").toString());
		} else {
			intent.putExtra("tv_code", "");
			intent.putExtra("tv_name", "");
			intent.putExtra("tv_address", "");
			intent.putExtra("tv_person", "");
			intent.putExtra("tv_tel", "");
			intent.putExtra("tv_lat", "");
			intent.putExtra("tv_lon", "");
			intent.putExtra("tv_isvisit", "");
			intent.putExtra("Distance", "");
		}
	}
}