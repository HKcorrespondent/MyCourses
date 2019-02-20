package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.nju.mycourses.data.entity.PublishedCourse;
import org.nju.mycourses.data.entity.State;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @ClassName PublishedCourseOutlineVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedCourseOutlineVO {
    private Integer id;
    //    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    //    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;
    //    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime selectStart;
    //    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime selectEnd;
    private Integer classNumLimit;
    private Integer studentEachClassLimit;
    private Integer studentTotalNum;
    @JsonProperty("class")
    private Map<String,Integer> classMap;
    private String description;
    private String longName;
    private State state;
    public PublishedCourseOutlineVO(PublishedCourse publishedCourse) {
        BeanUtils.copyProperties(publishedCourse, this);
    }
}
