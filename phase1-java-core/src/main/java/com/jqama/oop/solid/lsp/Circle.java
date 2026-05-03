package com.jqama.oop.solid.lsp;

public class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive");
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public boolean fitsInBoundingBox(double maxWidth, double maxHeight) {
        return 2 * radius <= maxWidth && 2 * radius <= maxHeight;
    }
}