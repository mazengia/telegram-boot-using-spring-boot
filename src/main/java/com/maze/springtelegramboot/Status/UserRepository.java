package com.maze.springtelegramboot.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String id);
    Page<User> findAllByOrderByIdDesc(Pageable pageable);
     User findAllByUserName(String chatId);
}