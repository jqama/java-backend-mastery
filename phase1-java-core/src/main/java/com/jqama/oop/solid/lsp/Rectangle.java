package com.jqama.oop.solid.lsp;

public class Rectangle extends Shape {
    private final double width, height;

    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Dimensions must be positive");
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public boolean fitsInBoundingBox(double maxWidth, double maxHeight) {
        return width <= maxWidth && height <= maxHeight;
    }
}