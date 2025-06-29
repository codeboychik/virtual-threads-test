package com.virtualthread.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * File is created by andreychernenko at 21.06.2025
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "select * from user_ order by random() limit 1")
    User getRandomUser();
}
