package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.UpHomework;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName UpHomeworkDAO
 * @PackageName org.nju.mycourses.data
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
public interface UpHomeworkDAO extends JpaRepository<UpHomework,String> {
}
