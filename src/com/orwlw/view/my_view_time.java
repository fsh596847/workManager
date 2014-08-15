package com.orwlw.view;

import com.orwlw.activity.R;
import com.orwlw.comm.Constants;
import com.orwlw.view.SlipButton.OnChangedListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class my_view_time extends LinearLayout implements OnClickListener {

	Button btnyear_add;
	Button btnyear_Minus;
	Button btnmonth_add;
	Button btnmonth_Minus;
	Button btnday_add;
	Button btnday_Minus;

	EditText txt_year;
	EditText txt_month;
	EditText txt_day;

	public my_view_time(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public my_view_time(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_control_time, this);
		txt_year = (EditText) findViewById(R.id.txt_year);
		txt_month = (EditText) findViewById(R.id.txt_month);
		txt_day = (EditText) findViewById(R.id.txt_day);
		txt_year.setText(Constants.GetYear());
		txt_month.setText(Constants.GetMonth());
		txt_day.setText(Constants.GetDay());

		btnyear_add = (Button) findViewById(R.id.btnyear_add);
		btnyear_Minus = (Button) findViewById(R.id.btnyear_Minus);
		btnmonth_add = (Button) findViewById(R.id.btnmonth_add);
		btnmonth_Minus = (Button) findViewById(R.id.btnmonth_Minus);
		btnday_add = (Button) findViewById(R.id.btnday_add);
		btnday_Minus = (Button) findViewById(R.id.btnday_Minus);

		btnyear_add.setOnClickListener(this);
		btnyear_Minus.setOnClickListener(this);
		btnmonth_add.setOnClickListener(this);
		btnmonth_Minus.setOnClickListener(this);
		btnday_add.setOnClickListener(this);
		btnday_Minus.setOnClickListener(this);

		// TODO Auto-generated constructor stub
	}

	void onYearAdd() {
		String ss = txt_year.getText().toString();
		int num = Integer
				.parseInt(txt_year.getText().toString().equalsIgnoreCase("") ? "0"
						: txt_year.getText().toString());
		if (num++ > 0)
			txt_year.setText((num++) + "");
	}

	void onYearMinus() {
		int num = Integer
				.parseInt(txt_year.getText().toString().equalsIgnoreCase("") ? "0"
						: txt_year.getText().toString());
		if (num-- > 0)
			txt_year.setText((num--) + "");
	}

	void onMonthADD() {
		int num = Integer
				.parseInt(txt_month.getText().toString().equalsIgnoreCase("") ? "0"
						: txt_month.getText().toString());
		if (num++ < 13 || num++ > 0)
			txt_month.setText((num++) + "");
	}

	void onMonthMinus() {
		int num = Integer
				.parseInt(txt_month.getText().toString().equalsIgnoreCase("") ? "0"
						: txt_month.getText().toString());
		if (num-- < 13 || num-- > 0)
			txt_month.setText((num--) + "");
	}

	void onDayAdd() {
		int num = Integer.parseInt(txt_day.getText().toString().equalsIgnoreCase("") ? "0"
				: txt_day.getText().toString());
		if (num++ < 32 || num++ > 0)
			txt_day.setText((num++) + "");
	}

	void onDayMinus() {
		int num = Integer.parseInt(txt_day.getText().toString().equalsIgnoreCase("") ? "0"
				: txt_day.getText().toString());
		if (num-- < 32 || num-- > 0)
			txt_day.setText((num--) + "");
	}

	@Override
	public void onClick(View v) {
		if (v == btnyear_add)
			onYearAdd();
		if (v == btnyear_Minus)
			onYearMinus();
		if (v == btnmonth_add)
			onMonthADD();
		if (v == btnmonth_Minus)
			onMonthMinus();
		if (v == btnday_add)
			onDayAdd();
		if (v == btnday_Minus)
			onDayMinus();
	}

	public String getView_Time() {
		String result = txt_year.getText().toString() + "-"
				+ txt_month.getText().toString() + "-"
				+ txt_day.getText().toString();
		return result;
	}

	public void SetView_Time(String datestr) {
		String[] datesStrings = datestr.split("-");
		if (datesStrings != null) {
			if (datesStrings.length > 2) {
				txt_year.setText(datesStrings[0]);
				txt_month.setText(datesStrings[1]);
				txt_day.setText(datesStrings[2]);
			}
		}
	}

}
