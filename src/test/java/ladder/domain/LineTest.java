package ladder.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LineTest {
    @Test
    void create_라인이_생성된다__전략_패턴_랜덤_boolean_true_인_경우() {
        Line line = new Line(List.of(new Point(0, Direction.RIGHT), new Point(1, Direction.LEFT), new Point(2, Direction.STRAIGHT)));
        assertThat(Line.create(3, () -> true)).isEqualTo(line);
    }

    @Test
    void create_라인이_생성된다__전략_패턴_랜덤_boolean_false_인_경우() {
        Line line = new Line(List.of(new Point(0, Direction.STRAIGHT), new Point(1, Direction.STRAIGHT), new Point(2, Direction.STRAIGHT)));
        assertThat(Line.create(3, () -> false)).isEqualTo(line);
    }

    @ParameterizedTest
    @NullSource
    void Line_포인트들이_null인_경우(List<Point> points) {
        assertThatThrownBy(() -> new Line(points)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Line_라인의_폭이_2미만_인_경우() {
        List<Point> points = List.of(new Point(0, Direction.RIGHT));
        assertThatThrownBy(() -> new Line(points)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1})
    void create_라인의_폭이_2미만_인_경우(int width) {
        assertThatThrownBy(() -> Line.create(width, () -> true)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Line_첫번째_포인트_방향이_왼쪽인_경우() {
        List<Point> points = List.of(new Point(0, Direction.LEFT), new Point(1, Direction.STRAIGHT));
        assertThatThrownBy(() -> new Line(points)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Line_첫번째_포인트_위치가_0이_아닌_경우() {
        List<Point> points = List.of(new Point(1, Direction.STRAIGHT), new Point(1, Direction.STRAIGHT));
        assertThatThrownBy(() -> new Line(points)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "LEFT, LEFT",
            "STRAIGHT, LEFT",
            "RIGHT, STRAIGHT",
            "RIGHT, RIGHT"
    })
    void Line_중간_포인트_방향이_충돌나는_경우(Direction previousDirection, Direction currentDirection) {
        List<Point> points = List.of(new Point(1, previousDirection), new Point(2, currentDirection), new Point(3, Direction.STRAIGHT));
        assertThatThrownBy(() -> new Line(points)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Line_인접한_중간_포인트_위치_차이가_1_이_아닌_경우() {
        List<Point> points = List.of(new Point(1, Direction.STRAIGHT), new Point(3, Direction.STRAIGHT), new Point(5, Direction.STRAIGHT));
        assertThatThrownBy(() -> new Line(points)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Line_마지막_포인트_방향이_오른쪽인_경우() {
        List<Point> points = List.of(new Point(0, Direction.STRAIGHT), new Point(1, Direction.RIGHT));
        assertThatThrownBy(() -> new Line(points)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Line_마지막_포인트_위치가_최대_폭이_아닌_경우() {
        List<Point> points = List.of(new Point(0, Direction.STRAIGHT), new Point(1, Direction.STRAIGHT), new Point(3, Direction.STRAIGHT));
        assertThatThrownBy(() -> new Line(points)).isInstanceOf(IllegalArgumentException.class);
    }
}