package com.example.autoimportxmltodb;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import propel.core.functional.Predicates.Predicate1;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.autoimportxmltodb.commmen.MyLinq;
import com.example.autoimportxmltodb.modle.Auto;
import com.example.autoimportxmltodb.modle.BrandProduct;
import com.example.autoimportxmltodb.modle.ColorAuto;
import com.example.autoimportxmltodb.modle.ColorFormula;
import com.example.autoimportxmltodb.modle.ColorFormulaColorants;
import com.example.autoimportxmltodb.modle.DerivateColor;
import com.example.autoimportxmltodb.modle.StandardColor;


/***
 * sql���
 * 
 * @author liu
 * 
 */
class SqlString
{
	//#region ɾ�������

	/***
	 * ��ձ����
	 */
	public static final String Sql_DeleteTable = "delete from \"{0}\"";


	//#endregion

	//#region �������
	/***
	 * �����䷽��ϵ���
	 */
	public static final String Sql_StandardFormula_InsertFormulaSystem = "insert into FormulaSystem (FormulaSystemId,FormulaSystemName,FormulaSystemVersion,CreateTime) values (?,?,?,?)";
	/***
	 * ����Ʒ�Ʊ����
	 */
	public static final String Sql_StandardFormula_InsertBrand = "insert into Brand (BrandId,BrandName,CreateDate,SystemDate) values (?,?,?,?)";

	/***
	 * ������λ���
	 */
	public static final String Sql_SCH_InsertUnit = "insert into Unit (UnitId,UnitName,UnitPrecision,UnitRatio,UnitType) values (?,?,?,?,?)";
	/***
	 * ����ɫĸ�۸����
	 */
	public static final String Sql_SCH_InsertColorantPrice = "insert into ColorantPrice (ColorantPriceId,ColorantId,ColorGroupId,ColorantCode,ColorantName,ColorantFeatures,ColorantDensity,ColorantPrice,ColorantPriceRate,CanSize,UnitName,CreatedDate,SystemDate) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/***
	 * ����Ʒ�Ʋ�Ʒ�����
	 */
	public static final String Sql_StandardFormula_InsertBrandProduct = "insert into BrandProduct (BrandProductId,ProductId,BrandId) values (?,?,?)";

	/***
	 * ������Ʒϵ�б����
	 */
	public static final String Sql_SH_InsertProduct = "insert into Product (ProductId,ProductCode,ProductName,CreateDate,SystemDate) values (?,?,?,?,?)";

	/***
	 * �����䷽�����
	 */
	public static final String Sql_CH_InsertFormula = "insert into Formula (FormulaId,ProductId,FormulaVersion,FormulaVersionDate,CreatedDate,SystemDate,RelationId,Source) values (?,?,?,?,?,?,?,?)";

	/***
	 * �����䷽��Ϣ����䣬
	 */
	public static final String Sql_SCH_InsertFormulaRemark = "insert into FormulaRemark (FormulaRemarkId,FormulaRemarks,ColorFormulaId,CreatedDate,SystemDate) values (?,?,?,?,?)";

	/***
	 * �����������������
	 */
	public static final String Sql_StandardFormula_InsertAutoParts = "insert into AutoParts (AutoPartsId,AutoPartsName,CreatedDate,SystemDate) values (?,?,?,?)";

	/***
	 * ������׼ɫ��ϵͳ���
	 */
	public static final String Sql_SH_InsertStandardColorSystem = "insert into StandardColorSystem (StandardColorSystemId,StandardColorSystemName,CreatedDate,SystemDate) values (?,?,?,?)";

	/***
	 * ����ɫ���䷽�����
	 */
	public static final String Sql_CH_InsertColorFormula = "insert into ColorFormula (ColorFormulaId,InnerColorId,FormulaId,LayerNumber,ColorFormulaCode,LayerDescription,CreatedDate,SystemDate,RelationId) values (?,?,?,?,?,?,?,?,?)";

	/***
	 * ����ɫ�����Ͳ��������
	 */
	public static final String Sql_SCH_InsertColorAutoParts = "insert into ColorAutoParts (ColorAutoId,InnerColorId,AutoPartsId,CreatedDate,SystemDate,ColorAutoPartsId) values (?,?,?,?,?,?)";

	/***
	 * ������׼ɫ�����
	 */
	public static final String Sql_StandardFormula_InsertStandardColor = "insert into StandardColor (InnerColorId,StandardColorCode,StandardColorSystemId,CreateDate,SystemDate,StandardColorId) values (?,?,?,?,?,?)";

