package io.github.roussel030.user.service;

import io.github.roussel030.user.dto.UserAdminRequest;
import io.github.roussel030.user.dto.UserRequest;
import io.github.roussel030.user.dto.UserResponse;

public interface UserService {

    UserResponse createUserAsUser(UserRequest request);
    UserResponse createUserAsAdmin(UserAdminRequest request);

}
