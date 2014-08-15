package com.orwlw.activity;

import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;

public class InitializeServer extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		new Thread() {
			public void run() {
				Looper.prepare();
				initlizeData();
				stopSelf();
				Looper.loop();
			};
		}.start();

		return Service.START_STICKY;
	}

	void initlizeData() {
		SyncHelper.RelocateTable(getApplicationContext(), "T_ENUM",
				SyncHelper.GetEnmus(((MyApplication) getApplication())
						.Getlocaldata().personno));
	}

}
