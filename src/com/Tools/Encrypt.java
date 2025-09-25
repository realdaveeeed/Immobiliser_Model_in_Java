package com.Tools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import com.Exception.EncryptionRuntimeException;

public class Encrypt{
    private static final byte[] encrypterKey = keyGen();

    public static String encrypt(String data) {
        try{Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, new SecretKeySpec(encrypterKey, "AES"));
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);}
        catch(Exception e){throw new EncryptionRuntimeException();}
    }

    public static byte[] keyGen(){
        SecureRandom sr = new SecureRandom();
        byte[] key = new byte[16];
        sr.nextBytes(key);
        return key;
    }

    public static void main(String[] args){
        System.out.println("Test encryption code:"+keyGen());
    }
}