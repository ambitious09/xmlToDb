package com.example.autoimportxmltodb.modle;

import java.util.List;


/***
 * 导入数据集合信息
 */
public class ImportDataSetInfo
{
	// #region 需导入的集合
	/***
	 * 配方体系集合
	 */
	public List<FormulaSystem> FormulaSystemSet;
	
	/***
	 * 单位实体集
	 */
//	public List<Unit> UnitSet;
	/***
	 * 颜色分组集合
	 */
	public List<ColorGroup> ColorGroupSet;

	/***
	 * 色卡组别色卡
	 */
	public List<ColorGroup_Color> ColorGroup_ColorSet;

	/***
	 * 颜色地图集合
	 */
	public List<ColorMap> ColorMapSet;

	/***
	 * 汽车集合
	 */
	public List<Auto> AutoSet;

	/***
	 * 色卡车型集合
	 */
	public List<ColorAuto> ColorAutoSet;

	/***
	 * 内部色卡集合
	 */
	public List<InnerColor> InnerColorSet;

	/***
	 * 差异色卡集合
	 */
	public List<DerivateColor> DerivateColorSet;

	/***
	 * 关联集合
	 */
	public List<Relations> RelationsSet;

	/***
	 * 品牌产品集合
	 */
	public List<BrandProduct> BrandProductSet;

	/***
	 * 色卡车型部件集合
	 */
	public List<ColorAutoParts> ColorAutoPartsSet;

	/***
	 * 标准色卡集合
	 */
	public List<StandardColor> StandardColorSet;

	/***
	 * 颜色类别集合
	 */
	public List<ColorType> ColorTypeSet;

	/***
	 * 色母集合
	 */
	public List<Colorants> ColorantsSet;
	/***
	 * 色母价格集合
	 */
	public List<ColorantPrice> ColorantPriceSet;

	/***
	 * 关联类别集合
	 */
	public List<RelationsType> RelationsTypeSet;

	/***
	 * 品牌集合
	 */
	public List<Brand> BrandSet;

	/***
	 * 产品系列集合
	 */
	public List<Product> ProductSet;

	/***
	 * 汽车部件集合
	 */
	public List<AutoParts> AutoPartsSet;

	/***
	 * 标准色卡系统体集
	 */
	public List<StandardColorSystem> StandardColorSystemSet;

	/***
	 * 色卡配方集合
	 */
	public List<ColorFormula> ColorFormulaSet;

	/***
	 * 色卡配方色母集合
	 */
	public List<ColorFormulaColorants> ColorFormulaColorantsSet;
	/***
	 *配方集合
	 */
	public List<Formula> FormulaSet;

	/***
	 * 配方信息集合
	 */
	public List<FormulaRemark> FormulaRemarkSet;

	

	/***
	 * 删除数据集合
	 */
	public List<DeleteData> DeleteDataSet;
}
