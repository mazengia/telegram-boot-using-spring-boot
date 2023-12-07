package com.maze.springtelegramboot.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String phoneNumber;

}
