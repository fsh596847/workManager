package com.orwlw.comm;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orwlw.activity.ItemQuestionActivity;
import com.orwlw.activity.R;
import com.orwlw.model.CheckResultItem;
import com.orwlw.model.DataBean;
import com.orwlw.model.ItemNumber;
import com.orwlw.model.QuestionsBean;

/*数字题适配器*/
public class ItemNumberAdapter extends BaseAdapter {
	private QuestionsBean questionsBean;
	private Context context;
	private ItemQuestionActivity it;
	private List<DataBean> list_comm;// 商品+数量
	private Handler handler;
	private DataBean mDataBean;
//	private CheckResultItem checkResultItem;
//	private List<CheckResultItem> list;

	public ItemNumberAdapter(QuestionsBean questionsBean, Context context,
			List<DataBean> list_comm, ItemQuestionActivity it, Handler handler) {
		this.questionsBean = questionsBean;
		this.context = context;
		this.list_comm = list_comm;
		this.it = it;
		// this.list_img = list_img;
		this.handler = handler;
//		list = new ArrayList<CheckResultItem>();
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView textView = null;
		EditText editText = null;
		Button btn_photo = null;
		ImageView imageView = null;
		Button del = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.number,
					null);
			textView = (TextView) convertView
					.findViewById(R.id.number_Commodity_text);
			editText = (EditText) convertView
					.findViewById(R.id.number_Comm_edit);
			btn_photo = (Button) convertView
					.findViewById(R.id.number_Comm_photo);
			del = (Button) convertView.findViewById(R.id.number_delete);
			imageView = (ImageView) convertView.findViewById(R.id.number_image);
			ViewHolder holder = new ViewHolder();
			holder.btn_photo = btn_photo;
			holder.editText = editText;
			holder.textView = textView;
			holder.imageView = imageView;
			holder.del = del;
			holder.editText.setTag(position);
			holder.editText.addTextChangedListener(new CustTextWatch(holder) {

				@Override
				public void afterTextChanged(Editable s, ViewHolder holder) {
					int p = (Integer) holder.editText.getTag();
					cacheData(s.toString(), p);
				}

			});
			convertView.setTag(holder);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			btn_photo = holder.btn_photo;
			editText = holder.editText;
			textView = holder.textView;
			imageView = holder.imageView;
			del = holder.del;
			holder.editText.setTag(position);
		}
		del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list_comm.remove(position);
				((ItemNumberAdapter) it.listView.getAdapter())
						.notifyDataSetChanged();
			}
		});

		btn_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent4 = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				it.startActivityForResult(intent4, 2);

				Message msg = new Message();
				msg.what = 2;
				msg.arg1 = position;
				handler.sendMessage(msg);

			}
		});
		textView.setText(list_comm.get(position).getName());
		editText.setText(list_comm.get(position).getInfo());
		// 设置照片
		if (list_comm.get(position).getImage() != null) {
			BitmapFactory.Options option = new BitmapFactory.Options();
			option.inSampleSize = 2;
			Bitmap bm = BitmapFactory.decodeFile(list_comm.get(position)
					.getImage(), option);
			imageView.setImageBitmap(bm);

		} else {
			imageView.setImageBitmap(null);

		}

		return convertView;
	}

	public class ViewHolder {
		TextView textView;
		EditText editText;
		Button btn_photo;
		ImageView imageView;
		Button del;
	}

	void cacheData(String str, int p) {
		if (p > list_comm.size())
			return;
		mDataBean = list_comm.get(p);
		mDataBean.setInfo(str);
		list_comm.set(p, mDataBean);
        Message message=handler.obtainMessage();
        message.what=3;
        message.obj=list_comm;
        handler.sendMessage(message);
	}

	private abstract class CustTextWatch implements TextWatcher {
		private ViewHolder mViewHolder;

		public CustTextWatch(ViewHolder holder) {
			this.mViewHolder = holder;
		}

		@Override
		public void afterTextChanged(Editable s) {
			afterTextChanged(s, mViewHolder);
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		public abstract void afterTextChanged(Editable s, ViewHolder holder);
	}

}
