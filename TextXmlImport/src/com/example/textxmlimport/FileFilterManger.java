package com.example.textxmlimport;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
/**
 * 文件过滤类 
 */
public class FileFilterManger {

	@SuppressLint("SdCardPath")
	private ArrayList<HashMap<String, String>> filesList = new ArrayList<HashMap<String, String>>();

	public FileFilterManger() {
	}

	public ArrayList<HashMap<String, String>> getFileList(Context mContext) {

		File[] listFiles=getSDAddRootFileList(mContext, new FileExtensionFilter());
		
		if(listFiles!=null)
		{
			if (listFiles.length > 0) {

				for (File file : getSDAddRootFileList(mContext, new FileExtensionFilter())) {

					HashMap<String, String> map = new HashMap<String, String>();

					map.put("FileTitle",
							file.getName().substring(0,
									(file.getName().length() - 4)));

					map.put("FilePath", file.getPath());

					filesList.add(map);
				}
			}
		}
		return filesList;

	}

	class FileExtensionFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			return (name.endsWith(".flv") || name.endsWith(".FLV")|| name.endsWith(".San")||name.endsWith(".san")|| name.endsWith(".rar"));

		}

	}

	public File[] getFileDataList(Context mContext) 
	{
		File[] fileList = getSDAddRootFileList(mContext, new FileExtensionFilter());

		return fileList;

	}

	public ArrayList<HashMap<String, String>> getMixerFileList(Context mContext, String str) {

		if(str.equals("2"))
		{
			MixerTwoFileExtensionFilter filter=new MixerTwoFileExtensionFilter();
			
			File[] listFiles=getSDAddRootFileList(mContext, filter);
			
			if(listFiles!=null)
			{
				if (listFiles.length > 0) {

					for (File file : getSDAddRootFileList(mContext, new MixerTwoFileExtensionFilter())) {

						HashMap<String, String> map = new HashMap<String, String>();

						map.put("FileTitle",
								file.getName().substring(0,
										(file.getName().length() - 4)));

						map.put("FilePath", file.getPath());

						filesList.add(map);
					}
				}
			}
		}
		else 
		{
			MixerFileExtensionFilter filter=new MixerFileExtensionFilter();
			
			File[] listFiles=getSDAddRootFileList(mContext, filter);
			
			if(listFiles!=null)
			{
				if (listFiles.length > 0) {

					for (File file : getSDAddRootFileList(mContext, new MixerFileExtensionFilter())) {

						HashMap<String, String> map = new HashMap<String, String>();

						map.put("FileTitle",
								file.getName().substring(0,
										(file.getName().length() - 4)));

						map.put("FilePath", file.getPath());

						filesList.add(map);
					}
				}
			}
		}
		return filesList;

	}

	/*
	 * 获取语言文件列表
	 */
	public ArrayList<HashMap<String, String>> getLanguageFileList(Context mContext) {

		File[] listFiles=getSDAddRootFileList(mContext, new LanguageFileExtensionFilter());
		
		if(listFiles!=null)
		{
			if (listFiles.length > 0) 
			{

				for (File file : getSDAddRootFileList(mContext, new LanguageFileExtensionFilter())) 
				{

					HashMap<String, String> map = new HashMap<String, String>();

					map.put("FileTitle",file.getName().substring(0,(file.getName().length() - 4)));

					map.put("FilePath", file.getPath());

					filesList.add(map);
				}
			}
		}
		return filesList;

	}
	
	class LanguageFileExtensionFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			return (name.endsWith(".cel") || name.endsWith(".Cel")|| name.endsWith(".CEL"));

		}

	}
	
	class MixerFileExtensionFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			return (name.endsWith(".cal") || name.endsWith(".CAL")|| name.endsWith(".Cal"));

		}

	}

	class MixerTwoFileExtensionFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			return (name.endsWith(".CCA") || name.endsWith(".cca")|| name.endsWith(".Cca"));

		}

	}
	
	/*
	 * 获取导入标签文件列表
	 */
	public ArrayList<HashMap<String, String>> getImportLableFileList(Context mContext) {

		File[] listFiles=getSDAddRootFileList(mContext, new ImportLableFileExtensionFilter());
		
		if(listFiles!=null)
		{
			if (listFiles.length > 0) 
			{

				for (File file : getSDAddRootFileList(mContext, new ImportLableFileExtensionFilter())) 
				{

					HashMap<String, String> map = new HashMap<String, String>();

					map.put("FileTitle",file.getName().substring(0,(file.getName().length() - 4)));

					map.put("FilePath", file.getPath());

					filesList.add(map);
				}
			}
		}
		return filesList;

	}
	
	class ImportLableFileExtensionFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			return (name.endsWith(".lbz") || name.endsWith(".Lbz")|| name.endsWith(".LBZ"));

		}

	}
	
	/*
	 * 获取导出标签文件列表
	 */
	public ArrayList<HashMap<String, String>> getExportLableFileList(String pathString) {

		File home = new File(pathString);

		File[] listFiles=home.listFiles(new ExportLableFileExtensionFilter());
		
		if(listFiles!=null)
		{
			if (listFiles.length > 0) 
			{

				for (File file : home.listFiles(new ExportLableFileExtensionFilter())) 
				{

					HashMap<String, String> map = new HashMap<String, String>();

					map.put("FileTitle",file.getName().substring(0,(file.getName().length() - 4)));

					map.put("FilePath", file.getPath());

					filesList.add(map);
				}
			}
		}
		return filesList;

	}
	
	class ExportLableFileExtensionFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			return (name.endsWith(".lbl") || name.endsWith(".Lbl")|| name.endsWith(".LBL"));

		}

	}
	
	public File[] getMixerFileDataList(Context mContext, String str) 
	{
		File[] fileList=null;
		
		if(str.equals("2"))
		{
			fileList = getSDAddRootFileList(mContext, new MixerTwoFileExtensionFilter());
		}
		else 
		{
			fileList = getSDAddRootFileList(mContext, new MixerFileExtensionFilter());
		}
		

		return fileList;

	}
	
	public ArrayList<HashMap<String, String>> getRestoreFileList(Context mContext) {

		FileRestoreExtensionFilter filter=new FileRestoreExtensionFilter();
		
		File[] listFiles=getSDAddRootFileList(mContext, filter);
		
		if(listFiles!=null)
		{
			if (listFiles.length > 0) {

				for (File file : getSDAddRootFileList(mContext, new FileRestoreExtensionFilter())) {

					HashMap<String, String> map = new HashMap<String, String>();

					map.put("FileTitle",
							file.getName().substring(0,
									(file.getName().length() - 4)));

					map.put("FilePath", file.getPath());

					filesList.add(map);
				}
			}
		}
		return filesList;

	}

	class FileRestoreExtensionFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			return (name.endsWith(".crs")|| name.endsWith(".CRS")|| name.endsWith(".Crs"));

		}

	}

	public File[] getRestoreFileDataList(Context mContext) {
		File[] fileList = getSDAddRootFileList(mContext, new FileRestoreExtensionFilter());

		return fileList;

	}
	
	public static String getSDPath(){ 
	       File sdDir = null; 
	       boolean sdCardExist = Environment.getExternalStorageState()   
	                           .equals(Environment.MEDIA_MOUNTED);   
	       if(sdCardExist)      
	       {                               
			   sdDir = Environment.getExternalStorageDirectory();//Environment.getRootDirectory();
		   }else{
			   sdDir = Environment.getRootDirectory();
		   }
	       return sdDir.toString(); 
	       
	}
	/*
	 * 返回SD卡和手机存储得文件列表
	 */
	public File[] getSDAddRootFileList(Context mContext, FilenameFilter filter){ 
	       File sdDir = null; 
	       File rootDir = null; 
	       File[] fileList = null;
	       File[] fileListRoot = null;
	       StorageManager mStorageManager = null;
	       Method mMethodGetPaths = null;
	       String[] SdPaths=null;
	       if(mContext!=null){  
	    	   mStorageManager=(StorageManager)mContext.getSystemService(Context.STORAGE_SERVICE);  
	            try{  
	                mMethodGetPaths=mStorageManager.getClass().  
	                        getMethod("getVolumePaths");  
	            }catch(NoSuchMethodException ex){  
	                ex.printStackTrace();  
	            }  
	        }   
	       
	       try{  
	    	   SdPaths=(String[])mMethodGetPaths.invoke(mStorageManager);//调用该方法  
	        }catch(IllegalArgumentException ex){  
	            ex.printStackTrace();  
	        }catch(IllegalAccessException ex){  
	            ex.printStackTrace();     
	        }catch(InvocationTargetException ex){  
	            ex.printStackTrace();  
	        }  
	       boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);   
	       if(SdPaths == null || SdPaths.length == 0){
		       if(sdCardExist)      
		       {                               
				   sdDir = Environment.getExternalStorageDirectory();//Environment.getRootDirectory();
			   }else{
				   sdDir = Environment.getRootDirectory();
			   }
		       fileList = sdDir.listFiles(filter);
		       return fileList; 
	       }
	       //获取第一个存储卡列表
	       sdDir = new File(SdPaths[0]);
	       if(!sdDir.equals("")){
	    	   fileList = sdDir.listFiles(filter);
	       }
	       if(SdPaths.length == 1)
	    	   return fileList;
	     //获取第二个存储卡列表
	       rootDir = new File(SdPaths[1]);
	       fileListRoot = rootDir.listFiles(filter);
	       if(fileListRoot != null && fileListRoot.length > 0){
	    	   File[] mSdFiles = new File[fileList.length + fileListRoot.length];
	    	   int mSdLength = fileList.length;
	    	   for(int i=0; i < mSdLength; i++){
	    		   mSdFiles[i] = fileList[i];
	    	   }
	    	   for(int j=0; j < fileListRoot.length; j++){
	    		   mSdFiles[mSdLength+j] = fileListRoot[j];
	    	   }
	    	   return mSdFiles;
	       }else{
	    	   return fileList; 
	       }
	       
	}
	
	public ArrayList<HashMap<String, String>> getSakFileList(Context mContext) {

		File[] listFiles=getSDAddRootFileList(mContext, new SakFileExtensionFilter());
		
		if(listFiles!=null)
		{
			if (listFiles.length > 0) 
			{

				for (File file : getSDAddRootFileList(mContext, new SakFileExtensionFilter())) 
				{

					HashMap<String, String> map = new HashMap<String, String>();

					map.put("FileTitle",file.getName().substring(0,(file.getName().length() - 4)));

					map.put("FilePath", file.getPath());

					filesList.add(map);
				}
			}
		}
		return filesList;

	}
	
	public File[] getSakFileDataList(Context mContext) 
	{
		File[] fileList = getSDAddRootFileList(mContext, new SakFileExtensionFilter());

		return fileList;

	}
	
	class SakFileExtensionFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			return (name.endsWith(".sak") || name.toUpperCase().endsWith(".SAK"));

		}

	}
}
