package com.orwlw.comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import com.orwlw.model.ImageModel;
import com.orwlw.model.LocationModel;
import com.orwlw.model.Order_Model;
import android.content.Context;
import android.util.Log;

public class SyncHelper {

	public static boolean RelocateTable(Context context, String tablename,
			List<Map<String, Object>> list1) {
		DBHelper da = null;
		try {
			da = new DBHelper(context);
			// �������ݿ�
			List<Map<String, Object>> list = list1;
			if (list.size() > 0) {
				boolean b = da.DropTable(tablename);
			} else {
				if (da.isTableExist(tablename))
					da.ExecSql("delete from " + tablename);
				return true;
			}
			StringBuffer sb = new StringBuffer();
			sb.append("create table ");
			sb.append(tablename);
			sb.append("(");
			Iterator<String> iter = list.get(0).keySet().iterator();
			while (iter.hasNext()) {
				sb.append(iter.next());
				sb.append(" text,");
			}
			String cteateSql = sb.toString().substring(0, sb.length() - 1)
					+ ")";
			da.ExecSql(cteateSql);
			// ������ֵ
			for (int i = 0; i < list.size(); i++) {
				Iterator<String> iter1 = list.get(i).keySet().iterator();
				StringBuffer sb1 = new StringBuffer();
				sb1.append("insert into " + tablename + " values(");
				while (iter1.hasNext()) {
					String key = (String) iter1.next();
					String value = (String) list.get(i).get(key);
					sb1.append("'");
					sb1.append(value);
					sb1.append("',");
				}
				String insertSql = sb1.toString()
						.substring(0, sb1.length() - 1) + ")";
				da.ExecSql(insertSql);
			}
			return true;
		} catch (Exception e) {
			if (da != null) {
				da.close();
			}
			return false;
		}
	}

	public static boolean RelocateTable_Data(Context context, String tablename,
			List<Map<String, Object>> list1) {
		DBHelper da = null;
		try {
			da = new DBHelper(context);
			// �����Ӧ�ľ�����
			List<Map<String, Object>> list = list1;
			if (list.size() > 0) {
				String wherein = "";
				for (int i = 0; i < list.size(); i++) {
					wherein += "'" + list.get(i).get("F_COMM_CODE") + "',";
				}
				if (da.isTableExist(tablename))
					da.ExecSql("delete from " + tablename
							+ " where F_COMM_CODE in ("
							+ wherein.substring(0, wherein.length() - 1) + ")");
			}
			// ����������
			for (int i = 0; i < list.size(); i++) {
				Iterator<String> iter1 = list.get(i).keySet().iterator();
				StringBuffer sb1 = new StringBuffer();
				sb1.append("insert into " + tablename + " values(");
				while (iter1.hasNext()) {
					String key = (String) iter1.next();
					String value = (String) list.get(i).get(key);
					sb1.append("'");
					sb1.append(value);
					sb1.append("',");
				}
				String insertSql = sb1.toString()
						.substring(0, sb1.length() - 1) + ")";
				da.ExecSql(insertSql);
			}
			return true;
		} catch (Exception e) {
			if (da != null) {
				da.close();
			}
			return false;
		}
	}

	/**
	 * ��¼��֤
	 */
	public static boolean Login(String myIMEI, String userName, String passWord)
			throws IOException, XmlPullParserException {

		String nameSpace = "http://tempuri.org/";
		// ���õķ�����
		String methodName = "Single_Login";
		// �������������ƿռ����һ��
		String SOAP_ACTION = "http://tempuri.org/" + methodName;
		// ��÷����������
		SoapObject request = new SoapObject(nameSpace, methodName);
		// ������Ҫ�����������Ĳ���
		request.addProperty("imei", myIMEI);
		request.addProperty("user", userName);
		request.addProperty("pwd", passWord);
		// ����soap�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// �����Ƿ���õ���dotNet������
		envelope.dotNet = true;
		//
		envelope.bodyOut = request;
		HttpTransportSE hts = new HttpTransportSE(Constants.webserverString,
				15000);
		// web service����
		hts.call(SOAP_ACTION, envelope);
		// �õ����ؽ��
		Object o = envelope.getResponse();

		return (o.toString()).equalsIgnoreCase("true");

	}

