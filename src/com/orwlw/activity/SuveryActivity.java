package com.orwlw.activity;

import java.io.File;

import com.orwlw.comm.MyApplication;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.CacheManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SuveryActivity extends Activity {
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		try {
			setContentView(R.layout.browse_view);
			String URL = com.orwlw.comm.Constants.WEB_SUVERY
					+ "&custom="
					+ ((MyApplication) getApplication()).Getlocaldata().current_custom_code
					+ "";
			webView = (WebView) findViewById(R.id.webView1);
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					return false;
				}
			});
			webView.loadUrl(URL);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void login_back(View v) { // 标题栏 返回按钮
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
