package io.github.roussel030.user.repository;

import io.github.roussel030.user.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository, PanacheRepository<User> {

    @Override
    public void save(User user) {
        persist(user);
    }

    @Override
    public List<User> findAllPaginated(int page, int size) {
        return findAll()
                .page(page, size)
                .list();
    }

    @Override
    public void update(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public boolean removeById(Long id) {
        return deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }

}
