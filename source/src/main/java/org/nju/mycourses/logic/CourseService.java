package org.nju.mycourses.logic;

import org.nju.mycourses.data.*;
import org.nju.mycourses.data.entity.*;
import org.nju.mycourses.logic.exception.ExceptionNotValid;
import org.nju.mycourses.logic.util.EmailService;
import org.nju.mycourses.web.controller.dto.*;
import org.nju.mycourses.web.controller.vo.CourseVO;
import org.nju.mycourses.web.controller.vo.PublishedCourseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private final HomeworkDAO homeworkDAO;
    private final UpHomeworkDAO upHomeworkDAO;
    private final ScoreDAO scoreDAO;
    private final LogDAO __LOGDAO__;
    @Autowired
    public CourseService(UserDAO userDAO, EmailService emailService, StudentDAO studentDAO,
                         TeacherDAO teacherDAO, CourseDAO courseDAO, DocumentDAO documentDAO,
                         PublishedCourseDAO publishedCourseDAO, HomeworkDAO homeworkDAO, UpHomeworkDAO upHomeworkDAO, ScoreDAO scoreDAO, LogDAO logDAO) {
        this.userDAO = userDAO;
        this.emailService = emailService;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
        this.courseDAO = courseDAO;
        this.documentDAO = documentDAO;
        this.publishedCourseDAO = publishedCourseDAO;
        this.homeworkDAO = homeworkDAO;
        this.upHomeworkDAO = upHomeworkDAO;
        this.scoreDAO = scoreDAO;
        this.__LOGDAO__ = logDAO;
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
        __LOGDAO__.save(new Log("创建课程",saved.getId()+":"+saved.getName(),username,Role.TEACHER));
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
            __LOGDAO__.save(new Log("通过课程",saved.getId()+":"+saved.getName(),username,Role.ADMIN));
            return Optional.of(saved);
        }

    }

    public Optional<Course> postDoc(List<DocumentDTO> addDocs, Integer id, String username) {
        final Optional<Course> byId = courseDAO.findById(id);
        if(!byId.isPresent()){
            return Optional.empty();
        }else{
            Course course =byId.get();
            List<Document> docs = course.getDocs();
            if(docs==null){docs=new ArrayList<>();}
            for(DocumentDTO docDTO: addDocs){
                Document document=new Document();
                document.setName(docDTO.getName());
                document.setPath(docDTO.getPath());
                document.setType(docDTO.getType());
                final Document savedDoc = documentDAO.save(document);
                docs.add(savedDoc);
            }
            course.setDocs(docs);
            final Course saved = courseDAO.save(course);

            __LOGDAO__.save(new Log("上传课件",saved.getId()+":"+saved.getName()+":数量-"+addDocs.size(),username,Role.TEACHER));
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
        publishedCourse.setHomework(new ArrayList<>());
        publishedCourse.setState(State.UNCERTIFIED);
        publishedCourse.setCourse(byId.get());
        publishedCourse.setStudentTotalNum(0);
        Map<String,Integer> classMap=new HashMap<>();
        for(int i=1;i<=courseDTO.getClassNumLimit();i++){
            classMap.put(""+i,0);
        }
        publishedCourse.setClassMap(classMap);
        final PublishedCourse saved = publishedCourseDAO.save(publishedCourse);
        __LOGDAO__.save(new Log("发布课程",saved.getId()+":"+saved.getLongName(),username,Role.TEACHER));
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

            __LOGDAO__.save(new Log("通过发布课程",saved.getId()+":"+saved.getLongName(),username,Role.ADMIN));
            return Optional.of(saved);
        }
    }

    public Optional<Course> getCourse(Integer id, String username) {
        return courseDAO.findById(id);
    }

    public List<PublishedCourse> getPublishedCourses(Integer id, String username) {
        final Optional<Course> byId = courseDAO.findById(id);
        if(!byId.isPresent()){
            return new ArrayList<>();
        }
        return publishedCourseDAO.findAllByCourse(byId.get());
    }

    public List<PublishedCourse> getCouldSelectCourse(String username) {
        Optional<Student> byId = studentDAO.findById(username);
        if(!byId.isPresent()){
            return new ArrayList<>();
        }
        Student student=byId.get();
        Set<PublishedCourse> courses = student.getCourses();
        List<PublishedCourse> all = publishedCourseDAO.findAllByState(State.INFORCE);
        all.removeAll(courses);
        LocalDateTime now=LocalDateTime.now();
        List<PublishedCourse> ret=new ArrayList<>();
        for(PublishedCourse c:all){
            if(now.isAfter(c.getSelectStart())&&now.isBefore(c.getSelectEnd())){
                if(c.getStudentTotalNum()<c.getClassNumLimit()*c.getStudentEachClassLimit()){
                    ret.add(c);
                }
            }
        }
        return ret;

    }

    public List<Course> getTeacherCourse(String username) {
        Optional<Teacher> byId = teacherDAO.findById(username);
        if(!byId.isPresent()){
            return new ArrayList<>();
        }
        Teacher teacher=byId.get();
        return courseDAO.findAllByTeacher(teacher);
    }

    public Optional<Homework> postHomework(HomeworkDTO homeworkDTO, Integer id, Integer publishId, String username) {
        Optional<PublishedCourse> byId = publishedCourseDAO.findById(publishId);
        if(!byId.isPresent()){
            return Optional.empty();
        }
        PublishedCourse publishedCourse=byId.get();
        List<Homework> homeworkList = publishedCourse.getHomework();
        if(homeworkList==null){homeworkList=new ArrayList<>();}
        Homework homework=new Homework();
        homework.setState(State.INFORCE);
        homework.setOpen(true);
        homework.setUpHomework(new ArrayList<>());
        BeanUtils.copyProperties(homeworkDTO,homework);
        final Homework savedHomework = homeworkDAO.save(homework);
        homeworkList.add(savedHomework);
        publishedCourse.setHomework(homeworkList);
        publishedCourseDAO.save(publishedCourse);

        __LOGDAO__.save(new Log("布置作业",publishedCourse.getId()+":"+publishedCourse.getLongName()+":"+homeworkDTO.getName(),username,Role.TEACHER));
        return Optional.of(savedHomework);
    }

    public Optional<UpHomework> upHomework(UpHomeworkDTO upHomeworkDTO, Integer id, Integer publishId, Integer homeworkId, String username) {
        Optional<Homework> homeworkOptional = homeworkDAO.findById(homeworkId);
        Optional<Student> studentOptional = studentDAO.findById(username);
        if(!homeworkOptional.isPresent()||!studentOptional.isPresent()){
            return Optional.empty();
        }
        UpHomework upHomework=new UpHomework();
        upHomework.setUper(studentOptional.get());
        upHomework.setUperUsername(studentOptional.get().getUsername());
        upHomework.setUpTime(LocalDateTime.now()) ;
        upHomework.setScore(0);
        upHomework.setState(State.UNCERTIFIED);
        BeanUtils.copyProperties(upHomeworkDTO,upHomework);
        final UpHomework savedHomework = upHomeworkDAO.save(upHomework);
        Homework homework = homeworkOptional.get();
        List<UpHomework> upHomeworkList = homework.getUpHomework();
        if(upHomeworkList==null){upHomeworkList=new ArrayList<>();}
        upHomeworkList.add(savedHomework);
        homework.setUpHomework(upHomeworkList);
        homeworkDAO.save(homework);
        __LOGDAO__.save(new Log("上交作业",publishId+":作业ID-"+homeworkId,username,Role.STUDENT));

        return Optional.of(savedHomework);
    }

    public Optional<PublishedCourse> selectCourse(Integer id, Integer publishId, String username) {
        Optional<PublishedCourse> publishedCourseOptional = publishedCourseDAO.findById(publishId);
        Optional<Student> studentOptional = studentDAO.findById(username);
        if(!publishedCourseOptional.isPresent()||!studentOptional.isPresent()){
            return Optional.empty();
        }
        PublishedCourse publishedCourse=publishedCourseOptional.get();
        Student student=studentOptional.get();
        Set<PublishedCourse> publishedCourseSet = student.getCourses();
        //确定此人没选这门课
        if(publishedCourseSet.contains(publishedCourse)){//todo:
            throw new ExceptionNotValid("已选该课程");
        }
        //确定这门课有位置
        if(publishedCourse.getStudentTotalNum()>=publishedCourse.getStudentEachClassLimit()*publishedCourse.getClassNumLimit()){
            throw new ExceptionNotValid("课程人数已满");
        }
        //分配班级，增加选课人数
        Map<String, Integer> classMap = publishedCourse.getClassMap();
        Integer studentTotalNum = publishedCourse.getStudentTotalNum();
        List<Student> students = publishedCourse.getStudents();

        students.add(student);
        studentTotalNum=studentTotalNum+1;
        List<String> couldAddClass=new ArrayList<>();
        classMap.keySet().forEach(k->{
            if(classMap.get(k)<publishedCourse.getStudentEachClassLimit()){
                couldAddClass.add(k);
            }
        });
        int listNum=(int) (Math.random() * couldAddClass.size());
        String className=couldAddClass.get(listNum);
        classMap.put(className,classMap.get(className)+1);
        publishedCourse.getSt2ClassName().put(student.getUsername(),className);
        publishedCourse.setClassMap(classMap);
        publishedCourse.setStudentTotalNum(studentTotalNum);
        publishedCourse.setStudents(students);
        Map<Integer, String> pcClass = student.getPCClass();
        pcClass.put(publishedCourse.getId(),className);
        final PublishedCourse savedPublishedCourse = publishedCourseDAO.save(publishedCourse);
        studentDAO.save(student);

        __LOGDAO__.save(new Log("选课",publishId+":班级-"+className,username,Role.STUDENT));
        return Optional.of(savedPublishedCourse);
    }
    public Optional<PublishedCourse> unSelectCourse(Integer id, Integer publishId, String username) {
        Optional<PublishedCourse> publishedCourseOptional = publishedCourseDAO.findById(publishId);
        Optional<Student> studentOptional = studentDAO.findById(username);
        if(!publishedCourseOptional.isPresent()||!studentOptional.isPresent()){
            return Optional.empty();
        }
        PublishedCourse publishedCourse=publishedCourseOptional.get();
        Student student=studentOptional.get();
        Set<PublishedCourse> publishedCourseSet = student.getCourses();
        //确定此人选了这门课
        if(!publishedCourseSet.contains(publishedCourse)){//todo:
            throw new ExceptionNotValid("未选该课程");
        }
        String className=student.getPCClass().get(publishedCourse.getId());
        //分配班级，减少选课人数 todo:还要确定他在哪里班级
        Map<String, Integer> classMap = publishedCourse.getClassMap();
        Integer studentTotalNum = publishedCourse.getStudentTotalNum();
        List<Student> students = publishedCourse.getStudents();

        students.remove(student);
        studentTotalNum=studentTotalNum-1;

        classMap.put(className,classMap.get(className)-1);

        Map<Integer, String> pcClass = student.getPCClass();
        pcClass.remove(publishedCourse.getId());

        publishedCourse.getSt2ClassName().remove(student.getUsername());
        publishedCourse.setClassMap(classMap);
        publishedCourse.setStudentTotalNum(studentTotalNum);
        publishedCourse.setStudents(students);

        final PublishedCourse savedPublishedCourse = publishedCourseDAO.save(publishedCourse);
        studentDAO.save(student);
        __LOGDAO__.save(new Log("退课",publishId+":班级-"+className,username,Role.STUDENT));
        return Optional.of(savedPublishedCourse);
    }

    public Optional<Homework> getHomework(Integer hwId,Integer id, Integer publishId, String username) {
        return homeworkDAO.findById(hwId);
    }

    public Optional<PublishedCourse> getPublishedCourse(Integer id, Integer publishId, String username) {
        return publishedCourseDAO.findById(publishId);
    }


    public List<UpHomework> homeworkScore(HomeworkScoreDTO homeworkScoreDTO, Integer id, Integer publishId, Integer homeworkId, String username) {
        Optional<Homework> homeworkOptional = homeworkDAO.findById(homeworkId);
        Optional<Teacher> teacherOptional = teacherDAO.findById(username);
        Optional<PublishedCourse> publishedCourseOptional = publishedCourseDAO.findById(publishId);
        if(!homeworkOptional.isPresent()||!teacherOptional.isPresent()||!publishedCourseOptional.isPresent()){
            return new ArrayList<>();
        }
        Homework homework = homeworkOptional.get();
        List<UpHomework> upHomework = homework.getUpHomework();
        List<Score> shouldSaveScore=new ArrayList<>();
        upHomework.forEach(uh->{
            final String un=uh.getUperUsername();
            final String uper = un.substring(0,un.indexOf("@"));
            Integer score=homeworkScoreDTO.getScores().get(uper);
            __LOGDAO__.save(new Log("作业评分",publishId+":作业号-"+homeworkId+":分数"+score,un,Role.STUDENT));
            if(score==null){score=0;}
            uh.setScore(score);
            Score scoreEntity=new Score(publishedCourseOptional.get(),teacherOptional.get(),
                            uh.getUper(),uh,homeworkOptional.get(),score);
            scoreEntity.setStudentNumber(uper);
            shouldSaveScore.add(scoreEntity);
        });
        homework.setState(State.CANCELLED);
        homework.setOpen(homeworkScoreDTO.getIsOpen());
        homeworkDAO.save(homework);
        scoreDAO.saveAll(shouldSaveScore);
        upHomeworkDAO.saveAll(upHomework);
        __LOGDAO__.save(new Log("上传作业评分",publishId+":作业号-"+homeworkId+":份数"+homeworkScoreDTO.getScores().size(),username,Role.TEACHER));
        return upHomework;
    }

    public void examScore(HomeworkScoreDTO examDTO, Integer cid, Integer publishId, String username) {
        Optional<PublishedCourse> publishedCourseOptional = publishedCourseDAO.findById(publishId);
        if(!publishedCourseOptional.isPresent()){
            return ;
        }
        final PublishedCourse publishedCourse = publishedCourseOptional.get();
        publishedCourse.setExamOpen(examDTO.getIsOpen().toString());
        publishedCourse.setExamScore(examDTO.getScores());
        __LOGDAO__.save(new Log("上传考试评分",publishId+":份数"+examDTO.getScores().size(),username,Role.TEACHER));

        publishedCourseDAO.save(publishedCourse);
    }
    public Integer email2All(EmailDTO emailDTO, Integer id, Integer publishId, String username) {
        Optional<PublishedCourse> publishedCourseOptional = publishedCourseDAO.findById(publishId);
        Optional<Teacher> teacherOptional = teacherDAO.findById(username);
        if(!teacherOptional.isPresent()||!publishedCourseOptional.isPresent()){
            throw new ExceptionNotValid("课程不存在");
        }
        final List<Student> students = publishedCourseOptional.get().getStudents();
        int sandNum=0;
        for(Student student:students){
            emailService.send(student.getUsername(),emailDTO.getTitle(),emailDTO.getContent());
            sandNum++;
        }
        __LOGDAO__.save(new Log("群发邮件",publishId+":标题"+emailDTO.getTitle(),username,Role.TEACHER));
        return sandNum;
    }

    public Optional<PublishedCourse> checkCourse(Integer id, Integer publishId, String username) {
        Optional<PublishedCourse> publishedCourseOptional = publishedCourseDAO.findById(publishId);
        Optional<Student> studentOptional = studentDAO.findById(username);
        if(!publishedCourseOptional.isPresent()||!studentOptional.isPresent()){
            return Optional.empty();
        }
        PublishedCourse publishedCourse=publishedCourseOptional.get();
        Student student=studentOptional.get();
        Set<PublishedCourse> publishedCourseSet = student.getCourses();
        //确定此人选了这门课
        if(!publishedCourseSet.contains(publishedCourse)){//todo:
            return Optional.empty();
        }
        publishedCourse.setClassName(publishedCourse.getSt2ClassName().get(username));
        return Optional.of(publishedCourse);
    }

    public List<PublishedCourse> getStudentCourse(String username) {
        Optional<Student> byId = studentDAO.findById(username);
        if(!byId.isPresent()){
            return new ArrayList<>();
        }
        Student student=byId.get();
        return student.getCourses().stream().peek(e -> e.setClassName(e.getSt2ClassName().get(student.getUsername()))).collect(Collectors.toList());
    }

    public Map adminGetCourse() {
        HashMap<String,Object> ret=new HashMap<>();
        final List<PublishedCourseVO> pcs = publishedCourseDAO.findAllByState(State.UNCERTIFIED).stream().map(PublishedCourseVO::new).collect(Collectors.toList());
        final List<CourseVO> cs = courseDAO.findAllByState(State.UNCERTIFIED).stream().map(CourseVO::new).collect(Collectors.toList());
        ret.put("cs",cs);
        ret.put("pcs",pcs);
        return ret;
    }
}
