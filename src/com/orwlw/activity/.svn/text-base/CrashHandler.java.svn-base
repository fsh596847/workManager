package com.orwlw.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 全局的异常处理类
 */
public class CrashHandler implements UncaughtExceptionHandler {
	// Debug Log tag
	public ProgressDialog dialog;
	public Timer timer;
	public Toast toast = null;
	private Handler mHandler = null;

	public static String msg = "";
	public static String msgstack = "";
	public static Runnable mToastThread;
	public static final String TAG = "CrashHandler";
	public static boolean flag = true;

	// 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
	public static final boolean DEBUG = true;

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	// CrashHandler实例
	private static CrashHandler INSTANCE;

	// 程序的Context对象
	public static Context mContext;

	// 使用Properties来保存设备的信息和错误堆栈信息
	private Properties mDeviceCrashInfo = new Properties();
	private static final String VERSION_NAME = "versionName";
	private static final String VERSION_CODE = "versionCode";
	private static final String STACK_TRACE = "STACK_TRACE";

	private static final String CRASH_REPORTER_EXTENSION = ".xml";// 错误报告文件的扩展名

	// 保证只有一个CrashHandler实例
	private CrashHandler() {
	}

	// 获取CrashHandler实例 ,单例模式
	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	// 初始化,注册Context对象, 获取系统默认的UncaughtException处理器,
	// 设置该CrashHandler为程序的默认处理器
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	// 当UncaughtException发生时会转入该函数来处理
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		MyApplication.WriteLog("系统发生异常崩溃了");
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理 61.
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			// Sleep一会后结束程序
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {

			}
			// Android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(10);
		}
	}

	/**
	 * 75. * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		msg = ex.getLocalizedMessage();
		msgstack = ex.getStackTrace().toString();
		collectCrashDeviceInfo(mContext);// 收集设备信息
		saveCrashInfoToFile(ex);// 保存异常信息到文件
		MyApplication.WriteLog("系统发生异常崩溃了");

		return false;
	}

	// 未启用
	private void execToast() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				initToast();
			}

		}, 300000000);
	}

	// 未启用
	private void initToast() {
		toast.show();
	}

	public static void killAll(Context context) {

		// 获取一个ActivityManager 对象
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		// 获取系统中所有正在运行的进程
		List<RunningAppProcessInfo> appProcessInfos = activityManager
				.getRunningAppProcesses();

		// 获取当前activity所在的进程
		String currentProcess = context.getApplicationInfo().processName;

		// 对系统中所有正在运行的进程进行迭代，如果进程名不是当前进程，则Kill掉
		for (RunningAppProcessInfo appProcessInfo : appProcessInfos) {
			String processName = appProcessInfo.processName;
			if (processName.equals(".GPSserver")) {
				// System.out.println("ApplicationInfo-->"+processName);
				activityManager.killBackgroundProcesses(processName);
				// System.out.println("Killed -->PID:"+appProcessInfo.pid+"--ProcessName:"+processName);
			}
		}
	}

	// 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
	public void sendPreviousReportsToServer() {
		sendCrashReportsToServer(mContext);
	}

	// 把错误报告发送给服务器,包含新产生的和以前没发送的.
	private void sendCrashReportsToServer(Context ctx) {
		String[] crFiles = getCrashReportFiles(ctx);
		if (crFiles != null && crFiles.length > 0) {
			TreeSet<String> sortedFiles = new TreeSet<String>();
			sortedFiles.addAll(Arrays.asList(crFiles));

			for (String fileName : sortedFiles) {
				File cr = new File(ctx.getFilesDir(), fileName);
				postReport(cr);
				cr.delete();// 删除已发送的报告
			}
		}
	}

	private void postReport(File file) {
		// TODO 使用HTTP Post 发送错误报告到服务器 134.
		// 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作 135.
		// 教程来提交错误报告 136.
		Boolean success = SyncHelper.SubmitErrorFile(file,
				((TelephonyManager) mContext.getSystemService("phone"))
						.getDeviceId());
		if (DEBUG) {
			Log.d(TAG, "发送错误报告结果" + success.toString());
		}

	}

	// 获取错误报告文件名
	private String[] getCrashReportFiles(Context ctx) {
		File filesDir = ctx.getFilesDir();
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(CRASH_REPORTER_EXTENSION);
			}
		};
		return filesDir.list(filter);
	}

	// 保存错误信息到文件中
	private String saveCrashInfoToFile(Throwable ex) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);

		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}

		String result = info.toString();
		printWriter.close();
		mDeviceCrashInfo.put(STACK_TRACE, result);

		try {
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyy-MM-dd-HH_mm_ss");
			String time = formatter.format(curDate);
			String fileName = "or"
					+ "_"
					+ ((TelephonyManager) mContext.getSystemService("phone"))
							.getDeviceId() + "_" + time
					+ CRASH_REPORTER_EXTENSION;
			FileOutputStream trace = mContext.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			mDeviceCrashInfo.storeToXML(trace, "异常信息");
			trace.flush();
			trace.close();
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing report file...", e);
		}
		return null;
	}

	// 收集程序崩溃的设备信息
	public void collectCrashDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				mDeviceCrashInfo.put(VERSION_NAME,
						pi.versionName == null ? "not set" : pi.versionName);
				mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode + "");
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Error while collect package info", e);
		}
		// 使用反射来收集设备信息.在Build类中包含各种设备信息,
		// 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
		// 具体信息请参考后面的截图
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				mDeviceCrashInfo.put(field.getName(), field.get(null)
						.toString());
				if (DEBUG) {
					Log.d(TAG, field.getName() + " : " + field.get(null));
				}
			} catch (Exception e) {
				Log.e(TAG, "Error while collect crash info", e);
			}

		}

	}
}
