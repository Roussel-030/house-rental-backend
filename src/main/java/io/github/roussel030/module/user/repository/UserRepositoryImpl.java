package io.github.roussel030.module.user.repository;

import io.github.roussel030.module.user.entity.User;
import io.github.roussel030.module.user.enumeration.UserStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
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
    public Optional<User> findByIdOptional(Long id) {
        return Optional.ofNullable(findById(id));
    }

    @Override
    public void update(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public void activeUser(Long id) {
        update("status = ?1 where id = ?2", UserStatus.ACTIVE, id);
    }

    @Override
    public void deleteUser(User user) {
        delete(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }

    @Override
    public long countALl() {
        return count();
    }

}
