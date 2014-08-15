package com.orwlw.dal;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.orwlw.comm.Constants;
import com.orwlw.comm.DBHelper;
import com.orwlw.comm.MyApplication;
import com.orwlw.model.ImageModel;
import com.orwlw.model.LocationModel;

/**
 * 位置数据操作类
 * 
 * @author HYJ 2013-2-26
 * 
 */
public class LoationDAL {

	public static void Insert(Application application, LocationModel location) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = MessageFormat
					.format("insert into T_lOCATION_info(ID,IMEI,UID,DATETIME,LAT,LON,FLAG)  values(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'',''{6}'')",
							location.ID, location.IMEI, location.PERSONNO,
							location.DATETIME, location.LAT, location.LON,
							location.FLAG);
			dbHelper.ExecSql(sql);
		} catch (Exception ex) {
			MyApplication.SaveLog(Constants.GetTime() + "保存定位数据异常"
					+ ex.getMessage());
		} finally {
		}
	}

	public static void Delete(Application application, String ID) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = MessageFormat.format(
					"delete from T_lOCATION_info where ID=''{0}''", ID);
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {

		} finally {

		}
	}

	public static List<LocationModel> getLocationList(Application application) {
		List<LocationModel> list = new ArrayList<LocationModel>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_lOCATION_info ";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				LocationModel location = new LocationModel();
				int i = cursor.getColumnIndex("IMEI");
				location.IMEI = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("ID");
				location.ID = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("UID");
				location.PERSONNO = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("DATETIME");
				location.DATETIME = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("LAT");
				location.LAT = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("LON");
				location.LON = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("FLAG");
				location.FLAG = cursor.isNull(i) ? "" : cursor.getString(i);
				list.add(location);
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
