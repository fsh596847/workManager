package com.orwlw.comm;

import java.util.List;

import com.orwlw.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Item_CommAdapter extends BaseAdapter {
	private List<String> list_comm;
	private Context context;

	public Item_CommAdapter(List<String> list_comm, Context context) {
		this.list_comm = list_comm;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_comm.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list_comm.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			arg1 = LayoutInflater.from(context).inflate(R.layout.item_comm,
					null);

		}
		TextView tv_comm = (TextView) arg1.findViewById(R.id.item_comm_tv);
		tv_comm.setText(list_comm.get(arg0));
		return arg1;
	}

}
