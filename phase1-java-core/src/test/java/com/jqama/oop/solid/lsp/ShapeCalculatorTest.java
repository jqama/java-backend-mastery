package com.jqama.oop.solid.lsp;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for LSP — Shape hierarchy and ShapeCalculator.
 *
 * The LSP proof is here: ShapeCalculator methods accept List<Shape>.
 * We pass in mixed lists of Rectangle and Circle — no instanceof, no casting.
 * If substitution is correct, all assertions pass regardless of subtype.
 *
 * AssertJ numeric assertions:
 * isCloseTo(expected, within(delta)) — for floating point comparisons
 * Never use isEqualTo() for doubles — floating point precision issues.
 */
@DisplayName("LSP — Shape hierarchy")
class ShapeCalculatorTest {

    private ShapeCalculator calculator;
    private Rectangle rectangle; // 4 x 5 = area 20
    private Circle circle; // r=3, area = 28.274...

    @BeforeEach
    void setUp() {
        calculator = new ShapeCalculator();
        rectangle = new Rectangle(4.0, 5.0);
        circle = new Circle(3.0);
    }

    // ── Rectangle contract ────────────────────────────────────────────────────

    @Nested
    @DisplayName("Rectangle")
    class RectangleTests {

        @Test
        @DisplayName("area should be width x height")
        void areaShouldBeWidthTimesHeight() {
            assertThat(rectangle.area()).isEqualTo(20.0);
        }

        @Test
        @DisplayName("perimeter should be 2 x (width + height)")
        void perimeterShouldBeCorrect() {
            assertThat(rectangle.perimeter()).isEqualTo(18.0);
        }

        @Test
        @DisplayName("should fit in a box larger than its dimensions")
        void shouldFitInLargerBox() {
            assertThat(rectangle.fitsInBoundingBox(10.0, 10.0)).isTrue();
        }

        @Test
        @DisplayName("should NOT fit in a box smaller than its width or height")
        void shouldNotFitInSmallerBox() {
            assertThat(rectangle.fitsInBoundingBox(3.0, 3.0)).isFalse();
        }

        @Test
        @DisplayName("should reject non-positive dimensions")
        void shouldRejectNonPositiveDimensions() {
            assertThatThrownBy(() -> new Rectangle(-1, 5))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    // ── Circle contract ───────────────────────────────────────────────────────

    @Nested
    @DisplayName("Circle")
    class CircleTests {

        @Test
        @DisplayName("area should be π × r²")
        void areaShouldBePiRSquared() {
            // use isCloseTo for floating point — never isEqualTo
            assertThat(circle.area()).isCloseTo(Math.PI * 9, within(0.0001));
        }

        @Test
        @DisplayName("perimeter should be 2πr")
        void perimeterShouldBeTwoPiR() {
            assertThat(circle.perimeter()).isCloseTo(2 * Math.PI * 3, within(0.0001));
        }

        @Test
        @DisplayName("circle with radius 3 (diameter 6) should NOT fit in 5x5 box")
        void shouldNotFitWhenDiameterExceedsBox() {
            assertThat(circle.fitsInBoundingBox(5.0, 5.0)).isFalse();
        }

        @Test
        @DisplayName("circle with radius 3 (diameter 6) should fit in 10x10 box")
        void shouldFitInLargerBox() {
            assertThat(circle.fitsInBoundingBox(10.0, 10.0)).isTrue();
        }
    }

    // ── LSP proof: ShapeCalculator works on any subtype ───────────────────────

    @Nested
    @DisplayName("ShapeCalculator — LSP in action")
    class ShapeCalculatorTests {

        @Test
        @DisplayName("totalArea should sum areas across mixed shape types")
        void totalArea_mixedShapes() {
            List<Shape> shapes = List.of(rectangle, circle);
            double expectedTotal = 20.0 + Math.PI * 9;

            assertThat(calculator.totalArea(shapes)).isCloseTo(expectedTotal, within(0.001));
        }

        @Test
        @DisplayName("findLargest should return shape with greatest area from a mixed list")
        void findLargest_shouldReturnBiggestShape() {
            Rectangle huge = new Rectangle(100, 100); // area = 10,000
            List<Shape> shapes = List.of(rectangle, circle, huge);

            Shape largest = calculator.findLargest(shapes);

            // No instanceof needed — area() works on any Shape
            assertThat(largest.area()).isEqualTo(10000.0);
        }

        @Test
        @DisplayName("findLargest on empty list should throw IllegalArgumentException")
        void findLargest_emptyList_shouldThrow() {
            assertThatThrownBy(() -> calculator.findLargest(List.of()))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("filterFitting should return only shapes that fit in the bounding box")
        void filterFitting_shouldReturnOnlyFittingShapes() {
            Rectangle small = new Rectangle(2.0, 2.0); // fits in 5x5
            Circle big = new Circle(10.0); // diameter=20, doesn't fit

            List<Shape> shapes = List.of(small, rectangle, circle, big);
            // In 5x5: small(2x2)=fits, rectangle(4x5)=fits, circle(d=6)=no, big(d=20)=no
            List<Shape> fitting = calculator.filterFitting(shapes, 5.0, 5.0);

            assertThat(fitting).hasSize(2)
                    .containsExactlyInAnyOrder(small, rectangle);
        }

        @Test
        @DisplayName("describe() should work on all subtypes without casting")
        void describe_worksOnAllSubtypes() {
            // LSP: no instanceof, no casting — polymorphism handles it
            List<Shape> shapes = List.of(rectangle, circle);

            shapes.forEach(shape -> {
                assertThat(shape.describe()).isNotBlank();
                assertThat(shape.describe()).contains("area=");
                assertThat(shape.describe()).contains("perimeter=");
            });
        }
    }
}