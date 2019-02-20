package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.nju.mycourses.data.entity.State;
import org.nju.mycourses.data.entity.Student;
import org.nju.mycourses.data.entity.UpHomework;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @ClassName UpHomeworkVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpHomeworkVO {
    private String id;
    //path是按学号保存的

    private String path;

    private String name;

    private String type;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime upTime;

    private String username;
    private String studentName;
    private Integer score=0;

    private State state;

    public UpHomeworkVO(UpHomework upHomework) {
        this.username=upHomework.getUper().getUsername();
        this.studentName=upHomework.getUper().getName();
        this.id = upHomework.getId();
        this.path = upHomework.getPath();
        this.name = upHomework.getName();
        this.type = upHomework.getType();
        this.upTime = upHomework.getUpTime();
        this.score = upHomework.getScore() ;
        this.state = upHomework.getState() ;
    }


}
