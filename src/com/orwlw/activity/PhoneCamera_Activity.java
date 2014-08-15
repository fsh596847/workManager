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
	 * ��ť������������¼�
	 * 
	 * @param v
	 */
	public void btnOnclick(View v) {
		if (camera != null) {
			switch (v.getId()) {
			case R.id.takepicture:
				// ����
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
	 * ͼƬ�����������ʱ��
	 * 
	 * @param v
	 */
	public void imageClick(View v) {
		if (v.getId() == R.id.scalePic) {
			if (bundle == null) {
				Toast.makeText(getApplicationContext(), "û��ҪԤ����Ƭ",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(this, ShowPicActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
	}

	// ��Ƭ�ص�
	private final class MyPictureCallback implements PictureCallback {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				bundle = new Bundle();
				bundle.putByteArray("bytes", data); // ��ͼƬ�ֽ����ݱ�����bundle���У�ʵ�����ݽ���
				saveToSDCard(data); // ����ͼƬ��sd����
				Toast.makeText(getApplicationContext(), "����ɹ�",
						Toast.LENGTH_SHORT).show();
				camera.startPreview(); // �����պ����¿�ʼԤ��

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ������������Ƭ�����SD����
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static void saveToSDCard(byte[] data) throws IOException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // ��ʽ��ʱ��
		String filename = format.format(date) + ".jpg";
		File fileFolder = new File(Environment.getExternalStorageDirectory()
				+ "/finger/");
		if (!fileFolder.exists()) { // ���Ŀ¼�����ڣ��򴴽�һ����Ϊ"finger"��Ŀ¼
			fileFolder.mkdir();
		}
		File jpgFile = new File(fileFolder, filename);
		FileOutputStream outputStream = new FileOutputStream(jpgFile); // �ļ������
		outputStream.write(data); // д��sd����
		outputStream.close(); // �ر������
	}

	private final class SurfaceCallback implements Callback {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// // TODO Auto-generated method stub
			// parameters = camera.getParameters();
			// parameters.setPictureFormat(PixelFormat.JPEG);
			// parameters.setPreviewSize(width, height);
			// parameters.setPreviewFrameRate(5); // ����ÿ����ʾ4֡
			//
			// parameters.setPictureSize(width, height); // ���ñ����ͼƬ�ߴ�
			// parameters.setJpegQuality(80); // ������Ƭ����
			try {
				if (camera != null) {
					Camera.Parameters parameters = camera.getParameters();
					int height1 = 480;
					int width1 = 640;
					// if (IsClose) {
					// parameters.setPictureSize(640, 480);
					// } else {
					int cou = 0;
					// ��ȡ֧�ֵ����������б�
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
				// ����������ʾ����Ӱ���SurfaceHolder����// ����Ԥ��
				camera.setPreviewDisplay(holder);
				camera.setDisplayOrientation(getPreviewDegree(PhoneCamera_Activity.this));
				camera.startPreview(); // ��ʼԤ��

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			// ֹͣԤ��
			if (camera != null) {
				camera.stopPreview(); // ֹͣԤ��
				// �ͷ������Դ
				camera.release(); // �ͷ������
				camera = null;
			}

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			layout.setVisibility(ViewGroup.VISIBLE); // ������ͼ�ɼ�
			break;
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_CAMERA: // �������հ�ť
			if (camera != null && event.getRepeatCount() == 0) {
				// ����
				// ע������takePicture()�������������Ǵ�����һ��PictureCallback���󡪡��������ȡ���������õ�ͼƬ����֮��
				// ��PictureCallback���󽫻ᱻ�ص����ö�����Ը������Ƭ���б����������
				camera.takePicture(null, null, new MyPictureCallback());
			}
		}
		return super.onKeyDown(keyCode, event);

	}

	// �ṩһ����̬���������ڸ����ֻ����������Ԥ��������ת�ĽǶ�
	public static int getPreviewDegree(Activity activity) {
		// ����ֻ��ķ���
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degree = 0;
		// �����ֻ��ķ���������Ԥ������Ӧ��ѡ��ĽǶ�
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
