package com.exampleautoimportxmltodb.xmlimport.common;

import java.util.Date;
import java.util.List;

/***
 * 电子邮件信息
 *
 */
public class EmailInfo {
	
	/***
	 * 邮件主题
	 */
	public String Subject;
	
	/***
	 * 接收邮件地址
	 */
	public Date ReceivedDate;
	
	/***
	 * 邮件正文
	 */
	public String BodyContents;
	
	/***
	 * 包含附件
	 */
	public boolean ContainAttach;
	
	/***
	 * 附件内容
	 */
	public List<EmailAttachment> AttachContents;
}
