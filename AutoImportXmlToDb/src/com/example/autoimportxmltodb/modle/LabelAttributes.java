package com.example.autoimportxmltodb.modle;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class LabelAttributes implements Serializable
{
	/***
	 * 品牌
	 */
	public String Brand;
	
	/***
	 * 系列产品
	 */
	public String Product;
		
	/***
	 * 制造商
	 */
	public String Manufacture;
	
	/***
	 * 车型
	 */
	public String Auto;
	
	/***
	 * 颜色描述
	 */
	public String ColorDescription;
	
	/***
	 * 颜色类型
	 */
	public String ColorType;
	
	/***
	 * 内部色号编码
	 */
	public String ColorCode;
	
	/***
	 * 色号名称
	 */
	public String ColorName;
		
	/***
	 * 颜色注释
	 */
	public String ColorNotes;
	
	/***
	 * 配方日期
	 */
	public String ColorDate;
	
	/***
	 * 调色日期
	 */
	public String MixDate;
	
	/***
	 * 打印日期
	 */
	public String PrintDate;
	
	/***
	 * 调色量
	 */
	public String MixAmount;
	
	/**
	 * 层
	 */
	public String Layer;
	
	/***
	 * 色母列表
	 */
	public List<ColorantInfo> ColorantList;
}

