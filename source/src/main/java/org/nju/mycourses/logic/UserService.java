package org.nju.mycourses.logic;

import org.apache.commons.codec.digest.DigestUtils;
import org.nju.mycourses.data.*;
import org.nju.mycourses.data.entity.*;
import org.nju.mycourses.logic.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final StudentDAO studentDAO;
    private final TeacherDAO teacherDAO;
    private final EmailService emailService;
    @Autowired
    public UserService(UserDAO userDAO, EmailService emailService, StudentDAO studentDAO, TeacherDAO teacherDAO) {
        this.userDAO = userDAO;
        this.emailService = emailService;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
    }
    @Transactional
    public Optional<User> Register(String email, String password, Role role){
        //todo:检查状态
        if(userDAO.findById(email).isPresent()){
            return Optional.empty();
        }
        User newUser=new User();
        newUser.setUsername(email);
        newUser.setPassword(DigestUtils.sha256Hex(password));
        newUser.setRole(role);
        newUser.setState(State.UNCERTIFIED);
        String certifiedCode=UUID.randomUUID().toString().replace("-", "").substring(5);
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
        return Optional.of(user);
    }

    public Optional<User> validateEmail(String code, String username) {
        final Optional<User> user = userDAO.findById(username);
        if(user.isPresent()){
            User rUser=user.get();
            if(rUser.getCERTIFIED_CODE().equals(code)){
                rUser.setState(State.INFORCE);
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
            return Optional.of(saved);
        }
    }
}