	/***
	 * ����ɫ���䷽ɫĸ�����
	 */
	public static final String Sql_StandardFormula_InsertColorFormulaColorants = "insert into ColorFormulaColorants (ColorFormulaColorantsId,ColorFormulaId,ColorantsId,ColorantSequence,WeightPercent,CreatedDate,SystemDate) values (?,?,?,?,?,?,?)";
////////////////////////////
	/***
	 * ������ɫ�������
	 */
	public static final String Sql_StandardFormula_InsertColorType= "insert into ColorType (ColorTypeId,ColorTypeName,CreatedDate,SystemDate) values (?,?,?,?)";

	/***
	 * ����ɫĸ�����
	 */
	public static final String Sql_SCH_InsertColorants = "insert into Colorants (ColorantId,ColorGroupId,ColorantCode,ColorantName,ColorantFeatures,ColorantDensity,CreatedDate,SystemDate) values (?,?,?,?,?,?,?,?)";
	/***
	 * ����ɫ���䷽ɫĸ�����
	 */
	public static final String Sql_SCH_InsertColorFormulaColorants = "insert into ColorFormulaColorants (ColorFormulaColorantsId,ColorFormulaId,ColorantsId,ColorantSequence,WeightPercent,CreatedDate,SystemDate) values (?,?,?,?,?,?,?)";

	/***
	 * ���������������
	 */
	public static final String Sql_SCH_InsertRelationsType = "insert into RelationsType (RelationsTypeId,RelationsTypeName,CreatedDate,SystemDate) values (?,?,?,?)";

	/***
	 * �������������
	 */
	public static final String Sql_StandardFormula_InsertAuto = "insert into Auto (AutoId,MasterId,AutoName,YearFirstUsed,YearLastUsed,RelationId,CreatedDate,SystemDate) values (?,?,?,?,?,?,?,?)";

	/***
	 * ����ɫ�����ͱ����
	 */
	public static final String Sql_StandardFormula_InsertColorAuto = "insert into ColorAuto (ColorAutoId,AutoId,InnerColorId,YearFirstUsed,YearLastUsed,CreatedDate,SystemDate) values (?,?,?,?,?,?,?)";

	/***
	 * �����ڲ�ɫ�������
	 */
	public static final String Sql_SCH_InsertInnerColor = "insert into InnerColor (InnerColorId,ColorTypeId,ColorCode,ColorName,YearFirstUsed,YearLastUsed,RGB,CreatedDate,SystemDate,RelationId) values (?,?,?,?,?,?,?,?,?,?)";

	/***
	 * ��������ɫ�������
	 */
	public static final String Sql_SCH_InsertDerivateColor = "insert into DerivateColor (DerivateColorId,InnerParentColorId,InnerColorId,RelationsId,CreatedDate,SystemDate) values (?,?,?,?,?,?)";

	/***
	 * �������������
	 */
	public static final String Sql_SCH_InsertRelations = "insert into Relations (RelationsId,RelationsName,RelationsTypeId,CreatedDate,SystemDate) values (?,?,?,?,?)";

	/***
	 * ������ɫ��������
	 */
	public static final String Sql_StandardFormula_InsertColorGroup = "insert into ColorGroup (ColorGroupId,ColorGroupName,CreatedDate,SystemDate) values (?,?,?,?)";

	/***
	 * ����ɫ�����ɫ�������
	 */
	public static final String Sql_StandardFormula_InsertColorGroup_Color = "insert into ColorGroup_Color (ColorGroupId,ColorId,CreatedDate,SystemDate,ColorGroupColorId) values (?,?,?,?,?)";
	/***
	 * ������ɫ��ͼ�����
	 */
	public static final String Sql_StandardFormula_InsertColorMap = "insert into ColorMap (ColorMap,InnerColorId,Page,Documentation) values (?,?,?,?)";
	
	/////////////////////////////////////////////////////

	//#region �޸����

	/***
	 * �޸ĵ�λ���
	 */
	public static final String Sql_SCH_UpdateUnit = "update UNIT set UnitName=?,UnitPrecision=?,UnitRatio=?,UnitType=? where UnitId=?";
	/***
	 * �޸�ɫĸ�۸����
	 */
	public static final String Sql_SCH_UpdateColorantPrice = "update ColorantPrice set ColorantId=?,ColorGroupId=?,ColorantCode=?,ColorantName=?,ColorantFeatures=?,ColorantDensity=?,ColorantPrice=?,ColorantPriceRate=?,CanSize=?,UnitName=?,CreatedDate=?,SystemDate=?where ColorantPriceId=?";
	/***
	 * �޸Ĺ������
	 */
	public static final String Sql_SCH_UpdateRelationsType = "update RelationsType set RelationsTypeName=?,CreateDate=?,SystemDate=? where RelationsTypeId=?";

