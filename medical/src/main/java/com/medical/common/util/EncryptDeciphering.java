package com.medical.common.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.cxf.common.util.Base64Utility;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64Encoder;

import com.medical.common.commonconst.MedicalConst;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 使用AES方式对数据加解密。
 * 
 * @author kfy
 * 
 */
public class EncryptDeciphering {

	private static final Logger log4j = Logger
			.getLogger(EncryptDeciphering.class);

	private static final String DEFAULT_KEY = "0123456789ABCDEF";

	private static final String AES = "AES";// 高级加密标准(Advanced Encryption Standard)

	private static EncryptDeciphering self = null;

	/** 加密工具 */
	private static Cipher encrypt = null;

	/** 解密工具 */
	private static Cipher decrypt = null;

	/**
	 * 
	 * 指定密钥构造方法
	 * 
	 * @param pwdKey
	 *            指定的密钥
	 */
	private EncryptDeciphering(String pwdKey) {

		try {
			Key key = null;
			if (pwdKey == null || pwdKey.trim().length() == 0) {
				key = getKey(DEFAULT_KEY.getBytes(MedicalConst.STR_UTF8));
			} else {
				key = getKey(pwdKey.getBytes(MedicalConst.STR_UTF8));
			}

			encrypt = Cipher.getInstance(AES);
			encrypt.init(Cipher.ENCRYPT_MODE, key);

			decrypt = Cipher.getInstance(AES);
			decrypt.init(Cipher.DECRYPT_MODE, key);

		} catch (Exception e) {
			log4j.error("初始化加解密对象失败：" + e.getMessage());
		}
	}
	/**
	 * 获取单例加解密对象。
	 * <br/>
	 * 与带参数的getInstance方法相比，二者谁先调用谁先起作用。
	 * @return
	 */
	public static EncryptDeciphering getInstance() {

		return getInstance(null);
	}
	/**
	 * 获取单例加解密对象。
	 * <br/>
	 * 与不带参数的getInstance方法相比，二者谁先调用谁先起作用。
	 * 
	 * @param key
	 *            指定的密钥，仅在第一次调用本方法时有效，其后每次调用即使更换了key还是采用首次指定的key
	 *            <br/>
	 *            key有大小写之分，如果为空将采用缺省的key
	 * @return
	 */
	private static synchronized EncryptDeciphering getInstance(String key) {

		if (self == null) {
			self = new EncryptDeciphering(key);
		}
		
		return self;
	}
	
