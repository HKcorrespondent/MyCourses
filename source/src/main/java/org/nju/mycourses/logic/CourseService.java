package org.nju.mycourses.logic;

import org.nju.mycourses.data.*;
import org.nju.mycourses.data.entity.Course;
import org.nju.mycourses.data.entity.Document;
import org.nju.mycourses.data.entity.PublishedCourse;
import org.nju.mycourses.data.entity.State;
import org.nju.mycourses.logic.util.EmailService;
import org.nju.mycourses.web.controller.dto.PublishedCourseDTO;
import org.nju.mycourses.web.controller.vo.DocumentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CourseService
 * @PackageName org.nju.mycourses.logic
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Service
public class CourseService {
    private final UserDAO userDAO;
    private final StudentDAO studentDAO;
    private final TeacherDAO teacherDAO;
    private final CourseDAO courseDAO;
    private final EmailService emailService;
    private final DocumentDAO documentDAO;
    private final PublishedCourseDAO publishedCourseDAO;
    @Autowired
    public CourseService(UserDAO userDAO, EmailService emailService, StudentDAO studentDAO, TeacherDAO teacherDAO, CourseDAO courseDAO, DocumentDAO documentDAO, PublishedCourseDAO publishedCourseDAO) {
        this.userDAO = userDAO;
        this.emailService = emailService;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
        this.courseDAO = courseDAO;
        this.documentDAO = documentDAO;
        this.publishedCourseDAO = publishedCourseDAO;
    }

    public Optional<Course> createCourse(String name, String username) {
        if(courseDAO.findByName(name).isPresent()){
            return Optional.empty();
        }

        Course course=new Course();
        course.setName(name);
        course.setTeacher(teacherDAO.findById(username).get());
        course.setDocs(new ArrayList<>());
        course.setForums(new ArrayList<>());
        course.setState(State.UNCERTIFIED);
        final Course saved = courseDAO.save(course);
        return Optional.of(saved);
    }

    public Optional<Course> passCourse(Integer id, String username) {
        final Optional<Course> byId = courseDAO.findById(id);
        if(!byId.isPresent()){
            return Optional.empty();
        }else{
            Course course =byId.get();
            course.setState(State.INFORCE);
            final Course saved = courseDAO.save(course);
            return Optional.of(saved);
        }

    }

    public Optional<Course> postDoc(List<DocumentVO> addDocs, Integer id, String username) {
        final Optional<Course> byId = courseDAO.findById(id);
        if(!byId.isPresent()){
            return Optional.empty();
        }else{
            Course course =byId.get();
            List<Document> docs = course.getDocs();
            if(docs==null){docs=new ArrayList<>();}
            for(DocumentVO docVO: addDocs){
                Document document=new Document();
                document.setName(docVO.getName());
                document.setPath(docVO.getPath());
                document.setType(docVO.getType());
                final Document savedDoc = documentDAO.save(document);
                docs.add(savedDoc);
            }
            course.setDocs(docs);
            final Course saved = courseDAO.save(course);
            return Optional.of(saved);
        }
    }

    public Optional<PublishedCourse> publishCourse(PublishedCourseDTO courseDTO, Integer id, String username) {
        final Optional<Course> byId = courseDAO.findById(id);
        if(!byId.isPresent()){
            return Optional.empty();
        }
        PublishedCourse publishedCourse =new PublishedCourse();
        BeanUtils.copyProperties(courseDTO, publishedCourse);
        publishedCourse.setStudents(new ArrayList<>());
        publishedCourse.setUpHomework(new ArrayList<>());
        publishedCourse.setState(State.UNCERTIFIED);
        publishedCourse.setCourse(byId.get());
        final PublishedCourse saved = publishedCourseDAO.save(publishedCourse);
        return Optional.of(saved);
    }

    public Optional<PublishedCourse> passPublishedCourse(Integer publishId, Integer id, String username) {
        final Optional<PublishedCourse> byId = publishedCourseDAO.findById(publishId);
        if(!byId.isPresent()){
            return Optional.empty();
        }else{
            PublishedCourse publishedCourse =byId.get();
            publishedCourse.setState(State.INFORCE);
            final PublishedCourse saved = publishedCourseDAO.save(publishedCourse);
            return Optional.of(saved);
        }
    }
}
