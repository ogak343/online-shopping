package com.example.onlineshopping.mapper;

import com.example.onlineshopping.contants.Role;
import com.example.onlineshopping.dto.request.UserCreateRequest;
import com.example.onlineshopping.dto.request.UserUpdateRequest;
import com.example.onlineshopping.dto.response.UserResponse;
import com.example.onlineshopping.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class UserMapper {

    public User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setRole(Role.USER);
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setVerified(false);
        user.setMiddleName(request.middleName());
        user.setAddress(request.address());
        user.setState(request.state());
        user.setCity(request.city());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getRole(),
                user.getAddress(),
                user.getCity(),
                user.getState());
    }

    public void update(User entity, UserUpdateRequest request) {
        map(request.username(), entity::setUsername);
        map(request.email(), entity::setEmail);
        map(request.phoneNumber(), entity::setPhoneNumber);
        map(request.firstName(), entity::setFirstName);
        map(request.lastName(), entity::setLastName);
        map(request.middleName(), entity::setMiddleName);
        map(request.address(), entity::setAddress);
        map(request.city(), entity::setCity);
        map(request.state(), entity::setState);
    }

    private <T> void map(T field, Consumer<T> action) {
        if (field != null) action.accept(field);
    }
}
