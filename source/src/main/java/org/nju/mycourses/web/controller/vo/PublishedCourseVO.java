package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.nju.mycourses.data.entity.PublishedCourse;
import org.nju.mycourses.data.entity.State;
import org.nju.mycourses.data.entity.Teacher;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @JsonProperty("state")
    private State state;
    @JsonProperty("className")
    public String className="未知或者无意义";
    private Map<String,Integer> examScore=new HashMap<>();

    private String examOpen="true";
    public PublishedCourseVO(PublishedCourse publishedCourse) {
        this.courseVO=new CourseVO(publishedCourse.getCourse());
        BeanUtils.copyProperties(publishedCourse, this);
    }
}
