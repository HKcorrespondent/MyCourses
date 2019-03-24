package org.nju.mycourses.logic;

import org.nju.mycourses.data.*;
import org.nju.mycourses.data.entity.*;
import org.nju.mycourses.logic.util.EmailService;
import org.nju.mycourses.web.controller.dto.ForumDTO;
import org.nju.mycourses.web.controller.dto.RemarkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName ForumService
 * @PackageName org.nju.mycourses.logic
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@Service
public class ForumService {

    private final UserDAO userDAO;
    private final StudentDAO studentDAO;
    private final TeacherDAO teacherDAO;
    private final CourseDAO courseDAO;
    private final EmailService emailService;
    private final DocumentDAO documentDAO;
    private final PublishedCourseDAO publishedCourseDAO;
    private final HomeworkDAO homeworkDAO;
    private final UpHomeworkDAO upHomeworkDAO;
    private final ForumDAO forumDAO;
    private final RemarkDAO remarkDAO;
    private final LogDAO __LOGDAO__;
    @Autowired
    public ForumService(UserDAO userDAO, EmailService emailService, StudentDAO studentDAO,
                        TeacherDAO teacherDAO, CourseDAO courseDAO, DocumentDAO documentDAO,
                        PublishedCourseDAO publishedCourseDAO, HomeworkDAO homeworkDAO,
                        UpHomeworkDAO upHomeworkDAO, ForumDAO forumDAO, RemarkDAO remarkDAO, LogDAO logdao__) {
        this.userDAO = userDAO;
        this.emailService = emailService;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
        this.courseDAO = courseDAO;
        this.documentDAO = documentDAO;
        this.publishedCourseDAO = publishedCourseDAO;
        this.homeworkDAO = homeworkDAO;
        this.upHomeworkDAO = upHomeworkDAO;
        this.forumDAO = forumDAO;
        this.remarkDAO = remarkDAO;
        __LOGDAO__ = logdao__;
    }

    public Optional<Forum> postForum(ForumDTO forumDTO, Integer id, String username) {
        final Optional<User> byId = userDAO.findById(username);
        final Optional<Course> courseOptional = courseDAO.findById(id);
        if(!byId.isPresent()||!courseOptional.isPresent()){
            return Optional.empty();
        }
        Course course=courseOptional.get();
        User user=byId.get();
        Forum forum=new Forum();
        forum.setName(forumDTO.getName());
        forum.setNumber(0);
        forum.setStartAt(LocalDateTime.now());
        forum.setUsername(user.getUsername());
        forum.setRemarks(new ArrayList<>());
        final Forum savedForum = forumDAO.save(forum);
        course.getForums().add(savedForum);
        courseDAO.save(course);
        RemarkDTO remarkDTO=new RemarkDTO();
        remarkDTO.setContent(forumDTO.getContent());
        __LOGDAO__.save(new Log("论坛发帖",id+":标题"+forumDTO.getName(),user));
        return postRemark(remarkDTO,id,savedForum.getId(),username);
    }
    public Optional<Forum> postRemark(RemarkDTO remarkDTO, Integer id, Integer forumId, String username) {
        final Optional<User> userOptional = userDAO.findById(username);
        Optional<Forum> forumOptional = forumDAO.findById(forumId);
        if(!userOptional.isPresent()||!forumOptional.isPresent()){
            return Optional.empty();
        }
        User user=userOptional.get();
        Forum forum=forumOptional.get();
        Integer num=forum.getNumber();
        num++;
        Remark remark=new Remark();
        remark.setUsername(user.getUsername());
        remark.setStartAt(LocalDateTime.now());
        remark.setRole(user.getRole());
        remark.setContent(remarkDTO.getContent());
        remark.setNum(num);
        final Remark savedRemark = remarkDAO.save(remark);
        List<Remark> remarks = forum.getRemarks();
        if(remarks==null){remarks=new ArrayList<>();}
        remarks.add(savedRemark);
        forum.setRemarks(remarks);
        forum.setNumber(num);
        __LOGDAO__.save(new Log("论坛回帖",id+":帖子id"+forumId,user));
        return Optional.of(forumDAO.save(forum));
    }

    public List<Forum> getForumOutline(Integer id, String username) {
        final Optional<User> byId = userDAO.findById(username);
        final Optional<Course> courseOptional = courseDAO.findById(id);
        if(!byId.isPresent()||!courseOptional.isPresent()){
            return new ArrayList<>();
        }
        Course course=courseOptional.get();
        User user=byId.get();
        return course.getForums();
    }

    public Optional<Forum> getForum(Integer forumId, Integer id, String username) {
        final Optional<Forum> byId = forumDAO.findById(forumId);
        return byId;
    }
}
