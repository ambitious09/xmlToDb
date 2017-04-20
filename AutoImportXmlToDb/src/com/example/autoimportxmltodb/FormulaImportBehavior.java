package com.example.autoimportxmltodb;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.autoimportxmltodb.commmen.Common;
import com.example.autoimportxmltodb.commmen.EncryptDecryptHelper;
import com.example.autoimportxmltodb.commmen.EventHandler;
import com.example.autoimportxmltodb.commmen.LanguageLocal;
import com.example.autoimportxmltodb.commmen.MessageKeys;
import com.example.autoimportxmltodb.commmen.Version;
import com.example.autoimportxmltodb.modle.Auto;
import com.example.autoimportxmltodb.modle.AutoParts;
import com.example.autoimportxmltodb.modle.Brand;
import com.example.autoimportxmltodb.modle.BrandProduct;
import com.example.autoimportxmltodb.modle.ColorAuto;
import com.example.autoimportxmltodb.modle.ColorAutoParts;
import com.example.autoimportxmltodb.modle.ColorFormula;
import com.example.autoimportxmltodb.modle.ColorFormulaColorants;
import com.example.autoimportxmltodb.modle.ColorGroup;
import com.example.autoimportxmltodb.modle.ColorGroup_Color;
import com.example.autoimportxmltodb.modle.ColorMap;
import com.example.autoimportxmltodb.modle.ColorType;
import com.example.autoimportxmltodb.modle.ColorantPrice;
import com.example.autoimportxmltodb.modle.Colorants;
import com.example.autoimportxmltodb.modle.DataTypeConvert;
import com.example.autoimportxmltodb.modle.DerivateColor;
import com.example.autoimportxmltodb.modle.Formula;
import com.example.autoimportxmltodb.modle.FormulaImportParam;
import com.example.autoimportxmltodb.modle.FormulaRemark;
import com.example.autoimportxmltodb.modle.FormulaSystem;
import com.example.autoimportxmltodb.modle.ImportDataSetInfo;
import com.example.autoimportxmltodb.modle.InnerColor;
import com.example.autoimportxmltodb.modle.Product;
import com.example.autoimportxmltodb.modle.Relations;
import com.example.autoimportxmltodb.modle.RelationsType;
import com.example.autoimportxmltodb.modle.StandardColor;
import com.example.autoimportxmltodb.modle.StandardColorSystem;
import com.exampleautoimportxmltodb.xmlimport.common.CompletedStatus;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaImportCompletedEventArgs;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaImportProgressingEventArgs;
import com.exampleautoimportxmltodb.xmlimport.common.ImportFileVersion;
import com.exampleautoimportxmltodb.xmlimport.common.ImportMode;
import com.exampleautoimportxmltodb.xmlimport.common.ImportProgressingStatus;
import com.exampleautoimportxmltodb.xmlimport.common.Functions.SantintFunc;

/***
 * 导入配方行为接口
 * 
 * @author liu
 * 
 */
abstract class FormulaImportBehavior
{
	
	// #region 保护类型变量

	/***
	 * 配方导入的步进数
	 */
	protected int formulaSteps = 100;

	/***
	 * 是否已验证
	 */
	protected boolean formulaVerified = false;

	/***
	 * 导入配方参数实体
	 */
	protected FormulaImportParam formulaImportParam;

	/***
	 * 导入数据集合信息
	 */
	protected ImportDataSetInfo importDataSetInfo;

	/***
	 * 数据库连接
	 */
	protected SQLiteDatabase connection;

	/***
	 * 转换ID字典
	 */
	protected Hashtable<String, Hashtable<Integer, Integer>> dictConvertIds;

	// #endregion

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

	/***
	 * 构造函数
	 * 
	 * @param formulaImportParam
	 *            配方导入参数
	 */
	public FormulaImportBehavior(FormulaImportParam formulaImportParam)
	{
		this.formulaImportParam = formulaImportParam;
	}

	// #region 公有方法

	/***
	 * 导入数据方法
	 */
	public abstract void ImportFormulas();

	// #endregion

	// #region 保护类型方法

	/***
	 * 设置导入进度状态，触发事件
	 * 
	 * @param progressingStatus
	 *            导入进度状态
	 * @param message
	 *            导入进度信息
	 */
	protected void SetImportProgressing(ImportProgressingStatus progressingStatus, String message)
	{
		if (FormulaImportProgressing != null)
		{
			FormulaImportProgressingEventArgs e = new FormulaImportProgressingEventArgs(progressingStatus, message);
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
	protected void SetFormulaImportCompleted(CompletedStatus completedStatus, String message)
	{
		if (FormulaImportCompleted != null)
		{
			FormulaImportCompletedEventArgs e = new FormulaImportCompletedEventArgs(completedStatus, message, formulaImportParam.importOldFile);
			FormulaImportCompleted.Deal(e);
		}
	}

	

	// #endregion
}

/***
 * 标准配方导入行为类
 * 
 * @author 刘红杰
 * 
 */
class StandardFormulaImportBehavior extends FormulaImportBehavior
{
	private SQLiteDatabase unitConnection;

	/***
	 * 标准配方导入构造函数
	 * 
	 * @param formulaImportParam
	 *            导入配方参数
	 */
	public StandardFormulaImportBehavior(FormulaImportParam formulaImportParam)
	{
		super(formulaImportParam);
	}

	/***
	 * 导入标准配方
	 */
	@Override
	public void ImportFormulas()
	{
		// 验证导入文件的合法性
		if (!formulaVerified)
		{
			SetImportProgressing(ImportProgressingStatus.LoadData, MessageKeys.StandardFormula_ImportProgressing_CheckLegality);

			int n = DetectStandardFormulaFile();

			if (n == 0)// 不允许导入
			{
				SetFormulaImportCompleted(CompletedStatus.Failure, MessageKeys.StandardFormula_Valid_DifferentFormulaSystem);
				return;
			} else if (n == 1)// 版本低，确认是否导入
			{
				formulaVerified = true;
				SetFormulaImportCompleted(CompletedStatus.NeedConfirm, MessageKeys.StandardFormula_Valid_Versions);
				return;
			}
		}

		try
		{
			// 开始加载数据
			SetImportProgressing(ImportProgressingStatus.LoadData, MessageKeys.StandardFormula_Import_LoadData_DataFormat);

			// 获取导入的原始数据
			if (formulaImportParam.importXElement != null)
			{
				importDataSetInfo = GetImportDatas();
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			Utility.WriteLog("StandardFormulaImportBehavior", "ImportFormulas", ex);

			SetFormulaImportCompleted(CompletedStatus.Failure, MessageKeys.StandardFormula_ImportFailed_GetImportDatas_ColorExpert3);
			return;
		} finally
		{
			formulaVerified = false;
		}

		try
		{
			// 初始化数据库连接
			connection = Utility.getSantintStandardDatabase(formulaImportParam.DBContext);
			connection.beginTransaction();
			unitConnection = Utility.getSantintMainDatabase(formulaImportParam.DBContext);
			unitConnection.beginTransaction();
			if (formulaImportParam.ImportMode == ImportMode.EmptyImport)
			{
				EmptyStandardDataTable();
				if (formulaImportParam.importXElement == null)
				{
					ReadImportStandardDataTable();
				} else
				{
					ImportStandardDataTable();
				}
			} else
			{
				dictConvertIds = new Hashtable<String, Hashtable<Integer, Integer>>();
				dictConvertIds.put(Utility.TableColorantPrice, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableRelations, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableRelationsType, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableBrand, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableProduct, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableBrandProduct, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableAuto, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorGroup, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableStandardColorSystem, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableInnerColor, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableFormula, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorAuto, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableStandardColor, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorMap, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableDerivateColor, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorFormula, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableFormulaRemark, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorants, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorFormulaColorants, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorType, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorGroup_Color, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableAutoParts, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableColorAutoParts, new Hashtable<Integer, Integer>());
				dictConvertIds.put(Utility.TableFORMULASYSTEM, new Hashtable<Integer, Integer>());

				if (formulaImportParam.importXElement == null)
				{
					ReadAddStandardDataTable();
				} else
				{
					AddStandardDataTable();
				}
			}
			unitConnection.setTransactionSuccessful();
			connection.setTransactionSuccessful();

			// 导入成功事件
			SetFormulaImportCompleted(CompletedStatus.Success, MessageKeys.StandardFormula_ImportSuccess);
		} catch (Exception ex)
		{
			Utility.WriteLog("StandardFormulaImportBehavior", "ImportFormulas", ex);
			ex.printStackTrace();
			// 导入失败事件
			SetFormulaImportCompleted(CompletedStatus.Failure, MessageKeys.StandardFormula_ImportFailed);
		} finally
		{
			if (connection.isOpen())
			{
				connection.endTransaction();
				connection.close();
				
			}
			if (unitConnection.isOpen())
			{
				unitConnection.endTransaction();
				unitConnection.close();
			}
		}
	}

	/** 清空模式下导入标准配方库相关数据集 ***/
	private void ImportStandardDataTable()
	{
		// 正在导入基础信息事件
		SetImportProgressing(ImportProgressingStatus.ImportBaseData, MessageKeys.StandardFormula_Import_ImportBaseData_FormulaBaseData);

		// #region 导入基础信息

		 //导入配方体系 if
		if (!Common.IsNullOrEmpty(importDataSetInfo.FormulaSystemSet))
		{
			for (FormulaSystem s : importDataSetInfo.FormulaSystemSet)
			{
				Object[] objs =
				{ s.getFormulaSystemId(), s.getFormulaSystemName(), s.getFormulaSystemVersion(), DataTypeConvert.dateToString(s.getCreateTime()) };
				connection.execSQL(SqlString.Sql_StandardFormula_InsertFormulaSystem, objs);
			}
		}

		
		// 导入色母价格
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorantPriceSet))
		{
			for (ColorantPrice s : importDataSetInfo.ColorantPriceSet)
			{
				Object[] objs =
				{s.getColorantPriceId(),s.getColorantId(),s.getColorGroupId(),s.getColorantCode(),s.getColorantName(), s.getColorantFeatures(),s.getColorantDensity(),s.getColorantPrice(),s.getColorantPriceRate(),s.getCanSize(),s.getUnitId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				unitConnection.execSQL(SqlString.Sql_SCH_InsertColorantPrice, objs);
		}
		}

		// 导入关联类别表
		if (!Common.IsNullOrEmpty(importDataSetInfo.RelationsTypeSet))
		{
			for (RelationsType s : importDataSetInfo.RelationsTypeSet)
			{
				Object[] objs =
				{ s.getRelationsTypeId(), s.getRelationsTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_SCH_InsertRelationsType, objs);
			}
		}
		// 导入关联表
		if (!Common.IsNullOrEmpty(importDataSetInfo.RelationsSet))
		{
			for (Relations s : importDataSetInfo.RelationsSet)
			{
				Object[] objs =
				{ s.getRelationsId(), s.getRelationsName(), s.getRelationsTypeId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_SCH_InsertRelations, objs);
			}
		}

		// 导入品牌表
		if (!Common.IsNullOrEmpty(importDataSetInfo.BrandSet))
		{
			for (Brand s : importDataSetInfo.BrandSet)
			{
				Object[] objs =
				{ s.getBrandId(), s.getBrandName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_StandardFormula_InsertBrand, objs);
			}
		}

		// 导入产品
		if (!Common.IsNullOrEmpty(importDataSetInfo.ProductSet))
		{
			for (Product s : importDataSetInfo.ProductSet)
			{
				Object[] objs =
				{ s.getProductId(), s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_SH_InsertProduct, objs);
			}
		}

		// 导入品牌产品表
		if (!Common.IsNullOrEmpty(importDataSetInfo.BrandProductSet))
		{
			for (BrandProduct s : importDataSetInfo.BrandProductSet)
			{
				try
				{
					connection.execSQL(SqlString.Sql_StandardFormula_InsertBrandProduct, new Object[]
					{ s.getBrandProductId(), s.getProductId(), s.getBrandId() });
				} catch (SQLException ex)
				{
				}
			}
		}

		// 导入车厂型号表
		if (!Common.IsNullOrEmpty(importDataSetInfo.AutoSet))
		{
			for (Auto s : importDataSetInfo.AutoSet)
			{
				Object[] objs =
					{ s.getAutoId(), s.getMasterId(), s.getAutoName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRelationId(),DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate())};
				connection.execSQL(SqlString.Sql_StandardFormula_InsertAuto, objs);
			}
		}
		// 导入颜色分组表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorGroupSet))
		{
			for (ColorGroup s : importDataSetInfo.ColorGroupSet)
			{
				Object[] objs =
				{ s.getColorGroupId(), s.getColorGroupName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup, objs);
			}
		}

		// 导入标准色卡系统
		if (!Common.IsNullOrEmpty(importDataSetInfo.StandardColorSystemSet))
		{
			for (StandardColorSystem s : importDataSetInfo.StandardColorSystemSet)
			{
				Object[] objs =
				{ s.getStandardColorSystemId(), s.getStandardColorSystemName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_SH_InsertStandardColorSystem, objs);
			}
		}

		// 导入内部色卡表
		if (!Common.IsNullOrEmpty(importDataSetInfo.InnerColorSet))
		{
			for (InnerColor s : importDataSetInfo.InnerColorSet)
			{
				Object[] objs =
				{ s.getInnerColorId(), s.getColorTypeId(), s.getColorCode(), s.getColorName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRGB(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
				connection.execSQL(SqlString.Sql_SCH_InsertInnerColor, objs);
			}
		}

		// 导入配方表
		if (!Common.IsNullOrEmpty(importDataSetInfo.FormulaSet))
		{
			for (Formula s : importDataSetInfo.FormulaSet)
			{
				Object[] objs =
				{ s.getFormulaId(), s.getProductId(), s.getFormulaVersion(), DataTypeConvert.dateToString(s.getFormulaVersionDate()), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(),s.getSource() };
				connection.execSQL(SqlString.Sql_CH_InsertFormula, objs);
			}
		}

		// 导入色卡车型表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorAutoSet))
		{
			for (ColorAuto s : importDataSetInfo.ColorAutoSet)
			{
				Object[] objs =
				{ s.getColorAutoId(), s.getAutoId(), s.getInnerColorId(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_StandardFormula_InsertColorAuto, objs);
			}
		}

		// 导入标准色卡表
		if (!Common.IsNullOrEmpty(importDataSetInfo.StandardColorSet))
		{

			for (StandardColor s : importDataSetInfo.StandardColorSet)
			{
				Object[] objs =
				{ s.getInnerColorId(), s.getStandardColorCode(), s.getStandardColorSystemId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getStandardColorId() };
				try
				{
					connection.execSQL(SqlString.Sql_StandardFormula_InsertStandardColor, objs);
				} catch (SQLException ex)
				{
				}
			}
		}

		// 导入颜色地图
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorMapSet))
		{
			for (ColorMap s : importDataSetInfo.ColorMapSet)
			{
				Object[] objs =
				{ s.getColorMap(), s.getInnerColorId(), s.getPage(), s.getDocumentation() };
				connection.execSQL(SqlString.Sql_StandardFormula_InsertColorMap, objs);
			}
		}

		// 导入差异色卡表
		if (!Common.IsNullOrEmpty(importDataSetInfo.DerivateColorSet))
		{
			for (DerivateColor s : importDataSetInfo.DerivateColorSet)
			{
				Object[] objs =
				{ s.getDerivateColorId(), s.getInnerParentColorId(), s.getInnerColorId(),s.getRelationsId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_SCH_InsertDerivateColor, objs);
			}
		}
		// 色卡配方表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorFormulaSet))
		{
			for (ColorFormula s : importDataSetInfo.ColorFormulaSet)
			{
				Object[] objs =
				{ s.getColorFormulaId(), s.getFormulaId(), s.getFormulaId(), s.getLayerNumber(), s.getColorFormulaCode(), s.getLayerDescription(),DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
				connection.execSQL(SqlString.Sql_CH_InsertColorFormula, objs);
			}
		}
		// 导入配方信息表
		if (!Common.IsNullOrEmpty(importDataSetInfo.FormulaRemarkSet))
		{
			for (FormulaRemark s : importDataSetInfo.FormulaRemarkSet)
			{
				Object[] objs =
				{ s.getFormulaRemarkId(), s.getFormulaRemarks(), s.getColorFormulaId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_SCH_InsertFormulaRemark, objs);
			}
		}

		// 导入色母表表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorantsSet))
		{
			for (Colorants s : importDataSetInfo.ColorantsSet)
			{
				Object[] objs =
				{ s.getColorantId(), s.getColorGroupId(), s.getColorantCode(), s.getColorantName(), s.getColorantFeatures(), s.getColorantDensity(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_SCH_InsertColorants, objs);
			}
		}
		// 导入色卡配方色母表
				if (!Common.IsNullOrEmpty(importDataSetInfo.ColorFormulaColorantsSet))
				{
					for (ColorFormulaColorants s : importDataSetInfo.ColorFormulaColorantsSet)
					{
						try
						{
							connection.execSQL(SqlString.Sql_StandardFormula_InsertColorFormulaColorants, new Object[]
									{ s.getColorFormulaColorantsId(), s.getColorFormulaId(), s.getColorantsId(), s.getColorantSequence(), s.getWeightPercent(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) });
						} catch (SQLException ex)
						{
						}
					}
				}
		
		// 导入颜色类别表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorTypeSet))
		{
			for (ColorType s : importDataSetInfo.ColorTypeSet)
			{
				Object[] objs =
				{ s.getColorTypeId(), s.getColorTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_StandardFormula_InsertColorType, objs);
			}
		}
		// 导入色卡组别色卡表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorGroup_ColorSet))
		{

			for (ColorGroup_Color s : importDataSetInfo.ColorGroup_ColorSet)
			{
				Object[] objs =
				{ s.getColorGroupId(), s.getColorId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getColorGroupColorId() };
				try
				{
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup_Color, objs);
				} catch (SQLException ex)
				{
				}
			}
		}
		// TODO
		// 导入汽车部件表
		if (!Common.IsNullOrEmpty(importDataSetInfo.AutoPartsSet))
		{
			for (AutoParts s : importDataSetInfo.AutoPartsSet)
			{
				Object[] objs =
				{ s.getAutoPartsId(), s.getAutoPartsName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
				connection.execSQL(SqlString.Sql_StandardFormula_InsertAutoParts, objs);
			}
		}
		// 导入色卡车型部件表表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorAutoPartsSet))
		{

			for (ColorAutoParts s : importDataSetInfo.ColorAutoPartsSet)
			{
				Object[] objs =
				{ s.getColorAutoId(), s.getInnerColorId(), s.getAutoPartsId(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getColorAutoPartsId() };
				try
				{
					connection.execSQL(SqlString.Sql_SCH_InsertColorAutoParts, objs);
				} catch (SQLException ex)
				{
				}
			}
		}
		// #endregion

	}

	/**
	 * 清空模式下读取导入标准配方库相关数据集
	 * 
	 * @throws Exception
	 ***/
	private void ReadImportStandardDataTable() throws Exception
	{
		// 正在导入基础信息事件
		SetImportProgressing(ImportProgressingStatus.ImportBaseData, MessageKeys.StandardFormula_Import_ImportBaseData_FormulaBaseData);

		// final int fmaxId = Utility.GetMaxId(connection, "STANDARDFORMULA",
		// "FORMULAID");
		SaxParseService reader = new SaxParseService();

		
		ReaderParam colorantPriceParam = new ReaderParam();
		colorantPriceParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorantPrice parse(Hashtable<String, String> s)
			{
				ColorantPrice colorantPrice = new ColorantPrice();
				if (s.get("ColorantPriceId")!=null&&s.get("ColorantPriceId").length()>0)
				{
					colorantPrice.setColorantPriceId(Integer.parseInt(s.get("ColorantPriceId")));
					
				}
				else{
					colorantPrice.setColorantPriceId(Integer.parseInt(s.get("ColorantId")));
				}
				if (s.get("ColorantId")!=null&&s.get("ColorantId").length()>0)
				{
					colorantPrice.setColorantId(Integer.parseInt(s.get("ColorantId")));
				}
				if (s.get("ColorGroupId")!=null&&s.get("ColorGroupId").length()>0)
				{
					colorantPrice.setColorGroupId(Integer.parseInt(s.get("ColorGroupId")));
				}
				colorantPrice.setColorantCode(s.get("ColorantCode"));
				colorantPrice.setColorantName(s.get("ColorantName"));
				colorantPrice.setColorantFeatures(s.get("ColorantFeatures"));
				colorantPrice.setColorantDensity(LanguageLocal.ConvertStringToDoubleWithEn(s.get("ColorantDensity")));
				if (s.get("ColorantPrices")!=null&&s.get("ColorantPrices").length()>0)
				{
					colorantPrice.setColorantPrice(LanguageLocal.ConvertStringToDoubleWithEn(s.get("ColorantPrices")));
				}
				else
				{
					colorantPrice.setColorantPrice(LanguageLocal.ConvertStringToDoubleWithEn("0"));
				}
				if (s.get("ColorPriceRate")!=null&&s.get("ColorPriceRate").length()>0)
				{
					colorantPrice.setColorantPriceRate(LanguageLocal.ConvertStringToDoubleWithEn(s.get("ColorPriceRate")));
				}
				else
				{
					colorantPrice.setColorantPriceRate(LanguageLocal.ConvertStringToDoubleWithEn("1"));
				}
				if (s.get("CanSize")!=null&&s.get("CanSize").length()>0)
				{
					colorantPrice.setCanSize(LanguageLocal.ConvertStringToDoubleWithEn(s.get("CanSize")));
				}
				else
				{
					colorantPrice.setCanSize(LanguageLocal.ConvertStringToDoubleWithEn("1"));
				}
				if (s.get("UnitName")!=null&&s.get("UnitName").length()>0)
				{
					colorantPrice.setUnitId(s.get("UnitName"));
				}
				else
				{
					colorantPrice.setUnitId("L");
				}
				colorantPrice.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorantPrice.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorantPrice;
			}
		};
		colorantPriceParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{	
				for (Object o : datas)
				{
					List<DictEntity> list = Utility.GetDictEntitys(unitConnection, "ColorantPrice", "ColorantPriceId", "ColorantCode");
					ColorantPrice s = (ColorantPrice) o;
					DictEntity de = Utility.First(list, s.getColorantCode(), false);
					if (de == null)
					{
					Object[] objs =
					{s.getColorantPriceId(),s.getColorantId(),s.getColorGroupId(),s.getColorantCode(),s.getColorantName(), s.getColorantFeatures(),s.getColorantDensity(),s.getColorantPrice(),s.getColorantPriceRate(),s.getCanSize(),s.getUnitId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					unitConnection.execSQL(SqlString.Sql_SCH_InsertColorantPrice, objs);
					}
				}
			}
		};
		reader.regix("ColorantPrice", colorantPriceParam);

		ReaderParam relationsTypeParam = new ReaderParam();
		relationsTypeParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public RelationsType parse(Hashtable<String, String> s)
			{
				RelationsType relatonsType = new RelationsType();
				relatonsType.setRelationsTypeId(Integer.parseInt(s.get("RelationsTypeId")));
				relatonsType.setRelationsTypeName(s.get("RelationsTypeName"));
				relatonsType.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				relatonsType.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return relatonsType;
			}
		};
		relationsTypeParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					RelationsType s = (RelationsType) o;
					Object[] objs =
					{ s.getRelationsTypeId(), s.getRelationsTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertRelationsType, objs);
				}
			}
		};
		reader.regix("RelationsType", relationsTypeParam);

		ReaderParam relationsParam = new ReaderParam();
		relationsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Relations parse(Hashtable<String, String> s)
			{
				Relations relations = new Relations();
				relations.setRelationsId(Integer.parseInt(s.get("RelationsId")));
				relations.setRelationsTypeId(Integer.parseInt(s.get("RelationsTypeId")));
				relations.setRelationsName(s.get("RelationsName"));
				relations.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				relations.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return relations;
			}
		};
		relationsParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
//				List<Object> lists=Logger.removeDuplicate(datas);
				for (Object o : datas)
				{
					Relations s = (Relations) o;
					Object[] objs =
					{ s.getRelationsId(), s.getRelationsName(), s.getRelationsTypeId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertRelations, objs);
				}
			}
		};
		reader.regix("Relations", relationsParam);

		ReaderParam brandParam = new ReaderParam();
		brandParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Brand parse(Hashtable<String, String> s)
			{
				Brand brand = new Brand();
				brand.setBrandId(Integer.parseInt(s.get("BrandId")));
				brand.setBrandName(s.get("BrandName"));
				brand.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				brand.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return brand;
			}
		};
		brandParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					Brand s = (Brand) o;
					Object[] objs =
					{ s.getBrandId(), s.getBrandName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertBrand, objs);
				}
			}
		};
		reader.regix("Brand", brandParam);

		ReaderParam productParam = new ReaderParam();
		productParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Product parse(Hashtable<String, String> s)
			{
				Product product = new Product();
				product.setProductId(Integer.parseInt(s.get("ProductId")));
				product.setProductCode(s.get("ProductCode"));
				product.setProductName(s.get("ProductName"));
				product.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				product.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return product;
			}
		};
		productParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					Product s = (Product) o;
					Object[] objs =
					{ s.getProductId(), s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SH_InsertProduct, objs);
				}
			}
		};
		reader.regix("Product", productParam);

		ReaderParam brandProductParam = new ReaderParam();
		brandProductParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public BrandProduct parse(Hashtable<String, String> s)
			{
				BrandProduct brandProduct = new BrandProduct();
				brandProduct.setBrandId(Integer.parseInt(s.get("BrandId")));// BrandId
				brandProduct.setBrandProductId(Integer.parseInt(s.get("BrandProductId")));
				brandProduct.setProductId(Integer.parseInt(s.get("ProductId")));
				return brandProduct;
			}
		};
		brandProductParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					BrandProduct s = (BrandProduct) o;
					try
					{
						connection.execSQL(SqlString.Sql_StandardFormula_InsertBrandProduct, new Object[]
						{ s.getBrandProductId(), s.getProductId(),s.getBrandId() });
					} catch (SQLException ex)
					{
					}
				}
			}
		};
		reader.regix("BrandProduct", brandProductParam);

		ReaderParam autoParam = new ReaderParam();
		autoParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Auto parse(Hashtable<String, String> s)
			{
				Auto auto = new Auto();
				auto.setAutoId(Integer.parseInt(s.get("AutoId")));
				if (s.get("MasterId") != null && s.get("MasterId").length() > 0)
				{
					auto.setMasterId(Integer.parseInt(s.get("MasterId")));
				}else
				{
					auto.setMasterId(0);
				}
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{
					auto.setRelationId(Integer.parseInt(s.get("RelationId")));
				}
				auto.setAutoName(s.get("AutoName"));
				auto.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearFirstUsed")));
				auto.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearLastUsed")));
				auto.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				auto.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return auto;
			}
		};
		autoParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					Auto s = (Auto) o;
