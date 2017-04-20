package com.example.autoimportxmltodb.modle;

import java.io.Serializable;

import android.content.Context;


/***
 * ��֤ʵ��
 */
public class Authenticate implements Serializable
{
	private static final long serialVersionUID = 7425257751113997894L;
	
	
	public Context DbContext;
	
	/***
	 * ��֤��ʶ
	 */
	public int UserUniqueCode;

	/***
	 * �ͻ���Ψһ��ʶ
	 */
	public int ClientUniqueCode;

	/***
	 * �䷽��ϵ
	 */
	public int FormulaSystemId;

	/***
	 * ���ݷ��ʳ�������
	 */
	public String DataAccessAssembly;
	/// <summary>
    /// ���ݿ����·��
    /// </summary>
    public String DatabasePath;

	/***
	 * �۸����
	 */
    public PricingStrategy PricingStrategy;

	/***
	 * �ͻ���������Ϣ[��Χ�����С��������]
	 */
	public String AreaCode;

}
