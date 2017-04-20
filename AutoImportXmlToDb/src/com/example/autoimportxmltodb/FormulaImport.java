package com.example.autoimportxmltodb;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.example.autoimportxmltodb.commmen.CategoryLog;
import com.example.autoimportxmltodb.commmen.CommonException;
import com.example.autoimportxmltodb.commmen.CompressDecompressHelper;
import com.example.autoimportxmltodb.commmen.EventHandler;
import com.example.autoimportxmltodb.commmen.ExceptionLevel;
import com.example.autoimportxmltodb.commmen.LanguageLocal;
import com.example.autoimportxmltodb.commmen.MessageKeys;
import com.example.autoimportxmltodb.modle.DicKey;
import com.example.autoimportxmltodb.modle.FormulaImportParam;
import com.example.autoimportxmltodb.modle.FormulaSystem;
import com.exampleautoimportxmltodb.xmlimport.common.CompletedStatus;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaImportCompletedEventArgs;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaImportProgressingEventArgs;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaMode;
import com.exampleautoimportxmltodb.xmlimport.common.ImportFileVersion;
import com.exampleautoimportxmltodb.xmlimport.common.ImportMode;
import com.exampleautoimportxmltodb.xmlimport.common.ImportProgressingStatus;

/***
 * 导入配方信息
 * 
 * 
 */
public class FormulaImport
{
	// #region 变量

	/***
	 * 是否提示选择导入方式
	 */
	private boolean isPromptImportMode;

	/***
	 * 导入配方参数实体
	 */
	private FormulaImportParam formulaImportParam;

	/***
	 * 导入配方行为接口
	 */
	private FormulaImportBehavior formulaImportBehavior;

	/***
	 * 全加载配方文件的上限文件件大小[默认为5M]
	 */
	private static final long fileSize = 5242880;

	// #endregion

	/***
	 * 构造函数
	 */
	public FormulaImport(FormulaImportParam formulaImportParam, boolean isPromptImportMode)
	{
		this.formulaImportParam = formulaImportParam;
		this.isPromptImportMode = isPromptImportMode;
	}

	// #region 事件

	/***
	 * 配方导入进度事件
	 */
	public EventHandler<FormulaImportProgressingEventArgs> FormulaImportProgressing;

	/***
	 * 配方导入完成事件
	 */
	public EventHandler<FormulaImportCompletedEventArgs> FormulaImportCompleted;

	// #endregion

	// #region 公共方法

