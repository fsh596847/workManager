package com.orwlw.comm;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.os.Environment;
import android.util.Log;

/**
 * @author HYJ ��̬������
 */
public class Constants {
	// �ݷù���
	public static final String PHOTO_FUNCTION = "�����ϱ�";
	public static final String SUVERY_FUNCTION = "�ش��ʾ�";
	public static final String SUBMITSTOCK_FUNCTION = "����ϱ�";
	public static final String ONLINEORDER_FUNCTION = "���߶���";
	public static final String SUBMITLOCATION_FUNCTION = "�ɼ�λ��";
	public static final String HISTORYORDER_FUNCTION = "��ʷ����";
	public static final String ALARM_FUNCTION = "�����¼�";
	public static final String EXCHANGE_BACK_FUNCTION = "�����˻�";
	public static final String SALEUPLOAD_FUNCTION = "�����ϱ�";
	public static final String ATTEND_FUNCTION = "ǩ��";

	public static final String PHONECAMERA_FUNCTION = "�᰸��";
	// ������Ʒ����������
	public static final int SHOP_STORE_ACTION_ADD = 1;
	public static final int ONLINE_ORDER_ACTION_ADD = 2;
	public static final int EXCHANGE_COMMSOLD_ACTION_ADD = 3;
	public static final int EXCHANGE_COMMSNEW_ACTION_ADD = 4;
	public static final int BACK_COMMS_ACTION_ADD = 5;
	public static final int SHOP_STORE_ACTION_UPDATE = 6;
	public static final int ONLINE_ORDER_ACTION_UPDATE = 7;
	public static final int EXCHANGE_COMMSOLD_ACTION_UPDATE = 8;
	public static final int EXCHANGE_COMMSNEW_ACTION_UPDATE = 9;
	public static final int BACK_COMMS_ACTION_UPDATE = 10;
	public static final int SUVERY_ACTION_SELECT_COMMS = 11;

	// ��ӡ���ͳ���
	public static final String PRINT_TYPE = "PRINT_TYPE";
	public static final int PRINT_ONLINE_ORDER = 1;
	public static final int PRINT_SALE_OUT = 2;
	public static final int PRINT_EXCHANGE_COMM = 3;
	public static final int PRINT_BACK_COMM = 4;

	// ��������
	public static final String MESSAGE_FUNCTION = "�ҵ���Ϣ";
	public static final String REPORT_FUNCTION = "��Ч����";

	public static String[][] FuncImages = {
			{ Constants.PHOTO_FUNCTION, "takephoto" },
			{ Constants.SUVERY_FUNCTION, "question" },
			{ Constants.SUBMITSTOCK_FUNCTION, "store" },
			{ Constants.ONLINEORDER_FUNCTION, "onlineorder" },
			{ Constants.HISTORYORDER_FUNCTION, "historyorder" },
			{ Constants.ALARM_FUNCTION, "alarm" },
			{ Constants.MESSAGE_FUNCTION, "message" },
			{ Constants.REPORT_FUNCTION, "report" },
			{ Constants.EXCHANGE_BACK_FUNCTION, "exchange" },
			{ Constants.SALEUPLOAD_FUNCTION, "saleout" },
			{ Constants.PHONECAMERA_FUNCTION, "phonecamera" } };

	//
	public static final String MAP_KEY = "AEE9312B6EFBAFBC492F419CF1C5AF891FBDD5CF";

	// ����app
	public static final String ASC = "android.system.core";
	public static final String DOWNNAME = "core.apk";

	// ��˾ ouran0036
	// public static final String webserverString =
	// "http://webalone.orwlw.com/DataBaseUnite.asmx?wsdl";
	// public static final String webservicesString2 =
	// "http://webalone.orwlw.com/Gencodeing.asmx?wsdl";
	// public static final String WEB_SUVERY =
	// "http://webalone.orwlw.com/suvery/suverylist.aspx";
	// public static final String WEB_REPORT =
	// "http://webalone.orwlw.com/report/reportlist.aspx";
	// ʳƷ or2309
	public static final String webserverString = "http://orserver.orwlw.com/DataBaseUnite.asmx?wsdl";
	public static final String webservicesString2 = "http://orserver.orwlw.com/Gencodeing.asmx?wsdl";
	public static final String WEB_SUVERY = "http://orserver.orwlw.com/suvery/suverylist.aspx";
	public static final String WEB_REPORT = "http://orserver.orwlw.com/report/reportlist.aspx";

	// public static final String WEB_PRODUCT_PIC =
	// "http://webalone.orwlw.com/Sencha Touch/ProductPic/productpic.htm";
	public static final String PIC_LOADE = "http://{0}.orwlw.com/content/image/ProductImage/{1}/{2}.jpg";

	public static final String keycode = "OURAN-S";
	// ������Ʒ
	public static final String ALLCOMM = "ALLCOMM";
	// ������Ʒ
	public static final String USED = "USED";
	// �ͻ�
	public static final String CUSTOM = "CUSTOM";
	public static final String CORE_SERVICE = "android.system.core.service";
	public static final String CORE_RECEIVER = "com.android.core.WMSTOP";

