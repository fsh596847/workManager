package com.orwlw.activity;

import java.util.List;
import java.util.Map;
import com.orwlw.comm.AsyncBitmapLoader;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.SyncHelper;
import com.orwlw.comm.AsyncBitmapLoader.ImageCallBack;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryProductActivity extends Activity {
	private Gallery gallery;
	public List<Map<String, Object>> li;
	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.product_pic_all);
		gallery = (Gallery) findViewById(R.id.gallery);
		mContext = this;
		getpics();
	}

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				gallery.setAdapter(new ImageAdapter(mContext, li));
				break;
			}
		};
	};

	void getpics() {
		new Thread() {
			public void run() {
				Looper.prepare();

				li = SyncHelper.GetProductPic(((MyApplication) mContext
						.getApplicationContext()).Getlocaldata().app,
						((MyApplication) mContext.getApplicationContext())
								.Getlocaldata().CURRENT_COMM_CODE);
				message(1);
				Looper.loop();
			};
		}.start();
	}

	void message(int x) {
		Message attaget = Message.obtain();
		attaget.what = x;
		handler1.sendMessage(attaget);
	}

	public class ImageAdapter extends BaseAdapter {
		// int mGalleryItemBackground;
		private Context mContext;

		public ImageAdapter(Context context, List<Map<String, Object>> lii) {
			mContext = context;
			li = lii;
			// TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
			// mGalleryItemBackground = a.getResourceId(
			// R.styleable.Gallery1_android_galleryItemBackground, 0);
			// a.recycle();
		}

		@Override
		public int getCount() {
			return li.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ImgTextWrapper wrapper;
			if (view == null) {
				wrapper = new ImgTextWrapper();
				LayoutInflater inflater = LayoutInflater.from(mContext);
				view = inflater.inflate(R.layout.product_pic_all_item, null);
				view.setTag(wrapper);
			} else {
				wrapper = (ImgTextWrapper) view.getTag();
			}
			final int j = position;
			wrapper.imageView = (ImageView) view.findViewById(R.id.imageView1);
			Bitmap bitmap = new AsyncBitmapLoader().loadBitmap(
					wrapper.imageView, li.get(position).get("F_IMAGE_NAME")
							.toString(), new ImageCallBack() {

						@Override
						public void imageLoad(ImageView imageView, Bitmap bitmap) {
							imageView.setImageBitmap(bitmap);
						}
					});
			if (bitmap == null) {
				view.setLayoutParams(new Gallery.LayoutParams(300, 400));
				wrapper.imageView.setImageBitmap(null);
			} else {
				wrapper.imageView.setImageBitmap(bitmap);
			}
			wrapper.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			// view.setLayoutParams(new Gallery.LayoutParams(300, 400));

			// The preferred Gallery item background
			// view.setBackgroundResource(mGalleryItemBackground);

			wrapper.textView = (TextView) view.findViewById(R.id.textView2);
			wrapper.textView.setText(li.get(position).get("F_IMAGE_DESCRIBE")
					.toString());

			return view;
		}

		class ImgTextWrapper {
			ImageView imageView;
			TextView textView;
		}

	}

}
