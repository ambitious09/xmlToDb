package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

/***
 * 数据库信息
 */
public class DatabaseInformation implements Serializable
{
	private static final long serialVersionUID = 7769155512107664148L;
	
	/***
	 * 当前数据库版本信息
	 */
	public String CurrentDatabaseVersion;

	/***
	 * 当前配方体系版本信息
	 */
	public FormulaSystem CurrentFormulaSystem;
}

