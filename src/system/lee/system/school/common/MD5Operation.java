package lee.system.school.common;

import java.security.MessageDigest;

public class MD5Operation {
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
	 * MD5加密
	 * @param password
	 * @return
	 */
	public static String getEncryptedPwd(String password) {
		byte[] digest = null;
		try {
			//声明摘要对象，并生成
			MessageDigest md = MessageDigest.getInstance("MD5");
			//计算MD5函数
			//passwd.getBytes("UTF-8")将输入密码变成byte数组，即将某个数装换成一个16进制数
			md.update(password.getBytes("UTF-8"));
			//计算后获得字节数组,这就是那128位了即16个元素
			digest = md.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toHexString(digest);
	}
	
	/**
	 * 将二进制数组转为16进制字符串
	 * @param b
	 * @return
	 */
	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	/*
	byte呢是一个字节，也就是8位，如：0010 0100
	而0xf0呢，也是8位：1111 0000
	而byte&0xf0呢，就是按位与操作(0&1=0,0&0=0,1&1=1)，这样呢就得到0010 0000这样八位表示的字节，然后">>4"操作是向右移四位，最高位用0补，就得到0000 0010。
	*/
}
