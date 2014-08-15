package com.orwlw.activity;

import java.util.ArrayList;
import java.util.List;

import com.orwlw.model.FunctionModel;

import com.orwlw.activity.R;
import com.orwlw.comm.FunctionAdapter;
import com.orwlw.dal.FunctionDAL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author HYJ 2013-1-25 个人中心
 */
public class CenterViewActivity extends BaseActivity implements
		OnItemClickListener {
	GridView MainActivityGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.center);
		MainActivityGrid = (GridView) findViewById(R.id.MainActivityGrid);

		List<FunctionModel> list = new ArrayList<FunctionModel>();

		list = FunctionDAL.getFUNCList(getApplication(), "2");
		MainActivityGrid.setOnItemClickListener(this);
		FunctionAdapter functionAdapter = new FunctionAdapter(this, list);
		MainActivityGrid.setAdapter(functionAdapter);

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String aa = ((FunctionAdapter) MainActivityGrid.getAdapter()).li
				.get(arg2).Functionintent;
		Intent intent = new Intent(aa);
		CenterViewActivity.this.startActivity(intent);
	}
}
