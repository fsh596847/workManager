package com.orwlw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SyncActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sync_data);
	}

	public void login_back(View v)
	{ // ������ ���ذ�ť
		this.finish();
	}

	// ��ʼ��������
	void syncbtn_start(View view)
	{

	}
}
