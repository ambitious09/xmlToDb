package com.example.autoimportxmltodb.modle;

import java.util.Date;
import java.util.List;

import com.exampleautoimportxmltodb.xmlimport.common.FormulaMode;
/***
 * �䷽��������
 * 
 * 
 */
public class FormulaExportParam
{
	/***
	 * ����ģ��
	 */
	public FormulaMode ExportMode;

	/***
	 * ��֤��Ϣ
	 */
	public Authenticate AuthenticateInfo;

	/***
	 * ������ʷ�䷽�������[ֻ�Ե�����ʷ�䷽������Ч]
	 */
	public List<HistoryFormulaReportParam> DispensedFormulaReportParams;

	/***
	 * ��ʼʱ��
	 */
	public Date StartDateTime;

	/***
	 * ����ʱ��
	 */
	public Date EndDateTime;
	
	/***
	 * Ҫ������������[ֻ�Ե���˽��������Ч]
	 */
	public List<String> ExportDataNames;
}
