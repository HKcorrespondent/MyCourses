package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.nju.mycourses.data.entity.Teacher;

/**
 * @ClassName TeacherVO
 * @PackageName org.nju.mycourses.web.controller
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@EqualsAndHashCode(callSuper = true)
@Data()
public class TeacherVO  extends UserVO {
    @JsonProperty("name")
    private String name;

    public TeacherVO(Teacher teacher) {
        super(teacher.getUser());
        this.name = teacher.getName();
    }
}