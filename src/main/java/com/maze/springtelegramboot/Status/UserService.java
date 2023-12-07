package com.maze.springtelegramboot.Status;

import org.springframework.hateoas.CollectionModel;

import java.util.Optional;

public interface UserService {
    CollectionModel<UserDTO> findAll(int page, int size );

    UserDTO addStatus(User user);

    Optional<User> findStatusById(String id);
    UserDTO updateStatus(Long id, User user);

    UserDTO deleteStatusById(String id);
}
