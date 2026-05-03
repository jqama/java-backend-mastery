package com.jqama.oop.solid.isp;

public interface OrderRepository extends Readable<Order, String>, Writable<Order>, Deletable<String> {
}