	/**
	 * ��ȡ����Ͻ�Ŀͻ��б�
	 * 
	 * @param app
	 *            ��ҵ����
	 * @param personno
	 *            �û�����
	 * @return
	 */
	public static List<Map<String, Object>> GetCustomlist(String personno) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetCustomList";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���

			request.addProperty("personno", personno);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param app
	 *            ��ҵ����
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> GetPictureType(String personno) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetPictureType";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	/**
	 * ��ȡ��Ʒ��Ϣ
	 * 
	 * @param app
	 *            ��ҵ���� *
	 * @return
	 */
	public static List<Map<String, Object>> GetCommList(String personno) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetCommList";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	/**
	 * @param jsonString
	 * @return
	 */
	public static List<Map<String, Object>> getList(String jsonString) {
		List<Map<String, Object>> list = null;
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			JSONObject jsonObject;
			list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				list.add(getMap(jsonObject.toString().trim()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Map<String, Object> getMap(String jsonString) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			@SuppressWarnings("unchecked")
			Iterator<String> keyIter = jsonObject.keys();
			String key;
			Object value;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String GetMD5(String str, String pwd) {
		return getDigestStr(str + pwd);
	}

	public static String getDigestStr(String info) {
		try {
			byte[] res = info.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(res);
			for (int i = 0; i < result.length; i++) {
				md.update(result[i]);
			}
			byte[] hash = md.digest();
			StringBuffer d = new StringBuffer("");
			for (int i = 0; i < hash.length; i++) {
				int v = hash[i] & 0xFF;
				if (v < 16) {
					d.append("0");
				}
				d.append(Integer.toString(v, 16).toUpperCase());
			}
			return d.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @Title: onSaveCustom
	 * @Description:TODO �ͻ���������
	 * @author wangmin
	 * @date 2013-2-21 ����05:15:28
	 * @param @return�趨�ļ�
	 * @return boolean ��������
	 * @throws
	 */
	public static boolean onSaveCustom(Map<String, String> map) {

		boolean success = false;
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "AddCustom";
			// �жϱ����Ƿ�Ϊ�գ���Ϊ����ִ�и��²���
			if (map.get("customCode").trim().length() > 0)
				methodName = "UpdateCustom";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���

			if (methodName.equals("AddCustom"))
				request.addProperty("personno", map.get("personno"));
			else if (methodName.equals("UpdateCustom"))
				request.addProperty("custom_code", map.get("customCode"));
			request.addProperty("custom_name", map.get("customName"));
			request.addProperty("custom_address", map.get("address"));
			request.addProperty("content_person", map.get("person"));
			request.addProperty("tel", map.get("phone"));
			request.addProperty("price_raid_code", map.get("price_raid_code"));
			request.addProperty("custom_levle", map.get("custom_levle"));
			request.addProperty("lat", map.get("lat"));
			request.addProperty("lon", map.get("lon"));
			request.addProperty("time", map.get("time"));
			request.addProperty("key",
					GetMD5(Constants.keycode, map.get("time")));
			request.addProperty("area", map.get("area"));

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 30000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			success = o == null ? false : Boolean.parseBoolean(o.toString());
			return success;
		} catch (Exception ex) {
			Log.e("����", ex.getMessage() + "");
		}
		return success;
	}

	/**
	 * @Title: onSaveCustomStroe
	 * @Description:TODO ����ϱ�
	 * @author WangMin
	 * @date 2013-2-28 ����04:28:15
	 * @param map
	 * @return�趨�ļ�
	 * @return boolean ��������
	 * @throws
	 */
	public static boolean onSaveCustomStroe(String personno, Order_Model mod) {

		boolean success = false;
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "SaveCustom_Stroe";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);
			request.addProperty("custom_code", mod.F_CUSTOM_CODE);
			request.addProperty("comm_code", mod.F_COMM_CODE);
			request.addProperty("comm_name", mod.F_COMM_NAME);
			request.addProperty("num", mod.F_NUM1 + "." + mod.F_NUM2);
			request.addProperty("date", mod.F_DATE);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 30000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			success = o == null ? false : Boolean.parseBoolean(o.toString());
			return success;
		} catch (Exception ex) {
			Log.e("����", ex.getMessage() + "");
		}
		return success;
	}

	/**
	 * �ϴ���Ƭ
	 * 
	 * @param app
	 * @param imageModel
	 * @return
	 */
	public static boolean SubmitImages(String app, ImageModel imageModel) {
		boolean success = false;
		try {
			File pic = new File(imageModel.Path);
			FileInputStream fout;
			fout = new FileInputStream(pic);
			byte[] buffer = new byte[(int) pic.length()];
			int i = fout.read(buffer);
			String ret = new String();
			ret = Base64.encode(buffer); // ����ı��뷽��

			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "SaveImageFile ";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("data", ret);
			request.addProperty("FileLength", pic.length());
			request.addProperty("CustomCode", imageModel.CustomCode);
			request.addProperty("DateTime", imageModel.DateTime);
			request.addProperty("ImageName", imageModel.ImageName);
			request.addProperty("ImageType", imageModel.ImageType);
			request.addProperty("User", imageModel.ImageOpearter);
			request.addProperty("Lon", imageModel.Lat);
			request.addProperty("Lat", imageModel.Lon);
			request.addProperty("F_REMARK", imageModel.F_REMARK);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 30000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			success = o == null ? false : Boolean.parseBoolean(o.toString());
			return success;
		} catch (Exception ex) {
			Log.e("����", ex.getMessage());
		}
		return success;
	}

	/**
	 * ��ȡ����Ȩ��
	 * 
	 * @param app
	 * @return
	 */
	public static List<Map<String, Object>> GetAPP_FUNC(String app,
			String personno) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetAPP_FUNC";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���

			request.addProperty("personno", personno);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	public static String Get_Order_Resut_View(String app, String personno,
			String custom_code, String str, String sendtime, String flag) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "Get_Order_Result";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���

			request.addProperty("personno", personno);
			request.addProperty("custom_code", custom_code);
			request.addProperty("str", str);
			request.addProperty("sendtime", sendtime);
			request.addProperty("flag", flag);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				return o.toString();
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	/**
	 * ��ȡ�۸񼶱�
	 * 
	 * @param app
	 * @return
	 */
	public static List<Map<String, Object>> GetPRICE_RAID(String personno) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetPRICE_RAID";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	/**
	 * ��ȡ�ͻ��ȼ�
	 * 
	 * @param app
	 * @return
	 */
	public static List<Map<String, Object>> GetCUSTOM_LEVEL(String personno) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetCustom_level";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	/**
	 * ��ȡ�ͻ��^��
	 * 
	 * @param app
	 * @return
	 */
	public static List<Map<String, Object>> GetCUSTOM_AREA(String personno) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetCustom_area";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	public static List<Map<String, Object>> GetHistory_Order(String personno,
			String custom_code, int i) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetHistory_Order";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);
			request.addProperty("custom_code", custom_code);
			request.addProperty("lastnum", i);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	public static List<Map<String, Object>> GetCategorys(String personno) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetCategorys";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	public static boolean submit_location(String app, LocationModel location) {
		boolean success = false;
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "Submit_Mylocation";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���

			request.addProperty("imei", location.IMEI);
			request.addProperty("personno", location.PERSONNO);
			request.addProperty("datetime", location.DATETIME);
			request.addProperty("lon", location.LON);
			request.addProperty("lat", location.LAT);
			request.addProperty("speed", location.SPEED);
			request.addProperty("flag", location.FLAG);
			request.addProperty("accuracy", location.RADIUS);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			success = o == null ? false : Boolean.parseBoolean(o.toString());
			return success;

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return success;
	}

