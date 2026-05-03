package com.jqama.oop.solid.lsp;

public abstract class Shape {
    public abstract double area();

    public abstract double perimeter();

    public boolean fitsInBoundingBox(double maxWidth, double maxHeight) {
        return area() <= maxWidth * maxHeight;
    }

    public String describe() {
        return "%s: area=%.2f, perimeter=%.2f".formatted(getClass().getSimpleName(), area(), perimeter());
    }
}