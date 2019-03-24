package org.nju.mycourses.web.security.impl;

import org.springframework.security.core.AuthenticationException;

/**
 * @ClassName MyAuthenticationException
 * @PackageName org.nju.mycourses.web.security.impl
 * @Author sheen
 * @Date 2019/3/24
 * @Version 1.0
 * @Description //TODO
 **/
public class MyAuthenticationException extends AuthenticationException {
    public MyAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyAuthenticationException(String msg) {
        super(msg);
    }
}
