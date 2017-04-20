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

	private String codeException = "000000";// 异常代码
	private String codeExceptionKind = "0000";// 异常类型代码
	private ExceptionLevel levelException = ExceptionLevel.Information;// 异常等级
	private CategoryLog levelLog = CategoryLog.Error;// 日志等级

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
	 * 已定义异常类
	 * 
	 * @param levelEx
	 *            错误等级
	 * @param codeEx
	 *            异常代码
	 * @param logLevel
	 *            日志等级
	 * @param innerEx
	 *            内部异常
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
	 * 分析异常种类代码
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

