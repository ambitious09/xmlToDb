 
package  com.exampleautoimportxmltodb.xmlimport.common;

/***
 * 导入配方进度状态
 */
public enum ImportProgressingStatus
{
	/***
	 * 加载配方数据
	 */
	LoadData(1),

	/***
	 * 导入基础数据
	 */
	ImportBaseData(2),

	/***
	 * 导入配方数据
	 */
	ImportFormula(3),

	/***
	 * 清理非法数据
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
