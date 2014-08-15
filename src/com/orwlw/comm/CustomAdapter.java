package com.orwlw.comm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings.System;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.orwlw.activity.CustomViewActivity;
import com.orwlw.activity.R;
import com.orwlw.model.PhotoModel;

public class CustomAdapter extends BaseAdapter {
	public Context context;
	public int temp = -1;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	View tempview;

	public CustomAdapter(Context context, List<Map<String, Object>> list1) {
		this.context = context;
		if (list1 != null) {
			this.list = list1;
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int aa = this.list.size();
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			// MyApplication.WriteLog("getView" + position);
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.list_custom_item, null);
				holder.txtcode = (TextView) convertView
						.findViewById(R.id.tv_code);
				holder.shop_image = (ImageView) convertView
						.findViewById(R.id.image_myExam_infoIcon);
				holder.txtname = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.txtaddress = (TextView) convertView
						.findViewById(R.id.text_myExam_content);
				holder.img_arrivde = (ImageView) convertView
						.findViewById(R.id.View_arrived);
				holder.img_quit = (ImageView) convertView
						.findViewById(R.id.View_quit);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Map<String, Object> valueMap = null;
			valueMap = this.list.get(position);
			String distance = "";
			// if (valueMap.get("Distance") != null) {
			// if (!(valueMap.get("Distance") + "").equalsIgnoreCase("0.0"))
			// distance = "[" + valueMap.get("Distance").toString() + "��]";
			// else {
			//
			// distance = "[δ֪]";
			// }
			//
			// }
			holder.txtcode.setText(valueMap.get("F_CUSTOM_CODE").toString());
			final String currentcode = valueMap.get("F_CUSTOM_CODE").toString();

			String custom_code = valueMap.get("F_CUSTOM_CODE").toString();
			File file = new File("/sdcard/WORK_PICTURE/" + custom_code + "/"
					+ custom_code + ".jpg");
			if (file.exists()) {
				String path = "/sdcard/WORK_PICTURE/" + custom_code + "/"
						+ custom_code + ".jpg";
				// holder.shop_image.setImageBitmap(Constants.scaleImg(BitmapFactory.decodeFile(path),
				// 50, 50));
				Bitmap bitmap = Constants.toRoundCorner(Constants.scaleImg(
						BitmapFactory.decodeFile(path), 50, 50), 5);
				holder.shop_image.setImageBitmap(bitmap);
			} else {
				holder.shop_image
						.setImageResource(R.drawable.mini_avatar_shadow);
				
			}

			holder.txtname.setText(valueMap.get("F_CUSTOM_NAME").toString()
					+ distance);

			holder.txtaddress.setText(valueMap.get("F_CO_ADDRESS").toString());
			// holder.img_isvivited = (ImageView) convertView
			// .findViewById(R.id.image_isvisited);
			// if ((valueMap.get("F_ISVISIT").toString()).equalsIgnoreCase("1"))
			// holder.img_isvivited.setVisibility(0);
			// else {
			// holder.img_isvivited.setVisibility(8);
			// }

			if ((valueMap.get("F_ATTENDED").toString()).equalsIgnoreCase("2")) {
				holder.img_arrivde.setVisibility(0);
				holder.img_quit.setVisibility(0);
			} else if ((valueMap.get("F_ATTENDED").toString())
					.equalsIgnoreCase("1")) {
				holder.img_arrivde.setVisibility(0);
			} else {
				holder.img_arrivde.setVisibility(8);
				holder.img_quit.setVisibility(8);
			}

			// convertView.setOnTouchListener(new ListView.OnTouchListener() {
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// // v.setBackgroundResource(R.drawable.bg_custom_item);
			// // if()
			// // tempview.setBackgroundResource(R.drawable.bg_list_item);
			// // tempview = v;
			// return false;
			// }
			// });
		} catch (Exception e) {
			MyApplication.WriteLog(e.getMessage());
		}
		return convertView;
	}

	private class Holder {
		TextView txtcode;
		TextView txtname;
		TextView txtaddress;
		ImageView img_isvivited;
		ImageView shop_image;
		ImageView img_arrivde;
		ImageView img_quit;
	}
}