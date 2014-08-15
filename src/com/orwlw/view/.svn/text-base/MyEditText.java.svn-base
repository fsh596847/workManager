package com.orwlw.view;

import com.orwlw.activity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MyEditText extends LinearLayout
{
	private EditText mEditText;
	private Button bAdd;
	private Button bReduce;

	private LayoutInflater inflater;
	private LinearLayout headView;

	public MyEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init_widget(context);
		addListener();
	}

	public void init_widget(Context context)
	{
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(R.layout.myedittext, null);

		mEditText = (EditText) headView.findViewById(R.id.editText1);
		bAdd = (Button) headView.findViewById(R.id.button1);
		bReduce = (Button) headView.findViewById(R.id.button1);
		mEditText.setText("1");
	}

	public void addListener()
	{
		bAdd.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				int num = Integer.valueOf(mEditText.getText().toString());
				num++;
				mEditText.setText(Integer.toString(num));
			}
		});

		bReduce.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				int num = Integer.valueOf(mEditText.getText().toString());
				num--;
				mEditText.setText(Integer.toString(num));
			}
		});
	}

}
