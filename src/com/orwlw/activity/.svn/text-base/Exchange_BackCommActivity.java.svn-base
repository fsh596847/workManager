package com.orwlw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * 调退货
 * 
 * @author Administrator
 * 
 */
public class Exchange_BackCommActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exchange_back_comm_view);
	}
	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}
	public void gochange(View v) {
		startActivity(new Intent(Exchange_BackCommActivity.this,
				exchange_commsActivity.class));
		finish();
	}

	public void gobackcomm(View v) {
		startActivity(new Intent(Exchange_BackCommActivity.this,
				back_commsActivity.class));
		finish();
	}
}
