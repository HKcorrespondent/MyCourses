package org.nju.mycourses.web.security.impl;

import org.nju.mycourses.data.User;

import java.util.HashMap;
import java.util.Map;

public class TokenMap {
    private Map<String,String> tokenMap;
    private static TokenMap ST=null;
    private TokenMap(){tokenMap=new HashMap<>();}
    public static TokenMap getTokenMap(){
        if(ST==null){
            ST=new TokenMap();
        }
        return ST;
    }

    public String get(String username) {
        return tokenMap.get(username);
    }
    public String remove(String username){
        return tokenMap.remove(username);
    }

    public String put(String username, String token) {
        return tokenMap.put(username, token);
    }
}
