package com.example.autoimportxmltodb.modle;


/** 
* @ClassName: ListModel 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 刘会华 
* @date 2016-7-30 下午4:03:14 
*  
*/
 
public class ListModel
{
    public String Key;
    
    public String Value;
    
    public String Description;
    
    /***
     * 构造函数
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
