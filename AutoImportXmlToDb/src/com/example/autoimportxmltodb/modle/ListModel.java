package com.example.autoimportxmltodb.modle;


/** 
* @ClassName: ListModel 
* @Description: TODO(������һ�仰��������������) 
* @author ���Ừ 
* @date 2016-7-30 ����4:03:14 
*  
*/
 
public class ListModel
{
    public String Key;
    
    public String Value;
    
    public String Description;
    
    /***
     * ���캯��
     * @param key
     * @param name
     */
    public ListModel(String key, String name)
    {
        Key = key;
        Value = name;
        Description = name;
    }

}