//					List<Auto> mlist = Utility.GetAutoEntitys(connection);
//					Auto auto = Utility.AutoFirsts(mlist, s.getAutoName());
//					if (auto!=null){
					Object[] objs =
					{ s.getAutoId(), s.getMasterId(), s.getAutoName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRelationId(),DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate())};
					connection.execSQL(SqlString.Sql_StandardFormula_InsertAuto, objs);
//					}
				}
			}
		};
		reader.regix("Auto", autoParam);

		ReaderParam colorGroupParam = new ReaderParam();
		colorGroupParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorGroup parse(Hashtable<String, String> s)
			{
				ColorGroup colorGroup = new ColorGroup();
				if (s.get("ColorGroupId") != null && s.get("ColorGroupId").length() > 0)
				{

					colorGroup.setColorGroupId(Integer.parseInt(s.get("ColorGroupId")));
				}
				colorGroup.setColorGroupName(s.get("ColorGroupName"));
				colorGroup.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorGroup.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorGroup;
			}
		};
		colorGroupParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					ColorGroup s = (ColorGroup) o;
					Object[] objs =
					{ s.getColorGroupId(), s.getColorGroupName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup, objs);
				}
			}
		};
		reader.regix("ColorGroup", colorGroupParam);

		ReaderParam standardColorSystemParam = new ReaderParam();
		standardColorSystemParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public StandardColorSystem parse(Hashtable<String, String> s)
			{
				StandardColorSystem standardColorSystem = new StandardColorSystem();
				if (s.get("StandardColorSystemId") != null && s.get("StandardColorSystemId").length() >= 0)
				{
					standardColorSystem.setStandardColorSystemId(Integer.parseInt(s.get("StandardColorSystemId")));
				}
				standardColorSystem.setStandardColorSystemName(s.get("StandardColorSystemName"));
				standardColorSystem.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				standardColorSystem.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return standardColorSystem;
			}
		};
		standardColorSystemParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					StandardColorSystem s = (StandardColorSystem) o;
					Object[] objs =
					{ s.getStandardColorSystemId(), s.getStandardColorSystemName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SH_InsertStandardColorSystem, objs);
				}
			}
		};
		reader.regix("StandardColorSystem", standardColorSystemParam);

		ReaderParam innerColorParam = new ReaderParam();
		innerColorParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public InnerColor parse(Hashtable<String, String> s)
			{
				InnerColor innerColor = new InnerColor();
				innerColor.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));

				if (s.get("ColorTypeId") != null && s.get("ColorTypeId").length() > 0)
				{
					innerColor.setColorTypeId(Integer.parseInt(s.get("ColorTypeId")));

				}
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{

					innerColor.setRelationId(Integer.parseInt(s.get("RelationId")));
				}

				innerColor.setColorCode(s.get("ColorCode"));
				innerColor.setColorName(s.get("ColorName"));
				innerColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				innerColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				innerColor.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearFirstUsed")));
				innerColor.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearLastUsed")));
				innerColor.setRGB(!Common.IsNullOrEmpty(s.get("RGB")) ? s.get("RGB") : "000000");
				return innerColor;
			}
		};
		innerColorParam.DataImport = new IDataImport()
		{

			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					InnerColor s = (InnerColor) o;
					Object[] objs =
					{ s.getInnerColorId(), s.getColorTypeId(), s.getColorCode(), s.getColorName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRGB(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
					connection.execSQL(SqlString.Sql_SCH_InsertInnerColor, objs);
				}
			}
		};
		reader.regix("InnerColor", innerColorParam);

		ReaderParam formulaParam = new ReaderParam();
		formulaParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Formula parse(Hashtable<String, String> s)
			{
				Formula formula = new Formula();
				formula.setFormulaId(Integer.parseInt(s.get("FormulaId")));
				formula.setProductId(Integer.parseInt(s.get("ProductId")));
				formula.setFormulaVersion(s.get("FormulaVersion"));
				if (s.get("Source") != null && s.get("Source").length() > 0)
				{
					formula.setSource(Integer.parseInt(s.get("Source")));
				}
				else
				{
					formula.setSource(Integer.parseInt("-1"));
				}
				formula.setFormulaVersionDate(ConvertStringToDateTimeWithSystem(s.get("FormulaVersionDate")));
				formula.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				formula.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{
					formula.setRelationId(Integer.parseInt(s.get("RelationId")));
				}
				return formula;
			}
		};
		formulaParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					Formula s = (Formula) o;
					Object[] objs =
					{ s.getFormulaId(), s.getProductId(), s.getFormulaVersion(), DataTypeConvert.dateToString(s.getFormulaVersionDate()), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(),s.getSource() };
					connection.execSQL(SqlString.Sql_CH_InsertFormula, objs);
				}
			}
		};
		reader.regix("Formula", formulaParam);

		ReaderParam colorAutoParam = new ReaderParam();
		colorAutoParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorAuto parse(Hashtable<String, String> s)
			{
				ColorAuto colorAuto = new ColorAuto();
				colorAuto.setAutoId(Integer.parseInt(s.get("AutoId")));
				colorAuto.setColorAutoId(Integer.parseInt(s.get("ColorAutoId")));
				colorAuto.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					colorAuto.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				colorAuto.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				colorAuto.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearFirstUsed")));
				colorAuto.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearLastUsed")));
				return colorAuto;
			}
		};
		colorAutoParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					ColorAuto s = (ColorAuto) o;
					Object[] objs =
					{ s.getColorAutoId(), s.getAutoId(), s.getInnerColorId(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorAuto, objs);
				}
			}
		};
		reader.regix("ColorAuto", colorAutoParam);

		ReaderParam standardColorParam = new ReaderParam();
		standardColorParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public StandardColor parse(Hashtable<String, String> s)
			{
				StandardColor standardColor = new StandardColor();
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					standardColor.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				if (s.get("StandardColorId") != null && s.get("StandardColorId").length() > 0)
				{
					standardColor.setStandardColorId(Integer.parseInt(s.get("StandardColorId")));
				}
				if (s.get("StandardColorSystemId") != null && s.get("StandardColorSystemId").length() > 0)
				{
					standardColor.setStandardColorSystemId(Integer.parseInt(s.get("StandardColorSystemId")));
				}
				standardColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				standardColor.setStandardColorCode(s.get("StandardColorCode"));
				standardColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return standardColor;
			}
		};
		// TODO
		standardColorParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					StandardColor s = (StandardColor) o;
					Object[] objs =
					{ s.getInnerColorId(), s.getStandardColorCode(), s.getStandardColorSystemId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getStandardColorId() };
					try
					{
						connection.execSQL(SqlString.Sql_StandardFormula_InsertStandardColor, objs);
					} catch (SQLException ex)
					{
					}
				}
			}
		};
		reader.regix("StandardColor", standardColorParam);

		ReaderParam colorMapParam = new ReaderParam();
		colorMapParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorMap parse(Hashtable<String, String> s)
			{
				ColorMap colorMap = new ColorMap();
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)//InnerColorId
				{
					colorMap.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				if (s.get("ColorMapId") != null && s.get("ColorMapId").length() > 0)
				{
					colorMap.setColorMap(Integer.parseInt((String) s.get("ColorMapId")));
				}
				colorMap.setDocumentation(s.get("Documentation"));
				colorMap.setPage(s.get("Page"));
				return colorMap;
			}
		};
		colorMapParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					ColorMap s = (ColorMap) o;
					Object[] objs =
					{ s.getColorMap(), s.getInnerColorId(), s.getPage(), s.getDocumentation() };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorMap, objs);
				}
			}
		};
		reader.regix("ColorMap", colorMapParam);

		ReaderParam derivateColorParam = new ReaderParam();
		derivateColorParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public DerivateColor parse(Hashtable<String, String> s)
			{
				DerivateColor derivateColor = new DerivateColor();
				derivateColor.setDerivateColorId(Integer.parseInt(s.get("DerivateColorId")));
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					derivateColor.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				if (s.get("InnerParentColorId") != null && s.get("InnerParentColorId").length() > 0)
				{
					derivateColor.setInnerParentColorId(Integer.parseInt(s.get("InnerParentColorId")));
				}
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{
					derivateColor.setRelationsId(Integer.parseInt(s.get("RelationId")));//RelationId
				}
				derivateColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				derivateColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return derivateColor;
			}
		};
		derivateColorParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					DerivateColor s = (DerivateColor) o;
					Object[] objs =
					{ s.getDerivateColorId(), s.getInnerParentColorId(), s.getInnerColorId(),s.getRelationsId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertDerivateColor, objs);
				}
			}
		};
		reader.regix("DerivateColor", derivateColorParam);

		ReaderParam colorFormulaParam = new ReaderParam();
		colorFormulaParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorFormula parse(Hashtable<String, String> s)
			{
				ColorFormula colorFormula = new ColorFormula();
				colorFormula.setColorFormulaId(Integer.parseInt(s.get("ColorFormulaId")));
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					colorFormula.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				if (s.get("FormulaId") != null && s.get("FormulaId").length() > 0)
				{
					colorFormula.setFormulaId(Integer.parseInt(s.get("FormulaId")));
				}
				if (s.get("LayerNumber") != null && s.get("LayerNumber").length() > 0)
				{
					colorFormula.setLayerNumber(Integer.parseInt(s.get("LayerNumber")));
				}
				colorFormula.setColorFormulaCode(s.get("ColorFormulaCode"));
				if (s.get("LayerDescription") != null && s.get("LayerDescription").length() > 0)
				{
					colorFormula.setLayerDescription(s.get("LayerDescription"));//LayerDescription
				}else{
					colorFormula.setLayerDescription(s.get("LayerNumber"));//LayerDescription
				}
			
				colorFormula.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorFormula.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{

					colorFormula.setRelationId(Integer.parseInt(s.get("RelationId")));
				}
				return colorFormula;
			}
		};
		colorFormulaParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					ColorFormula s = (ColorFormula) o;
					Object[] objs =
					{ s.getColorFormulaId(), s.getInnerColorId(), s.getFormulaId(), s.getLayerNumber(), s.getColorFormulaCode(),s.getLayerDescription(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
					connection.execSQL(SqlString.Sql_CH_InsertColorFormula, objs);
				}
			}
		};
		reader.regix("ColorFormula", colorFormulaParam);

		ReaderParam formulaRemarkParam = new ReaderParam();
		formulaRemarkParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public FormulaRemark parse(Hashtable<String, String> s)
			{
				FormulaRemark formulaRemark = new FormulaRemark();
				formulaRemark.setFormulaRemarkId(Integer.parseInt(s.get("FormulaRemarkId")));
				formulaRemark.setFormulaRemarks(s.get("FormulaRemarks"));
				if (s.get("ColorFormulaId") != null && s.get("ColorFormulaId").length() > 0)
				{
					formulaRemark.setColorFormulaId(Integer.parseInt(s.get("ColorFormulaId")));
				}
				formulaRemark.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				formulaRemark.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return formulaRemark;
			}
		};
		formulaRemarkParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					FormulaRemark s = (FormulaRemark) o;
					Object[] objs =
					{ s.getFormulaRemarkId(), s.getFormulaRemarks(), s.getColorFormulaId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertFormulaRemark, objs);
				}
			}
		};
		reader.regix("FormulaRemark", formulaRemarkParam);

		ReaderParam colorantsParam = new ReaderParam();
		colorantsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Colorants parse(Hashtable<String, String> s)
			{
				Colorants colorants = new Colorants();
				colorants.setColorantId(Integer.parseInt(s.get("ColorantId")));
				colorants.setColorantCode(s.get("ColorantCode"));
				colorants.setColorantFeatures(s.get("ColorantFeatures"));
				colorants.setColorantName(s.get("ColorantName"));
				colorants.setColorGroupId(Integer.parseInt(s.get("ColorGroupId")));
				colorants.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorants.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				colorants.setColorantDensity(LanguageLocal.ConvertStringToDoubleWithEn(s.get("ColorantDensity")));
				return colorants;
			}
		};
		colorantsParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					List<DictEntity> list = Utility.GetDictEntitys(connection, "Colorants", "ColorantId", "ColorantCode");
					Colorants s = (Colorants) o;
					DictEntity de = Utility.First(list, s.getColorantCode(), false);
					if (de==null)
					{
					Object[] objs =
					{ s.getColorantId(), s.getColorGroupId(), s.getColorantCode(), s.getColorantName(), s.getColorantFeatures(), s.getColorantDensity(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertColorants, objs);
					}
				}
			}
		};
		reader.regix("Colorants", colorantsParam);
