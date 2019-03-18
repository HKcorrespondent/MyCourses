package org.nju.mycourses.web.security.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.nju.mycourses.data.entity.User;
import org.nju.mycourses.data.UserDAO;
import org.nju.mycourses.logic.exception.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 用户登陆
 *
 * @author xuxiangzhe
 * # User - 用户
 *
 * > url 前缀：**auth**
 * >
 * > 示例：**https://xxxxxxxxx/api/auth/xxxx**
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final String NOOP = "{noop}";
    private AuthenticationManager authenticationManager=null;
    private UserDAO userDAO;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            CustomUserDetails securityUser = new ObjectMapper()
                    .readValue(req.getInputStream(), CustomUserDetails.class);
//            securityUser.setUsername((securityUser.getUsername()));
//            //这里的security user是从前端的包生成的，所以密码里不含noop，因此要在这里手动加上
//            //不！！！Spring security在解析密码的时候会自动去掉noop，所以登录不用加noop
//            securityUser.setPassword(securityUser.getPassword());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            securityUser.getUsername(),
                            securityUser.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    @Transactional
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws JsonProcessingException {
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "ymymym")
                .compact();
        assert username != null;
        assert userDAO != null;
        Map<String,Object> ret=new HashMap<>();
        User user= userDAO.findByUsername(username);
        if(user==null){
            throw new NotFoundException("找不到用户");
        }
        ret.put("user",user);
        ret.put("authorization","Bearer " + token);
        res.setContentType("application/json; charset=utf-8");
        res.addHeader("Authorization", "Bearer " + token);
//        TokenMap.getTokenMap().put(user.getUsername(),token);
        res.addHeader("Role", user.getRole().toString());
        res.addHeader("State", user.getState().toString());
        try {
            PrintWriter writer = res.getWriter();
            writer.print(new ObjectMapper().writeValueAsString(ret));
            writer.close();
            res.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

@lombok.Data
class Data {
    Object data;

    Data(Object data) {
        this.data = data;
    }
}