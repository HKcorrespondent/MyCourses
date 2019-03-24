package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.data.entity.PublishedCourse;
import org.nju.mycourses.data.entity.State;
import org.nju.mycourses.data.entity.Student;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName PublishedCourseDetailVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedCourseDetailVO {

    private Integer id;
    @JsonProperty("startAt")
//    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    @JsonProperty("endAt")
//    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    @JsonProperty("selectStart")
//    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime selectStart;
    @JsonProperty("selectEnd")
//    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime selectEnd;

    private Integer classNumLimit;
    private Integer studentEachClassLimit;
    private Integer studentTotalNum;
    @JsonProperty("description")
    private String description;
    @JsonProperty("longName")
    private String longName;

    @JsonProperty("course")
    public CourseVO courseVO;

    @JsonProperty("className")
    public String className="未知或者无意义";
    private Map<String,Integer> examScore=new HashMap<>();

    private String examOpen="true";
    @JsonProperty("state")
    private State state;
    @JsonProperty("homework")
    private List<HomeworkVO> homework;
    @JsonProperty("students")
    private List<StudentVO> students;
    public PublishedCourseDetailVO(PublishedCourse publishedCourse) {
        this.courseVO = new CourseVO(publishedCourse.getCourse());
        this.homework = publishedCourse.getHomework().stream().map(HomeworkVO::new).collect(Collectors.toList());
        this.students = publishedCourse.getStudents().stream().map(e->{
            StudentVO student=new StudentVO(e);
            final String un=student.getUsername();
            final String uper = un.substring(0,un.indexOf("@"));
            if(publishedCourse.getExamScore()!=null&&publishedCourse.getExamScore().get(uper)!=null){
                student.setExamScore(publishedCourse.getExamScore().get(uper).toString());
            }
            if(publishedCourse.getSt2ClassName()!=null&&publishedCourse.getSt2ClassName().get(un)!=null){
                student.setClassName(publishedCourse.getSt2ClassName().get(un));
            }
            return student;
        }).collect(Collectors.toList());
        this.id = publishedCourse.getId();
        this.startAt = publishedCourse.getStartAt();
        this.endAt = publishedCourse.getEndAt();
        this.selectStart = publishedCourse.getSelectStart();
        this.selectEnd = publishedCourse.getSelectEnd();
        this.classNumLimit = publishedCourse.getClassNumLimit();
        this.studentEachClassLimit = publishedCourse.getStudentEachClassLimit();
        this.studentTotalNum = publishedCourse.getStudentTotalNum();
        this.description = publishedCourse.getDescription();
        this.longName = publishedCourse.getLongName();
        this.state = publishedCourse.getState();
        this.className=publishedCourse.getClassName();
        this.examScore=publishedCourse.getExamScore();
        this.examOpen=publishedCourse.getExamOpen();
    }

}
