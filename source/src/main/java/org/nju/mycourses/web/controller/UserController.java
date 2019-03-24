package org.nju.mycourses.web.controller;

import org.hibernate.service.spi.ServiceException;
import org.nju.mycourses.data.entity.Role;
import org.nju.mycourses.logic.UserService;
import org.nju.mycourses.logic.exception.ExceptionNotValid;
import org.nju.mycourses.logic.util.EmailService;
import org.nju.mycourses.web.controller.dto.UserDTO;
import org.nju.mycourses.web.controller.vo.*;
import org.nju.mycourses.web.security.impl.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.nju.mycourses.web.security.WebSecurityConstants.*;
@RestController
@RequestMapping(value="/api")
public class UserController {
    private final EmailService emailService;
    private final UserService userService;
    @Autowired
    public UserController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @RequestMapping(value="/student/register",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public UserVO registerStudent(@RequestBody @Validated(PostMapping.class) UserDTO userDTO, Errors errors) throws ServiceException {
        if (errors.hasGlobalErrors()) {
            throw new ExceptionNotValid(errors.getGlobalError().getDefaultMessage());
        }
        if (errors.hasFieldErrors()) {
            throw new ExceptionNotValid(errors.getFieldError().getDefaultMessage());
        }
        return new UserVO(
                userService.Register(
                        userDTO.getUsername(),userDTO.getPassword(),Role.STUDENT
                ).orElseThrow(() -> new ExceptionNotValid("该账户已注册"))
               );
    }
    @RequestMapping(value="/teacher/register",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public UserVO registerTeacher(@RequestBody @Validated(PostMapping.class) UserDTO userDTO, Errors errors) throws ServiceException {
        if (errors.hasGlobalErrors()) {
            throw new ExceptionNotValid(errors.getGlobalError().getDefaultMessage());
        }
        if (errors.hasFieldErrors()) {
            throw new ExceptionNotValid(errors.getFieldError().getDefaultMessage());
        }
        return new UserVO(
                userService.Register(
                        userDTO.getUsername(),userDTO.getPassword(),Role.TEACHER
                ).orElseThrow(() -> new ExceptionNotValid("该账户已注册"))
        );
    }
    @RequestMapping(value="/admin/register",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public UserVO registerAdmin(@RequestBody @Validated(PostMapping.class) UserDTO userDTO, Errors errors) throws ServiceException {
        return new UserVO(
                userService.registerAdmin(
                        userDTO.getUsername(),userDTO.getPassword()
                ).orElseThrow(() -> new ExceptionNotValid("该账户已注册"))
        );
    }

    @RolesAllowed({UNCERTIFIED_STUDENT_ROLE,UNCERTIFIED_TEACHER_ROLE})
    @RequestMapping(value="/mail/validate",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public UserVO mailValidate(@AuthenticationPrincipal CustomUserDetails userDetails,@RequestBody Map certifiedCode, Errors errors) throws ServiceException {
        if(certifiedCode.size()==0||certifiedCode.get("certifiedCode")==null){
            throw new ExceptionNotValid("验证码错误");
        }
        return new UserVO(
                userService.validateEmail((String) certifiedCode.get("certifiedCode")
                        ,userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("验证码错误"))
        );

    }
    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/user/cancel",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public UserVO cancelUser(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        return new UserVO(
                userService.cancel(userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("验证码错误"))
        );
    }

    @RolesAllowed({})
    @RequestMapping(value="/user/log",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public List<LogVO> cancelUser(@AuthenticationPrincipal CustomUserDetails userDetails,@RequestParam String username)
    {
        return userService.checkLog(username)
                .stream()
                .map(LogVO::new)
                .collect(Collectors.toList());
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value="/send",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public String getUser(@PathParam(value = "username")String username, @PathParam(value = "msg")String msg)
    {
        assert msg!=null;
        emailService.send(username,"第一次邮件",msg);
        return "发送成功";
    }
    @RolesAllowed({UNCERTIFIED_STUDENT_ROLE})
    @RequestMapping(value="/UNCERTIFIED_STUDENT_ROLE",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public Map UNCERTIFIED_STUDENT_ROLE()
    {
        Map m=new HashMap<String,String>();
        m.put("data","UNCERTIFIED_STUDENT_ROLE");
        return m;
    }
    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/STUDENT_ROLE",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public Map test()
    {
        Map m=new HashMap<String,String>();
        m.put("data","STUDENT_ROLE");
        return m;
    }
    @RequestMapping(value="/all",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public Map all()
    {
        Map m=new HashMap<String,String>();
        m.put("data","all");
        return m;
    }
    @RolesAllowed({STUDENT_ROLE,UNCERTIFIED_STUDENT_ROLE})
    @RequestMapping(value="/student",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public StudentVO student(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        return new StudentVO(userService.getStudent(userDetails.getUsername())
                .orElseThrow(() -> new ExceptionNotValid("用户不存在"))
        );
    }
    @RolesAllowed({TEACHER_ROLE,UNCERTIFIED_TEACHER_ROLE,ADMIN_ROLE})
    @RequestMapping(value="/teacher",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public TeacherVO teacher(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        return new TeacherVO(userService.getTeacher(userDetails.getUsername())
                .orElseThrow(() -> new ExceptionNotValid("用户不存在"))
        );
    }
    @RolesAllowed({STUDENT_ROLE})
    @RequestMapping(value="/student/modifyInfo",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public UserVO modifyStudentInfo(@AuthenticationPrincipal CustomUserDetails userDetails,@RequestBody Map nameAndNumber, Errors errors) throws ServiceException {
        if(nameAndNumber.size()==0||nameAndNumber.get("name")==null||nameAndNumber.get("number")==null){
            throw new ExceptionNotValid("未填写姓名或者号码");
        }
        return new StudentVO(
                userService.modifyStudentInfo(
                        (String) nameAndNumber.get("name"),
                        (String)nameAndNumber.get("number"),
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("姓名或者号码不符合规范"))
        );

    }

    @RolesAllowed({TEACHER_ROLE})
    @RequestMapping(value="/teacher/modifyInfo",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public UserVO modifyTeacherInfo(@AuthenticationPrincipal CustomUserDetails userDetails,@RequestBody Map name, Errors errors) throws ServiceException {
        if(name.size()==0||name.get("name")==null){
            throw new ExceptionNotValid("未填写姓名");
        }
        return new TeacherVO(
                userService.modifyTeacherInfo(
                        (String) name.get("name"),
                        userDetails.getUsername()
                ).orElseThrow(() -> new ExceptionNotValid("要选的课程不存在"))
        );

    }
}
