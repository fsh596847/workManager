package com.orwlw.view;

import java.util.ArrayList;
import java.util.List;

import com.orwlw.activity.R;
import com.orwlw.dal.EnumDAL;
import com.orwlw.model.EnumModel;

import android.R.integer;
import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class my_view_selectitem extends LinearLayout implements OnClickListener {

	Button btnnext;
	Button btnprevious;
	TextView txtTextView;
	String current_code;
	List<EnumModel> list = new ArrayList<EnumModel>();
	int falg = 0;

	public my_view_selectitem(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.mycontrol_selecteitem, this);
		btnnext = (Button) findViewById(R.id.btnnext);
		btnprevious = (Button) findViewById(R.id.btnprevious);
		txtTextView = (TextView) findViewById(R.id.txt_reason);

		btnnext.setOnClickListener(this);
		btnprevious.setOnClickListener(this);

		list = EnumDAL.getEnum(context, "100016");
		if (list.size() > 0) {
			txtTextView.setText(list.get(0).KEY_VALUE);
			current_code = list.get(0).KEY_CODE;
		}
	}

	public my_view_selectitem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.mycontrol_selecteitem, this);
		btnnext = (Button) findViewById(R.id.btnnext);
		btnprevious = (Button) findViewById(R.id.btnprevious);
		txtTextView = (TextView) findViewById(R.id.txt_reason);

		btnnext.setOnClickListener(this);
		btnprevious.setOnClickListener(this);

		list = EnumDAL.getEnum(context, "REASON");
		if (list.size() > 0) {
			txtTextView.setText(list.get(0).KEY_VALUE);
			current_code = list.get(0).KEY_CODE;
		}

	}

	@Override
	public void onClick(View v) {
		if (v == btnnext) {
			next();
		}
		if (v == btnprevious) {
			previous();
		}

	}

	void next() {
		try {
			if (list != null) {
				if ((falg + 1) <= (list.size() - 1)) {
					falg++;
					txtTextView.setText(list.get(falg).KEY_VALUE);
					current_code = list.get(falg).KEY_CODE;
				}
			}
		} catch (Exception e) {
			Log.e("œ¬∑≠¥ÌŒÛ", "" + e.getMessage());
		}

	}

	void previous() {
		try {
			if (list != null) {
				if ((falg - 1) <= (list.size() - 1) && falg > -1) {
					falg--;
					txtTextView.setText(list.get(falg).KEY_VALUE);
					current_code = list.get(falg).KEY_CODE;
				}
			}
		} catch (Exception e) {
			Log.e("…œ∑≠¥ÌŒÛ", "" + e.getMessage());
		}

	}

	public String getKeyCode() {
		return current_code;
	}

	public String getKeyName() {
		return txtTextView.getText().toString();
	}

	public void setKeyCode(String keycode) {
		current_code = keycode;
	}

	public void setKeyName(String keyname) {
		txtTextView.setText(keyname);
	}

}