	/***
	 * �޸ı��ڲ�ɫ�����
	 */
	public static final String Sql_StandardFormula_UpdateStandardInnerColor = "update InnerColor set ColorTypeId=?,ColorCode=?,ColorName=?,YearFirstUsed=?,YearLastUsed=?,RGB=?,CreatedDate=?,SystemDate=?,RelationId=? where InnerColorId=?";

	/***
	 * �޸�ϵ�����
	 */
	public static final String Sql_SH_UpdateRelations = "update Relations set RelationsName=?,RelationsTypeId=?,CreateDate=?,SystemDate=? where RelationsId=?";

	/***
	 * �޸��������
	 */
	public static final String Sql_CH_UpdateAuto = "update Auto set MasterId=?,AutoName=?,YearFirstUsed=?,YearLastUsed=?,RelationId=?,CreatedDate=?,SystemDate=?where AutoId=?";
	/***
	 * �޸�Ʒ�Ʊ����
	 */
	public static final String Sql_CH_UpdateBrand = "update Brand set BrandName=?,CreateDate=?,SystemDate=?where BrandId=?";

	/***
	 * �޸Ĳ�Ʒ���
	 */
	public static final String Sql_SCH_UpdateProduct = "update Product set ProductCode=?,ProductName=?,CreateDate=?,SystemDate=? where ProductId=?";

	/***
	 * �޸���ɫ�����
	 */
	public static final String Sql_StandardFormula_UpdateColorGroup = "update ColorGroup set ColorGroupName=?,CreateDate=?,SystemDate=? where ColorGroupId=?";

	/***
	 * �޸���ɫ������
	 */
	public static final String Sql_SCH_UpdateColorType = "update ColorType set ColorTypeName=?,CreateDate=?,SystemDate=?where ColorTypeId=?";
	/***
	 * �޸��������������
	 */
	public static final String Sql_SCH_UpdateAutoParts = "update AutoParts set AutoPartsName=?,CreateDate=?,SystemDate=?where AutoPartsId=?";

	/***
	 * �޸�ɫĸ���
	 */
	public static final String Sql_StandardFormula_UpdateColorants = "update Colorants set ColorGroupId=?,ColorantCode=?,ColorantName=?,ColorantFeatures=?,ColorantDensity=?,CreateDate=?,SystemDate=? where ColorantId=?";
	/***
	 * �޸�ɫ���䷽ɫĸ���
	 */
	public static final String Sql_StandardFormula_UpdateColorFormulaColorants = "update ColorFormulaColorants set ColorFormulaId=?,ColorantsId=?,ColorantSequence=?,WeightPercent=?,CreatedDate=?,SystemDate=? where ColorFormulaColorantsId=?";

	/***
	 * �޸ı�׼ɫ��ϵͳ���
	 */
	public static final String Sql_SCH_UpdateStandardColorSystem = "update StandardColorSystem set StandardColorSystemName=?,CreateDate=?,SystemDate=? where StandardColorSystemId=?";

	/***
	 * �޸�ɫ�����
	 */
	public static final String Sql_SCH_UpdateColorants = "update COLORANTS set COLORANTSCODE=?,COLORANTSNAME=?,COLORANTSPRICE=?,COLORANTSPRICERATE=?,SURCHARGE=?,COLORANTSDENSITY=?,COLORANTSPRIORITY=?,RGB=?,PAINTTYPE=?,REMARK=? where COLORANTSID=?";

	/***
	 * �޸���ɫ��ͼ���
	 */
	public static final String Sql_StandardFormula_UpdateColorMap = "update ColorMap set InnerColorId=?,Page=?,Documentation=? whereColorMap=?";

	/***
	 * �޸Ĳ���ɫ����
	 */
	public static final String Sql_StandardFormula_UpdateDerivateColor = "update DerivateColor set InnerParentColorId=?,CreatedDate=?,SystemDate=? where DerivateColorId=?";

	/***
	 * �޸��䷽���
	 */
	public static final String Sql_SCH_UpdateFormula = "update Formula set ProductId=?,FormulaVersion=?,RelationId=?,FormulaVersionDate=?,CreatedDate=?,SystemDate=? ,Source=?where FormulaId=?";

	/***
	 * �޸�ɫ�����ͱ����
	 */
	public static final String Sql_SCH_UpdateColorAuto = "update ColorAuto set YearFirstUsed=?, YearLastUsed=?,CreatedDate=?,SystemDate=?where ColorAutoId=?";

	/***
	 * �޸ı�׼ɫ�����
	 */
	public static final String Sql_SCH_UpdateStandardColor = "update StandardColor set InnerColorId=?,StandardColorCode=?,StandardColorSystemId=?,CreateDate=?,SystemDate=?where StandardColorId=?";
	/***
	 * �޸�ɫ�����ɫ����
	 */
	public static final String Sql_SCH_UpdateColorGroup_Color = "update ColorGroup_Color set ColorGroupId=?,InnerColorId=?,AutoPartsId=?,CreateDate=?,SystemDate=?where ColorAutoPartsId=?";
	/***
	 * �޸�ɫ�����Ͳ�����
	 */
	public static final String Sql_SCH_UpdateColorAutoParts = "update ColorAutoParts set ColorGroupId=?,ColorId=?,CreateDate=?,SystemDate=?where ColorGroupColorId=?";

