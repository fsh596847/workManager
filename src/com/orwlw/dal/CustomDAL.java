package com.orwlw.dal;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.orwlw.comm.ComparatorCustomList;
import com.orwlw.comm.DBHelper;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.PrepareLaction;
import com.orwlw.model.CustomModel;
import com.orwlw.model.LocationModel;

public class CustomDAL {
	public static List<Map<String, Object>> getCustomList(
			Application application) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_SCM_CUSTOM";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				int i = cursor.getColumnIndex("F_CUSTOM_CODE");
				map.put("F_CUSTOM_CODE",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CUSTOM_NAME");
				map.put("F_CUSTOM_NAME",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CO_ADDRESS");
				map.put("F_CO_ADDRESS",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CONTACT_PERSON");
				map.put("F_CONTACT_PERSON",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CONTACT_TEL");
				map.put("F_CONTACT_TEL",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_LAT");
				map.put("F_LAT", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_LON");
				map.put("F_LON", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_ISVISIT");
				map.put("F_ISVISIT",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_ATTENDED");
				map.put("F_ATTENDED",
						cursor.isNull(i) ? "" : cursor.getString(i));

				double distance = 0.0;
				if (!(map.get("F_LAT") + "").equalsIgnoreCase("")) {
					distance = PrepareLaction.GetDistance(Double
							.parseDouble(((MyApplication) application)
									.Getlocaldata().lastlat), Double
							.parseDouble(((MyApplication) application)
									.Getlocaldata().lastlon), Double
							.parseDouble(map.get("F_LAT") + ""), Double
							.parseDouble(map.get("F_LON") + ""));
					String dd = distance + "";
				}
				map.put("Distance", distance);
				list.add(map);
			}
			cursor.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.close();
		}
		ComparatorCustomList comparator = new ComparatorCustomList();
		Collections.sort(list, comparator);
		return list;
	}

	public static List<Map<String, Object>> getCustomList_plan(
			Application application) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_SCM_CUSTOM where type='1'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				int i = cursor.getColumnIndex("F_CUSTOM_CODE");
				map.put("F_CUSTOM_CODE",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CUSTOM_NAME");
				map.put("F_CUSTOM_NAME",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CO_ADDRESS");
				map.put("F_CO_ADDRESS",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CONTACT_PERSON");
				map.put("F_CONTACT_PERSON",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CONTACT_TEL");
				map.put("F_CONTACT_TEL",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_LAT");
				map.put("F_LAT", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_LON");
				map.put("F_LON", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_ISVISIT");
				map.put("F_ISVISIT",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_ATTENDED");
				map.put("F_ATTENDED",
						cursor.isNull(i) ? "" : cursor.getString(i));

				double distance = 0.0;
				if (!(map.get("F_LAT") + "").equalsIgnoreCase("")) {
					distance = PrepareLaction.GetDistance(Double
							.parseDouble(((MyApplication) application)
									.Getlocaldata().lastlat), Double
							.parseDouble(((MyApplication) application)
									.Getlocaldata().lastlon), Double
							.parseDouble(map.get("F_LAT") + ""), Double
							.parseDouble(map.get("F_LON") + ""));
					String dd = distance + "";
				}
				map.put("Distance", distance);
				list.add(map);
			}
			cursor.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.close();
		}
		ComparatorCustomList comparator = new ComparatorCustomList();
		Collections.sort(list, comparator);
		return list;
	}

	public static Map<String, Object> getCustom(Application application,
			String tv_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "Select * From T_SCM_CUSTOM where F_CUSTOM_CODE='"
					+ tv_code + "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				int i = cursor.getColumnIndex("F_CUSTOM_CODE");
				map.put("F_CUSTOM_CODE",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CUSTOM_NAME");
				map.put("F_CUSTOM_NAME",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CO_ADDRESS");
				map.put("F_CO_ADDRESS",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CONTACT_PERSON");
				map.put("F_CONTACT_PERSON",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CONTACT_TEL");
				map.put("F_CONTACT_TEL",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_LAT");
				map.put("F_LAT", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_LON");
				map.put("F_LON", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_ISVISIT");
				map.put("F_ISVISIT",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_ATTENDED");
				map.put("F_ATTENDED",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_AREA_NAME");
				map.put("F_AREA_NAME",
						cursor.isNull(i) ? "" : cursor.getString(i));
				double distance = 0.0;
				String dd = "0.0";
				if (!(map.get("F_LAT") + "").equalsIgnoreCase("")) {
					distance = PrepareLaction.GetDistance(Double
							.parseDouble(((MyApplication) application)
									.Getlocaldata().lastlat), Double
							.parseDouble(((MyApplication) application)
									.Getlocaldata().lastlon), Double
							.parseDouble(map.get("F_LAT") + ""), Double
							.parseDouble(map.get("F_LON") + ""));
					dd = distance + "";
				}
				BigDecimal b = new BigDecimal(dd);
				distance = b.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

				map.put("Distance", distance);
			}
			cursor.close();
			return map;
		} catch (Exception e) {
			Log.e(CustomDAL.class.toString(), e.toString());
		} finally {
			dbHelper.close();
		}
		return null;
	}

	public static List<Map<String, Object>> getCustomList_near(
			Application application, LocationModel location) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(application);
		try {
			if (location != null) {
				String sql = "Select * From T_SCM_CUSTOM";
				Cursor cursor = dbHelper.db.rawQuery(sql, new String[] {});

				while (cursor.moveToNext()) {
					Map<String, Object> map = new HashMap<String, Object>();
					int i = cursor.getColumnIndex("F_CUSTOM_CODE");
					map.put("F_CUSTOM_CODE",
							cursor.isNull(i) ? "" : cursor.getString(i));
					i = cursor.getColumnIndex("F_CUSTOM_NAME");
					map.put("F_CUSTOM_NAME",
							cursor.isNull(i) ? "" : cursor.getString(i));
					i = cursor.getColumnIndex("F_CO_ADDRESS");
					map.put("F_CO_ADDRESS",
							cursor.isNull(i) ? "" : cursor.getString(i));
					i = cursor.getColumnIndex("F_CONTACT_PERSON");
					map.put("F_CONTACT_PERSON",
							cursor.isNull(i) ? "" : cursor.getString(i));
					i = cursor.getColumnIndex("F_CONTACT_TEL");
					map.put("F_CONTACT_TEL",
							cursor.isNull(i) ? "" : cursor.getString(i));
					i = cursor.getColumnIndex("F_LAT");
					map.put("F_LAT",
							cursor.isNull(i) ? "" : cursor.getString(i));
					i = cursor.getColumnIndex("F_LON");
					map.put("F_LON",
							cursor.isNull(i) ? "" : cursor.getString(i));
					i = cursor.getColumnIndex("F_ISVISIT");
					map.put("F_ISVISIT",
							cursor.isNull(i) ? "" : cursor.getString(i));
					i = cursor.getColumnIndex("F_ATTENDED");
					map.put("F_ATTENDED",
							cursor.isNull(i) ? "" : cursor.getString(i));
					double distance = 0.0;
					if (!(map.get("F_LAT") + "").equalsIgnoreCase("")) {
						distance = PrepareLaction.GetDistance(
								Double.parseDouble(location.LAT),
								Double.parseDouble(location.LON),
								Double.parseDouble(map.get("F_LAT") + ""),
								Double.parseDouble(map.get("F_LON") + ""));
						String dd = distance + "";
					}
					map.put("Distance", distance);
					list.add(map);

				}
				cursor.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.close();
		}
		ComparatorCustomList comparator = new ComparatorCustomList();
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * @Title: searchCusList
	 * @Description:TODO 模糊查询客户档案
	 * @author WangMin
	 * @date 2013-3-7 上午09:44:07
	 * @param context
	 * @param strValue
	 * @return设定文件
	 * @return List<Map<String,Object>> 返回类型
	 * @throws
	 */
	public static List<Map<String, Object>> searchCusList(Context context,
			String strValue) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(context);
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT * FROM T_SCM_CUSTOM ");
			if (!strValue.trim().equalsIgnoreCase(""))
				sb.append(" WHERE F_CUSTOM_CODE LIKE '%" + strValue
						+ "%' OR F_CUSTOM_NAME LIKE '%" + strValue + "%' ");
			Cursor cursor = dbHelper.db
					.rawQuery(sb.toString(), new String[] {});

			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				int i = cursor.getColumnIndex("F_CUSTOM_CODE");
				map.put("F_CUSTOM_CODE",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CUSTOM_NAME");
				map.put("F_CUSTOM_NAME",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CO_ADDRESS");
				map.put("F_CO_ADDRESS",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CONTACT_PERSON");
				map.put("F_CONTACT_PERSON",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_CONTACT_TEL");
				map.put("F_CONTACT_TEL",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_LAT");
				map.put("F_LAT", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_LON");
				map.put("F_LON", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_ISVISIT");
				map.put("F_ISVISIT",
						cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_ATTENDED");
				map.put("F_ATTENDED",
						cursor.isNull(i) ? "" : cursor.getString(i));
				list.add(map);
			}
			cursor.close();
		} catch (Exception e) {
			MyApplication.WriteLog(e.getMessage());
		} finally {
			dbHelper.close();
		}
		return list;
	}

	public static void updatecustom(Application application,
			Map<String, String> map) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_SCM_CUSTOM set F_CUSTOM_NAME='"
					+ map.get("customName") + "' ,F_CO_ADDRESS='"
					+ map.get("address") + "' ,F_CONTACT_PERSON='"
					+ map.get("person") + "' ,F_CONTACT_TEL='"
					+ map.get("phone") + "' where F_CUSTOM_CODE='"
					+ map.get("customCode") + "'";
			dbHelper.ExecSql(sql);
		} catch (Exception ex) {
			Log.e("异常", ex.getMessage());
		} finally {

		}
	}

	// 标记本地客户为拜访状态
	public static void markvisit_custom(Application application,
			String custom_code) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_SCM_CUSTOM set F_ISVISIT='1' where F_CUSTOM_CODE='"
					+ custom_code + "'";
			dbHelper.ExecSql(sql);
		} catch (Exception ex) {
			Log.e("异常", ex.getMessage());
		} finally {

		}
	}

	// 标记本地客户为签到狀態
	public static void markcustom_attend(Application application,
			String custom_code, String attendstatus) {
		DBHelper dbHelper = new DBHelper(application);
		try {
			String sql = "update T_SCM_CUSTOM set F_ATTENDED='" + attendstatus
					+ "' where F_CUSTOM_CODE='" + custom_code + "'";
			dbHelper.ExecSql(sql);
		} catch (Exception ex) {
			Log.e("异常", ex.getMessage());
		} finally {

		}
	}
}
