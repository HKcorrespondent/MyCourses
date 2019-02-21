package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName ScoreDAO
 * @PackageName org.nju.mycourses.data
 * @Author sheen
 * @Date 2019/2/21
 * @Version 1.0
 * @Description //TODO
 **/
public interface ScoreDAO extends JpaRepository<Score,Integer> {
}