	/***
	 * �޸�ɫ���䷽���
	 */
	public static final String Sql_StandardFormula_UpdateColorFormula = "update ColorFormula set FormulaId=?,InnerColorId=?,LayerNumber=?,ColorFormulaCode=?,LayerDescription=?,CreateDate=?,SystemDate=?,RelationId=? where ColorFormulaId=?";

	/***
	 * �޸��䷽��Ϣ���
	 */
	public static final String Sql_StandardFormula_UpdateFormulaRemark = "update FormulaRemark set FormulaRemarks=?,ColorFormulaId=?,CreateDate=?,SystemDate=? where FormulaRemarkId=?";

	//#endregion

	//#region ��ѯ���

	/***
	 * ��ȡ���ID
	 */
	public static final String Sql_SelectMaxId = "select ifnull(max({0}),0) from \"{1}\"";
	
	/***
	 * ��ȡ��ʶʵ��Ψһ�Ե��ֵ�
	 */
	public static final String Sql_SelectDict = "select {0},{1} from \"{2}\"";
	public static final String Sql_SelectAutoDict = "select {0},{1},{2} from \"{3}\"";


	/***
	 * ��ȡɫ�����ͱ���
	 */
	public static final String Sql_SCH_SelectColorAuto = "select ColorAutoId,AutoId,InnerColorId,YearFirstUsed,YearLastUsed,CreatedDate,SystemDate from ColorAuto";
	public static final String Sql_SCH_SelectAuto = "select AutoId, MasterId,AutoName from auto";
	public static final String Sql_SCH_SelectBrandProduct = "select BrandProductId,BrandId,ProductId from BrandProduct";

	/***
	 * ��������ɫ���䷽
	 */
	public static final String Sql_CustomerFormula_SelectColorFormula = "select ColorFormulaId,InnerColorId,FormulaId,LayerNumber,ColorFormulaCode,LayerDescription,CreatedDate,SystemDate,RelationId from ColorFormula ";
	public static final String Sql_CustomerFormula_SelectDerivateColor = "select DerivateColorId,InnerParentColorId,InnerColorId,RelationsId from DerivateColor ";
	public static final String Sql_CustomerFormula_Entiy = "select StandardColorId,InnerColorId,StandardColorCode,StandardColorSystemId from StandardColor";
	
	/***
	 * ��������ɫ���䷽ɫĸ��
	 */
	public static final String Sql_CustomerFormula_SelectColorFormulaColorants = "select ColorFormulaColorantsId,ColorFormulaId,ColorantsId from ColorFormulaColorants ";

	//#endregion
}

/***
 * ʵ��Ψһ�Ե��ֵ�
 */
class DictEntity
{
	/***
	 * ID
	 */
	public int Id;
	public int MasterId;
	public int Lay;
    
	/***
	 * ����
	 */
	public String Code;
}

/***
 * ������
 */
class Utility
{
	//#region ��̬����
	public static final String TableFORMULASYSTEM = "FORMULASYSTEM";
	public static final String TableUnit = "Unit";
	public static final String TableColorantPrice = "ColorantPrice";
	public static final String TableColorGroup = "ColorGroup";
	public static final String TableColorGroup_Color = "ColorGroup_Color";
	public static final String TableColorMap = "ColorMap";
	public static final String TableAuto = "Auto";
	public static final String TableColorAuto = "ColorAuto";
	public static final String TableInnerColor = "InnerColor";
	public static final String TableDerivateColor = "DerivateColor";
	public static final String TableRelations = "Relations";
	public static final String TableBrandProduct = "BrandProduct";
	public static final String TableColorAutoParts = "ColorAutoParts";
	public static final String TableStandardColor = "StandardColor";
	public static final String TableColorType = "ColorType";
	public static final String TableColorants = "Colorants";
	public static final String TableRelationsType = "RelationsType";
	public static final String TableBrand = "Brand";
	public static final String TableProduct = "Product";
	public static final String TableAutoParts = "AutoParts";
	public static final String TableStandardColorSystem = "StandardColorSystem";
	public static final String TableColorFormula = "ColorFormula";
	public static final String TableColorFormulaColorants = "ColorFormulaColorants";
	public static final String TableFormula = "Formula";
	public static final String TableFormulaRemark = "FormulaRemark";
	public static final String TableAutoAdd = "TableAutoAdd";
	public static boolean isExists = false;
	public static boolean isSmallExists = false;

