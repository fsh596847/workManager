/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.orwlw.activity;

import java.util.Random;

import org.androidpn.client.Constants;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.IQ.Type;

import android.R.string;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * This class is to notify the user of messages with NotificationManager.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class Notifier {

	// private static final String LOGTAG = LogUtil.makeLogTag(Notifier.class);

	private static final Random random = new Random(System.currentTimeMillis());

	private Context context;

	private SharedPreferences sharedPrefs;

	private NotificationManager notificationManager;

	public Notifier(Context context) {
		this.context = context;
		this.sharedPrefs = context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void notify(String notificationDataId, String sendUsername,
			String title, String message, String time) {

		if (isNotificationEnabled()) {
			// Show the toast
			if (isNotificationToastEnabled()) {
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			}

			// Notification
			Notification notification = new Notification();
			notification.icon = getNotificationIcon();
			notification.defaults = Notification.DEFAULT_LIGHTS;
			if (isNotificationSoundEnabled()) {
				notification.defaults |= Notification.DEFAULT_SOUND;
			}
			if (isNotificationVibrateEnabled()) {
				notification.defaults |= Notification.DEFAULT_VIBRATE;
			}
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.when = System.currentTimeMillis();
			notification.tickerText = "新信息来自：" + sendUsername;

			Intent intent = new Intent(context, BrowseActivity.class);
			intent.putExtra(Constants.NOTIFICATION_ID, notificationDataId);
			intent.putExtra(Constants.NOTIFICATION_TITLE, title);
			intent.putExtra(Constants.NOTIFICATION_MESSAGE, message);
			intent.putExtra(Constants.NOTIFICATION_TIME, time);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(context, sendUsername, "    " + title,
					contentIntent);
			notificationManager.notify(random.nextInt(), notification);

		} else {
			// Log.w(LOGTAG, "Notificaitons disabled.");
		}
	}

	private int getNotificationIcon() {
		return R.drawable.notification;
	}

	private boolean isNotificationEnabled() {
		return sharedPrefs.getBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED,
				true);
	}

	private boolean isNotificationSoundEnabled() {
		return sharedPrefs.getBoolean(Constants.SETTINGS_SOUND_ENABLED, true);
	}

	private boolean isNotificationVibrateEnabled() {
		return sharedPrefs.getBoolean(Constants.SETTINGS_VIBRATE_ENABLED, true);
	}

	private boolean isNotificationToastEnabled() {
		return sharedPrefs.getBoolean(Constants.SETTINGS_TOAST_ENABLED, false);
	}

}
