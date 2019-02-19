package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.PublishedCourse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName PublishedCourseDAO
 * @PackageName org.nju.mycourses.data
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
public interface PublishedCourseDAO extends JpaRepository<PublishedCourse,Integer> {
}
