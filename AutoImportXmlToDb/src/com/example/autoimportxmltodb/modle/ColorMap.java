package com.example.autoimportxmltodb.modle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

/***********************************************************************
 * Module:  ColorMap.java
 * Author:  SUQIANG
 * Purpose: Defines the Class ColorMap
 ***********************************************************************/

/**
 * ��ɫ��ͼ��
 * 
 * 
 */
@DatabaseTable(tableName = "ColorMap")
public class ColorMap extends BaseDaoEnabled<Product, Integer> {
	@DatabaseField
	private int ColorMap;

	@DatabaseField
	private int InnerColorId;;

	@DatabaseField
	private String Page;

	@DatabaseField
	private String Documentation;

	/**
	 * ��ɫ��ͼ���
	 * 
	 * @return
	 */
	public int getColorMap() {
		return ColorMap;
	}

	public void setColorMap(int colorMap) {
		ColorMap = colorMap;
	}

	/**
	 * ɫ�����
	 * 
	 * @return
	 */
	public int getInnerColorId() {
		return InnerColorId;
	}

	public void setInnerColorId(int innerColorId) {
		InnerColorId = innerColorId;
	}

	/**
	 * ҳ��
	 * 
	 * @return
	 */
	public String getPage() {
		return Page;
	}

	public void setPage(String page) {
		Page = page;
	}

	/**
	 * �ĵ�
	 * 
	 * @return
	 */
	public String getDocumentation() {
		return Documentation;
	}

	public void setDocumentation(String documentation) {
		Documentation = documentation;
	}

}