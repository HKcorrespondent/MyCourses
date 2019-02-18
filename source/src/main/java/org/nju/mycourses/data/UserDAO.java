package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,String> {

    User findByUsername(String email);
}
