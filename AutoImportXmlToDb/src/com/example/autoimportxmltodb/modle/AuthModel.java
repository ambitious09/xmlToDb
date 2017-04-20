package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

import android.content.Context;

public class AuthModel implements Serializable
{
	private static final long serialVersionUID = 7425257751113997894L;	
	
	/**
	 * 上下文内容
	 */
	public Context DbContext;

	/***
	 * 数据访问程序集名称
	 */
	public String DataAccessAssembly;
}
