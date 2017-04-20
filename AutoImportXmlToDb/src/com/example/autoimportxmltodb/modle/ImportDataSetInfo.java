package com.example.autoimportxmltodb.modle;

import java.util.List;


/***
 * �������ݼ�����Ϣ
 */
public class ImportDataSetInfo
{
	// #region �赼��ļ���
	/***
	 * �䷽��ϵ����
	 */
	public List<FormulaSystem> FormulaSystemSet;
	
	/***
	 * ��λʵ�弯
	 */
//	public List<Unit> UnitSet;
	/***
	 * ��ɫ���鼯��
	 */
	public List<ColorGroup> ColorGroupSet;

	/***
	 * ɫ�����ɫ��
	 */
	public List<ColorGroup_Color> ColorGroup_ColorSet;

	/***
	 * ��ɫ��ͼ����
	 */
	public List<ColorMap> ColorMapSet;

	/***
	 * ��������
	 */
	public List<Auto> AutoSet;

	/***
	 * ɫ�����ͼ���
	 */
	public List<ColorAuto> ColorAutoSet;

	/***
	 * �ڲ�ɫ������
	 */
	public List<InnerColor> InnerColorSet;

	/***
	 * ����ɫ������
	 */
	public List<DerivateColor> DerivateColorSet;

	/***
	 * ��������
	 */
	public List<Relations> RelationsSet;

	/***
	 * Ʒ�Ʋ�Ʒ����
	 */
	public List<BrandProduct> BrandProductSet;

	/***
	 * ɫ�����Ͳ�������
	 */
	public List<ColorAutoParts> ColorAutoPartsSet;

	/***
	 * ��׼ɫ������
	 */
	public List<StandardColor> StandardColorSet;

	/***
	 * ��ɫ��𼯺�
	 */
	public List<ColorType> ColorTypeSet;

	/***
	 * ɫĸ����
	 */
	public List<Colorants> ColorantsSet;
	/***
	 * ɫĸ�۸񼯺�
	 */
	public List<ColorantPrice> ColorantPriceSet;

	/***
	 * ������𼯺�
	 */
	public List<RelationsType> RelationsTypeSet;

	/***
	 * Ʒ�Ƽ���
	 */
	public List<Brand> BrandSet;

	/***
	 * ��Ʒϵ�м���
	 */
	public List<Product> ProductSet;

	/***
	 * ������������
	 */
	public List<AutoParts> AutoPartsSet;

	/***
	 * ��׼ɫ��ϵͳ�弯
	 */
	public List<StandardColorSystem> StandardColorSystemSet;

	/***
	 * ɫ���䷽����
	 */
	public List<ColorFormula> ColorFormulaSet;

	/***
	 * ɫ���䷽ɫĸ����
	 */
	public List<ColorFormulaColorants> ColorFormulaColorantsSet;
	/***
	 *�䷽����
	 */
	public List<Formula> FormulaSet;

	/***
	 * �䷽��Ϣ����
	 */
	public List<FormulaRemark> FormulaRemarkSet;

	

	/***
	 * ɾ�����ݼ���
	 */
	public List<DeleteData> DeleteDataSet;
}
