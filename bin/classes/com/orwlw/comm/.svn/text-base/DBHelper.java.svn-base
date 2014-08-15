package com.orwlw.comm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBHelper {
	public static final String DB_ACTION = "db_action";// LogCat

	public static final String DB_NAME = "data.db";// 数据库名
	private static final int DB_VERSION = 1;// 数据库版本号

	public SQLiteDatabase db;
	private Context xContext;
	private DBOpenHelper dbOpenHelper;

	public DBHelper(Context context) {
		xContext = context;
		dbOpenHelper = new DBOpenHelper(xContext, DB_NAME, null, DB_VERSION);
		this.open();
	}

	/**
	 * 空间不够存储的时候设为只读
	 * 
	 * @throws SQLiteException
	 */
	public void open() throws SQLiteException {
		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			db = dbOpenHelper.getReadableDatabase();
		}
	}

	public void close() {
		if (dbOpenHelper != null) {
			dbOpenHelper.close();
		}
		if (db != null) {
			db.close();
			db = null;

		}
	}

	public boolean DropTable(String tableName) {
		try {
			open();
			db.execSQL(" DROP TABLE IF EXISTS " + tableName);
			close();
			return true;
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}

	}

	public void ExecSql(String sql) {
		open();
		db.execSQL(sql);
		close();
	}

	/**
	 * 判断某张表是否存在
	 * 
	 * @param tabName
	 * @return
	 */
	public boolean isTableExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}

		try {
			open();
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where type ='table' and name ='"
					+ tableName.trim() + "'";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

			cursor.close();
			close();
		} catch (Exception e) {
			Log.e("查询表存在错误", e.getMessage() + "");
			close();
		}
		return result;
	}

	/**
	 * 静态Helper类，用于建立、更新和打开数据库
	 */
	private class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				// 本地照片表
				db.execSQL(DBfinal.SQL_IMAGE_ERRINFO);
				// 本地订货商品表
				db.execSQL(DBfinal.SQL_LOCAL_ORDER);
				// 换货表
				db.execSQL(DBfinal.SQL_EXCHAGNE_COMMS);
				// 退货表
				db.execSQL(DBfinal.SQL_BACK_COMMS);
				// 离线基站表
				db.execSQL(DBfinal.SQL_Device_info);
				// 常用商品表
				db.execSQL(DBfinal.SQL_COMM_USED);
				// 离线位置存储表
				db.execSQL(DBfinal.SQL_LOCATION_info);
				// 照片类型表
				// db.execSQL(DBfinal.SQL_IMAGE_TYPE);				
				// db.execSQL(DBfinal.SQL_SCM_CATEGORY);
				// 客户信息表 联系人，客户编号，客户名称，地址，电话，经度，纬度。
				// db.execSQL(DBfinal.SQL_Custom_info);
				// 枚举表
				// db.execSQL(DBfinal.SQL_ENUM);

			} catch (Exception ex) {
				Log.i("创建数据库", ex.getMessage());
				db.close();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			// 函数在数据库需要升级时被调用，
			// 一般用来删除旧的数据库表，
			// 并将数据转移到新版本的数据库表中
		}

	}
}
