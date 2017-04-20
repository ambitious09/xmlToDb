package com.example.autoimportxmltodb.commmen;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;

public class CommonException extends Exception
{
	/**
	 * ID
	 */
	private static final long serialVersionUID = -5165371526757663833L;

	private String codeException = "000000";// �쳣����
	private String codeExceptionKind = "0000";// �쳣���ʹ���
	private ExceptionLevel levelException = ExceptionLevel.Information;// �쳣�ȼ�
	private CategoryLog levelLog = CategoryLog.Error;// ��־�ȼ�

	public String getCodeException()
	{
		return codeException;
	}

	public String getCodeExceptionKind()
	{
		return codeExceptionKind;
	}

	public ExceptionLevel getLevelException()
	{
		return levelException;
	}

	public CategoryLog getLevelLog()
	{
		return levelLog;
	}

	/***
	 * �Ѷ����쳣��
	 * 
	 * @param levelEx
	 *            ����ȼ�
	 * @param codeEx
	 *            �쳣����
	 * @param logLevel
	 *            ��־�ȼ�
	 * @param innerEx
	 *            �ڲ��쳣
	 */
	public CommonException(ExceptionLevel levelEx, String codeEx, CategoryLog logLevel, Exception innerEx)
	{
		super(innerEx.getMessage(), innerEx);

		levelException = levelEx;
		codeException = codeEx;
		levelLog = logLevel;
		AnalyseException(innerEx);
	}

	/***
	 * �����쳣�������
	 * 
	 * @param ex
	 */
	private void AnalyseException(Exception ex)
	{
		if (ex instanceof IOException)// 01
		{
			if (ex instanceof FileNotFoundException)
			{
				codeExceptionKind = "0101";
			}
			else if (ex instanceof InvalidClassException)
			{
				codeExceptionKind = "0102";
			}
			else if (ex instanceof EOFException)
			{
				codeExceptionKind = "0103";
			}
		}
	}
}

