package com.orwlw.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orwlw.activity.R;
import com.orwlw.comm.Constants;
import com.orwlw.model.Comm;
import com.orwlw.model.Comm_Category;
import com.orwlw.model.ImageModel;
import com.orwlw.model.QuestionsBean;

public class ConvertToBean {
	// private final static String
	// serviceURL="http://webalone.orwlw.com/DataBaseUnite.asmx";

	public final static String serviceURL = "http://webalone.orwlw.com/DataBaseUnite.asmx?wsdl";
	public final static String nameSpace = "http://tempuri.org/";

	private SyncHelper syncHelper;
	private Context mContext;

	public ConvertToBean(Context context) {
		// TODO Auto-generated constructor stub
		syncHelper = new SyncHelper();
		this.mContext = context;

	}

	/**
	 * ��������ʾ������б�
	 * 
	 * @param methodName
	 *            ������
	 * @param propertys
	 *            ���Ӳ���
	 * @return
	 */
	public List<QuestionsBean> getAllCheckItem(String methodName,
			HashMap<String, String> propertys) {
		String json = null;
		String soapAction = nameSpace + methodName;
		File file1 = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator
				+ "wenjuan"
				+ File.separator + "question.json");
		if (file1.exists()) {
			json = readTxtFile(file1.toString());
		} else {
			json = syncHelper.getServerMsg(nameSpace, methodName, serviceURL,
					soapAction, propertys);
		}

		// ������
		// String json = readFile();
		System.out.println("json=" + json);
		Type listType = new TypeToken<LinkedList<QuestionsBean>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<QuestionsBean> checkitem = gson.fromJson(json, listType);

		if (checkitem != null)
			System.out.println("��Ŀ" + checkitem.get(0).getF_ITEM_NAME());

		// ͬ���������ļ�
		try {
			saveQuestion(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkitem;
	}

	/*
	 * ���ر����ļ�
	 */
	private void saveQuestion(String content) throws IOException {
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "wenjuan");

		if (!file.exists()) {
			file.mkdirs();
		}
		File file1 = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator
				+ "wenjuan"
				+ File.separator + "question.json");
		FileOutputStream fileOutputStream = new FileOutputStream(file1);// ��ȡ���ļ��������
		OutputStreamWriter outputStream = new OutputStreamWriter(
				fileOutputStream, "gbk");
		outputStream.write(content);// д����
		outputStream.flush();// ��ջ�������������������������д���ļ���
		outputStream.close();// �ر��������ʩ����Դ

	}

	/***
	 * ͬ��������Ʒ������
	 * 
	 * @param methodName
	 *            ��GetComms��
	 * @param propertys
	 *            ��personno��
	 * @return
	 */
	public List<Comm> getAllComm(String methodName,
			HashMap<String, String> propertys) {
		String soapAction = nameSpace + methodName;
		String json = syncHelper.getServerMsg(nameSpace, methodName,
				serviceURL, soapAction, propertys);
		System.out.println("json=" + json);

		Type listType = new TypeToken<LinkedList<Comm>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<Comm> comms = gson.fromJson(json, listType);

		return comms;
	}

	public List<Comm_Category> getAllComm1(String methodName,
			HashMap<String, String> propertys) {
		String soapAction = nameSpace + methodName;
		String json = syncHelper.getServerMsg(nameSpace, methodName,
				serviceURL, soapAction, propertys);
		System.out.println("json=" + json);

		Type listType = new TypeToken<List<Comm_Category>>() {
		}.getType();
		Gson gson = new Gson();
		List<Comm_Category> comms = gson.fromJson(json, listType);
		for (int i = 0; i < comms.size(); i++) {
			System.out.println(comms.get(i).getF_COMM_CATEGORY());
		}
		return comms;
	}

	private String readFile() {
		Resources res = mContext.getResources();
		InputStream in = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		sb.append("");
		try {
			in = res.openRawResource(R.raw.test);
			String str;
			br = new BufferedReader(new InputStreamReader(in, "GBK"));
			while ((str = br.readLine()) != null) {
				sb.append(str);
				sb.append("\n");
			}
		} catch (NotFoundException e) {
			Toast.makeText(mContext, "�ı��ļ�������", 100).show();
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(mContext, "�ı���������쳣", 100).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(mContext, "�ļ���ȡ����", 100).show();
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/*
	 * ɾ���ļ��ķ���
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/*
	 * ��ȡ�ַ���
	 */
	public String readTxtFile(String filePath) {
		StringBuffer sb = new StringBuffer();
		try {
			String encoding = "GBK";
			File file = new File(filePath);

			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt);
				}
				read.close();

			} else {
				System.out.println("�Ҳ���ָ�����ļ�");

			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		return sb.toString();

	}

	/**
	 * �ϴ��᰸��Ƭ
	 * 
	 */
	public boolean SaveJieAn_ImageFile(String ImageName,String ret,int FileLength,
			String CustomCode, String DateTime, String Personno) {
		boolean success = false;
		try {
			String nameSpace = "http://tempuri.org/";
			// ���õķ�����
			String methodName = "SaveJieAn_ImageFile";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			request.addProperty("ImageName", ImageName);
			request.addProperty("data", ret);
			request.addProperty("FileLength", FileLength);
			request.addProperty("CustomCode", CustomCode);
			request.addProperty("DateTime", DateTime);
			request.addProperty("Personno", Personno);
			Log.i("key", ImageName);
			Log.i("data", ret);
			Log.i("FileLength", FileLength + "");
			Log.i("CustomCode", CustomCode);
			Log.i("DateTime", DateTime);
			Log.i("Personno", Personno);
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
	 * �ϴ������Ƭ
	 * 
	 */
	public boolean Submit_check_Images(ImageModel imageModel) {
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
			String methodName = "SaveCheck_ImageFile ";
			// �������������ƿռ����һ��
			String SOAP_ACTION = "http://tempuri.org/" + methodName;
			// ��÷����������
			SoapObject request = new SoapObject(nameSpace, methodName);
			// ������Ҫ�����������Ĳ���
			System.out.println("ѡ������" + pic.length());
			request.addProperty("data", ret);
			request.addProperty("FileLength", pic.length());
			request.addProperty("CustomCode", imageModel.CustomCode);
			request.addProperty("DateTime", imageModel.DateTime);
			request.addProperty("ImageName", imageModel.ImageName);
			request.addProperty("Personno", imageModel.ImageOpearter);

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

}