//		TODO
		ReaderParam colorFormulaColorantsParam = new ReaderParam();
		colorFormulaColorantsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorFormulaColorants parse(Hashtable<String, String> s)
			{
				ColorFormulaColorants colorants = new ColorFormulaColorants();
				colorants.setColorFormulaColorantsId(Integer.parseInt(s.get("ColorFormulaColorantsId")));
				if (s.get("ColorantSequence") != null && s.get("ColorantSequence").length() > 0)
				{
					colorants.setColorantSequence(Integer.parseInt(s.get("ColorantSequence")));
				}
				colorants.setWeightPercent(EncryptDecryptHelper.DESEncrypt(s.get("WeightPercent"), "@SANTINT!"));
				colorants.setColorantsId(Integer.parseInt(s.get("ColorantsId")));
				colorants.setColorFormulaId(Integer.parseInt(s.get("ColorFormulaId")));
				colorants.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorants.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorants;
			}
		};
		colorFormulaColorantsParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					ColorFormulaColorants s = (ColorFormulaColorants) o;
					Object[] objs =
						{ s.getColorFormulaColorantsId(), s.getColorFormulaId(), s.getColorantsId(), s.getColorantSequence(), s.getWeightPercent(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertColorFormulaColorants, objs);
				}
			}
		};
		reader.regix("ColorFormulaColorants", colorFormulaColorantsParam);

		ReaderParam colorTypeParam = new ReaderParam();
		colorTypeParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorType parse(Hashtable<String, String> s)
			{
				ColorType colorType = new ColorType();
				colorType.setColorTypeId(Integer.parseInt(s.get("ColorTypeId")));
				colorType.setColorTypeName(s.get("ColorTypeName"));
				colorType.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorType.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorType;
			}
		};
		colorTypeParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					ColorType s = (ColorType) o;
					Object[] objs =
					{ s.getColorTypeId(), s.getColorTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorType, objs);
				}
			}
		};
		reader.regix("ColorType", colorTypeParam);

		ReaderParam colorGroup_ColorParam = new ReaderParam();
		colorGroup_ColorParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorGroup_Color parse(Hashtable<String, String> s)
			{
				ColorGroup_Color colorGroup_Color = new ColorGroup_Color();
				if (s.get("ColorGroupColorId") != null && s.get("ColorGroupColorId").length() > 0)
				{
					colorGroup_Color.setColorGroupColorId(Integer.parseInt(s.get("ColorGroupColorId")));
				}
				if (s.get("ColorGroupId") != null && s.get("ColorGroupId").length() > 0)
				{
					colorGroup_Color.setColorGroupId(Integer.parseInt(s.get("ColorGroupId")));
				}
				if (s.get("ColorId") != null && s.get("ColorId").length() > 0)
				{
					colorGroup_Color.setColorId(Integer.parseInt(s.get("ColorId")));
				}
				colorGroup_Color.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorGroup_Color.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorGroup_Color;
			}
		};
		colorGroup_ColorParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					ColorGroup_Color s = (ColorGroup_Color) o;
					Object[] objs =
					{ s.getColorGroupId(), s.getColorId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getColorGroupColorId() };
					try
					{
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup_Color, objs);
					} catch (SQLException ex)
					{
					}
				}
			}
		};
		reader.regix("ColorGroup_Color", colorGroup_ColorParam);

		ReaderParam autoPartsParam = new ReaderParam();
		autoPartsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public AutoParts parse(Hashtable<String, String> s)
			{
				AutoParts autoParts = new AutoParts();
				if (s.get("AutoPartsId") != null && s.get("AutoPartsId").length() > 0)
				{
					autoParts.setAutoPartsId(Integer.parseInt(s.get("AutoPartsId")));
				}
				autoParts.setAutoPartsName(s.get("AutoPartsName"));
				autoParts.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				autoParts.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return autoParts;
			}
		};
		autoPartsParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					AutoParts s = (AutoParts) o;
					Object[] objs =
					{ s.getAutoPartsId(), s.getAutoPartsName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertAutoParts, objs);
				}
			}
		};
		reader.regix("AutoParts", autoPartsParam);

		ReaderParam colorAutoPartsParam = new ReaderParam();
		colorAutoPartsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorAutoParts parse(Hashtable<String, String> s)
			{
				ColorAutoParts colorAutoParts = new ColorAutoParts();
				if (s.get("AutoPartsId") != null && s.get("AutoPartsId").length() > 0)
				{
					colorAutoParts.setAutoPartsId(Integer.parseInt(s.get("AutoPartsId")));
				}
				if (s.get("ColorAutoId") != null && s.get("ColorAutoId").length() > 0)
				{
					colorAutoParts.setColorAutoId(Integer.parseInt(s.get("ColorAutoId")));
				}
				if (s.get("ColorAutoPartsId") != null && s.get("ColorAutoPartsId").length() > 0)
				{
					colorAutoParts.setColorAutoPartsId(Integer.parseInt(s.get("ColorAutoPartsId")));
				}
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					colorAutoParts.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				colorAutoParts.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorAutoParts.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorAutoParts;
			}
		};
		colorAutoPartsParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				for (Object o : datas)
				{
					ColorAutoParts s = (ColorAutoParts) o;
					Object[] objs =
					{ s.getColorAutoId(), s.getInnerColorId(), s.getAutoPartsId(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getColorAutoPartsId() };
					try
					{
						connection.execSQL(SqlString.Sql_SCH_InsertColorAutoParts, objs);
					} catch (SQLException ex)
					{
					}
				}
			}
		};
		reader.regix("ColorAutoParts", colorAutoPartsParam);

		// 导入配方体系
				Object[] objs = { formulaImportParam.CurrentFormulaSystem.getFormulaSystemId(),formulaImportParam.CurrentFormulaSystem.getFormulaSystemName(), formulaImportParam.CurrentFormulaSystem.getFormulaSystemVersion(), DataTypeConvert.dateToString(formulaImportParam.CurrentFormulaSystem.getCreateTime()) };
				connection.execSQL(SqlString.Sql_StandardFormula_InsertFormulaSystem, objs);
		
		reader.start(formulaImportParam.importFile, false);

	}

	/***
	 * 清空标准配方库相关数据集
	 */
	private void EmptyStandardDataTable()
	{
		// 正在清空标准配方库相关数据集事件
		SetImportProgressing(ImportProgressingStatus.ImportBaseData, MessageKeys.StandardFormula_Import_ImportBaseData_ClearData);

		try
		{
			unitConnection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorantPrice"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "FORMULASYSTEM"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "BrandProduct"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "Brand"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorAutoParts"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "AutoParts"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorFormulaColorants"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "Colorants"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorGroup_Color"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorGroup"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorMap"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "DerivateColor"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "FormulaRemark"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "StandardColor"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "StandardColorSystem"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorAuto"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "Auto"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorFormula"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "Formula"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "Product"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "InnerColor"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "ColorType"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "Relations"));
			connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "RelationsType"));
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// #region 私有方法

	/***
	 * 获取导入数据
	 */
	@SuppressWarnings("incomplete-switch")
	private ImportDataSetInfo GetImportDatas()
	{
		ImportDataConversionBehavior importDataConversionBehavior = null;

		switch (formulaImportParam.ImportFileVersion)
		{
		case ColorExpert3_1:
			importDataConversionBehavior = new ColorExpert3_1StandardFormulaImport(formulaImportParam);
			break;
		}

		ImportDataSetInfo importDataSet = importDataConversionBehavior.GetImportDatas();

		return importDataSet;
	}

	/***
	 * 附加导入标准配方库相关数据集
	 */
	private void AddStandardDataTable()
	{
		// 正在导入基础信息事件
		SetImportProgressing(ImportProgressingStatus.ImportBaseData, MessageKeys.StandardFormula_Import_ImportBaseData_FormulaBaseData);

		// #region 导入基础信息

		// 导入配方体系
		if (!Common.IsNullOrEmpty(importDataSetInfo.FormulaSystemSet))
		{
			for (FormulaSystem s : importDataSetInfo.FormulaSystemSet)
			{
				Object[] objs =
				{ s.getFormulaSystemId(), s.getFormulaSystemName(), s.getFormulaSystemVersion(), DataTypeConvert.dateToString(s.getCreateTime()) };
				connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "FormulaSystem"));
				connection.execSQL(SqlString.Sql_StandardFormula_InsertFormulaSystem, objs);
			}
		}

		// 导入色母价格
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorantPriceSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(unitConnection, "ColorantPrice", "ColorantPriceId", "ColorantName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
			if (Common.IsNullOrEmpty(list))
			{
				for (ColorantPrice s : importDataSetInfo.ColorantPriceSet)
				{
					Object[] objs =
					{s.getColorantPriceId(),s.getColorantId(),s.getColorGroupId(),s.getColorantCode(),s.getColorantName(), s.getColorantFeatures(),s.getColorantDensity(),s.getColorantPrice(),s.getColorantPriceRate(),s.getCanSize(),s.getUnitId(),s.getCreatedDate(),s.getSystemDate() };
					unitConnection.execSQL(SqlString.Sql_SCH_InsertColorantPrice, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorantPrice");

				for (ColorantPrice s : importDataSetInfo.ColorantPriceSet)
				{
					DictEntity de = Utility.First(list, s.getColorantName(), true);
					if (de == null)
					{
						Object[] objs =
						{ ++maxId,s.getColorantId(),s.getColorGroupId(),s.getColorantCode(),s.getColorantName(), s.getColorantFeatures(),s.getColorantDensity(),s.getColorantPrice(),s.getColorantPriceRate(),s.getCanSize(),s.getUnitId(),s.getCreatedDate(),s.getSystemDate() };
						unitConnection.execSQL(SqlString.Sql_SCH_InsertColorantPrice, objs);
						}  
					else
					{
							if (isUpdate)
							{
								Object[] objs =
								{ s.getColorantId(),s.getColorGroupId(),s.getColorantCode(),s.getColorantName(), s.getColorantFeatures(),s.getColorantDensity(),s.getColorantPrice(),s.getColorantPriceRate(),s.getCanSize(),s.getUnitId(),s.getCreatedDate(),s.getSystemDate() ,s.getColorantPriceId()};
								unitConnection.execSQL(SqlString.Sql_SCH_UpdateColorantPrice, objs);
							}
					}
				}
			}
		}
		// 导入关联类别表
		if (!Common.IsNullOrEmpty(importDataSetInfo.RelationsTypeSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "RelationsType", "RelationsTypeId", "RelationsTypeName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
			if (Common.IsNullOrEmpty(list))
			{
				for (RelationsType s : importDataSetInfo.RelationsTypeSet)
				{
					Object[] objs =
					{ s.getRelationsTypeId(), s.getRelationsTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertRelationsType, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("RelationsType");

				for (RelationsType s : importDataSetInfo.RelationsTypeSet)
				{
					DictEntity de = Utility.First(list, s.getRelationsTypeName(), true);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableRelationsType).put(s.getRelationsTypeId(), ++maxId);
						Object[] objs =
						{ maxId, s.getRelationsTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertRelationsType, objs);
					} else
					{
						dictConvertIds.get(Utility.TableRelationsType).put(s.getRelationsTypeId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getRelationsTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_SCH_UpdateRelationsType, objs);
						}
					}
				}
			}
		}
		// 导入关联表
		if (!Common.IsNullOrEmpty(importDataSetInfo.RelationsSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "Relations", "RelationsId", "RelationsName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (Relations s : importDataSetInfo.RelationsSet)
				{
					int RelationsTypeId = dictConvertIds.get(Utility.TableRelationsType).size() > 0 ? dictConvertIds.get(Utility.TableRelationsType).get(s.getRelationsTypeId()) : s.getRelationsTypeId();
					Object[] objs =
					{ s.getRelationsId(), s.getRelationsName(), RelationsTypeId, DataTypeConvert.dateToString(s.getCreatedDate(), DataTypeConvert.dateToString(s.getSystemDate())) };
					connection.execSQL(SqlString.Sql_SCH_InsertRelations, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Relations");

				for (Relations s : importDataSetInfo.RelationsSet)
				{
					int RelationsTypeId = dictConvertIds.get(Utility.TableRelationsType).size() > 0 ? dictConvertIds.get(Utility.TableRelationsType).get(s.getRelationsTypeId()) : s.getRelationsTypeId();
					DictEntity de = Utility.First(list, s.getRelationsName(), true);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableRelations).put(s.getRelationsId(), ++maxId);
						Object[] objs =
						{ maxId, s.getRelationsName(), RelationsTypeId, DataTypeConvert.dateToString(s.getCreatedDate(), DataTypeConvert.dateToString(s.getSystemDate())) };
						connection.execSQL(SqlString.Sql_SCH_InsertRelations, objs);
					} else
					{
						dictConvertIds.get(Utility.TableRelations).put(s.getRelationsId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getRelationsName(), RelationsTypeId, DataTypeConvert.dateToString(s.getCreatedDate(), DataTypeConvert.dateToString(s.getSystemDate())), de.Id };
							connection.execSQL(SqlString.Sql_SH_UpdateRelations, objs);
						}
					}
				}
			}
		}

		// 导入品牌表
		if (!Common.IsNullOrEmpty(importDataSetInfo.BrandSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "Brand", "BrandId", "BrandName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (Brand s : importDataSetInfo.BrandSet)
				{
					Object[] objs =
					{ s.getBrandId(), s.getBrandName(), DataTypeConvert.dateToString(s.getCreateDate(), DataTypeConvert.dateToString(s.getSystemDate())) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertBrand, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Brand");

				for (Brand s : importDataSetInfo.BrandSet)
				{

					DictEntity de = Utility.First(list, s.getBrandName(), false);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableBrand).put(s.getBrandId(), ++maxId);
						Utility.isSmallExists=true;
						Object[] objs =
						{ maxId, s.getBrandName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertBrand, objs);
					} else
					{
						Utility.isSmallExists=false;
						dictConvertIds.get(Utility.TableBrand).put(s.getBrandId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getBrandName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_CH_UpdateBrand, objs);
						}
					}
				
				}
			}
		}

		// 导入产品
		if (!Common.IsNullOrEmpty(importDataSetInfo.ProductSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "Product", "ProductId", "ProductCode");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (Product s : importDataSetInfo.ProductSet)
				{
					Object[] objs =
					{ s.getProductId(), s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SH_InsertProduct, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Product");

				for (Product s : importDataSetInfo.ProductSet)
				{

					if (Utility.isSmallExists)
					{
						DictEntity de = Utility.First(list, s.getProductName(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableProduct).put(s.getProductId(), ++maxId);
							Object[] objs =
							{ maxId, s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_SH_InsertProduct, objs);
						} else
						{
							dictConvertIds.get(Utility.TableProduct).put(s.getProductId(), de.Id);
							if (isUpdate)
							{
								Object[] objs = 
								{ s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_SCH_UpdateProduct, objs);
							}
						}
						
					} else
					{  
						dictConvertIds.get(Utility.TableProduct).put(s.getProductId(), ++maxId);
						Object[] objs =
						{ maxId, s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SH_InsertProduct, objs);
					}
				
				}
			}
		}

		// 导入品牌产品表
		if (!Common.IsNullOrEmpty(importDataSetInfo.BrandProductSet))
		{
			List<BrandProduct> list = Utility.GetBrandProductEntitys(connection); 
			int maxId = Utility.GetMaxId(connection, "BrandProduct", "BrandProductId");
			for (BrandProduct s : importDataSetInfo.BrandProductSet)
			{
				try
				{
					int sid = dictConvertIds.get(Utility.TableBrand).size() > 0 ? dictConvertIds.get(Utility.TableBrand).get(s.getBrandId()) : s.getBrandId();
					int pid = dictConvertIds.get(Utility.TableProduct).size() > 0 ? dictConvertIds.get(Utility.TableProduct).get(s.getProductId()) : s.getProductId();
					connection.execSQL(SqlString.Sql_StandardFormula_InsertBrandProduct, new Object[]
					{ ++maxId, pid, sid });
				} catch (SQLException ex)
				{
			}
		}
		}
		// 导入车厂型号表
		if (!Common.IsNullOrEmpty(importDataSetInfo.AutoSet))
		{  
			List<DictEntity> list = Utility.GetDictEntitys(connection, "Auto", "AutoId", "AutoName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (Auto s : importDataSetInfo.AutoSet)
				{
					Object[] objs =
						{ s.getAutoId(), s.getMasterId(), s.getAutoName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRelationId(),DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate())};
					connection.execSQL(SqlString.Sql_StandardFormula_InsertAuto, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Auto");

				for (Auto s : importDataSetInfo.AutoSet)
				{
					DictEntity de = Utility.First(list, s.getAutoName(), false);
					if (de == null||(s.getAutoName().equals("...")))
					{  
						try
						{int mid = 0;
							
							if (s.getMasterId()==0)
							{
								dictConvertIds.get(Utility.TableAuto).put(s.getAutoId(), ++maxId);
							} else
							{
								if(s.getAutoName().equals("..."))
								{
									//第一步：替换MasterID
									mid = dictConvertIds.get(Utility.TableAuto).size() > 0 ? dictConvertIds.get(Utility.TableAuto).get(s.getMasterId()) : s.getMasterId();
									//第二步：根据MasterID和AutoName查数据库中是否有数据
//								masteriD,autoname(...)  在数据库中是否存在
									List<Auto> mlist = Utility.GetAutoEntitys(connection);
									Auto auto = Utility.AutoFirst(mlist,mid, s.getAutoName());
									if (auto!=null)
									{
										//有：放进dictConvertIds.get(Utility.TableAuto) (key=原始key,Value=查邮来数据库中的AutoID)
										dictConvertIds.get(Utility.TableAuto).put(s.getAutoId(),auto.getAutoId());
										continue;
									} else
									{
										//无：插入数据加，放进放进dictConvertIds.get(Utility.TableAuto) (key=原始key,Value=查入的最新AutoID)
										dictConvertIds.get(Utility.TableAuto).put(s.getAutoId(), ++maxId);

									}
								}
								else
								{
								dictConvertIds.get(Utility.TableAuto).put(s.getAutoId(), ++maxId);
								 mid=dictConvertIds.get(Utility.TableAuto).size() > 0 ? dictConvertIds.get(Utility.TableAuto).get(s.getMasterId()) : s.getMasterId();
								}
							}
							
							Object[] objs =
							{maxId, mid, s.getAutoName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRelationId(),DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_StandardFormula_InsertAuto, objs);
						} catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else
					{
						dictConvertIds.get(Utility.TableAuto).put(s.getAutoId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getMasterId(), s.getAutoName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRelationId(),DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_CH_UpdateAuto, objs);
						}
					}
				}
			}
		}
		// 导入颜色分组表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorGroupSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "ColorGroup", "ColorGroupId", "ColorGroupName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (ColorGroup s : importDataSetInfo.ColorGroupSet)
				{
					Object[] objs =
					{ s.getColorGroupId(), s.getColorGroupName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorGroup");

				for (ColorGroup s : importDataSetInfo.ColorGroupSet)
				{
					DictEntity de = Utility.First(list, s.getColorGroupName(), false);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableColorGroup).put(s.getColorGroupId(), ++maxId);
						Object[] objs =
						{ maxId, s.getColorGroupName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup, objs);
					} else
					{
						dictConvertIds.get(Utility.TableColorGroup).put(s.getColorGroupId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getColorGroupName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_StandardFormula_UpdateColorGroup, objs);
						}
					}
				}
			}
		}

		// 导入标准色卡系统
		if (!Common.IsNullOrEmpty(importDataSetInfo.StandardColorSystemSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "StandardColorSystem", "StandardColorSystemId", "StandardColorSystemName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
			if (Common.IsNullOrEmpty(list))
			{
				for (StandardColorSystem s : importDataSetInfo.StandardColorSystemSet)
				{
					Object[] objs =
					{ s.getStandardColorSystemId(), s.getStandardColorSystemName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SH_InsertStandardColorSystem, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("StandardColorSystem");

				for (StandardColorSystem s : importDataSetInfo.StandardColorSystemSet)
				{
					DictEntity de = Utility.First(list, s.getStandardColorSystemName(), false);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableStandardColorSystem).put(s.getStandardColorSystemId(), ++maxId);
						Object[] objs =
						{ maxId, s.getStandardColorSystemName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SH_InsertStandardColorSystem, objs);
					} else
					{
						dictConvertIds.get(Utility.TableStandardColorSystem).put(s.getStandardColorSystemId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getStandardColorSystemName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_SCH_UpdateStandardColorSystem, objs);
						}
					}
				}
			}
		}

		// 导入内部色卡表
		if (!Common.IsNullOrEmpty(importDataSetInfo.InnerColorSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "InnerColor", "InnerColorId", "ColorCode");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (InnerColor s : importDataSetInfo.InnerColorSet)
				{
					int uid = dictConvertIds.get(Utility.TableColorType).size() > 0 ? dictConvertIds.get(Utility.TableColorType).get(s.getColorTypeId()) : s.getColorTypeId();
					Object[] objs =
					{ s.getInnerColorId(), uid, s.getColorCode(), s.getColorName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRGB(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
					connection.execSQL(SqlString.Sql_SCH_InsertInnerColor, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("InnerColor");

				for (InnerColor s : importDataSetInfo.InnerColorSet)
				{
					int uid = dictConvertIds.get(Utility.TableColorType).size() > 0 ? dictConvertIds.get(Utility.TableColorType).get(s.getColorTypeId()) : s.getColorTypeId();
					DictEntity de = Utility.First(list, s.getColorCode(), false);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableInnerColor).put(s.getInnerColorId(), ++maxId);
						Object[] objs =
						{ maxId, uid, s.getColorCode(), s.getColorName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRGB(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
						connection.execSQL(SqlString.Sql_SCH_InsertInnerColor, objs);
					} else
					{
						dictConvertIds.get(Utility.TableInnerColor).put(s.getInnerColorId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ uid, s.getColorCode(), s.getColorName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRGB(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(), de.Id };
							connection.execSQL(SqlString.Sql_StandardFormula_UpdateStandardInnerColor, objs);
						}
					}
				}
			}
		}

		// 导入配方表
		if (!Common.IsNullOrEmpty(importDataSetInfo.FormulaSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "Formula", "FormulaId", "FormulaVersion");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
			int sourece;

			if (Common.IsNullOrEmpty(list))
			{
				for (Formula s : importDataSetInfo.FormulaSet)
				{
					int uid = dictConvertIds.get(Utility.TableProduct).size() > 0 ? dictConvertIds.get(Utility.TableProduct).get(s.getProductId()) : s.getProductId();
					Object[] objs =
					{ s.getFormulaId(), uid, s.getFormulaVersion(), DataTypeConvert.dateToString(s.getFormulaVersionDate()), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(),s.getSource() };
					connection.execSQL(SqlString.Sql_CH_InsertFormula, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Formula");

				for (Formula s : importDataSetInfo.FormulaSet)
				{
					int uid = dictConvertIds.get(Utility.TableProduct).size() > 0 ? dictConvertIds.get(Utility.TableProduct).get(s.getProductId()) : s.getProductId();
					if (s.getSource()==-1)
					{
						sourece=-1;
					} else
					{
						sourece = dictConvertIds.get(Utility.TableRelations).size()>0?dictConvertIds.get(Utility.TableRelations).get(s.getSource()):s.getSource();

					}
					dictConvertIds.get(Utility.TableFormula).put(s.getFormulaId(), ++maxId);
					Object[] objs =
					{ maxId, uid, s.getFormulaVersion(), DataTypeConvert.dateToString(s.getFormulaVersionDate()), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(),sourece };
					connection.execSQL(SqlString.Sql_CH_InsertFormula, objs);
				}
			}
		}

		// 导入色卡车型表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorAutoSet))
		{
			List<ColorAuto> list = Utility.GetColorAutoEntitys(connection);
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.PMaxId(list);
			if (Common.IsNullOrEmpty(list))
			{
				for (ColorAuto s : importDataSetInfo.ColorAutoSet)
				{
					int aid = dictConvertIds.get(Utility.TableAuto).size() > 0 ? dictConvertIds.get(Utility.TableAuto).get(s.getAutoId()) : s.getAutoId();
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableProduct).get(s.getInnerColorId()) : s.getInnerColorId();
					Object[] objs =
					{ s.getColorAutoId(), aid, iid, DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorAuto, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorAuto");

				for (ColorAuto s : importDataSetInfo.ColorAutoSet)
				{
					int aid = dictConvertIds.get(Utility.TableAuto).size() > 0 ? dictConvertIds.get(Utility.TableAuto).get(s.getAutoId()) : s.getAutoId();
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableProduct).get(s.getInnerColorId()) : s.getInnerColorId();
					ColorAuto de = Utility.PFirst(list, aid, iid);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableColorAuto).put(s.getColorAutoId(), ++maxId);
						Object[] objs =
						{ maxId, aid, iid, DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorAuto, objs);
					} else
					{
						dictConvertIds.get(Utility.TableColorAuto).put(s.getColorAutoId(), de.getColorAutoId());
						if (isUpdate)
						{
							Object[] objs =
							{ DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_SCH_UpdateColorAuto, objs);
						}
					}
				}
			}
		}

		// 导入标准色卡表
		if (!Common.IsNullOrEmpty(importDataSetInfo.StandardColorSet))
		{
			int max = Utility.GetMaxId(connection, "StandardColor", "StandardColorId");
			boolean isUpdate = formulaImportParam.UpdateTableNames.contains("StandardColor");

			for (StandardColor s : importDataSetInfo.StandardColorSet)
			{
				int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
				int sid=0;
				if (s.getStandardColorSystemId()>0)
				{
					sid = dictConvertIds.get(Utility.TableStandardColorSystem).size() > 0 ? dictConvertIds.get(Utility.TableStandardColorSystem).get(s.getStandardColorSystemId()) : s.getStandardColorSystemId();
				}
				Object[] objs =
				{ iid, s.getStandardColorCode(), sid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), ++max };
				try
				{
					connection.execSQL(SqlString.Sql_StandardFormula_InsertStandardColor, objs);
				} catch (SQLException ex)
				{
					if (isUpdate)
					{
						connection.execSQL(SqlString.Sql_SCH_UpdateStandardColor, objs);
					}
				}
			}
		}

		// 导入颜色地图
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorMapSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "ColorMap", "ColorMap", "Documentation");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
			if (Common.IsNullOrEmpty(list))
			{
				for (ColorMap s : importDataSetInfo.ColorMapSet)
				{
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
					Object[] objs =
					{ s.getColorMap(), iid, s.getPage(), s.getDocumentation() };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorMap, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorMap");

				for (ColorMap s : importDataSetInfo.ColorMapSet)
				{
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
					DictEntity de = Utility.First(list, s.getDocumentation(), true);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableColorMap).put(s.getColorMap(), ++maxId);
						Object[] objs =
						{ maxId, iid, s.getPage(), s.getDocumentation() };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorMap, objs);
					} else
					{
						dictConvertIds.get(Utility.TableColorMap).put(s.getColorMap(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ iid, s.getPage(), s.getDocumentation(), de.Id };
							connection.execSQL(SqlString.Sql_StandardFormula_UpdateColorMap, objs);
						}
					}
				}
			}
		}

		// 导入差异色卡表
		if (!Common.IsNullOrEmpty(importDataSetInfo.DerivateColorSet))
		{
			List<DerivateColor> list = Utility.GetDerivateColorEntitys(connection);
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.DCaxId(list);
			if (Common.IsNullOrEmpty(list))
			{
				for (DerivateColor s : importDataSetInfo.DerivateColorSet)
				{
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
					Object[] objs =
					{ s.getDerivateColorId(), s.getInnerParentColorId(), iid,s.getRelationsId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertDerivateColor, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("DerivateColor");

				for (DerivateColor s : importDataSetInfo.DerivateColorSet)
				{
					
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
					int iip=dictConvertIds.get(Utility.TableInnerColor).size()>0?dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerParentColorId()) : s.getInnerParentColorId();
					int rid=dictConvertIds.get(Utility.TableRelations).size()>0?dictConvertIds.get(Utility.TableRelations).get(s.getRelationsId()) : s.getRelationsId();
					DerivateColor de = Utility.DCFirst(list, s.getDerivateColorId(),iid);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableDerivateColor).put(s.getDerivateColorId(), ++maxId);
						Object[] objs =
						{ maxId, iip, iid,rid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertDerivateColor, objs);
					} else
					{
						dictConvertIds.get(Utility.TableDerivateColor).put(s.getDerivateColorId(), de.getDerivateColorId());
						if (isUpdate)
						{
							Object[] objs =
							{ s.getInnerParentColorId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.getDerivateColorId() };
							connection.execSQL(SqlString.Sql_StandardFormula_UpdateDerivateColor, objs);
						}
					}
				}
			}
		}
		// 色卡配方表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorFormulaSet))
		{
			List<ColorFormula> list = Utility.GetColorFormulaEntitys(connection);
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.CFaxId(list);
			if (Common.IsNullOrEmpty(list))
			{
				for (ColorFormula s : importDataSetInfo.ColorFormulaSet)
				{
					int fid = dictConvertIds.get(Utility.TableFormula).size() > 0 ? dictConvertIds.get(Utility.TableFormula).get(s.getFormulaId()) : s.getFormulaId();
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
					Object[] objs =
					{ s.getColorFormulaId(), iid, fid, s.getLayerNumber(), s.getColorFormulaCode(),s.getLayerDescription(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
					connection.execSQL(SqlString.Sql_CH_InsertColorFormula, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorFormula");

				for (ColorFormula s : importDataSetInfo.ColorFormulaSet)
				{
					int fid = dictConvertIds.get(Utility.TableFormula).size() > 0 ? dictConvertIds.get(Utility.TableFormula).get(s.getFormulaId()) : s.getFormulaId();
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
					ColorFormula de = Utility.CFirst(list, fid, iid);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableColorFormula).put(s.getColorFormulaId(), ++maxId);
						Object[] objs =
						{ maxId, iid, fid, s.getLayerNumber(), s.getColorFormulaCode(),s.getLayerDescription(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
						connection.execSQL(SqlString.Sql_CH_InsertColorFormula, objs);
					} else
					{
						dictConvertIds.get(Utility.TableColorFormula).put(s.getColorFormulaId(), de.getFormulaId());
						if (isUpdate)
						{
							Object[] objs =
							{ fid, iid, s.getLayerNumber(), s.getColorFormulaCode(),s.getLayerDescription(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(), s.getColorFormulaId() };
							connection.execSQL(SqlString.Sql_StandardFormula_UpdateColorFormula, objs);
						}
					}
				}
			}
		}
		// 导入配方信息表
		if (!Common.IsNullOrEmpty(importDataSetInfo.FormulaRemarkSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "FormulaRemark", "FormulaRemarkId", "FormulaRemarks");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
			if (Common.IsNullOrEmpty(list))
			{
				for (FormulaRemark s : importDataSetInfo.FormulaRemarkSet)
				{
					int cfid = dictConvertIds.get(Utility.TableColorFormula).size() > 0 ? dictConvertIds.get(Utility.TableColorFormula).get(s.getColorFormulaId()) : s.getColorFormulaId();
					Object[] objs =
					{ s.getFormulaRemarkId(), s.getFormulaRemarks(), cfid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertFormulaRemark, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("FormulaRemark");

				for (FormulaRemark s : importDataSetInfo.FormulaRemarkSet)
				{
					int cfid = dictConvertIds.get(Utility.TableColorFormula).size() > 0 ? dictConvertIds.get(Utility.TableColorFormula).get(s.getColorFormulaId()) : s.getColorFormulaId();
					DictEntity de = Utility.First(list, s.getFormulaRemarks(), true);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableFormulaRemark).put(s.getFormulaRemarkId(), ++maxId);
						Object[] objs =
						{ maxId, s.getFormulaRemarks(), cfid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertFormulaRemark, objs);
					} else
					{
						dictConvertIds.get(Utility.TableFormulaRemark).put(s.getFormulaRemarkId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getFormulaRemarks(), cfid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_StandardFormula_UpdateFormulaRemark, objs);
						}
					}
				}
			}
		}

		// 导入色母表表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorantsSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "Colorants", "ColorantId", "ColorantCode");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (Colorants s : importDataSetInfo.ColorantsSet)
				{
					int cgid = dictConvertIds.get(Utility.TableColorGroup).size() > 0 ? dictConvertIds.get(Utility.TableColorGroup).get(s.getColorGroupId()) : s.getColorGroupId();
					Object[] objs =
					{ s.getColorantId(), cgid, s.getColorantCode(), s.getColorantName(), s.getColorantFeatures(), s.getColorantDensity(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertColorants, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Colorants");

				for (Colorants s : importDataSetInfo.ColorantsSet)
				{
					int cgid=0;
					if (s.getColorGroupId()>0)
					{
						 cgid = dictConvertIds.get(Utility.TableColorGroup).size() > 0 ? dictConvertIds.get(Utility.TableColorGroup).get(s.getColorGroupId()) : s.getColorGroupId();
					}
					DictEntity de = Utility.First(list, s.getColorantCode(), false);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableColorants).put(s.getColorantId(), ++maxId);
						Object[] objs =
						{ maxId, cgid, s.getColorantCode(), s.getColorantName(), s.getColorantFeatures(), s.getColorantDensity(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertColorants, objs);
					} else
					{
						dictConvertIds.get(Utility.TableColorants).put(s.getColorantId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ cgid, s.getColorantCode(), s.getColorantName(), s.getColorantFeatures(), s.getColorantDensity(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_StandardFormula_UpdateColorants, objs);
						}
					}
				}
			}
		}
		// 导入颜色类别表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorTypeSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "ColorType", "ColorTypeId", "ColorTypeName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (ColorType s : importDataSetInfo.ColorTypeSet)
				{
					Object[] objs =
					{ s.getColorTypeId(), s.getColorTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorType, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("COLORCARD");

				for (ColorType s : importDataSetInfo.ColorTypeSet)
				{
					DictEntity de = Utility.First(list, s.getColorTypeName(), false);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableColorType).put(s.getColorTypeId(), ++maxId);
						Object[] objs =
						{ maxId, s.getColorTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorType, objs);
					} else
					{
						dictConvertIds.get(Utility.TableColorType).put(s.getColorTypeId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getColorTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_SCH_UpdateColorType, objs);
						}
					}
				}
			}
		}
		// 导入色卡组别色卡表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorGroup_ColorSet))
		{
			boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorGroup_Color");

			for (ColorGroup_Color s : importDataSetInfo.ColorGroup_ColorSet)
			{
				int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getColorId()) : s.getColorId();
				int sid = dictConvertIds.get(Utility.TableColorGroup).size() > 0 ? dictConvertIds.get(Utility.TableColorGroup).get(s.getColorGroupId()) : s.getColorGroupId();
				Object[] objs =
				{ sid, iid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getColorGroupColorId() };
				try
				{
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup_Color, objs);
				} catch (SQLException ex)
				{
					if (isUpdate)
					{
						connection.execSQL(SqlString.Sql_SCH_UpdateColorGroup_Color, objs);
					}
				}
			}
		}
		// 导入汽车部件表
		if (!Common.IsNullOrEmpty(importDataSetInfo.AutoPartsSet))
		{
			List<DictEntity> list = Utility.GetDictEntitys(connection, "AutoParts", "AutoPartsId", "AutoPartsName");
			int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

			if (Common.IsNullOrEmpty(list))
			{
				for (AutoParts s : importDataSetInfo.AutoPartsSet)
				{
					Object[] objs =
					{ s.getAutoPartsId(), s.getAutoPartsName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertAutoParts, objs);
				}
			} else
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("AutoParts");

				for (AutoParts s : importDataSetInfo.AutoPartsSet)
				{
					DictEntity de = Utility.First(list, s.getAutoPartsName(), false);
					if (de == null)
					{
						dictConvertIds.get(Utility.TableAutoParts).put(s.getAutoPartsId(), ++maxId);
						Object[] objs =
						{ maxId, s.getAutoPartsName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertAutoParts, objs);
					} else
					{
						dictConvertIds.get(Utility.TableAutoParts).put(s.getAutoPartsId(), de.Id);
						if (isUpdate)
						{
							Object[] objs =
							{ s.getAutoPartsName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
							connection.execSQL(SqlString.Sql_SCH_UpdateAutoParts, objs);
						}
					}
				}
			}
		}
		// 导入色卡车型部件表表
		if (!Common.IsNullOrEmpty(importDataSetInfo.ColorAutoPartsSet))
		{
			boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorAutoParts");

			for (ColorAutoParts s : importDataSetInfo.ColorAutoPartsSet)
			{
				int aid = dictConvertIds.get(Utility.TableAutoParts).size() > 0 ? dictConvertIds.get(Utility.TableAutoParts).get(s.getAutoPartsId()) : s.getAutoPartsId();
				int cid = dictConvertIds.get(Utility.TableColorAuto).size() > 0 ? dictConvertIds.get(Utility.TableColorAuto).get(s.getColorAutoId()) : s.getColorAutoId();
				Object[] objs =
				{ cid, s.getInnerColorId(), aid, DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getColorAutoPartsId(), };
				try
				{
					connection.execSQL(SqlString.Sql_SCH_InsertColorAutoParts, objs);
				} catch (SQLException ex)
				{
					if (isUpdate)
					{
						connection.execSQL(SqlString.Sql_SCH_UpdateColorAutoParts, objs);
					}
				}
			}
		}
		// #endregion

	}

	/***
	 * 读取附加导入标准配方库相关数据集
	 */
	private void ReadAddStandardDataTable() throws Exception
	{
		// 正在导入基础信息事件
		SetImportProgressing(ImportProgressingStatus.ImportBaseData, MessageKeys.StandardFormula_Import_ImportBaseData_FormulaBaseData);

		SaxParseService reader = new SaxParseService();
     
//		TODO
		
		
		ReaderParam relationsTypeParam = new ReaderParam();
		relationsTypeParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public RelationsType parse(Hashtable<String, String> s)
			{

				RelationsType relatonsType = new RelationsType();
				relatonsType.setRelationsTypeId(Integer.parseInt(s.get("RelationsTypeId")));
				relatonsType.setRelationsTypeName(s.get("RelationsTypeName"));
				relatonsType.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				relatonsType.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return relatonsType;
			}
		};
		relationsTypeParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{ 
				List<DictEntity> list = Utility.GetDictEntitys(connection, "RelationsType", "RelationsTypeId", "RelationsTypeName");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						RelationsType s = (RelationsType) o;
						Object[] objs =
						{ s.getRelationsTypeId(), s.getRelationsTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertRelationsType, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("RelationsType");

					for (Object o : datas)
					{
						RelationsType s = (RelationsType) o;
						DictEntity de = Utility.First(list, s.getRelationsTypeName(), true);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableRelationsType).put(s.getRelationsTypeId(), ++maxId);
							Object[] objs =
							{ maxId, s.getRelationsTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_SCH_InsertRelationsType, objs);
						} else
						{
							dictConvertIds.get(Utility.TableRelationsType).put(s.getRelationsTypeId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getRelationsTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_SCH_UpdateRelationsType, objs);
							}
						}
					}
				}
				
			      
			}
		};
		reader.regix("RelationsType", relationsTypeParam);

		ReaderParam relationsParam = new ReaderParam();
		relationsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Relations parse(Hashtable<String, String> s)
			{
				Relations relations = new Relations();
				relations.setRelationsId(Integer.parseInt(s.get("RelationsId")));
				relations.setRelationsTypeId(Integer.parseInt(s.get("RelationsTypeId")));
				relations.setRelationsName(s.get("RelationsName"));
				relations.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				relations.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return relations;
			}
		};
		relationsParam.DataImport = new IDataImport()
		{

			@Override
			public void importData(List<Object> datas)
			{
	        
				List<DictEntity> list = Utility.GetDictEntitys(connection, "Relations", "RelationsId", "RelationsName");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						Relations s = (Relations) o;
						int RelationsTypeId = dictConvertIds.get(Utility.TableRelationsType).size() > 0 ? dictConvertIds.get(Utility.TableRelationsType).get(s.getRelationsTypeId()) : s.getRelationsTypeId();
						Object[] objs =
						{ s.getRelationsId(), s.getRelationsName(), RelationsTypeId, DataTypeConvert.dateToString(s.getCreatedDate(), DataTypeConvert.dateToString(s.getSystemDate())) };
						connection.execSQL(SqlString.Sql_SCH_InsertRelations, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Relations");

					for (Object o : datas)
					{
						Relations s = (Relations) o;
						int RelationsTypeId = dictConvertIds.get(Utility.TableRelationsType).size() > 0 ? dictConvertIds.get(Utility.TableRelationsType).get(s.getRelationsTypeId()) : s.getRelationsTypeId();
						DictEntity de = Utility.First(list, s.getRelationsName(), true);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableRelations).put(s.getRelationsId(), ++maxId);
							Object[] objs =
							{ maxId, s.getRelationsName(), RelationsTypeId, DataTypeConvert.dateToString(s.getCreatedDate(), DataTypeConvert.dateToString(s.getSystemDate())) };
							connection.execSQL(SqlString.Sql_SCH_InsertRelations, objs);
						} else
						{
							dictConvertIds.get(Utility.TableRelations).put(s.getRelationsId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getRelationsName(), RelationsTypeId, DataTypeConvert.dateToString(s.getCreatedDate(), DataTypeConvert.dateToString(s.getSystemDate())), de.Id };
								connection.execSQL(SqlString.Sql_SH_UpdateRelations, objs);
							}
						}
					}
				}
			
		
			}
		};
		reader.regix("Relations", relationsParam);

		ReaderParam brandParam = new ReaderParam();
		brandParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Brand parse(Hashtable<String, String> s)
			{
				Brand brand = new Brand();
				brand.setBrandId(Integer.parseInt(s.get("BrandId")));
				brand.setBrandName(s.get("BrandName"));
				brand.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				brand.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return brand;
			}
		};
		brandParam.DataImport = new IDataImport()
		{

			@Override
			public void importData(List<Object> datas)
			{
	        
				List<DictEntity> list = Utility.GetDictEntitys(connection, "Brand", "BrandId", "BrandName");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						Brand s = (Brand) o;
						Object[] objs =
						{ s.getBrandId(), s.getBrandName(), DataTypeConvert.dateToString(s.getCreateDate(), DataTypeConvert.dateToString(s.getSystemDate())) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertBrand, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Brand");

					for (Object o : datas)
					{
						Brand s = (Brand) o;
						DictEntity de = Utility.First(list, s.getBrandName(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableBrand).put(s.getBrandId(), ++maxId);
							Utility.isExists=true;
							Object[] objs =
							{ maxId, s.getBrandName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_StandardFormula_InsertBrand, objs);
						} else
						{
							Utility.isExists=false;
							dictConvertIds.get(Utility.TableBrand).put(s.getBrandId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getBrandName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_CH_UpdateBrand, objs);
							}
						}
					}
				}
			
		
			}
		};
		reader.regix("Brand", brandParam);

		ReaderParam productParam = new ReaderParam();
		productParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Product parse(Hashtable<String, String> s)
			{
				Product product = new Product();
				product.setProductId(Integer.parseInt(s.get("ProductId")));
				product.setProductCode(s.get("ProductCode"));
				product.setProductName(s.get("ProductName"));
				product.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				product.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return product;
			}
		};
		productParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
		       
				List<DictEntity> list = Utility.GetDictEntitys(connection, "Product", "ProductId", "ProductCode");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						Product s = (Product) o;
						Object[] objs =
						{ s.getProductId(), s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SH_InsertProduct, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Product");

					for (Object o : datas)
					{
						Product s = (Product) o;
						if (Utility.isExists)
						{
							DictEntity de = Utility.First(list, s.getProductName(), false);
							if (de == null)
							{
								dictConvertIds.get(Utility.TableProduct).put(s.getProductId(), ++maxId);
								Log.i("tag1", s.getProductId()+""+maxId);
								Object[] objs =
								{ maxId, s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
								connection.execSQL(SqlString.Sql_SH_InsertProduct, objs);
							} else
							{
								dictConvertIds.get(Utility.TableProduct).put(s.getProductId(), de.Id);
								if (isUpdate)
								{
									Object[] objs = 
									{ s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
									connection.execSQL(SqlString.Sql_SCH_UpdateProduct, objs);
								}
							}
							
						} else
						{
							dictConvertIds.get(Utility.TableProduct).put(s.getProductId(), ++maxId);
							Log.i("tag1",Utility.isExists+""+ s.getProductId()+""+maxId);
							Object[] objs =
							{ maxId, s.getProductCode(), s.getProductName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_SH_InsertProduct, objs);
						}
					}
				}
			
			
			}
		};
		reader.regix("Product", productParam);

		ReaderParam brandProductParam = new ReaderParam();
		brandProductParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public BrandProduct parse(Hashtable<String, String> s)
			{
				BrandProduct brandProduct = new BrandProduct();
				brandProduct.setBrandId(Integer.parseInt(s.get("BrandId")));// BrandId
				brandProduct.setBrandProductId(Integer.parseInt(s.get("BrandProductId")));
				brandProduct.setProductId(Integer.parseInt(s.get("ProductId")));
				return brandProduct;
			}
		};
		brandProductParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				
				int maxId = Utility.GetMaxId(connection, "BrandProduct", "BrandProductId");
				for (Object o : datas)
				{
					BrandProduct s = (BrandProduct) o;
					try
					{
						int sid = dictConvertIds.get(Utility.TableBrand).size() > 0 ? dictConvertIds.get(Utility.TableBrand).get(s.getBrandId()) : s.getBrandId();
						int pid = dictConvertIds.get(Utility.TableProduct).size() > 0 ? dictConvertIds.get(Utility.TableProduct).get(s.getProductId()) : s.getProductId();
						connection.execSQL(SqlString.Sql_StandardFormula_InsertBrandProduct, new Object[]
						{ ++maxId, pid, sid });
					} catch (SQLException ex)
					{
					}
				}
			
			
			}
		};
		reader.regix("BrandProduct", brandProductParam);
