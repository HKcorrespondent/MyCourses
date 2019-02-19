package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.nju.mycourses.data.entity.PublishedCourse;
import org.nju.mycourses.data.entity.State;
import org.nju.mycourses.data.entity.Teacher;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @ClassName PublishedCourseDTO
 * @PackageName org.nju.mycourses.web.controller
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedCourseVO {


    @JsonProperty("startAt")
    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    private LocalDateTime startAt;
    @JsonProperty("endAt")
    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    private LocalDateTime endAt;

    @JsonProperty("selectStart")
    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    private LocalDateTime selectStart;
    @JsonProperty("selectStart")
    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    private LocalDateTime selectEnd;

    @JsonProperty("classNum")
    private Integer classNum;
    @JsonProperty("studentEachClass")
    private Integer studentEachClass;
    @JsonProperty("description")
    private String description;
    @JsonProperty("longName")
    private String longName;

    @JsonProperty("course")
    public CourseVO courseVO;

    @JsonProperty("state")
    private State state;

    public PublishedCourseVO(PublishedCourse publishedCourse) {
        this.courseVO=new CourseVO(publishedCourse.getCourse());
        BeanUtils.copyProperties(publishedCourse, this);
    }
}