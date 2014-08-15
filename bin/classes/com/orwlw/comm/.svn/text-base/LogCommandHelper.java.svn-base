package com.orwlw.comm;

import java.io.*;

import android.util.Log;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;
import android.content.ContentResolver;
import android.content.Context;
import android.os.*;

public class LogCommandHelper
{

	public LogCommandHelper()
	{
		// TODO Auto-generated constructor stub
	}

	public static String GetDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}

	public static String GetTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		return date;
	}

	public static String GetTime(Locale locale)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
		String date = sdf.format(new Date());
		return date;
	}

	private static Boolean CreateFile(String savedText, CmdType type, String Code)
	{
		String path = "";
		String sDStateString = android.os.Environment.getExternalStorageState();
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED))
		{
			try
			{
				File SDFile = android.os.Environment.getExternalStorageDirectory();

				String dirPath = MessageFormat.format(SDFile.getAbsolutePath() + File.separator + "Commands/{0}/{1}", type.toString(), Code);
				path = MessageFormat.format(SDFile.getAbsolutePath() + File.separator + "Commands/{0}/{1}/{2}", type.toString(), Code, GetDate() + ".txt");

				File comFile = new File(path);

				File dir = new File(dirPath);

				if (!dir.exists())
				{
					dir.mkdirs();
				}

				if (!comFile.exists())
				{
					comFile.createNewFile();

					File[] fileArray = dir.listFiles();

					if (fileArray != null)
					{

						for (int i = 0; i < fileArray.length; i++)
						{
							String fname = fileArray[i].getName().substring(0, fileArray[i].getName().indexOf(".") - 1);
							try
							{
								Date now = new Date(Date.parse(fname));
								if ((new Date()).getDate() - now.getDate() > 7)// delete
																				// files
																				// which
																				// are
																				// created
																				// 10
																				// days
																				// ago
								{
									fileArray[i].delete();
								}
							} catch (Exception e)
							{

							}
						}
					}
				}

				FileOutputStream fout = new FileOutputStream(comFile, true);
				String content = EncodingUtils.getString(savedText.getBytes(), "UTF-8"); // 这个也能把字符数组转码制！
				fout.write(content.getBytes());

				fout.close();
				return true;

			} catch (Exception ex)
			{
				Log.i("", ex.getMessage());
			}
		}
		return false;
	}

	public static Boolean SaveOrderCommand(String Command, String CustomCode)
	{
		Command = Command.replace(';', ' ') + ";";
		return CreateFile(Command, CmdType.Order, CustomCode);
	}

	public static Boolean SaveTruckStoreCommand(String Command, String DriverCode)
	{
		Command = Command.replace(';', ' ') + ";";
		return CreateFile(Command, CmdType.SaveTruckStore, DriverCode);
	}

	public static Boolean SaveCustomStoreCommand(String Command, String CustomCode)
	{
		Command = Command.replace(';', ' ') + ";";
		return CreateFile(Command, CmdType.CustomStore, CustomCode);
	}

	public static Boolean SaveSurveyCommand(String Command, String CustomCode)
	{
		Command = Command.replace(';', ' ') + ";";
		return CreateFile(Command, CmdType.Survey, CustomCode);
	}

	public static Boolean SaveGetGpsCommand(String Command, String CustomCode)
	{
		Command = Command.replace(';', ' ') + ";";
		return CreateFile(Command, CmdType.GetGps, CustomCode);
	}

	public static Boolean SaveAddCustomommand(String Command, String CustomCode)
	{
		Command = Command.replace(';', ' ') + ";";
		return CreateFile(Command, CmdType.AddCustom, CustomCode);
	}

	public static Boolean SaveMsgCommand(String Command, String CustomCode)
	{
		Command = Command.replace(';', ' ') + ";";
		return CreateFile(Command, CmdType.MSG, CustomCode);
	}

	public static Boolean SaveLog(String Command)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("{-------------------------------");
		sb.append("\r\n");
		sb.append((new Date()).toLocaleString());
		sb.append("\r\n");
		sb.append(Command + "}");
		sb.append("\r\n");

		return CreateFile(Command, CmdType.Log, "admin");
	}

	public static boolean CommandExists(CmdType CommandType, String Code)
	{
		String sDStateString = android.os.Environment.getExternalStorageState();
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED))
		{
			File SDFile = android.os.Environment.getExternalStorageDirectory();
			String path = MessageFormat.format(SDFile.getAbsolutePath() + File.separator + "Commands/{0}/{1}/{2}", CommandType.toString(), Code, GetDate() + ".txt");
			File comFile = new File(path);
			return comFile.exists();
		}
		return false;
	}

	public static boolean DeleteCommand(CmdType CommandType, String Code)
	{
		String sDStateString = android.os.Environment.getExternalStorageState();
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED))
		{
			File SDFile = android.os.Environment.getExternalStorageDirectory();
			String path = MessageFormat.format(SDFile.getAbsolutePath() + File.separator + "Commands/{0}/{1}/{2}", CommandType.toString(), Code, GetDate() + ".txt");
			File comFile = new File(path);
			return comFile.delete();
		}
		return false;
	}

	public static String GetCommands(CmdType CommandType, String Code)
	{
		String sDStateString = android.os.Environment.getExternalStorageState();
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED))
		{
			File SDFile = android.os.Environment.getExternalStorageDirectory();
			String path = MessageFormat.format(SDFile.getAbsolutePath() + File.separator + "Commands/{0}/{1}/{2}", CommandType.toString(), Code, GetDate() + ".txt");
			File comFile = new File(path);
			if (comFile.exists())
			{
				try
				{
					FileInputStream fout = new FileInputStream(comFile);
					byte[] buffer = new byte[4096];
					int i = fout.read(buffer);
					String s = new String(buffer, 0, i);
					Log.i("cmd", s);
					return s;
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	public static void Retry(CmdType cmdType)
	{
		String sDStateString = android.os.Environment.getExternalStorageState();
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED))
		{
			File SDFile = android.os.Environment.getExternalStorageDirectory();
			String path = SDFile.getAbsolutePath() + File.separator + "Commands/" + cmdType.toString();
			File dir = new File(path);
			if (dir.exists())
			{
				try
				{
					File[] dirs = dir.listFiles();
					for (int i = 0; i < dirs.length; i++)
					{
						String cmd = LogCommandHelper.GetCommands(cmdType, dirs[i].getName());
						if (cmd.length() > 5)
						{
							try
							{
								// SyncHelper.ExcuteSqlWithTans(cmd);
								boolean success = false;
								if (success)
								{
									File subFile = new File(path + File.pathSeparator + dirs[i].getName() + File.pathSeparator + GetDate());
									if (subFile.delete())
									{
										DeleteCommand(cmdType, dirs[i].getName());
									}
								}
							} catch (Exception ex)
							{
								Log.i("异常", ex.getMessage());
							}
						}
					}
				} catch (Exception ex)
				{

				}
			}
		}
	}

	public enum CmdType
	{
		Order, SaveTruckStore, CustomStore, Survey, AddCustom, GetGps, MSG, Log
	}
}
