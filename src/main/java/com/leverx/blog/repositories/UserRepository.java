package com.leverx.blog.repositories;

import com.leverx.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Andrey Egorov
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