	public static final int CUSTOM_TYPE_ALL = 1;
	public static final int CUSTOM_TYPE_PLAN = 2;
	public static final int CUSTOM_TYPE_ROAD = 3;

	public static String GetDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}

	public static String GetTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		return date;
	}

	public static String GetTime(Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				locale);
		String date = sdf.format(new Date());
		return date;
	}

	public static String GetYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String date = sdf.format(new Date());
		return date;
	}

	public static String GetMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String date = sdf.format(new Date());
		return date;
	}

	public static String GetDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String date = sdf.format(new Date());
		return date;
	}

	// ����ͼƬָ����С
	public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
		// ͼƬԴ
		// Bitmap bm = BitmapFactory.decodeStream(getResources()
		// .openRawResource(id));
		// ���ͼƬ�Ŀ��
		int width = bm.getWidth();
		int height = bm.getHeight();
		// ������Ҫ�Ĵ�С
		int newWidth1 = newWidth;
		int newHeight1 = newHeight;
		// �������ű���
		float scaleWidth = ((float) newWidth1) / width;
		float scaleHeight = ((float) newHeight1) / height;
		// ȡ����Ҫ���ŵ�matrix����
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// �õ��µ�ͼƬ
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);

		return newbm;
	}

	// ͼƬԲ��
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		final RectF rectF = new RectF(rect);

		final float roundPx = pixels;

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;

	}

	public static final String UPDATE_SAVENAME = "WorkManager.apk";
	public static final String UPDATE_VERJSON = "ver.txt";

	// ��ȡ����ʹ�õ�����İ汾��
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					"com.orwlw.activity", 0).versionCode;
		} catch (NameNotFoundException e) {

		}
		return verCode;
	}

	// ��ȡ����ʹ�õ����������
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					"com.orwlw.activity", 0).versionName;
		} catch (NameNotFoundException e) {

		}
		return verName;
	}

	// ��ȡ�汾��֤��ַ
	public static String getUpgradeURL(String app, String filename) {
		String urlString = String.format("http://%s.orwlw.com/%s", app,
				filename).toString();
		return urlString;
	}

	// ��ȡ����ͼƬ
	public static Bitmap getHttpBitmap(String url) {

		URL myFileURL;

		Bitmap bitmap = null;

		try {

			myFileURL = new URL(url);

			// �������

			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();

			// ���ó�ʱʱ��Ϊ6000���룬conn.setConnectionTiem(0);��ʾû��ʱ������

			conn.setConnectTimeout(6000);

			// �������û��������

			conn.setDoInput(true);

			// ��ʹ�û���

			conn.setUseCaches(false);

			// �����п��ޣ�û��Ӱ��

			// conn.connect();

			// �õ�������

			InputStream is = conn.getInputStream();

			// �����õ�ͼƬ

			bitmap = BitmapFactory.decodeStream(is);

			// �ر�������

			is.close();

		} catch (Exception e) {

		}

		return bitmap;

	}

	public static String replaceString(String strData, String regex,
			String replacement) {
		if (strData == null) {
			return null;
		}
		int index;
		index = strData.indexOf(regex);
		String strNew = "";
		if (index >= 0) {
			while (index >= 0) {
				strNew += strData.substring(0, index) + replacement;
				strData = strData.substring(index + regex.length());
				index = strData.indexOf(regex);
			}
			strNew += strData;
			return strNew;
		}
		return strData;
	}

	/**
	 * �滻�ַ����������ַ�
	 */
	public static String encodeString(String strData) {
		if (strData == null) {
			return "";
		}
		strData = replaceString(strData, "&", "&amp;");
		strData = replaceString(strData, "<", "&lt;");
		strData = replaceString(strData, ">", "&gt;");
		strData = replaceString(strData, "&apos;", "&apos;");
		strData = replaceString(strData, "\"", "&quot;");
		return strData;
	}

	/**
	 * ��ԭ�ַ����������ַ�
	 */
	public static String decodeString(String strData) {
		strData = replaceString(strData, "&lt;", "<");
		strData = replaceString(strData, "&gt;", ">");
		strData = replaceString(strData, "&apos;", "&apos;");
		strData = replaceString(strData, "&quot;", "\"");
		strData = replaceString(strData, "&amp;", "&");
		return strData;
	}

	/**
	 * �Ƿ��Ѱ�װ��������
	 */
	public static boolean isApkInstalled(Context context, final String pkgName) {
		try {
			context.getPackageManager().getPackageInfo(pkgName, 0);
			return true;
		} catch (NameNotFoundException e) {
			// e.printStackTrace();
		}
		return false;
	}

	/**
	 * �Ƿ��Ƿ���а�װ��
	 */
	public static boolean ifExists(String path) {
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File file = new File(path);
			if (file.exists()) {
				return true;
			}
		}
		return false;
	}

}
