package com.orwlw.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.orwlw.comm.Constants;
import com.orwlw.comm.DBHelper;
import com.orwlw.comm.MyApplication;
import com.orwlw.model.ProductModel;

/**
 * @author HYJ 2013-02-25 商品信息数据访问类
 * 
 */
public class ProductDAL
{

	/**
	 * 获取所有商品
	 * 
	 * @param application
	 * @return
	 */
	public static List<Map<String, Object>> getCOMMList(Application application)
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(application);
		try
		{
			String sql = "Select * From T_SCM_PRODUCT";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[]
			{});

			while (cursor.moveToNext())
			{
				Map<String, Object> map = new HashMap<String, Object>();
				int i = cursor.getColumnIndex("F_COMM_CODE");
				map.put("F_COMM_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_NAME");
				map.put("F_COMM_NAME", cursor.isNull(i) ? "" : cursor.getString(i));

				list.add(map);
			}
			cursor.close();
		} catch (Exception e)
		{
			// TODO: handle exception
		} finally
		{
			dbHelper.close();
		}
		return list;
	}

	/**
	 * 根据品相获取商品
	 * 
	 * @param application
	 * @param categroy_code
	 * @return
	 */
	public static List<Map<String, Object>> getCOMMList_by_Cate(Application application, String categroy_code)
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(application);
		try
		{
			String sql = "Select * From T_SCM_PRODUCT where F_COMM_CATEGORY_CODE='" + categroy_code + "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[]
			{});

			while (cursor.moveToNext())
			{
				Map<String, Object> map = new HashMap<String, Object>();
				int i = cursor.getColumnIndex("F_COMM_CODE");
				map.put("F_COMM_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_NAME");
				map.put("F_COMM_NAME", cursor.isNull(i) ? "" : cursor.getString(i));

				list.add(map);
			}
			cursor.close();
		} catch (Exception e)
		{
			// TODO: handle exception
		} finally
		{
			dbHelper.close();
		}
		return list;
	}

	/**
	 * 获取常用商品数据
	 * 
	 * @param application
	 * @param categroy_code
	 * @return
	 */
	public static List<Map<String, Object>> getCOMMList_by_used(Application application, String categroy_code)
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(application);
		try
		{
			String sql = "Select * From T_SCM_PRODUCT where F_COMM_CODE in (select F_COMM_CODE from T_COMM_USED)";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[]
			{});

			while (cursor.moveToNext())
			{
				Map<String, Object> map = new HashMap<String, Object>();
				int i = cursor.getColumnIndex("F_COMM_CODE");
				map.put("F_COMM_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_NAME");
				map.put("F_COMM_CODE", cursor.isNull(i) ? "" : cursor.getString(i));

				list.add(map);
			}
			cursor.close();
		} catch (Exception e)
		{
			// TODO: handle exception
		} finally
		{
			dbHelper.close();
		}
		return list;
	}

	public static Map<String, Object> getCOMM_by_scan(Application application, String bar_code)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		DBHelper dbHelper = new DBHelper(application);
		try
		{
			String sql = "Select * From T_SCM_PRODUCT where F_BAR_CODE='" + bar_code + "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[]
			{});

			while (cursor.moveToNext())
			{

				int i = cursor.getColumnIndex("F_COMM_CODE");
				map.put("F_COMM_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_NAME");
				map.put("F_COMM_NAME", cursor.isNull(i) ? "" : cursor.getString(i));

			}
			cursor.close();
		} catch (Exception e)
		{
			// TODO: handle exception
		} finally
		{
			dbHelper.close();
		}
		return map;
	}

	/**
	 * @Title: getCommUsedList
	 * @Description:TODO(这里用一句话描述这个方法的作用)
	 * @author WangMin
	 * @date 2013-3-5 上午11:11:40
	 * @param application
	 * @return设定文件
	 * @return List<Map<String,Object>> 返回类型
	 * @throws
	 */
	public static List<Map<String, Object>> getCommUsedList(Application application)
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(application);
		try
		{
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT B.F_COMM_CODE,B.F_COMM_NAME,B.F_COMM_CATEGORY_CODE,B.F_BAR_CODE ");
			sb.append(" FROM T_COMM_USED  A LEFT JOIN T_SCM_PRODUCT B WHERE A.F_COMM_CODE=B.F_COMM_CODE ");
			Cursor cursor = dbHelper.db.rawQuery(sb.toString(), new String[]
			{});

			while (cursor.moveToNext())
			{
				Map<String, Object> map = new HashMap<String, Object>();
				int i = cursor.getColumnIndex("F_COMM_CODE");
				map.put("F_COMM_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_NAME");
				map.put("F_COMM_NAME", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_CATEGORY_CODE");
				map.put("F_COMM_CATEGORY_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_BAR_CODE");
				map.put("F_BAR_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				list.add(map);
			}
			cursor.close();
		} catch (Exception e)
		{
			MyApplication.WriteLog(e.getMessage());
		} finally
		{
			dbHelper.close();
		}
		return list;
	}

	/**
	 * @Title: isDataExist
	 * @Description:TODO 通过商品编码查询本地的常用商品表是否存在某条数据
	 * @author WangMin
	 * @date 2013-3-5 上午10:56:50
	 * @param application
	 * @param F_COMM_CODE
	 *            商品编码
	 * @return设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isDataExist(Application application, String F_COMM_CODE)
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DBHelper dbHelper = new DBHelper(application);
		try
		{
			String sql = "Select * From T_COMM_USED WHERE F_COMM_CODE = '" + F_COMM_CODE + "'";
			Cursor cursor = dbHelper.db.rawQuery(sql, new String[]
			{});
			if (cursor.getCount() > 0)
				return true;
			cursor.close();
		} catch (Exception e)
		{
			MyApplication.WriteLog(e.getMessage());
			return false;
		} finally
		{
			dbHelper.close();
		}
		return false;
	}
	
	/**
	 * @Title: addData 
	 * @Description:TODO SQLlite（手机端数据库）中增加一条或多条数据
	 * @author WangMin
	 * @date 2013-3-4 上午09:45:09
	 * @param context
	 * @param tableName 
	 * @param list
	 * @return设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean addData(Context context, String tableName,
			List<Map<String, Object>> list) {
		DBHelper da = null;
		try {
			da = new DBHelper(context);
			// 插入新值
			for (int i = 0; i < list.size(); i++) {
				Iterator<String> iter1 = list.get(i).keySet().iterator();
				StringBuffer sb1 = new StringBuffer();
				sb1.append("insert into " + tableName + " values(");
				while (iter1.hasNext()) {
					String key = (String) iter1.next();
					String value = (String) list.get(i).get(key);
					sb1.append("'");
					sb1.append(value);
					sb1.append("',");
				}
				String insertSql = sb1.toString().substring(0, sb1.length() - 1) + ")";
				da.ExecSql(insertSql);
			}
			return true;
		} catch (Exception e) {
			MyApplication.WriteLog(e.getMessage());
			return false;
		} finally {
			da.close();
		}
	}
	
	/**
	 * @Title: addData 
	 * @Description:TODO SQLlite 通过商品编码删除本地常用商品
	 * @author WangMin
	 * @date 2013-3-4 上午09:45:09
	 * @param context
	 * @param tableName 
	 * @param list
	 * @return设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean deleteData(Context context,String strCommCode) {
		DBHelper da = null;
		try {
			da = new DBHelper(context);
			StringBuffer sb=new StringBuffer();
			sb.append("delete from T_COMM_USED where F_COMM_CODE='"+strCommCode+"'");
			da.ExecSql(sb.toString());
			return true;
		} catch (Exception e) {
			MyApplication.WriteLog(e.getMessage());
			return false;
		} finally {
			da.close();
		}
	}

	public static List<Map<String, String>> getCategorys_by_parent(Application application, String parentcode)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		DBHelper dbHelper = new DBHelper(application);
		try
		{
			StringBuffer sb = new StringBuffer();
			sb.append("select F_COMM_CATEGORY_CODE,F_COMM_CATEGORY,F_PARENT_CODE from T_SCM_CATEGORY where  trim(f_parent_code)=trim('" + parentcode + "')");
			Cursor cursor = dbHelper.db.rawQuery(sb.toString(), new String[]
			{});

			while (cursor.moveToNext())
			{
				Map<String, String> map = new HashMap<String, String>();
				int i = cursor.getColumnIndex("F_COMM_CATEGORY_CODE");
				map.put("F_COMM_CATEGORY_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_CATEGORY");
				map.put("F_COMM_CATEGORY", cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_CATEGORY_CODE");
				map.put("F_COMM_CATEGORY_CODE", cursor.isNull(i) ? "" : cursor.getString(i));
				list.add(map);
			}
			cursor.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbHelper.close();
		}
		return list;
	}

	public static int getCategorys_count(Application application)
	{		
		DBHelper dbHelper = new DBHelper(application);
		int cou = 0;
		try
		{
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) as cou from T_SCM_CATEGORY");
			Cursor cursor = dbHelper.db.rawQuery(sb.toString(), new String[]
			{});

			while (cursor.moveToNext())
			{
				int i = cursor.getColumnIndex("cou");
				cou = cursor.getInt(i);
			}
			cursor.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbHelper.close();
		}
		return cou;
	}
	/**
	 * @Title: searchCommList 
	 * @Description:TODO 商品模糊查询
	 * @author WangMin
	 * @date 2013-3-6 下午02:18:01
	 * @param context
	 * @param strValue 模糊查询条件
	 * @return设定文件
	 * @return List<Map<String,Object>> 返回类型
	 * @throws
	 */
	public static List<Map<String, Object>> searchCommList(Context context, String strValue) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		DBHelper dbHelper = new DBHelper(context);
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" SELECT B.F_COMM_CODE,B.F_COMM_NAME,B.F_COMM_CATEGORY_CODE,B.F_BAR_CODE FROM T_SCM_PRODUCT B  ");
			if(!strValue.trim().equalsIgnoreCase(""))
				sb.append(" WHERE B.F_COMM_CODE LIKE '%"+strValue+"%' OR B.F_COMM_NAME LIKE '%"+strValue+"%' " );
			Cursor cursor = dbHelper.db.rawQuery(sb.toString(), new String[] {});
			
			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				int i = cursor.getColumnIndex("F_COMM_CODE");
				map.put("F_COMM_CODE",cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_NAME");
				map.put("F_COMM_NAME",cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_COMM_CATEGORY_CODE");
				map.put("F_COMM_CATEGORY_CODE",cursor.isNull(i) ? "" : cursor.getString(i));
				i = cursor.getColumnIndex("F_BAR_CODE");
				map.put("F_BAR_CODE",cursor.isNull(i) ? "" : cursor.getString(i));
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

}
