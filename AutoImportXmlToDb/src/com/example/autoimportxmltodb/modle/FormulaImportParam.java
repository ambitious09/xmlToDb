package com.example.autoimportxmltodb.modle;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import android.content.Context;

import com.exampleautoimportxmltodb.xmlimport.common.FormulaMode;
import com.exampleautoimportxmltodb.xmlimport.common.ImportMode;


/***
 * 配方导入参数
 * 
 * @param <ImportMode>
 * @param <ImportFileVersion>
 * 
 */
public class FormulaImportParam
{
	/***
	 * 数据库相对路径
	 */
	public String DatabasePath;

	/***
	 * 数据库上下文
	 */
	public Context DBContext;

	/***
	 * 当前配方体系
	 */
	public FormulaSystem CurrentFormulaSystem;

	/***
	 * 导入的配方体系
	 */
	public FormulaSystem ImportFormulaSystem;

	/***
	 * 当前客户端唯一标识
	 */
	public int CurrentClientUniqueCode;

	/***
	 * 当前用户唯一认证标识
	 */
	public int CurrentUserUniqueCode;

	/***
	 * 导入的用户唯一认证标识
	 */
	public int ImportUserUniqueCode;

	/***
	 * 默认色浆体系
	 */
	public String DefaultColorantsSystem;

	/***
	 * 客户端在线模式
	 */
//	public NetworkingMode ClientOnline;

	/***
	 * 导入文件
	 */
	public File importFile;
	
	/***
	 * 导入原文件
	 */
	public File importOldFile;

	/***
	 * 导入XML文件根节点
	 */
	public Element importXElement;

	/***
	 * 导入配方模型
	 */
	public FormulaMode ImportFormulaMode;

	/***
	 * 导入模式
	 */
	public ImportMode ImportMode;

	/***
	 * 导入数据文件的版本
	 */
	public com.exampleautoimportxmltodb.xmlimport.common.ImportFileVersion ImportFileVersion;

	/***
	 * 数据存在时需要更新的表名列表
	 */
	public List<String> UpdateTableNames;
}