package io.github.roussel030.module.user.service;

import io.github.roussel030.module.user.dto.UserAdminUpdateRequest;
import io.github.roussel030.shared.dto.PageResponse;
import io.github.roussel030.module.user.dto.UserAdminCreateRequest;
import io.github.roussel030.module.user.dto.UserRequest;
import io.github.roussel030.module.user.dto.UserResponse;

public interface UserService {

    UserResponse createUserAsUser(UserRequest request);
    UserResponse createUserAsAdmin(UserAdminCreateRequest request);
    PageResponse<UserResponse> getUsers(String search, int page, int size);
    UserResponse updateUserAsAdmin(Long id, UserAdminUpdateRequest request);
    void deleteUser(Long id);

}
