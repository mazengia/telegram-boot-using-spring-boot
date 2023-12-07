package com.maze.springtelegramboot.Status;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/status")
public class UserController implements UserAPI {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDTO updateStatus(long id, User user) {
        return userService.updateStatus(id, user);
    }

    @Override
    public Optional<User> findStatusById(String id) {
        return userService.findStatusById(id);
    }
    @Override
    public ResponseEntity<UserDTO> deleteStatus(String id) {
        UserDTO userDTO = userService.deleteStatusById(id);
        if (userDTO != null) return ResponseEntity.ok(userDTO);
        return ResponseEntity.noContent().build();
    }
    @Override
    public ResponseEntity<CollectionModel<UserDTO>> findAll(Integer page, Integer size) {
        {
            CollectionModel<UserDTO> statusDTOS = userService.findAll(page, size);
            if (statusDTOS != null)
                return ResponseEntity.ok(statusDTOS);
            return ResponseEntity.noContent().build();
        }
    }


    @Override
    public UserDTO addStatus(User user) {
        return userService.addStatus(user);
    }
}
