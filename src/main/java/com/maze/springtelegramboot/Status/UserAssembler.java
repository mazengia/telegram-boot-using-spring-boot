package com.maze.springtelegramboot.Status;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, UserDTO> {

    @Override
    public UserDTO toModel(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getPhoneNumber()
        );
    }
}
