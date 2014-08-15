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
 * ��ȡjson���ݵĹ�����
 * 
 * @author Administrator
 * 
 */

public class SyncHelper {

	public SyncHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ��÷�������json����
	 * 
	 * @param nameSpace
	 *            ���õķ�����
	 * @param methodName
	 *            �������������ƿռ����һ��
	 * @param serviceURL
	 *            WSDL�ĵ�URL
	 * @param soapAction
	 *            ��÷����������
	 * @param propertys
	 *            ������Ҫ�����������Ĳ���
	 * @return
	 */
	public String getServerMsg(String nameSpace, String methodName,
			String serviceURL, String soapAction,
			HashMap<String, String> propertys) {


		// ��÷����������
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

}
