package com.orwlw.comm;

import java.lang.reflect.Field;
import java.util.List;

import com.orwlw.activity.R;
import com.orwlw.model.FunctionModel;
import com.orwlw.model.Localdata;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FunctionAdapter extends BaseAdapter {
	private Context context;
	public List<FunctionModel> li;

	public FunctionAdapter(Context context, List<FunctionModel> li) {
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
			view = inflater.inflate(R.layout.box_function, null);
			view.setTag(wrapper);
			view.setPadding(15, 15, 15, 15); // 每格的间距
		} else {
			wrapper = (ImgTextWrapper) view.getTag();
		}

		wrapper.imageView = (ImageView) view
				.findViewById(R.id.MainActivityImage);
		String ico = "";
		for (int i = 0; i < Constants.FuncImages.length; i++) {
			if (Constants.FuncImages[i][0]
					.equalsIgnoreCase(li.get(position).Functionname)) {
				ico = Constants.FuncImages[i][1];
			}
		}
		try {
			if (!ico.equalsIgnoreCase("")) {
				Field f = (Field) R.drawable.class.getDeclaredField(ico);
				int i = f.getInt(R.drawable.class);
				wrapper.imageView.setImageResource(i);
			} else {
				wrapper.imageView
						.setImageResource(R.drawable.mini_avatar_shadow);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// wrapper.imageView.setBackgroundResource(li.get(position).image);
		wrapper.textView = (TextView) view.findViewById(R.id.MainActivityText);
		wrapper.textView.setText(li.get(position).Functionname);

		// 考勤设置状态
		if (li.get(position).Functionname
				.equalsIgnoreCase(Constants.ATTEND_FUNCTION)) {
			Localdata localdata = ((MyApplication) context
					.getApplicationContext()).Getlocaldata();
			if (localdata.current_custom_attended.equalsIgnoreCase("1")) {
				// 已签到
				wrapper.imageView.setImageResource(R.drawable.arrived);
			} else if (localdata.current_custom_attended.equalsIgnoreCase("2")) {
				// 已签退
				wrapper.imageView.setImageResource(R.drawable.quit);
			} else {
				// 未签到
				wrapper.imageView.setImageResource(R.drawable.goattend);
			}
		}
		return view;

	}

	class ImgTextWrapper {
		ImageView imageView;
		TextView textView;
	}
}
