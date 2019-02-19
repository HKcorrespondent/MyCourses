package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName DocumentDAO
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/2/19
 * @Version 1.0
 * @Description //TODO
 **/
public interface DocumentDAO extends JpaRepository<Document,String> {
}
