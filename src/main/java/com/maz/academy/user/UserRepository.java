package com.maz.academy.user;

import com.maz.academy.core.enums.UserRole;
import com.maz.academy.user.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String p);
    List<User> findAllByUserRole(UserRole p);
}