package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

import android.content.Context;

public class AuthModel implements Serializable
{
	private static final long serialVersionUID = 7425257751113997894L;	
	
	/**
	 * ����������
	 */
	public Context DbContext;

	/***
	 * ���ݷ��ʳ�������
	 */
	public String DataAccessAssembly;
}