	//#endregion

	// #region ����

	/***
	 * ��ȡ��׼�䷽���ݿ�
	 * 
	 * @param context
	 *            ����������
	 * @return ��׼�䷽���ݿ�
	 */
	public static SQLiteDatabase getSantintStandardDatabase(Context context)
	{
//		String path = context.getDatabasePath("SANTINTSTANDARD.DB").getPath();
		String path =  android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/data"+ "/SANTINTSTANDARD.DB";
//		String path =  android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SANTINTSTANDARD.DB";
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
		return db;
	}
	public static SQLiteDatabase getSantintMainDatabase(Context context)
	{
//		String path = context.getDatabasePath("SANTINTSTANDARD.DB").getPath();
		String path =  android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/data"+ "/SANTINTMAIN.DB";
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
		return db;
	}

	/***
	 * ��ȡ�ͻ��䷽���ݿ�
	 * 
	 * @param context
	 *            ����������
	 * @return �ͻ��䷽���ݿ�
	 */
	public static SQLiteDatabase getSantintCustomerDatabase(Context context)
	{
		String path = context.getDatabasePath("SANTINTCUSTOMER.DB").getPath();
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
		return db;
	}

	/***
	 * ��ȡ��ʷ�䷽���ݿ�
	 * 
	 * @param context
	 *            ����������
	 * @return ��ʷ�䷽���ݿ�
	 */
	public static SQLiteDatabase getSantintHistoryDatabase(Context context)
	{
		String path = context.getDatabasePath("SANTINTDISPENSED.DB").getPath();
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
		return db;
	}

	/***
	 * ��ȡ���ID
	 * @param list ʵ�弯��
	 */
	public static int MaxId(List<DictEntity> list)
	{
		return MyLinq.Max(list, new Comparator<DictEntity>()
		{
			@Override
			public int compare(DictEntity dict1, DictEntity dict2)
			{
				return dict1.Id - dict2.Id;
			}
		}).Id;
	}

	/***
	 * ��ȡ���ID
	 * @param list ʵ�弯��
	 */
	public static int PMaxId(List<ColorAuto> list)
	{
		return MyLinq.Max(list, new Comparator<ColorAuto>()
		{
			@Override
			public int compare(ColorAuto pf1, ColorAuto pf2)
			{
				return pf1.getColorAutoId() - pf2.getColorAutoId();
			}
		}).getColorAutoId();
	}
	
	/***
	 * ��ȡ���ID
	 * @param list ʵ�弯��
	 */
	public static int CfCaxId(List<ColorFormulaColorants> list)
	{
		return MyLinq.Max(list, new Comparator<ColorFormulaColorants>()
				{
			@Override
			public int compare(ColorFormulaColorants pf1, ColorFormulaColorants pf2)
			{
				return pf1.getColorFormulaColorantsId() - pf2.getColorFormulaColorantsId();
			}
				}).getColorFormulaColorantsId();
	}
	/***
	 * ��ȡ���ID
	 * @param list ʵ�弯��
	 */
	public static int CFaxId(List<ColorFormula> list)
	{
		return MyLinq.Max(list, new Comparator<ColorFormula>()
				{
			@Override
			public int compare(ColorFormula pf1, ColorFormula pf2)
			{
				return pf1.getColorFormulaId() - pf2.getColorFormulaId();
			}
				}).getColorFormulaId();
	}
	/**
	 * ��ȡ���ID
	 * @param list
	 * @return
	 */
	public static int DCaxId(List<DerivateColor> list)
	{
		return MyLinq.Max(list, new Comparator<DerivateColor>()
			{
			@Override
			public int compare(DerivateColor pf1, DerivateColor pf2)
			{
				return pf1.getDerivateColorId() - pf2.getDerivateColorId();
			}
			}).getDerivateColorId();
	}
	/**
	 * ��ȡ���ID
	 * @param list
	 * @return
	 */
	public static int atMaxId(List<Auto> list)
	{
		return MyLinq.Max(list, new Comparator<Auto>()
			{
			@Override
			public int compare(Auto pf1, Auto pf2)
			{
				return pf1.getAutoId() - pf2.getAutoId();
			}
			}).getAutoId();
	}

