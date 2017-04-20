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
 * 颜色地图表
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
	 * 颜色地图标号
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
	 * 色卡编号
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
	 * 页码
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
	 * 文档
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