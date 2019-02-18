package org.nju.mycourses.data;

import org.nju.mycourses.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDAO extends JpaRepository<Student,String> {
}
