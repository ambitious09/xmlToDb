package com.example.autoimportxmltodb.commmen;

/***
 * 版本信息类
 * 
 * 
 */
public class Version
{
	private String versionString = null;// 版本号字符串
	private int[] versionEntys;// 版本号节点数组

	/***
	 * 构造函数
	 * 
	 * @param version
	 *            版本号字符串
	 */
	public Version(String version)
	{
		setVersion(version);
	}

	/***
	 * 比较版本大小
	 * 
	 * @param version
	 *            版本
	 * @return 比较结果
	 */
	public int compareTo(Version version)
	{
		int result = 0;

		int[] entys1 = version.getVersionEntys();

		for (int i = 0; i < 4; i++)
		{
			if (versionEntys[i] > entys1[i])
			{
				result = 1;
				break;
			}
			else if (versionEntys[i] < entys1[i])
			{
				result = -1;
				break;
			}
		}

		return result;
	}

	/***
	 * 获取版本号字符串
	 * 
	 * @return 版本号字符串
	 */
	public String getVersionString()
	{
		return versionString;
	}

	/***
	 * 获取版本号节点数组
	 * 
	 * @return 版本号节点数组
	 */
	public int[] getVersionEntys()
	{
		return versionEntys;
	}

	/***
	 * 设置版本号节点数组
	 * 
	 * @param versionstr
	 *            版本号字符串
	 */
	private void setVersion(String versionstr)
	{
		versionEntys = new int[4];

		String[] strs = versionstr.split("\\.");
		for (int i = 0; i < 4; i++)
		{
			if (i < strs.length)
			{
				versionEntys[i] = Integer.parseInt(strs[i]);
			}
			else
			{
				versionEntys[i] = 0;
			}
		}

		versionString = Common.Join(".", versionEntys[0], versionEntys[1], versionEntys[2], versionEntys[3]);
	}
}
