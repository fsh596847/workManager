package com.orwlw.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.orwlw.activity.R;
import com.orwlw.comm.Constants;
import com.orwlw.comm.FunctionAdapter;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.PhotoAdapter;
import com.orwlw.comm.StorageList;
import com.orwlw.comm.SyncHelper;
import com.orwlw.dal.CustomDAL;
import com.orwlw.dal.ImageDAL;
import com.orwlw.dal.PhotoDAL;
import com.orwlw.model.FunctionModel;
import com.orwlw.model.ImageModel;
import com.orwlw.model.PhotoModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author HYJ 2013-1-25 形象拍照
 */
public class PhotoActivity extends Activity implements OnItemClickListener {
	GridView MainActivityGrid;
	List<PhotoModel> list = new ArrayList<PhotoModel>();
	Context mcontext;
	ProgressBar pbar;
	String currentCode;
	private LocationClient mLocClient;
	private boolean mIsStart = false;
	TextView txt_newaddress;
	PhotoAdapter typeAdapter;

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				typeAdapter = new PhotoAdapter(mcontext, list);
				MainActivityGrid.setAdapter(typeAdapter);
				pbar.setVisibility(8);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.photo);
		mcontext = this;
		// mLocClient = ((MyApplication) getApplication()).mLocationClient;

		MainActivityGrid = (GridView) findViewById(R.id.MainActivityGrid);
		pbar = (ProgressBar) findViewById(R.id.progressBar1);
		txt_newaddress = (TextView) findViewById(R.id.txt_newaddress);

		((MyApplication) getApplication()).maddress = txt_newaddress;

		registerForContextMenu(MainActivityGrid);

		MainActivityGrid.setOnItemClickListener(this);

		currentCode = ((MyApplication) getApplication()).Getlocaldata().current_custom_code;
		mLocClient = ((MyApplication) getApplication()).mLocationClient;
		if (mLocClient != null)
			mLocClient.requestLocation();
		Getlocaltype();// 刷新本地
		// setLocationOption();
		// mLocClient.start();
		// mLocClient.requestLocation();
		// mIsStart = true;
	}

	@Override
	protected void onStart() {
		MyApplication.WriteLog("onstart");
		super.onStart();
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, 0, Menu.NONE, "删除该照片");
		menu.add(Menu.NONE, 1, Menu.NONE, "修改备注");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			int selectedPosition = ((AdapterContextMenuInfo) item.getMenuInfo()).position;// 获取点击了第几行

			ImageDAL.DeleteImage(getApplication(),
					list.get(selectedPosition).id);
			File picture = new File(list.get(selectedPosition).image);
			picture.delete();
			Getlocaltype();// 刷新本地
			return true;
		case 1:
			final int selectedPosition1 = ((AdapterContextMenuInfo) item
					.getMenuInfo()).position;// 获取点击了第几行
			final EditText inputServer = new EditText(this);
			inputServer.setFocusable(true);
			inputServer.setText(list.get(selectedPosition1).REMARK);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("输入照片备注").setView(inputServer);
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							String inputName = inputServer.getText().toString();
							PhotoDAL.updateRemark(getApplication(), inputName,
									list.get(selectedPosition1).id);
							Getlocaltype();
						}
					});
			builder.show();
			return true;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	public void btnrefresh(View v) { // 刷新按钮
		RefreshType();
	}

	public void submit_image(View v) {// 提交
		UploadPIC();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		((MyApplication) getApplication()).Savelocaldata("currentphototype",
				list.get(arg2).typecode);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}

	void RefreshType() {
		pbar.setVisibility(0);
		new Thread() {
			public void run() {
				try {
					new SyncHelper()
							.RelocateTable(
									mcontext,
									"T_IMAGE_TYPE",
									new SyncHelper()
											.GetPictureType(((MyApplication) getApplication())
													.Getlocaldata().personno));
					List<Map<String, Object>> li = new SyncHelper()
							.GetPictureType(((MyApplication) getApplication())
									.Getlocaldata().personno);
					List<PhotoModel> li1 = new ArrayList<PhotoModel>();
					for (int i = 0; i < li.size(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						map = li.get(i);
						PhotoModel photomodel = new PhotoModel();
						photomodel.typecode = map.get("F_KEY_CODE").toString();
						photomodel.typename = map.get("F_KEY_VALUE").toString();
						li1.add(photomodel);
					}
					Getlocaltype();
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	void Getlocaltype() {
		new Thread() {
			public void run() {
				try {
					List<PhotoModel> li = PhotoDAL.getImageType(
							getApplication(), currentCode);
					list = li;
					message(1);
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	void UploadPIC() {
		pbar.setVisibility(0);
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					List<ImageModel> liimgs = new ArrayList<ImageModel>();
					liimgs = ImageDAL
							.getImgeList(getApplication(), currentCode);

					int i = 0;
					for (ImageModel img : liimgs) {
						if (SyncHelper.SubmitImages(
								((MyApplication) getApplication())
										.Getlocaldata().app, img)) {
							File picture = new File(img.Path);
							picture.delete();
							ImageDAL.DeleteImage(getApplication(), img.Id);
							i++;
						}
					}
					if (i > 0) {
						// 有照片上传成功标记本地客户为拜访状态
						CustomDAL.markvisit_custom(getApplication(),
								((MyApplication) getApplication())
										.Getlocaldata().current_custom_code);
						Toast toast = Toast.makeText(PhotoActivity.this, "成功！",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					} else {
						Toast toast = Toast.makeText(PhotoActivity.this, "失败！",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					Getlocaltype();
					Looper.loop();
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		{
			if (resultCode == Activity.RESULT_OK) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.v("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				String sdPath = "/mnt/sdcard/";
				String sdk = android.os.Build.VERSION.SDK;// andorid
															// 4.0以上调用一下方法获取判断存储器路径
				if (Integer.parseInt(sdk) >= 14) {
					String[] storagePathList = new StorageList(this)
							.getVolumePaths();
					if (storagePathList != null) {
						if (storagePathList.length >= 2) {
							sdPath = storagePathList[1] + "/";
						} else if (storagePathList.length == 1) {
							sdPath = storagePathList[0] + "/";
						}
					}
				}

				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b = null;
				File file = new File(sdPath + "WORK_PICTURE/"
						+ Constants.GetDate() + "/" + currentCode + "/");
				file.mkdirs();// 创建文件夹
				if (!file.exists()) {
					sdPath = "/mnt/sdcard/";
					file = new File(sdPath + "/WORK_PICTURE/"
							+ Constants.GetDate() + "/" + currentCode + "/");
					file.mkdirs();// 创建文件夹
				}

				UUID guid = UUID.randomUUID();
				String fileName = file.getAbsolutePath() + File.separator
						+ guid + ".jpg";

				// 如果是门头照存储本地
				String localfileName = "";
				if (((MyApplication) getApplication()).Getlocaldata().currentphototype
						.equalsIgnoreCase("1")) {
					File localfile = new File(sdPath + "/WORK_PICTURE/"
							+ currentCode);
					localfile.mkdirs();// 创建文件夹
					localfileName = localfile.getAbsolutePath()
							+ File.separator + currentCode + ".jpg";
				}

				try {
					File newfile = new File(fileName);
					if (!newfile.exists()) {
						try {
							newfile.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

					// 如果是门头照存储本地
					if (!localfileName.equalsIgnoreCase("")) {
						File fi = new File(localfileName);
						if (fi.exists()) {
							fi.delete();
						}
						b = new FileOutputStream(localfileName);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				final ImageModel imageModel = new ImageModel();

				imageModel.Id = guid.toString();
				imageModel.Path = fileName;
				imageModel.ImageName = guid + ".jpg";
				imageModel.ImageType = ((MyApplication) getApplication())
						.Getlocaldata().currentphototype;
				imageModel.ImageOpearter = ((MyApplication) getApplication())
						.Getlocaldata().personno;
				imageModel.DateTime = Constants.GetTime();
				imageModel.CustomCode = currentCode;
				if (((MyApplication) getApplication()).loc != null) {
					imageModel.Lat = ((MyApplication) getApplication()).loc.LAT;
					imageModel.Lon = ((MyApplication) getApplication()).loc.LON;
				} else {
					imageModel.Lat = ((MyApplication) getApplication())
							.Getlocaldata().lastlat;
					imageModel.Lon = ((MyApplication) getApplication())
							.Getlocaldata().lastlon;
				}

				final EditText inputServer = new EditText(this);
				inputServer.setFocusable(true);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("输入照片备注").setView(inputServer);
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								String inputName = inputServer.getText()
										.toString();
								imageModel.F_REMARK = inputName;
								ImageDAL.Insert(getApplication(), imageModel);
								Getlocaltype();
							}
						});
				builder.show();
			}
		}
	}

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setAddrType("all"); // 设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
		option.setScanSpan(10000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		mLocClient.setLocOption(option);
	}
}
