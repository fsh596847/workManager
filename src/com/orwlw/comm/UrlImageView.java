/**
 * 鏂囦欢鍚嶏細UrlImageView.java
 * 鍏ㄨ矾寰勶細cn.intimes.client.ui.UrlImageView
 */
package com.orwlw.comm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 鍔熻兘锛氳嚜瀹氫箟鍥剧墖鎺т欢锛屽疄鐜板姞杞界綉缁滃浘鐗囧姛鑳�br>
 * 浣滆�锛氱劍鏈嬮<br>
 * 鏃堕棿锛�014-1-17<br>
 * 鐗堟湰锛�.0<br>
 * 
 */
public class UrlImageView extends ImageView {

	private final String tag = "UrlImageView"; // log鏍囩
	private String localImageUrl = ""; // 缂撳瓨涓�釜鍥剧墖鍦板潃锛岄槻姝㈤噸澶嶅姞杞�

	public UrlImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public UrlImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UrlImageView(Context context) {
		super(context);
	}

	/**
	 * 鍔犺浇缃戠粶鍥剧墖銆傚厛妫�煡璇ュ浘鐗囧湪鏈湴鏄惁鏈夌紦瀛橈紝濡傛灉鏈夛紝鐩存帴鍔犺浇鏈湴锛�br>
	 * 鍚﹀垯锛岀綉缁滆幏鍙栥�<br>
	 * 
	 * @param imageUrl
	 *            鍥剧墖閾炬帴
	 * @param resId
	 *            鍒濆鍥剧墖Id
	 */
	public void setImageUrl(final String imageUrl, final int resId) {
		if (imageUrl == null || imageUrl.equals("") || imageUrl.equals("null")) {
			Log.i(tag, "鍥剧墖閾炬帴涓簄ull");
			return;
		}
		if (imageUrl.equals(localImageUrl)) {
			return;
		}
		// 鑾峰彇缃戠粶閾炬帴鍦ㄦ湰鍦扮殑淇濆瓨璺緞
		final String imagePath = getImagePath(imageUrl);
		// 璇诲彇鏈湴鍥剧墖
		Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
		// 鏈湴鍥剧墖涓簄ull锛屼粠缃戠粶鍔犺浇
		if (imageBitmap == null) {
			new AsyncTask<Void, Void, Bitmap>() {

				@Override
				protected void onPreExecute() {
					setImageResource(resId);
				}

				@Override
				protected Bitmap doInBackground(Void... params) {
					return getImageFromHttp(imageUrl, imagePath);
				}

				@Override
				protected void onPostExecute(Bitmap result) {
					if (result == null) {
						Log.i(tag, "鍔犺浇鍥剧墖澶辫触");
						return;
					}
					setImageBitmap(result);
					localImageUrl = imageUrl;
				}

			}.execute();
		} else {
			// 鍔犺浇鏈湴鍥剧墖
			setImageBitmap(imageBitmap);
			localImageUrl = imageUrl;
		}
	}

	/**
	 * 鑾峰彇鍥剧墖淇濆瓨鍦ㄦ湰鍦扮殑璺緞
	 * 
	 * @param imageUrl
	 *            鍥剧墖閾炬帴
	 * @return
	 */
	private String getImagePath(String imageUrl) {
		if (imageUrl == null) {
			return null;
		}
		// sd鍗″彲鐢�
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().getPath()
					+ File.separator + "intimes" + File.separator
					+ getContext().getPackageName() + File.separator
					+ "imageCache" + File.separator
					+ imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		}
		// sd鍗′笉鍙敤鏃惰繑鍥瀗ull
		return null;
	}

	/**
	 * 鍔犺浇缃戠粶鍥剧墖
	 * 
	 * @param imageUrl
	 *            鍥剧墖鍦板潃
	 * @return 鍔犺浇鎴愬姛锛岃繑鍥炲浘鐗囷紱鍚﹀垯杩斿洖null
	 */
	private Bitmap getImageFromHttp(String imageUrl, String imagePath) {
		if (imageUrl == null) {
			return null;
		}
		Bitmap bitmap = null;
		File imageFile = null;
		try {
			Log.i(tag, "涓嬭浇鍥剧墖锛屽浘鐗囪繛鎺ワ細" + imageUrl + "锛屼繚瀛樿矾寰勶細" + imagePath);
			// 璁剧疆缃戠粶璇锋眰
			HttpGet httpGet = new HttpGet(imageUrl);
			// 鍙戣捣缃戠粶璇锋眰
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			// 澶勭悊鍝嶅簲缁撴灉
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 鍝嶅簲鎴愬姛
				InputStream is = httpResponse.getEntity().getContent();
				// 鍥剧墖璺緞涓簄ull锛岀洿鎺ヨ繑鍥瀊itmap锛涘惁鍒欙紝灏嗗浘鐗囦繚瀛樺湪鏈湴鍚庯紝杩斿洖
				if (imagePath == null) {
					bitmap = BitmapFactory.decodeStream(is);
				} else {
					imageFile = new File(imagePath);
					File imageParent = imageFile.getParentFile();
					if (!imageParent.exists()) {
						imageParent.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream(imageFile);
					byte[] buffer = new byte[8192];
					int count = 0;
					while ((count = is.read(buffer)) != -1) {
						fos.write(buffer, 0, count);
					}
					fos.close();
					is.close();
					bitmap = BitmapFactory.decodeFile(imagePath);
				}
			}
		} catch (Exception e) {
			Log.w(tag, "鍔犺浇缃戠粶鍥剧墖寮傚父", e);
			if (imageFile != null && imageFile.exists()) {
				imageFile.delete();
			}
		}
		return bitmap;
	}
}
