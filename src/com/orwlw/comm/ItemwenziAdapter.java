package com.orwlw.comm;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.orwlw.activity.R;
import com.orwlw.model.QuestionsBean;

/*Œƒ◊÷Ã‚ƒø  ≈‰∆˜*/
public class ItemwenziAdapter extends BaseAdapter {
	private QuestionsBean questionsBean;
	private Context context;

	public ItemwenziAdapter(QuestionsBean questionsBean, Context context) {
		this.questionsBean = questionsBean;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		EditText editText = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.wenzi,
					null);
			editText = (EditText) convertView.findViewById(R.id.wenzi_textview);
		}
		return convertView;
	}
	

}
