package com.orwlw.comm;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.orwlw.activity.R;

public class PictureAdapter1 extends BaseAdapter {
	private List<String> list;
	private Context context;

	public PictureAdapter1(List<String> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {

		ImageView image = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.picture_item1, null);
			image = (ImageView) convertView.findViewById(R.id.picture_image);

			ViewHolder holder = new ViewHolder();

			holder.image = image;
			convertView.setTag(holder);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();

			image = holder.image;

		}
		BitmapFactory.Options option = new BitmapFactory.Options();
		Bitmap bm = BitmapFactory.decodeFile(list.get(arg0), option);
		image.setImageBitmap(bm);
		return convertView;
	}

	class ViewHolder {
		public ImageView image;
	}
}
