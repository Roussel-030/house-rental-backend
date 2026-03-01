package io.github.roussel030.module.user.repository;

import io.github.roussel030.module.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);
    List<User> findAllPaginated(int page, int size);
    Optional<User> findByIdOptional(Long id);
    void update(User user);
    void activeUser(Long id);
    boolean removeById(Long id);
    boolean existsByEmail(String email);
    long countALl();

}
