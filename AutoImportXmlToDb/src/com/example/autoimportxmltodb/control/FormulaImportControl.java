package com.example.autoimportxmltodb.control;

import java.io.File;

import org.dom4j.Element;

import android.content.Context;

import com.example.autoimportxmltodb.FormulaImport;
import com.example.autoimportxmltodb.commmen.EventHandler;
import com.example.autoimportxmltodb.modle.FormulaImportParam;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaImportCompletedEventArgs;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaImportProgressingEventArgs;
import com.exampleautoimportxmltodb.xmlimport.common.ImportMode;

public class FormulaImportControl {
	// #region ��������

		/***
		 * �����䷽ҵ���߼�
		 */
		private FormulaImport formulaImport;

		/***
		 * ��׼�䷽����
		 */
		private FormulaImportParam formulaImportParam;

		/***
		 * true:�������ݵ��룻false:ͬ�����ݵ���
		 */
		private boolean isLocalImport = true;

		// #endregion

		/***
		 * ��ʼ��������
		 */
		public FormulaImportControl()
		{
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
		 * �����赼����䷽�ļ�
		 * 
		 * @param pop3Host
		 *            pop3�����ַ
		 * @param userName
		 *            �����¼��
		 * @param passWord
		 *            �����¼����
		 * @param context
		 *            ����������
		 * @return �䷽�ļ�ȫ����ջ
		 * @throws Exception
		 */
		/*public Stack<String> DownloadFormulas(String pop3Host, String userName, String passWord, Context context) throws Exception
		{
			FormulaDownloadParam param = new FormulaDownloadParam();
			param.CurrentFormulaSystem = SanTintCommon.DeepClone(ControllerUtility.getFormulaSystemInfo());
			param.Pop3Host = pop3Host;
			param.UserName = userName;
			param.PassWord = passWord;
			param.FContext = context;

			FormulaDownload formulaDownload = new FormulaDownload(param);
			Stack<String> formulaStack = formulaDownload.DownloadFormulas();

			return formulaStack;
		}*/
		/***
		 * �����䷽
		 * @param importFile Ҫ������ļ�
		 * @param isloacalimport true:�������ݵ��룻false:ͬ�����ݵ���
		 * @param context ����������
		 */
		public void ImportFormula(File importFile, boolean isloacalimport, Context context)
		{
			isLocalImport = isloacalimport;

			formulaImportParam = new FormulaImportParam();
			formulaImportParam.importFile = importFile;
			formulaImportParam.importOldFile = importFile;
			formulaImportParam.DBContext = context;

			ImportData(false);
		}

		/***
		 * ͬ����������
		 * @param importXElement Ҫ����ı���
		 * @param issync �Ƿ�����ִ�е���
		 * @param context ����������
		 */
		public void ImportSyncFormula(Element importXElement, File file, boolean issync, Context context)
		{
			formulaImportParam = new FormulaImportParam();
			formulaImportParam.importXElement = importXElement;
			formulaImportParam.importFile = file;
			formulaImportParam.importOldFile = file;
			formulaImportParam.DBContext = context;

			ImportData(issync);
		}
		/***
		 * �䷽�汾�ͣ����������䷽
		 */
		public void ContinueImportFormula()
		{
			formulaImport.ContinueImportFormulaAsync();
		}

		/***
		 * ѡ����ģʽ�����������䷽
		 * @param importMode ����ģʽ
		 */
		public void ContinueImportFormula(ImportMode importMode)
		{
			formulaImport.ContinueImportFormulaAsync(importMode);
		}

		// #endregion

		// #region ˽�з���

		/***
		 * ��������
		 * @param isPromptImportMode �Ƿ�����ִ�е���
		 */
		private void ImportData(boolean isSync)
		{
//			formulaImportParam.DefaultColorantsSystem = com.santint.autopaint.controller.ControllerUtility.getPaintType();
//			formulaImportParam.ClientOnline = ControllerUtility.getClientOnline();
//			formulaImportParam.CurrentFormulaSystem = SanTintCommon.DeepClone(ControllerUtility.getFormulaSystemInfo());
//			formulaImportParam.CurrentUserUniqueCode = ControllerUtility.getAuthenticateInfo().;

			formulaImport = new FormulaImport(formulaImportParam, isLocalImport);

			formulaImport.FormulaImportProgressing = FormulaImportProgressing;
			formulaImport.FormulaImportCompleted = new EventHandler<FormulaImportCompletedEventArgs>()
			{
				@Override
				public void Deal(FormulaImportCompletedEventArgs e)
				{
//					formulaImport_FormulaImportCompleted(e);

					if (FormulaImportCompleted != null)
					{
						FormulaImportCompleted.Deal(e);
					}
				}
			};

			if (!isSync)
			{
				formulaImport.ImportFormulaAsync();
			}
			else
			{
				formulaImport.ImportFormula();
			}
		}

}
