package com.orwlw.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;
import com.orwlw.dal.ExchangeBackDAL;
import com.orwlw.dal.Order_Local_DAL;
import com.orwlw.dal.ProductDAL;
import com.orwlw.model.Order_Model;
import com.orwlw.view.my_view_selectitem;
import com.orwlw.view.my_view_time;

/**
 * @author HYJ 2013-1-25 ѡ����Ʒ����
 */
public class ProductEditNumActivity extends Activity {

	public static final String ADD = "add";
	public static final String MINUS = "minus";

	Intent intent;
	Button btnButton;
	CheckBox checkBox;
	EditText etBox, etBag;
	TextView tvCommName, tvCommCode;
	int num, tempintent;
	Order_Model order_model;
	LinearLayout LinearLayout3;
	LinearLayout LinearLayout4;
	my_view_time view_time;
	my_view_selectitem view_selectitem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.product_edit_num_view);
		initview();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initview() {
		btnButton = (Button) findViewById(R.id.sub_btn);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		etBox = (EditText) findViewById(R.id.editText1);
		etBag = (EditText) findViewById(R.id.editText2);
		tvCommCode = (TextView) findViewById(R.id.card_comm_code);
		tvCommName = (TextView) findViewById(R.id.card_comm_name);
		tempintent = ((MyApplication) getApplication()).Getlocaldata().CURRENT_PARAMS;
		LinearLayout3 = (LinearLayout) findViewById(R.id.LinearLayout3);
		LinearLayout4 = (LinearLayout) findViewById(R.id.LinearLayout4);
		view_time = (my_view_time) findViewById(R.id.myview_time);
		view_selectitem = (my_view_selectitem) findViewById(R.id.myview_selectitem);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					if (!ProductDAL.isDataExist(getApplication(), tvCommCode
							.getText().toString())) {
						Map<String, Object> itemMap = new HashMap<String, Object>();
						itemMap.put("F_COMM_CODE", tvCommCode.getText()
								.toString());
						List<Map<String, Object>> listHash = new ArrayList<Map<String, Object>>();
						listHash.add(itemMap);
						ProductDAL.addData(getApplication(), "T_COMM_USED",
								listHash);
					}
				} else {
					ProductDAL.deleteData(getApplication(), tvCommCode
							.getText().toString());
				}
			}
		});
		switch (tempintent) {
		case Constants.ONLINE_ORDER_ACTION_ADD:// ����������Ʒ
			btnButton.setText("���붩��");
			break;
		case Constants.SHOP_STORE_ACTION_ADD:// ���������Ʒ
			LinearLayout3.setVisibility(0);
			btnButton.setText("�����浥");
			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_ADD:// ���������ľ���Ʒ
			LinearLayout4.setVisibility(0);// **��ʾ����ԭ��
			btnButton.setText("ȷ��");
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_ADD:// ��������������Ʒ
			// LinearLayout4.setVisibility(0);// **��ʾ����ԭ��
			btnButton.setText("ȷ��");
			break;
		case Constants.BACK_COMMS_ACTION_ADD:// �����˻���Ʒ
			LinearLayout4.setVisibility(0);// **��ʾ����ԭ��
			btnButton.setText("ȷ��");
			break;
		case Constants.ONLINE_ORDER_ACTION_UPDATE:// �޸Ķ�����ѡ��Ʒ
			btnButton.setText("ȷ��");
			break;
		case Constants.SHOP_STORE_ACTION_UPDATE:// �޸Ŀ����ѡ��Ʒ
			LinearLayout3.setVisibility(0);
			btnButton.setText("ȷ��");
			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_UPDATE:// �޸ĵ����ľ���Ʒ
			LinearLayout4.setVisibility(0);// **��ʾ����ԭ��
			btnButton.setText("ȷ��");
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_UPDATE:// �޸ĵ���������Ʒ
			btnButton.setText("ȷ��");
			break;
		case Constants.BACK_COMMS_ACTION_UPDATE:// �޸��˻�����Ʒ
			LinearLayout4.setVisibility(0);// **��ʾ����ԭ��
			btnButton.setText("ȷ��");
			break;

		default:
			break;
		}
	}

	/**
	 * @Title: initData
	 * @Description:TODO ��ʼ������
	 * @author WangMin
	 * @date 2013-2-27 ����05:21:30�趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void initData() {
		order_model = new Order_Model();
		order_model.F_COMM_CODE = ((MyApplication) getApplication())
				.Getlocaldata().CURRENT_COMM_CODE;
		order_model.F_COMM_NAME = ((MyApplication) getApplication())
				.Getlocaldata().CURRENT_COMM_NAME;
		order_model.F_CUSTOM_CODE = ((MyApplication) getApplication())
				.Getlocaldata().current_custom_code;
		tvCommCode.setText(order_model.F_COMM_CODE);
		tvCommName.setText(order_model.F_COMM_NAME);
		switch (tempintent) {
		case Constants.ONLINE_ORDER_ACTION_ADD:// ����������Ʒ

			break;
		case Constants.SHOP_STORE_ACTION_ADD:// ���������Ʒ

			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_ADD:// ���������ľ���Ʒ

			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_ADD:// ��������������Ʒ

			break;
		case Constants.BACK_COMMS_ACTION_ADD:// �����˻���Ʒ

			break;
		case Constants.ONLINE_ORDER_ACTION_UPDATE:// �޸Ķ�����ѡ��Ʒ
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);
			break;
		case Constants.SHOP_STORE_ACTION_UPDATE:// �޸Ŀ����ѡ��Ʒ
			LinearLayout3.setVisibility(0);
			btnButton.setText("ȷ��");
			view_time.SetView_Time(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_DATE);
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);

			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_UPDATE:// �޸ĵ����ľ���Ʒ
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);
			view_selectitem.setKeyCode(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_REASON_ID);
			view_selectitem.setKeyCode(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_REASON);

			// �����˻�ԭ��
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_UPDATE:// �޸ĵ���������Ʒ
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);
			break;
		case Constants.BACK_COMMS_ACTION_UPDATE:// �޸��˻�����Ʒ
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);
			view_selectitem.setKeyCode(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_REASON_ID);
			view_selectitem.setKeyCode(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_REASON);
			// �����˻�ԭ��
			break;

		default:
			break;

		}
		if (ProductDAL.isDataExist(getApplication(), tvCommCode.getText()
				.toString()))
			checkBox.setChecked(true);
	}

	public void login_back(View v) {
		// ������ ���ذ�ť
		this.finish();
	}

	/**
	 * @Title: onBoxMinus
	 * @Description:TODO �������ٰ�ť�¼�
	 * @author WangMin
	 * @date 2013-2-27 ����01:19:58�趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void onBoxMinus(View v) {
		setNum(etBox, MINUS);
	}

	/**
	 * @Title: onBoxAdd
	 * @Description:TODO �������Ӱ�ť�¼�
	 * @author WangMin
	 * @date 2013-2-27 ����01:19:58�趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void onBoxAdd(View v) {
		setNum(etBox, ADD);
	}

	/**
	 * @Title: onBagMinus
	 * @Description:TODO �������ٰ�ť�¼�
	 * @author WangMin
	 * @date 2013-2-27 ����01:19:58�趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void onBagMinus(View v) {
		setNum(etBag, MINUS);
	}

	/**
	 * @Title: onBagMinus
	 * @Description:TODO �������Ӱ�ť�¼�
	 * @author WangMin
	 * @date 2013-2-27 ����01:19:58�趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void onBagAdd(View v) {
		setNum(etBag, ADD);
	}

	/**
	 * @Title: setNum
	 * @Description:TODO ͨ���Ӽ���ť���Ƽ����༭��͸����༭�������
	 * @author WangMin
	 * @date 2013-2-27 ����03:45:30
	 * @param et
	 * @param operat�趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void setNum(EditText et, String operat) {
		String strNum = et.getText().toString().equalsIgnoreCase("") ? "0" : et.getText()
				.toString();
		num = Integer.parseInt(strNum);
		if (operat.equals(ADD))
			num++;
		else if (operat.equals(MINUS))
			num--;
		if (num > 0)
			et.setText(num + "");
		else
			et.setText("");
	}

	/**
	 * @Title: onSubButton
	 * @Description:TODO ��ť����¼�
	 * @author WangMin
	 * @date 2013-2-28 ����10:05:57
	 * @param v�趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void onSubButton(View v) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String strBox = etBox.getText().toString().trim().equalsIgnoreCase("")? "0"
				: etBox.getText().toString();
		String strBag = etBag.getText().toString().trim().equalsIgnoreCase("") ? "0"
				: etBag.getText().toString();
		String cus_code = ((MyApplication) getApplication()).Getlocaldata().current_custom_code;

		// if(strBox.equalsIgnoreCase("0")&&strBag.equalsIgnoreCase("0"));
		// {
		// MyApplication.ShowLog(getApplicationContext(), "��������ȷ������!");
		// return;
		// }

		if (order_model != null) {
			order_model.F_NUM1 = strBox;
			order_model.F_NUM2 = strBag;
		}
		switch (tempintent) {
		case Constants.ONLINE_ORDER_ACTION_ADD:// ����������Ʒ
			Order_Local_DAL.Insert(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					OrderActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.SHOP_STORE_ACTION_ADD:// ���������Ʒ
			order_model.F_DATE = view_time.getView_Time();// ��������
			Order_Local_DAL.Insert_Store(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					ShopStoreActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_ADD:// ���������ľ���Ʒ
			order_model.F_REASON_ID = view_selectitem.getKeyCode();// **���ص���ԭ��
			order_model.F_REASON = view_selectitem.getKeyName();
			ExchangeBackDAL.Exchange_Insert_old(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					exchange_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_ADD:// ��������������Ʒ
			ExchangeBackDAL.Exchange_Insert_new(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					exchange_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.BACK_COMMS_ACTION_ADD:// �����˻���Ʒ
			order_model.F_REASON_ID = view_selectitem.getKeyCode();// **���ص���ԭ��
			order_model.F_REASON = view_selectitem.getKeyName();
			ExchangeBackDAL.Back_Insert(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					back_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.ONLINE_ORDER_ACTION_UPDATE:// �޸Ķ�����ѡ��Ʒ
			Order_Local_DAL
					.UpdateLocal_Order_Num(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					OrderActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.SHOP_STORE_ACTION_UPDATE:// �޸Ŀ����ѡ��Ʒ
			order_model.F_DATE = view_time.getView_Time();// ��������
			Order_Local_DAL
					.UpdateLocal_Store_Num(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					ShopStoreActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_UPDATE:// �޸ĵ����ľ���Ʒ
			order_model.F_REASON_ID = view_selectitem.getKeyCode();// **���ص���ԭ��
			order_model.F_REASON = view_selectitem.getKeyName();
			ExchangeBackDAL
					.UpdateLocal_Comms_old(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					exchange_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_UPDATE:// �޸ĵ���������Ʒ
			ExchangeBackDAL
					.UpdateLocal_Comms_new(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					exchange_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.BACK_COMMS_ACTION_UPDATE:// �޸��˻�����Ʒ
			order_model.F_REASON_ID = view_selectitem.getKeyCode();// **���ص���ԭ��
			order_model.F_REASON = view_selectitem.getKeyName();
			ExchangeBackDAL.UpdateLocal_Back_Comms(getApplication(),
					order_model);
			intent = new Intent(ProductEditNumActivity.this,
					back_commsActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;

		}
	}
}
