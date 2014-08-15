package com.orwlw.dal;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import com.orwlw.comm.DBHelper;
import com.orwlw.model.Order_Model;
import com.orwlw.model.PhotoModel;
import com.orwlw.model.PushDataModel;

public class Order_Local_DAL {
	public static List<Order_Model> getLocalOrderby_custom_code(
			Application application, String custom_code) {
		List<Order_Model> list = new ArrayList<Order_Model>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_LOCAL_ORDER where F_CUSTOM_CODE='"
					+ custom_code + "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				Order_Model ordermodel = new Order_Model();
				int i = cursor.getColumnIndex("F_CUSTOM_CODE");
				ordermodel.F_CUSTOM_CODE = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_COMM_CODE");
				ordermodel.F_COMM_CODE = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_COMM_NAME");
				ordermodel.F_COMM_NAME = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_NUM1");
				ordermodel.F_NUM1 = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_NUM2");
				ordermodel.F_NUM2 = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_DATE");
				ordermodel.F_DATE = cursor.isNull(i) ? "" : cursor.getString(i);

				list.add(ordermodel);
			}
			cursor.close();
		} catch (Exception e) {

		} finally {
			dbHelper.close();
		}
		return list;
	}

	public static void Insert(Application application, Order_Model order_model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "insert into  T_LOCAL_ORDER(F_CUSTOM_CODE,F_COMM_CODE,F_COMM_NAME,F_NUM1,F_NUM2) values('"
					+ order_model.F_CUSTOM_CODE
					+ "','"
					+ order_model.F_COMM_CODE
					+ "','"
					+ order_model.F_COMM_NAME
					+ "','"
					+ order_model.F_NUM1
					+ "','"
					+ order_model.F_NUM2
					+ "')";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	public static void Insert_Store(Application application,
			Order_Model order_model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "insert into  T_LOCAL_ORDER(F_CUSTOM_CODE,F_COMM_CODE,F_COMM_NAME,F_NUM1,F_NUM2,F_DATE) values('"
					+ order_model.F_CUSTOM_CODE
					+ "','"
					+ order_model.F_COMM_CODE
					+ "','"
					+ order_model.F_COMM_NAME
					+ "','"
					+ order_model.F_NUM1
					+ "','"
					+ order_model.F_NUM2
					+ "','" + order_model.F_DATE + "')";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	public static void UpdateLocal_Order_Num(Application application,
			Order_Model order_model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_LOCAL_ORDER set F_NUM1='"
					+ order_model.F_NUM1 + "',F_NUM2='" + order_model.F_NUM2
					+ "' where F_CUSTOM_CODE='" + order_model.F_CUSTOM_CODE
					+ "' and F_COMM_CODE='" + order_model.F_COMM_CODE + "'";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	public static void UpdateLocal_Store_Num(Application application,
			Order_Model order_model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_LOCAL_ORDER set F_NUM1='"
					+ order_model.F_NUM1 + "',F_NUM2='" + order_model.F_NUM2
					+ "',F_DATE='" + order_model.F_DATE
					+ "' where F_CUSTOM_CODE='" + order_model.F_CUSTOM_CODE
					+ "' and F_COMM_CODE='" + order_model.F_COMM_CODE + "'";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	public static void DeleteLocal_Order(Application application,
			Order_Model order_model) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "delete from T_LOCAL_ORDER where F_CUSTOM_CODE='"
					+ order_model.F_CUSTOM_CODE + "' and F_COMM_CODE='"
					+ order_model.F_COMM_CODE + "'";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

}
