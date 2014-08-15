package com.orwlw.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.orwlw.activity.R;
import com.orwlw.comm.Constants;
import com.orwlw.comm.DatabaseHelper;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.PhotoAdapter;
import com.orwlw.comm.SyncHelper;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * @author HYJ 2013-1-25 login
 */
public class Login extends Activity implements OnClickListener {
	private EditText comPanycode;// 企业编码1
	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	Button downbtn;
	Button installbtn;
	boolean flag = false;
	boolean enable = false;
	public ProgressBar pBar; // 进度条对话框 　　

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				downbtn.setText("下载中");
				flag = false;
				break;
			case 1:
				// pBar.setVisibility(4);
				downbtn.setVisibility(4);
				installbtn.setVisibility(0);
				break;
			}
		};
	};

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		comPanycode = (EditText) findViewById(R.id.login_company_edit);
		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		downbtn = (Button) findViewById(R.id.btn_down);
		downbtn.setOnClickListener(this);
		installbtn = (Button) findViewById(R.id.btn_install);
		installbtn.setOnClickListener(this);
		pBar = (ProgressBar) findViewById(R.id.progressBar1);

		((MyApplication) getApplication()).Savelocaldata("app", comPanycode
				.getText().toString());
		if (!((MyApplication) getApplication()).Getlocaldata().personno
				.equalsIgnoreCase("")) {
			mUser.setText(((MyApplication) getApplication()).Getlocaldata().personno);
		}

		// ActivityManager activityManager = (ActivityManager) this
		// .getSystemService(Context.ACTIVITY_SERVICE);
		// int cString = activityManager.getMemoryClass();
		// Toast.makeText(getApplicationContext(), "" + cString,
		// Toast.LENGTH_SHORT).show();

		// String str = "10.2368686986859686";
		// Pattern p = Pattern.compile("[\\d]*[\\.][\\d]{2}"); // 小数保留两位小数
		// Matcher m = p.matcher(str);
		//
		// // 查找相应的字符串
		// while (m.find()) {
		// String tmp = m.group();
		// Log.v("tmp", tmp);
		// }
		//
		// Matcher localMatcher =
		// Pattern.compile("\\s+from\\s+(.+)\\s+(where)|$",
		// 2).matcher("select * from table1 where 1=1");
		// if (localMatcher.find()) {
		// String a = localMatcher.group(1);
		// Log.v("a", a);
		// }

		// DatabaseHelper db= new DatabaseHelper(this);
		// db

	}

	@Override
	protected void onResume() {
		if (!Constants.isApkInstalled(this, Constants.ASC)) {
			if (Constants.ifExists("/mnt/sdcard/" + Constants.DOWNNAME)) {
				File file = new File("/mnt/sdcard/" + Constants.DOWNNAME);
				if (file.length() >= 226637) {
					downbtn.setVisibility(4);
					installbtn.setVisibility(0);
					installbtn.setText("点击进行安装辅助软件");
				}
			} else {
				flag = true;
				installbtn.setVisibility(4);
				downbtn.setVisibility(0);
				downbtn.setText("点击下载辅助软件进行激活登录");
			}
		} else {
			downbtn.setVisibility(4);
			installbtn.setVisibility(4);
			enable = true;
		}
		super.onResume();
	}

	public void login_main(View v) {
		if (enable) {
			String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
					.getDeviceId();
			Intent intent = new Intent();
			intent.setClass(Login.this, LoadingActivity.class);
			intent.putExtra("imei", imei);
			intent.putExtra("personno", mUser.getText().toString());
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "请安装辅助软件后登录",
					Toast.LENGTH_LONG).show();
		}

		// new
		// AlertDialog.Builder(Login.this).setIcon(getResources().getDrawable(R.drawable.login_error_icon)).setTitle("登录失败").setMessage("输入信息不正确，\n请检查后重新输入！").create().show();

	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	public void about_click(View v) {
		Intent intent = new Intent(Login.this, AboutSoftActivity.class);
		startActivity(intent);
	}

	void downFile(final String url) {
		pBar.setVisibility(0);
		message(0);
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						long length = entity.getContentLength();
						InputStream is = entity.getContent();// 通过HttpEntiy.getContent得到一个输入流
						pBar.setMax((int) length);
						FileOutputStream fileOutputStream = null;
						if (is != null) {

							File file = new File(Environment
									.getExternalStorageDirectory()
									.getAbsolutePath(), Constants.DOWNNAME);

							if (file.exists()) {
								if (file.delete()) {
									file.createNewFile();
								}

							} else {
								file.createNewFile();
							}
							fileOutputStream = new FileOutputStream(file);

							byte[] buf = new byte[10240];
							int ch = -1;
							int count = 0;
							while ((ch = is.read(buf)) != -1) {
								fileOutputStream.write(buf, 0, ch);
								count += ch;
								pBar.setProgress(count);
								if (length > 0) {
								}
							}

						}
						fileOutputStream.flush();
						if (fileOutputStream != null) {
							fileOutputStream.close();
						}
						message(1);
					} else {

					}

				} catch (ClientProtocolException e) {

					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Looper.loop();

			}

		}.start();

	}

	void installingAPK() {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory().getAbsolutePath(),
				Constants.DOWNNAME)), "application/vnd.android.package-archive");
		startActivity(intent);

	}

	@Override
	public void onClick(View v) {
		if (v == downbtn) {
			if (flag) {
				if (android.os.Environment.MEDIA_MOUNTED
						.equals(android.os.Environment
								.getExternalStorageState())) {
					downFile(Constants
							.getUpgradeURL(((MyApplication) getApplication())
									.Getlocaldata().app, Constants.DOWNNAME));
				} else {
					Toast toast = Toast.makeText(Login.this,
							"无有效的SD卡,请插入SD卡后下载", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

			}

		}
		if (v == installbtn) {
			installingAPK();
		}

	}

}