	/***
	 * ��ȡ���������ĵ�һ��ʵ��
	 * @param list ʵ�弯��
	 * @param code ����
	 * @param isIgnoreCase �Ƿ���Դ�Сд
	 * @return ʵ��
	 */
	public static DictEntity First(List<DictEntity> list, final String code, final boolean isIgnoreCase)
	{
		return MyLinq.First(list, new Predicate1<DictEntity>()
		{
			@Override
			public boolean evaluate(DictEntity dict)
			{
				return !isIgnoreCase ? dict.Code.equals(code) : dict.Code.equalsIgnoreCase(code);
			}
		});
	}
	//�ж�Formula���ӵ���ʱ���޶��������ƶ�
	public static DictEntity DCFirst(List<DictEntity> list, final String code, final int bid, final boolean isIgnoreCase)
	{
		return MyLinq.First(list, new Predicate1<DictEntity>()
		{
			@Override
			public boolean evaluate(DictEntity dict)
			{
				return (!isIgnoreCase ? dict.Code.equals(code) : dict.Code.equalsIgnoreCase(code))&&(dict.MasterId==bid);
			}
		});
	}
	public static DictEntity DCColorFormulaFirst(List<DictEntity> list, final int lay, final int iid,final int fid)
	{
		return MyLinq.First(list, new Predicate1<DictEntity>()
			{
			@Override
			public boolean evaluate(DictEntity dict)
			{
				return (dict.Lay==lay)&&(dict.MasterId==fid)&&(dict.Id==iid);
			}
			});
	}
	/***
	 * ��ȡ���������ĵ�һ��ʵ��
	 * @param list ʵ�弯��
	 */
	public static DerivateColor DCFirst(List<DerivateColor> list, final int pid, final int bid)
	{
		return MyLinq.First(list, new Predicate1<DerivateColor>()
		{
			@Override
			public boolean evaluate(DerivateColor pf)
			{
				return pf.getDerivateColorId() == pid && pf.getInnerColorId() == bid;
			}
		});
	}
	public static StandardColor DCFirst(List<StandardColor> list, final int iid, final String code)
	{
		return MyLinq.First(list, new Predicate1<StandardColor>()
			{
			@Override
			public boolean evaluate(StandardColor pf)
			{
				return pf.getInnerColorId() == iid && pf.getStandardColorCode().equals(code);
			}
			});
	}
	
	/**
	 * 
	 * @param list
	 * @param pid
	 * @param bid
	 * @return
	 */
	public static ColorAuto PFirst(List<ColorAuto> list, final int pid, final int bid)
	{
		return MyLinq.First(list, new Predicate1<ColorAuto>()
			{
			@Override
			public boolean evaluate(ColorAuto pf)
			{
				return pf.getAutoId() == pid && pf.getInnerColorId() == bid;
			}
			});
	}
	/**
	 * 
	 * @param list
	 * @param bid
	 * @param pid
	 * @return
	 */
	public static BrandProduct PBFirst(List<BrandProduct> list, final int bid, final int pid)
	{
		return MyLinq.First(list, new Predicate1<BrandProduct>()
			{
			@Override
			public boolean evaluate(BrandProduct pf)
			{
				return pf.getBrandId() == bid && pf.getProductId() == pid;
			}
			});
	}
	/**
	 * 
	 * @param list
	 * @param bid
	 * @param pid
	 * @return
	 */
	public static Auto AutoFirst(List<Auto> list, final int mid, final String aName)
	{
		return MyLinq.First(list, new Predicate1<Auto>()
			{
			@Override
			public boolean evaluate(Auto pf)
			{
				return pf.getMasterId() == mid && pf.getAutoName().equalsIgnoreCase(aName);
			}
			});
	}
	public static Auto AutoFirsts(List<Auto> list,  final String aName)
	{
		return MyLinq.First(list, new Predicate1<Auto>()
			{
			@Override
			public boolean evaluate(Auto pf)
			{
				return pf.getAutoName().equalsIgnoreCase(aName);
			}
			});
	}
	
	/***
	 * ��ȡ���������ĵ�һ��ʵ��
	 * @param list ʵ�弯��
	 */
	public static ColorFormulaColorants CFCirst(List<ColorFormulaColorants> list, final int cfid, final int cid)
	{
		return MyLinq.First(list, new Predicate1<ColorFormulaColorants>()
				{
			@Override
			public boolean evaluate(ColorFormulaColorants pf)
			{
				return pf.getColorFormulaId() == cfid && pf.getColorantsId() == cid;
			}
				});
	}
	/***
	 * ��ȡ���������ĵ�һ��ʵ��
	 * @param list ʵ�弯��
	 */
	public static ColorFormula CFirst(List<ColorFormula> list, final int fid, final int iid)
	{
		return MyLinq.First(list, new Predicate1<ColorFormula>()
				{
			@Override
			public boolean evaluate(ColorFormula pf)
			{
				return pf.getFormulaId() == fid && pf.getInnerColorId() == iid;
			}
				});
	}

