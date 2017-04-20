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
 * �����䷽��Ϣ
 * 
 * 
 */
public class FormulaImport
{
	// #region ����

	/***
	 * �Ƿ���ʾѡ���뷽ʽ
	 */
	private boolean isPromptImportMode;

	/***
	 * �����䷽����ʵ��
	 */
	private FormulaImportParam formulaImportParam;

	/***
	 * �����䷽��Ϊ�ӿ�
	 */
	private FormulaImportBehavior formulaImportBehavior;

	/***
	 * ȫ�����䷽�ļ��������ļ�����С[Ĭ��Ϊ5M]
	 */
	private static final long fileSize = 5242880;

	// #endregion

	/***
	 * ���캯��
	 */
	public FormulaImport(FormulaImportParam formulaImportParam, boolean isPromptImportMode)
	{
		this.formulaImportParam = formulaImportParam;
		this.isPromptImportMode = isPromptImportMode;
	}

	// #region �¼�

	/***
	 * �䷽��������¼�
	 */
	public EventHandler<FormulaImportProgressingEventArgs> FormulaImportProgressing;

	/***
	 * �䷽��������¼�
	 */
	public EventHandler<FormulaImportCompletedEventArgs> FormulaImportCompleted;

	// #endregion

	// #region ��������

	/***
	 * �첽��������
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
	 * �䷽�汾�ͣ����������䷽����
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
	 * ѡ����ģʽ�����������䷽����
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
	 * ͬ�������䷽
	 */
	public void ImportFormula()
	{
		try
		{
			SetImportProgressing(ImportProgressingStatus.LoadData, MessageKeys.Formula_Import_LoadData_Start);

			// ��ȡ�����ļ����ڵ�
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

			// ��ȡ�䷽�������
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

	// #region ˽�з���

	/***
	 * ��ȡ�����ļ����ڵ�
	 * 
	 * @return ���ڵ�
	 * @throws DocumentException
	 * @throws IOException
	 */
	private Element GetImportXElement() throws DocumentException, IOException
	{
		Element element = null;

		formulaImportParam.importFile.length();
		if (formulaImportParam.importFile.getName().toLowerCase().endsWith(".san"))// ��ѹ
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
	 * ��ȡ�䷽�������
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
			hashtable.put("FormulaMode", "0");// ��׼�䷽
		} else
		{
			Element formulaImportParamXElement = formulaImportParam.importXElement.element("ImportParam");
			hashtable = new Hashtable<String, String>(5);
			try
			{
				hashtable.put("ImportFileVersion", formulaImportParamXElement.elementTextTrim("ImportFileVersion"));
				hashtable.put("FormulaMode", "0");// ��׼�䷽
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
	 * �����������ϰ汾�������
	 */
	@SuppressWarnings("unchecked")
	private void SetImportParamForColorExpert3(Hashtable<String, String> hashtable) throws Exception, CommonException
	{
		// ���������ļ��İ汾
		formulaImportParam.ImportFileVersion = ImportFileVersion.ColorExpert3_1;

		// �����䷽����
		try
		{
			formulaImportParam.ImportFormulaMode = FormulaMode.valueof(Integer.parseInt(hashtable.get("FormulaMode")));
		} catch (Exception e)
		{
			ThrowCommonException(MessageKeys.StandardFormula_Valid_Versions2_0, e);
		}

		// ��������
		formulaImportParam.ImportMode = ImportMode.valueof(Integer.parseInt(hashtable.get("ImportMode")));

		// ���ݴ���ʱ��Ҫ���µı����б�
		String utn = hashtable.containsKey("updatetables") ? hashtable.get("updatetables") : "";
		formulaImportParam.UpdateTableNames = Arrays.asList(utn.toUpperCase().split("\\$"));

		switch (formulaImportParam.ImportFormulaMode)
		{
		case StandardFormula:
			formulaImportParam.ImportFormulaSystem = new FormulaSystem();

			// ��ȡ������䷽��ϵ
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
	 * ���õ������״̬�������¼�
	 * 
	 * @param progressingStatus
	 *            �������״̬
	 * @param code
	 *            ���������Ϣ����
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
	 * ���õ������״̬�������¼�
	 * 
	 * @param progressingStatus
	 *            �������״̬
	 * @param code
	 *            ���������Ϣ����
	 * @param message
	 *            ���������Ϣ
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
	 * ���õ������״̬�������¼�
	 * 
	 * @param completedStatus
	 *            ���״̬
	 * @param message
	 *            ���������Ϣ
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
