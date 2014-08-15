package com.orwlw.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.orwlw.comm.Constants;
import com.orwlw.comm.MyApplication;

/**
 * 获取json数据的公共类
 * 
 * @author Administrator
 * 
 */

public class SyncHelper {

	public SyncHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获得服务器端json数据
	 * 
	 * @param nameSpace
	 *            调用的方法名
	 * @param methodName
	 *            将方法名和名称空间绑定在一起
	 * @param serviceURL
	 *            WSDL文档URL
	 * @param soapAction
	 *            获得返回请求对象
	 * @param propertys
	 *            设置需要返回请求对象的参数
	 * @return
	 */
	public String getServerMsg(String nameSpace, String methodName,
			String serviceURL, String soapAction,
			HashMap<String, String> propertys) {


		// 获得返回请求对象
		SoapObject request = new SoapObject(nameSpace, methodName);

		if (propertys != null) {
			Iterator<Entry<String, String>> iter = propertys.entrySet()
					.iterator();
			while (iter.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				 System.out.println("key==="+key);
				request.addProperty(key, value);
			}

		}

		

		try {

			String SOAP_ACTION = "http://tempuri.org/" + methodName;


			request.addProperty("personno", "or0001");

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// 设置是否调用的是dotNet开发的
			envelope.dotNet = true;
			//
			envelope.bodyOut = request;
			HttpTransportSE hts = new HttpTransportSE(
					Constants.webserverString, 15000);
			// web service请求
			hts.call(SOAP_ACTION, envelope);
			// 得到返回结果
			Object o = envelope.getResponse();
			if (o != null) {
				return o.toString();
			}
		} catch (Exception ex) {
			MyApplication.WriteLog("错误" + ex.getMessage());
		}
		return null;
	}

}