//TODO
		ReaderParam autoParam = new ReaderParam();
		autoParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Auto parse(Hashtable<String, String> s)
			{
				Auto auto = new Auto();
				auto.setAutoId(Integer.parseInt(s.get("AutoId")));
				if (s.get("MasterId") != null && s.get("MasterId").length() > 0)
				{

					auto.setMasterId(Integer.parseInt(s.get("MasterId")));
				}
				else{
					auto.setMasterId(0);
				}
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{

					auto.setRelationId(Integer.parseInt(s.get("RelationId")));
				}
				auto.setAutoName(s.get("AutoName"));
				auto.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearFirstUsed")));
				auto.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearLastUsed")));
				auto.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				auto.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				
				return auto;
			}
		};
		autoParam.DataImport = new IDataImport()
		{
			private int mid=0;

			@Override
			public void importData(List<Object> datas)
			{
		       
				List<DictEntity> list = Utility.GetAutoDictEntitys(connection, "Auto", "AutoId","MasterId", "AutoName");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
                 int f=0;
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						Auto s = (Auto) o;
						Object[] objs =
							{ s.getAutoId(), s.getMasterId(), s.getAutoName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRelationId(),DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate())};
						connection.execSQL(SqlString.Sql_StandardFormula_InsertAuto, objs);
					}
				} else
				{
//					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Auto");
            
					for (Object o : datas)
					{    Auto s=(Auto) o;
						Iterator<Object> sListIterator = datas.iterator();
				        while (sListIterator.hasNext()&&f==0) {
				        	Auto str = (Auto) sListIterator.next();
				            if (str.getMasterId()==0) {
				            	List<Auto> mlist = Utility.GetAutoEntitys(connection);
								Auto auto = Utility.AutoFirsts(mlist, str.getAutoName());
								if (auto!=null)
								{ Auto at=Utility.GetAutoEntitys(connection,str.getAutoName());
									//有：放进dictConvertIds.get(Utility.TableAuto) (key=原始key,Value=查邮来数据库中的AutoID)
									dictConvertIds.get(Utility.TableAuto).put(str.getAutoId(),at.getAutoId());
									continue;
								} else
								{
									//无：插入数据加，放进放进dictConvertIds.get(Utility.TableAuto) (key=原始key,Value=查入的最新AutoID)
									dictConvertIds.get(Utility.TableAuto).put(str.getAutoId(), ++maxId);

								}
								Object[] objs =
								{maxId, mid, str.getAutoName(), DataTypeConvert.dateToString(str.getYearFirstUsed()), DataTypeConvert.dateToString(str.getYearLastUsed()), str.getRelationId(),DataTypeConvert.dateToString(str.getCreateDate()), DataTypeConvert.dateToString(str.getSystemDate()) };
								connection.execSQL(SqlString.Sql_StandardFormula_InsertAuto, objs);
				            }
				            
				        }
						if(s.getMasterId()==0||s.getMasterId()!=0)
						{   f=1;
						    if (s.getMasterId()!=0)
						      {
							
						
							//第一步：替换MasterID
							mid = dictConvertIds.get(Utility.TableAuto).size() > 0 ? dictConvertIds.get(Utility.TableAuto).get(s.getMasterId()) : s.getMasterId();
							//第二步：根据MasterID和AutoName查数据库中是否有数据
//						masteriD,autoname(...)  在数据库中是否存在
							List<Auto> mlist = Utility.GetAutoEntitys(connection);
							Auto auto = Utility.AutoFirst(mlist,mid, s.getAutoName());
							if (auto!=null)
							{
								//有：放进dictConvertIds.get(Utility.TableAuto) (key=原始key,Value=查邮来数据库中的AutoID)
								dictConvertIds.get(Utility.TableAuto).put(s.getAutoId(),auto.getAutoId());
								continue;
							} else
							{
								//无：插入数据加，放进放进dictConvertIds.get(Utility.TableAuto) (key=原始key,Value=查入的最新AutoID)
								dictConvertIds.get(Utility.TableAuto).put(s.getAutoId(), ++maxId);

							}
							Object[] objs =
							{maxId, mid, s.getAutoName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRelationId(),DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_StandardFormula_InsertAuto, objs);
						}
						}
					}
				}
			
			
			}
		};
		reader.regix("Auto", autoParam);

		ReaderParam colorGroupParam = new ReaderParam();
		colorGroupParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorGroup parse(Hashtable<String, String> s)
			{
				ColorGroup colorGroup = new ColorGroup();
				if (s.get("ColorGroupId") != null && s.get("ColorGroupId").length() > 0)
				{

					colorGroup.setColorGroupId(Integer.parseInt(s.get("ColorGroupId")));
				}
				colorGroup.setColorGroupName(s.get("ColorGroupName"));
				colorGroup.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorGroup.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorGroup;
			}
		};
		colorGroupParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
		       
				List<DictEntity> list = Utility.GetDictEntitys(connection, "ColorGroup", "ColorGroupId", "ColorGroupName");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						ColorGroup s = (ColorGroup) o;
						Object[] objs =
						{ s.getColorGroupId(), s.getColorGroupName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorGroup");

					for (Object o : datas)
					{
						ColorGroup s = (ColorGroup) o;
						DictEntity de = Utility.First(list, s.getColorGroupName(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableColorGroup).put(s.getColorGroupId(), ++maxId);
							Object[] objs =
							{ maxId, s.getColorGroupName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup, objs);
						} else
						{
							dictConvertIds.get(Utility.TableColorGroup).put(s.getColorGroupId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getColorGroupName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_StandardFormula_UpdateColorGroup, objs);
							}
						}
					}
				}
			
			
			}
		};
		reader.regix("ColorGroup", colorGroupParam);

		ReaderParam standardColorSystemParam = new ReaderParam();
		standardColorSystemParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public StandardColorSystem parse(Hashtable<String, String> s)
			{
				StandardColorSystem standardColorSystem = new StandardColorSystem();
				if (s.get("StandardColorSystemId") != null && s.get("StandardColorSystemId").length() >= 0)
				{
					standardColorSystem.setStandardColorSystemId(Integer.parseInt(s.get("StandardColorSystemId")));
				}
				standardColorSystem.setStandardColorSystemName(s.get("StandardColorSystemName"));
				standardColorSystem.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				standardColorSystem.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return standardColorSystem;
			}
		};
		standardColorSystemParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
	        
				List<DictEntity> list = Utility.GetDictEntitys(connection, "StandardColorSystem", "StandardColorSystemId", "StandardColorSystemName");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);

				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						StandardColorSystem s = (StandardColorSystem) o;
						Object[] objs =
						{ s.getStandardColorSystemId(), s.getStandardColorSystemName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SH_InsertStandardColorSystem, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("StandardColorSystem");

					for (Object o : datas)
					{
						StandardColorSystem s = (StandardColorSystem) o;
						DictEntity de = Utility.First(list, s.getStandardColorSystemName(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableStandardColorSystem).put(s.getStandardColorSystemId(), ++maxId);
							Object[] objs =
							{ maxId, s.getStandardColorSystemName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_SH_InsertStandardColorSystem, objs);
						} else
						{
							dictConvertIds.get(Utility.TableStandardColorSystem).put(s.getStandardColorSystemId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getStandardColorSystemName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_SCH_UpdateStandardColorSystem, objs);
							}
						}
					}
				}
			
			
			}
		};
		reader.regix("StandardColorSystem", standardColorSystemParam);

		ReaderParam colorTypeParam = new ReaderParam();
		colorTypeParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorType parse(Hashtable<String, String> s)
			{
				ColorType colorType = new ColorType();
				colorType.setColorTypeId(Integer.parseInt(s.get("ColorTypeId")));
				colorType.setColorTypeName(s.get("ColorTypeName"));
				colorType.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorType.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorType;
			}
		};
		colorTypeParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
	        
				List<DictEntity> list = Utility.GetDictEntitys(connection, "ColorType", "ColorTypeId", "ColorTypeName");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						ColorType s = (ColorType) o;
						Object[] objs =
						{ s.getColorTypeId(), s.getColorTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorType, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorType");

					for (Object o : datas)
					{
						ColorType s = (ColorType) o;
						DictEntity de = Utility.First(list, s.getColorTypeName(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableColorType).put(s.getColorTypeId(), ++maxId);
							Object[] objs =
							{ maxId, s.getColorTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_StandardFormula_InsertColorType, objs);
						} else
						{
							dictConvertIds.get(Utility.TableColorType).put(s.getColorTypeId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getColorTypeName(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_SCH_UpdateColorType, objs);
							}
						}
					}
				}
			
			}
		};
		reader.regix("ColorType", colorTypeParam);
		
		ReaderParam innerColorParam = new ReaderParam();
		innerColorParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public InnerColor parse(Hashtable<String, String> s)
			{
				InnerColor innerColor = new InnerColor();
				innerColor.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));

				if (s.get("ColorTypeId") != null && s.get("ColorTypeId").length() > 0)
				{
					innerColor.setColorTypeId(Integer.parseInt(s.get("ColorTypeId")));

				}
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{

					innerColor.setRelationId(Integer.parseInt(s.get("RelationId")));
				}

				innerColor.setColorCode(s.get("ColorCode"));
				innerColor.setColorName(s.get("ColorName"));
				innerColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				innerColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				innerColor.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearFirstUsed")));
				innerColor.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearLastUsed")));
				innerColor.setRGB(!Common.IsNullOrEmpty(s.get("RGB")) ? s.get("RGB") : "000000");
				return innerColor;
			}
		};
		innerColorParam.DataImport = new IDataImport()
		{

			@Override
			public void importData(List<Object> datas)
			{
	        
				List<DictEntity> list = Utility.GetDictEntitys(connection, "InnerColor", "InnerColorId", "ColorCode");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						InnerColor s = (InnerColor) o;
						int uid = dictConvertIds.get(Utility.TableColorType).size() > 0 ? dictConvertIds.get(Utility.TableColorType).get(s.getColorTypeId()) : s.getColorTypeId();
						Object[] objs =
						{ s.getInnerColorId(), uid, s.getColorCode(), s.getColorName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRGB(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
						connection.execSQL(SqlString.Sql_SCH_InsertInnerColor, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("InnerColor");

					for (Object o : datas)
					{
						InnerColor s = (InnerColor) o;
						int uid = dictConvertIds.get(Utility.TableColorType).size() > 0 ? dictConvertIds.get(Utility.TableColorType).get(s.getColorTypeId()) : s.getColorTypeId();
						DictEntity de = Utility.First(list, s.getColorCode(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableInnerColor).put(s.getInnerColorId(), ++maxId);
							Object[] objs =
							{ maxId, uid, s.getColorCode(), s.getColorName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRGB(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
							connection.execSQL(SqlString.Sql_SCH_InsertInnerColor, objs);
						} else
						{
							dictConvertIds.get(Utility.TableInnerColor).put(s.getInnerColorId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ uid, s.getColorCode(), s.getColorName(), DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), s.getRGB(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(), de.Id };
								connection.execSQL(SqlString.Sql_StandardFormula_UpdateStandardInnerColor, objs);
							}
						}
					}
				}
			
			
			}
		};
		reader.regix("InnerColor", innerColorParam);

		ReaderParam formulaParam = new ReaderParam();
		formulaParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Formula parse(Hashtable<String, String> s)
			{
				Formula formula = new Formula();
				formula.setFormulaId(Integer.parseInt(s.get("FormulaId")));
				formula.setProductId(Integer.parseInt(s.get("ProductId")));
				formula.setFormulaVersion(s.get("FormulaVersion"));
				if (s.get("Source") != null && s.get("Source").length() > 0)
				{
					formula.setSource(Integer.parseInt(s.get("Source")));
				}
				else
				{
					formula.setSource(Integer.parseInt("-1"));
				}
				formula.setFormulaVersionDate(ConvertStringToDateTimeWithSystem(s.get("FormulaVersionDate")));
				formula.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				formula.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{
					formula.setRelationId(Integer.parseInt(s.get("RelationId")));
				}
				return formula;
			}
		};
		formulaParam.DataImport = new IDataImport()
		{

			@Override
			public void importData(List<Object> datas)
			{
				int sourece;
		       
				List<DictEntity> list = Utility.GetAutoDictEntitys(connection, "Formula", "FormulaId","ProductId","FormulaVersion");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						Formula s = (Formula) o;
						int uid = dictConvertIds.get(Utility.TableProduct).size() > 0 ? dictConvertIds.get(Utility.TableProduct).get(s.getProductId()) : s.getProductId();
						if (s.getSource()==-1)
						{
							sourece=-1;
						} else
						{
							sourece = dictConvertIds.get(Utility.TableRelations).size()>0?dictConvertIds.get(Utility.TableRelations).get(s.getSource()):s.getSource();

						}
						Object[] objs =
						{ s.getFormulaId(), uid, s.getFormulaVersion(), DataTypeConvert.dateToString(s.getFormulaVersionDate()), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(),sourece };
						connection.execSQL(SqlString.Sql_CH_InsertFormula, objs);
					}
				} else
				{
//					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Formula");

					for (Object o : datas)
					{
						Formula s = (Formula) o;
//						List<DictEntity> lists = Utility.GetAutoDictEntitys(connection, "Formula", "ProductId", "FormulaVersion");
						int uid = dictConvertIds.get(Utility.TableProduct).size() > 0 ? dictConvertIds.get(Utility.TableProduct).get(s.getProductId()) : s.getProductId();
						DictEntity entiny=Utility.DCFirst(list, s.getFormulaVersion(), uid, false);
						if (entiny==null)
						{
							dictConvertIds.get(Utility.TableFormula).put(s.getFormulaId(), ++maxId);
							if (s.getSource()==-1)
							{
								sourece=-1;
							} else
							{
								sourece = dictConvertIds.get(Utility.TableRelations).size()>0?dictConvertIds.get(Utility.TableRelations).get(s.getSource()):s.getSource();
								
							}
							dictConvertIds.get(Utility.TableFormula).put(s.getFormulaId(), ++maxId);
							Object[] objs =
							{ maxId, uid, s.getFormulaVersion(), DataTypeConvert.dateToString(s.getFormulaVersionDate()), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(),sourece };
							connection.execSQL(SqlString.Sql_CH_InsertFormula, objs);
							
						} else
						{
							dictConvertIds.get(Utility.TableFormula).put(s.getFormulaId(), entiny.Id);
						}
					}
				}
			
			
			}
		};
		reader.regix("Formula", formulaParam);

		ReaderParam colorAutoParam = new ReaderParam();
		colorAutoParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorAuto parse(Hashtable<String, String> s)
			{
				ColorAuto colorAuto = new ColorAuto();
				colorAuto.setAutoId(Integer.parseInt(s.get("AutoId")));

				colorAuto.setColorAutoId(Integer.parseInt(s.get("ColorAutoId")));
				colorAuto.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{

					colorAuto.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				colorAuto.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				colorAuto.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearFirstUsed")));
				colorAuto.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("YearLastUsed")));
				return colorAuto;
			}
		};
		colorAutoParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
			int maxId = Utility.GetMaxId(connection, "ColorAuto", "ColorAutoId");
			for (Object o : datas)
			{
				ColorAuto s = (ColorAuto) o;
				try
				{
					int aid = dictConvertIds.get(Utility.TableAuto).size() > 0 ? dictConvertIds.get(Utility.TableAuto).get(s.getAutoId()) : s.getAutoId();
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
					Object[] objs =
					{ ++maxId, aid, iid, DataTypeConvert.dateToString(s.getYearFirstUsed()), DataTypeConvert.dateToString(s.getYearLastUsed()), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_StandardFormula_InsertColorAuto, objs);

				} catch (SQLException ex)
				{
				}
			}
			
			}
		};
		reader.regix("ColorAuto", colorAutoParam);

		ReaderParam standardColorParam = new ReaderParam();
		standardColorParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public StandardColor parse(Hashtable<String, String> s)
			{
				StandardColor standardColor = new StandardColor();
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					standardColor.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				if (s.get("StandardColorId") != null && s.get("StandardColorId").length() > 0)
				{
					standardColor.setStandardColorId(Integer.parseInt(s.get("StandardColorId")));
				}
				if (s.get("StandardColorSystemId") != null && s.get("StandardColorSystemId").length() > 0)
				{
					standardColor.setStandardColorSystemId(Integer.parseInt(s.get("StandardColorSystemId")));
				}

				standardColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				standardColor.setStandardColorCode(s.get("StandardColorCode"));
				standardColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return standardColor;
			}
		};
		// TODO
		standardColorParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{	

				int max = Utility.GetMaxId(connection, "StandardColor", "StandardColorId");
				List<DictEntity> list = Utility.GetDictEntitys(connection, "StandardColor", "StandardColorId", "StandardColorCode");
			
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						StandardColor s = (StandardColor) o;
						int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
						int sid=0;
						if (s.getStandardColorSystemId()>0)
						{
							sid = dictConvertIds.get(Utility.TableStandardColorSystem).size() > 0 ? dictConvertIds.get(Utility.TableStandardColorSystem).get(s.getStandardColorSystemId()) : s.getStandardColorSystemId();
						}								
						Object[] objs =
						{ iid, s.getStandardColorCode(), sid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), ++max };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertStandardColor, objs);
					}
				} else
				{
//					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("StandardColor");

					for (Object o : datas)
					{
						StandardColor s = (StandardColor) o;
						int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
						int sid=0;
						if (s.getStandardColorSystemId()>0)
						{
							sid = dictConvertIds.get(Utility.TableStandardColorSystem).size() > 0 ? dictConvertIds.get(Utility.TableStandardColorSystem).get(s.getStandardColorSystemId()) : s.getStandardColorSystemId();
						}						
//						DictEntity de = Utility.First(list, s.getStandardColorCode(), false);
						Object[] objs =
						{ iid, s.getStandardColorCode(), sid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), ++max };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertStandardColor, objs);