	/**
	 * 加密字节数组
	 * 
	 * @param bytes
	 *            需加密的字节数组
	 * 
	 * @return 加密后的字节数组
	 * 
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] bytes) throws Exception {

		if(bytes==null||bytes.length==0){
			return null;
		}
		return encrypt.doFinal(bytes);
	}

	/**
	 * 加密字符串
	 * 
	 * @param str
	 *            需加密的字符串
	 * 
	 * @return 加密后的字符串
	 * 
	 * @throws Exception
	 */
	public String encrypt(String str) {

		if(str==null||str.trim().length()==0){
			return null;
		}
		try {
			return bytes2HexStr(encrypt(str.getBytes(MedicalConst.STR_UTF8)));
		} catch (Exception e) {
			log4j.error("加密失败：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * 解密字节数组
	 * 
	 * @param encryptBytes
	 * 
	 *            需解密的字节数组
	 * 
	 * @return 解密后的字节数组
	 * 
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] encryptBytes) throws Exception {

		if(encryptBytes==null||encryptBytes.length==0){
			return null;
		}
		return decrypt.doFinal(encryptBytes);
	}

	/**
	 * 
	 * 解密字符串
	 * 
	 * @param hexStr
	 * 
	 *            需解密的字符串
	 * 
	 * @return 解密后的字符串
	 * 
	 * @throws Exception
	 */
	public String decrypt(String hexStr) {

		if(hexStr==null||hexStr.trim().length()==0){
			return null;
		}
		try {
			return new String(decrypt(hexStr2Bytes(hexStr)),MedicalConst.STR_UTF8);
		} catch (Exception e) {
			log4j.error("解密失败：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * 将byte数组转换为表示16进制值的字符串， 和 hexStr2Bytes(String hexStr) 互为可逆的转换过程 <br/>
	 * 之所以要转换为16进制字符串，主要是因为加密后的byte数组是不能强制转换成字符串的，转换为16进制也便于传递和存储
	 * 
	 * @param bytes
	 *            需要转换的byte数组
	 * 
	 * @return 转换后的字符串
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	private static String bytes2HexStr(byte[] bytes) throws Exception {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * 
	 * 将表示16进制值的字符串转换为byte数组， 和public static String bytes2HexStr(byte[] bytes)
	 * 
	 * 互为可逆的转换过程
	 * 
	 * @param hexStr
	 *            需要转换的字符串
	 * 
	 * @return 转换后的byte数组
	 * 
	 * @throws Exception
	 * 
	 */
	private static byte[] hexStr2Bytes(String hexStr) throws Exception {

		byte[] arrB = hexStr.getBytes(MedicalConst.STR_UTF8);
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2

		byte[] arrOut = new byte[iLen / 2];

		for (int i = 0; i < iLen; i = i + 2) {

			String strTmp = new String(arrB, i, 2);

			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
		/*
		 * 方法二 if (hexStr.length() < 1) return null; byte[] result = new
		 * byte[hexStr.length() / 2]; for (int i = 0; i < hexStr.length() / 2;
		 * i++) { int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 +
		 * 1), 16); int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2
		 * + 2), 16); result[i] = (byte) (high * 16 + low); } return result;
		 */

	}

	/**
	 * 
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 * 
	 *            构成该字符串的字节数组
	 * 
	 * @return 生成的密钥
	 * 
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] pwd) throws Exception {

//		KeyGenerator kgen = KeyGenerator.getInstance(AES);
//		
//		SecureRandom sr=SecureRandom.getInstance("SHA1PRNG");
//		sr.setSeed(pwd);
//		
//		//此处必须为128、192、256其中之一
//		kgen.init(128, sr);
//
//		SecretKey secretKey = kgen.generateKey();
//		byte[] enCodeFormat = secretKey.getEncoded();
		
		SecretKeySpec key = new SecretKeySpec(pwd, AES);

		return key;
	}
	public static void main(String[] args) {
		try {
			//byte[] bs=EncryptDeciphering.getInstance("123456").encrypt("0123456789".getBytes("utf-8"));
			String str="4ef4c4eff1017facf260a7b0d0edca6020e6d03804f914ff86e1427444a5d34f091afa3b07514c840aab336738000831b4731614e11700ea31d3f1075f45dd08efef9a2b348023bfe81af3848e6d99e8487a2e3cdfb7873ce1135105ce127bfc0648d1612684f6534eba774fa50c2f5111438ab0a3d938f5d3164d5588f5d240d7f5f89bf081a995f64907a721efce03e943c425dc7298cf85edf15c01991d59a3823331229492eed3bdf4ebb0842b331c0520c696f83098ae629c06bf35414f1360d2784735517caafe93e0ae36c368ba722398aa19bb5211c3a74607c4c14c394e66c1098ec410dfcbbaa715fa6bd2b4afc582a1a622a8bf6372b210624700ad47f9bb5a4473c5b6a28251b4183b6a95a01a097c024d84768e06a1d97087fdd1b51dbdd9c2e085936944f4df4cb31798d93555727c7097c5aff8958b4c367d0c854d8014bdca39429876c11fbf305fe52c1832daa7b2e3d019847214e26e4f323bae779d7d4b2debe064a4902ebd5dc197024763b9c1cead407b8d70b2926bda6ac681f1247143f49df1f885bab8664b2246d3d0debb7b1e13fb54040cd83943826071decc8731f3e9b1142676d46384f1b0b544b316af1434b63ae152c7b6d4de139680baa93c3d171a817fede62c1385c22fd8a5fc58e7eb5e6eb7a1551bb57ff813f3d2824cbdbf091b581bd125a832ee7bb9cecedbb9aac28fe9f8f6a2f12847a15722716ed5db2187d8fdc8f275322b61f3b601bf98ce258cc6459a27e3d1ed5bfa43e9e5998fd946256b70d808e364df8554de24d30dc3e68bf8f88e61ebe32bc7a9eaad497120bb34d787bebe2a46e455653a958fde6ddffc012347c3bf0da1b67baa3fee44069e6f4d7c07da8ff75402efe8cff5ff8f1a24fc9116c019590c8d38d103e0d8b3f5af81e40fdfa7636cc2787f81ed23128be0519e7d7b1c26ceebd21382e9001bc3e568389509ab93d9a7ee951417b4a44ea0b12ff60d4b0b11395de636abd9a3ad863ad293f24c8e87b7b58ccc244b3e28d69d19a8affde3254617a57bf643a5b9cd3e5615ef7867f81cc99115a8c3b44ac6bb5a4adce29870c503fab5c4381bed6cc52f7358ebec6d366842e86bf1e5643afbc8cb06b95bcec2ed7371d971d5d5ffa2bd5d";
			System.out.println(EncryptDeciphering.getInstance().decrypt(str));
			String ystr="12345678901234561234567890123456";
			System.out.println(EncryptDeciphering.getInstance().encrypt(ystr));
			System.out.println(EncryptDeciphering.getInstance().encrypt(ystr).length());
		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
		}
	}
}
