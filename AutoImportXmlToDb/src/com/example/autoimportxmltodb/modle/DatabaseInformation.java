package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

/***
 * ���ݿ���Ϣ
 */
public class DatabaseInformation implements Serializable
{
	private static final long serialVersionUID = 7769155512107664148L;
	
	/***
	 * ��ǰ���ݿ�汾��Ϣ
	 */
	public String CurrentDatabaseVersion;

	/***
	 * ��ǰ�䷽��ϵ�汾��Ϣ
	 */
	public FormulaSystem CurrentFormulaSystem;
}

