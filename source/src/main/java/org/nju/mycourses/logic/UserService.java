package org.nju.mycourses.logic;

import org.apache.commons.codec.digest.DigestUtils;
import org.nju.mycourses.data.*;
import org.nju.mycourses.data.entity.*;
import org.nju.mycourses.logic.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final StudentDAO studentDAO;
    private final TeacherDAO teacherDAO;
    private final EmailService emailService;
    private final LogDAO __LOGDAO__;
    @Autowired
    public UserService(UserDAO userDAO, EmailService emailService, StudentDAO studentDAO, TeacherDAO teacherDAO, LogDAO logdao__) {
        this.userDAO = userDAO;
        this.emailService = emailService;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
        __LOGDAO__ = logdao__;
    }
    @Transactional
    public Optional<User> Register(String email, String password, Role role){
        //todo:检查状态
        User newUser=new User();
        final Optional<User> byId = userDAO.findById(email);
        if(byId.isPresent()){
            if(!byId.get().getState().equals(State.CANCELLED)){
                return Optional.empty();
            }
            else{
                newUser=byId.get();
            }
        }

        newUser.setUsername(email);
        newUser.setPassword(DigestUtils.sha256Hex(password));
        newUser.setRole(role);
        newUser.setState(State.UNCERTIFIED);
        String certifiedCode=UUID.randomUUID().toString().replace("-", "").substring(2,8);
        newUser.setCERTIFIED_CODE(certifiedCode);
        emailService.send(email,"欢迎注册myCourses平台!","您的验证码是:"+certifiedCode);
        final User user = userDAO.save(newUser);
        if(role.equals(Role.STUDENT)){
            Student student=new Student();
            student.setUsername(email);
            student.setUser(user);
            studentDAO.save(student);
        }else if(role.equals(Role.TEACHER)){
            Teacher teacher=new Teacher();
            teacher.setUsername(email);
            teacher.setUser(user);
            teacherDAO.save(teacher);
        }

        __LOGDAO__.save(new Log("注册",user.getUsername()+":"+user.getCERTIFIED_CODE(),user));
        return Optional.of(user);
    }
    public Optional<User> registerAdmin(String username, String password) {
        if(userDAO.findById(username).isPresent()){
            return Optional.empty();
        }
        User newUser=new User();
        newUser.setUsername(username);
        newUser.setPassword(DigestUtils.sha256Hex(password));
        newUser.setRole(Role.ADMIN);
        newUser.setState(State.INFORCE);
        newUser.setCERTIFIED_CODE("不需要");
        final User user = userDAO.save(newUser);
        return Optional.of(user);
    }
    public Optional<User> validateEmail(String code, String username) {
        final Optional<User> user = userDAO.findById(username);
        if(user.isPresent()){
            User rUser=user.get();
            if(rUser.getCERTIFIED_CODE().equals(code)){
                rUser.setState(State.INFORCE);
                __LOGDAO__.save(new Log("验证邮箱",rUser.getUsername()+":"+rUser.getCERTIFIED_CODE(),rUser));
                return Optional.of(userDAO.save(rUser));
            }else{
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }

    public Optional<Student> modifyStudentInfo(String name, String number, String username) {
        final Optional<Student> byId = studentDAO.findById(username);
        if(!byId.isPresent()){
            return Optional.empty();
        }else{
            Student student=byId.get();
            student.setName(name);
            student.setNumber(number);
            final Student saved = studentDAO.save(student);

            __LOGDAO__.save(new Log("修改用户信息",student.getUser().getUsername(),student.getUser()));
            return Optional.of(saved);
        }

    }

    public Optional<Teacher> modifyTeacherInfo(String name, String username) {
        final Optional<Teacher> byId = teacherDAO.findById(username);
        if(!byId.isPresent()){
            return Optional.empty();
        }else{
            Teacher teacher=byId.get();
            teacher.setName(name);
            final Teacher saved = teacherDAO.save(teacher);
            __LOGDAO__.save(new Log("修改用户信息",teacher.getUser().getUsername(),teacher.getUser()));
            return Optional.of(saved);
        }
    }

    public Optional<Student> getStudent(String username) {
        return studentDAO.findById(username);
    }

    public Optional<Teacher> getTeacher(String username) {
        final Optional<User> user = userDAO.findById(username);
        if(user.isPresent()){
            if(user.get().getRole().equals(Role.ADMIN)){
                Teacher admin=new Teacher();
                admin.setUser(user.get());
                admin.setName("admin");
                admin.setUsername(user.get().getUsername());
                return Optional.of(admin);
            }
        }
        return teacherDAO.findById(username);
    }

    public Optional<User> cancel(String username) {
        final Optional<User> user = userDAO.findById(username);
        if(user.isPresent()){
            User rUser=user.get();
            rUser.setState(State.CANCELLED);
            __LOGDAO__.save(new Log("注销",rUser.getUsername(),rUser));
                return Optional.of(userDAO.save(rUser));
        }else{
                return Optional.empty();
        }
    }

    public List<Log> checkLog(String username) {
        if(username.equals("admin")){
            return __LOGDAO__.findAll();
        }else{
            return __LOGDAO__.findAllByUsername(username);
        }
    }

}
