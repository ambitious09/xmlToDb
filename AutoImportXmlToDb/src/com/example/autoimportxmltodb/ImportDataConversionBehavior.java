package com.example.autoimportxmltodb;

import java.util.Date;

import org.dom4j.Element;

import propel.core.functional.Functions.Function1;

import com.example.autoimportxmltodb.commmen.Common;
import com.example.autoimportxmltodb.commmen.EncryptDecryptHelper;
import com.example.autoimportxmltodb.commmen.LanguageLocal;
import com.example.autoimportxmltodb.commmen.MyLinq;
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

/***
 * 导入数据转化行为接口
 */
abstract class ImportDataConversionBehavior
{
	/***
	 * 配方导入参数实体
	 */
	protected FormulaImportParam formulaImportParam = null;

	/***
	 * 导入数据转化行为构造函数
	 * 
	 * @param formulaImportParam
	 *            配方导入参数实体
	 */
	public ImportDataConversionBehavior(FormulaImportParam formulaImportParam)
	{
		this.formulaImportParam = formulaImportParam;
	}

	/***
	 * 获取导入的数据集合
	 * 
	 * @return 导入数据集合
	 */
	public abstract ImportDataSetInfo GetImportDatas();
}

/***
 * 标准配方导入数据转化行为类
 */
class StandardFormulaImport extends ImportDataConversionBehavior
{
	/***
	 * ColorExpert3数据转化行为类构造函数
	 */
	public StandardFormulaImport(FormulaImportParam standardFormulaParam)
	{
		super(standardFormulaParam);
	}

