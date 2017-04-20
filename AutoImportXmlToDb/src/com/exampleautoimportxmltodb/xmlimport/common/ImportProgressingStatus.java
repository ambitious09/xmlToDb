 
package  com.exampleautoimportxmltodb.xmlimport.common;

/***
 * �����䷽����״̬
 */
public enum ImportProgressingStatus
{
	/***
	 * �����䷽����
	 */
	LoadData(1),

	/***
	 * �����������
	 */
	ImportBaseData(2),

	/***
	 * �����䷽����
	 */
	ImportFormula(3),

	/***
	 * ����Ƿ�����
	 */
	ClearData(4);

	private int _value;

	private ImportProgressingStatus(int value)
	{
		_value = value;
	}

	public int value()
	{
		return _value;
	}
}
