package org.nju.mycourses.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import org.nju.mycourses.data.entity.Course;
import org.nju.mycourses.data.entity.PublishedCourse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TotalCourseVO
 * @PackageName org.nju.mycourses.web.controller.vo
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
@Data
public class TotalCourseVO {
    @JsonUnwrapped
    private CourseVO courseVO;
    @JsonProperty("publishedCourses")
    private List<PublishedCourseOutlineVO> publishedCourses;

    public TotalCourseVO(Course courseVO, List<PublishedCourse> publishedCourses) {
        this.courseVO = new CourseVO(courseVO);
        this.publishedCourses = publishedCourses.stream().map(PublishedCourseOutlineVO::new).collect(Collectors.toList());
    }
}
