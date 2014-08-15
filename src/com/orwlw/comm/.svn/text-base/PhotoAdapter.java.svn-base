package com.orwlw.comm;

import java.io.File;
import java.util.List;

import com.orwlw.activity.R;
import com.orwlw.model.FunctionModel;
import com.orwlw.model.PhotoModel;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoAdapter extends BaseAdapter {
	private Context context;
	public List<PhotoModel> li;

	public PhotoAdapter(Context context, List<PhotoModel> li) {
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
		ImgTextWrapper wrapper;
		if (view == null) {
			wrapper = new ImgTextWrapper();
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.box_photo, null);
			view.setTag(wrapper);
			view.setPadding(5, 5, 5, 5); // 每格的间距
		} else {
			wrapper = (ImgTextWrapper) view.getTag();
		}

		wrapper.imageView = (ImageView) view.findViewById(R.id.imageView1);

		if (li.get(position).image == null
				|| li.get(position).image.equalsIgnoreCase("")) {
			wrapper.imageView.setImageResource(R.drawable.mini_avatar_shadow);
		} else {
			File newfile = new File(li.get(position).image);
			if (newfile.exists()) {
				wrapper.imageView.setImageBitmap(Constants.scaleImg(
						BitmapFactory.decodeFile(li.get(position).image), 80,
						80));
			} else {
				wrapper.imageView.setImageResource(R.drawable.photeerror);
			}
		}
		wrapper.textView = (TextView) view.findViewById(R.id.textView1);
		wrapper.textView.setText(li.get(position).typename);

		wrapper.textView_remark = (TextView) view
				.findViewById(R.id.remark_TextView1);
		wrapper.textView_remark.setText(li.get(position).REMARK);

		return view;
	}

	class ImgTextWrapper {
		ImageView imageView;
		TextView textView;
		TextView textView_remark;
	}
}
