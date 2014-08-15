package com.orwlw.comm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.orwlw.model.Entity;

public class DatabaseHelper extends SQLiteOpenHelper {
	private Context mContext;
	private SQLiteDatabase mDatabase;
	public static final String DB_NAME = "data.db";// 数据库名

	public DatabaseHelper(Context paramContext) {
		super(paramContext, DB_NAME, null, 1);
		this.mContext = paramContext;
	}

	public SQLiteDatabase getmDatabase() {
		return this.mDatabase;
	}

	public void insert(Entity paramEntity) {
		open(true);
		ContentValues localContentValues = new ContentValues();
		setEntityToValues(paramEntity, localContentValues);
		if ((this.mDatabase != null) && (this.mDatabase.isOpen()))
			this.mDatabase.insert(paramEntity.getTableName(), null,
					localContentValues);
		close();
	}

	public void delete(Entity paramEntity) {
		open(true);
		if (this.mDatabase != null)
			this.mDatabase.delete(paramEntity.getTableName(),
					getWhereClause(paramEntity), null);
		close();
	}

	public void delete(String paramString1, String paramString2,
			String[] paramArrayOfString) {
		open(true);
		if (this.mDatabase != null)
			this.mDatabase.delete(paramString1, paramString2,
					paramArrayOfString);
		close();
	}

	public void deleteAll(String paramString) {
		open(true);
		if (this.mDatabase != null)
			this.mDatabase.execSQL("DELETE FROM " + paramString);
		close();
	}

	public boolean deleteDatabase(Context paramContext) {
		return paramContext.deleteDatabase(DB_NAME);
	}

	public void update(Entity paramEntity) {
		open(true);
		ContentValues localContentValues = new ContentValues();
		setEntityToValues(paramEntity, localContentValues);
		if (this.mDatabase != null)
			this.mDatabase.update(paramEntity.getTableName(),
					localContentValues, getWhereClause(paramEntity), null);
		close();
	}

	public void update(String paramString1, ContentValues paramContentValues,
			String paramString2, String[] paramArrayOfString) {
		open(true);
		if (this.mDatabase != null)
			this.mDatabase.update(paramString1, paramContentValues,
					paramString2, paramArrayOfString);
		close();
	}

	public List<? extends Entity> select(int paramInt,
			String[] paramArrayOfString) {
		open(true);
		String str = this.mContext.getString(paramInt);
		ArrayList localArrayList = new ArrayList();
		Cursor localCursor = null;
		try {
			Matcher localMatcher = Pattern.compile(
					"\\s+from\\s+(.+)\\s+(where)|$", 2).matcher(str);
			if (localMatcher.find()) {
				localCursor = this.mDatabase.rawQuery(str, paramArrayOfString);
				localArrayList = setCursorToList(localMatcher.group(1),
						localCursor);
			}			
		} catch (Exception localException) {
			close();
		} finally {
			if (localCursor != null)
				localCursor.close();
			close();
		}
		return localArrayList;

	}

	public int selectCount(int paramInt, String[] paramArrayOfString) {
		open(true);
		int i = 0;
		Object localObject = this.mContext.getString(paramInt);
		localObject = this.mDatabase.rawQuery((String) localObject,
				paramArrayOfString);
		if (((Cursor) localObject).moveToNext())
			i = ((Cursor) localObject).getInt(0);
		close();
		return i;
	}

	private String getWhereClause(Entity paramEntity) {
		StringBuilder localStringBuilder = new StringBuilder();
		String[] arrayOfString;
		int j;
		if (paramEntity.getKeys() != null) {
			arrayOfString = paramEntity.getKeys();
			int i = arrayOfString.length;
			j = 0;
			while (j < i) {
				Object localObject = arrayOfString[j];
				localStringBuilder.append(" AND ");
				localStringBuilder.append((String) localObject);
				localStringBuilder.append("='");
				try {
					localObject = paramEntity.getClass().getDeclaredField(
							(String) localObject);
					((Field) localObject).setAccessible(true);
					localStringBuilder.append(String
							.valueOf(((Field) localObject).get(paramEntity)));
					localStringBuilder.append("'");
					j++;
				} catch (Exception localException) {

				}
			}
		}
		return localStringBuilder.toString();

	}

