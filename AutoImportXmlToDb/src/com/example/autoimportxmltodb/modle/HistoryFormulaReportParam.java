package com.example.autoimportxmltodb.modle;

import java.util.List;

/***
 * 历史配方报表导出参数
 * 
 * 
 */
public class HistoryFormulaReportParam
{
	/***
	 * 导出的列编码名称
	 */
	public String Code;

	/***
	 * 导出的列显示名称
	 */
	public String Name;

	/***
	 * 子列的最大个数
	 */
	public int SubColumnMaxCount;

	/***
	 * 报表导出参数集合
	 */
	public List<HistoryFormulaReportParam> ReportParamSet;

	public HistoryFormulaReportParam(String code, String name)
	{
		Code = code;
		Name = name;
	}
}
