package com.orwlw.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orwlw.comm.Constants;
import com.orwlw.comm.ItemCheckAdapter;
import com.orwlw.comm.ItemNumberAdapter;
import com.orwlw.comm.ItemQuestionAdapter;
import com.orwlw.comm.Item_CommAdapter;
import com.orwlw.comm.ItemwenziAdapter;
import com.orwlw.comm.MyApplication;
import com.orwlw.comm.PictureAdapter;
import com.orwlw.model.CheckItem;
import com.orwlw.model.CheckResult;
import com.orwlw.model.CheckResultItem;
import com.orwlw.model.DataBean;
import com.orwlw.model.DataBeanToo;
import com.orwlw.model.QuestionsBean;
import com.orwlw.model.ResultJsonBean;
import com.orwlw.net.ConvertToBean;

public class ItemQuestionActivity extends Activity {

	private QuestionsBean questionsBean;
	public ListView listView;
	private List<String> list;// ѡ��
	private String style;// ����
	private Button main_sub;
	private Button btn_photo, btn_add;// ��Ŀ�Աߵ� ������Ʒ����Ƭ
	private List<String> list_img;
	private List<String> list_img_all;
	private List<String> list_comm_all;
	private GridView gridView;
	private PictureAdapter Img_adapter;
	private Item_CommAdapter commAdapter;
	private String choicename = null;// ÿ��ѡ��
	private List<String> list_comm;
	private ListView lv_comm;
	private List<String> item_name;// ����ѡ������
	private List<ResultJsonBean> resultBean;
	private ItemNumberAdapter numberAadapter;// ������������
	private String a[][];
	private int count = 0;
	private int position = 0;// ��ѡ���߶�ѡ�ĵڼ���ѡ��
	private String fileName;// ͼƬ����
	private List<DataBean> list_num;// ������(EditText,textView)
	private List<DataBeanToo> list_rc;// ��Ƭ
	private String comm_name;// ��Ʒ����
	private String json = "";// �ύ���ص�

