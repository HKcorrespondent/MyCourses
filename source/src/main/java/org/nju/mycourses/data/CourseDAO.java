package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Course;
import org.nju.mycourses.data.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @InterfaceName CourseDAO
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
public interface CourseDAO extends JpaRepository<Course,Integer> {
    Optional<Course> findByName(String name);
    List<Course> findAllByTeacher(Teacher teacher);
}
