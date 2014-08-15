package com.orwlw.comm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.os.storage.StorageManager;

public class StorageList {
	private Context mcontext;
	private StorageManager mStorageManager;
	private Method mMethodGetPaths;

	public StorageList(Context context) {
		mcontext = context;
		if (mcontext != null) {
			mStorageManager = (StorageManager) mcontext
					.getSystemService(Activity.STORAGE_SERVICE);
			try {
				mMethodGetPaths = mStorageManager.getClass().getMethod(
						"getVolumePaths");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public String[] getVolumePaths() {
		String[] paths = null;
		try {
			paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return paths;
	}
}