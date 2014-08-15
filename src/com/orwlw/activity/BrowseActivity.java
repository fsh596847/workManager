package com.orwlw.activity;

import java.io.File;

import org.androidpn.client.Constants;

import com.orwlw.dal.PushDataDAL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.CacheManager;
import android.webkit.WebView;

/**
 * @author HYJ 2013-1-30 webview activity
 */
public class BrowseActivity extends Activity {
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.browse_view);

		Intent intent = getIntent();
		String notificationMessage = intent
				.getStringExtra(Constants.NOTIFICATION_MESSAGE);
		String data_id = intent.getStringExtra(Constants.NOTIFICATION_DATA_ID);

		PushDataDAL pushdal = new PushDataDAL(getApplication());
		pushdal.Update(data_id);

		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setDefaultTextEncodingName("UTF -8");// 设置默认为utf-8
		webView.loadData(notificationMessage, "text/html; charset=UTF-8", null);// 这种写法可以正确解码

		// webView = (WebView) findViewById(R.id.webView1);
		// webView.loadUrl("http://www.baidu.com");
	}

	public void login_back(View v) {
		File file = CacheManager.getCacheFileBaseDir();
		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				item.delete();
			}
			file.delete();
		}
		getApplicationContext().deleteDatabase("webview.db");
		getApplicationContext().deleteDatabase("webviewCache.db");
		this.finish();
	}
}
