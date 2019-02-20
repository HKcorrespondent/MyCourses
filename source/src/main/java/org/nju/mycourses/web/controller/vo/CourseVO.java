package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.data.entity.Course;
import org.nju.mycourses.data.entity.State;
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
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseVO {
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("teacher")
    private TeacherVO teacher;
    @JsonProperty("docs")
    private List<DocumentVO> docs;
    @JsonProperty("forums")
    private List<ForumVO> forums;
    @JsonProperty("state")
    private State state;
    public CourseVO(Course course) {
        this.id=course.getId();
        this.state=course.getState();
        this.name = course.getName();
        this.teacher = new TeacherVO(course.getTeacher());
        this.docs = course.getDocs().stream().map(DocumentVO::new).collect(Collectors.toList());
        this.forums = course.getForums().stream().map(ForumVO::new).collect(Collectors.toList());
    }
}