	private ArrayList setCursorToList(String paramString, Cursor paramCursor) {
		ArrayList paramList = new ArrayList();
		Field[] arrayOfField;
		int i = 0;
		Object localObject = paramString;
		// .replaceAll("_", "").toLowerCase();
		Class localClass = null;
		try {
			localClass = Class.forName(this.mContext.getPackageName()
					+ ".com.orwlw.model.".concat((String) localObject).concat(
							"Entity"));
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		arrayOfField = localClass.getDeclaredFields();
		if (paramCursor.moveToNext()) {
			try {
				localObject = (Entity) localClass.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int j = arrayOfField.length;
			while (i < j) {
				Field localField = arrayOfField[i];
				localField.setAccessible(true);
				int k = paramCursor.getColumnIndex(localField.getName()
						.toUpperCase());
				if (k == -1)
					continue;
				String str = localField.getType().getSimpleName();

				try {
					if ("String".equals(str)) {
						localField.set(localField.getName().toUpperCase(),
								paramCursor.getString(k));
					}
					if (("int".equals(str)) || ("Integer".equals(str))) {
						localField.set(localField.getName().toUpperCase(),
								Integer.valueOf(paramCursor.getInt(k)));
					}
					if (("double".equals(str)) || ("Double".equals(str))) {
						localField.set(localField.getName().toUpperCase(),
								Double.valueOf(paramCursor.getDouble(k)));
					}
					if ("byte[]".equals(str)) {
						localField.set(localField.getName().toUpperCase(),
								paramCursor.getBlob(k));
					}
					if (("long".equals(str)) || ("Long".equals(str))) {
						localField.set(localField.getName().toUpperCase(),
								Long.valueOf(paramCursor.getLong(k)));
					}
					localField.set(localField.getName().toUpperCase(), null);

					i++;
					paramList.add((Entity) localObject);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return paramList;

	}

	private void setEntityToValues(Entity paramEntity,
			ContentValues paramContentValues) {
		int j;
		int i;
		String str;
		try {
			Field[] arrayOfField = paramEntity.getClass().getDeclaredFields();
			j = arrayOfField.length;
			i = 0;

			while (i < j) {
				Field localField = arrayOfField[i];
				localField.setAccessible(true);

				str = localField.getType().getSimpleName();
				if ("String".equals(str)) {
					paramContentValues.put(localField.getName(),
							(String) localField.get(paramEntity));
				}
				if ("String[]".equals(str))
					paramContentValues
							.put(localField.getName(), ((String[]) localField
									.get(paramEntity)).toString());
				if (("int".equals(str)) || ("Integer".equals(str)))
					paramContentValues.put(localField.getName(),
							Integer.valueOf(localField.getInt(paramEntity)));
				else if (("double".equals(str)) || ("Double".equals(str)))
					paramContentValues.put(localField.getName(),
							Double.valueOf(localField.getDouble(paramEntity)));
				else if ("byte[]".equals(str))
					paramContentValues.put(localField.getName(),
							(byte[]) localField.get(paramEntity));
				else if (("long".equals(str)) || ("Long".equals(str)))
					paramContentValues.put(localField.getName(),
							Long.valueOf(localField.getLong(paramEntity)));
				else
					paramContentValues.put(localField.getName(), "");
			}

		} catch (Exception localException) {

		}

	}

	public void SetContent(Context paramContext) {
		this.mContext = paramContext;
	}

	public void open(boolean paramBoolean) {
		if (!paramBoolean)
			this.mDatabase = getReadableDatabase();
		else
			this.mDatabase = getWritableDatabase();
	}

	public void close() {
		close(true);
	}

	public void close(boolean paramBoolean) {
		if (this.mDatabase != null) {
			if (paramBoolean)
				this.mDatabase.isReadOnly();
			super.close();
			this.mDatabase = null;
		}
	}

	public void doSubmit() {
		if (!this.mDatabase.isReadOnly()) {
			this.mDatabase.setTransactionSuccessful();
			this.mDatabase.endTransaction();
		}
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		// 本地照片表
		paramSQLiteDatabase.execSQL(DBfinal.SQL_IMAGE_ERRINFO);
		// 照片类型表
		paramSQLiteDatabase.execSQL(DBfinal.SQL_IMAGE_TYPE);
		// 常用商品表
		paramSQLiteDatabase.execSQL(DBfinal.SQL_COMM_USED);
		paramSQLiteDatabase.execSQL(DBfinal.SQL_SCM_CATEGORY);
		// 离线基站表
		paramSQLiteDatabase.execSQL(DBfinal.SQL_Device_info);
		// 客户信息表 联系人，客户编号，客户名称，地址，电话，经度，纬度。
		paramSQLiteDatabase.execSQL(DBfinal.SQL_Custom_info);
		// 本地订货商品表
		paramSQLiteDatabase.execSQL(DBfinal.SQL_LOCAL_ORDER);
		// 换货表
		paramSQLiteDatabase.execSQL(DBfinal.SQL_EXCHAGNE_COMMS);
		// 退货表
		paramSQLiteDatabase.execSQL(DBfinal.SQL_BACK_COMMS);
		// 枚举表
		paramSQLiteDatabase.execSQL(DBfinal.SQL_ENUM);
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
	}

	public void startTrans(boolean paramBoolean) {
		if (!paramBoolean) {
			this.mDatabase = getReadableDatabase();
		} else {
			this.mDatabase = getWritableDatabase();
			this.mDatabase.beginTransaction();
		}
	}

}