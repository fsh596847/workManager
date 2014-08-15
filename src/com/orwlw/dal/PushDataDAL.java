package com.orwlw.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.orwlw.comm.DBHelper;
import com.orwlw.comm.MyApplication;
import com.orwlw.model.PushDataModel;

import android.R.integer;
import android.app.Application;
import android.database.Cursor;
import android.util.Log;

/**
 * *
 * 
 * @author HYJ 2012-12-05 即时信息数据处理类
 * 
 */
public class PushDataDAL {
	public Application app = null;

	public PushDataDAL(Application application) {
		app = application;
	}

	public void Insert(PushDataModel pushdata) {
		DBHelper dbHelper = new DBHelper(app);
		try {
			String sql = "insert into  tb_push_data(created_date,data_id,message,send_name,send_username,status,uri) values('"
					+ pushdata.created_date
					+ "','"
					+ pushdata.data_id
					+ "','"
					+ pushdata.message
					+ "','"
					+ pushdata.send_name
					+ "','"
					+ pushdata.send_username
					+ "','"
					+ pushdata.status
					+ "','"
					+ pushdata.uri + "')";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			MyApplication.WriteLog("保存信息异常：" + ex.getMessage() + "");
		} finally {

		}
	}

	public void Delete(String pushdata_id) {
		DBHelper dbHelper = new DBHelper(app);
		try {
			String sql = "delete from tb_push_data where data_id='"
					+ pushdata_id + "'";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	public void Update(String pushdata_id) {
		DBHelper dbHelper = new DBHelper(app);
		try {
			String sql = "update tb_push_data set status='1' where data_id='"
					+ pushdata_id + "'";
			dbHelper.ExecSql(sql);

		} catch (Exception ex) {
			Log.i("异常", ex.getMessage());
		} finally {

		}
	}

	public int getCount() {
		DBHelper dbHelper = new DBHelper(app);
		int cou = 0;
		try {
			String sql = "Select count(*) as cou From tb_push_data where status='0' ";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				int i = cursor.getColumnIndex("cou");
				cou = cursor.isNull(i) ? 0 : cursor.getInt(i);
			}
			cursor.close();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.close();
		}
		return cou;
	}

	public List<PushDataModel> getpushdatalist() {
		List<PushDataModel> list = new ArrayList<PushDataModel>();
		DBHelper dbHelper = new DBHelper(app);
		try {
			String sql = "Select * From tb_push_data order by created_date ";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			String data_id;
			String send_username;
			String send_name;
			String created_date;
			String status;
			String uri;
			String message;

			while (cursor.moveToNext()) {
				int i = cursor.getColumnIndex("data_id");
				data_id = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("send_name");
				send_name = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("send_username");
				send_username = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("created_date");
				created_date = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("status");
				status = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("uri");
				uri = cursor.isNull(i) ? "" : cursor.getString(i);
				i = cursor.getColumnIndex("message");
				message = cursor.isNull(i) ? "" : cursor.getString(i);

				PushDataModel pushmodel = new PushDataModel();
				pushmodel.data_id = data_id;
				pushmodel.send_name = send_name;
				pushmodel.send_username = send_username;
				pushmodel.created_date = created_date;
				pushmodel.status = status;
				pushmodel.uri = uri;
				pushmodel.message = java.net.URLDecoder.decode(message);

				list.add(pushmodel);
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
