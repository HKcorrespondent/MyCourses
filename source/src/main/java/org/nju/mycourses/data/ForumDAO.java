package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName ForumDAO
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
public interface ForumDAO extends JpaRepository<Forum,Integer> {
}
