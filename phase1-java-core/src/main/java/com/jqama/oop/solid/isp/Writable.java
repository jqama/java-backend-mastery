package com.jqama.oop.solid.isp;

public interface Writable<T> {
    T save(T entity);
}