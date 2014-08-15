package com.orwlw.activity;

import java.io.UnsupportedEncodingException;
import com.orwlw.comm.BluetoothService;
import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;
import com.orwlw.dal.ExchangeBackDAL;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Print_OrderActivity extends Activity {
	// SlipButton sb = null;
	ProgressBar pbar;
	TextView print_view;
	String txt_result = "";
	String txt_result1 = "";
	String result = "";
	Button btn_ok;
	Button btn_start_print;
	TextView titleTextView;
	int PRINT_TYPE;

	// 连接蓝牙打印机的几个常量状态
	public static final int MESSAGE_STATE_CHANGE = 3;
	public static final int MESSAGE_READ = 4;
	public static final int MESSAGE_WRITE = 5;
	public static final int MESSAGE_DEVICE_NAME = 6;
	public static final int MESSAGE_TOAST = 7;

	public static final String DEVICE_NAME = "device_name";
	public static String DEVICE_ID = "";
	public static final String TOAST = "toast";

	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	private String mConnectedDeviceName = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothService mService = null;

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				print_view.setText(result);
				pbar.setVisibility(8);
				break;
			case 2:
				print_view.setText(result);
				pbar.setVisibility(8);
				btn_ok.setText("已完成！");
				btn_ok.setClickable(false);
				break;
			case MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
					btn_start_print.setText("已成功连接,点击打印");
					((MyApplication) getApplication()).Savelocaldata(
							"print_device", DEVICE_ID);
					break;
				case BluetoothService.STATE_CONNECTING:
					btn_start_print.setText("正在连接打印设备…");
					break;
				case BluetoothService.STATE_LISTEN:
				case BluetoothService.STATE_NONE:
					btn_start_print.setText("未连接到打印设备");
					break;
				}
				break;
			case MESSAGE_WRITE:
				break;
			case MESSAGE_READ:
				break;
			case MESSAGE_DEVICE_NAME:
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.print_voder_view);
		// sb = (SlipButton) findViewById(R.id.slipbutton1);
		titleTextView = (TextView) findViewById(R.id.textView_title);
		// sb.SetOnChangedListener(new OnChangedListener() {
		//
		// public void OnChanged(boolean CheckState) {
		// if (CheckState) {
		// titleTextView.setText("在线订单");
		// getOrder_resut();
		// } else {
		// titleTextView.setText("放货单");
		// getTruck_resut();
		// }
		// }
		// });
		// sb.setCheck(true);

		pbar = (ProgressBar) findViewById(R.id.progressBar1);
		print_view = (TextView) findViewById(R.id.txt_print_view);
		btn_ok = (Button) findViewById(R.id.print_btn_ok);
		btn_start_print = (Button) findViewById(R.id.print_btn_start);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "该设备不支持蓝牙功能，打印功能将不能使用", Toast.LENGTH_LONG)
					.show();
		}

		Intent intent = getIntent();
		if (intent != null) {
			PRINT_TYPE = intent.getIntExtra(Constants.PRINT_TYPE, 0);
			txt_result = intent.getStringExtra("str");
			switch (PRINT_TYPE) {
			case Constants.PRINT_ONLINE_ORDER:
				getOrder_resut();
				break;
			case Constants.PRINT_SALE_OUT:
				getTruck_resut();
				break;
			case Constants.PRINT_EXCHANGE_COMM:
				// 调货单预览
				txt_result1 = intent.getStringExtra("str1");
				getExchange_resut();
				break;
			case Constants.PRINT_BACK_COMM:
				// 退货单预览
				getBack_resut();
				break;
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!mBluetoothAdapter.isEnabled()) {
			mBluetoothAdapter.enable();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mService != null) {
			if (mService.getState() == BluetoothService.STATE_NONE) {
				mService.start();
			}
		} else {
			mService = new BluetoothService(this, handler1);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mService != null)
			mService.stop();
		if (mBluetoothAdapter != null) {
			if (mBluetoothAdapter.isEnabled()) {
				mBluetoothAdapter.disable();
			}
		}

	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	// 根据商品获取在线订单的单据预览
	void getOrder_resut() {
		pbar.setVisibility(0);
		new Thread() {
			public void run() {
				try {
					result = SyncHelper
							.Get_Order_Resut_View(
									((MyApplication) getApplication())
											.Getlocaldata().app,
									((MyApplication) getApplication())
											.Getlocaldata().personno,
									((MyApplication) getApplication())
											.Getlocaldata().current_custom_code,
									txt_result, Constants.GetTime(), "0");
					message(1);
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	// 根据商品获取放货单的单据预览
	void getTruck_resut() {
		pbar.setVisibility(0);
		new Thread() {
			public void run() {
				try {
					result = SyncHelper
							.Get_Truck_Resut_View(

									((MyApplication) getApplication())
											.Getlocaldata().personno,
									((MyApplication) getApplication())
											.Getlocaldata().current_custom_code,
									txt_result, "0");
					message(1);
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	// 根据商品获取调货单的单据预览
	void getExchange_resut() {
		pbar.setVisibility(0);
		new Thread() {
			public void run() {
				try {
					result = SyncHelper
							.ExchangeComms(((MyApplication) getApplication())
									.Getlocaldata().current_custom_code,
									((MyApplication) getApplication())
											.Getlocaldata().personno,
									txt_result, txt_result1, "0");
					message(1);
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	// 根据商品获取 退货单的单据预览
	void getBack_resut() {
		pbar.setVisibility(0);
		new Thread() {
			public void run() {
				try {
					result = SyncHelper
							.BackComms(((MyApplication) getApplication())
									.Getlocaldata().current_custom_code,
									((MyApplication) getApplication())
											.Getlocaldata().personno,
									txt_result, "0");
					message(1);
				} catch (SQLiteException e) {
					MyApplication.WriteLog(e.getMessage());
				}
			};
		}.start();
	}

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	/**
	 * 上传订单
	 * 
	 * @param v
	 */
	public void btn_print(View v) {
		switch (PRINT_TYPE) {
		case Constants.PRINT_ONLINE_ORDER:
			result = SyncHelper
					.Get_Order_Resut_View(
							((MyApplication) getApplication()).Getlocaldata().app,
							((MyApplication) getApplication()).Getlocaldata().personno,
							((MyApplication) getApplication()).Getlocaldata().current_custom_code,
							txt_result, Constants.GetTime(), "1");
			message(2);
			break;
		case Constants.PRINT_SALE_OUT:
			result = SyncHelper
					.Get_Truck_Resut_View(
							((MyApplication) getApplication()).Getlocaldata().personno,
							((MyApplication) getApplication()).Getlocaldata().current_custom_code,
							txt_result, "1");
			message(2);
			break;
		case Constants.PRINT_EXCHANGE_COMM:
			result = SyncHelper
					.ExchangeComms(
							((MyApplication) getApplication()).Getlocaldata().current_custom_code,
							((MyApplication) getApplication()).Getlocaldata().personno,
							txt_result, txt_result1, "1");
			if (!result.equalsIgnoreCase("")) {
				ExchangeBackDAL
						.DeleteLocalExchangeComms_by_custom(getApplication(),
								((MyApplication) getApplication())
										.Getlocaldata().current_custom_code);
				message(2);
			}

			break;
		case Constants.PRINT_BACK_COMM:
			result = SyncHelper
					.BackComms(
							((MyApplication) getApplication()).Getlocaldata().current_custom_code,
							((MyApplication) getApplication()).Getlocaldata().personno,
							txt_result, "1");
			if (!result.equalsIgnoreCase("")) {
				ExchangeBackDAL
						.DeleteLocalBackComms_by_custom(getApplication(),
								((MyApplication) getApplication())
										.Getlocaldata().current_custom_code);
				message(2);
			}
			break;
		}

	}

	/**
	 * 打印小票
	 * 
	 * @param v
	 */
	public void btn_print_start(View v) {
		if (!btn_ok.isClickable()) {
			String message = print_view.getText().toString();
			if (sendMessage(message))
				btn_start_print.setClickable(false);
		} else {
			Toast.makeText(this, "请下单成功后进行打印！", Toast.LENGTH_SHORT).show();
		}

	}

	private boolean sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
			// Toast.makeText(this, "未连接打印机,不可打印", Toast.LENGTH_SHORT).show();
			if (!((MyApplication) getApplication()).Getlocaldata().print_device
					.equalsIgnoreCase("")) {
				String address = ((MyApplication) getApplication())
						.Getlocaldata().print_device;
				if (BluetoothAdapter.checkBluetoothAddress(address)) {
					mService.connect(mBluetoothAdapter.getRemoteDevice(address));
				}
			} else {
				Toast.makeText(this, "点击菜单键->扫描 可用设备！", Toast.LENGTH_SHORT)
						.show();
			}

			return false;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothService to write
			byte[] send;
			try {
				send = message.getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
				send = message.getBytes();
			}
			mService.write(send);
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			try {
				// When DeviceListActivity returns with a device to connect
				if (resultCode == Activity.RESULT_OK) {
					// Get the device MAC address
					String address = data.getExtras().getString(
							DeviceListActivity.EXTRA_DEVICE_ADDRESS);
					// Get the BLuetoothDevice object
					if (BluetoothAdapter.checkBluetoothAddress(address)) {
						BluetoothDevice device = mBluetoothAdapter
								.getRemoteDevice(address);
						// Attempt to connect to the device
						DEVICE_ID = address;
						mService.connect(device);
					}
				}
			} catch (Exception e) {
				Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a session
				// init();
			} else {
				// User did not enable Bluetooth or an error occured
				Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.option_menu, menu);
		menu.add(Menu.NONE, 0, Menu.NONE, "扫描蓝牙设备");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return true;
		}
		return false;
	}
}