//						if (de == null)
//						{  
//						} else
//						{
//							if (isUpdate)
//							{
//								Object[] objs =
//								{ iid, s.getStandardColorCode(), sid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), ++max };
//
//								connection.execSQL(SqlString.Sql_SCH_UpdateStandardColor, objs);
//							}
//						}
					}
				}
			

			}
		};
		reader.regix("StandardColor", standardColorParam);

		ReaderParam colorMapParam = new ReaderParam();
		colorMapParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorMap parse(Hashtable<String, String> s)
			{
				ColorMap colorMap = new ColorMap();
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					colorMap.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				colorMap.setDocumentation(s.get("Documentation"));
				if (s.get("ColorMapId") != null && s.get("ColorMapId").length() > 0)
				{

					colorMap.setColorMap(Integer.parseInt(s.get("ColorMapId")));
				}
				colorMap.setPage(s.get("Page"));
				return colorMap;
			}
		};
		colorMapParam.DataImport = new IDataImport()
		{

			@Override
			public void importData(List<Object> datas)
			{
				int maxId = Utility.GetMaxId(connection, "ColorMap", "ColorMap");
				for (Object o : datas)
				{
					ColorMap s = (ColorMap) o;
					try
					{
						int iid =  s.getInnerColorId();
						Object[] objs =
						{ ++maxId, iid, s.getPage(), s.getDocumentation() };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorMap, objs);
						
					} catch (Exception e)
					{
						// TODO: handle exception
					}
				}

			}
		};
		reader.regix("ColorMap", colorMapParam);

		ReaderParam derivateColorParam = new ReaderParam();
		derivateColorParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public DerivateColor parse(Hashtable<String, String> s)
			{
				DerivateColor derivateColor = new DerivateColor();
				derivateColor.setDerivateColorId(Integer.parseInt(s.get("DerivateColorId")));
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					derivateColor.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				if (s.get("InnerParentColorId") != null && s.get("InnerParentColorId").length() > 0)
				{
					derivateColor.setInnerParentColorId(Integer.parseInt(s.get("InnerParentColorId")));
				}
				derivateColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				derivateColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{

					derivateColor.setRelationsId(Integer.parseInt(s.get("RelationId")));
				}
				return derivateColor;
			}
		};
		derivateColorParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
		    
				List<DerivateColor> list = Utility.GetDerivateColorEntitys(connection);
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.DCaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						DerivateColor s = (DerivateColor) o;
						int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
						int iip=dictConvertIds.get(Utility.TableInnerColor).size()>0?dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerParentColorId()) : s.getInnerParentColorId();
						int rid=dictConvertIds.get(Utility.TableRelations).size()>0?dictConvertIds.get(Utility.TableRelations).get(s.getRelationsId()) : s.getRelationsId();
						Object[] objs =
						{ s.getDerivateColorId(), iip, iid,rid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertDerivateColor, objs);
					}
				} else
				{
//					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("DerivateColor");

					int count=1;
					for (Object o : datas)
					{
						DerivateColor s = (DerivateColor) o;
						int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
						int iip=dictConvertIds.get(Utility.TableInnerColor).size()>0?dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerParentColorId()) : s.getInnerParentColorId();
						int rid=dictConvertIds.get(Utility.TableRelations).size()>0?dictConvertIds.get(Utility.TableRelations).get(s.getRelationsId()) : s.getRelationsId();
//						DerivateColor de = Utility.DCFirst(list, s.getDerivateColorId(),iid);
						dictConvertIds.get(Utility.TableDerivateColor).put(s.getDerivateColorId(), ++maxId);
						Object[] objs =
						{ maxId, iip, iid,rid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertDerivateColor, objs);
//						if (de == null)
//						{
//						} else
//						{
//							dictConvertIds.get(Utility.TableDerivateColor).put(s.getDerivateColorId(), de.getDerivateColorId());
//							if (isUpdate)
//							{
//								Object[] objs =
//								{ s.getInnerParentColorId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.getDerivateColorId() };
//								connection.execSQL(SqlString.Sql_StandardFormula_UpdateDerivateColor, objs);
//							}
//						}
					}
				}
			

			}
		};
		reader.regix("DerivateColor", derivateColorParam);

		ReaderParam colorFormulaParam = new ReaderParam();
		colorFormulaParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorFormula parse(Hashtable<String, String> s)
			{
				ColorFormula colorFormula = new ColorFormula();
				colorFormula.setColorFormulaId(Integer.parseInt(s.get("ColorFormulaId")));
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					colorFormula.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				if (s.get("FormulaId") != null && s.get("FormulaId").length() > 0)
				{
					colorFormula.setFormulaId(Integer.parseInt(s.get("FormulaId")));
				}
				if (s.get("LayerNumber") != null && s.get("LayerNumber").length() > 0)
				{
					colorFormula.setLayerNumber(Integer.parseInt(s.get("LayerNumber")));
				}
				colorFormula.setColorFormulaCode(s.get("ColorFormulaCode"));
				if (s.get("LayerDescription") != null && s.get("LayerDescription").length() > 0)
				{
					colorFormula.setLayerDescription(s.get("LayerDescription"));//LayerDescription
				}else{
					colorFormula.setLayerDescription(s.get("LayerNumber"));//LayerDescription
				}
			
				colorFormula.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorFormula.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				if (s.get("RelationId") != null && s.get("RelationId").length() > 0)
				{

					colorFormula.setRelationId(Integer.parseInt(s.get("RelationId")));
				}
				return colorFormula;
			}
		};
		colorFormulaParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				int max = Utility.GetMaxId(connection, "ColorFormula", "ColorFormulaId");
