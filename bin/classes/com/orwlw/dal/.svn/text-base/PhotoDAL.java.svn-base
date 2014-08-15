package com.orwlw.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import com.orwlw.comm.DBHelper;
import com.orwlw.model.PhotoModel;

public class PhotoDAL {

	/**
	 * 获取照片类型
	 * 
	 * @param application
	 * @return
	 */
	public static List<PhotoModel> getImageType(Application application) {
		List<PhotoModel> list = new ArrayList<PhotoModel>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_IMAGE_TYPE";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				PhotoModel photomodel = new PhotoModel();
				int i = cursor.getColumnIndex("F_KEY_CODE");
				photomodel.typecode = cursor.isNull(i) ? "" : cursor
						.getString(i);
				i = cursor.getColumnIndex("F_KEY_VALUE");
				photomodel.typename = cursor.isNull(i) ? "" : cursor
						.getString(i);

				list.add(photomodel);
			}
			cursor.close();
		} catch (Exception e) {

		} finally {
			dbHelper.close();
		}
		return list;
	}

	/**
	 * 按门店获取各类型照片
	 * 
	 * @param application
	 * @param currentcode
	 * @return
	 */
	public static List<PhotoModel> getImageType(Application application,
			String currentcode) {
		List<PhotoModel> list = new ArrayList<PhotoModel>();
		DBHelper dbHelper = new DBHelper(application);

		try {
			String sql = "select * from T_IMAGE_TYPE left join (Select f_id,f_imagetype,f_path,F_REMARK From MAIN.[T_IMAGE_ERRINFO] where f_customcode='"
					+ currentcode
					+ "' ) a on      T_IMAGE_TYPE.[F_KEY_CODE]=a. f_imagetype";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				PhotoModel photomodel = new PhotoModel();

				int i = cursor.getColumnIndex("F_KEY_CODE");
				photomodel.typecode = cursor.isNull(i) ? "" : cursor
						.getString(i);

				i = cursor.getColumnIndex("F_KEY_VALUE");
				photomodel.typename = cursor.isNull(i) ? "" : cursor
						.getString(i);

				i = cursor.getColumnIndex("f_path");
				photomodel.image = cursor.isNull(i) ? "" : cursor.getString(i);

				i = cursor.getColumnIndex("f_id");
				photomodel.id = cursor.isNull(i) ? "" : cursor.getString(i);

				i = cursor.getColumnIndex("F_REMARK");
				photomodel.REMARK = cursor.isNull(i) ? "" : cursor.getString(i);

				list.add(photomodel);
			}
			cursor.close();
		} catch (Exception e) {

		} finally {
			dbHelper.close();
		}
		return list;
	}

	public static void updateRemark(Application application, String remark,
			String photo_id) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_IMAGE_ERRINFO set F_REMARK='" + remark
					+ "'  where f_id='" + photo_id + "'";
			dbHelper.ExecSql(sql);
		} catch (Exception ex) {
			Log.e("异常", ex.getMessage());
		} finally {

		}
	}

}