	public static String Get_Truck_Resut_View(String personno,
			String custom_code, String str, String flag) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "Get_Truck_Out_Result";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���

			request.addProperty("personno", personno);
			request.addProperty("custom_code", custom_code);
			request.addProperty("str", str);
			request.addProperty("flag", flag);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				return o.toString();
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	public static List<Map<String, Object>> GetProductPic(String app,
			String comm_code) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetProductPic";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("app", app);
			request.addProperty("comm_code", comm_code);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	public static List<Map<String, Object>> GetEnmus(String app) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetEnums";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("app", app);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	public static List<Map<String, Object>> GetCommList_byLastdate(
			String person, String date) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetCommList_by_lastdate";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("person", person);
			request.addProperty("date", date);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	public static String ExchangeComms(String custom_code, String person,
			String comms_old, String comms_new, String flag) {
		boolean success = false;
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetExchageComm_by_custom";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			// request.addProperty("custom_code", map.get("customCode"));
			request.addProperty("custom_code", custom_code);
			request.addProperty("personno", person);
			request.addProperty("comm1", comms_old);
			request.addProperty("comm2", comms_new);
			request.addProperty("flag", flag);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 30000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				return o.toString();
			}
		} catch (Exception ex) {
			Log.e("����", ex.getMessage() + "");
		}
		return null;
	}

	public static String BackComms(String custom_code, String person,
			String comms, String flag) {
		boolean success = false;
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetBackComm_by_custom";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			// request.addProperty("custom_code", map.get("customCode"));
			request.addProperty("custom_code", custom_code);
			request.addProperty("personno", person);
			request.addProperty("comm1", comms);
			request.addProperty("flag", flag);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 30000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				return o.toString();
			}
		} catch (Exception ex) {
			Log.e("����", ex.getMessage() + "");
		}
		return null;
	}

	public static List<Map<String, Object>> GetInfo_by_custom_code(
			String custom_code) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetInfo_by_Code";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("custom_code", custom_code);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}

	/**
	 * @Title:��������
	 * @Description:
	 * @author:
	 * @date:
	 * @param:
	 * @return:
	 */
	public static boolean cancelOrder(String personno, String order_code) {
		boolean success = false;
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "CancelOrder";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("personno", personno);
			request.addProperty("order_code", order_code);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			success = o == null ? false : Boolean.parseBoolean(o.toString());
			return success;

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return success;
	}

	public static boolean SubmitErrorFile(File file, String IMEI) {
		boolean success = false;
		try {
			BufferedReader reader = null;

			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file),
					System.getProperty("file.encoding")));
			StringBuilder builder = new StringBuilder();
			char[] chars = new char[4096];
			int length = 0;
			while (0 < (length = reader.read(chars))) {
				builder.append(chars, 0, length);
			}

			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "SaveErrorFile";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("app", "or");
			request.addProperty("data", builder.toString());
			request.addProperty("FileLength", file.length());
			request.addProperty("IMEI", IMEI);
			request.addProperty("filename", file.getName());

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 50000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			success = o == null ? false : Boolean.parseBoolean(o.toString());
			return success;
		} catch (Exception ex) {
			Log.e("����", "" + ex.getMessage());
		}
		return success;
	}

	public static boolean Attend(String customcode, String personno,
			String lat, String lon, String attendtype) {
		boolean success = false;
		try {

			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "AttendShop";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("customcode", customcode);
			request.addProperty("personno", personno);
			request.addProperty("lat", lat);
			request.addProperty("lon", lon);
			request.addProperty("attendtype", attendtype);

			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 50000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			success = o == null ? false : Boolean.parseBoolean(o.toString());
			return success;
		} catch (Exception ex) {
			Log.e("����", "" + ex.getMessage());
		}
		return success;
	}
	
	public static List<Map<String, Object>> GetSalesMan_Driver(String customcode) {
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "GetPerson_by_Code";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("custom_code", customcode);
			// ����soap�İ汾
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// �����Ƿ���õ���dotNet������
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service����
			hts.call(SOAP_ACTION, envelope);
			// �õ����ؽ��
			Object o = envelope.getResponse();
			if (o != null) {
				List<Map<String, Object>> list = getList(o.toString());
				return list;
			}

		} catch (Exception ex) {
			MyApplication.WriteLog("����" + ex.getMessage());
		}
		return null;
	}
}
