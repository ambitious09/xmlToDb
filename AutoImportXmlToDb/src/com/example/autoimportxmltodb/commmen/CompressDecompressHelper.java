package com.example.autoimportxmltodb.commmen;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.http.protocol.HTTP;


/***
 * 压缩解压类库
 * 
 * 
 */
public class CompressDecompressHelper
{
	/***
	 * 用GZIP方式压缩文件
	 * 
	 * @param inFile
	 *            要压缩的文件
	 * @param gzipFile
	 *            压缩后的文件
	 * @return 压缩后的文件
	 * @throws IOException
	 */
	public static File CompressGZipFile(File inFile, File gzipFile) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), "UTF-8"));
		BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(gzipFile)));

		int count = 0;
		char[] buff = new char[2048];
		while ((count = in.read(buff)) > 0)
		{
			out.write(String.valueOf(buff, 0, count).getBytes("UTF-8"));
		}

		in.close();
		out.close();

		return gzipFile;
	}

	/***
	 * 用GZIP方式解压文件
	 * 
	 * @param gzipFile
	 *            压缩文件
	 * @param outFile
	 *            解压后的文件
	 * @return 解压后的文件
	 * @throws IOException
	 */
	public static File DeCompressGZipFile(File gzipFile, File outFile) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(new MultiMemberGZIPInputStream(new FileInputStream(gzipFile)), "UTF-8"));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));

		int count = 0;
		char[] buff = new char[2048];
		while ((count = in.read(buff)) > 0)
		{
			out.write(String.valueOf(buff, 0, count).getBytes("UTF-8"));
		}

		in.close();
		out.close();

		return outFile;
	}

	/***
	 * 用ZIP方式压缩文件
	 * 
	 * @param gzipFile
	 *            压缩后的文件
	 * @param inFiles
	 *            要压缩的文件或文件夹列表
	 * @return 压缩后的文件
	 * @throws IOException
	 */
	public static File CompressZipFile(File zipFile, File... inFiles) throws IOException
	{
		ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(zipFile));

		CompressZipFile(zipStream, "", inFiles);

		zipStream.close();

		return zipFile;
	}

	/***
	 * 用ZIP方式解压文件
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @param rootPath
	 *            解压根路径
	 * @throws IOException
	 */
	public static void DeCompressZipFile(File zipFile, String rootPath) throws IOException
	{
		String rootpath = rootPath.endsWith("/") ? rootPath : rootPath + "/";
		ZipFile zip = new ZipFile(zipFile);
		ZipInputStream zipStream = new ZipInputStream(new FileInputStream(zipFile));

		ZipEntry zipEntry = null;
		while ((zipEntry = zipStream.getNextEntry()) != null)
		{
			if (!zipEntry.isDirectory())
			{
				File file = new File(rootpath + zipEntry.getName());
				File pfile = file.getParentFile();
				if (!pfile.exists())
				{
					pfile.mkdirs();
				}

				int len = 0;
				byte[] buff = new byte[2048];
				OutputStream outStream = new FileOutputStream(file);
				InputStream inStream = zip.getInputStream(zipEntry);
				while ((len = inStream.read(buff)) > 0)
				{
					outStream.write(buff, 0, len);
				}
				outStream.close();
				inStream.close();
			}
		}

		zipStream.close();		
	}

	/**
     * 解压缩一个文件
    * 
     * @param zipFile
     *            压缩文件
    * @param folderPath
     *            解压缩的目标目录
    * @throws IOException
     *             当解压缩过程出错时抛出
    */ 
	public static void upZipFile(File zipFile, String folderPath) throws ZipException, IOException 
	{ 
        File desDir = new File(folderPath); 
        if (!desDir.exists()) 
        { 
            desDir.mkdirs(); 
        } 
        ZipFile zf = new ZipFile(zipFile); 
        InputStream in = null; 
        OutputStream out = null; 
        try { 
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) 
            { 
                ZipEntry entry = ((ZipEntry) entries.nextElement()); 
                in = zf.getInputStream(entry); 
                String str = folderPath + File.separator + entry.getName(); 
                str = new String(str.getBytes("8859_1"), HTTP.UTF_8); 
                File desFile = new File(str); 
                if (!desFile.exists()) 
                { 
                    File fileParentDir = desFile.getParentFile(); 
                    if (!fileParentDir.exists()) 
                    { 
                        fileParentDir.mkdirs(); 
                    } 
                    desFile.createNewFile(); 
                } 
                out = new FileOutputStream(desFile); 
                byte buffer[] = new byte[2048]; 
                int realLength; 
                while ((realLength = in.read(buffer)) > 0) 
                { 
                    out.write(buffer, 0, realLength); 
                } 
 
            } 
        } finally 
        { 
            if (in != null) 
                in.close(); 
            if (out != null) 
                out.close(); 
        } 
    } 
	
	/***
	 * 用ZIP方式解压文件
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @param rootPath
	 *            解压根路径
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public static File DeCompressZipFile2(File zipFile, String rootPath) throws IOException
	{
		File newFile = null;
		String rootpath = rootPath.endsWith("/") ? rootPath : rootPath + "/";
		ZipFile zip = new ZipFile(zipFile);
		ZipInputStream zipStream = new ZipInputStream(new FileInputStream(zipFile));

		ZipEntry zipEntry = null;
		while ((zipEntry = zipStream.getNextEntry()) != null)
		{
			if (!zipEntry.isDirectory())
			{
				File file = new File(rootpath + zipEntry.getName());
				File pfile = file.getParentFile();
				if (!pfile.exists())
				{
					pfile.mkdirs();
				}

				int len = 0;
				byte[] buff = new byte[2048];
				OutputStream outStream = new FileOutputStream(file);
				InputStream inStream = zip.getInputStream(zipEntry);
				while ((len = inStream.read(buff)) > 0)
				{
					outStream.write(buff, 0, len);
				}
				outStream.close();
				inStream.close();
			}
		}

		zipStream.close();

		zipFile.delete();

		newFile = new File(rootpath + zipEntry.getName());

		return newFile;
	}

	/***
	 * 用ZIP方式解压匹配的文件
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @param rootPath
	 *            解压根路径
	 * @param matchstr
	 *            匹配字符串[以此字符串开始匹配]
	 * @return 解压个数
	 * @throws IOException
	 */
	public static int DeCompressZipFile(File zipFile, String rootPath, String matchstr) throws IOException
	{
		int count = 0;
		String rootpath = rootPath.endsWith("/") ? rootPath : rootPath + "/";
		ZipFile zip = new ZipFile(zipFile);
		ZipInputStream zipStream = new ZipInputStream(new FileInputStream(zipFile));

		ZipEntry zipEntry = null;
		while ((zipEntry = zipStream.getNextEntry()) != null)
		{
			if (!zipEntry.isDirectory() && zipEntry.getName().startsWith(matchstr))
			{
				File file = new File(rootpath + zipEntry.getName());
				File pfile = file.getParentFile();
				if (!pfile.exists())
				{
					pfile.mkdirs();
				}

				int len = 0;
				byte[] buff = new byte[2048];
				OutputStream outStream = new FileOutputStream(file);
				InputStream inStream = zip.getInputStream(zipEntry);
				while ((len = inStream.read(buff)) > 0)
				{
					outStream.write(buff, 0, len);
				}
				outStream.close();
				inStream.close();
				count++;
			}
		}

		zipStream.close();
		return count;
	}

	/***
	 * 用ZIP方式压缩文件到压缩流
	 * 
	 * @param zipStream
	 *            压缩流
	 * @param dir
	 *            父目录
	 * @param inFiles
	 *            要压缩的文件或文件夹列表
	 * @throws IOException
	 */
	private static void CompressZipFile(ZipOutputStream zipStream, String dir, File... inFiles) throws IOException
	{
		for (File file : inFiles)
		{
			String entryName = !dir.isEmpty() ? dir + "/" + file.getName() : file.getName();

			if (file.isDirectory())
			{
				CompressZipFile(zipStream, entryName, file.listFiles());
			}
			else if (file.isFile())
			{
				ZipEntry zipEntry = new ZipEntry(entryName);
				zipStream.putNextEntry(zipEntry);

				int len = 0;
				byte[] buff = new byte[2048];
				InputStream inStream = new FileInputStream(file);
				while ((len = inStream.read(buff)) > 0)
				{
					zipStream.write(buff, 0, len);
				}
				zipStream.closeEntry();
				inStream.close();
			}
		}
	}
}

