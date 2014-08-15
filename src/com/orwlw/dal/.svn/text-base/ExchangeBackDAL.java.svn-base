package com.orwlw.dal;

import java.util.ArrayList;
import java.util.List;
import android.app.Application;
import android.database.Cursor;
import android.util.Log;
import com.orwlw.comm.DBHelper;
import com.orwlw.comm.MyApplication;
import com.orwlw.model.Order_Model;

/**
 * 调退货DAL
 * 
 * @author HYJ
 * 
 */
public class ExchangeBackDAL {

	// 新增要换的旧损的商品
	public static void Exchange_Insert_old(Application application,
			Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "insert into  T_EXCHAGNE_COMMS(F_CUSTOM_CODE,F_COMM_CODE,F_COMM_NAME,F_NUM1,F_NUM2,F_REASON,F_REASON_ID,F_TYPE) values('"
					+ model.F_CUSTOM_CODE
					+ "','"
					+ model.F_COMM_CODE
					+ "','"
					+ model.F_COMM_NAME
					+ "','"
					+ model.F_NUM1
					+ "','"
					+ model.F_NUM2
					+ "','"
					+ model.F_REASON
					+ "','"
					+ model.F_REASON_ID + "','1')";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			MyApplication.WriteLog("异常" + ex.getMessage());
		} finally {

		}
	}

	// 新增要换的新的商品
	public static void Exchange_Insert_new(Application application,
			Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "insert into  T_EXCHAGNE_COMMS(F_CUSTOM_CODE,F_COMM_CODE,F_COMM_NAME,F_NUM1,F_NUM2,F_REASON,F_REASON_ID,F_TYPE) values('"
					+ model.F_CUSTOM_CODE
					+ "','"
					+ model.F_COMM_CODE
					+ "','"
					+ model.F_COMM_NAME
					+ "','"
					+ model.F_NUM1
					+ "','"
					+ model.F_NUM2
					+ "','"
					+ model.F_REASON
					+ "','"
					+ model.F_REASON_ID + "','2')";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	// 新增要退货的商品
	public static void Back_Insert(Application application, Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "insert into  T_BACK_COMMS(F_CUSTOM_CODE,F_COMM_CODE,F_COMM_NAME,F_NUM1,F_NUM2,F_REASON,F_REASON_ID) values('"
					+ model.F_CUSTOM_CODE
					+ "','"
					+ model.F_COMM_CODE
					+ "','"
					+ model.F_COMM_NAME
					+ "','"
					+ model.F_NUM1
					+ "','"
					+ model.F_NUM2
					+ "','"
					+ model.F_REASON
					+ "','"
					+ model.F_REASON_ID + "')";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	// 删除要换的旧损的商品
	public static void DeleteLocal_ExchangeComms_old(Application application,
			Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "delete from T_EXCHAGNE_COMMS where F_CUSTOM_CODE='"
					+ model.F_CUSTOM_CODE + "' and F_COMM_CODE='"
					+ model.F_COMM_CODE + "' and F_TYPE='1'";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	// 删除要换的新的商品
	public static void DeleteLocal_ExchangeComms_new(Application application,
			Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "delete from T_EXCHAGNE_COMMS where F_CUSTOM_CODE='"
					+ model.F_CUSTOM_CODE + "' and F_COMM_CODE='"
					+ model.F_COMM_CODE + "' and F_TYPE='2'";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	// 删除单个客户已选的调换货商品
	public static void DeleteLocalExchangeComms_by_custom(
			Application application, String custom_code) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "delete from T_EXCHAGNE_COMMS where F_CUSTOM_CODE='"
					+ custom_code + "'";

			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	// 删除要退货的商品
	public static void DeleteLocal_BackComms(Application application,
			Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "delete from T_BACK_COMMS where F_CUSTOM_CODE='"
					+ model.F_CUSTOM_CODE + "' and F_COMM_CODE='"
					+ model.F_COMM_CODE + "'";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	// 删除单个客户已选的退货商品
	public static void DeleteLocalBackComms_by_custom(Application application,
			String custom_code) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "delete from T_BACK_COMMS where F_CUSTOM_CODE='"
					+ custom_code + "'";

			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	public static List<Order_Model> getold_commsby_custom_code(
			Application application, String custom_code) {
		List<Order_Model> list = new ArrayList<Order_Model>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_EXCHAGNE_COMMS where F_CUSTOM_CODE='"
					+ custom_code + "' and F_TYPE='1'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				Order_Model model = new Order_Model();
				int i = cursor.getColumnIndex("F_CUSTOM_CODE");
				model.F_CUSTOM_CODE = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_COMM_CODE");
				model.F_COMM_CODE = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_COMM_NAME");
				model.F_COMM_NAME = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_NUM1");
				model.F_NUM1 = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_NUM2");
				model.F_NUM2 = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_REASON");
				model.F_REASON = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_REASON_ID");
				model.F_REASON_ID = cursor.isNull(i) ? "" : cursor.getString(i);
				list.add(model);
			}
			cursor.close();
		} catch (Exception e) {

		} finally {
			dbHelper.close();
		}
		return list;
	}

	public static List<Order_Model> getnew_commsby_custom_code(
			Application application, String custom_code) {
		List<Order_Model> list = new ArrayList<Order_Model>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_EXCHAGNE_COMMS where F_CUSTOM_CODE='"
					+ custom_code + "' and F_TYPE='2'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				Order_Model model = new Order_Model();
				int i = cursor.getColumnIndex("F_CUSTOM_CODE");
				model.F_CUSTOM_CODE = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_COMM_CODE");
				model.F_COMM_CODE = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_COMM_NAME");
				model.F_COMM_NAME = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_NUM1");
				model.F_NUM1 = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_NUM2");
				model.F_NUM2 = cursor.isNull(i) ? "" : cursor.getString(i);
				list.add(model);
			}
			cursor.close();
		} catch (Exception e) {

		} finally {
			dbHelper.close();
		}
		return list;
	}

	public static List<Order_Model> getBack_commsby_custom_code(
			Application application, String custom_code) {
		List<Order_Model> list = new ArrayList<Order_Model>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_BACK_COMMS where F_CUSTOM_CODE='"
					+ custom_code + "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				Order_Model model = new Order_Model();
				int i = cursor.getColumnIndex("F_CUSTOM_CODE");
				model.F_CUSTOM_CODE = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_COMM_CODE");
				model.F_COMM_CODE = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_COMM_NAME");
				model.F_COMM_NAME = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_NUM1");
				model.F_NUM1 = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_NUM2");
				model.F_NUM2 = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_REASON");
				model.F_REASON = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_REASON_ID");
				model.F_REASON_ID = cursor.isNull(i) ? "" : cursor.getString(i);
				list.add(model);
			}
			cursor.close();
		} catch (Exception e) {

		} finally {
			dbHelper.close();
		}
		return list;
	}

	// 修改
	public static void UpdateLocal_Comms_old(Application application,
			Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_EXCHAGNE_COMMS set F_NUM1='" + model.F_NUM1
					+ "',F_NUM2='" + model.F_NUM2 + "',F_REASON='"
					+ model.F_REASON + "',F_REASON_ID='" + model.F_REASON_ID
					+ "' where F_CUSTOM_CODE='" + model.F_CUSTOM_CODE
					+ "' and F_COMM_CODE='" + model.F_COMM_CODE
					+ "' and F_TYPE='1'";
			dbHelper.ExecSql(sql);
		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {
		}
	}

	// 修改
	public static void UpdateLocal_Comms_new(Application application,
			Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_EXCHAGNE_COMMS set F_NUM1='" + model.F_NUM1
					+ "',F_NUM2='" + model.F_NUM2 + "' where F_CUSTOM_CODE='"
					+ model.F_CUSTOM_CODE + "' and F_COMM_CODE='"
					+ model.F_COMM_CODE + "' and F_TYPE='2'";
			dbHelper.ExecSql(sql);
		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {
		}
	}

	// 修改
	public static void UpdateLocal_Back_Comms(Application application,
			Order_Model model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_BACK_COMMS set F_NUM1='" + model.F_NUM1
					+ "',F_NUM2='" + model.F_NUM2 + "',F_REASON='"
					+ model.F_REASON + "',F_REASON_ID='" + model.F_REASON_ID
					+ "' where F_CUSTOM_CODE='" + model.F_CUSTOM_CODE
					+ "' and F_COMM_CODE='" + model.F_COMM_CODE + "'";
			dbHelper.ExecSql(sql);
		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {
		}
	}
}
