package com.orwlw.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.model.ImageModel;
import com.orwlw.net.ConvertToBean;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowPicActivity extends Activity {
	private ImageView ivPic = null; // 显示图片控件
	private Button button_submit;// 提交结案照
	private Button button_delete;// 删除结案照
	boolean statue = false;// 是提交成功
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (statue == true) {
				Toast.makeText(ShowPicActivity.this, "提交成功", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(ShowPicActivity.this, "提交失败", Toast.LENGTH_LONG)
						.show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpic);
		ivPic = (ImageView) findViewById(R.id.ivPic);
		button_submit = (Button) findViewById(R.id.submit);
		button_delete = (Button) findViewById(R.id.delete);

		setImageBitmap(getImageFormBundle());
		button_submit.setOnClickListener(conClickListener);
		button_delete.setOnClickListener(conClickListener);
	}

	/**
	 * 将MainActivity传过来的图片显示在界面当中
	 * 
	 * @param bytes
	 */
	public void setImageBitmap(byte[] bytes) {
		Bitmap cameraBitmap = byte2Bitmap();
		// 根据拍摄的方向旋转图像（纵向拍摄时要需要将图像选择90度)
		Matrix matrix = new Matrix();
		matrix.setRotate(PhoneCamera_Activity.getPreviewDegree(this));
		cameraBitmap = Bitmap
				.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(),
						cameraBitmap.getHeight(), matrix, true);
		ivPic.setImageBitmap(cameraBitmap);
	}

	/**
	 * 从Bundle对象中获取数据
	 * 
	 * @return
	 */
	public byte[] getImageFormBundle() {
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		byte[] bytes = data.getByteArray("bytes");
		return bytes;
	}

	/**
	 * 将字节数组的图形数据转换为Bitmap
	 * 
	 * @return
	 */
	private Bitmap byte2Bitmap() {
		byte[] data = getImageFormBundle();
		// 将byte数组转换成Bitmap对象
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		return bitmap;
	}

	private OnClickListener conClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.submit:
				thread();
				break;
			case R.id.delete:
				// deleteFile("/sdcard/finger/");
				break;
			default:
				break;
			}

		}
	};

	private void thread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				submit_image();
			}
		}).start();
	}

	/*
	 * 删除文件的方法
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	private void submit_image() {
		try {
			// 图pain储存路径
			// 文件路径
			String path = Environment.getExternalStorageDirectory()
					+ File.separator + "finger";
			File file = new File(path);
			File[] files_image = file.listFiles();
			int length = files_image.length;// 长度
			String custom_code = ((MyApplication) getApplication())
					.Getlocaldata().current_custom_code;
			String personno = ((MyApplication) getApplication()).Getlocaldata().personno;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String time = format.format(date);
			for (int i = 0; i < length; i++) {
				// 乱麻麻
				File pic = new File(files_image[i] + "");
				FileInputStream fout;
				fout = new FileInputStream(pic);
				byte[] buffer = new byte[(int) pic.length()];
				int length1 = fout.read(buffer);
				String ret = new String();
				ret = Base64.encode(buffer); // 具体的编码方法
				// 图片名称
				String imagename = files_image[i].toString();
				imagename = imagename.substring(imagename.lastIndexOf("/") + 1);
				statue = new ConvertToBean(ShowPicActivity.this)
						.SaveJieAn_ImageFile(imagename, ret, length1,
								custom_code, time, personno);
				System.out.println("结果：" + statue);
				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
