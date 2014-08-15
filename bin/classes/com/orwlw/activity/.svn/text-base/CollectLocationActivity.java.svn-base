package com.orwlw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author HYJ 2013-1-25 采集客户位置
 */
public class CollectLocationActivity extends Activity
{
	Button button;
	TextView textView;
	ProgressBar pb;
	Button collect_btncancle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collectlocation);

		button = (Button) findViewById(R.id.collect_btn);
		textView = (TextView) findViewById(R.id.collect_view);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		collect_btncancle = (Button) findViewById(R.id.collect_btncancle);

		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				pb.setVisibility(8);
				button.setVisibility(0);
				collect_btncancle.setVisibility(0);
				textView.setText("精确位置:\n南中环企联大厦");
				// Toast.makeText(getApplicationContext(), "登录成功",
				// Toast.LENGTH_SHORT).show();
			}
		}, 2000);
	}

	public void collect(View view)
	{
		finish();
	}

	public void cancle(View view)
	{
		finish();
	}
}
