package io.github.roussel030.user.repository;

import io.github.roussel030.user.entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);
    List<User> findAllPaginated(int page, int size);
    void update(User user);
    boolean removeById(Long id);
    boolean existsByEmail(String email);

}
