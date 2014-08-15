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
 * @author HYJ 2013-1-25 选择商品数量
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
		case Constants.ONLINE_ORDER_ACTION_ADD:// 新增订单商品
			btnButton.setText("加入订单");
			break;
		case Constants.SHOP_STORE_ACTION_ADD:// 新增库存商品
			LinearLayout3.setVisibility(0);
			btnButton.setText("加入库存单");
			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_ADD:// 新增调换的旧商品
			LinearLayout4.setVisibility(0);// **显示调换原因
			btnButton.setText("确定");
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_ADD:// 新增调换的新商品
			// LinearLayout4.setVisibility(0);// **显示调换原因
			btnButton.setText("确定");
			break;
		case Constants.BACK_COMMS_ACTION_ADD:// 新增退货商品
			LinearLayout4.setVisibility(0);// **显示调换原因
			btnButton.setText("确定");
			break;
		case Constants.ONLINE_ORDER_ACTION_UPDATE:// 修改订单已选商品
			btnButton.setText("确定");
			break;
		case Constants.SHOP_STORE_ACTION_UPDATE:// 修改库存已选商品
			LinearLayout3.setVisibility(0);
			btnButton.setText("确定");
			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_UPDATE:// 修改调换的旧商品
			LinearLayout4.setVisibility(0);// **显示调换原因
			btnButton.setText("确定");
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_UPDATE:// 修改调换的新商品
			btnButton.setText("确定");
			break;
		case Constants.BACK_COMMS_ACTION_UPDATE:// 修改退货的商品
			LinearLayout4.setVisibility(0);// **显示调换原因
			btnButton.setText("确定");
			break;

		default:
			break;
		}
	}

	/**
	 * @Title: initData
	 * @Description:TODO 初始化数据
	 * @author WangMin
	 * @date 2013-2-27 下午05:21:30设定文件
	 * @return void 返回类型
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
		case Constants.ONLINE_ORDER_ACTION_ADD:// 新增订单商品

			break;
		case Constants.SHOP_STORE_ACTION_ADD:// 新增库存商品

			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_ADD:// 新增调换的旧商品

			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_ADD:// 新增调换的新商品

			break;
		case Constants.BACK_COMMS_ACTION_ADD:// 新增退货商品

			break;
		case Constants.ONLINE_ORDER_ACTION_UPDATE:// 修改订单已选商品
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);
			break;
		case Constants.SHOP_STORE_ACTION_UPDATE:// 修改库存已选商品
			LinearLayout3.setVisibility(0);
			btnButton.setText("确定");
			view_time.SetView_Time(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_DATE);
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);

			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_UPDATE:// 修改调换的旧商品
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);
			view_selectitem.setKeyCode(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_REASON_ID);
			view_selectitem.setKeyCode(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_REASON);

			// 加载退货原因
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_UPDATE:// 修改调换的新商品
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);
			break;
		case Constants.BACK_COMMS_ACTION_UPDATE:// 修改退货的商品
			etBox.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM1);
			etBag.setText(((MyApplication) getApplication()).Getlocaldata().CURRENT_COMM_NUM2);
			view_selectitem.setKeyCode(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_REASON_ID);
			view_selectitem.setKeyCode(((MyApplication) getApplication())
					.Getlocaldata().CURRENT_COMM_REASON);
			// 加载退货原因
			break;

		default:
			break;

		}
		if (ProductDAL.isDataExist(getApplication(), tvCommCode.getText()
				.toString()))
			checkBox.setChecked(true);
	}

	public void login_back(View v) {
		// 标题栏 返回按钮
		this.finish();
	}

	/**
	 * @Title: onBoxMinus
	 * @Description:TODO 件数减少按钮事件
	 * @author WangMin
	 * @date 2013-2-27 下午01:19:58设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void onBoxMinus(View v) {
		setNum(etBox, MINUS);
	}

	/**
	 * @Title: onBoxAdd
	 * @Description:TODO 件数增加按钮事件
	 * @author WangMin
	 * @date 2013-2-27 下午01:19:58设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void onBoxAdd(View v) {
		setNum(etBox, ADD);
	}

	/**
	 * @Title: onBagMinus
	 * @Description:TODO 个数减少按钮事件
	 * @author WangMin
	 * @date 2013-2-27 下午01:19:58设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void onBagMinus(View v) {
		setNum(etBag, MINUS);
	}

	/**
	 * @Title: onBagMinus
	 * @Description:TODO 个数增加按钮事件
	 * @author WangMin
	 * @date 2013-2-27 下午01:19:58设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void onBagAdd(View v) {
		setNum(etBag, ADD);
	}

	/**
	 * @Title: setNum
	 * @Description:TODO 通过加减按钮控制件数编辑框和个数编辑框的数量
	 * @author WangMin
	 * @date 2013-2-27 下午03:45:30
	 * @param et
	 * @param operat设定文件
	 * @return void 返回类型
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
	 * @Description:TODO 按钮点击事件
	 * @author WangMin
	 * @date 2013-2-28 上午10:05:57
	 * @param v设定文件
	 * @return void 返回类型
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
		// MyApplication.ShowLog(getApplicationContext(), "请输入正确的数量!");
		// return;
		// }

		if (order_model != null) {
			order_model.F_NUM1 = strBox;
			order_model.F_NUM2 = strBag;
		}
		switch (tempintent) {
		case Constants.ONLINE_ORDER_ACTION_ADD:// 新增订单商品
			Order_Local_DAL.Insert(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					OrderActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.SHOP_STORE_ACTION_ADD:// 新增库存商品
			order_model.F_DATE = view_time.getView_Time();// 生产日期
			Order_Local_DAL.Insert_Store(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					ShopStoreActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_ADD:// 新增调换的旧商品
			order_model.F_REASON_ID = view_selectitem.getKeyCode();// **加载调货原因
			order_model.F_REASON = view_selectitem.getKeyName();
			ExchangeBackDAL.Exchange_Insert_old(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					exchange_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_ADD:// 新增调换的新商品
			ExchangeBackDAL.Exchange_Insert_new(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					exchange_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.BACK_COMMS_ACTION_ADD:// 新增退货商品
			order_model.F_REASON_ID = view_selectitem.getKeyCode();// **加载调货原因
			order_model.F_REASON = view_selectitem.getKeyName();
			ExchangeBackDAL.Back_Insert(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					back_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.ONLINE_ORDER_ACTION_UPDATE:// 修改订单已选商品
			Order_Local_DAL
					.UpdateLocal_Order_Num(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					OrderActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.SHOP_STORE_ACTION_UPDATE:// 修改库存已选商品
			order_model.F_DATE = view_time.getView_Time();// 生产日期
			Order_Local_DAL
					.UpdateLocal_Store_Num(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					ShopStoreActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.EXCHANGE_COMMSOLD_ACTION_UPDATE:// 修改调换的旧商品
			order_model.F_REASON_ID = view_selectitem.getKeyCode();// **加载调货原因
			order_model.F_REASON = view_selectitem.getKeyName();
			ExchangeBackDAL
					.UpdateLocal_Comms_old(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					exchange_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.EXCHANGE_COMMSNEW_ACTION_UPDATE:// 修改调换的新商品
			ExchangeBackDAL
					.UpdateLocal_Comms_new(getApplication(), order_model);
			intent = new Intent(ProductEditNumActivity.this,
					exchange_commsActivity.class);
			startActivity(intent);
			finish();
			break;
		case Constants.BACK_COMMS_ACTION_UPDATE:// 修改退货的商品
			order_model.F_REASON_ID = view_selectitem.getKeyCode();// **加载调货原因
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
