package com.orwlw.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.orwlw.comm.MainAdapter;
import com.orwlw.comm.MyApplication;
import com.orwlw.model.ImageModel;
import com.orwlw.model.QuestionsBean;
import com.orwlw.net.ConvertToBean;
import com.orwlw.net.SyncHelper;

public class WenJuanActivity extends Activity {
	private ListView listView;
	private MainAdapter myAdapter;
	private List<QuestionsBean> itemList;
	private Button button_again;// 失败重试
	private Button subButton_img;
	private ProgressBar progressBar;
	private String a;
	HashMap<String, String> propertys = new HashMap<String, String>();
	String methodName = "GetCheckItems";
	ConvertToBean toBean = new ConvertToBean(this);
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.arg1 == 1) {
				System.out.println("提交结果" + a);
				if (a.equals("true")) {
					Toast.makeText(WenJuanActivity.this, "提交文件成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(WenJuanActivity.this, "暂无文件提交",
							Toast.LENGTH_LONG).show();
				}
			} else if (msg.arg1 == 2) {
				// length = itemList.size();
				System.out.println("itemList长度" + itemList.size());
				myAdapter = new MainAdapter(WenJuanActivity.this,
						(List<QuestionsBean>) itemList);
				listView.setAdapter(myAdapter);
				progressBar.setVisibility(View.GONE);
			} else if (msg.arg1 == 3) {
				System.out.println("提交结果" + a);
				if (a.equals("true")) {
					Toast.makeText(WenJuanActivity.this, "提交图片成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(WenJuanActivity.this, "暂无照片提交",
							Toast.LENGTH_LONG).show();
				}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wenjuan_main);
		listView = (ListView) findViewById(R.id.listview);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		listView.setOnItemClickListener(onItemClickListener);
		button_again = (Button) findViewById(R.id.button_again);// 失败重试
		subButton_img = (Button) findViewById(R.id.submit_img);
		subButton_img.setOnClickListener(onClickListener);
		button_again.setOnClickListener(onClickListener);
		propertys.put("personno", "or0001");
		/* 获取suoyou题目 */
		getInformation(methodName, propertys);

	}

	private void getInformation(final String methodName,
			final HashMap<String, String> propertys) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				itemList = toBean.getAllCheckItem(methodName, propertys);
				Message message = handler.obtainMessage();
				message.arg1 = 2;
				message.obj = itemList;
				handler.sendMessage(message);
			}
		}).start();
	}

	private void submit_file() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				a = faile_aga();
				Message message = handler.obtainMessage();
				message.arg1 = 1;// 95 145 151
				handler.sendMessage(message);

			}
		}).start();
	}

	private void submit_img() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				a = SaveCheck_ImageFile();
				Message message = handler.obtainMessage();
				message.arg1 = 3;// 95 145 151
				handler.sendMessage(message);

			}
		}).start();
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(WenJuanActivity.this,
					ItemQuestionActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("list", itemList.get(arg2));
			bundle.putInt("item", arg2);
			// bundle.putInt("length", length);
			intent.putExtra("bdu", bundle);
			startActivityForResult(intent, 5);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_again:
				submit_file();
				break;
			case R.id.submit_img:
				submit_img();
				break;
			default:
				break;
			}
		}

	};

	// 提交文件，成功就删除
	private String faile_aga() {
		try {
			String result = null;//
			String json = null;
			File file = new File("/sdcard/cache/");
			String filenameString[] = file.list();
			System.out.println("文件数量：" + filenameString.length);
			if (filenameString.length > 0) {
				if (file.exists() && file.isDirectory()
						&& file.list().length > 0) {
					for (int i = 0; i < filenameString.length; i++) {
						/* webservice提交方法 */
						// 捕捉异常
						ConvertToBean toBean = new ConvertToBean(
								WenJuanActivity.this);
						result = toBean.readTxtFile("/sdcard/cache/"
								+ filenameString[i]);
						HashMap<String, String> propertys = new HashMap<String, String>();
						// 参数
						propertys.put("custom_code",
								((MyApplication) getApplication())
										.Getlocaldata().current_custom_code);
						propertys.put("personno",
								((MyApplication) getApplication())
										.Getlocaldata().personno);
						propertys.put("result", result);
						SimpleDateFormat myFmt2 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date now = new Date();
						propertys.put("date", myFmt2.format(now));
						// 命名空间，方法名，地址，命名空间+方法名，soapAction = nameSpace +
						// methodName;参数
						json = new SyncHelper().getServerMsg(
								ConvertToBean.nameSpace, "SubmitCheckResult",
								ConvertToBean.serviceURL,
								ConvertToBean.nameSpace + "SubmitCheckResult",
								propertys);
						System.out.println("返回来的结果------" + json);
						// 删除备份文件
						if (json != null) {
							toBean.deleteFile("/sdcard/cache/"
									+ filenameString[i]);
						}

					}
				}
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	// 提交照片
	private String SaveCheck_ImageFile() {
		// 先提交照片
		int count = 0;
		File file_image = new File(
				"/sdcard/myImage/"
						+ ((MyApplication) getApplication()).Getlocaldata().current_custom_code);
		String filenameString[] = file_image.list();
		if (filenameString.length>0) {
			for (int i = 0; i < filenameString.length; i++) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = sdf.format(new java.util.Date());
				ImageModel im = new ImageModel();
				im.CustomCode = ((MyApplication) getApplication())
						.Getlocaldata().current_custom_code;
				im.DateTime = date1;
				im.ImageOpearter = ((MyApplication) getApplication())
						.Getlocaldata().personno;
				im.ImageName = filenameString[i];
				im.Path = "/sdcard/myImage/"
						+ ((MyApplication) getApplication()).Getlocaldata().current_custom_code
						+ "/" + filenameString[i];

				boolean a = toBean.Submit_check_Images(im);
				if (a == true) {
					toBean.deleteFile("/sdcard/myImage/"
							+ ((MyApplication) getApplication()).Getlocaldata().current_custom_code
							+ "/" + filenameString[i]);
					count++;
				}
			}
			if (count == filenameString.length) {
				return "true";
				// Toast.makeText(WenJuanActivity.this, "照片全部提交成功",
				// Toast.LENGTH_SHORT).show();
			}
		} else if(filenameString.length<=0){
			return "暂无照片";
		}
		return null;
	}

}
