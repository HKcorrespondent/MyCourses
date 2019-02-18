package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDAO extends JpaRepository<Teacher,String> {
}
