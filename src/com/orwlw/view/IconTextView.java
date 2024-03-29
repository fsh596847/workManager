package com.orwlw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IconTextView extends LinearLayout {
	private TextView mText;
	private ImageView mIcon;

	public IconTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// 设置IconifiedTextView为水平线性布局
		this.setOrientation(VERTICAL);
		this.setGravity(Gravity.CENTER_VERTICAL);
		mIcon = new ImageView(context);
		// mIcon.setImageDrawable();
//		mIcon.setPadding(1, 1, 1, 1);
		addView(mIcon, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		// 添加TextView， 由于先添加ImageView后添加TextView，所以 ICON 在前 Text 在后。
		mText = new TextView(context);
		// mText.setText("我是文本我是文本我是文本我是文本");
		mText.setTextSize(17);
		mText.setPadding(1, 0, 2, 0);
		// mText.setGravity(CENTER_VERTICAL);
		addView(mText, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

	public void setText(String words) {
		// Log.i("test","setText "+words);
		mText.setText(words);
	}

	// 设置ImageView的Icon图标

	public void setIcon(Bitmap bullet) {
		// Log.i("test","setIcon ");

		mIcon.setImageBitmap(bullet);
		mIcon.setScaleType(ImageView.ScaleType.FIT_XY);

	}
}
