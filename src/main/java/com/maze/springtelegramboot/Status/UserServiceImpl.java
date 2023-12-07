package com.maze.springtelegramboot.Status;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserAssembler userAssembler;
    PagedResourcesAssembler pagedResourcesAssembler;

    @Override
    public CollectionModel<UserDTO> findAll(int page, int size) {
        PageRequest pageRequest;
        pageRequest = PageRequest.of(page, size);
        Page<User> statuses = userRepository.findAllByOrderByIdDesc(pageRequest);
        if (!CollectionUtils.isEmpty(statuses.getContent()))
            return pagedResourcesAssembler.toModel(statuses, userAssembler);
        return null;
    }

    public UserDTO addStatus(User user) {
        return userAssembler.toModel(userRepository.save(user));
    }

    @Override
    public Optional<User> findStatusById(String id) {
       return userRepository.findById(id);

    }

    @Override
    public UserDTO updateStatus(Long id, User user) {
//        Status oldData = statusRepository.findById(id).orElseThrow(()->new EntityNotFoundException(Status.class,"Id",String.valueOf(id)));
//        if (oldData != null) {
//            BeanUtils.copyProperties(status,oldData,getNullPropertyNames(status));
//            return  addStatus(oldData);
//        }
        return null;    }

    @Override
    public UserDTO deleteStatusById(String userName) {
        userRepository.deleteById(userName);
        return null;
    }
}
