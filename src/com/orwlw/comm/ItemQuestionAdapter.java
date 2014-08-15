package com.orwlw.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.orwlw.activity.ItemQuestionActivity;
import com.orwlw.activity.R;
import com.orwlw.activity.SlidingActivity;
import com.orwlw.model.QuestionsBean;
import com.orwlw.model.Radio_Statue;

/*单选适配器*/
public class ItemQuestionAdapter extends BaseAdapter {
	private QuestionsBean questionsBean;
	private Context context;
	private List<String> list;
	private ItemQuestionActivity iq;
	private Map<Integer, Boolean> map;
	RadioButton radiobutton = null;
	private List<Radio_Statue> list_bo;
	private Handler handler;

	public ItemQuestionAdapter(QuestionsBean questionsBean, Context context,
			List<String> list, ItemQuestionActivity iq, Handler handler) {
		this.questionsBean = questionsBean;
		this.context = context;
		this.list = list;
		this.iq = iq;
		this.handler = handler;
		// map = new HashMap<Integer, Boolean>();
		list_bo = new ArrayList<Radio_Statue>();
		for (int i = 0; i < list.size(); i++) {
			Radio_Statue statue = new Radio_Statue();
			// map.put(i, false);
			statue.setStaute(false);
			list_bo.add(statue);
		}

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

		TextView textView = null;
		Button btn_photo = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.radio,
					null);
			textView = (TextView) convertView.findViewById(R.id.radio_textview);
			radiobutton = (RadioButton) convertView.findViewById(R.id.radio);
			btn_photo = (Button) convertView.findViewById(R.id.radio_photo_add);
			ViewHolder holder = new ViewHolder();
			holder.textView = textView;
			holder.RadioButton = radiobutton;
			holder.btn_photo = btn_photo;
			convertView.setTag(holder);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			textView = holder.textView;
			radiobutton = holder.RadioButton;
			btn_photo = holder.btn_photo;
		}
		textView.setText(list.get(position));
		radiobutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ListView paView = (ListView) v.getParent().getParent();
				for (int i = 0; i < paView.getCount(); i++) {
					View aView = paView.getChildAt(i);
					RadioButton mButton = (RadioButton) aView
							.findViewById(R.id.radio);
					mButton.setChecked(false);
				}
				RadioButton mButton = (RadioButton) v.findViewById(R.id.radio);
				mButton.setChecked(true);
				// if (mButton.isChecked()) {
				// // 发送第几个选项
				// Message message = new Message();
				// message.what = 2;
				// message.obj = position;
				// handler.sendMessage(message);
				// System.out.println("执行");
				// }
				// 保存选中状态， map.put(position, radiobutton.isChecked());
				for (int i = 0; i < list.size(); i++) {
					if (i == position) {
						list_bo.get(i).setStaute(true);
					} else {
						list_bo.get(i).setStaute(false);
					}
				}

			}
		});
		// radiobutton.setChecked(false);
		radiobutton.setChecked(list_bo.get(position).isStaute());// 设置选择状态

		if (!questionsBean.isF_COMM_PHOTO_REALATION()) {
			btn_photo.setVisibility(View.GONE);
		}
		btn_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 发送选项
				Message message = new Message();
				message.what = 1;
				message.obj = list.get(position);
				handler.sendMessage(message);
				// 跳转商品页面
				((MyApplication) ((Activity) context).getApplication())
						.SavelocalInt("CURRENT_PARAMS",
								Constants.SUVERY_ACTION_SELECT_COMMS);
				Intent intent3 = new Intent(context, SlidingActivity.class);
				iq.startActivityForResult(intent3, 4);

			}
		});
		return convertView;
	}

	public class ViewHolder {
		TextView textView;
		RadioButton RadioButton;
		Button btn_photo;
	}
}
