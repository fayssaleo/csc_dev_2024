package com.ta.csc.repositroy;

import com.ta.csc.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);

    @Transactional
    void deleteById(Long id);

    List<User> findAll();
}
