package com.example.autoimportxmltodb.modle;

public class DicKey
{
	/***
	 * 用户数据根路径
	 */
	public static final String UserData_RootPath = "userdata";

	/***
	 * 配置文件相对路径
	 */
	public static final String Config_Path = "config";

	/***
	 * 驱动配置文件相对路径
	 */
	public static final String Dispenser_Path = "dispenser";

	/***
	 * 应用配置文件名称
	 */
	public static final String Config_Application_Name = "Application.xml";

	/***
	 * 网络配置文件名称
	 */
	public static final String Config_Network_Name = "Network.xml";

	/***
	 * 标签配置文件名称
	 */
	public static final String Config_Label_Name = "LabelLink.xml";
	
	/***
	 * 日志配置文件名称
	 */
	public static final String Config_Log_Name = "LogConfig.xml";
	
	/***
	 * 辅料色浆配置文件名称
	 */
	public static final String Config_ExcludeColorants_Name = "ExcludeColorantConfig.xml";

	/***
	 * 配方导出报表格式文件名称
	 */
	public static final String Config_ReportFormat_Name = "ReportFormat.xml";

	/***
	 * 色浆量化格式文件相对路径
	 */
	public static final String DataFormat_Path = "dataformat";

	/***
	 * 标签文件相对路径
	 */
	public static final String Lable_Path = "label";

	/***
	 * 语言包（文件夹名）
	 */
	public static final String Language_Path = "language";

	/***
	 * 计划任务相对路径
	 */
	public static final String Task_Path = "task";

	/***
	 * 任务作业文件名
	 */
	public static final String Task_Job_FileName = "SchemeTask.xml";

	/***
	 * 基本任务文件名
	 */
	public static final String Task_Base_FileName = "BaseTask.xml";

	/***
	 * 临时数据根路径
	 */
	public static final String Temp_RootPath = "temp";

	/***
	 * 同步服务相关数据文件根路径
	 */
	public static final String Roaming_RootPath = "roamingstate";

	/***
	 * 认证文件相对路径
	 */
	public static final String Auth_Path = "auth";

	/***
	 * 认证文件名称
	 */
	public static final String Authentication_Name = "Authentication.data";

	/***
	 * 限制文件名称
	 */
	public static final String Limit_Name = "Limitation.data";

	/***
	 * 推送文件相对路径
	 */
	public static final String PushData_Path = "pushdata";

	/***
	 * 同步文件相对路径
	 */
	public static final String SyncData_Path = "syncdata";

	/***
	 * 同步标准配方文件相对路径
	 */
	public static final String SyncData_StandFormula_Path = "standformula";

	/***
	 * 同步历史配方文件相对路径
	 */
	public static final String SyncData_HistoryFormula_Path = "historyformula";

	/***
	 * 同步用户私有数据文件相对路径
	 */
	public static final String SyncData_ClientPrivateData_Path = "clientprivatedata";

	/***
	 * 广告文件相对路径
	 */
	public static final String Advertisement_Path = "advertisement";

	/***
	 * 缓存文件相对路径
	 */
	public static final String CacheData_Path = "database";

	/***
	 * 缓存文件名
	 */
	public static final String CacheData_Name = "HistoryRecord.db";

	/***
	 * 还原点[系统备份]相对路径
	 */
	public static final String RestorePoint_Path = "santint/backup";

	/***
	 * 初始化时用户数据还原包
	 */
	public static final String UserData_BackupFile = "USERDATA.CRS";

	/***
	 * 还原规则配置文件
	 */
	public static final String UserData_RestoreConfig = "RestoreConfig.xml";

	/// 数据库相对路径
    /// </summary>
    public static final String Database_Path = "databases";

    /// <summary>
    /// 标准配方数据库文件名称
    /// </summary>
    public static final String Database_StandardFormula_Name = "SANTINTSTANDARD.DB";

    /// <summary>
    /// 客户配方数据库文件名称
    /// </summary>
    public static final String Database_CustomerFormula_Name = "SANTINTCUSTOMER.DB";

    /// <summary>
    /// 历史配方数据库文件名称
    /// </summary>
    public static final String Database_HistoryFormula_Name = "SANTINTDISPENSED.DB";
    
    /***
     * ML Name
     */
    public static final String Unit_ML_Name = "ML";

    /***
     * L Name
     */
    public static final String Unit_L_Name = "L";

    /***
     * G Name
     */
    public static final String Unit_G_Name = "G";

    /***
     * KG Name
     */
    public static final String Unit_KG_Name = "KG";
}

