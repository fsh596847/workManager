package com.orwlw.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.database.Cursor;

import com.orwlw.comm.DBHelper;
import com.orwlw.model.FunctionModel;

public class FunctionDAL {
	public static List<FunctionModel> getFUNCList(Application application,
			String mod) {
		List<FunctionModel> list = new ArrayList<FunctionModel>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_SYS_APPFUNC where F_MOD='" + mod
					+ "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				FunctionModel funcionmodel = new FunctionModel();
				int i = cursor.getColumnIndex("F_FUNC_NAME");
				funcionmodel.Functionname = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_FUNC_ACTIVITY");
				funcionmodel.Functionintent = cursor.isNull(i) ? "" : cursor
						.getString(i);

				list.add(funcionmodel);
			}
			cursor.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.close();
		}
		return list;
	}
}
