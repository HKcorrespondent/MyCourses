package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.nju.mycourses.data.entity.Course;
import org.nju.mycourses.data.entity.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CourseVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
public class CourseVO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("teacher")
    private TeacherVO teacher;
    @JsonProperty("docs")
    private List<DocumentVO> docs;
    @JsonProperty("forums")
    private List<ForumVO> forums;

    public CourseVO(Course course) {
        this.name = course.getName();
        this.teacher = new TeacherVO(course.getTeacher());
        this.docs = course.getDocs().stream().map(DocumentVO::new).collect(Collectors.toList());
        this.forums = course.getForums().stream().map(ForumVO::new).collect(Collectors.toList());
    }
}
