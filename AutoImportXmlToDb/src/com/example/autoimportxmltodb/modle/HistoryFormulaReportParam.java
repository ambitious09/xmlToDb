package com.example.autoimportxmltodb.modle;

import java.util.List;

/***
 * ��ʷ�䷽����������
 * 
 * 
 */
public class HistoryFormulaReportParam
{
	/***
	 * �������б�������
	 */
	public String Code;

	/***
	 * ����������ʾ����
	 */
	public String Name;

	/***
	 * ���е�������
	 */
	public int SubColumnMaxCount;

	/***
	 * ��������������
	 */
	public List<HistoryFormulaReportParam> ReportParamSet;

	public HistoryFormulaReportParam(String code, String name)
	{
		Code = code;
		Name = name;
	}
}
