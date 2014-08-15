package com.orwlw.comm;

import java.util.List;

import android.R.menu;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.orwlw.activity.ItemQuestionActivity;
import com.orwlw.activity.R;
import com.orwlw.activity.SlidingActivity;
import com.orwlw.model.QuestionsBean;

/*单选适配器*/
public class ItemCheckAdapter extends BaseAdapter {
	private QuestionsBean questionsBean;
	private Context context;
	private List<String> list;
	private ItemQuestionActivity iq;
	private Handler handler;

	public ItemCheckAdapter(QuestionsBean questionsBean, Context context,
			List<String> list, ItemQuestionActivity iq, Handler handler) {
		this.questionsBean = questionsBean;
		this.context = context;
		this.list = list;
		this.iq = iq;
		this.handler = handler;
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CheckBox checkBox = null;
		Button btn_photo = null;
		TextView textView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.check,
					null);
			checkBox = (CheckBox) convertView.findViewById(R.id.check);
			textView = (TextView) convertView.findViewById(R.id.check_textView);
			btn_photo = (Button) convertView.findViewById(R.id.check_photo_add);
			ViewHolder holder = new ViewHolder();
			holder.textView = textView;
			holder.checkBox = checkBox;
			holder.btn_photo = btn_photo;
			convertView.setTag(holder);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			textView = holder.textView;
			checkBox = holder.checkBox;
			btn_photo = holder.btn_photo;
		}
		if (!questionsBean.isF_COMM_PHOTO_REALATION()) {
			btn_photo.setVisibility(View.GONE);
		}
		btn_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MyApplication) ((Activity) context).getApplication())
						.SavelocalInt("CURRENT_PARAMS",
								Constants.SUVERY_ACTION_SELECT_COMMS);
				Intent intent3 = new Intent(context, SlidingActivity.class);
				iq.startActivityForResult(intent3, 4);
			}
		});
		textView.setText(list.get(position));
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					System.out.println("选择内容:" + list.get(position));
					Message msg = new Message();
					msg.what=1;
					msg.obj = list.get(position);
					handler.sendMessage(msg);
				}
			}
		});
		return convertView;
	}

	public class ViewHolder {
		TextView textView;
		CheckBox checkBox;
		Button btn_photo;
	}

}
