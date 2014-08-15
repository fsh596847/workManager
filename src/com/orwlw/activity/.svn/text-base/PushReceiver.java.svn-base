package com.orwlw.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidpn.client.Constants;

import com.orwlw.comm.DBHelper;
import com.orwlw.comm.MyApplication;
import com.orwlw.dal.PushDataDAL;

import com.orwlw.model.PushDataModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class PushReceiver extends BroadcastReceiver {
	DBHelper da = null;
	PushDataDAL pushDataDAL = null;
	PushDataModel pushDataInfo = null;

	public void onReceive(Context context, Intent intent) {
		// Log.e("听到广播", "" + intent.getAction());

		// 存储本地
		da = new DBHelper(context);
		pushDataDAL = new PushDataDAL(
				((MyApplication) context.getApplicationContext()));
		pushDataInfo = new PushDataModel();

		pushDataInfo.data_id = intent
				.getStringExtra(Constants.NOTIFICATION_DATA_ID);
		pushDataInfo.send_username = intent
				.getStringExtra(Constants.NOTIFICATION_SEND_USERNAME);// 发送者
		pushDataInfo.send_name = intent
				.getStringExtra(Constants.NOTIFICATION_SEND_NAME);// 标题
		pushDataInfo.message = java.net.URLEncoder.encode(intent
				.getStringExtra(Constants.NOTIFICATION_MESSAGE));// 内容
		pushDataInfo.uri = intent.getStringExtra(Constants.NOTIFICATION_URI);
		pushDataInfo.status = "0";
		pushDataInfo.created_date = intent
				.getStringExtra(Constants.NOTIFICATION_TIME);// 时间
		MyApplication.WriteLog(pushDataInfo.message);
		if (da.isTableExist("tb_push_data")) {
			pushDataDAL.Insert(pushDataInfo);
		} else {
			da.ExecSql("CREATE TABLE \"tb_push_data\"(\"created_date\" VARCHAR,\"data_id\" VARCHAR,\"message\" text,\"send_name\" VARCHAR,\"send_username\" VARCHAR,\"status\" VARCHAR,\"uri\" VARCHAR)");
			pushDataDAL.Insert(pushDataInfo);
		}

		// 通知栏提示
		Notifier notifier = new Notifier(context);
		notifier.notify(pushDataInfo.data_id, pushDataInfo.send_username,
				pushDataInfo.send_name, pushDataInfo.message,
				pushDataInfo.created_date);
	}

}