	/***
	 * ��ȡ���ID
	 * @param connection ���ݿ�����
	 * @param tableName ����
	 * @param columnName ����
	 * @return ���ID
	 */
	public static int GetMaxId(SQLiteDatabase connection, String tableName, String columnName)
	{
		int maxid = 0;

		Cursor idcCursor = connection.rawQuery(MessageFormat.format(SqlString.Sql_SelectMaxId, columnName, tableName), new String[] {});
		if (idcCursor.moveToFirst())
		{
			maxid = idcCursor.getInt(0);
		}
		idcCursor.close();

		return maxid;
	}

	/***
	 * ��ȡʵ��Ψһ�Ե��ֵ伯��
	 * @param connection ���ݿ�����
	 * @param tableName ����
	 * @param idcol ID����
	 * @param codecol Code����
	 * @return ʵ��Ψһ�Ե��ֵ伯��
	 */
	public static List<DictEntity> GetDictEntitys(SQLiteDatabase connection, String tableName, String idcol, String codecol)
	{
		List<DictEntity> list = new ArrayList<DictEntity>();

		Cursor dictCursor = connection.rawQuery(MessageFormat.format(SqlString.Sql_SelectDict, idcol, codecol, tableName), new String[] {});
		while (dictCursor.moveToNext())
		{
			DictEntity dict = new DictEntity();
			dict.Id = dictCursor.getInt(0);
			dict.Code = dictCursor.getString(1);
			list.add(dict);
		}
		dictCursor.close();

		return list;
	}
	public static List<DictEntity> GetAutoDictEntitys(SQLiteDatabase connection, String tableName, String idcol,String mastId, String codecol)
	{
		List<DictEntity> list = new ArrayList<DictEntity>();
		
		Cursor dictCursor = connection.rawQuery(MessageFormat.format(SqlString.Sql_SelectAutoDict, idcol,mastId, codecol, tableName), new String[] {});
		while (dictCursor.moveToNext())
		{
			DictEntity dict = new DictEntity();
			dict.Id = dictCursor.getInt(0);
			dict.MasterId=dictCursor.getInt(1);
			dict.Code = dictCursor.getString(2);
			list.add(dict);
		}
		dictCursor.close();
		
		return list;
	}
	public static List<DictEntity> GetColorFormulaDictEntitys(SQLiteDatabase connection, String tableName, String idcol,String mastId, String codecol)
	{
		List<DictEntity> list = new ArrayList<DictEntity>();
		
		Cursor dictCursor = connection.rawQuery(MessageFormat.format(SqlString.Sql_SelectAutoDict, idcol,mastId, codecol, tableName), new String[] {});
		while (dictCursor.moveToNext())
		{
			DictEntity dict = new DictEntity();
			dict.Id = dictCursor.getInt(0);
			dict.MasterId=dictCursor.getInt(1);
			dict.Lay = dictCursor.getInt(2);
			list.add(dict);
		}
		dictCursor.close();
		
		return list;
	}

