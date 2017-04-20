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
 * XML������
 * 
 * 
 */
class SaxParseService extends DefaultHandler
{
	private boolean preTag = false;// �ҵ��ڵ���
	private boolean isInterrupt = false;// �ҵ��ڵ���Ƿ��Ƿ��ж�
	private int bufferLenth = 3000;// ���ݻ�������С
	private String rootName = null;// ���ڵ�����
	private String elementName = null;// ��һ��ʵ��ڵ�����
	private String currentName = null;// ��ǰʵ��ڵ�����
	private String nodeName = null;// �ڲ��ڵ�����
	private Object element = null;// �ڵ�ʵ��
	private Hashtable<String, String> edict = null;// �ڵ��ֵ��
	private Hashtable<String, ReaderParam> hashtable = null;// ��ȡ�������ֵ�
	private List<Object> dataList = null;// ʵ�弯��

	/***
	 * ע���ȡ������
	 * 
	 * @param key
	 *            �ڵ�������
	 * @param param
	 *            ��ȡ������
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
	 * ��ʼ������ȡXmL�ļ�
	 * 
	 * @param file
	 *            XmL�ļ�
	 * @param isInterrupt
	 *            �ҵ��ڵ���Ƿ��Ƿ��ж�
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
	 * ��ȡ�ڵ�ʵ��
	 * 
	 * @return �ڵ�ʵ��
	 */
	public Object getElement()
	{
		return element;
	}

	/***
	 * ����ж��Ƿ�ɹ�
	 * 
	 * @return �ж��Ƿ�ɹ�
	 */
	public boolean checkInterrupt()
	{
		return !isInterrupt;
	}

	/***
	 * ��ʼ��ȡDocument�¼�
	 */
	@Override
	public void startDocument() throws SAXException
	{
		dataList = new ArrayList<Object>(bufferLenth);
	}

	/***
	 * ��ʼ��ȡElement�ڵ��¼�
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
	 * ������ȡElement�ڵ��¼�
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
	 * ��ȡ�ڵ������¼�
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
 * Elimentʵ��ת����
 * 
 * @author �����
 * 
 */
interface IObjectTranslate
{
	Object parse(Hashtable<String, String> arge);
}

/***
 * Ĭ��ת����
 * 
 * @author �����
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
 * �������ݽӿ�
 * 
 * @author �����
 * 
 */
interface IDataImport
{
	void importData(List<Object> list);
}

/***
 * ��ȡXML����
 * 
 * @author �����
 * 
 */
class ReaderParam
{
	/***
	 * Elimentʵ��ת����
	 */
	public IObjectTranslate ObjectTranslate;

	/***
	 * �������ݽӿ�
	 */
	public IDataImport DataImport;
}