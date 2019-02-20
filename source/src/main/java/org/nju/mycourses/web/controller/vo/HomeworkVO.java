package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.data.entity.Homework;
import org.nju.mycourses.data.entity.UpHomework;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName HomeworkDTO
 * @PackageName org.nju.mycourses.web.controller.dto
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeworkVO {
    @JsonProperty("forum_id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("type")
    private String type;
    /**
     * 作业可以开始提交时间
     */
    @JsonProperty("startAt")
    //    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请填写作业开始提交时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime startAt;
    /**
     * 作业截止时间
     */
    @JsonProperty("endAt")
    //    @JsonSerialize(using = TimestampLocalDateTimeSerializer.class)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请填写作业截止提交时间", groups = {PostMapping.class, PutMapping.class})
    private LocalDateTime endAt;
    /**
     * 提交的作业
     */
    @JsonProperty("upHomeworkVO")
    private List<UpHomeworkVO> upHomeworkVO;

    public HomeworkVO(Homework homework) {
        this.upHomeworkVO=homework.getUpHomework().stream().map(UpHomeworkVO::new).collect(Collectors.toList());
        BeanUtils.copyProperties(homework,this);

    }
}
