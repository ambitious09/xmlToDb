package com.example.autoimportxmltodb.modle;

import java.util.Date;
import java.util.List;

import com.exampleautoimportxmltodb.xmlimport.common.FormulaMode;
/***
 * 配方导出参数
 * 
 * 
 */
public class FormulaExportParam
{
	/***
	 * 导出模型
	 */
	public FormulaMode ExportMode;

	/***
	 * 认证信息
	 */
	public Authenticate AuthenticateInfo;

	/***
	 * 导出历史配方报表参数[只对导出历史配方报表有效]
	 */
	public List<HistoryFormulaReportParam> DispensedFormulaReportParams;

	/***
	 * 开始时间
	 */
	public Date StartDateTime;

	/***
	 * 结束时间
	 */
	public Date EndDateTime;
	
	/***
	 * 要导出的数据名[只对导出私有数据有效]
	 */
	public List<String> ExportDataNames;
}
