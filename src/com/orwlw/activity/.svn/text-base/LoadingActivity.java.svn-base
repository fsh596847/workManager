package com.orwlw.activity;

import com.orwlw.activity.R;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;

/**
 * @author HYJ 2013-1-25 loading view
 */
public class LoadingActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					Intent intent = getIntent();
					if (SyncHelper.Login(intent.getExtras().getString("imei"),
							intent.getExtras().getString("personno"), "")) {

						((MyApplication) getApplication()).Savelocaldata(
								"personno",
								intent.getExtras().getString("personno"));

						Intent intent1 = new Intent(LoadingActivity.this,
								MainActivity.class);
						startActivity(intent1);
						LoadingActivity.this.finish();

					} else {
						message(2);
					}
					Looper.loop();
				} catch (Exception e) {
					message(1);
				}

			}
		}.start();
	}

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				((MyApplication) getApplication()).ShowLog("µÇÂ¼Òì³£Çë¼ì²éÍøÂç");
				finish();
				break;
			case 2:
				((MyApplication) getApplication()).ShowLog("µÇÂ¼Ê§°Ü£¡");
				finish();
				break;

			}
		};
	};

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}
}