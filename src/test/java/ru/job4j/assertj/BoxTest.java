package ru.job4j.assertj;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere")
                .containsIgnoringCase("PH");
    }

    @Test
    void isThisUnknown() {
        Box box = new Box(1, 10);
        String name = box.whatsThis();
        assertThat(name).isNotNull()
                .isEqualTo("Unknown object");
    }

    @Test
    void whenAmountOfVertexesIsWrongThenVertexIsMinus1() {
        Box box = new Box(-10, 10);
        int res = box.getNumberOfVertices();
        assertThat(res).isNegative()
                .isEqualTo(-1);
    }

    @Test
    void whenAmountOfEdgesIsWrongThenVertexIsMinus1() {
        Box box = new Box(0, -10);
        int res = box.getNumberOfVertices();
        assertThat(res).isLessThan(0)
                .isEqualTo(-1);
    }

    @Test
    void whenAmountOfVertexesIsExpectedThenIsExist() {
        Box box = new Box(0, 10);
        assertThat(box.isExist()).hasToString("true")
                .isTrue();
    }

    @Test
    void whenAmountOfVertexesIsNotExpectedThenIsNotExist() {
        Box box = new Box(1, 10);
        assertThat(box.isExist()).isNotIn(true)
                .isFalse();
    }

    @Test
    void getAreaOfSphere() {
        Box box = new Box(0, 10);
        double res = box.getArea();
        assertThat(res).isPositive()
                .isEqualTo(1256.64d, withPrecision(0.01d));
    }

    @Test
    void getAreaOfTetrahedron() {
        Box box = new Box(4, 3);
        double res = box.getArea();
        assertThat(res).isNotNegative()
                .isCloseTo(15.59d, withPrecision(0.01));
    }

    @Test
    void getAreaOfCube() {
        Box box = new Box(8, 3);
        double res = box.getArea();
        assertThat(res).isNotNaN()
                .isCloseTo(54d, Percentage.withPercentage(1d));
    }
}
