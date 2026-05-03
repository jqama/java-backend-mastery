package com.jqama.oop.solid.isp;

public interface Deletable<ID> {
    void deleteById(ID id);
}