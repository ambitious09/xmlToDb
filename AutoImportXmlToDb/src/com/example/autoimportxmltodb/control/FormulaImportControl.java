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
	// #region 变量属性

		/***
		 * 导入配方业务逻辑
		 */
		private FormulaImport formulaImport;

		/***
		 * 标准配方参数
		 */
		private FormulaImportParam formulaImportParam;

		/***
		 * true:本地数据导入；false:同步数据导入
		 */
		private boolean isLocalImport = true;

		// #endregion

		/***
		 * 初始化控制器
		 */
		public FormulaImportControl()
		{
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
		 * 下载需导入的配方文件
		 * 
		 * @param pop3Host
		 *            pop3邮箱地址
		 * @param userName
		 *            邮箱登录名
		 * @param passWord
		 *            邮箱登录密码
		 * @param context
		 *            数据上下文
		 * @return 配方文件全名堆栈
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
		 * 导入配方
		 * @param importFile 要导入的文件
		 * @param isloacalimport true:本地数据导入；false:同步数据导入
		 * @param context 数据上下文
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
		 * 同步导入数据
		 * @param importXElement 要导入的报文
		 * @param issync 是否阻塞执行导入
		 * @param context 数据上下文
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
		 * 配方版本低，继续导入配方
		 */
		public void ContinueImportFormula()
		{
			formulaImport.ContinueImportFormulaAsync();
		}

		/***
		 * 选择导入模式，继续导入配方
		 * @param importMode 导入模式
		 */
		public void ContinueImportFormula(ImportMode importMode)
		{
			formulaImport.ContinueImportFormulaAsync(importMode);
		}

		// #endregion

		// #region 私有方法

		/***
		 * 导入数据
		 * @param isPromptImportMode 是否阻塞执行导入
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
