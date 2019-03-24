package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.nju.mycourses.data.entity.Student;

/**
 * @ClassName StudentVO
 * @PackageName org.nju.mycourses.web.controller
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@EqualsAndHashCode(callSuper = true)
@Data()
public class StudentVO extends UserVO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("number")
    private String number;

    @JsonProperty("score")
    private String examScore="0";

    @JsonProperty("class")
    private String className="未知";

    public StudentVO(Student student) {
        super(student.getUser());
        this.name = student.getName();
        this.number =student.getNumber();
    }
}
