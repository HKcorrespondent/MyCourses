package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName HomeworkDAO
 * @PackageName org.nju.mycourses.data
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
public interface HomeworkDAO extends JpaRepository<Homework,Integer> {
}
