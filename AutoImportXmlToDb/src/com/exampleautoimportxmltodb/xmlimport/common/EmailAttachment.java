package com.exampleautoimportxmltodb.xmlimport.common;

import java.io.InputStream;

/***
 * 邮件附件实体
 * 
 * 
 *
 */
public class EmailAttachment {

	/***
	 * 附件名称
	 */
	public String AttachmentName;
	
	/***
	 * 附件文件完整路径
	 */
	public String FullAttachmentName;
	
	/***
	 * 附件内容
	 */
	public InputStream AttachmentContent;
}
