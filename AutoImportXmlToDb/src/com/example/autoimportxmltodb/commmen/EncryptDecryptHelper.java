package com.example.autoimportxmltodb.commmen;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

/***
 * 加密、解密类库
 *
 */
public class EncryptDecryptHelper
{
	private final static byte[] ivkey = { 0x21, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };
	private final static String HEX = "0123456789ABCDEF";
	private final static String seed = "santint";
	private final static byte[] iVs = new byte[16];

	/**
	 * DES加密
	 * 
	 * @param datasource
	 *            要加密的字符串
	 * @param password
	 *            密钥[大于等于8位]
	 * @return 加密后的字符串
	 */
	public static String DESEncrypt(String datasource, String password)
	{
		String result = datasource;
		try
		{
			if (datasource != null && !datasource.isEmpty())
			{
				DESKeySpec desKey = new DESKeySpec(password.substring(0, 8).getBytes("UTF-8"));
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey securekey = keyFactory.generateSecret(desKey);
				AlgorithmParameterSpec iv = new IvParameterSpec(ivkey);
				Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
				return Base64.encodeToString(cipher.doFinal(datasource.getBytes("UTF-8")), Base64.DEFAULT);
			}
		}
		catch (Throwable e)
		{
			Log.i("@Santint", e.getMessage());
		}
		return result;
	}

	/**
	 * DES解密
	 * 
	 * @param src
	 *            要解密的字符串
	 * @param password
	 *            密钥[大于等于8位]
	 * @return 解密后的字符串
	 */
	public static String DESDecrypt(String src, String password)
	{
		String result = src;
		try
		{
			if (src != null && !src.isEmpty())
			{
				DESKeySpec desKey = new DESKeySpec(password.substring(0, 8).getBytes("UTF-8"));
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey securekey = keyFactory.generateSecret(desKey);
				AlgorithmParameterSpec iv = new IvParameterSpec(ivkey);
				Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
				return new String(cipher.doFinal(Base64.decode(src, Base64.DEFAULT)));
			}
		}
		catch (Exception e)
		{
			Log.i("@Santint", e.getMessage());
		}
		return result;
	}

	/***
	 * 加密（最大支持64字符串加密）
	 * 
	 * @param strMsg
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String SymmetricKeyEncrypt(String strMsg)
	{
		try
		{
			byte[] rawKey = PadLeft(32, '0', seed).getBytes("UTF-8");
			byte[] result = encrypt(rawKey, iVs, strMsg.getBytes("UTF-8"));
			return toHex(result);
		}
		catch (Exception ex)
		{
			return strMsg;
		}
	}

	/***
	 * 解密
	 * 
	 * @param strEncrypt
	 *            加密后的字符串
	 * @return 解密后的字符串
	 */
	public static String SymmetricKeyDecrypt(String strEncrypt)
	{
		try
		{
			byte[] rawKey = PadLeft(32, '0', seed).getBytes("UTF-8");
			byte[] result = decrypt(rawKey, iVs, toByte(strEncrypt));
			return new String(result);
		}
		catch (Exception ex)
		{
			return strEncrypt;
		}
	}

	/***
	 * AES加密
	 * 
	 * @param encrypt
	 *            要加密的字符串
	 * @param key
	 *            秘钥
	 * @param iv
	 *            IV
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String AESEncrypt(String encrypt, String key, String iv) throws Exception
	{
		String aesencrypt = encrypt;

		byte[] rawKey = PadLeft(32, '0', key).getBytes("UTF-8");
		byte[] rawIV = PadLeft(16, '0', iv).getBytes("UTF-8");
		byte[] result = encrypt(rawKey, rawIV, encrypt.getBytes("UTF-8"));
		aesencrypt = toHex(result);

		return aesencrypt;
	}

	/***
	 * AES解密
	 * 
	 * @param decrypt
	 *            要解密的字符串
	 * @param key
	 *            秘钥
	 * @param iv
	 *            IV
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String AESDecrypt(String decrypt, String key, String iv) throws Exception
	{
		String aesdecrypt = decrypt;

		byte[] rawKey = PadLeft(32, '0', key).getBytes("UTF-8");
		byte[] rawIV = PadLeft(16, '0', iv).getBytes("UTF-8");
		byte[] result = decrypt(rawKey, rawIV, toByte(decrypt));
		aesdecrypt = new String(result);

		return aesdecrypt;
	}

	/***
	 * AES解密
	 * 
	 * @param decrypt
	 *            要解密的字符串
	 * @param key
	 *            秘钥
	 * @param iv
	 *            IV
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String AESDecrypt(byte[] decrypt, String key, String iv) throws Exception
	{
		String aesdecrypt = "";

		byte[] rawKey = PadLeft(32, '0', key).getBytes("UTF-8");
		byte[] rawIV = PadLeft(16, '0', iv).getBytes("UTF-8");
		byte[] result = decrypt(rawKey, rawIV, decrypt);
		aesdecrypt = new String(result);

		return aesdecrypt;
	}

	/***
	 * AES 加密[异或运算]
	 * 
	 * @param encrypt
	 *            要加密的字符串
	 * @param key
	 *            秘钥
	 * @return 加密后的字符串
	 */
	public static String AESEncrypt(String encrypt, int key)
	{
		char[] chars = encrypt.toCharArray();
		int[] ints = new int[chars.length];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++)
		{
			ints[i] = (int) chars[i];
			sb.append((char) ((ints[i] ^ key)));
		}
		return sb.toString();
	}

	// ///////////////////////////////////////////////////////////////////////

	private static byte[] encrypt(byte[] raw, byte[] iv, byte[] strEncrypt) throws Exception
	{
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec ivp = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivp);
		byte[] encrypted = cipher.doFinal(strEncrypt);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] iv, byte[] strdecrypt) throws Exception
	{
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec ivp = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivp);
		byte[] decrypted = cipher.doFinal(strdecrypt);
		return decrypted;
	}

	private static byte[] toByte(String hexString)
	{
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
		{
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		}
		return result;
	}

	private static String toHex(byte[] buf)
	{
		if (buf == null)
		{
			return "";
		}

		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++)
		{
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b)
	{
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	private static String PadLeft(int lenth, char ch, String str)
	{
		String pstr = str;

		while (pstr.length() < lenth)
		{
			pstr = pstr + ch;
		}

		return pstr;
	}
}

