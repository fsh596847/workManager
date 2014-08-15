package com.orwlw.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class PhoneCamera_Activity extends Activity {
	private View layout;
	private Camera camera;
	private Camera.Parameters parameters = null;
	private Bundle bundle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_camera);
		layout = this.findViewById(R.id.buttonLayout);
		SurfaceView surfaceView = (SurfaceView) this
				.findViewById(R.id.surfaceView);
		surfaceView.getHolder()
				.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().setFixedSize(176, 144);
		surfaceView.getHolder().setKeepScreenOn(true);
		surfaceView.getHolder().addCallback(new SurfaceCallback());
	}

	/**
	 * 按钮被点击触发的事件
	 * 
	 * @param v
	 */
	public void btnOnclick(View v) {
		if (camera != null) {
			switch (v.getId()) {
			case R.id.takepicture:
				// 拍照
				camera.autoFocus(new AutoFocusCallback() {

					@Override
					public void onAutoFocus(boolean success, Camera camera) {
						// TODO Auto-generated method stub
						if (success) {
							if (camera != null) {
								camera.takePicture(null, null,
										new MyPictureCallback());
							}
						}
					}
				});

				break;
			}
		}
	}

	/**
	 * 图片被点击触发的时间
	 * 
	 * @param v
	 */
	public void imageClick(View v) {
		if (v.getId() == R.id.scalePic) {
			if (bundle == null) {
				Toast.makeText(getApplicationContext(), "没有要预览照片",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(this, ShowPicActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
	}

	// 照片回调
	private final class MyPictureCallback implements PictureCallback {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				bundle = new Bundle();
				bundle.putByteArray("bytes", data); // 将图片字节数据保存在bundle当中，实现数据交换
				saveToSDCard(data); // 保存图片到sd卡中
				Toast.makeText(getApplicationContext(), "保存成功",
						Toast.LENGTH_SHORT).show();
				camera.startPreview(); // 拍完照后，重新开始预览

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将拍下来的照片存放在SD卡中
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static void saveToSDCard(byte[] data) throws IOException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
		String filename = format.format(date) + ".jpg";
		File fileFolder = new File(Environment.getExternalStorageDirectory()
				+ "/finger/");
		if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
			fileFolder.mkdir();
		}
		File jpgFile = new File(fileFolder, filename);
		FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
		outputStream.write(data); // 写入sd卡中
		outputStream.close(); // 关闭输出流
	}

	private final class SurfaceCallback implements Callback {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// // TODO Auto-generated method stub
			// parameters = camera.getParameters();
			// parameters.setPictureFormat(PixelFormat.JPEG);
			// parameters.setPreviewSize(width, height);
			// parameters.setPreviewFrameRate(5); // 设置每秒显示4帧
			//
			// parameters.setPictureSize(width, height); // 设置保存的图片尺寸
			// parameters.setJpegQuality(80); // 设置照片质量
			try {
				if (camera != null) {
					Camera.Parameters parameters = camera.getParameters();
					int height1 = 480;
					int width1 = 640;
					// if (IsClose) {
					// parameters.setPictureSize(640, 480);
					// } else {
					int cou = 0;
					// 获取支持的所有像素列表
					List<Size> supportedPreviewSizes = parameters
							.getSupportedPreviewSizes();
					for (int i = 0; i < supportedPreviewSizes.size(); i++) {
						if (supportedPreviewSizes.get(i) != null) {
							cou++;
						}
					}
					if (cou > 3) {
						height1 = supportedPreviewSizes.get(cou - 1).height;
						width1 = supportedPreviewSizes.get(cou - 1).width;
					}
					// }
					parameters.setPictureSize(width1, height1);
					System.out.println(width1+"==="+height1);
					parameters.setPictureFormat(PixelFormat.JPEG);// Setting
																	// Picture
					camera.setParameters(parameters); // Setting camera
														// parameters
					camera.startPreview(); // Start Preview
				}
			} catch (Exception e) {
				camera.release();// release camera
				camera = null;
			}

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			try {
				camera = Camera.open();
				// 设置用于显示拍照影像的SurfaceHolder对象，// 设置预览
				camera.setPreviewDisplay(holder);
				camera.setDisplayOrientation(getPreviewDegree(PhoneCamera_Activity.this));
				camera.startPreview(); // 开始预览

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			// 停止预览
			if (camera != null) {
				camera.stopPreview(); // 停止预览
				// 释放相机资源
				camera.release(); // 释放照相机
				camera = null;
			}

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			layout.setVisibility(ViewGroup.VISIBLE); // 设置视图可见
			break;
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_CAMERA: // 按下拍照按钮
			if (camera != null && event.getRepeatCount() == 0) {
				// 拍照
				// 注：调用takePicture()方法进行拍照是传入了一个PictureCallback对象——当程序获取了拍照所得的图片数据之后
				// ，PictureCallback对象将会被回调，该对象可以负责对相片进行保存或传入网络
				camera.takePicture(null, null, new MyPictureCallback());
			}
		}
		return super.onKeyDown(keyCode, event);

	}

	// 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
	public static int getPreviewDegree(Activity activity) {
		// 获得手机的方向
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degree = 0;
		// 根据手机的方向计算相机预览画面应该选择的角度
		switch (rotation) {
		case Surface.ROTATION_0:
			degree = 90;
			break;
		case Surface.ROTATION_90:
			degree = 0;
			break;
		case Surface.ROTATION_180:
			degree = 270;
			break;
		case Surface.ROTATION_270:
			degree = 180;
			break;
		}
		return degree;
	}

}