	/***
	 * 异步导入数据
	 */
	public void ImportFormulaAsync()
	{
		Thread importDataThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				ImportFormula();
			}
		});
		importDataThread.start();
	}

	/***
	 * 配方版本低，继续导入配方方法
	 */
	public void ContinueImportFormulaAsync()
	{
		Thread importDataThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				formulaImportBehavior.ImportFormulas();
			}
		});
		importDataThread.start();
	}

	/***
	 * 选择导入模式，继续导入配方方法
	 */
	public void ContinueImportFormulaAsync(ImportMode importMode)
	{
		if (importMode != null)
		{
			formulaImportParam.ImportMode = importMode;
		}

		formulaImportBehavior.FormulaImportProgressing = FormulaImportProgressing;
		formulaImportBehavior.FormulaImportCompleted = FormulaImportCompleted;

		ContinueImportFormulaAsync();
	}

	/***
	 * 同步导入配方
	 */
	public void ImportFormula()
	{
		try
		{
			SetImportProgressing(ImportProgressingStatus.LoadData, MessageKeys.Formula_Import_LoadData_Start);

			// 获取导入文件根节点
			formulaImportParam.importXElement = formulaImportParam.importXElement == null ? GetImportXElement() : formulaImportParam.importXElement;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			Utility.WriteLog("FormulaImport", "ImportFormula", ex);

			SetFormulaImportCompleted(CompletedStatus.Failure, MessageKeys.Formula_ImportFailed_LoadFile, formulaImportParam.importOldFile);
			return;
		}

		try
		{
			SetImportProgressing(ImportProgressingStatus.LoadData, MessageKeys.Formula_Import_LoadData_ReadConfig);

			// 读取配方导入参数
			ReadFormulaImportParam();
		} catch (CommonException e)
		{
			Utility.WriteLog("FormulaImport", "ImportFormula", e);
			SetFormulaImportCompleted(CompletedStatus.Failure, e.getCodeException(), formulaImportParam.importOldFile);
			return;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			Utility.WriteLog("FormulaImport", "ImportFormula", ex);
			SetFormulaImportCompleted(CompletedStatus.Failure, MessageKeys.Formula_ImportFailed_ReadImportParam, formulaImportParam.importOldFile);
			return;
		}

		switch (formulaImportParam.ImportFormulaMode)
		{
		case StandardFormula:
			formulaImportBehavior = new StandardFormulaImportBehavior(formulaImportParam);

			SetImportProgressing(ImportProgressingStatus.LoadData, "", formulaImportParam.ImportFormulaSystem.getFormulaSystemVersion());
			break;
		default:
			return;
		}

		formulaImportBehavior.FormulaImportProgressing = FormulaImportProgressing;
		formulaImportBehavior.FormulaImportCompleted = FormulaImportCompleted;

		formulaImportBehavior.ImportFormulas();
	}

	// #endregion

	// #region 私有方法

	/***
	 * 获取导入文件根节点
	 * 
	 * @return 根节点
	 * @throws DocumentException
	 * @throws IOException
	 */
	private Element GetImportXElement() throws DocumentException, IOException
	{
		Element element = null;

		formulaImportParam.importFile.length();
		if (formulaImportParam.importFile.getName().toLowerCase().endsWith(".san"))// 解压
		{
			File tempFile = new File(formulaImportParam.DBContext.getDir(DicKey.Temp_RootPath, 0), "formula.flv");
			if (tempFile.exists())
			{
				tempFile.delete();
			}
			formulaImportParam.importFile = CompressDecompressHelper.DeCompressGZipFile(formulaImportParam.importFile, tempFile);
			tempFile.deleteOnExit();
		}

		if (formulaImportParam.importFile.length() <= fileSize)
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read(formulaImportParam.importFile);
			element = document.getRootElement();
		}

		return element;
	}

	/***
	 * 读取配方导入参数
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void ReadFormulaImportParam() throws Exception, CommonException
	{
		Hashtable<String, String> hashtable = null;

		if (formulaImportParam.importXElement == null)
		{
			SaxParseService reader = new SaxParseService();
			ReaderParam param = new ReaderParam();
			param.ObjectTranslate = new DefaultObjectTranslate();
			reader.regix("ImportParam", param);
			try
			{
				reader.start(formulaImportParam.importFile, true);
			} catch (Exception e)
			{
				if (!reader.checkInterrupt())
				{
					throw (e);
				}
			}
			hashtable = (Hashtable<String, String>) reader.getElement();
			hashtable.put("FormulaMode", "0");// 标准配方
		} else
		{
			Element formulaImportParamXElement = formulaImportParam.importXElement.element("ImportParam");
			hashtable = new Hashtable<String, String>(5);
			try
			{
				hashtable.put("ImportFileVersion", formulaImportParamXElement.elementTextTrim("ImportFileVersion"));
				hashtable.put("FormulaMode", "0");// 标准配方
			} catch (Exception e)
			{
				ThrowCommonException(MessageKeys.StandardFormula_Valid_Versions2_0, e);
			}
			hashtable.put("ImportMode", formulaImportParamXElement.elementTextTrim("ImportMode"));
			hashtable.put("UpdateTableNames", formulaImportParamXElement.elementTextTrim("updatetables"));
//			hashtable.put("UserUniqueCode", formulaImportParamXElement.elementTextTrim("UserUniqueCode") == null ? "0" : formulaImportParamXElement.elementTextTrim("UserUniqueCode"));
		}

		try
		{
			SetImportParamForColorExpert3(hashtable);
		} catch (CommonException e)
		{
			throw e;
		}
	}

	/***
	 * 设置三代以上版本导入参数
	 */
	@SuppressWarnings("unchecked")
	private void SetImportParamForColorExpert3(Hashtable<String, String> hashtable) throws Exception, CommonException
	{
		// 导入数据文件的版本
		formulaImportParam.ImportFileVersion = ImportFileVersion.ColorExpert3_1;

		// 导入配方类型
		try
		{
			formulaImportParam.ImportFormulaMode = FormulaMode.valueof(Integer.parseInt(hashtable.get("FormulaMode")));
		} catch (Exception e)
		{
			ThrowCommonException(MessageKeys.StandardFormula_Valid_Versions2_0, e);
		}

		// 导入类型
		formulaImportParam.ImportMode = ImportMode.valueof(Integer.parseInt(hashtable.get("ImportMode")));

		// 数据存在时需要更新的表名列表
		String utn = hashtable.containsKey("updatetables") ? hashtable.get("updatetables") : "";
		formulaImportParam.UpdateTableNames = Arrays.asList(utn.toUpperCase().split("\\$"));

		switch (formulaImportParam.ImportFormulaMode)
		{
		case StandardFormula:
			formulaImportParam.ImportFormulaSystem = new FormulaSystem();

			// 获取导入的配方体系
			if (formulaImportParam.importXElement == null)
			{
				SaxParseService reader = new SaxParseService();
				ReaderParam param = new ReaderParam();
				param.ObjectTranslate = new DefaultObjectTranslate();
				reader.regix("FormulaSystem", param);
				try
				{
					reader.start(formulaImportParam.importFile, true);
				} catch (Exception e)
				{
					e.printStackTrace();
					if (!reader.checkInterrupt())
					{
						throw (e);
					}
				}
				Hashtable<String, String> obj = (Hashtable<String, String>) (reader.getElement());
				formulaImportParam.ImportFormulaSystem.setFormulaSystemId(Integer.parseInt(obj.get("FormulaSystemId")));
				formulaImportParam.ImportFormulaSystem.setFormulaSystemName(obj.get("FormulaSystemName"));
				formulaImportParam.ImportFormulaSystem.setFormulaSystemVersion(obj.get("FormulaSystemVersion"));
				formulaImportParam.ImportFormulaSystem.setCreateTime(LanguageLocal.ConvertStringToDateTimeWithEn(obj.get("CreateTime")));
			} else
			{
				Element xElement = formulaImportParam.importXElement.element("FormulaSystem");
				formulaImportParam.ImportFormulaSystem.setFormulaSystemId(Integer.parseInt(xElement.element("FormulaSystemId").getText()));
				formulaImportParam.ImportFormulaSystem.setFormulaSystemName(xElement.element("FormulaSystemName").getText());
				formulaImportParam.ImportFormulaSystem.setFormulaSystemVersion(xElement.element("FormulaSystemVersion").getText());
				formulaImportParam.ImportFormulaSystem.setCreateTime(LanguageLocal.ConvertStringToDateTimeWithEn(xElement.element("CreateTime").getText()));
			}
			break;
		case CustomerFormula:
		case DispensedFormula:
		case UserData:
			formulaImportParam.ImportUserUniqueCode = Integer.parseInt(hashtable.get("UserUniqueCode"));
			break;
		default:
			return;
		}
	}

	/***
	 * 设置导入进度状态，触发事件
	 * 
	 * @param progressingStatus
	 *            导入进度状态
	 * @param code
	 *            导入进度信息代码
	 */
	private void SetImportProgressing(ImportProgressingStatus progressingStatus, String code)
	{
		if (FormulaImportProgressing != null)
		{
			FormulaImportProgressingEventArgs e = new FormulaImportProgressingEventArgs(progressingStatus, code);
			FormulaImportProgressing.Deal(e);
		}
	}

	/***
	 * 设置导入进度状态，触发事件
	 * 
	 * @param progressingStatus
	 *            导入进度状态
	 * @param code
	 *            导入进度信息代码
	 * @param message
	 *            导入进度信息
	 */
	private void SetImportProgressing(ImportProgressingStatus progressingStatus, String code, String message)
	{
		if (FormulaImportProgressing != null)
		{
			FormulaImportProgressingEventArgs e = new FormulaImportProgressingEventArgs(progressingStatus, code, message);
			FormulaImportProgressing.Deal(e);
		}
	}

	/***
	 * 设置导入完成状态，触发事件
	 * 
	 * @param completedStatus
	 *            完成状态
	 * @param message
	 *            导入完成信息
	 */
	private void SetFormulaImportCompleted(CompletedStatus completedStatus, String message, File file)
	{
		if (FormulaImportCompleted != null)
		{
			FormulaImportCompletedEventArgs e = new FormulaImportCompletedEventArgs(completedStatus, message, file);
			FormulaImportCompleted.Deal(e);
		}
	}

	private static void ThrowCommonException(String code, Exception ex) throws CommonException
	{
		CommonException commonException = new CommonException(ExceptionLevel.Error, code, CategoryLog.Error, ex);
		throw commonException;
	}
	// #endregion
}
