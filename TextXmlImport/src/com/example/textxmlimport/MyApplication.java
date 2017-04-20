package com.example.textxmlimport;

import android.app.Application;

public class MyApplication extends Application
{
	String str = null;
	private static final String NAME = "MyApplication";
	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		setStr(str);
	}

	private static class MyApplicationHolder
	{
		private static MyApplication instance = new MyApplication();
	}

	public static MyApplication getInstance()
	{
		return MyApplicationHolder.instance;
	}

	public String getStr()
	{
		return str;
	}

	public void setStr(String str)
	{
		this.str = str;
	}

}
