package com.jqama.oop.solid.srp;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(long id);

    List<User> findAll();

    void deleteById(long id);
}