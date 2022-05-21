package com.zhugong.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

@Service
public class PasswordHelper {
    private final String algorithmName = "md5";
    private final int hashIterations = 1;

    public String encryptPassword(String password) {
        //没有使用salt
        SimpleHash newPass = new SimpleHash(algorithmName, password,null, hashIterations);
        return newPass.toString();
    }

    public String encryptPassword(String salt,String password) {
        //使用salt
        SimpleHash newPass = new SimpleHash(algorithmName, password,salt, hashIterations);
        return newPass.toString();
    }
}