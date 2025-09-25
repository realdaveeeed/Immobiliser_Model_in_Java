package com.BodyControlModule;

public class Key{
    private final String encryptedKey;

    public Key(String key) {
        this.encryptedKey = com.Tools.Encrypt.encrypt(key);
    }

    public String getEncryptedKey(){
        return encryptedKey;
    }
}
