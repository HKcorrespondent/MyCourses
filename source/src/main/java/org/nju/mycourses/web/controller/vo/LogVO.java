package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.nju.mycourses.data.entity.Forum;
import org.nju.mycourses.data.entity.Log;
import org.nju.mycourses.data.entity.Role;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @ClassName LogVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/3/25
 * @Version 1.0
 * @Description //TODO
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogVO {
    private Integer id;
    private String type;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yy-MM-dd HH:mm")
    private LocalDateTime time;
    private String massage;
    private Role role;
    private String username;
    public LogVO(Log log
    ) {
        BeanUtils.copyProperties(log,this);
    }
}
