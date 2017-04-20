package com.example.autoimportxmltodb.modle;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class LabelAttributes implements Serializable
{
	/***
	 * Ʒ��
	 */
	public String Brand;
	
	/***
	 * ϵ�в�Ʒ
	 */
	public String Product;
		
	/***
	 * ������
	 */
	public String Manufacture;
	
	/***
	 * ����
	 */
	public String Auto;
	
	/***
	 * ��ɫ����
	 */
	public String ColorDescription;
	
	/***
	 * ��ɫ����
	 */
	public String ColorType;
	
	/***
	 * �ڲ�ɫ�ű���
	 */
	public String ColorCode;
	
	/***
	 * ɫ������
	 */
	public String ColorName;
		
	/***
	 * ��ɫע��
	 */
	public String ColorNotes;
	
	/***
	 * �䷽����
	 */
	public String ColorDate;
	
	/***
	 * ��ɫ����
	 */
	public String MixDate;
	
	/***
	 * ��ӡ����
	 */
	public String PrintDate;
	
	/***
	 * ��ɫ��
	 */
	public String MixAmount;
	
	/**
	 * ��
	 */
	public String Layer;
	
	/***
	 * ɫĸ�б�
	 */
	public List<ColorantInfo> ColorantList;
}