	/***
	 * ��ȡɫ�����ͱ�ʵ�弯��
	 * @param connection ���ݿ�����
	 * @return Ԥװ��Ϣʵ�弯��
	 */
	public static List<ColorAuto> GetColorAutoEntitys(SQLiteDatabase connection)
	{
		List<ColorAuto> list = new ArrayList<ColorAuto>();

		Cursor formulaCursor = connection.rawQuery(SqlString.Sql_SCH_SelectColorAuto, new String[] {});
		while (formulaCursor.moveToNext())
		{
			ColorAuto pf = new ColorAuto();
			pf.setColorAutoId(formulaCursor.getInt(0));
			pf.setAutoId(formulaCursor.getInt(1));
			pf.setInnerColorId(formulaCursor.getInt(2));
			list.add(pf);
		}
		formulaCursor.close();

		return list;
	}
	/***
	 * ��ȡɫ�����ͱ�ʵ�弯��
	 * @param connection ���ݿ�����
	 * @return Ԥװ��Ϣʵ�弯��
	 */
	public static List<Auto> GetAutoEntitys(SQLiteDatabase connection)
	{
		List<Auto> list = new ArrayList<Auto>();
		
		Cursor formulaCursor = connection.rawQuery(SqlString.Sql_SCH_SelectAuto, new String[] {});
		while (formulaCursor.moveToNext())
		{
			Auto pf = new Auto();
			pf.setAutoId(formulaCursor.getInt(0));
			pf.setMasterId(formulaCursor.getInt(1));
			pf.setAutoName(formulaCursor.getString(2));
			list.add(pf);
		}
		formulaCursor.close();
		
		return list;
	}
	public static List<BrandProduct> GetBrandProductEntitys(SQLiteDatabase connection)
	{
		List<BrandProduct> list = new ArrayList<BrandProduct>();
		
		Cursor formulaCursor = connection.rawQuery(SqlString.Sql_SCH_SelectBrandProduct, new String[] {});
		while (formulaCursor.moveToNext())
		{
			BrandProduct pf = new BrandProduct();
			pf.setBrandProductId(formulaCursor.getInt(0));
			pf.setBrandId(formulaCursor.getInt(1));
			pf.setProductId(formulaCursor.getInt(2));
			list.add(pf);
		}
		formulaCursor.close();
		
		return list;
	}
	/***
	 * ��ȡɫ���䷽ʵ�弯��
	 * @param connection ���ݿ�����
	 * @return Ԥװ��Ϣʵ�弯��
	 */
	public static List<ColorFormula> GetColorFormulaEntitys(SQLiteDatabase connection)
	{
		List<ColorFormula> list = new ArrayList<ColorFormula>();
		
		Cursor formulaCursor = connection.rawQuery(SqlString.Sql_CustomerFormula_SelectColorFormula, new String[] {});
		while (formulaCursor.moveToNext())
		{
			ColorFormula pf = new ColorFormula();
			pf.setColorFormulaId(formulaCursor.getInt(0));
			pf.setInnerColorId(formulaCursor.getInt(1));
			pf.setFormulaId(formulaCursor.getInt(2));
			list.add(pf);
		}
		formulaCursor.close();
		
		return list;
	}
	public static List<DerivateColor> GetDerivateColorEntitys(SQLiteDatabase connection)
	{
		List<DerivateColor> list = new ArrayList<DerivateColor>();
		
		Cursor formulaCursor = connection.rawQuery(SqlString.Sql_CustomerFormula_SelectDerivateColor, new String[] {});
		while (formulaCursor.moveToNext())
		{
			DerivateColor dc = new DerivateColor();
			dc.setDerivateColorId(formulaCursor.getInt(0));
			dc.setInnerParentColorId(formulaCursor.getInt(1));
			dc.setInnerColorId(formulaCursor.getInt(2));
			list.add(dc);
		}
		formulaCursor.close();
		
		return list;
	}
	public static List<StandardColor> getEntitys( SQLiteDatabase connection){
       List<StandardColor> list = new ArrayList<StandardColor>();
		
		Cursor formulaCursor = connection.rawQuery(SqlString.Sql_CustomerFormula_Entiy, new String[] {});
		while (formulaCursor.moveToNext())
		{
			StandardColor dc = new StandardColor();
			dc.setStandardColorId(formulaCursor.getInt(0));
			dc.setInnerColorId(formulaCursor.getInt(1));
			dc.setStandardColorCode(formulaCursor.getString(2));
			dc.setStandardColorSystemId(formulaCursor.getInt(3));
			list.add(dc);
		}
		formulaCursor.close();
		return list;
		
	}
	/***
	 * ��ȡɫ���䷽ɫĸ��ʵ�弯��
	 * @param connection ���ݿ�����
	 * @return Ԥװ��Ϣʵ�弯��
	 */
	public static List<ColorFormulaColorants> GetColorFormulaColorantsEntitys(SQLiteDatabase connection)
	{
		List<ColorFormulaColorants> list = new ArrayList<ColorFormulaColorants>();
		
		Cursor formulaCursor = connection.rawQuery(SqlString.Sql_CustomerFormula_SelectColorFormulaColorants, new String[] {});
		while (formulaCursor.moveToNext())
		{
			ColorFormulaColorants pf = new ColorFormulaColorants();
			pf.setColorFormulaColorantsId(formulaCursor.getInt(0));
			pf.setColorFormulaId(formulaCursor.getInt(1));
			pf.setColorantsId(formulaCursor.getInt(2));
			list.add(pf);
		}
		formulaCursor.close();
		
		return list;
	}

	/***
	 * д��־
	 * 
	 * @param className
	 *            ����
	 * @param functionName
	 *            ������
	 * @param ex
	 *            �쳣
	 */
	public static void WriteLog(String className, String functionName, Exception ex)
	{
//		Logger.SoftWareName = "ColorExpert3 WindowsApp";
//		Logger.ModelName = "Tint";
//		Logger.NameSpace = "SanTint.ColorExpert.WindowsApp.FormulaImportExport";
//		Logger.ClassName = className;
//		Logger.FunctionName = functionName;
//		Logger.Write(ex);
	}
	public static Auto GetAutoEntitys(SQLiteDatabase connection,String autoName)
	{
		String sql="select AutoId, MasterId,AutoName from auto where AutoName='"+""+autoName+"'";
		Auto pf=null;
		Cursor formulaCursor = connection.rawQuery(sql, new String[] {});
		while (formulaCursor.moveToNext())
		{
			pf = new Auto();
			pf.setAutoId(formulaCursor.getInt(0));
			pf.setMasterId(formulaCursor.getInt(1));
			pf.setAutoName(formulaCursor.getString(2));
		}
		formulaCursor.close();
		
		return pf;
	}
	// #endregion
}
