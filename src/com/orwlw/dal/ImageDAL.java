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
import com.orwlw.comm.DBHelper;
import com.orwlw.model.ImageModel;

/**
 * 照片数据操作类
 * 
 * @author HYJ 2013-2-26
 * 
 */
public class ImageDAL {

	public static void Insert(Application application, ImageModel imageModel) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = MessageFormat
					.format("insert into T_IMAGE_ERRINFO(F_Id,F_PATH,F_ImageName,F_ImageType,F_CustomCode,F_DateTime,F_user,F_REMARK,F_LAT,F_LON)  values(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'',''{6}'',''{7}'',''{8}'',''{9}'')",
							imageModel.Id, imageModel.Path,
							imageModel.ImageName, imageModel.ImageType,
							imageModel.CustomCode, imageModel.DateTime,
							imageModel.ImageOpearter, imageModel.F_REMARK,
							imageModel.Lat, imageModel.Lon);
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.e("异常", ex.getMessage());
		} finally {

		}

	}

	public static void DeleteImage(Application application, String ID) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = MessageFormat.format(
					"delete from T_IMAGE_ERRINFO where F_Id=''{0}''", ID);
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {

		} finally {

		}
	}

	public static List<ImageModel> getImgeList(Application application,
			String custom_code) {
		List<ImageModel> list = new ArrayList<ImageModel>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_IMAGE_ERRINFO where F_CustomCode='"
					+ custom_code + "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				ImageModel imagemodel = new ImageModel();
				int i = cursor.getColumnIndex("F_Id");
				imagemodel.Id = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_PATH");
				imagemodel.Path = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("F_ImageName");
				imagemodel.ImageName = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_ImageType");
				imagemodel.ImageType = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_CustomCode");
				imagemodel.CustomCode = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_DateTime");
				imagemodel.DateTime = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_user");
				imagemodel.ImageOpearter = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_REMARK");
				imagemodel.F_REMARK = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_LAT");
				imagemodel.Lat = cursor.isNull(i) ? "0" : cursor.getString(i);
				i = cursor.getColumnIndex("F_LON");
				imagemodel.Lon = cursor.isNull(i) ? "0" : cursor.getString(i);
				list.add(imagemodel);
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