//				List<ColorFormula> list = Utility.GetColorFormulaEntitys(connection);
				List<DictEntity> list =  Utility.GetColorFormulaDictEntitys(connection, "ColorFormula", "InnerColorId", "FormulaId","LayerNumber");
//				int maxId = SanTintCommon.IsNullOrEmpty(list) ? 0 : Utility.CFaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						ColorFormula s = (ColorFormula) o;
						int fid = dictConvertIds.get(Utility.TableFormula).size() > 0 ? dictConvertIds.get(Utility.TableFormula).get(s.getFormulaId()) : s.getFormulaId();
						int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
						Object[] objs =
						{ s.getColorFormulaId(), iid, fid, s.getLayerNumber(), s.getColorFormulaCode(),s.getLayerDescription(),DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
						connection.execSQL(SqlString.Sql_CH_InsertColorFormula, objs);
					}
				} else
				{
//					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorFormula");

					for (Object o : datas)
					{
//						List<DictEntity> lists =  Utility.GetColorFormulaDictEntitys(connection, "ColorFormula", "InnerColorId", "FormulaId","LayerNumber");
						ColorFormula s = (ColorFormula) o;
						int fid = dictConvertIds.get(Utility.TableFormula).size() > 0 ? dictConvertIds.get(Utility.TableFormula).get(s.getFormulaId()) : s.getFormulaId();
						int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getInnerColorId()) : s.getInnerColorId();
						DictEntity de = Utility.DCColorFormulaFirst(list,s.getLayerNumber(),iid,fid);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableColorFormula).put(s.getColorFormulaId(), ++max);
						Object[] objs =
						{max, iid, fid, s.getLayerNumber(), s.getColorFormulaCode(),s.getLayerDescription(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId() };
						connection.execSQL(SqlString.Sql_CH_InsertColorFormula, objs);
						} else
						{
							dictConvertIds.get(Utility.TableColorFormula).put(s.getColorFormulaId(), de.Id);
//							if (isUpdate)
//							{
//								Object[] objs =
//								{ fid, iid, s.getLayerNumber(), s.getColorFormulaCode(),s.getLayerDescription(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), s.getRelationId(), s.getColorFormulaId() };
//								connection.execSQL(SqlString.Sql_StandardFormula_UpdateColorFormula, objs);
//							}
						}
					}
				}
			

			}
		};
		reader.regix("ColorFormula", colorFormulaParam);

		ReaderParam formulaRemarkParam = new ReaderParam();
		formulaRemarkParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public FormulaRemark parse(Hashtable<String, String> s)
			{
				FormulaRemark formulaRemark = new FormulaRemark();
				formulaRemark.setFormulaRemarkId(Integer.parseInt(s.get("FormulaRemarkId")));
				formulaRemark.setFormulaRemarks(s.get("FormulaRemarks"));
				if (s.get("ColorFormulaId") != null && s.get("ColorFormulaId").length() > 0)
				{
					formulaRemark.setColorFormulaId(Integer.parseInt(s.get("ColorFormulaId")));
				}
				formulaRemark.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				formulaRemark.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return formulaRemark;
			}
		};
		formulaRemarkParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
	        
				List<DictEntity> list = Utility.GetDictEntitys(connection, "FormulaRemark", "FormulaRemarkId", "FormulaRemarks");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						FormulaRemark s = (FormulaRemark) o;
						int cfid = dictConvertIds.get(Utility.TableColorFormula).size() > 0 ? dictConvertIds.get(Utility.TableColorFormula).get(s.getColorFormulaId()) : s.getColorFormulaId();
						Object[] objs =
						{ s.getFormulaRemarkId(), s.getFormulaRemarks(), cfid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertFormulaRemark, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("FormulaRemark");

					for (Object o : datas)
					{
						FormulaRemark s = (FormulaRemark) o;
						int cfid = dictConvertIds.get(Utility.TableColorFormula).size() > 0 ? dictConvertIds.get(Utility.TableColorFormula).get(s.getColorFormulaId()) : s.getColorFormulaId();
						DictEntity de = Utility.First(list, s.getFormulaRemarks(), true);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableFormulaRemark).put(s.getFormulaRemarkId(), ++maxId);
							Object[] objs =
							{ maxId, s.getFormulaRemarks(), cfid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_SCH_InsertFormulaRemark, objs);
						} else
						{
							dictConvertIds.get(Utility.TableFormulaRemark).put(s.getFormulaRemarkId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getFormulaRemarks(), cfid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_StandardFormula_UpdateFormulaRemark, objs);
							}
						}
					}
				}
			

			}
		};
		reader.regix("FormulaRemark", formulaRemarkParam);

		ReaderParam colorantsParam = new ReaderParam();
		colorantsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public Colorants parse(Hashtable<String, String> s)
			{
				Colorants colorants = new Colorants();
				colorants.setColorantId(Integer.parseInt(s.get("ColorantId")));
				colorants.setColorantCode(EncryptDecryptHelper.DESEncrypt(s.get("ColorantCode"), "@SANTINT!"));
				colorants.setColorantFeatures(s.get("ColorantFeatures"));
				colorants.setColorantName(EncryptDecryptHelper.DESEncrypt(s.get("ColorantName"), "@SANTINT!"));
				if (s.get("ColorGroupId") != null && s.get("ColorGroupId").length() > 0)
				{
					colorants.setColorGroupId(Integer.parseInt(s.get("ColorGroupId")));
				}
				colorants.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorants.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				if (s.get("ColorantDensity") != null && s.get("ColorantDensity").length() > 0)
				{

					colorants.setColorantDensity(LanguageLocal.ConvertStringToDoubleWithEn(s.get("ColorantDensity")));
				}

				return colorants;
			}
		};
		colorantsParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
	        
				List<DictEntity> list = Utility.GetDictEntitys(connection, "Colorants", "ColorantId", "ColorantCode");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						Colorants s = (Colorants) o;
						int cgid = dictConvertIds.get(Utility.TableColorGroup).size() > 0 ? dictConvertIds.get(Utility.TableColorGroup).get(s.getColorGroupId()) : s.getColorGroupId();
						Object[] objs =
						{ s.getColorantId(), cgid, s.getColorantCode(), s.getColorantName(), s.getColorantFeatures(), s.getColorantDensity(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_SCH_InsertColorants, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("Colorants");

					for (Object o : datas)
					{
						Colorants s = (Colorants) o;
						int cgid=0;
						if (s.getColorGroupId()>0)
						{
							 cgid = dictConvertIds.get(Utility.TableColorGroup).size() > 0 ? dictConvertIds.get(Utility.TableColorGroup).get(s.getColorGroupId()) : s.getColorGroupId();
						}
						DictEntity de = Utility.First(list, s.getColorantCode(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableColorants).put(s.getColorantId(), ++maxId);
							Object[] objs =
							{ maxId, cgid, s.getColorantCode(), s.getColorantName(), s.getColorantFeatures(), s.getColorantDensity(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_SCH_InsertColorants, objs);
						} else
						{
							dictConvertIds.get(Utility.TableColorants).put(s.getColorantId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ cgid, s.getColorantCode(), s.getColorantName(), s.getColorantFeatures(), s.getColorantDensity(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_StandardFormula_UpdateColorants, objs);
							}
						}
					}
				}
			}
		};
		reader.regix("Colorants", colorantsParam);
		
		ReaderParam colorantPriceParam = new ReaderParam();
		colorantPriceParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorantPrice parse(Hashtable<String, String> s)
			{
				ColorantPrice colorantPrice = new ColorantPrice();
				if (s.get("ColorantPriceId")!=null&&s.get("ColorantPriceId").length()>0)
				{
					colorantPrice.setColorantPriceId(Integer.parseInt(s.get("ColorantPriceId")));
					
				}else{
					colorantPrice.setColorantPriceId(Integer.parseInt(s.get("ColorantId")));
				}
				if (s.get("ColorantId")!=null&&s.get("ColorantId").length()>0)
				{
					colorantPrice.setColorantId(Integer.parseInt(s.get("ColorantId")));
					
				}
				if (s.get("ColorGroupId")!=null&&s.get("ColorGroupId").length()>0)
				{
					
					colorantPrice.setColorGroupId(Integer.parseInt(s.get("ColorGroupId")));
				}
				colorantPrice.setColorantCode(s.get("ColorantCode"));
				colorantPrice.setColorantName(s.get("ColorantName"));
				colorantPrice.setColorantFeatures(s.get("ColorantFeatures"));
				if (s.get("ColorantDensity")!=null&&s.get("ColorantDensity").length()>0)
				{
					colorantPrice.setColorantDensity(LanguageLocal.ConvertStringToDoubleWithEn(s.get("ColorantDensity")));
				}
				if (s.get("ColorantPrices")!=null&&s.get("ColorantPrices").length()>0)
				{
					colorantPrice.setColorantPrice(LanguageLocal.ConvertStringToDoubleWithEn(s.get("ColorantPrices")));
				}
				else
				{
					colorantPrice.setColorantPrice(LanguageLocal.ConvertStringToDoubleWithEn("0"));
				}
				if (s.get("ColorPriceRate")!=null&&s.get("ColorPriceRate").length()>0)
				{
					colorantPrice.setColorantPriceRate(LanguageLocal.ConvertStringToDoubleWithEn(s.get("ColorPriceRate")));
				}
				else
				{
					colorantPrice.setColorantPriceRate(LanguageLocal.ConvertStringToDoubleWithEn("1"));
				}
				if (s.get("CanSize")!=null&&s.get("CanSize").length()>0)
				{
					colorantPrice.setCanSize(LanguageLocal.ConvertStringToDoubleWithEn(s.get("CanSize")));
				}
				else
				{
					colorantPrice.setCanSize(LanguageLocal.ConvertStringToDoubleWithEn("1"));
				}
				if (s.get("UnitName")!=null&&s.get("UnitName").length()>0)
				{
					colorantPrice.setUnitId(s.get("UnitName"));
				}
				else
				{
					colorantPrice.setUnitId("L");
				}
				
				if (s.get("CreateDate")!=null&&s.get("CreateDate").length()>0)
				{
					colorantPrice.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				}
				colorantPrice.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorantPrice;
			}
		};
		colorantPriceParam.DataImport = new IDataImport()
		{

			@Override
			public void importData(List<Object> datas)//ColorantPrice
			{
				List<DictEntity> list = Utility.GetDictEntitys(unitConnection, "ColorantPrice", "ColorantPriceId", "ColorantCode");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						ColorantPrice s = (ColorantPrice) o;
						Object[] objs =
						{s.getColorantPriceId(),s.getColorantId(),s.getColorGroupId(),s.getColorantCode(),s.getColorantName(), s.getColorantFeatures(),s.getColorantDensity(),s.getColorantPrice(),s.getColorantPriceRate(),s.getCanSize(),s.getUnitId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						unitConnection.execSQL(SqlString.Sql_SCH_InsertColorantPrice, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("COLORANTPRICE");

					for (Object o : datas)
					{
						ColorantPrice s = (ColorantPrice) o;
						List<DictEntity> lists = Utility.GetDictEntitys(unitConnection, "ColorantPrice", "ColorantPriceId", "ColorantCode");
						DictEntity de = Utility.First(lists, s.getColorantCode(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableColorantPrice).put(s.getColorantPriceId(), ++maxId);
							Object[] objs =
							{ maxId,s.getColorantId(),s.getColorGroupId(),s.getColorantCode(),s.getColorantName(), s.getColorantFeatures(),s.getColorantDensity(),s.getColorantPrice(),s.getColorantPriceRate(),s.getCanSize(),s.getUnitId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							unitConnection.execSQL(SqlString.Sql_SCH_InsertColorantPrice, objs);
							} 
						else
						{
							dictConvertIds.get(Utility.TableColorantPrice).put(s.getColorantPriceId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getColorantId(),s.getColorGroupId(),s.getColorantCode(),s.getColorantName(), s.getColorantFeatures(),s.getColorantDensity(),s.getColorantPrice(),s.getColorantPriceRate(),s.getCanSize(),s.getUnitId(), DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) ,s.getColorantPriceId()};
								unitConnection.execSQL(SqlString.Sql_SCH_UpdateColorantPrice, objs);
							}
						}
					}
				}
			}
		};
		reader.regix("ColorantPrice", colorantPriceParam);
		
		ReaderParam colorFormulaColorantsParam = new ReaderParam();
		colorFormulaColorantsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorFormulaColorants parse(Hashtable<String, String> s)
			{
				ColorFormulaColorants colorFormulaColorants = new ColorFormulaColorants();
				colorFormulaColorants.setColorFormulaColorantsId(Integer.parseInt(s.get("ColorFormulaColorantsId")));
				if (s.get("ColorantSequence") != null && s.get("ColorantSequence").length() > 0)
				{
					colorFormulaColorants.setColorantSequence(Integer.parseInt(s.get("ColorantSequence")));
				}
				colorFormulaColorants.setWeightPercent(EncryptDecryptHelper.DESEncrypt(s.get("WeightPercent"), "@SANTINT!"));
				 colorFormulaColorants.setColorantsId(Integer.parseInt(s.get("ColorantsId")));
				colorFormulaColorants.setColorFormulaId(Integer.parseInt(s.get("ColorFormulaId")));
				colorFormulaColorants.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorFormulaColorants.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorFormulaColorants;
			}
		};
		colorFormulaColorantsParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
			int maxId = Utility.GetMaxId(connection, "ColorFormulaColorants", "ColorFormulaColorantsId");
			for (Object o : datas)
			{
				ColorFormulaColorants s = (ColorFormulaColorants) o;
				try
				{
					int cfid = dictConvertIds.get(Utility.TableColorFormula).size() > 0 ? dictConvertIds.get(Utility.TableColorFormula).get(s.getColorFormulaId()) : s.getColorFormulaId();
					int cid = dictConvertIds.get(Utility.TableColorants).size() > 0 ? dictConvertIds.get(Utility.TableColorants).get(s.getColorantsId()) : s.getColorantsId();
					Object[] objs = { ++maxId, cfid, cid,s.getColorantSequence(),s.getWeightPercent(),DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
					connection.execSQL(SqlString.Sql_SCH_InsertColorFormulaColorants, objs);
					
				} catch (SQLException ex)
				{
				}
			}

			}
		};
		reader.regix("ColorFormulaColorants", colorFormulaColorantsParam);
		
		

		ReaderParam colorGroup_ColorParam = new ReaderParam();
		colorGroup_ColorParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorGroup_Color parse(Hashtable<String, String> s)
			{
				ColorGroup_Color colorGroup_Color = new ColorGroup_Color();
				if (s.get("ColorGroupColorId") != null && s.get("ColorGroupColorId").length() > 0)
				{
					colorGroup_Color.setColorGroupColorId(Integer.parseInt(s.get("ColorGroupColorId")));
				}
				if (s.get("ColorGroupId") != null && s.get("ColorGroupId").length() > 0)
				{
					colorGroup_Color.setColorGroupId(Integer.parseInt(s.get("ColorGroupId")));
				}
				if (s.get("ColorId") != null && s.get("ColorId").length() > 0)
				{
					colorGroup_Color.setColorId(Integer.parseInt(s.get("ColorId")));
				}
				colorGroup_Color.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorGroup_Color.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorGroup_Color;
			}
		};
		colorGroup_ColorParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{

				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorGroup_Color");

				int maxId = Utility.GetMaxId(connection, "ColorGroup_Color", "ColorGroupColorId");
				for (Object o : datas)
				{
					ColorGroup_Color s = (ColorGroup_Color) o;
					int iid = dictConvertIds.get(Utility.TableInnerColor).size() > 0 ? dictConvertIds.get(Utility.TableInnerColor).get(s.getColorId()) : s.getColorId();
					int sid = dictConvertIds.get(Utility.TableColorGroup).size() > 0 ? dictConvertIds.get(Utility.TableColorGroup).get(s.getColorGroupId()) : s.getColorGroupId();
					Object[] objs =
					{ sid, iid, DataTypeConvert.dateToString(s.getCreatedDate()), DataTypeConvert.dateToString(s.getSystemDate()), ++maxId };
					try
					{
						connection.execSQL(SqlString.Sql_StandardFormula_InsertColorGroup_Color, objs);
					} catch (SQLException ex)
					{
						if (isUpdate)
						{
							connection.execSQL(SqlString.Sql_SCH_UpdateColorGroup_Color, objs);
						}
					}
				}
			

			}
		};
		reader.regix("ColorGroup_Color", colorGroup_ColorParam);

		ReaderParam autoPartsParam = new ReaderParam();
		autoPartsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public AutoParts parse(Hashtable<String, String> s)
			{
				AutoParts autoParts = new AutoParts();
				if (s.get("AutoPartsId") != null && s.get("AutoPartsId").length() > 0)
				{
					autoParts.setAutoPartsId(Integer.parseInt(s.get("AutoPartsId")));
				}
				autoParts.setAutoPartsName(s.get("AutoPartsName"));
				autoParts.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				autoParts.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return autoParts;
			}
		};
		autoPartsParam.DataImport = new IDataImport()
		{

			@Override
			public void importData(List<Object> datas)
			{
				List<DictEntity> list = Utility.GetDictEntitys(connection, "AutoParts", "AutoPartsId", "AutoPartsName");
				int maxId = Common.IsNullOrEmpty(list) ? 0 : Utility.MaxId(list);
				if (Common.IsNullOrEmpty(list))
				{
					for (Object o : datas)
					{
						AutoParts s = (AutoParts) o;
						Object[] objs =
						{ s.getAutoPartsId(), s.getAutoPartsName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
						connection.execSQL(SqlString.Sql_StandardFormula_InsertAutoParts, objs);
					}
				} else
				{
					boolean isUpdate = formulaImportParam.UpdateTableNames.contains("AutoParts");

					for (Object o : datas)
					{
						AutoParts s = (AutoParts) o;
						DictEntity de = Utility.First(list, s.getAutoPartsName(), false);
						if (de == null)
						{
							dictConvertIds.get(Utility.TableAutoParts).put(s.getAutoPartsId(), ++maxId);
							Object[] objs =
							{ maxId, s.getAutoPartsName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()) };
							connection.execSQL(SqlString.Sql_StandardFormula_InsertAutoParts, objs);
						} else
						{
							dictConvertIds.get(Utility.TableAutoParts).put(s.getAutoPartsId(), de.Id);
							if (isUpdate)
							{
								Object[] objs =
								{ s.getAutoPartsName(), DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), de.Id };
								connection.execSQL(SqlString.Sql_SCH_UpdateAutoParts, objs);
							}
						}
					}
				}
			

			}
			
		};
		reader.regix("AutoParts", autoPartsParam);

		ReaderParam colorAutoPartsParam = new ReaderParam();
		colorAutoPartsParam.ObjectTranslate = new IObjectTranslate()
		{
			@Override
			public ColorAutoParts parse(Hashtable<String, String> s)
			{
				ColorAutoParts colorAutoParts = new ColorAutoParts();
				if (s.get("AutoPartsId") != null && s.get("AutoPartsId").length() > 0)
				{
					colorAutoParts.setAutoPartsId(Integer.parseInt(s.get("AutoPartsId")));
				}
				if (s.get("ColorAutoId") != null && s.get("ColorAutoId").length() > 0)
				{
					colorAutoParts.setColorAutoId(Integer.parseInt(s.get("ColorAutoId")));
				}
				if (s.get("ColorAutoPartsId") != null && s.get("ColorAutoPartsId").length() > 0)
				{
					colorAutoParts.setColorAutoPartsId(Integer.parseInt(s.get("ColorAutoPartsId")));
				}
				if (s.get("InnerColorId") != null && s.get("InnerColorId").length() > 0)
				{
					colorAutoParts.setInnerColorId(Integer.parseInt(s.get("InnerColorId")));
				}
				colorAutoParts.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.get("CreateDate")));
				colorAutoParts.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
				return colorAutoParts;
			}
		};
		colorAutoPartsParam.DataImport = new IDataImport()
		{
			@Override
			public void importData(List<Object> datas)
			{
				boolean isUpdate = formulaImportParam.UpdateTableNames.contains("ColorAutoParts");

				int maxId = Utility.GetMaxId(connection, "ColorAutoParts", "ColorAutoPartsId");
				for (Object o : datas)
				{
					ColorAutoParts s = (ColorAutoParts) o;
					int aid = dictConvertIds.get(Utility.TableAutoParts).size() > 0 ? dictConvertIds.get(Utility.TableAutoParts).get(s.getAutoPartsId()) : s.getAutoPartsId();
					int cid = dictConvertIds.get(Utility.TableColorAuto).size() > 0 ? dictConvertIds.get(Utility.TableColorAuto).get(s.getColorAutoId()) : s.getColorAutoId();
					Object[] objs =
					{ cid, s.getInnerColorId(), aid, DataTypeConvert.dateToString(s.getCreateDate()), DataTypeConvert.dateToString(s.getSystemDate()), maxId };
					try
					{
						connection.execSQL(SqlString.Sql_SCH_InsertColorAutoParts, objs);
					} catch (SQLException ex)
					{
						if (isUpdate)
						{
							connection.execSQL(SqlString.Sql_SCH_UpdateColorAutoParts, objs);
						}
					}
				}
			

			}
		};
		reader.regix("ColorAutoParts", colorAutoPartsParam);

		// 导入配方体系
		
		Object[] objs =
		{formulaImportParam.CurrentFormulaSystem.getFormulaSystemId(), formulaImportParam.CurrentFormulaSystem.getFormulaSystemName(), formulaImportParam.CurrentFormulaSystem.getFormulaSystemVersion(),
				DataTypeConvert.dateToString(formulaImportParam.CurrentFormulaSystem.getCreateTime()) };
		connection.execSQL(MessageFormat.format(SqlString.Sql_DeleteTable, "FORMULASYSTEM"));
		connection.execSQL(SqlString.Sql_StandardFormula_InsertFormulaSystem, objs);//FORMULASYSTEM
	
       


		reader.start(formulaImportParam.importFile, false);
	}


	/***
	 * 检测标准配方文件的合法性
	 */
	private int DetectStandardFormulaFile()
	{
		// 内部方法
		SantintFunc<Integer> func = new SantintFunc<Integer>()
		{
			@Override
			public Integer apply()
			{
				Integer result = 0;

				// 判断导入版本是否低于现在配方版本
				Version importVersion = new Version(formulaImportParam.ImportFormulaSystem.getFormulaSystemVersion());
				Version currentVersion = new Version(formulaImportParam.CurrentFormulaSystem.getFormulaSystemVersion());
				if (importVersion.compareTo(currentVersion) < 0)
				{
					result = 1;
				} else
				{
					result = 2;
				}
				formulaImportParam.CurrentFormulaSystem.setFormulaSystemVersion(formulaImportParam.ImportFormulaSystem.getFormulaSystemVersion());
				formulaImportParam.CurrentFormulaSystem.setCreateTime(formulaImportParam.ImportFormulaSystem.getCreateTime());

				return result;
			}
		};

		int result = 0;// 0:配方体系不同，不允许导入；1：导入配方文件版本低，返回提示信息；2：配方文件正常，允许导入

		// 当前配方体系为空
		if (formulaImportParam.CurrentFormulaSystem == null)
		{
			result = 2;
			formulaImportParam.CurrentFormulaSystem = formulaImportParam.ImportFormulaSystem;

			return result;
		}

		// 二代配方文件
		if (formulaImportParam.ImportFileVersion == ImportFileVersion.ColorExpert2)
		{
			result = func.apply();

			return result;
		}

		// 三代配方文件,附加导入
		if (formulaImportParam.ImportMode == ImportMode.AdditionalImport)
		{
			// 判断配方体系是否相同
			if (formulaImportParam.CurrentFormulaSystem.getFormulaSystemName().equals(formulaImportParam.ImportFormulaSystem.getFormulaSystemName()))
			{
				result = func.apply();

				return result;
			} else
			{
				return result;
			}
		}


		// 三代配方文件,清空导入,离线，配方体系不同
		if (!formulaImportParam.CurrentFormulaSystem.getFormulaSystemName().equals(formulaImportParam.ImportFormulaSystem.getFormulaSystemName()))
		{
			result = 2;
			formulaImportParam.CurrentFormulaSystem = formulaImportParam.ImportFormulaSystem;

			return result;
		}

		result = func.apply();

		return result;
	}
	/***
	 * 字符串转换为日期类型(当前系统环境)
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date ConvertStringToDateTimeWithSystem(String value) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
		try
		{
			return sdf.parse(value);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		// return Date.parse(value);
		// return DateTime.Parse(value);
	}
	// #endregion
}
