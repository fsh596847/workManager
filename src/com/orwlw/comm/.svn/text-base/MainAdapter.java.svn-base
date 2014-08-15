package com.orwlw.comm;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orwlw.activity.R;
import com.orwlw.model.QuestionsBean;

public class MainAdapter extends BaseAdapter {
	private List<QuestionsBean> list;
	private Context context;


	public MainAdapter(Context context, List<QuestionsBean> list) {
		this.context = context;
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
		QuestionsBean item = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.main_item, null);
//			TextView tv = (TextView) convertView.findViewById(R.id.tijiao);
			TextView textView = (TextView) convertView.findViewById(R.id.timu);
			textView.setText(position + "." + item.getF_ITEM_NAME());
			
		}
		return convertView;
	}
}
