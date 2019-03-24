package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Log;
import org.nju.mycourses.data.entity.PublishedCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @InterfaceName LogDAO
 * @PackageName org.nju.mycourses.data
 * @Author sheen
 * @Date 2019/3/25
 * @Version 1.0
 * @Description //TODO
 **/
public interface LogDAO extends JpaRepository<Log,Integer> {
    List<Log> findAllByUsername(String username);
}
