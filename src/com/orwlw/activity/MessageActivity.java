package com.orwlw.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.client.Constants;

import com.orwlw.dal.PushDataDAL;

import com.orwlw.model.PushDataModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author HYJ 2013-1-25 我的信息
 */
public class MessageActivity extends Activity {

	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<PushDataModel> mDataArrays = new ArrayList<PushDataModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_message_view);

		mListView = (ListView) findViewById(R.id.listview);
		initData();
	}

	public void initData() {

		PushDataDAL pushdata = new PushDataDAL(getApplication());
		mDataArrays = pushdata.getpushdatalist();
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
	}

	public void _back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	class ChatMsgViewAdapter extends BaseAdapter {
		private List<PushDataModel> coll;
		private Context ctx;

		private LayoutInflater mInflater;

		public ChatMsgViewAdapter(Context context, List<PushDataModel> coll) {
			ctx = context;
			this.coll = coll;
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return coll.size();
		}

		public Object getItem(int position) {
			return coll.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			PushDataModel entity = coll.get(position);

			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);

				viewHolder = new ViewHolder();
				viewHolder.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_sendtime);
				viewHolder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_chatcontent);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final String messagecontent = entity.message;
			final String data_id = entity.data_id;
			viewHolder.tvSendTime.setText(entity.created_date);
			viewHolder.tvContent.setText(entity.send_name);
			viewHolder.tvContent.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MessageActivity.this,
							BrowseActivity.class);
					intent.putExtra(Constants.NOTIFICATION_MESSAGE,
							messagecontent);
					intent.putExtra(Constants.NOTIFICATION_DATA_ID,
							data_id);
					
					startActivity(intent);
				}
			});
			viewHolder.tvContent
					.setOnLongClickListener(new View.OnLongClickListener() {

						@Override
						public boolean onLongClick(View arg0) {
							Dialog dialog = new AlertDialog.Builder(
									MessageActivity.this)
									.setMessage("删除该信息")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													PushDataDAL pushdata = new PushDataDAL(
															getApplication());
													pushdata.Delete(data_id);
													initData();
												}

											})
									.setNegativeButton(
											"取消",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													dialog.cancel();
												}
											}).create();
							dialog.show();

							return false;
						}
					});
			return convertView;
		}

		class ViewHolder {
			public TextView tvSendTime;
			public TextView tvContent;
		}

	}
}
