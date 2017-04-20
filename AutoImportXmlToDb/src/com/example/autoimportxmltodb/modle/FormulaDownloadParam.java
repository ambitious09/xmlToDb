package com.example.autoimportxmltodb.modle;


import android.content.Context;

/***
 * 配方下载参数
 * 
 * 
 */
public class FormulaDownloadParam
{
	/***
	 * 当前配方体系
	 */
	public FormulaSystem CurrentFormulaSystem;

	/***
	 * pop3邮箱地址
	 */
	public String Pop3Host;

	/***
	 * 邮箱登录名
	 */
	public String UserName;

	/***
	 * 邮箱登录密码
	 */
	public String PassWord;

	/***
	 * 上下文
	 */
	public Context FContext;
}
