package com.maze.springtelegramboot.Status;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public interface UserAPI {
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    UserDTO updateStatus(@PathVariable long id, @RequestBody User user);

    @GetMapping("/{id}")
    Optional<User> findStatusById(@PathVariable String id);
    @DeleteMapping("/{id}")
    ResponseEntity<UserDTO> deleteStatus(@PathVariable String id);

    @GetMapping
    ResponseEntity<CollectionModel<UserDTO>> findAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size);
    @PostMapping
    UserDTO addStatus(@RequestBody User user);

}
