package com.orwlw.comm;

import java.util.List;
import java.util.Map;
import com.orwlw.activity.GalleryProductActivity;
import com.orwlw.activity.R;
import com.orwlw.comm.AsyncBitmapLoader.ImageCallBack;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	private Context context;
	public List<Map<String, Object>> li;

	public ProductAdapter(Context context, List<Map<String, Object>> li) {
		this.context = context;
		this.li = li;
	}

	// get the number
	@Override
	public int getCount() {
		return li.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	// get the current selector's id number
	@Override
	public long getItemId(int position) {
		return position;
	}

	// create view method
	@Override
	public View getView(int position, View view, ViewGroup viewgroup) {
		final ImgTextWrapper wrapper;
		final int j = position;
		if (view == null) {
			wrapper = new ImgTextWrapper();
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.product_photo, null);
			view.setTag(wrapper);
			view.setPadding(1, 0, 1, 0); // 每格的间距
		} else {
			wrapper = (ImgTextWrapper) view.getTag();
		}

		wrapper.comms_pic = (ImageView) view.findViewById(R.id.imageView1);
		wrapper.comms_pic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		wrapper.txtview1 = (TextView) view.findViewById(R.id.textView1);

		Bitmap bitmap = new AsyncBitmapLoader().loadBitmap(
				wrapper.comms_pic,
				"http://"
						+ ((MyApplication) context.getApplicationContext())
								.Getlocaldata().app
						+ ".orwlw.com/content/image/ProductImage/"
						+ li.get(j).get("F_COMM_CODE") + "/"
						+ li.get(j).get("F_COMM_CODE") + ".jpg",
				new ImageCallBack() {

					@Override
					public void imageLoad(ImageView imageView, Bitmap bitmap) {
						wrapper.txtview1.setVisibility(8);
						imageView.setImageBitmap(bitmap);
					}
				});
		if (bitmap == null) {
			wrapper.txtview1.setVisibility(0);
			wrapper.comms_pic.setImageBitmap(null);
		} else {
			wrapper.comms_pic.setImageBitmap(bitmap);
		}

		wrapper.myLinearLayout = (LinearLayout) view
				.findViewById(R.id.linearLayout1);
		wrapper.myLinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MyApplication) context.getApplicationContext())
						.Savelocaldata("CURRENT_COMM_CODE",
								li.get(j).get("F_COMM_CODE").toString());
				((MyApplication) context.getApplicationContext())
						.Savelocaldata("CURRENT_COMM_NAME",
								li.get(j).get("F_COMM_CODE").toString());
				context.startActivity(new Intent(context,
						GalleryProductActivity.class));
			}
		});

		wrapper.txt_comms = (TextView) view.findViewById(R.id.text_comm);
		wrapper.txt_comms.setText(li.get(position).get("F_COMM_NAME")
				.toString());
		return view;
	}

	class ImgTextWrapper {
		ImageView comms_pic;
		TextView txtview1;
		TextView txt_comms;
		// TextView show_more;
		LinearLayout myLinearLayout;
	}

}
