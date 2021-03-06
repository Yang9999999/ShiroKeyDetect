package utils;

import org.apache.shiro.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class createAESCBCCipher {
    //实现AES中的CBC加密
    public static String encrypt(String Shirokey) throws Exception {
        byte[] payloads = new KeyDetect().getPayload();
        byte[] key = java.util.Base64.getDecoder().decode(Shirokey);

//        byte[] raw = sKey.getBytes("utf-8");
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(payloads);
        byte[] encryptedIvandtext = new byte[ivSize + encrypted.length];
        System.arraycopy(iv, 0, encryptedIvandtext, 0, ivSize);
        System.arraycopy(encrypted, 0, encryptedIvandtext, ivSize, encrypted.length);
//        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        String b64Payload = Base64.encodeToString(encryptedIvandtext);


//        System.out.println(b64Payload);
        return b64Payload;
    }

}
