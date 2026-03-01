package io.github.roussel030.module.user.service;

import io.github.roussel030.shared.dto.PageResponse;
import io.github.roussel030.module.user.dto.UserAdminRequest;
import io.github.roussel030.module.user.dto.UserRequest;
import io.github.roussel030.module.user.dto.UserResponse;

public interface UserService {

    UserResponse createUserAsUser(UserRequest request);
    UserResponse createUserAsAdmin(UserAdminRequest request);
    PageResponse<UserResponse> getUsers(int page, int size);
    UserResponse updateUserAsAdmin(Long id, UserAdminRequest request);
    void deleteUser(Long id);

}
