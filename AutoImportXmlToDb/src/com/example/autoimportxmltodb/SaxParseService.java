package com.example.autoimportxmltodb;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/***
 * XML解析类
 * 
 * 
 */
class SaxParseService extends DefaultHandler
{
	private boolean preTag = false;// 找到节点标记
	private boolean isInterrupt = false;// 找到节点后是否是否中断
	private int bufferLenth = 3000;// 数据缓冲区大小
	private String rootName = null;// 根节点名称
	private String elementName = null;// 上一个实体节点名称
	private String currentName = null;// 当前实体节点名称
	private String nodeName = null;// 内部节点名称
	private Object element = null;// 节点实体
	private Hashtable<String, String> edict = null;// 节点键值对
	private Hashtable<String, ReaderParam> hashtable = null;// 读取器参数字典
	private List<Object> dataList = null;// 实体集合

	/***
	 * 注册读取器参数
	 * 
	 * @param key
	 *            节点类型名
	 * @param param
	 *            读取器参数
	 */
	public void regix(String key, ReaderParam param)
	{
		if (hashtable == null)
		{
			hashtable = new Hashtable<String, ReaderParam>(15);
		}
		hashtable.put(key, param);
	}

	/***
	 * 开始遍历读取XmL文件
	 * 
	 * @param file
	 *            XmL文件
	 * @param isInterrupt
	 *            找到节点后是否是否中断
	 * @throws Exception
	 */
	public void start(File file, boolean isInterrupt) throws Exception
	{
		this.isInterrupt = isInterrupt;

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(new FileInputStream(file), this);
	}

	/***
	 * 获取节点实体
	 * 
	 * @return 节点实体
	 */
	public Object getElement()
	{
		return element;
	}

	/***
	 * 检测中断是否成功
	 * 
	 * @return 中断是否成功
	 */
	public boolean checkInterrupt()
	{
		return !isInterrupt;
	}

	/***
	 * 开始读取Document事件
	 */
	@Override
	public void startDocument() throws SAXException
	{
		dataList = new ArrayList<Object>(bufferLenth);
	}

	/***
	 * 开始读取Element节点事件
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (preTag)
		{
			nodeName = qName;
			edict.put(nodeName, "");
		}
		else if (rootName == null)
		{
			rootName = qName;
		}

		if (hashtable != null && hashtable.containsKey(qName))
		{
			currentName = qName;
			edict = new Hashtable<String, String>(25);

			preTag = true;
		}
	}

	/***
	 * 结束读取Element节点事件
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (!isInterrupt && dataList != null && dataList.size() > 0 && (dataList.size() >= bufferLenth || !elementName.equals(currentName) || qName.equals(rootName)))
		{
			IDataImport dt = hashtable.get(elementName).DataImport;
			dt.importData(dataList);

			dataList.clear();
		}

		if (hashtable != null && hashtable.containsKey(qName))
		{
			IObjectTranslate ot = hashtable.get(qName).ObjectTranslate;

			if (isInterrupt)
			{
				element = ot.parse(edict);
				isInterrupt = false;

				throw (new SAXException());
			}

			dataList.add(ot.parse(edict));
			elementName = currentName;

			preTag = false;
		}

		if (preTag)
		{
			nodeName = null;
		}
	}

	/***
	 * 读取节点内容事件
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		if (preTag && nodeName != null)
		{
			String content = new String(ch, start, length);
			if (!content.equals("\n"))
			{
				edict.put(nodeName, edict.get(nodeName) + content);
			}
		}
	}
}

/***
 * Eliment实体转换器
 * 
 * @author 刘红杰
 * 
 */
interface IObjectTranslate
{
	Object parse(Hashtable<String, String> arge);
}

/***
 * 默认转换器
 * 
 * @author 刘红杰
 * 
 */
class DefaultObjectTranslate implements IObjectTranslate
{
	@Override
	public Hashtable<String, String> parse(Hashtable<String, String> arge)
	{
		return arge;
	}
}

/***
 * 导入数据接口
 * 
 * @author 刘红杰
 * 
 */
interface IDataImport
{
	void importData(List<Object> list);
}

/***
 * 读取XML参数
 * 
 * @author 刘红杰
 * 
 */
class ReaderParam
{
	/***
	 * Eliment实体转换器
	 */
	public IObjectTranslate ObjectTranslate;

	/***
	 * 导入数据接口
	 */
	public IDataImport DataImport;
}