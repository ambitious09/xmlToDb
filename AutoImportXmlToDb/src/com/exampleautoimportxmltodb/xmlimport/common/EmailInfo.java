package com.exampleautoimportxmltodb.xmlimport.common;

import java.util.Date;
import java.util.List;

/***
 * �����ʼ���Ϣ
 *
 */
public class EmailInfo {
	
	/***
	 * �ʼ�����
	 */
	public String Subject;
	
	/***
	 * �����ʼ���ַ
	 */
	public Date ReceivedDate;
	
	/***
	 * �ʼ�����
	 */
	public String BodyContents;
	
	/***
	 * ��������
	 */
	public boolean ContainAttach;
	
	/***
	 * ��������
	 */
	public List<EmailAttachment> AttachContents;
}
