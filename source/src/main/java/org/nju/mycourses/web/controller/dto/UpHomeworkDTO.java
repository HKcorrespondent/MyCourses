package org.nju.mycourses.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.web.controller.vo.StudentVO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
public class UpHomeworkDTO {
    //path是按学号保存的
    private String path;

    private String name;

    private String type;

}
