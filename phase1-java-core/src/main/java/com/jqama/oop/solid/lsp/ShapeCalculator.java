package com.jqama.oop.solid.lsp;

import java.util.List;

public class ShapeCalculator {
    public double totalArea(List<Shape> shapes) {
        return shapes.stream().mapToDouble(Shape::area).sum();
    }

    public Shape findLargest(List<Shape> shapes) {
        return shapes.stream().max((a, b) -> Double.compare(a.area(), b.area()))
                .orElseThrow(() -> new IllegalArgumentException("Shape list is empty"));
    }

    public List<Shape> filterFitting(List<Shape> shapes, double maxWidth, double maxHeight) {
        return shapes.stream().filter(s -> s.fitsInBoundingBox(maxWidth, maxHeight)).toList();
    }
}