	/***
	 * 数据转化
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ImportDataSetInfo GetImportDatas()
	{
		ImportDataSetInfo importDataSetInfo = new ImportDataSetInfo();
		importDataSetInfo.FormulaSystemSet = MyLinq.Select(formulaImportParam.importXElement.elements("FormulaSystem"), null, new Function1<Element, FormulaSystem>()
			{
				@Override
				public FormulaSystem apply(Element arg)
				{
					return FormulaSystemConvertor(arg);
				}
			});
		// 设置色母价格集合
		importDataSetInfo.ColorantPriceSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorantPrice"), null, new Function1<Element, ColorantPrice>()
			{
			@Override
			public ColorantPrice apply(Element arg)
			{
				return ColorantPriceConvertor(arg);
			}
			});
		// 设置颜色分组集合
		importDataSetInfo.ColorGroupSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorGroup"), null, new Function1<Element, ColorGroup>()
		{
			@Override
			public ColorGroup apply(Element arg)
			{
				return ColorGroupConvertor(arg);
			}
		});

		// 设置色卡组别色卡
		importDataSetInfo.ColorGroup_ColorSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorGroup_Color"), null, new Function1<Element, ColorGroup_Color>()
		{
			@Override
			public ColorGroup_Color apply(Element arg)
			{
				return ColorGroup_ColorConvertor(arg);
			}
		});

		// 设置颜色地图集合
		importDataSetInfo.ColorMapSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorMap"), null, new Function1<Element, ColorMap>()
		{
			@Override
			public ColorMap apply(Element arg)
			{
				return ColorMapConvertor(arg);
			}
		});

		// 设置汽车集合
		importDataSetInfo.AutoSet = MyLinq.Select(formulaImportParam.importXElement.elements("Auto"), null, new Function1<Element, Auto>()
		{
			@Override
			public Auto apply(Element arg)
			{
				return AutoConvertor(arg);
			}
		});

		// 设置色卡车型集合
		importDataSetInfo.ColorAutoSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorAuto"), null, new Function1<Element, ColorAuto>()
		{
			@Override
			public ColorAuto apply(Element arg)
			{
				return ColorAutoConvertor(arg);
			}
		});

		// 设置内部色卡集合
		importDataSetInfo.InnerColorSet = MyLinq.Select(formulaImportParam.importXElement.elements("InnerColor"), null, new Function1<Element, InnerColor>()
		{
			@Override
			public InnerColor apply(Element arg)
			{
				return InnerColorConvertor(arg);
			}
		});

		// 设置差异色卡集合
		importDataSetInfo.DerivateColorSet = MyLinq.Select(formulaImportParam.importXElement.elements("DerivateColor"), null, new Function1<Element, DerivateColor>()
		{
			@Override
			public DerivateColor apply(Element arg)
			{
				return DerivateColorConvertor(arg);
			}
		});

		// 设置关联集合
		importDataSetInfo.RelationsSet = MyLinq.Select(formulaImportParam.importXElement.elements("Relations"), null, new Function1<Element, Relations>()
		{
			@Override
			public Relations apply(Element arg)
			{
				return RelationsConvertor(arg);
			}
		});

		// 设置 品牌产品集合
		importDataSetInfo.BrandProductSet = MyLinq.Select(formulaImportParam.importXElement.elements("BrandProduct"), null, new Function1<Element, BrandProduct>()
		{
			@Override
			public BrandProduct apply(Element arg)
			{
				return BrandProductConvertor(arg);
			}
		});

		// 设置色卡车型部件集合
		importDataSetInfo.ColorAutoPartsSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorAutoParts"), null, new Function1<Element, ColorAutoParts>()
		{
			@Override
			public ColorAutoParts apply(Element arg)
			{
				return ColorAutoPartsConvertor(arg);
			}
		});

		// 设置标准色卡集合
		importDataSetInfo.StandardColorSet = MyLinq.Select(formulaImportParam.importXElement.elements("StandardColor"), null, new Function1<Element, StandardColor>()
		{
			@Override
			public StandardColor apply(Element arg)
			{
				return StandardColorConvertor(arg);
			}
		});

		// 设置颜色类别集合
		importDataSetInfo.ColorTypeSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorType"), null, new Function1<Element, ColorType>()
		{
			@Override
			public ColorType apply(Element arg)
			{
				return ColorTypeConvertor(arg);
			}
		});

		// 设置色母集合
		importDataSetInfo.ColorantsSet = MyLinq.Select(formulaImportParam.importXElement.elements("Colorants"), null, new Function1<Element, Colorants>()
		{
			@Override
			public Colorants apply(Element arg)
			{
				return ColorantsConvertor(arg);
			}
		});
		// 设置色母集合
		importDataSetInfo.ColorantsSet = MyLinq.Select(formulaImportParam.importXElement.elements("Colorants"), null, new Function1<Element, Colorants>()
				{
			@Override
			public Colorants apply(Element arg)
			{
				return ColorantsConvertor(arg);
			}
				});
		// 设置色卡配方色母集合
		importDataSetInfo.ColorFormulaColorantsSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorFormulaColorants"), null, new Function1<Element, ColorFormulaColorants>()
				{
			@Override
			public ColorFormulaColorants apply(Element arg)
			{
				return ColorFormulaColorantsConvertor(arg);
			}
				});

		// 设置关联类别集合
		importDataSetInfo.RelationsTypeSet = MyLinq.Select(formulaImportParam.importXElement.elements("RelationsType"), null, new Function1<Element, RelationsType>()
		{
			@Override
			public RelationsType apply(Element arg)
			{
				return RelationsTypeConvertor(arg);
			}
		});

		// 设置品牌集合
		importDataSetInfo.BrandSet = MyLinq.Select(formulaImportParam.importXElement.elements("Brand"), null, new Function1<Element, Brand>()
		{
			@Override
			public Brand apply(Element arg)
			{
				return BrandConvertor(arg);
			}
		});
		// 设置产品系列集合
	    importDataSetInfo.ProductSet = MyLinq.Select(formulaImportParam.importXElement.elements("Product"), null, new Function1<Element, Product>()
		{
			@Override
			public Product apply(Element arg)
			{
				return ProductConvertor(arg);
		}
		});
		// 设置汽车部件集合
		importDataSetInfo.AutoPartsSet = MyLinq.Select(formulaImportParam.importXElement.elements("AutoParts"), null, new Function1<Element, AutoParts>()
		{
			@Override
			public AutoParts apply(Element arg)
			{
				return AutoPartsConvertor(arg);
			}
		});		
		// 设置标准色卡系统体集
		importDataSetInfo.StandardColorSystemSet = MyLinq.Select(formulaImportParam.importXElement.elements("StandardColorSystem"), null, new Function1<Element, StandardColorSystem>()
		{
			@Override
			public StandardColorSystem apply(Element arg)
			{
				return StandardColorSystemConvertor(arg);
			}
		});	
		// 设置色卡配方集合
		importDataSetInfo.ColorFormulaSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorFormula"), null, new Function1<Element, ColorFormula>()
		{
				@Override
				public ColorFormula apply(Element arg)
				{
					return ColorFormulaConvertor(arg);
				}
		});			
		// 设置色卡配方色母集合
		importDataSetInfo.ColorFormulaColorantsSet = MyLinq.Select(formulaImportParam.importXElement.elements("ColorFormulaColorants"), null, new Function1<Element, ColorFormulaColorants>()
		{
				@Override
				public ColorFormulaColorants apply(Element arg)
				{
					return ColorFormulaColorantsConvertor(arg);
				}
		});		
		// 设置配方集合
		importDataSetInfo.FormulaSet = MyLinq.Select(formulaImportParam.importXElement.elements("Formula"), null, new Function1<Element, Formula>()
		{
				@Override
				public Formula apply(Element arg)
				{
					return FormulaConvertor(arg);
				}
		});	
		// 设置配方信息集合
		importDataSetInfo.FormulaRemarkSet = MyLinq.Select(formulaImportParam.importXElement.elements("FormulaRemark"), null, new Function1<Element, FormulaRemark>()
		{
				@Override
				public FormulaRemark apply(Element arg)
				{
					return FormulaRemarkConvertor(arg);
				}
		});	
		
							

		return importDataSetInfo;
	}

	//#region 实体转换器

	private FormulaSystem FormulaSystemConvertor(Element s)
	{
		FormulaSystem formulaSystem=new FormulaSystem();
		formulaSystem.setFormulaSystemId(Integer.parseInt(s.elementTextTrim("FormulaSystemId")));
		formulaSystem.setFormulaSystemName(s.elementTextTrim("FormulaSystemName"));
		formulaSystem.setFormulaSystemVersion(s.elementTextTrim("FormulaSystemVersion"));
		formulaSystem.setCreateTime(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateTime")));
		return formulaSystem;
	}

	private ColorantPrice ColorantPriceConvertor(Element s)
	{
		ColorantPrice colorantPrice = new ColorantPrice();
		colorantPrice.setColorantPriceId(Integer.parseInt(s.elementTextTrim("ColorantPriceId")));
		colorantPrice.setColorantId(Integer.parseInt(s.elementTextTrim("ColorantId")));
		colorantPrice.setColorGroupId(Integer.parseInt(s.elementTextTrim("ColorGroupId")));
		colorantPrice.setColorantCode(s.elementTextTrim("ColorantCode"));
		colorantPrice.setColorantName(s.elementTextTrim("ColorantName"));
		colorantPrice.setColorantFeatures(s.elementTextTrim("ColorantFeatures"));
		colorantPrice.setColorantDensity(LanguageLocal.ConvertStringToDoubleWithEn(s.elementTextTrim("ColorantDensity")));
		if (s.elementTextTrim("ColorantPrices")!=null&&s.elementTextTrim("ColorantPrices").length()>0)
		{
			colorantPrice.setColorantPrice(LanguageLocal.ConvertStringToDoubleWithEn(s.elementTextTrim("ColorantPrices")));
		}
		else
		{
			colorantPrice.setColorantPrice(LanguageLocal.ConvertStringToDoubleWithEn("0"));
		}
		if (s.elementTextTrim("ColorPriceRate")!=null&&s.elementTextTrim("ColorPriceRate").length()>0)
		{
			colorantPrice.setColorantPriceRate(LanguageLocal.ConvertStringToDoubleWithEn(s.elementTextTrim("ColorPriceRate")));
		}
		else
		{
			colorantPrice.setColorantPriceRate(LanguageLocal.ConvertStringToDoubleWithEn("1"));
		}
		if (s.elementTextTrim("CanSize")!=null&&s.elementTextTrim("CanSize").length()>0)
		{
			colorantPrice.setCanSize(LanguageLocal.ConvertStringToDoubleWithEn(s.elementTextTrim("CanSize")));
		}
		else
		{
			colorantPrice.setCanSize(LanguageLocal.ConvertStringToDoubleWithEn("1"));
		}
		if (s.elementTextTrim("UnitName")!=null&&s.elementTextTrim("UnitName").length()>0)
		{
			colorantPrice.setUnitId(s.elementTextTrim("UnitName"));
		}
		else
		{
			colorantPrice.setUnitId("L");
		}
		colorantPrice.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorantPrice.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
		return colorantPrice;
	}
	//#region 实体转换器
	
	private ColorGroup ColorGroupConvertor(Element s)
	{
		ColorGroup colorGroup = new ColorGroup();
		colorGroup.setColorGroupId(Integer.parseInt(s.elementTextTrim("ColorGroupId")));
		colorGroup.setColorGroupName(s.elementTextTrim("ColorGroupName"));
		colorGroup.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorGroup.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return colorGroup;
	}

	private ColorGroup_Color ColorGroup_ColorConvertor(Element s)
	{
		ColorGroup_Color colorGroup_Color = new ColorGroup_Color();
		colorGroup_Color.setColorGroupColorId(Integer.parseInt(s.elementTextTrim("ColorGroupColorId")));
		colorGroup_Color.setColorGroupId(Integer.parseInt(s.elementTextTrim("ColorGroupId")));
		colorGroup_Color.setColorId(Integer.parseInt(s.elementTextTrim("ColorId")));
		colorGroup_Color.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorGroup_Color.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return colorGroup_Color;
	}

	private ColorMap ColorMapConvertor(Element s)
	{
		ColorMap colorMap = new ColorMap();
		colorMap.setInnerColorId(Integer.parseInt(s.elementTextTrim("InnerColorId")));
		colorMap.setDocumentation(s.elementTextTrim("Documentation"));
		colorMap.setColorMap(Integer.parseInt(s.elementTextTrim("ColorMapId")));
		colorMap.setPage(s.elementTextTrim("Page"));
		
		return colorMap;
	}

	private Auto AutoConvertor(Element s)
	{
		Auto auto = new Auto();
		auto.setAutoId(Integer.parseInt(s.elementTextTrim("AutoId")));
		if (s.elementTextTrim("MasterId")!=null&&s.elementTextTrim("MasterId").length()>0)
		{
			auto.setMasterId(Integer.parseInt(s.elementTextTrim("MasterId")));
			
		}
		auto.setRelationId(Integer.parseInt(s.elementTextTrim("RelationId")));
		auto.setAutoName(s.elementTextTrim("AutoName"));
		auto.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("YearFirstUsed")));
		auto.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("YearLastUsed")));
		auto.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		auto.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return auto;
	}

	private ColorAuto ColorAutoConvertor(Element s)
	{
		ColorAuto colorAuto = new ColorAuto();
		colorAuto.setAutoId(Integer.parseInt(s.elementTextTrim("AutoId")));
		colorAuto.setColorAutoId(Integer.parseInt(s.elementTextTrim("ColorAutoId")));
		colorAuto.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorAuto.setInnerColorId(Integer.parseInt(s.elementTextTrim("InnerColorId")));
		colorAuto.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		colorAuto.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("YearFirstUsed")));
		colorAuto.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("YearLastUsed")));
		return colorAuto;
	}

	private InnerColor InnerColorConvertor(Element s)
	{
		InnerColor innerColor = new InnerColor();
		innerColor.setInnerColorId(Integer.parseInt(s.elementTextTrim("InnerColorId")));
		innerColor.setColorTypeId(Integer.parseInt(s.elementTextTrim("ColorTypeId")));
		if (s.elementTextTrim("RelationId")!=null&&s.elementTextTrim("RelationId").length()>0)
		{
			innerColor.setRelationId(Integer.parseInt(s.elementTextTrim("RelationId")));
			
		}
		innerColor.setColorCode(s.elementTextTrim("ColorCode"));
		innerColor.setColorName(s.elementTextTrim("ColorName"));
		innerColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		innerColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		innerColor.setYearFirstUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("YearFirstUsed")));
		innerColor.setYearLastUsed(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("YearLastUsed")));
		innerColor.setRGB(!Common.IsNullOrEmpty(s.elementTextTrim("RGB")) ? s.elementTextTrim("RGB") : "000000");
		return innerColor;
	}

	private DerivateColor DerivateColorConvertor(Element s)
	{
		DerivateColor derivateColor = new DerivateColor();
		derivateColor.setDerivateColorId(Integer.parseInt(s.elementTextTrim("DerivateColorId")));
		derivateColor.setInnerColorId(Integer.parseInt(s.elementTextTrim("InnerColorId")));
		if (s.elementTextTrim("InnerParentColorId")!=null&&s.elementTextTrim("InnerParentColorId").length()>0)
		{
			
			derivateColor.setInnerParentColorId(Integer.parseInt(s.elementTextTrim("InnerParentColorId")));
		}
		derivateColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		derivateColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return derivateColor;
	}

	private Relations RelationsConvertor(Element s)
	{
		Relations relations = new Relations();
		relations.setRelationsId(Integer.parseInt(s.elementTextTrim("RelationsId")));
		relations.setRelationsTypeId(Integer.parseInt(s.elementTextTrim("RelationsTypeId")));
		relations.setRelationsName(s.elementTextTrim("RelationsName"));
		relations.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		relations.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return relations;
	}

	private BrandProduct BrandProductConvertor(Element s)
	{
		BrandProduct brandProduct = new BrandProduct();
		brandProduct.setBrandId(Integer.parseInt(s.elementTextTrim("BrandId")));//BrandId
		brandProduct.setBrandProductId(Integer.parseInt(s.elementTextTrim("BrandProductId")));
		brandProduct.setProductId(Integer.parseInt(s.elementTextTrim("ProductId")));
		return brandProduct;
	}

	private ColorAutoParts ColorAutoPartsConvertor(Element s)
	{
		ColorAutoParts colorAutoParts = new ColorAutoParts();
		colorAutoParts.setAutoPartsId(Integer.parseInt(s.elementTextTrim("AutoPartsId")));
		colorAutoParts.setColorAutoId(Integer.parseInt(s.elementTextTrim("ColorAutoId")));
		colorAutoParts.setColorAutoPartsId(Integer.parseInt(s.elementTextTrim("ColorAutoPartsId")));
		colorAutoParts.setInnerColorId(Integer.parseInt(s.elementTextTrim("InnerColorId")));
		colorAutoParts.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorAutoParts.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return colorAutoParts;
	}

	private StandardColor StandardColorConvertor(Element s)
	{
		StandardColor standardColor = new StandardColor();
		standardColor.setInnerColorId(Integer.parseInt(s.elementTextTrim("InnerColorId")));
		standardColor.setStandardColorId(Integer.parseInt(s.elementTextTrim("StandardColorId")));
		standardColor.setStandardColorSystemId(Integer.parseInt(s.elementTextTrim("StandardColorSystemId")));
		standardColor.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		standardColor.setStandardColorCode(s.elementTextTrim("StandardColorCode"));
		standardColor.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return standardColor;
	}

	private ColorType ColorTypeConvertor(Element s)
	{
		ColorType colorType = new ColorType();
		colorType.setColorTypeId( Integer.parseInt(s.elementTextTrim("ColorTypeId")));
		colorType.setColorTypeName(s.elementTextTrim("ColorTypeName"));
		colorType.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorType.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return colorType;
	}

	private Colorants ColorantsConvertor(Element s)
	{
		Colorants colorants = new Colorants();
		colorants.setColorantId(Integer.parseInt(s.elementTextTrim("ColorantId")));
		colorants.setColorantCode(EncryptDecryptHelper.DESEncrypt(s.elementTextTrim("ColorantCode"),"@SANTINT!"));
		colorants.setColorantFeatures(s.elementTextTrim("ColorantFeatures"));
		colorants.setColorantName(EncryptDecryptHelper.DESEncrypt(s.elementTextTrim("ColorantName"), "@SANTINT!"));
		colorants.setColorGroupId(Integer.parseInt(s.elementTextTrim("ColorGroupId")));
		colorants.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorants.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateTimeToShortStringWithEn(new Date())));
		colorants.setColorantDensity(LanguageLocal.ConvertStringToDoubleWithEn(s.elementTextTrim("ColorantDensity")));
		return colorants;
	}

	private RelationsType RelationsTypeConvertor(Element s)
	{
		RelationsType relatonsType = new RelationsType();
		relatonsType.setRelationsTypeId(Integer.parseInt(s.elementTextTrim("RelationsTypeId")));
		relatonsType.setRelationsTypeName(s.elementTextTrim("RelationsTypeName"));
		relatonsType.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		relatonsType.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return relatonsType;
	}

	private Brand BrandConvertor(Element s)
	{
		Brand  brand= new Brand();
		brand.setBrandId(Integer.parseInt(s.elementTextTrim("BrandId")));
		brand.setBrandName(s.elementTextTrim("BrandName"));
		brand.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		brand.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return brand;
	}
	private Product ProductConvertor(Element s)
	{
		Product product=new Product();
		product.setProductId(Integer.parseInt(s.elementTextTrim("ProductId")));
		product.setProductCode(s.elementTextTrim("ProductCode"));
		product.setProductName(s.elementTextTrim("ProductName"));
		product.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		product.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return product;
	}
	
	private AutoParts AutoPartsConvertor(Element s){
		AutoParts autoParts=new AutoParts();
		autoParts.setAutoPartsId(Integer.parseInt(s.elementTextTrim("AutoPartsId")));
		autoParts.setAutoPartsName(s.elementTextTrim("AutoPartsName"));
		autoParts.setCreateDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		autoParts.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return autoParts;
	}
	private StandardColorSystem StandardColorSystemConvertor(Element s){
		StandardColorSystem  standardColorSystem=new StandardColorSystem();
		standardColorSystem.setStandardColorSystemId(Integer.parseInt(s.elementTextTrim("StandardColorSystemId")));
		standardColorSystem.setStandardColorSystemName(s.elementTextTrim("StandardColorSystemName"));
		standardColorSystem.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		standardColorSystem.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return standardColorSystem;
	}
	private ColorFormula ColorFormulaConvertor(Element s){
		ColorFormula  colorFormula=new ColorFormula();
		colorFormula.setColorFormulaId(Integer.parseInt(s.elementTextTrim("ColorFormulaId")));
		colorFormula.setInnerColorId(Integer.parseInt(s.elementTextTrim("InnerColorId")));
		colorFormula.setFormulaId(Integer.parseInt(s.elementTextTrim("FormulaId")));
		colorFormula.setLayerNumber(Integer.parseInt(s.elementTextTrim("LayerNumber")));
		colorFormula.setColorFormulaCode(s.elementTextTrim("ColorFormulaCode"));
		colorFormula.setLayerDescription(s.elementTextTrim("LayerDescription"));
		colorFormula.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorFormula.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		if (s.elementTextTrim("RelationId")!=null&&s.elementTextTrim("RelationId").length()>0)
		{
			
			colorFormula.setRelationId(Integer.parseInt(s.elementTextTrim("RelationId")));
		}
		return colorFormula;
	}
	private ColorFormulaColorants ColorFormulaColorantsConvertor(Element s){
		ColorFormulaColorants  colorFormulaColorants=new ColorFormulaColorants();
		colorFormulaColorants.setColorFormulaColorantsId(Integer.parseInt(s.elementTextTrim("ColorFormulaColorantsId")));
		colorFormulaColorants.setColorFormulaId(Integer.parseInt(s.elementTextTrim("ColorFormulaId")));
		colorFormulaColorants.setColorantsId(Integer.parseInt(s.elementTextTrim("ColorantsId")));
		colorFormulaColorants.setColorantSequence(Integer.parseInt(s.elementTextTrim("ColorantSequence")));
		colorFormulaColorants.setWeightPercent(EncryptDecryptHelper.DESEncrypt(s.elementTextTrim("WeightPercent"), "@SANTINT!"));
		colorFormulaColorants.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		colorFormulaColorants.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return colorFormulaColorants;
	}
	private Formula FormulaConvertor(Element s){
		Formula  formula=new Formula();
		formula.setFormulaId(Integer.parseInt(s.elementTextTrim("FormulaId")));
		formula.setProductId(Integer.parseInt(s.elementTextTrim("ProductId")));
		formula.setFormulaVersion(s.elementTextTrim("FormulaVersion"));
		formula.setFormulaVersionDate(StandardFormulaImportBehavior.ConvertStringToDateTimeWithSystem(s.elementTextTrim("FormulaVersionDate")));
		formula.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		formula.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		if (s.elementTextTrim("Source") != null && s.elementTextTrim("Source").length() > 0)
		{
			formula.setSource(Integer.parseInt(s.elementTextTrim("Source")));
		}
		else
		{
			formula.setSource(Integer.parseInt("-1"));
		}
		if (s.elementTextTrim("RelationId")!=null&&s.elementTextTrim("RelationId").length()>0)
		{
			
			formula.setRelationId(Integer.parseInt(s.elementTextTrim("RelationId")));
		}
		return formula;
	}
	private FormulaRemark FormulaRemarkConvertor(Element s){
		FormulaRemark  formulaRemark=new FormulaRemark();
		formulaRemark.setFormulaRemarkId(Integer.parseInt(s.elementTextTrim("FormulaRemarkId")));
		formulaRemark.setFormulaRemarks(s.elementTextTrim("FormulaRemarks"));
		formulaRemark.setColorFormulaId(Integer.parseInt(s.elementTextTrim("ColorFormulaId")));
		formulaRemark.setCreatedDate(LanguageLocal.ConvertStringToDateTimeWithEn(s.elementTextTrim("CreateDate")));
		formulaRemark.setSystemDate(LanguageLocal.ConvertStringToDateTimeWithEn(LanguageLocal.ConvertDateToLongStringWithEn(new Date())));
		return formulaRemark;
	}
	//#endregion
}



