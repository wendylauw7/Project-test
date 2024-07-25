package com.project.test.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BaseHelper implements PasswordEncoder {
	public BaseHelper() {
	}
	private static final String BASE_KEY = "9a1eb2947a62aab66efadb4e9cef9487";
	
	public String getPasswordMD5(String password)throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	
    	return hexString.toString();
	}
	
	public String byteToHex(byte[] data){
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }

        StringBuffer hexString = new StringBuffer();
    	for (int i = 0; i < data.length; i++) {
    		String hex=Integer.toHexString(0xff & data[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     		hexString.append(hex);
    	}
    	
    	return hexString.toString();
	}
	
	public byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public String generateKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();

        return byteToHex(secretKey.getEncoded());
	}
	

	public SecretKey getDefaultSecretKey() throws Exception {
		byte[] secretKeyByte = hexStringToByteArray(BASE_KEY);
		SecretKey secretKey = new SecretKeySpec(secretKeyByte, 0, secretKeyByte.length, "AES");
		
		return secretKey;
	}

	public String encrypt(String plainText)
			throws Exception {
		
		byte[] plainTextByte = plainText.getBytes();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, getDefaultSecretKey());
		byte[] encryptedByte = cipher.doFinal(plainTextByte);

		return byteToHex(encryptedByte);
	}

	public String decrypt(String encryptedText)
			throws Exception {		
		byte[] encryptedTextByte = hexStringToByteArray(encryptedText);
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, getDefaultSecretKey());
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}

	@Override
	public String encode(CharSequence rawPassword){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(((String) rawPassword).getBytes());
	        byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }

	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	
	    	return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword == null || encodedPassword.length() == 0) {
			return false;
		}
		try {
			if(encodedPassword.equals(getPasswordMD5((String) rawPassword))){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}