	List<CheckResultItem> list_cheCheckResultItems = new ArrayList<CheckResultItem>();
	List<DataBean> list_etnum;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 2) {
				position = msg.arg1;
				System.out.println("λ�ã�" + position);
			} else if (msg.what == 1) {
				choicename = (String) msg.obj;
				System.out.println("ѡ������" + choicename);
				// ��ѡ���߶�ѡ��ѡ������
				if (choicename != null) {
					item_name.add(choicename);
				}
			} else if (msg.what == 3) {
				// list_cheCheckResultItems = (List<CheckResultItem>) msg.obj;
				// System.out.println("���ָ�����" +
				// list_cheCheckResultItems.size());
				list_etnum = (List<DataBean>) msg.obj;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		// ��ȡ�������ļ����Լ��ؼ���ʼ��
		init();
		style = questionsBean.getF_ITEM_TYPE();// ��Ŀ����
		list = new ArrayList<String>();
		setInformation();
		gridView.setOnItemClickListener(gridItemClickListener);
		btn_photo.setOnClickListener(onClickListener);
		btn_add.setOnClickListener(onClickListener);
		lv_comm.setOnItemClickListener(commItemClickListener);
		main_sub.setOnClickListener(onClickListener);
	}

	public void init() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bdu");
		questionsBean = (QuestionsBean) bundle.get("list");
		list_img = new ArrayList<String>();
		list_comm = new ArrayList<String>();
		list_img_all = new ArrayList<String>();
		list_comm_all = new ArrayList<String>();
		listView = (ListView) findViewById(R.id.list_view);
		lv_comm = (ListView) findViewById(R.id.question_list_comm);
		TextView textView = (TextView) findViewById(R.id.question_textview);
		btn_add = (Button) findViewById(R.id.question_comm);
		btn_photo = (Button) findViewById(R.id.question_photo);
		gridView = (GridView) findViewById(R.id.question_gridview);
		main_sub = (Button) findViewById(R.id.main_sub);

		textView.setText(questionsBean.getF_ITEM_NAME());// ����Ŀ��������������
		item_name = new ArrayList<String>();// ѡ������
		a = new String[100][4];
		// �����Ƭ
		list_rc = new ArrayList<DataBeanToo>();

		// �������õ���list
		list_num = new ArrayList<DataBean>();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			// ˢ��
			if (style.equals("3")) {
				numberAadapter.notifyDataSetChanged();
			} else {
				Img_adapter.notifyDataSetChanged();
				commAdapter.notifyDataSetChanged();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2) {
			String sd_Status = Environment.getExternalStorageState();
			if (!sd_Status.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
				return;
			}
			try {
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");
				FileOutputStream b = null;
				File file = new File(
						"/sdcard/myImage/"
								+ ((MyApplication) getApplication())
										.Getlocaldata().current_custom_code);
				if (!file.exists()) {
					file.mkdirs();
				}
				// ������Ʒ����-��Ƭlist
				// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				// Date date = new Date();
				// String time = format.format(date);

				String img_name = null;
				UUID guid = UUID.randomUUID();
				img_name = guid + ".jpg";

				fileName = "/sdcard/myImage/"
						+ ((MyApplication) getApplication()).Getlocaldata().current_custom_code
						+ "/" + img_name;
				b = new FileOutputStream(fileName);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
				list_img_all.add(img_name);

				if (style.equals("3")) { // ������
					DataBean dataBean = list_num.get(position);
					dataBean.setImage(fileName);
				} else {
					// ��ѡ����ѡ����Ƭ��ѡ���Ʒ
					DataBeanToo dataBeanToo = new DataBeanToo();
					dataBeanToo.setName(fileName);
					if (choicename != null && !choicename.equals("")) {
						dataBeanToo.setInfo(choicename + "-" + comm_name);
					} else {
						dataBeanToo.setInfo(null);
					}
					list_rc.add(dataBeanToo);
					if (choicename.equalsIgnoreCase("")) {
						list_img.add(img_name);
					} else {
						a[count][3] = img_name;
					}
				}

				// comm_name = "";
				b.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (requestCode == 3) {
			if (data != null) {
				// �����ȡ
				String code = data.getStringExtra("code");
				// ���ƻ�ȡ
				String name = data.getStringExtra("name");

				// �����⣬��Ʒ
				if (style.equals("3")) {
					list_comm.add("");
					DataBean number = new DataBean();
					number.setName(name);
					list_num.add(number);
				} else {
					list_comm.add(name);
					list_comm_all.add(name);
				}
			}
		} else if (requestCode == 4) {
			count++;
			// �����ȡ
			String code = data.getStringExtra("code");
			// ���ƻ�ȡ
			String name = data.getStringExtra("name");
			System.out.println("code" + code + "---name" + name);
			list_comm_all.add(name);
			a[count][0] = String.valueOf(count);
			a[count][1] = choicename;
			a[count][2] = name;
			comm_name = name;
			// list_comm.add(name); //
			// �ж���Ʒ�������Ƿ������ѡ����Ʒ�����գ�
			if (questionsBean.isF_COMM_PHOTO_REALATION()) {
				into_capture(2);
			}
			name = null;
		}
	}

	// ��������ѡ����Ӧ��������
	public void setInformation() {
		if (style.equals("1")) {// ��ѡ
			addItemName();
			ItemQuestionAdapter adapter = new ItemQuestionAdapter(
					questionsBean, ItemQuestionActivity.this, list, this,
					handler);
			listView.setAdapter(adapter);

			Img_adapter = new PictureAdapter(list_rc,
					ItemQuestionActivity.this, questionsBean);
			gridView.setAdapter(Img_adapter);
			commAdapter = new Item_CommAdapter(list_comm,
					ItemQuestionActivity.this);
			lv_comm.setAdapter(commAdapter);
			setPhoto_Comm_Visible();
			// }

		} else if (style.equals("2")) {// ��ѡ
			addItemName();
			ItemCheckAdapter adapter = new ItemCheckAdapter(questionsBean,
					ItemQuestionActivity.this, list, this, handler);
			listView.setAdapter(adapter);

			Img_adapter = new PictureAdapter(list_rc,
					ItemQuestionActivity.this, questionsBean);
			gridView.setAdapter(Img_adapter);
			commAdapter = new Item_CommAdapter(list_comm,
					ItemQuestionActivity.this);
			lv_comm.setAdapter(commAdapter);
			setPhoto_Comm_Visible();
			// }
		} else if (style.equals("3")) {// ����
			// ��Ʒ��ͼƬ�б����ɼ�
			lv_comm.setVisibility(View.GONE);
			gridView.setVisibility(View.GONE);
			numberAadapter = new ItemNumberAdapter(questionsBean,
					ItemQuestionActivity.this, list_num, this, handler);
			listView.setAdapter(numberAadapter);
			setPhoto_Comm_Visible();

		} else if (style.equals("4")) {// ����
			addItemName();
			ItemwenziAdapter adapter = new ItemwenziAdapter(questionsBean,
					ItemQuestionActivity.this);
			listView.setAdapter(adapter);
			setPhoto_Comm_Visible();
		} else if (style.equals("0")) {// ������
			Img_adapter = new PictureAdapter(list_rc,
					ItemQuestionActivity.this, questionsBean);
			gridView.setAdapter(Img_adapter);
			commAdapter = new Item_CommAdapter(list_comm,
					ItemQuestionActivity.this);
			lv_comm.setAdapter(commAdapter);
			setPhoto_Comm_Visible();
		}
	}

	// ����+������Ʒ�ɼ��� �Ƿ������Ƭ����Ʒ
	public void setPhoto_Comm_Visible() {
		// ����
		if (!questionsBean.isF_PHOTO_NEED()) {
			btn_photo.setVisibility(View.GONE);
		}
		// ������Ʒ
		if (!questionsBean.isF_COMM_LINKED()) {
			btn_add.setVisibility(View.GONE);
		}
	}

	public void addItemName() {
		for (int i = 0; i < questionsBean.getF_ITEMS().size(); i++) {
			list.add(questionsBean.getF_ITEMS().get(i).getF_ITEM_SELECTION());
		}
	}

	// ɾ����Ƭ�ĵ���¼�
	private OnItemClickListener gridItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int arg2,
				long arg3) {
			list_rc.remove(arg2);
			Img_adapter.notifyDataSetChanged();
		}
	};
	/*
	 * ɾ����Ʒ
	 */
	private OnItemClickListener commItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			int postion = arg2;
			list_comm.remove(postion);
			commAdapter.notifyDataSetChanged();

		}
	};
	/*
	 * ����
	 */
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.question_photo:
				into_capture(2);
				choicename = "";
				if (choicename == null) {
					item_name.add("");
				}
				break;
			case R.id.question_comm:
				((MyApplication) getApplication()).SavelocalInt(
						"CURRENT_PARAMS", Constants.SUVERY_ACTION_SELECT_COMMS);
				into_SlidingActivity(3);
				break;
			case R.id.main_sub:
				submain();
				break;
			default:
				break;
			}

		}

	};

	/*
	 * ��ת����
	 */
	private void into_capture(int id) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(intent, id);
	}

	/*
	 * ��ת��Ʒҳ��-��ȡ��Ʒ
	 */
	private void into_SlidingActivity(int id) {
		Intent intent = new Intent(ItemQuestionActivity.this,
				SlidingActivity.class);
		startActivityForResult(intent, id);
	}

	/**
	 * �ύ����
	 **/
	public void submain() {
		resultBean = new ArrayList<ResultJsonBean>();
		CheckItem item = new com.orwlw.model.CheckItem();
		item.setId(questionsBean.getF_ID());
		item.setType(questionsBean.getF_ITEM_TYPE());
		item.setF_COMM_LINKED(questionsBean.isF_COMM_LINKED());
		item.setF_COMM_PHOTO_REALATION(questionsBean.isF_COMM_PHOTO_REALATION());
		item.setF_PHOTO_NEED(questionsBean.isF_PHOTO_NEED());

		List<CheckResultItem> select = new ArrayList<CheckResultItem>();
		if (questionsBean.getF_ITEM_TYPE().equals("1")) {
			// ��ѡѡ������
			for (int j = 0; j < questionsBean.getF_ITEMS().size(); j++) {
				View view2 = (View) listView.getChildAt(j);
				RadioButton tt = (RadioButton) view2.findViewById(R.id.radio);
				boolean m = tt.isChecked();
				int count1 = 0;
				if (count > 0) {
					if (m) {// ĳ�ѡ��
						for (int r = 0; r <= count; r++) {
							if (a[r][1] == questionsBean.getF_ITEMS().get(j)
									.getF_ITEM_SELECTION()) {
								count1++;
								// �����ѡ��
								CheckResultItem citem = new CheckResultItem();
								citem.setComm(a[r][2]);
								citem.setImage(a[r][3]);
								citem.setResult(questionsBean.getF_ITEMS()
										.get(j).getF_ID());
								citem.setCategory("");
								citem.setcheck(true);
								select.add(citem);
							}
						}

						if (count1 == 0) {
							// �����ѡ��
							CheckResultItem citem = new CheckResultItem();
							citem.setComm("");
							citem.setImage("");
							citem.setResult(questionsBean.getF_ITEMS().get(j)
									.getF_ID());
							citem.setCategory("");
							citem.setcheck(true);
							select.add(citem);
						}
					} else {
						for (int r = 0; r <= count; r++) {
							if (a[r][1] == questionsBean.getF_ITEMS().get(j)
									.getF_ITEM_SELECTION()) {
								// �����ѡ��
								count1++;
								CheckResultItem citem = new CheckResultItem();
								citem.setComm(a[r][2]);
								citem.setImage(a[r][3]);
								citem.setResult(questionsBean.getF_ITEMS()
										.get(j).getF_ID());
								citem.setCategory("");
								citem.setcheck(false);
								select.add(citem);
							}
						}
						if (count1 == 0) {
							// �����ѡ��
							CheckResultItem citem = new CheckResultItem();
							citem.setComm("");
							citem.setImage("");
							citem.setResult(questionsBean.getF_ITEMS().get(j)
									.getF_ID());
							citem.setCategory("");
							citem.setcheck(false);
							select.add(citem);
						}
					}
				} else {
					if (m) {// ĳ�ѡ��
							// �����ѡ��
						CheckResultItem citem = new CheckResultItem();
						citem.setComm("");
						citem.setImage("");
						citem.setResult(questionsBean.getF_ITEMS().get(j)
								.getF_ID());
						citem.setCategory("");
						citem.setcheck(true);
						select.add(citem);
					} else {
						// �����ѡ��
						CheckResultItem citem = new CheckResultItem();
						citem.setComm("");
						citem.setImage("");
						citem.setResult(questionsBean.getF_ITEMS().get(j)
								.getF_ID());
						citem.setCategory("");
						citem.setcheck(false);
						select.add(citem);
					}

				}
			}
		}
		List<CheckResultItem> check = new ArrayList<CheckResultItem>();
		if (questionsBean.getF_ITEM_TYPE().equals("2")) {

			for (int j = 0; j < questionsBean.getF_ITEMS().size(); j++) {
				View view2 = (View) listView.getChildAt(j);
				CheckBox tt = (CheckBox) view2.findViewById(R.id.check);
				boolean m = tt.isChecked();
				int count2 = 0;
				if (count > 0) {
					if (m) {// ĳ�ѡ��
						for (int r = 0; r <= count; r++) {
							if (a[r][1] == questionsBean.getF_ITEMS().get(j)
									.getF_ITEM_SELECTION()) {
								// �����ѡ��
								count2++;
								CheckResultItem citem = new CheckResultItem();
								citem.setComm(a[r][2]);
								citem.setImage(a[r][3]);
								citem.setResult(questionsBean.getF_ITEMS()
										.get(j).getF_ID());
								citem.setCategory("");
								citem.setcheck(true);
								select.add(citem);
							}

						}
						if (count2 == 0) {
							// �����ѡ��
							CheckResultItem citem = new CheckResultItem();
							citem.setComm("");
							citem.setImage("");
							citem.setResult(questionsBean.getF_ITEMS().get(j)
									.getF_ID());
							citem.setCategory("");
							citem.setcheck(true);
							select.add(citem);
						}

					} else {
						for (int r = 0; r <= count; r++) {
							if (a[r][1] == questionsBean.getF_ITEMS().get(j)
									.getF_ITEM_SELECTION()) {
								// �����ѡ��
								count2++;
								CheckResultItem citem = new CheckResultItem();
								citem.setComm(a[r][2]);
								citem.setImage(a[r][3]);
								citem.setResult(questionsBean.getF_ITEMS()
										.get(j).getF_ID());
								citem.setCategory("");
								citem.setcheck(false);
								select.add(citem);
							}

						}

					}
					if (count2 == 0) {
						// �����ѡ��
						CheckResultItem citem = new CheckResultItem();
						citem.setComm("");
						citem.setImage("");
						citem.setResult(questionsBean.getF_ITEMS().get(j)
								.getF_ID());
						citem.setCategory("");
						citem.setcheck(false);
						select.add(citem);
					}
				} else {

					if (m) {// ĳ�ѡ��
							// �����ѡ��
						CheckResultItem citem = new CheckResultItem();
						citem.setComm("");
						citem.setImage("");
						citem.setResult(questionsBean.getF_ITEMS().get(j)
								.getF_ID());
						citem.setCategory("");
						citem.setcheck(true);
						select.add(citem);
					} else {
						// �����ѡ��
						CheckResultItem citem = new CheckResultItem();
						citem.setComm("");
						citem.setImage("");
						citem.setResult(questionsBean.getF_ITEMS().get(j)
								.getF_ID());
						citem.setCategory("");
						citem.setcheck(false);
						select.add(citem);
					}

				}
			}
		}
		List<CheckResultItem> text = new ArrayList<CheckResultItem>();
		if (questionsBean.getF_ITEM_TYPE().equals("4")) {
			View view2 = (View) listView;
			EditText msg = (EditText) view2.findViewById(R.id.wenzi_textview);
			System.out.println("����" + msg.getText());
			CheckResultItem citem = new CheckResultItem();
			citem.setResult(msg.getText().toString());
			text.add(citem);
			for (int r = 0; r < count; r++) {
				citem.setComm("");
				citem.setImage("");
				citem.setResult("");
				citem.setcheck(false);
				citem.setCategory("");
				text.add(citem);
			}

		}
		List<CheckResultItem> check1 = new ArrayList<CheckResultItem>();
		if (questionsBean.getF_ITEM_TYPE().equals("3")) {
			View view2 = (View) listView;
			EditText msg = (EditText) view2.findViewById(R.id.number_Comm_edit);
			System.out.println("����" + msg.getText());
			CheckResultItem citem = new CheckResultItem();

			for (int i = 0; i < list_etnum.size(); i++) {
				System.out.println(list_etnum.get(i).getInfo());
				CheckResultItem checkResultItem = new CheckResultItem();
				checkResultItem.setResult(list_etnum.get(i).getInfo());
				checkResultItem.setImage(list_etnum.get(i).getImage());
				checkResultItem.setComm(list_etnum.get(i).getName());
				checkResultItem.setcheck(false);
				check1.add(checkResultItem);
			}

		}
		if (questionsBean.getF_ITEM_TYPE().equals("0")) {
			View view2 = (View) listView;
			EditText msg = (EditText) view2.findViewById(R.id.wenzi_textview);
			CheckResultItem citem = new CheckResultItem();

			for (int r = 0; r < count; r++) {
				citem.setComm("");
				citem.setImage("");
				citem.setResult("");
				citem.setcheck(false);
				citem.setCategory("");
				text.add(citem);
			}

		}

		List<String> img = new ArrayList<String>();
		List<String> comm = new ArrayList<String>();
		for (int r = 0; r < list_img.size(); r++) {
			String m = list_img.get(r);
			String mm = m.substring(m.lastIndexOf("/") + 1);
			img.add(mm);
			System.out.println(m);
		}
		for (int r1 = 0; r1 < list_comm.size(); r1++) {
			String m = list_comm.get(r1);
			comm.add(m);
			System.out.println(m);

		}

		CheckResult result1 = new CheckResult();
		result1.setBrandId("22222");
		result1.setComms(comm);
		result1.setImages(img);

		result1.setNumResult(check1);
		result1.setSelectResult(select);
		result1.setTextResult(text);
		ResultJsonBean bean = new ResultJsonBean();
		bean.setCheckItem(item);
		bean.setCheckResult(result1);
		resultBean.add(bean);
		Gson gson = new Gson();
		System.out.println("�ύ���json===" + gson.toJson(resultBean));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = format.format(date);
		/* webservice�ύ���� */
		try { // ��׽�쳣
			HashMap<String, String> propertys = new HashMap<String, String>();
			// ����
			propertys
					.put("custom_code",
							((MyApplication) getApplication()).Getlocaldata().current_custom_code);
			propertys.put("personno",
					((MyApplication) getApplication()).Getlocaldata().personno);
			propertys.put("result", gson.toJson(resultBean));
			propertys.put("date", time);
			// �����ռ䣬����������ַ�������ռ�+��������soapAction = nameSpace + methodName;����
			json = new com.orwlw.net.SyncHelper().getServerMsg(
					ConvertToBean.nameSpace, "SubmitCheckResult",
					ConvertToBean.serviceURL, ConvertToBean.nameSpace
							+ "SubmitCheckResult", propertys);
			System.out.println("�������Ľ��" + json);
			if (json.equals("true")) {
				Toast.makeText(ItemQuestionActivity.this, "�ύ�ɹ�",
						Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(ItemQuestionActivity.this, "�ύʧ��",
						Toast.LENGTH_SHORT).show();
				json = null;
				json.indexOf(4);
			}

		} catch (NullPointerException e) {
			File file1 = new File("/sdcard/Cache");
			file1.mkdirs();
			File file = new File("/sdcard/Cache/"
					+ questionsBean.getF_ITEM_NAME() + ".json");
			RandomAccessFile mm = null;
			try {
				mm = new RandomAccessFile(file, "rw");
				mm.writeBytes(gson.toJson(resultBean));
			} catch (IOException e1) {
				// TODO �Զ����� catch ��
				e1.printStackTrace();

			} finally {
				if (mm != null) {
					try {
						mm.close();
					} catch (IOException e2) {
						// TODO �Զ����� catch ��
						e2.printStackTrace();
					}
				}

			}
		}

	}

}