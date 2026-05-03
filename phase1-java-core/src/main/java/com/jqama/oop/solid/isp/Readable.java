package com.jqama.oop.solid.isp;

import java.util.List;
import java.util.Optional;

public interface Readable<T, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();
}