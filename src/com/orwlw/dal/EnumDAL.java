package com.orwlw.dal;

import java.util.ArrayList;
import java.util.List;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.orwlw.comm.DBHelper;
import com.orwlw.model.EnumModel;
import com.orwlw.model.Order_Model;

/**
 * 枚举数据访问类
 * 
 * @author HYJ
 * 
 */
public class EnumDAL {
	public static List<EnumModel> getEnum(Context context, String type) {
		List<EnumModel> list = new ArrayList<EnumModel>();
		DBHelper dbHelper = new DBHelper(context);
		try {
			String sql = "Select * From T_ENUM where F_KEY_TYPE='" + type + "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				EnumModel model = new EnumModel();
				int i = cursor.getColumnIndex("F_KEY_CODE");
				model.KEY_CODE = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_KEY_VALUE");
				model.KEY_VALUE = cursor.isNull(i) ? "" : cursor.getString(i);
				list.add(model);
			}
			cursor.close();
		} catch (Exception e) {

		} finally {
			dbHelper.close();
		}
		return list;
	}
}
