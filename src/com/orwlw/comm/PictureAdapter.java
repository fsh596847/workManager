package com.orwlw.comm;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orwlw.activity.R;
import com.orwlw.model.DataBeanToo;
import com.orwlw.model.QuestionsBean;
import com.orwlw.view.IconTextView;

public class PictureAdapter extends BaseAdapter {
	private List<DataBeanToo> list;
	private Context context;
	private QuestionsBean questionsBean;

	public PictureAdapter(List<DataBeanToo> list, Context context,
			QuestionsBean questionsBean) {
		this.list = list;
		this.context = context;
		this.questionsBean = questionsBean;
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		IconTextView icon = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.picture_item, null);
			icon = (IconTextView) convertView
					.findViewById(R.id.picture_IconTextView);
			ViewHolder holder = new ViewHolder();
			holder.icon = icon;
			convertView.setTag(holder);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			icon = holder.icon;

		}
		BitmapFactory.Options option = new BitmapFactory.Options();
		String path = list.get(arg0).getName();
		// int rote = readPictureDegree(path);
		Bitmap bm = BitmapFactory.decodeFile(list.get(arg0).getName(), option);
		// Bitmap new_bm = rotaingImageView(rote, bm);
		icon.setIcon(bm);

		try {
			String type = questionsBean.getF_ITEM_TYPE();

			if (list.get(arg0).getInfo() != null && !type.equals("0")) {
				String contet = list.get(arg0).getInfo();
				String[] item = contet.split("-");
				if (item[1] != null && !item[1].equals("")) {
					icon.setText("选项：" + item[0] + "\n" + "商品：" + item[1]);
				} else {
					icon.setText("选项：" + item[0] + "\n" + "商品：" + null);
				}
				contet = null;
			} else {
				icon.setText(null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		return convertView;
	}

	class ViewHolder {
		public IconTextView icon;
	}

	/*
	 * 获取照片旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/*
	 * 旋转照片
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		bitmap.recycle();
		return resizedBitmap;
	}

}
