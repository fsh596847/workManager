package com.orwlw.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/*
 * 关于憨管佳
 * */
public class AboutSoftActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_softinfo);
	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	public void go_phone(View v) {
		Uri telUri = Uri.parse("tel:4000567505");
		Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
		startActivity(intent);
	}

	public void go_url_click(View v) {
		Uri uri = Uri.parse("http://www.orwlw.com.cn/");
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(it);

	}

}
