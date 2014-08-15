package com.orwlw.activity;

import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("start_service")) {
			try {
				MyApplication.SaveLog(Constants.GetTime() + "¿ªÊ¼¹ã²¥");
				Intent intent1 = new Intent(context, Bkservice.class);
				context.startService(intent1);
			} catch (Exception e) {
			}
		}
	}
}
