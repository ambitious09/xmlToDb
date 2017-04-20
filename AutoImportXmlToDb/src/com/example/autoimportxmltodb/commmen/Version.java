package com.example.autoimportxmltodb.commmen;

/***
 * �汾��Ϣ��
 * 
 * 
 */
public class Version
{
	private String versionString = null;// �汾���ַ���
	private int[] versionEntys;// �汾�Žڵ�����

	/***
	 * ���캯��
	 * 
	 * @param version
	 *            �汾���ַ���
	 */
	public Version(String version)
	{
		setVersion(version);
	}

	/***
	 * �Ƚϰ汾��С
	 * 
	 * @param version
	 *            �汾
	 * @return �ȽϽ��
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
	 * ��ȡ�汾���ַ���
	 * 
	 * @return �汾���ַ���
	 */
	public String getVersionString()
	{
		return versionString;
	}

	/***
	 * ��ȡ�汾�Žڵ�����
	 * 
	 * @return �汾�Žڵ�����
	 */
	public int[] getVersionEntys()
	{
		return versionEntys;
	}

	/***
	 * ���ð汾�Žڵ�����
	 * 
	 * @param versionstr
	 *            �汾���ַ���
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
