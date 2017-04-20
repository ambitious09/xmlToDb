package com.example.autoimportxmltodb.modle;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import android.content.Context;

import com.exampleautoimportxmltodb.xmlimport.common.FormulaMode;
import com.exampleautoimportxmltodb.xmlimport.common.ImportMode;


/***
 * �䷽�������
 * 
 * @param <ImportMode>
 * @param <ImportFileVersion>
 * 
 */
public class FormulaImportParam
{
	/***
	 * ���ݿ����·��
	 */
	public String DatabasePath;

	/***
	 * ���ݿ�������
	 */
	public Context DBContext;

	/***
	 * ��ǰ�䷽��ϵ
	 */
	public FormulaSystem CurrentFormulaSystem;

	/***
	 * ������䷽��ϵ
	 */
	public FormulaSystem ImportFormulaSystem;

	/***
	 * ��ǰ�ͻ���Ψһ��ʶ
	 */
	public int CurrentClientUniqueCode;

	/***
	 * ��ǰ�û�Ψһ��֤��ʶ
	 */
	public int CurrentUserUniqueCode;

	/***
	 * ������û�Ψһ��֤��ʶ
	 */
	public int ImportUserUniqueCode;

	/***
	 * Ĭ��ɫ����ϵ
	 */
	public String DefaultColorantsSystem;

	/***
	 * �ͻ�������ģʽ
	 */
//	public NetworkingMode ClientOnline;

	/***
	 * �����ļ�
	 */
	public File importFile;
	
	/***
	 * ����ԭ�ļ�
	 */
	public File importOldFile;

	/***
	 * ����XML�ļ����ڵ�
	 */
	public Element importXElement;

	/***
	 * �����䷽ģ��
	 */
	public FormulaMode ImportFormulaMode;

	/***
	 * ����ģʽ
	 */
	public ImportMode ImportMode;

	/***
	 * ���������ļ��İ汾
	 */
	public com.exampleautoimportxmltodb.xmlimport.common.ImportFileVersion ImportFileVersion;

	/***
	 * ���ݴ���ʱ��Ҫ���µı����б�
	 */
	public List<String> UpdateTableNames;
}