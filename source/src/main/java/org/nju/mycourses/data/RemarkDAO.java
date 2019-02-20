package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Remark;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName RemarkDAO
 * @PackageName org.nju.mycourses.data
 * @Author sheen
 * @Date 2019/2/20
 * @Version 1.0
 * @Description //TODO
 **/
public interface RemarkDAO extends JpaRepository<Remark,Integer> {
}
