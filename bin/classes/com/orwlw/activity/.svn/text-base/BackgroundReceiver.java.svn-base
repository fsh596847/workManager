package com.orwlw.activity;

import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class BackgroundReceiver extends BroadcastReceiver {

	public void onReceive(Context paramContext, Intent paramIntent) {
		MyApplication.WriteLog(Constants.GetTime() + "¼àÌýµ½"
				+ paramIntent.getAction());		
		if (paramIntent.getAction().equalsIgnoreCase(
				"android.intent.action.PACKAGE_ADDED")
				|| paramIntent.getAction().equalsIgnoreCase(
						"android.intent.action.PACKAGE_RESTARTED")
				|| paramIntent.getAction().equalsIgnoreCase(
						"android.intent.action.BOOT_COMPLETED")) {			
			paramContext.startService(new Intent(Constants.CORE_SERVICE));			
		}
		Intent alrmintent = new Intent(paramContext, AlarmReceiver.class);
		// alrmintent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		alrmintent.setAction("start_service");
		long l = SystemClock.elapsedRealtime();
		((AlarmManager) paramContext.getSystemService("alarm")).setRepeating(2,
				l, 60000 * 5,
				PendingIntent.getBroadcast(paramContext, 0, alrmintent, 0));

	}
}
