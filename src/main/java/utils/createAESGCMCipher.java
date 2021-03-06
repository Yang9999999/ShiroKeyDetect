package utils;

import org.apache.shiro.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class createAESGCMCipher {
    //实现AES中的GCM加密 shiro1.4.2版本更换为了AES-GCM加密方式
    public static String encrypt(String Shirokey) throws Exception {
        byte[] payloads = new KeyDetect().getPayload();
        byte[] key = java.util.Base64.getDecoder().decode(Shirokey);


        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        GCMParameterSpec ivParameterSpec = new GCMParameterSpec(128,iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
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
