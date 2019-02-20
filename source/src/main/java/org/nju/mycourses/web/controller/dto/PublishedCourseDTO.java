package org.nju.mycourses.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
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
public class PublishedCourseDTO {


    @JsonProperty("startAt")
//    @JsonDeserialize(using = TimestampLocalDateDeserializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "填写课程开始时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime startAt;
    @JsonProperty("endAt")
//    @JsonDeserialize(using = TimestampLocalDateDeserializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "填写课程结束时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime endAt;

    @JsonProperty("selectStart")
//    @JsonDeserialize(using = TimestampLocalDateDeserializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "填写选课开始时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime selectStart;
    @JsonProperty("selectEnd")
//    @JsonDeserialize(using = TimestampLocalDateDeserializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "填写选课结束时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime selectEnd;

    @JsonProperty("classNumLimit")
    private Integer classNumLimit;
    @JsonProperty("studentEachClassLimit")
    private Integer studentEachClassLimit;
    @JsonProperty("description")
    private String description;
    @JsonProperty("longName")
    private String longName;
}
